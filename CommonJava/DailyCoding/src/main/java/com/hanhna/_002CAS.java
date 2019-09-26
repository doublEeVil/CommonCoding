package com.hanhna;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟CAS操作
 */
public class _002CAS {
    /**
     * 模拟Java中的原子变量
     */
    class AtomicInt {
        private int val;

        public AtomicInt() {
        }

        public AtomicInt(int val) {
            this.val = val;
        }

        public int get() {
            return val;
        }

        public int getAndAdd(int addNum) {
            int oldVal;
            int expectValue;
            for (;;) {
                oldVal = val;
                expectValue = oldVal + addNum;
                if (compareAndSwap(expectValue, addNum)) {
                    return oldVal;
                }
                System.out.println("==== 数据冲突 === ");
            }
        }

        public int addAndGet(int addNum) {
            int oldVal;
            int expectValue;
            for (;;) {
                oldVal = val;
                expectValue = oldVal + addNum;
                if (compareAndSwap(expectValue, addNum)) {
                    return expectValue;
                }
                System.out.println("==== 数据冲突 === ");
            }
        }

        /**
         * CAS操作是cpu硬件支持的，这里没法模拟，只能使用 synchronized
         * @param expectValue
         * @param changeValue
         * @return
         */
        private synchronized boolean compareAndSwap(int expectValue, int changeValue) {
            int newValue = val + changeValue;
            if (expectValue == newValue) {
                val = newValue;
                return true;
            }
            return false;
        }

        private int compareAndSet(int expectValue, int newValue) {
            int oldValue = val;
            if (expectValue == newValue) {
                val = newValue;
                return val;
            }
            return oldValue;
        }
    }

    public void test() {
        AtomicInteger num1 = new AtomicInteger(0);
        AtomicInt num2 = new AtomicInt(0);

        Runnable run1 = () -> {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {

            }
            num1.addAndGet(1);
        };

        Runnable run2 = () -> {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {

            }
            num2.addAndGet(1);
        };

        for (int i = 0; i < 300; i++) {
            new Thread(run1).start();
        }

        for (int i = 0; i < 300; i++) {
            new Thread(run2).start();
        }

        while (true) {
            if (Thread.activeCount() <= 2) {
                System.out.println("====" + num1.get());
                System.out.println("====" + num2.get());
                return;
            }
        }
    }

    public static void main(String ... args) {
        new _002CAS().test();
    }
}
