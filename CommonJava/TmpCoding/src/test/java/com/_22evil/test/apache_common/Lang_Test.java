package com._22evil.test.apache_common;

import com._22evil.classloader.SimpleClassLoader;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * apache.common.lang3包
 * 主要包括   StringUtils
 *          CharSetUtils
 *          ArrayUtils
 *          ClassUtils
 *          NumberUtils
 */

public class Lang_Test {

    @Test
    public void test_ArrayUtils() {
        String[] array1 = {"blue", "red", "green"};
        String[] array2 = {"red", "green", "blue"};
        // 判断相等
        System.out.println(ArrayUtils.isEquals(array1, array2));
        // 输出{a,b}格式
        System.out.println(ArrayUtils.toString(array1));
        // 转为MAP, 对数组格式有要求，否则抛异常
        String[][] array3 = {{"red", "#FF0000"}, {"green", "#00FF00"}};
        Map<Object, Object> map = ArrayUtils.toMap(array3);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        // 增加, 实际上新建空间copy
        array1 = ArrayUtils.add(array1, "black");
        System.out.println(ArrayUtils.toString(array1));
        // 删除
        array1 = ArrayUtils.removeAll(array1, 0, 1);
        System.out.println(ArrayUtils.toString(array1));
    }

    @Test
    public void test_ClassUtils() {
        try {
            Class clazz = SimpleClassLoader.getInstance().load("F:\\CommonCoding\\CommonJava\\target\\classes\\com\\_22evil\\http_server\\handler\\TestHandler.class");
            // 得到包名
            System.out.println(ClassUtils.getPackageName("pkg: " + clazz));
            System.out.println("classname: " + clazz.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_NumberUtils() {
        // 最小最大值
        System.out.println(NumberUtils.min(new int[]{1, 8 , 9, -3}));
        // String2Number
        System.out.println(NumberUtils.toInt("7.8", 12));
    }

    @Test
    public void test_StringUtils() {
        // 4位随机字符，可能含有各国字符
        System.out.println(RandomStringUtils.random(4));
        // 4位随机字母字符
        System.out.println(RandomStringUtils.randomAlphabetic(4));
        // 4位随机字母数字组合
        System.out.println(RandomStringUtils.randomAlphanumeric(4));
        // 指定字符集组合
        System.out.println(RandomStringUtils.random(4, "123abc"));
        // html 转码
        System.out.println(StringEscapeUtils.escapeHtml4("<html>"));
        System.out.println(StringEscapeUtils.escapeJava("\\m"));
        // 左右子串
        System.out.println(StringUtils.left("abcd", 2));
        System.out.println(StringUtils.right("abcd", 2));
    }

    @Test
    public void test_CharSetUtils() {
        // B中字符在A中出现的总次数
        System.out.println(CharSetUtils.count("123a123a123", "124"));
    }

    @Test
    public void test_ObjectUtils() {
        System.out.println(ObjectUtils.toString(new Date()));
        System.out.println(ObjectUtils.toString(new Lang_Test()));
    }

    @Test
    public void test_SystemUtils() {
        System.out.println(SystemUtils.getJavaHome());
    }

}
