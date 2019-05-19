package com._22evil.test.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BlockingQueue下面有两种阻塞队列实现
 * FIFO队列： LinkedBlockingQueue,
 *           ArrayBlockQueue(固定长度)
 * 优先级队列：PriorityBlockingQueue
 */
public class BlockingQueueTest {

    @Test
    public void testProducerConsumer() {

        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
        // 生产者
        class Producer implements Runnable {
            @Override
            public void run() {
                try {
                    queue.put("生产");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---已经生产");
            }
        }

        // 消费者
        class Consumer implements Runnable {
            @Override
            public void run() {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---已经消费");
            }
        }

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; i++) {
            pool.execute(new Producer());
        }

        for (int i = 0; i < 9; i++) {
            pool.execute(new Consumer());
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }
}
