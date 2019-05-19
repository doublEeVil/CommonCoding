package com._22evil.test.concurrent;

/**
 *  1. 缓存不一致的问题
 *      高速缓存与主存数据不一致(硬件层面)
 *  2. 并发编程的三个要素
 *      原子性：
 *      可见性：
 *      有序性：
 *  3. 原子性： 不解释
 *  4. 可见性：所有缓存的数据都是最新的
 *  5. 有序性：JVM重排序
 *  6. volatile 保证了可见性，部分保证了有序性
 *
 *  7. volatile使用场景：
 *      状态标记
 *      doubleCheck
 */
public class ValatileTest {
    volatile boolean flag = false;

    /**
     * 用法1： 状态标记
     */
    public void testFlag() {
        Runnable run1 = () -> {
            while (flag) {
                //
            }
        };

        Runnable run2 = () -> {
            flag = false;
        };
    }

    /**
     * 用法2：
     */
    public void testDoubleCheck() {
        // 典型的就是单例模式的书写
//        class Singleton{
//            private volatile static Singleton instance = null;
//
//            private Singleton() {
//
//            }
//
//            public static Singleton getInstance() {
//                if(instance==null) {
//                    synchronized (Singleton.class) {
//                        if(instance==null)
//                            instance = new Singleton();
//                    }
//                }
//                return instance;
//            }
//        }
    }
}
