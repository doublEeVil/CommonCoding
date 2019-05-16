package com._22evil.stone;

import com._22evil.stone.ast.ASTree;
import com._22evil.stone.ast.NullStmnt;

import java.io.FileReader;
import java.io.IOException;

public class BasicInterpreter {
    public static void main(String ... args) throws ParseException, IOException {
        run(new BasicParser(), new BasicEnv());
    }

    public static void run(BasicParser bp, Environment env) throws ParseException, IOException {
        FileReader reader = new FileReader("F:\\CommonCoding\\CommonCoding\\CommonJava\\src\\test\\java\\com\\_22evil\\test\\tt.stone");
        Lexer lexer = new Lexer(reader);
        while (lexer.peek(0) != Token.EOF) {
            ASTree ast = bp.parse(lexer);
            if (ast instanceof NullStmnt)
                continue;
            Object v = ((BasicEvaluator.ASTreeEx)ast).eval(env);
            System.out.println("==>" + v);
        }
    }
}
