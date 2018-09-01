package com._22evil.test.concurrent;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * 并发包测试
 * 并发：多个任务可以在重叠时间运行
 * 并行：多个任务可以同时运行
 */
public class TestConcurrent {

    /**
     * 不使用并行得到运行时间
     */
    @Test
    public void test1() {
        Instant start = Instant.now();
        Arrays.asList(1,2,6,1,8,2)
                .stream()
                .mapToInt(TestConcurrent::action1)
                .sum();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
    }

    /**
     * 使用并行
     */
    @Test
    public void test2() {
        Instant start = Instant.now();
        Arrays.asList(1,2,6,1,8,2)
                .stream()
                .parallel()
                .mapToInt(TestConcurrent::action1)
                .sum();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
    }


    private static int action1(int num) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        return num * 2;
    }

    /**
     * 设置线程池大小
     */
    @Test
    public void test3() {
        System.setProperty("java.util.ForkJoinPool.common.parallelism", "20");
        test2();
    }
}
