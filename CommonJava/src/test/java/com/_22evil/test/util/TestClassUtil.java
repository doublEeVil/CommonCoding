package com._22evil.test.util;
import com._22evil.util.ClassUtil;
import com._22evil.util.ClassUtil2;
import org.junit.runner.RunWith;

import java.util.Set;
public class TestClassUtil {

    public static void main(String ... args) {
        Set<String> set = ClassUtil2.getAllClassName("com._22evil.test");
        for (String s : set) {
            //System.out.println(s);
        }
        ///
        String className = "com._22evil.test.TestBasic";
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            RunWith runWith = clazz.getAnnotation(RunWith.class);
            if (runWith != null) {
                System.out.println(runWith.value());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
