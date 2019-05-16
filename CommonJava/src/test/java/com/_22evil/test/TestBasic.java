package com._22evil.test;

import com._22evil.image.ImageTool;
import com._22evil.stone.*;
import com._22evil.stone.ast.ASTree;
import com._22evil.stone.ast.NullStmnt;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class TestBasic {

    @Test
    public void test() throws InterruptedException {

    }

    /**
     * 单词分析
     * @throws ParseException
     * @throws FileNotFoundException
     */
    @Test
    public void testStoneLexer() throws ParseException, FileNotFoundException{
        FileReader reader = new FileReader("F:\\CommonCoding\\CommonCoding\\CommonJava\\src\\test\\java\\com\\_22evil\\test\\tt.stone");
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

    @Test
    public void testBasicInterpreter() throws ParseException, FileNotFoundException {
        FileReader reader = new FileReader("F:\\CommonCoding\\CommonCoding\\CommonJava\\src\\test\\java\\com\\_22evil\\test\\tt.stone");
        Lexer lexer = new Lexer(reader);
        BasicParser bp = new BasicParser();
        Environment env = new BasicEnv();
        while (lexer.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(lexer);
            if (ast instanceof NullStmnt)
                continue;
            Object v = ((BasicEvaluator.ASTreeEx)ast).eval(env);
            System.out.println("==>" + v);
        }
    }

    @Test
    public void testImg() throws Exception{
        String f1 = "C:\\Users\\Administrator\\Desktop\\pp\\33.png";
        String f2 = "C:\\Users\\Administrator\\Desktop\\pp\\22.jpg";
        //ImageTool.gray(f1, f2);
        ImageTool.waterline(f1, f2, "  this is text");
    }
}



