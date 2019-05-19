package com._22evil.test.encrypt;

import org.junit.Test;

/**
 * 最简单的加密
 * 亦或运算
 * 亦或运算的基本规则
 * A ^ B ^ B = A
 */
public class SimpleXOR {

    @Test
    public void test1() {
        String src = "1ad(&^&ha哈fs生df";
        String key = "k22e（*&y";
        byte[] jiaMi = encrypt(src, key);
        System.out.println("加密前：" + src);
        System.out.println("加密后：" + new String(jiaMi));
        print(jiaMi);

        byte[] jieMi = decrypt(jiaMi, key.getBytes());
        System.out.println("解密后：" + new String(jieMi));

    }

    /**
     * 加密
     * @param src
     * @param key
     * @return
     */
    public byte[] encrypt(String src, String key) {
        return encrypt(src.getBytes(), key.getBytes());
    }

    public byte[] encrypt(byte[] src, byte[] key) {
        int i = 0;
        int len = key.length;
        byte[] ret = new byte[src.length];
        for (byte b : src) {
            ret[i] = (byte) (b ^ key[i % len]);
            i++;
        }
        return ret;
    }

    /**
     * 解密
     * @param input
     * @param key
     * @return
     */
    public byte[] decrypt(String input, String key) {
        return encrypt(input, key);
    }

    public byte[] decrypt(byte[] input, byte[] key) {
        return encrypt(input, key);
    }

    private void print(byte[] dd) {
        for (byte d : dd) {
            System.out.print(d + " ");
        }
        System.out.println();
    }
}
