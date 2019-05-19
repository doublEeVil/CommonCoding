package com._22evil.test.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private Semaphore semaphore = new Semaphore(2, true);

    @Test
    public void test() {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            pool.execute(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("----start---" + Thread.currentThread().getName());
                try {
                    Thread.sleep(123);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("-----end----");
                semaphore.release();
            });
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }
}
