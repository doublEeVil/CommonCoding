package com._22evil.test.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleSupplier;

/**
 * 包接口存在于java.util.function
 * 并用@FunctionalInterface注解
 * 包括四类：
 * 消费型：Consumer
 * 供给型：Supplier
 * 谓词型：Predicate
 * 功能型：Function
 */
public class TestLambda {

    /**
     * void accept(T t)
     * 输入T， 无返回，直接消费
     * 常见是用于循环打印数据
     * 其他类型
     *  IntConsumer void accept(int x)
        DoubleConsumer void accept(double x)
        LongConsumer void accept(long x)
        BiConsumer void accept(T t, U u)
     */
    @Test
    public void testConsumer() {
        List<String> strings = Arrays.asList("zhu", "jin", "shan");
        strings.forEach(x -> System.out.println(x));
        strings.forEach(System.out::println);
    }

    /**
     *  T get()
     *  供给性，无输入， 返回T
     *  常用与工厂，构造
     *  常见
     *   IntSupplier int getAsInt()
         DoubleSupplier double getAsDouble()
         LongSupplier long getAsLong()
         BooleanSupplier boolean getAsBoolean()
     */
    @Test
    public void testSupplier() {
        DoubleSupplier supplier1 = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return Math.random();
            }
        };

        DoubleSupplier supplier2 = () -> Math.random();

        DoubleSupplier supplier3 = Math::random;

        System.out.println(supplier1.getAsDouble());
        System.out.println(supplier2.getAsDouble());
        System.out.println(supplier3.getAsDouble());
    }

    /**
     * boolean test(T t)
     * 主要用于流的筛选
     */
    @Test
    public void testPredicate() {
        List<String> strings = Arrays.asList("zhu", "jin", "shan");
        strings.stream().filter(s -> s.startsWith("s"))
                .forEach(System.out::println);
    }

    /**
     *  R apply(T t)
     *  输入T， 输出R，等同于类型转换，例如map转成map的个数
     */
    @Test
    public void testFunction() {
        List<String> strings = Arrays.asList("zhu", "jin", "shan");
        strings.stream().map(s -> s.toUpperCase()) //小写转大写
                .forEach(System.out::println);
    }

    /**
     * 测试一下流的时间与普通时间的对比
     * 流初始化时间大概在31ms
     * 不清楚流的具体省时场景
     */
    @Test
    public void testCostTime() {
        AtomicLong cnt = new AtomicLong(0);

        for (int i = 10; i < 1000_0000; i *= 10) {
            cnt.set(0);
            List<String> list = new ArrayList<>(i);
            for (int j = 0; j < i; j++) {
                list.add("++" + j);
            }

            long t1 = System.currentTimeMillis();
            for (String s : list) {
                cnt.addAndGet(s.length());
            }
            System.out.println("size: " + i + " common loop cost: " + (System.currentTimeMillis() - t1) + " ms cnt:" + cnt);

            t1 = System.currentTimeMillis();
            cnt.set(0);
            list.stream().forEach(s -> cnt.addAndGet(s.length()));
            System.out.println("size: " + i + " stream loop cost: " + (System.currentTimeMillis() - t1) + " ms cnt:" + cnt);
        }
    }

    @Test
    public void 中文方法() {
        System.out.println("====");
    }
}
