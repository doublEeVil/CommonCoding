package com._22evil.test.disruptor;

import com.alipay.disruptor.BlockingWaitStrategy;
import com.alipay.disruptor.EventFactory;
import com.alipay.disruptor.EventHandler;
import com.alipay.disruptor.RingBuffer;
import com.alipay.disruptor.dsl.Disruptor;
import com.alipay.disruptor.dsl.ProducerType;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

/**
 * Disruptor是英国外汇交易公司LMAX开发的一个高性能队列
 * 比java内置队列处理速度搞一个数量级
 * 该队列指内存队列，不是kafka那种分布式队列
 */
public class TestDisruptor {
    
    /**
     * 测试缓存行
     * Cache是由很多个cache line组成的
     * 每个cache line通常是64字节
     * 并且它有效地引用主内存中的一块儿地址。一个Java的long类型变量是8字节，因此在一个缓存行中可以存8个long类型的变量
     * CPU每次从主存中拉取数据时，会把相邻的数据也存入同一个cache line
     * 结果如下 12 ms 63ms 8ms 54ms
     * 可以看到差距很大很大
     */
    static long[][] arr1; // static或者非static都可以
    @Test
    public void testCacheLine() {
        arr1 = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr1[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr1[i][j] = 12;
            }
        }

        // 使用cacheline
        long sum = 0L;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 8; j++) {
                sum += arr1[i][j];
            }
        }
        System.out.println("cacheline cost time: " + (System.currentTimeMillis() - t1) + " ms");

        // 不使用cacheline
        sum = 0L;
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum += arr1[j][i];
            }
        }
        System.out.println("no cacheline cost time: " + (System.currentTimeMillis() - t1) + " ms");

        // 使用cacheline
        sum = 0L;
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 8; j++) {
                sum += arr1[i][j];
            }
        }
        System.out.println("cacheline cost time: " + (System.currentTimeMillis() - t1) + " ms");

        // 不使用cacheline
        sum = 0L;
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum += arr1[j][i];
            }
        }
        System.out.println("no cacheline cost time: " + (System.currentTimeMillis() - t1) + " ms");
    }

    @Test
    public void testDisruptor() throws Exception{
        class Element {
            private int value;

            public int get() {
                return value;
            }

            public void set(int value) {
                this.value = value;
            }
        }

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, " simple_thread");
            }
        };

        EventFactory<Element> factory = new EventFactory<Element>() {
            @Override
            public Element newInstance() {
                return new Element();
            }
        };

        EventHandler<Element> handler = new EventHandler<Element>() {
            @Override
            public void onEvent(Element event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("element: " + event.get());
            }
        };

        BlockingWaitStrategy strategy = new BlockingWaitStrategy();
        int bufferSize = 8;

        Disruptor<Element> disruptor = new Disruptor<Element>(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);
        disruptor.handleEventsWith(handler);

        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; true; i++) {
            long t1 = ringBuffer.next();
            try {
                Element element = ringBuffer.get(t1);
                element.set(i);
            } finally {
                ringBuffer.publish(t1);
            }
            //Thread.sleep(1);
        }

    }
}