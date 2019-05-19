package com._22evil.test.concurrent;


import org.junit.Test;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在锁条件下，
 * condition可以保证 ThreadA 在 condition await下做出让步
 */
public class ConditionTest {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void before() {
        System.out.println("11111");
        lock.lock();
        System.out.println("A");
        condition.signal();
        lock.unlock();

    }

    private void after() {
        System.out.println("22222");
        try {
            lock.lock();
            condition.await();
            System.out.println("B");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    @Test
    public void test() {

        ConditionTest basic = new ConditionTest();
        new Thread(() -> {
            basic.after();
        }).start();

        new Thread(() -> {
            basic.before();
        }).start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }
}
