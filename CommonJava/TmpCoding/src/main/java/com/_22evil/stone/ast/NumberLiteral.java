package com._22evil.stone.ast;

import com._22evil.stone.Token;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token token) {
        super(token);
    }

    public int value() {
        return token.getNumber();
    }
}
