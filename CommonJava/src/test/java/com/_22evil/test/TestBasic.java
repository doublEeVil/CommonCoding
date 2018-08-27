package com._22evil.test;

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
        long t1 = System.currentTimeMillis();
        String a = "1ad(&^&ha哈fs生df";
        String b = "k22e（*&y";
        byte[] as = a.getBytes();
        byte[] bs = b.getBytes();

        byte[] jiami = new byte[as.length];
        int i = 0;

        for (byte t : as) {
            jiami[i] = (byte) (t ^ bs[i % bs.length]);
            i++;
        }
        System.out.println("jia mi hou: " + new String(jiami));
        print(jiami);
        System.out.println("-----");

        i = 0;
        for (byte t : jiami) {
            jiami[i] = (byte) (t ^ bs[i % bs.length]);
            i++;
        }
        System.out.println("jie mi hou: " + new String(jiami));
        System.out.println(" time is : " + (System.currentTimeMillis() - t1));
    }

    public static void main(String[] args) {
        System.out.println("====");
    }

    private void print(byte[] dd) {
        for (byte d : dd) {
            System.out.print(d + " ");
        }
        System.out.println();
    }
}
