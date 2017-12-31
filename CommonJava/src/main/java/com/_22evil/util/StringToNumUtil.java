package com._22evil.util;

/**
 * Created by shangguyi on 24/12/2017.
 * 字符串转Number
 */
public class StringToNumUtil {
    public static Object string2Num(String str, Class clazz) {
        if (clazz == String.class) {
            return str;
        }
        if (clazz == int.class) {
            return Integer.valueOf(str);
        }
        if (clazz == short.class) {
            return Short.valueOf(str);
        }
        if (clazz == boolean.class) {
            return Boolean.valueOf(str);
        }
        if (clazz == double.class) {
            return Double.valueOf(str);
        }
        if (clazz == float.class) {
            return Float.valueOf(str);
        }
        if (clazz == byte.class) {
            return Byte.valueOf(str);
        }
        if (clazz == long.class) {
            return Long.valueOf(str);
        }
        if (clazz == char.class) {
            return str.charAt(0);
        }
        return null;
    }
}
