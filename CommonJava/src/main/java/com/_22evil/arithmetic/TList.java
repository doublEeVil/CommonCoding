package com._22evil.arithmetic;

public interface TList<T> {
    
    public void add(T t);

    public void add(T t, int index);

    public boolean contain(T t);

    public T get(int index);

    public T remove(T t);

    public T remove(int index);

    public int size();

    public boolean isEmpty();

    public int indexOf(T t) ;

    public int lastIndexOf(T t);

    public void set(T t, int index);

    public TIterator iterator();
}