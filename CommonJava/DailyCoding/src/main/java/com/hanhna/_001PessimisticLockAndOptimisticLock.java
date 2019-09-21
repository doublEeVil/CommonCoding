package com.hanhna;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 悲观锁和乐观锁的比较
 * 所谓悲观锁，就是默认对方会修改数据，所以全程加锁处理数据
 * 乐观锁，就是默认对方不会修改数据。只有在最后提交时才会对比一下是否有过其他线程修改。所以实际上并未加锁，但最后一步对比数据因不同业务希求，可能会加锁
 * 乐观锁，最后对比一步，可能是采用CAS, 也可以是加版本号来处理
 */
public class _001PessimisticLockAndOptimisticLock {
    private int num1 = 0;
    private int num2 = 0;
    private AtomicInteger num3 = new AtomicInteger(0);

    public void test() {
        // 各开150个线程测试
        // 线程不安全的情况
        for (int i = 0; i < 150; i++) {
            new Thread(()->{
                num1++;
            }).start();
        }

        // 悲观锁的情况
        for (int i = 0; i < 150; i++) {
            new Thread(()->{
                num2Add();
            }).start();
        }

        // 乐观锁情况
        for (int i = 0; i < 150; i++) {
            new Thread(()->{
                num3.incrementAndGet();
            }).start();
        }

        // 打印结果
        while (true) {
            if (Thread.activeCount() <= 2) {
                System.out.println("====num1 " + num1);
                System.out.println("====num2 " + num2);
                System.out.println("====num3 " + num3.get());
                break;
            }
            try {
                Thread.sleep(2000L);
            } catch (Exception e) {

            }
        }
    }

    private synchronized void num2Add() {
        num2++;
    }

    public static void main(String ... args) {
        new _001PessimisticLockAndOptimisticLock().test();
    }
}
