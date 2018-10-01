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

    private String s;

    @Test
    public void test1() throws Exception {
        System.err.println("---");
        TT t1 = new TT();
        t1.say();

        new Thread(()->{
            synchronized (t1) {
                try {
                    for (int i = 0; i < 5; i++) {
                        t1.val++;
                    }
                    System.out.println("====1111==" + t1.val);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

            }
        }).start();

        new Thread(()->{
            synchronized (t1) {
                try {
                    for (int i = 0; i < 5; i++) {
                        t1.val++;
                    }
                    System.out.println("====222==" + t1.val);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }).start();

        Thread.sleep(20000);
    }

    @Test
    public void test() {

    }
}

class TT {
    int val;

    public void say() {
        System.out.println("====say");
    }
}


