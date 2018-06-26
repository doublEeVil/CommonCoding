package com._22evil.singleton;

/**
 * 常用实现方法
 * 可以使用，但写法很啰嗦
 */
public class Singleton3 {
    private static Singleton3 instance;

    private Singleton3() {

    }

    public static Singleton3 getInstance() {
        if (null == instance) {
            synchronized(Singleton3.instance) {
                if (null == instance) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}