package com._22evil.singleton;

/**
 * 静态内部类实现方式
 * 也是推荐大家使用的方式 
 */

public class Singleton5 {

    private Singleton5() {

    }
    public static Singleton5 getInstance() {
        return InstanceHolder.instance;
    }

    static class InstanceHolder {
        private static final Singleton5 instance = new Singleton5();
    }
}