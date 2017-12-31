package com._22evil.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by shangguyi on 03/12/2017.
 */
public class ByteCodeUtil {

    public static short readShort(byte[] bytes) {
        short s1 = (short)(bytes[0] & 0xff);
        short s2 = (short)((bytes[1] & 0xff) << 8);
        return (short)(s1 | s2);
    }

    public static byte[] writeShort(short num) {
        byte[] bytes = new byte[2];
        for (int j = 0; j < 2; j++) {
            bytes[j] = (byte)((num >> j * 8) & 0xff);
        }
        return bytes;
    }

    public static char readChar(byte[] bytes) {
        char c1 = (char)(bytes[0] & 0xff);
        char c2 = (char)(bytes[1] << 8 & 0xff);
        return (char)(c1 | c2);
    }

    public static byte[] writeChar(char c) {
        byte[] bytes = new byte[2];
        bytes[0] =(byte)(c & 0xff);
        bytes[1] =(byte)(c >> 8 & 0xff);
        return bytes;
    }

    public static int readInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value |= ((bytes[i] & 0xff))  << (i * 8);
        }
        return value;
    }

    public static byte[] writeInt(int i) {
        byte[] bytes = new byte[4];
        for (int j = 0; j < 4; j++) {
            bytes[j] = (byte)((i >> j * 8) & 0xff);
        }
        return bytes;
    }

    public static float readFloat(byte[] bytes) {
        int num = 0;
        for (int i = 0; i < 8; i++) {
            num |= ((bytes[i] & 0xff)) << (8 * i);
        }
        return Float.intBitsToFloat(num);
    }

    public static byte[] writeFloat(float f) {
        int fv = Float.floatToIntBits(f);
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte)((fv >> 8 * i) & 0xff);
        }
        return bytes;
    }

    public static double readDouble(byte[] bytes) {
        long num = 0;
        for (int i = 0; i < 8; i++) {
            num |= ((long)(bytes[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(num);
    }

    public static byte[] writeDouble(double d) {
        byte[] bytes = new byte[8];
        long dv = Double.doubleToLongBits(d);
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte)((dv >> 8 * i) & 0xff);
        }
        return bytes;
    }

    public static long readLong(byte[] bytes) {
        long num = 0;
        for (int i = 0; i < 8; i++) {
            num |= ((long)(bytes[i] & 0xff)) << (8 * i);
        }
        return num;
    }

    public static byte[] writeLong(long d) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte)((d >> 8 * i) & 0xff);
        }
        return bytes;
    }

    public static String readString(byte[] bytes) {
        String s = null;
        try {
            s = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static byte[] writeString(String s) {
        byte[] bytes = null;
        try {
            bytes = s.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
