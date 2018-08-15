package com._22evil.arithmetic;

import java.util.Stack;

/**
 * 一个用数组实现的栈
 */
public class TStack<T> {
    private static final int DEFALT_SIZE = 20;
    private Object[] elementData;
    private int index = -1;

    public TStack() {
        elementData = new Object[DEFALT_SIZE]; //java是伪泛型
    }

    public TStack(int size) {
        elementData = new Object[size];
    }

    public void push(T t) {
        if (isFull()) {
            throw new RuntimeException("stack is full");
        }
        index++;
        elementData[index] = t;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        T t = (T) elementData[index];
        index--;
        return t;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("stack is empty");
        }
        return (T) elementData[index];
    }

    public int size() {
        return index + 1;
    }

    public boolean isFull() {
        return index == elementData.length - 1;
    }

    public boolean isEmpty() {
        return index == -1;
    }

    @Override
    public String toString() {
        String ret;
        if (isEmpty()) {
            ret = "[]";
        } else {
            ret = "[";
            for (int i = 0; i <= index; i++) {
                ret += elementData[i] + ",";
            }
            ret += "]";
        }
        return ret;
    }
}