package com._22evil.thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by shangguyi on 2017/10/19.
 */
public class SImpleThreadPool {
    private BlockingQueue<Runnable> queue;

    public void execute(Runnable run) {
        try {
            queue.put(run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SImpleThreadPool(int size) {
        queue = new ArrayBlockingQueue<Runnable>(size);
        try {
            for (int i = 0; i < size; i++) {
                new MyThread(queue).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
