package com._22evil.test.arithmetic;

import com._22evil.arithmetic.TBinarySearchTree;
import org.junit.Test;

public class TestBinaryTree {

    @Test
    public void test1() {
        TBinarySearchTree<Integer, Integer> tree = new TBinarySearchTree<>();
        tree.put(12,12);
        tree.put(50,50);
        tree.put(60,60);
        tree.put(45,45);
        tree.put(78,78);
        tree.printAll();
    }
}
