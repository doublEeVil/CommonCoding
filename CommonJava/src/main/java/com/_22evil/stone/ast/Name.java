package com._22evil.stone.ast;

import com._22evil.stone.Token;

public class Name extends ASTLeaf {
    public Name(Token token) {
        super(token);
    }

    public String name() {
        return token.getText();
    }
}
