package com._22evil.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加解密相关
 */
public class EncryptUtil {

    /**
     * md5 32位摘要
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * base64编码
     */
    public static String base64Encode(String src) {
        byte[] byte64 = Base64.encodeBase64(src.getBytes(), true);
        return new String(byte64);
    }

    /**
     * base64解码
     */
    public static String base64Decode(String src) {
        byte[] base64 = Base64.decodeBase64(src.getBytes());
        return new String(base64);
    }

    /**
     * sha1 摘要
     */
    public static String sha1(String src) {
        return DigestUtils.sha1Hex(src);
    }

    /**
     * sha256摘要
     */
    public static String sha256(String src) {
        return DigestUtils.sha256Hex(src);
    }

    /**
     * sha512摘要
     */
    public static String sha512(String src) {
        return DigestUtils.sha512Hex(src);
    }
}