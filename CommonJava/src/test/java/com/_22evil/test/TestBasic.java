package com._22evil.test;

import com._22evil.stone.BasicParser;
import com._22evil.stone.Lexer;
import com._22evil.stone.ParseException;
import com._22evil.stone.Token;
import com._22evil.stone.ast.ASTree;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class TestBasic {

    @Test
    public void test() throws InterruptedException {
        Map<Object, Object> map = new ConcurrentHashMap<>();
        map.remove(1);
    }

    /**
     * 单词分析
     * @throws ParseException
     * @throws FileNotFoundException
     */
    @Test
    public void testStoneLexer() throws ParseException, FileNotFoundException{
        FileReader reader = new FileReader("F:\\CommonCoding\\CommonCoding\\CommonJava\\src\\main\\java\\com\\_22evil\\stone\\ast\\ASTree.java");
        Lexer lexer = new Lexer(reader);
        Token token;
        while (true) {
            token = lexer.read();
            if (token == Token.EOF)
                break;
            System.out.println("=>" + token.getText());
        }
    }

    /**
     * 语句分析
     * @throws ParseException
     * @throws FileNotFoundException
     */
    @Test
    public void testStoneParser() throws ParseException, FileNotFoundException{
        FileReader reader = new FileReader("F:\\CommonCoding\\CommonCoding\\CommonJava\\src\\test\\java\\com\\_22evil\\test\\tt.stone");
        Lexer lexer = new Lexer(reader);
        BasicParser bp = new BasicParser();
        while (lexer.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(lexer);
            System.out.println("=>" + ast.toString());
        }
    }
}



