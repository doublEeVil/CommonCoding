package com._22evil.test.number;

import org.junit.Test;

import java.lang.reflect.Method;

public class TestFloat {

    @Test
    public void test() {
        float val = 1.234f;
        System.out.println(Float.toHexString(val));
    }

    @Test
    public void test1() throws Exception{
        Class<?> clazz = TestFloat.class;
        TestFloat t = new TestFloat();
        Method method = clazz.getMethod("tt");
        method.setAccessible(true);
        int times = 10000;
        while (times-- > 0) {
            method.invoke(t);
        }
        t.tt();

        times = Integer.MAX_VALUE;
        long t1 = System.currentTimeMillis();
        while (times-- > 0) {
            method.invoke(t);
        }
        System.out.println("time: " + (System.currentTimeMillis() - t1));


        times = Integer.MAX_VALUE;
        t1 = System.currentTimeMillis();
        while (times-- > 0) {
            t.tt();
        }
        System.out.println("time: " + (System.currentTimeMillis() - t1));
    }

    public void tt() {
        int i = 0;
        i++;
    }
}
