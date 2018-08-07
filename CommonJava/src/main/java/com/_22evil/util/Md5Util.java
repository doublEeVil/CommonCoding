package com._22evil.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {
    /**
     * md5 32位摘要
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
}
