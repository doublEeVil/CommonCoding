package com._22evil.test.arithmetic;

import org.junit.Test;

/**
 * 算法测试
 */
public class TestArithmetic {

    /**
     * 测试递归的入栈出栈
     */
    @Test
    public void testRecursive() {
        //fibonacci(5);
        fibonacciLoop(5);
    }

    /**
     * 斐波那契数列的递归写法
     */
    private int fibonacci(int num) {
        System.out.println("---入栈 >> " + num);
        if (num <= 0) {
            throw new UnsupportedOperationException("斐波那契数列不允许输入非自然数");
        }
        
        int ret;
        if (num == 1) {
            ret = 1;
        } else if (num == 2) {
            ret = 1;
        } else {
            ret = fibonacci(num - 1) + fibonacci(num - 2);
        }
        System.out.println("<<出栈---" + num);
        return ret;
    }

    /**
     * 斐波那契数列的非递归写法
     * 任何递归转非递归，
     * 都是通过栈空间操作变成堆空间操作, 同时执行顺序是从下而上循环
     */
    private int fibonacciLoop(int num) {
        if (num <= 0) {
            throw new UnsupportedOperationException("斐波那契数列不允许输入非自然数");
        }
        if (num == 1) {
            return 1;
        }
        if (num == 2) {
            return 1;
        }

        int f1 = 1;
        int f2 = 1;
        int f3 = f1 + f2;
        int i = 2;
        while (i++ < num) {
            f3 = f2 + f1;
            f1 = f2;
            f2 = f3;
        }
        System.out.println("=== " + num + "====" + f3);
        return f3;
    }
}