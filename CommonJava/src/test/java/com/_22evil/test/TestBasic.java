package com._22evil.test;

import com._22evil.stone.Lexer;
import com._22evil.stone.ParseException;
import com._22evil.stone.Token;
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

    @Test
    public void testStone() throws ParseException, FileNotFoundException{
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
}



