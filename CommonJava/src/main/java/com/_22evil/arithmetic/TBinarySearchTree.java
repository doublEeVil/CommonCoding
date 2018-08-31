package com._22evil.arithmetic;

import java.util.NoSuchElementException;

/**
 * 二叉搜索树
 * @param <KEY,VALUE></KEY,VALUE>
 */
public class TBinarySearchTree<KEY extends Comparable, VALUE> {
    private Node root;

    private class Node {
        private KEY key;
        private VALUE val;
        private Node left, right;
        private int size;

        public Node(KEY key, VALUE val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public TBinarySearchTree() {

    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     *
     * @return
     */
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean contain(KEY key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        return get(key) != null;
    }

    /**
     *
     * @param key
     * @return
     */
    public VALUE get(KEY key) {
        return get(root, key);
    }

    private VALUE get(Node x, KEY key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(KEY key, VALUE val) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        //assert check();
    }

    private Node put(Node x, KEY key, VALUE val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     *
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("tree is empty");
        root = deleteMin(root);
        //assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     *
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("tree is empty");
        root = deleteMax(root);
        //assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(KEY key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        root = delete(root, key);
        //assert check();
    }

    private Node delete(Node x, KEY key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     *
     * @return
     */
    public KEY min() {
        if (isEmpty()) throw new NoSuchElementException("tree is empty");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    /**
     *
     * @return
     */
    public KEY max() {
        if (isEmpty()) throw new NoSuchElementException("tree is empty");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    /**
     *
     * @return
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    /**
     *
     */
    public void printAll() {
        print(root, 1);
    }

    private void print(Node x, int deepth) {
        if (x == null) return;
        for (int i = 0; i < deepth * 3; i++) {
            System.out.print("-");
        }
        System.out.print(x.val + "\n");
        print(x.left, deepth + 1);
        print(x.right, deepth + 1);
    }
}

