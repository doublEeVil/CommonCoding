package com._22evil.singleton;

/**
 * 多线程版本
 * 不推荐，性能有问题 
 */

public class Singleton2 {
    private static Singleton2 instance;

    private Singleton2 () {

    }

    public static synchronized Singleton2 getInstance() {
        if (null == instance) {
            instance = new Singleton2();
        }
        return instance;
    }
}