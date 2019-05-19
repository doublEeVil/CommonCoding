package com._22evil.test.class_file;

import com._22evil.util.FileUtil;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * .class文件解析
 * class文件又叫字节码文件，通过javac 命令可以把java源代码文件编译成字节码文件
 * 目的：了解class文件的构成
 */

public class ClassFileTest {

    @Test
    public void test() {
        System.out.println("==== test ====");
        DataInputStream input = readFileAsInputStream("C:\\Users\\Administrator\\Desktop\\com\\_22evil\\Test.class");
        try {
            int num = input.readInt();
            System.out.println(num);
            System.out.println(Integer.toHexString(num).toUpperCase());
        } catch (Exception e) {

        }
    }

    private DataInputStream readFileAsInputStream(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            return dataInputStream;
        } catch (IOException e) {

        }
        return null;
    }
}