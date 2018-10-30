package com._22evil.test.error.thread;
import com._22evil.test.error.ThreadType;

import java.math.BigInteger;
/**
 * 普通线程
 */
public class CommonThread implements Runnable{

    private ThreadType type;
    public CommonThread(ThreadType type) {
        this.type = type;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName("CommonThread");
        thread.start();
    }

    @Override
    public void run() {
        if (type == ThreadType.DEAD_LOCK) {
            // 模拟死锁
            deadLock();
        } else if (type == ThreadType.DEAD_LOOP) {
            // 模拟死循环
            deadLoop();
        } else if (type == ThreadType.LONG_WAIT) {
            // 模拟长时间等待
            longWait();
        } else if (type == ThreadType.KILLED) {
            killed();
        }
    }

    private void deadLock() {
        Obj obj1 = new Obj();
        Obj obj2 = new Obj();

        new Thread(() -> {
            synchronized (obj1) {
                try {
                    System.out.println("begin1111");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2) {
                    System.out.println("===");
                }
                System.out.println("end1111");
            }
        }).start();

        new Thread(() -> {
            synchronized (obj2) {
                try {
                    System.out.println("begin2222");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1) {
                    System.out.println("===");
                }
                System.out.println("end222");
            }
        }).start();
    }

    private void deadLoop() {
        while (true) {

        }
    }

    private void longWait() {
        BigInteger bigInteger = BigInteger.valueOf(1);
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                bigInteger = bigInteger.add(BigInteger.valueOf(i));
            }
        } catch (Exception e) {

        }
        System.out.println(bigInteger.intValue());
    }

    private void killed() {
        try {
            Thread.sleep(123);
            Thread.interrupted();
        } catch (InterruptedException e) {

        }
    }

    class Obj {

    }
}
