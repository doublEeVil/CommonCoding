package com._22evil.stone;
import java.io.IOException;
public class ParseException extends Exception {
    public ParseException(Token t) {
        this("", t);
    }

    public ParseException(String m, Token t) {
        super("syntax error around " + location(t) + ". " + m);
    }

    private static String location(Token t) {
        if (t == Token.EOF) {
            return "the last line";
        } else {
            return "\"" + t.getText() + "\"" + t.getLineNumber();
        }
    }

    public ParseException(IOException e) {
        super(e);
    }

    public ParseException(String m) {
        super(m);
    }
}
