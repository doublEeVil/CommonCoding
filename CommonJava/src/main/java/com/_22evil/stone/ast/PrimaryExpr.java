package com._22evil.stone.ast;

import java.util.List;

public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<ASTree> children) {
        super(children);
    }

    public static ASTree create(List<ASTree> o) {
        return o.size() == 1 ? o.get(0) : new PrimaryExpr(o);
    }
}
