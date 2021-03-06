package com._22evil.singleton;

/**
 * 最简单的单例写法
 * 不推荐
 *
 * 问题所在：
 *  不能保证线程安全
 */
public class Singleton1 {
    private static Singleton1 instance;

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        if (null == instance) {
            instance = new Singleton1();
        }
        return instance;
    }
}