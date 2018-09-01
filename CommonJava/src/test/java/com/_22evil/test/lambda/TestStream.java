package com._22evil.test.lambda;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;

/**
 * 流
 */
public class TestStream {

    /**
     * 流的创建
     */
    @Test
    public void testCreate() {
        //1. Stream.of
        String names = Stream.of("zhu", "jin", "shan")
                .collect(Collectors.joining(","));
        System.out.println(names);

        //2. Arrays.stream()
        String[] members = {"zhu", "jin", "shan"};
        names = Arrays.stream(members).collect(Collectors.joining(","));
        System.out.println(names);

        //3. Stream.iterate
        List<BigDecimal> nums = Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println(nums);

        //4. Stream generate
        Stream.generate(Math::random).limit(5).forEach(System.out::println);

        //5. 集合类的stream
        Arrays.asList("zhu", "jin", "shan").stream().forEach(System.out::println);

        //6. 特殊的基本数据类型stream
        List<Integer> ints = IntStream.range(10, 16).boxed().collect(Collectors.toList());
        List<Long> longs = LongStream.range(10, 16).boxed().collect(Collectors.toList());
    }

    /**
     * 流装箱
     */
    public void testBoxed() {
        // 对于引用数据，可以直接如下
        List<String> strings = Stream.of("zhu", "jshan").collect(Collectors.toList());

        // 对于基本数据，以下会报错
        // List<Integer> ints = IntStream.of(1, 2, 3).collect(Collectors.toList());
        // 无法将int 自动转为 Integer
        // 解决方法
        // 1. boxed()
        List<Integer> ints = IntStream.of(1, 2, 3).boxed().collect(Collectors.toList());

        // 2. mapToObj
        ints = IntStream.of(1, 2, 3).mapToObj(Integer::valueOf).collect(Collectors.toList());

        // 3. 使用collect三参数方法
        ints = IntStream.of(1, 2, 3).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // 流转数组
        int[] arr = IntStream.of(1, 2, 3).toArray();
    }

    /**
     *
     */
    @Test
    public void testReduce() {
        // 映射-筛选-规约 map-filter-reduce
        // 实现规约，也就是通过流生成单一值
        String[] strings = "this is my name".split(" ");
        long cnt = Arrays.stream(strings).map(String::length).count();
        System.out.println("char cnt " + cnt);

        int num = Arrays.stream(strings).mapToInt(String::length).sum();
        System.out.println("char len " + num);

        OptionalDouble ave = Arrays.stream(strings).mapToInt(String::length).average();
        System.out.println(ave.getAsDouble());

        OptionalInt max = Arrays.stream(strings).mapToInt(String::length).max();
        System.out.println(max.getAsInt());

        OptionalInt min = Arrays.stream(strings).mapToInt(String::length).min();
        System.out.println(min.getAsInt());

        // reduce操作
        int sum = IntStream.rangeClosed(1, 10).reduce((x, y) -> x + y).orElse(0);
        System.out.println(sum);

        // 打印流
        IntStream.rangeClosed(1, 10).reduce((x, y) -> {
            System.out.println("x = " + x + ", y = " + y);
           return x + y;
        });

        // 得到流的最小值
        int min1 = Stream.of(1,5,2,8,-9,12,4).reduce(Integer::min).get();
        System.out.println(min1);

        // 流的一般操作, Book转Map
        class Book {
            public int id;
            public String name;
            public Book(int id, String name) {
                this.id = id;
                this.name = name;
            }
        }
        Map<Integer, Book> bookMap = Stream.of(new Book(1, "java"), new Book(2, "c++"))
                .reduce(new HashMap<Integer,Book>(), (map, book) -> {
                    map.put(book.id, book);
                    return map;
                }, (m1, m2) -> {
                    m1.putAll(m2);
                    return m1;
                });
    }

    /**
     * peek操作
     * 不属于终止条件的消耗
     */
    @Test
    public void testPeek() {
        Stream.of("zhu", "jins", "hsna").peek(System.out::println)
                .filter(x -> x.startsWith("z") || x.startsWith("j"))
                .peek(x-> System.out.println("---" + x))
                .count(); // 终止条件必须要有，否则无任何输出
    }

    /**
     * 统计
     */
    @Test
    public void testStatistic() {
        IntSummaryStatistics ins = Stream.of("zhu", "jins", "hsna").mapToInt(String::length).summaryStatistics();
        System.out.println(ins.getCount());
    }

    /**
     * 流的拼接
     */
    @Test
    public void testStreamConcat() {
        Stream stream1 = Stream.of("zhu", "jins", "hsna");
        Stream stream2 = Stream.of("zhu", "jins", "hsna");
        Stream stream3 = Stream.of("zhu", "jins", "hsna");

        // 一种方法
        Stream total = Stream.of(stream1, stream2, stream3);
        // 或者
        total = Stream.concat(Stream.concat(stream1, stream2), stream3);

    }
}
