package com._22evil.effective_example.e004;

/**
 * 禁止工具类实例化
 */
public class NonUtil {

    private NonUtil() {
        throw new Error("forbidden");
    }

    public static long getSum(int ... nums) {
        return 0L;
    }
}