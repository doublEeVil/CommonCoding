package com._22evil.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 生成随机值
 */
public final class RandomUtil {
    
    /**
     * 随机字母数字组合
     * 长度为length
     */
    public static String randomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * 随机数字组合
     * 长度为length
     */
    public static String randomNumer(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
} 