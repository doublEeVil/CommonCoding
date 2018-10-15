package com._22evil.test.concurrent;

import org.junit.Test;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier 相当于等待一批放一批
 */
public class CyclicBarrierTest {

    private CyclicBarrier barrier = new CyclicBarrier(3);

    @Test
    public void test() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            pool.execute(() -> {
                System.out.println("--before");
                try {
                    barrier.await(); // 一直等待， 一直到到满足3个线程等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("--after");
            });
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }
}
