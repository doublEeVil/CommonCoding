package com._22evil.stone.ast;

import com._22evil.stone.Token;

public class StringLiteral extends ASTLeaf {
    public StringLiteral(Token token) {
        super(token);
    }

    public String value() {
        return token.getText();
    }
}
