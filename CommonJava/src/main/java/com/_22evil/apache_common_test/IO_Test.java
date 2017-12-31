package com._22evil.apache_common_test;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * apache.common.io包
 * 主要包含一些文件操作
 * 包括读写
 */

public class IO_Test {

    public static void test_FileUtils() throws IOException {
        // copy
        File file1 = new File("c:\\uninstall.txt");
        File file2 = new File("c:\\uninstall_1.txt");
        FileUtils.copyFile(file1, file2);
        // touch
        FileUtils.touch(new File("c:\\13.txt"));
        OutputStream out = FileUtils.openOutputStream(new File("c:\\13.txt"));
        out.write("hello , this is jva".getBytes());
        out.flush();
        out.close();

        // string 2 file
        FileUtils.writeStringToFile(new File("c:\\13.txt"), "hello...");
        // file 2 string
        String ss = FileUtils.readFileToString(new File("c:\\13.txt"));
        System.out.println(ss);
        // file 2 string list
        List<String> list = FileUtils.readLines(new File("c:\\13.txt"));
        // file 2 byte[]
        byte[] data = FileUtils.readFileToByteArray(new File("c:\\13.txt"));
        System.out.println("size: " + data.length);
        // 删除方法， 文件显示方法， 略
        // ...
    }

    public static void test_IOUtils() {

    }

    public static void test_FilenameUtils() {

    }

    public static void test_FileSystemUtils() throws IOException{
        System.out.println(FileSystemUtils.freeSpaceKb("c:\\") / 1024 / 1024);
    }

    public static void main(String[] args) throws IOException{
        // test_FileUtils();
        test_FileSystemUtils();
    }
}
