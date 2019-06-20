package com._22evil.stone.ast;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class ASTree implements Iterable<ASTree> {

    public abstract ASTree child(int num);

    public abstract String getLocation();

    public abstract int numChildren();

    public abstract Iterator<ASTree> children();

    public Iterator<ASTree> iterator() {
        return children();
    }

    @Override
    public void forEach(Consumer<? super ASTree> action) {

    }

    @Override
    public Spliterator<ASTree> spliterator() {
        return null;
    }
}
