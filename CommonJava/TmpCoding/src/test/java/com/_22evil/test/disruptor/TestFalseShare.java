package com._22evil.test.disruptor;

import org.junit.Test;

public class TestFalseShare implements Runnable {


    static long ITERATIONS = 500 * 1000 * 100L;
    int arrayIndex = 0;
    static ValueNoPadding[] longs1;
    static ValuePadding[] longs2;


    public TestFalseShare(int val) {
        arrayIndex = val;
    }


    public static void main(String[] args) throws Exception {
        testFalseShare1();
        System.out.println("========");
        testFalseShare2();
    }

    /**
     * 测试伪共享
     * 也就是说不能充分使用缓存行特性的现象
     */
    public static void testFalseShare1() throws Exception{
        for (int i = 0; i < 10; i++) {
            System.gc();
            final long t1 = System.currentTimeMillis();
            test1(i);
            System.out.println("thread num " + i + " duration: " + (System.currentTimeMillis() - t1));
        }
    }

    public static void testFalseShare2() throws Exception{
        for (int i = 0; i < 10; i++) {
            System.gc();
            final long t1 = System.currentTimeMillis();
            test1(i);
            System.out.println("thread num " + i + "  duration: " + (System.currentTimeMillis() - t1));
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs1[arrayIndex].value = 0L;
        }
    }

    private static void test1(int threadNum) throws Exception{
        Thread[] threads = new Thread[threadNum];
        longs1 = new ValueNoPadding[threadNum];
        for (int i = 0; i < threadNum; i++) {
            longs1[i] = new ValueNoPadding();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new TestFalseShare(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static void test2(int threadNum) throws Exception{
        Thread[] threads = new Thread[threadNum];
        longs2 = new ValuePadding[threadNum];
        for (int i = 0; i < threadNum; i++) {
            longs2[i] = new ValuePadding();
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new TestFalseShare(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14;
        protected long p15;
    }

    public final static class ValueNoPadding {
        // protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        // protected long p9, p10, p11, p12, p13, p14, p15;
    }
}
