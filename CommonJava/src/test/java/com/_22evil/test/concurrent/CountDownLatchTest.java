package com._22evil.test.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private CountDownLatch latch = new CountDownLatch(3);

    @Test
    public void test() {
        new Thread(() -> {
            System.out.println("----111");
            latch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("----222");
            latch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("----333");
            latch.countDown();
        }).start();

        System.out.println("----end 111");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----end 222");
    }
}
