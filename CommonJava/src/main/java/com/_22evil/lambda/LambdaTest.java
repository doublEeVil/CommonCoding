package com._22evil.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * lambda语法测试
 * 
 *  Function<T, R> - take a T as input, return an R as ouput
 *  Predicate<T> - take a T as input, return a boolean as output
 *  Consumer<T> - take a T as input, perform some action and don't return anything
 *  Supplier<T> - with nothing as input, return a T
 *  BinaryOperator<T> - take two T's as input, return one T as output, useful for "reduce" operations
 *  IntConsumer - take an int as input, perform some action and don't return anything
 */
public class LambdaTest {
    public static void main(String[] args) {
        // 1. 循环
        List<String> list1 = Arrays.asList("aaa", "bbb", "ccc");
        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "111");
        map1.put("2", "222");
        // 1.1 循环写法1
        list1.forEach(ele -> {
            System.out.println(ele);
        });
        map1.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
        // 1.2 循环写法2
        list1.forEach(System.out::println);
        map1.forEach(LambdaTest::myprint); 

        // 2. 构造
        // 2.1 构造1
        ThreadLocal<List<String>> strings = ThreadLocal.withInitial(ArrayList::new);
        
    }

    private static void  myprint(Object obj1, Object obj2) {
        System.out.println(obj1 + " : " + obj2);
    }
}