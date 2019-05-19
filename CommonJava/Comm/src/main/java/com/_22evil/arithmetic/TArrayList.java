package com._22evil.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 数据结构学习-列表(数组简单实现)
 */
public class TArrayList<T> implements TList<T>{
    private final static int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size = 0;

    public TArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public TArrayList(int capacity) {
        elementData = new Object[capacity];
    }

    public void add(T t) {
        checkCapacity();
        elementData[size] = t;
        size++;
    }

    public void add(T t, int index) {
        checkRange(index);
        checkCapacity();
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = t;
        size++;
    }

    public boolean contain(T t) {
        return indexOf(t) > -1;
    }

    public T get(int index) {
        checkRange(index);
        return (T) elementData[index];
    }

    public T remove(T t) {
        if (contain(t)) {
            remove(indexOf(t));
        }
        return t;
    }

    public T remove(int index) {
        checkRange(index);
        T val = get(index);
        if (index == size - 1) {
            // 最后一个元素
        } else {
            System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        }
        elementData[size] = null;
        size--;
        return val;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int indexOf(T t) {
        for (int i = 0; i < size; i++) {
            if (t == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(T t) {
        // int index = -1;
        // for (int i = 0; i < size; i++) {
        //     if (t == elementData[i]) {
        //         index = i;
        //     }
        // }
        // return index;

        for (int i = size - 1; i >=0; i--) {
            if (t == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    public void set(T t, int index) {
        checkRange(index);
        elementData[index] = t;
    }

	@Override
	public TIterator iterator() {
		return new TArrayIterator(this);
	}
    
    /**
     * 重新调整容量大小
     */
    private void checkCapacity() {
        if (size == elementData.length) {
            int newLenth = elementData.length * 2;
            elementData = Arrays.copyOf(elementData, newLenth);
        }
    }

    private void checkRange(int index) {
        if (index < 0 || index > size) {
            throw new RuntimeException("不允许的index值");
        } 
    }

    class TArrayIterator implements TIterator<T> {

        private int pos;
        private TArrayList<T> list;

        public TArrayIterator(TArrayList list) {
            this.list = list;
            pos = 0;
        }

		@Override
		public boolean hasNext() {
			return pos < list.size();
		}

		@Override
		public T next() {
			return list.get(pos++);
		}

    }
}