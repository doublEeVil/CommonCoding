package com._22evil.test.concurrent;

import org.junit.Test;

/**
 * 测试下Synchronized 用法，原理
 * 1. 每个对象(class类也有一个类对象)有一个监视器锁，monitor被占用就处于锁定状态
 * 2. 如果monitor的进入数为0，线程进入，该线程是monitor所有者
 * 3. 如果线程占有monitor，再次进入，monitor+1
 * 4. 如果其他线程想已经占用monitor，线程进入阻塞状态，一直到monitor = 0，重新尝试获取monitor所有权
 * 5. 对于相同对象，不同同步方法来说（test2）, 对象的monitor被占用，所以两个线程是顺序执行
 *    对于不同对象，不同静态方法， 类对象的monitor被占用，也是顺序执行
 *    对于代码快来说，在同步前monitor enter进入，才会占用锁
 */
public class SynchronizedTest {

    /**
     * 无同步， 结果是：
     *  Method 2 start
        Method 2 execute
        Method 1 start
        Method 1 execute
        Method 2 end
        Method 1 end
     */
    @Test
    public void test1() {
        final SynchronizedTest1 test = new SynchronizedTest1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method2();
            }
        }).start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

    }

    /**
     * 普通方法同步:
     * 运行结果：
     *   Method 2 start
         Method 2 execute
         Method 2 end
         Method 1 start
         Method 1 execute
         Method 1 end
     */
    @Test
    public void test2() throws InterruptedException {
        final SynchronizedTest2 test = new SynchronizedTest2();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.method1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.method2();
            }
        });

        t1.start();

        //t1.join();
        t2.start();
        //t2.join();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

    }

    /**
     * 类同步：
     * 运行结果
     *   Method 1 start
         Method 1 execute
         Method 1 end
         Method 2 start
         Method 2 execute
         Method 2 end
     */
    @Test
    public void test3() {
        final SynchronizedTest3 test1 = new SynchronizedTest3();
        final SynchronizedTest3 test2 = new SynchronizedTest3();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test2.method2();
            }
        }).start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

    }

    /**
     * 代码块同步
     * 运行结果：
     *   Method 1 start
         Method 1 execute
         Method 2 start
         Method 1 end
         Method 2 execute
         Method 2 end
     */
    @Test
    public void test4() {
        final SynchronizedTest4 test = new SynchronizedTest4();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method2();
            }
        }).start();

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

    }
}

class SynchronizedTest1 {

    public void method1(){
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public void method2(){
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }
}

class SynchronizedTest2 {

    public synchronized void method1(){
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public synchronized void method2(){
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }
}

class SynchronizedTest3 {

    public static synchronized void method1(){
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public static synchronized void method2(){
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }
}

class SynchronizedTest4 {

    public void method1(){
        System.out.println("Method 1 start");
        try {
            synchronized (this) {
                System.out.println("Method 1 execute");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public void method2(){
        System.out.println("Method 2 start");
        try {
            synchronized (this) {
                System.out.println("Method 2 execute");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }
}