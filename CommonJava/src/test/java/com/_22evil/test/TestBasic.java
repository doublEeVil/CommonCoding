package com._22evil.test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasic {



    @Test
    public void test() {

    }

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();



    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
            //break;
        }
    }


    private Map<Integer, Set<Integer>> playerMap            = new ConcurrentHashMap<>();
    @Test
    public void test2() {
        System.out.println("----");
        Set<Integer> set = playerMap.get(1);
        if (set == null) {
            set = playerMap.putIfAbsent(1, new HashSet<>());

        }
        // System.out.println("+++" + set.size());

        Map<String, String> map = new HashMap<>();
        String s = map.put("dd", "dd");
        System.out.println(s);
        System.out.println(map.get("dd"));
        LocalDate localDate1 = LocalDate.parse("2018-10-22");
        System.out.println(localDate1);

        LocalDate localDate2 = LocalDate.now();
        System.out.println(localDate1.isEqual(localDate2));
        for (int i = 0; i < 12; i++) {
            localDate2 = localDate2.plusDays(1);
            System.out.println(localDate2);
        }

    }

    @Test
    public void test3() {
        // 找到最近匹配
        //String  s1 =
    }
}



