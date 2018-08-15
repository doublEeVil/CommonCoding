package com._22evil.arithmetic;

/**
 * 自定义迭代器，方便实现，只有常用的两个接口
 */
public interface TIterator<T> {
    boolean hasNext();
    T next();
}