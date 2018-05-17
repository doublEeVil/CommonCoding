package com._22evil.unsafe;

import java.lang.reflect.Field;
import sun.misc.*;

/**
 * 以下操作在java8测试
 * java9以及之后的unsafe操作更加简单，限制也更少了
 * 目前来看，大家都不太建议用java9以及更新的版本
 */
public class UnsafeUtils {
    
    /**
     * 获取Unsafe
     */
    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            return unsafe;
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
    }

    /**
     * 返回对象的浅拷贝
     */
    public static <T> T shallowCopy(T srcObj) {
        return null;
    } 
}