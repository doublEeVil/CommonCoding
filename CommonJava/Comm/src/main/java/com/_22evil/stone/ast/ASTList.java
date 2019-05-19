package com._22evil.stone.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
    private List<ASTree> children;

    public ASTList(List<ASTree> children) {
        this.children = children;
    }

    @Override
    public ASTree child(int num) {
        return children.get(num);
    }

    @Override
    public String getLocation() {
        for (ASTree asTree :children) {
            if (asTree.getLocation() != null) {
                return asTree.getLocation();
            }
        }
        return null;
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        String sep = "";
        for (ASTree t : children) {
            sb.append(sep);
            sep = " ";
            sb.append(t.toString());
        }
        return sb.append(")").toString();
    }
}
