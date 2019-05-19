package com._22evil.singleton;

/**
 * 最常用的实现方式之一
 * 一般程序加载之初就启动
 */
public class Singleton4 {
    private static final Singleton4 instance = new Singleton4();

    private Singleton4() {

    }

    public static Singleton4 getInstance() {
        return instance;
    }
}