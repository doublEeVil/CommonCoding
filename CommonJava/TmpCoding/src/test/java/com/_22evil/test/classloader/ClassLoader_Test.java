package com._22evil.test.classloader;

import com._22evil.classloader.SimpleClassLoader;
import org.junit.Test;

/**
 * 测试class loader
 */
public class ClassLoader_Test {

    @Test
    public void testSimpleClassLoader() {
        SimpleClassLoader classLoader = SimpleClassLoader.getInstance();
        String path = "C:\\Users\\Administrator\\Desktop\\Test.class";

        try {
            Class<?> clazz = classLoader.load(path);
            System.out.println("===" + clazz.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}