package com._22evil.stone;
import com._22evil.stone.ast.ASTree;
public class StoneException extends RuntimeException {

    public StoneException(String m) {
        super(m);
    }

    public StoneException(String m, ASTree t) {
        super(m + " " + t.getLocation());
    }
}
