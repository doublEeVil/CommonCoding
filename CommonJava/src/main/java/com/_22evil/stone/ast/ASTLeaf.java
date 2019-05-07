package com._22evil.stone.ast;

import com._22evil.stone.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ASTLeaf extends ASTree {
    private static List<ASTree> empty = new ArrayList<>();
    protected Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    @Override
    public ASTree child(int num) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getLocation() {
        return "at line:" + token.getLineNumber();
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public Iterator<ASTree> children() {
        return empty.iterator();
    }

    public String toString() {
        return token.getText();
    }

    public Token token() {
        return token;
    }
}
