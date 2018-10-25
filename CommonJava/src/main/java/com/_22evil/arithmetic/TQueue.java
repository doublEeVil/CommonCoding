package com._22evil.arithmetic;

/**
 * 简单队列
 */
public class TQueue<T> {
    private static final int DEFALT_SIZE = 20;
    private Object[] elementData;
    private int head = 0;
    private int size = 0;

    public TQueue() {
        elementData = new Object[DEFALT_SIZE];
    }

    public TQueue(int capacity) {
        elementData = new Object[capacity];
    }

    /**
     * 入队
     */
    public void offer(T t) {
        if (isFull()) {
            throw new RuntimeException("queue is page");
        }
        int index = head + size;
        if (index >= elementData.length) {
            index -= elementData.length;
        }
        elementData[index] = t;
        size++;
    }

    /**
     * 出队, 抛异常还是返回null?
     */
    public T poll() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        T t = (T)elementData[head];
        elementData[head] = null;
        head++;
        if (head >= elementData.length) {
            head = 0;
        }
        size--;
        return t;
    }

    /**
     * 队头
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        return (T) elementData[head];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size() == elementData.length;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            String ret = "[";
            for (int i = 0; i < size; i++) {
                if (head + i < elementData.length) {
                    ret += elementData[head + i] + ",";
                } else {
                    ret += elementData[head + i - elementData.length] + ",";
                }
            }
            return ret + "]";
        } 
    }
}