package com._22evil.test.lambda;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 测试java.nio2.Files
 */
public class TestFiles {

    /**
     * 目标路径必须是文件且存在，不能是文件夹，
     * 打印输出文本内容
     * @throws IOException
     */
    @Test
    public void testLines() throws IOException{
        Files.lines(Paths.get("C:\\Users\\Administrator\\Downloads\\bootstrap-3.3.7\\dd.txt"))
                .filter(x->x.length() > 4)
                .sorted((o1,o2) -> o2.length() - o1.length()) //默认降序，改为升序
                .limit(3) //前三个
                .forEach(System.out::println);
    }

    /**
     * 测试每一种长度单词的数量(单文件内)
     * @throws Exception
     */
    @Test
    public void testLine2() throws Exception {
        Files.lines(Paths.get("C:\\Users\\Administrator\\Downloads\\bootstrap-3.3.7\\dd.txt"))
                .filter(x->x.length() > 4)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()))
                .forEach((k,v) -> {
                    System.out.println("k: " + k + " v:" + v);
                });
    }

    /**
     * 检索文件夹，输入路径必须是文件夹路径，不可以是文件路径
     * 得到的结果是输出该目录下文件夹或者文件名，不会遍历子文件夹
     */
    @Test
    public void checkFiles() {
        try (Stream<Path> list = Files.list(Paths.get("C:\\Users\\Administrator\\Downloads\\bootstrap-3.3.7"))) {
                list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件夹遍历
     */
    @Test
    public void testWalkTree() {
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Administrator\\Downloads\\bootstrap-3.3.7"))) {
            paths.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试查找
     */
    @Test
    public void testFind() {
        try {
            Files.find(Paths.get("G:\\"), Integer.MAX_VALUE, (path, attr) -> {
               return path.startsWith("u" ) && attr.isDirectory();
            }).forEach(System.out::println);
        } catch (Exception e) {

        }
    }
}
