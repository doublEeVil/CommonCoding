package com._22evil.test.regex;
import com._22evil.util.PrintUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则表达式
 */
public class TestRegex {
    /**
     * 基本用法-匹配
     */
    @Test
    public void testBasicMatch() {
        // 格式 4位数字 - 姓名
        String src = "1357-zhujinshan";
        Pattern ptn = Pattern.compile("\\d{4}-\\w+");
        Matcher matcher = ptn.matcher(src);
        if (matcher.matches()) {
            System.out.println("完全匹配");
        } else {
            System.out.println("匹配错误");
        }
    }

    /**
     * 基本用法-查找
     */
    @Test
    public void testBasicFind() {
       String src = "下面告知一下联系人的联系方式，张三155245547989，李四：13789896566，王五：18270818989，这是他们的联系方式，还是希望记一下，"
               + "同时，我们的集合地点是明阳路1854号";
       Pattern ptn = Pattern.compile("1\\d{10}");
       Matcher matcher = ptn.matcher(src);
       while (matcher.find()) {
           System.out.println(matcher.group());
       }

    }

    /**
     * 1. 基本用法-查找中文汉字
     */
    @Test
    public void findCNChars() {
        String input = "“汉字”用英语怎么说?Chinese Word?_搜狐教育_搜狐网";
        List<Character> CNCharList = new ArrayList<>(input.length());
        for (int i = 0, len = input.length(); i < len; i++) {
            if (Pattern.matches("[\\u4E00-\\u9FA5]", "" + input.charAt(i))) {
                CNCharList.add(input.charAt(i));
            }
        }
        PrintUtil.printVar(CNCharList);
    }
}
