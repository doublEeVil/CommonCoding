package com._22evil.test.grammar;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 定义3个类，
 * 一个hashcode 永远都是1
 * 一个永远equal
 * 一个hashcode = 1，且永远equal
 */
public class TestHashCode {

    @Test
    public void test() {
        Set set = new HashSet();

        Obj1 obj1_1 = new Obj1();
        Obj1 obj1_2 = new Obj1();
        set.add(obj1_1);
        set.add(obj1_2);
        System.out.println(set.size()); //结果是2

        set.clear();
        Obj2 obj2_1 = new Obj2();
        Obj2 obj2_2 = new Obj2();
        set.add(obj2_1);
        set.add(obj2_2);
        System.out.println(set.size()); //结果是2

        set.clear();
        Obj3 obj3_1 = new Obj3();
        Obj3 obj3_2 = new Obj3();
        set.add(obj3_1);
        set.add(obj3_2);
        System.out.println(set.size()); //结果是1

        // 结论，说明重写equal 不重写 hash在 hashMap下是肯定有问题的。
    }
}

class Obj1 {
    @Override
    public int hashCode() {
        return 1;
    }
}

class Obj2 {

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}

class Obj3 {
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}