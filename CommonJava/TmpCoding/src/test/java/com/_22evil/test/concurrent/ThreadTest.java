package com._22evil.test.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 对静态方法 Thread.yield() 的调用声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行
 */
public class ThreadTest {

    private class A extends Thread {
        @Override
        public void run() {
            System.out.println("---A");
        }
    }

    private class B implements Runnable {
        A a;
        public B(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---B");
        }
    }

    /**
     * 测试Thread Join方法， 保证Join 的线程先执行
     */
    @Test
    public void testJoin() {
        A a = new A();
        Thread t = new Thread(new B(a));
        a.start();
        t.start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }

    /**
     * 测试线程中断：
     * 1. Thread interrupted()
     * 2. ExecutorService shutdownNow()
     * 3. Future cancel();
     *
     * 对于Future 的cancel方法来说(FutureTask是具体实现类， Future是接口)
     *  如果任务运行之前调用了该方法，那么任务就不会被运行；
        如果任务已经完成或者已经被取消，那么该方法方法不起作用；
        如果任务正在运行，并且 cancel 传入参数为 true，那么便会去终止与 Future 关联的任务
     */
    @Test
    public void testInterrupted() {
        Runnable run1 = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("线程中断1");
            }
        };

        Thread thread1 = new Thread(run1);
        thread1.start();
        thread1.interrupt();

        ExecutorService pool = Executors.newCachedThreadPool();
        Runnable run2 = () -> {
            try {
                System.out.println("---");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("线程中断2");
            }
        };
        Future<?> task = pool.submit(run2);
        try {
            Thread.sleep(12);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.cancel(true);

        pool.execute(() -> {
            try {
                Thread.sleep(1234);
            } catch (InterruptedException e) {
                System.out.println("线程中断3");
            }
        });
        pool.shutdownNow();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }


}
