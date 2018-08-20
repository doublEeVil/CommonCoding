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
        int p = (int)(25);
        byte q = (byte)(25);
        int result = 0;
        if (p == q) {
            result = 1;
        }else{
            result = 2;
        }
        System.out.println("result is "+result);
        return;
    }

    public static void main(String[] args) {
        System.out.println("====");
    }
}
