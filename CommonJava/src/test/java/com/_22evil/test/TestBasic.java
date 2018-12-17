package com._22evil.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import com._22evil.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)

public class TestBasic {

    static {
        System.err.println("======");
    }

    @Test
    public void test() throws InterruptedException {
        List<String> dst = new LinkedList<>();
        Pattern ptn = Pattern.compile("19\\t\\w+\\t2031\\t\\w+\\t\\w+\\t0\\t\\w+\\t4");

        List<File> fileList = FileUtil.getFileList("C:\\Users\\Administrator\\Desktop\\ttt", true);
        long t1 = System.currentTimeMillis();
        for (File file : fileList) {
            List<String> contentList = FileUtil.readFileToStrings(file.getPath());
            for (String content : contentList) {
                if (ptn.matcher(content).find()) {
                    dst.add(content);
                }
            }
        }
        System.out.println(dst.size());
        for (String s : dst) {
            System.out.println(s);
        }
        System.out.println("time : " + (System.currentTimeMillis() - t1));
    }


    @Test
    public void test2() {

    }

}



