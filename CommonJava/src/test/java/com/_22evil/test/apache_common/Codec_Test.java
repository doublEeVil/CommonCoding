package com._22evil.test.apache_common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * apache.common.codec包
 * 包含主流的编解码
 */
public class Codec_Test {

    @Test
    public void testMD5() {
        String md5 = DigestUtils.md5Hex("helloW");
        System.out.println(md5);
    }

    @Test
    public void testBase64() {
        String src = "hello, l";
        String base64 = Base64.encodeBase64String(src.getBytes());
        System.out.println(base64);
        byte[] data = Base64.decodeBase64(base64);
        System.out.println(new String(data));
    }
}
