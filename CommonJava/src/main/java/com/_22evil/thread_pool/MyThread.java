package com._22evil.thread_pool;

import java.util.concurrent.BlockingQueue;

/**
 * Created by shangguyi on 2017/10/19.
 */
public class MyThread extends Thread {

    private BlockingQueue<Runnable> queue;

    public MyThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                Runnable runnable = queue.take();
                runnable.run();
            }
        } catch (Exception e) {

        }
    }
}
