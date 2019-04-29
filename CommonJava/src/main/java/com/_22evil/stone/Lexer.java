package com._22evil.stone;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Lexer {
    // 正则表达式，包括空白符，数字，注释，标识符，符号==，<=,>=,&&,||,
    // \p{Punct} 表示与任意一个符号匹配，例如+，-
    // "(\\"|\\\\|\\n|[^"])*" 表示字符串
    public static String      regexPat_o = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")" +
            "|[A-Z_a-z][A-z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";

    public static final String reg1 = "\\s*";    // 空白
    public static final String reg2 = "(//.*)";     //代码注释开头
    public static final String reg3 = "([0-9]+)";   // 数字
    public static final String reg4 = "(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")";   // 字符串
    public static final String reg5 = "[A-Z_a-z][A-z_a-z0-9]*";   // 标识符
    public static final String reg6 = "=="; // 符号==
    public static final String reg7 = "<="; // 符号<=
    public static final String reg8 = ">="; // 符号>=
    public static final String reg9 = "&&"; // 符号&&
    public static final String reg10 = "\\|\\|"; // 符号||
    public static final String reg11 = "\\p{Punct}";    //任意单个字符 + - 等

    public static String regexPat = reg1 + "(" + reg2 + "|" + reg3
                                        + "|" + reg4 + "|" + reg5 + "|" + reg6 + "|" + reg7
                                        + "|" + reg8 + "|" + reg9 + "|" + reg10 + "|" + reg11 + ")?";

    {
        System.out.println(regexPat_o);
        System.out.println(regexPat);
    }
    private       Pattern     pattern  = Pattern.compile(regexPat);
    private       List<Token> queue    = new ArrayList<>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader r) {
        hasMore = true;
        reader = new LineNumberReader(r);
    }

    /**
     * 一行一行读，如果文件没有结束。
     * @return
     * @throws ParseException
     */
    public Token read() throws ParseException {
        if (fillQueue(0))
            return queue.remove(0);
        return Token.EOF;
    }

    public Token peek(int i) throws ParseException {
        if (fillQueue(i))
            return queue.get(i);
        return Token.EOF;
    }

    public boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size()) {
            if (hasMore)
                readLine();
            else
                return false;
        }
        return true;
    }

    protected void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        // 解释器显然需要记住这个是第几行
        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        // 前一个是代表是否可以跨越边界值搜索，后一个是代表匹配器是否使用定位界限,例如^ $这两个符号
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {  // 尝试将从区域开头开始的输入序列与该模式匹配
                addToken(lineNo, matcher);
                pos = matcher.end();
            } else {
                throw new ParseException("bad token at line " + lineNo);
            }
        }
        // 行结束
        queue.add(new IdToken(lineNo, Token.EOL));
    }

    protected void addToken(int lineNo, Matcher matcher) {
        // group(0) 指代匹配整个字符串，举个例子， str = 'i am zhujinshan', regexp = 'zhu(jin)(shan)'
        // 那么， group(0)='zhujinshan', group(1)='jin'
        String m = matcher.group(1);// 刚刚好去掉了空格开头
        if (m != null) {
            if (matcher.group(2) == null) {// 不需要解析注释
                Token token;
                if (matcher.group(3) != null) { // 数字
                    token = new NumToken(lineNo, Integer.parseInt(m));
                } else if (matcher.group(4) != null) { // 字符串 以""表示的
                    token = new StrToken(lineNo, toStringLiteral(m));
                } else {
                    token = new IdToken(lineNo, m); // 符号，标识符，保留字等等
                }
                queue.add(token);
            }
        }
    }

    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\')
                    c = s.charAt(++i);
                else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    protected static class NumToken extends Token {
        private int value;
        protected NumToken(int line, int v) {
            super(line);
            value = v;
        }

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public String getText() {
            return Integer.toString(value);
        }

        public int getNumber() {
            return value;
        }
    }

    protected static class IdToken extends Token {
        private String text;
        protected IdToken(int line, String id) {
            super(line);
            text = id;
        }

        public boolean isIdentifier() {
            return true;
        }

        public String getText() {
            return text;
        }
    }

    protected static class StrToken extends Token {
        private String literal;

        public StrToken(int line, String str) {
            super(line);
            literal = str;
        }

        @Override
        public boolean isString() {
            return true;
        }

        public String getText() {
            return literal;
        }
    }
}
