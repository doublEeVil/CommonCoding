package com._22evil.test.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class TestFile {

    /**
     * 假设文本包含#include filename
     * 则输出包含的file 内容，
     * 如果包含自己，抛异常报错
     * limit time 5
     */
    @Test
    public void testInclude() throws IOException{
        // 需要解决的问题
        // 1. 路径的问题
        // 2. 抛异常的问题
        testInclude("G:\\apps\\111.txt");
    }

    private void testInclude(String filePath) throws IOException{
        Pattern ptn = Pattern.compile("#include.*");
        Files.lines(Paths.get(filePath))
                .forEach(x -> {
                    if (ptn.matcher(x).matches()) {
                        // System.out.println(x);
                        String anotherFile = x.replaceAll("#include", "").trim();
                        // System.out.println(anotherFile);
                        try {
                            anotherFile = "G:\\apps\\" + anotherFile;
                            if (anotherFile.equals(filePath)) {
                                throw new RuntimeException("can not include self");
                            }
                            testInclude(anotherFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(x);
                    }
                });
    }
}
