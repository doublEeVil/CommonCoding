package com._22evil.util;

import java.lang.reflect.Field;

public class PrintUtil {
    /**
     * 打印输出变量
     *
     * @param obj
     */
    public static void printVar(Object obj) {
        if (null == obj) {
            System.out.println("null");
            return;
        }
        Class<?> clazz = obj.getClass();
        Field[] fieldArr = clazz.getDeclaredFields();
        System.out.println("---------------------------------------------");
        try {
            for (Field field : fieldArr) {
                field.setAccessible(true);
                System.out.print("-----");
                if (field.getType().getSimpleName().endsWith("[]")) {
                    // 数组类型
                    Object[] subobjs = (Object[]) field.get(obj);
                    System.out.print(field.getName() + " " + field.getType().getSimpleName());
                    for (Object subobj : subobjs) {
                        System.out.print(" " + subobj);
                    }
                    System.out.println();
                } else {
                    // 其他类型
                    System.out.println(field.getName() + " " + field.getType().getSimpleName() + ":" + field.get(obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------");
    }

    /**
     * 二进制数据转十六进制数据并打印
     * @param data
     */
    public static void printBytes2Hex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String temp = Integer.toHexString(data[i] & 0xff);
            if (temp.length() == 1) {
                temp += "0";
            }
            sb.append(",");
            sb.append(temp);
        }
        System.out.println("----------------------");
        System.out.println(sb.toString());
        System.out.println("----------------------");
    }

    /**
     * 16进制字符串转二进制
     * @param hexString
     */
    public static byte[] printHex2Bytes(String hexString) {
        // 中间空格
        hexString = hexString.replaceAll(" ", "");
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        System.out.println("----------------------");
        for (byte b : d) {
            System.out.print(b + ",");
        }
        System.out.println();
        System.out.println("----------------------");
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
