package com._22evil.arithmetic;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 数据结构学习-列表(链表简单实现, 不考虑双端列表)
 */
public class TLinkedList<T> implements TList<T>{
    private TLinkedNode<T> first;
    private TLinkedNode<T> last;
    private int size = 0;

    public TLinkedList() {
    }

    public void add(T t) {
        TLinkedNode node = new TLinkedNode(t);
        if (null == first) {
            first = node;
            last = node;
        } else {
            last.next = node;
            last = node;
        }
        size++;
    }

    public void add(T t, int index) {
        checkRange(index);
        TLinkedNode node = new TLinkedNode(t);
        if (index == 0) {
            node.next = first;
            first = node;
        } else {
            TLinkedNode tnode = node(index);
            node.next = tnode.next;
            tnode.next = node;
        }
        size++;
    }

    public boolean contain(T t) {
        return indexOf(t) > -1;
    }

    public T get(int index) {
        checkRange(index);
        return (T) node(index).value;
    }

    public T remove(T t) {
        if (contain(t)) {
            remove(indexOf(t));
        }
        return t;
    }

    public T remove(int index) {
        checkRange(index);
        TLinkedNode node = first;
        T ret;
        if (index == 0) {
            ret = first.value;
            first = first.next;
        } else {
            for (int i = 0; i < index - 1; i++) {
                node = node.next;
            }
            ret = (T)node.next.value;
            node.next = node.next.next;
        }

        size--;
        return ret;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int indexOf(T t) {
        TLinkedNode node = first;
        for (int i = 0; i < size; i++) {
            if (node.value == t) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    public int lastIndexOf(T t) {
        TLinkedNode node = first;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (node.value == t) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    public void set(T t, int index) {
        checkRange(index);
        TLinkedNode node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.value = t;
    }

    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("index范围不合法");
        }
    }

    private TLinkedNode node(int index) {
        TLinkedNode node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

	@Override
	public TIterator iterator() {
		return new TLinkedIterator(this);
    }
    
    private static class TLinkedNode<T> {
        T value;
        TLinkedNode<T> next;

        public TLinkedNode(T t) {
            value = t;
        }
    }

    class TLinkedIterator implements TIterator<T> {

        private int pos;
        private TLinkedList<T> list;

        public TLinkedIterator(TLinkedList list) {
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