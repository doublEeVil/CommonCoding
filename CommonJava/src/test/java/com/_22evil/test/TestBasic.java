package com._22evil.test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasic {
    @Test
    public void test1() {
        System.out.println("====");

        URL url = this.getClass().getClassLoader().getResource("");
        System.out.println("---" + url);
        File file = new File(url.getFile());
        System.out.println(file.getName());
    }

    public static void main(String[] args) {
        System.out.println("====");
    }
}

class TT1 {
    private String name;
    private int age;

    public TT1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
