package com._22evil.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasic {

    private String s;

    @Test
    public void test1() {
        s = get(s);
        System.out.println(s);

        String s1 = null;
        s1 = get(s1);
        System.out.println(s1);
    }

    public String get(String s) {
        if (s == null) {
            s = new String("222");
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println("===");
    }
}

