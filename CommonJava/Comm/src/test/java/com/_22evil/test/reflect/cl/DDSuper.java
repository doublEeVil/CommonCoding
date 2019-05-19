package com._22evil.test.reflect.cl;
public class DDSuper {

    // 常量
    public static final int MAX = 12;

    // 变量
    public int n1;
    public int n2;

    private int n3;

    // 类变量
    public static int n4;


    // 静态公开方法
    public static void f1() {
        System.out.println("++++f1+++");
    }

    // 静态私有方法
    private static void f2() {
        System.out.println("++++f2+++");
    }

    // 普通公开方法
    public void f3() {
        System.out.println("++++f3+++");
    }

    // 私有方法
    public void f4() {

    }
}
