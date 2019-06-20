package com._22evil.DFA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * 有穷状态机
 * 一个具体应用：敏感词过滤或者具体单词统计
 */

public class WordFilter {
    private static Map<Character, WordNode> WORD_MAP = new HashMap<>();
    private static Set<Character> NOSENSE_WORD_SET = new HashSet<>();
    /**
     * 导入敏感词
     */
    public static void loadLimitedWords(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("加载词库不存在 " + filePath);
        }
        List<String> list = new ArrayList<>();
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader  reader= new BufferedReader(fReader);  
            reader.lines().forEach(s->{
                list.add(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("读取文件发生异常");
            System.exit(0);
        }

        for (String word : list) {
            if (word.equals("")) {
                continue;
            }
            addLimitedWord(word);
        }
    }

    public static void addLimitedWord(String word) {
        char[] chars = word.toCharArray();
        WordNode subNode = null;

        for (int i = 0, len = chars.length; i < len; i++) {
            char c1 = chars[i];
            if (i == 0) {
                if (!WORD_MAP.containsKey(c1)) {
                    WORD_MAP.put(c1, new WordNode(c1));
                }
                subNode = WORD_MAP.get(c1);
            }
            if (i < len - 1) {
                char c2 = chars[i+1];
                if (null == subNode.subMap) {
                    subNode.subMap = new HashMap<>();
                }
                if (!subNode.subMap.containsKey(c2)) {
                    subNode.subMap.put(c2, new WordNode(c2));
                }
                subNode = subNode.subMap.get(c2);
            } else {
                subNode.isEnd = true; // 至此，一个限制词的输入已经完成
            }
        }
    }

    /**
     * 加载没有意义的分割词
     * 例如傻+比啊 与 傻比没有任何区别 分歌词就是+
     */
    public static void loadNoSenseWords(Character ... words) {
        if (null == words) {
            return;
        }
        for (char c : words) {
            addNoSenseWord(c);
        }
    } 

    public static void addNoSenseWord(char c) {
        NOSENSE_WORD_SET.add(c);
    }

    /**
     * 将大写转小写
     * 全角转半角
     * 功能待完善
     */
    private static char getCommonChar(char c) {
        // if (c >= 'A' && c <= 'Z') {
        //     int s = (int) c + 32;
        //     return new Character(s);
        // }
        return c;
    }

    /**
     * 判断输入语句是否包含限制词
     */
    public static boolean containLimitWord(String input) {
        char[] chars = input.toCharArray();
        char c;
        WordNode node;
        for (int i = 0, len = chars.length; i < len; i++) {
            c = chars[i];
            // 过滤毫无意义的字符
            if (NOSENSE_WORD_SET.contains(c)) {
                continue;
            }
            // 转换大小写
            c = getCommonChar(c);
            node = WORD_MAP.get(c);
            // 不存在
            if (null == node) {
                continue;
            }
            if (node.isEnd) {
                return true;
            }
            int j = i + 1;
            while (j < len) {
                if (NOSENSE_WORD_SET.contains(chars[j])) {
                    // 过滤没有意义的词
                    j++;
                    continue;
                }
                node = node.subMap.get(chars[j]);
                if (null == node) {
                    break;
                }
                if (node.isEnd) {
                    return true;
                } else {
                    j++;
                }
            }
        }
        return false;
    }

    /**
     * 判断输入语句是否包含限制词
     * 如果有，用特定字符代替
     */
    public static String getFiltString(String input, char replace) {
        char[] chars = input.toCharArray();
        char c;
        boolean canMark;
        WordNode node;
        int markNum;
        for (int i = 0, len = chars.length; i < len; i++) {
            canMark = false;
            markNum = 0;
            c = chars[i];
            // 过滤毫无意义的字符
            if (NOSENSE_WORD_SET.contains(c)) {
                continue;
            }
            // 转换大小写
            c = getCommonChar(c);
            node = WORD_MAP.get(c);
            if (null == node) {
                // 直接下一个判断
                continue;
            }
            if (node.isEnd) {
                // 已经匹配，继续最大长度匹配
                canMark = true;
                markNum = 1;
            }
            int j = i + 1;
            while (j < len) {
                if (NOSENSE_WORD_SET.contains(chars[j])) {
                    // 过滤没有意义的词
                    j++;
                    continue;
                }
                if (null == node.subMap) {
                    // 到达尾部了
                    break;
                }
                node = node.subMap.get(chars[j]);
                if (null == node) {
                    break;
                }
                if (node.isEnd) {
                    canMark = true;
                    markNum = j - i + 1;
                } else {
                    j++;
                }
            }
            if (canMark) {
                for(int k = 0; k < markNum; k++) {
                    chars[k+i] = replace;
                }
            }
        }
        return new String(chars);
    }

    public static String getFiltString(String input) {
        return getFiltString(input, '*');
    }

    static class WordNode {
        public char key;
        public boolean isEnd = false;   //是否是尾节点
        public Map<Character, WordNode> subMap;

        public WordNode(char c) {
            key = c;
        }

        public WordNode(char c, boolean isEnd) {
            this.key = c;
            this.isEnd = isEnd;
        }
    }
}