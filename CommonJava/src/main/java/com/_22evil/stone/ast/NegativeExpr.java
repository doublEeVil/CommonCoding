package com._22evil.stone.ast;

import java.util.List;

public class NegativeExpr extends ASTList {
    public NegativeExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree operand() {
        return child(0);
    }

    public String toString() {
        return "-" + operand();
    }
}
