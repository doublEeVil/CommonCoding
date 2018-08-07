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
        fibonacci(5);
    }

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
}