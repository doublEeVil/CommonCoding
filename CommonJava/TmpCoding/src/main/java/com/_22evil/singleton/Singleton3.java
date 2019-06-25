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
                    // 唯一存在的问题， synchronized不能保证块内部不会发生指令重排序
                    // new Singleton3()不是原子操作
                    // 具体过程可能是  1. 分配内存 2. 对象初始化 3. instance指向该内存
                    // 也可能是       1. 分配内存 2. instance指向该对象 3. 对象初始化
                    // 至于是否发生指令重排序，未知，
                    // 所以，多线程条件下，有可能出现返回未初始化对象的错误
                }
            }
        }
        return instance;
    }
}