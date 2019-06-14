package com._22evil.test.game;

import com._22evil.util.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestGame {

    @Test
    public void testDAU() throws Exception{
        Pattern ptn_create = Pattern.compile("\\s*1\\t([0-9]{13})\\t47\\t1060.*");//创号
        Pattern ptn_login = Pattern.compile("(.*)2\\t([0-9]*)\\t47\\t1060.*");  //登录
        Matcher matcher;
        List<File> fileList = FileUtil.getFileList("C:\\Users\\Administrator\\Desktop\\H5\\datalog", true);
        Set<String>  set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        int count = 0;
        for (File file :fileList) {
            System.out.println("---" + file.getName());
            set1.clear();
            set2.clear();
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (ptn_create.matcher(line).matches()) {
                    set1.add(line);
                }
                if (ptn_login.matcher(line).matches()) {
                    String[] lines = line.split("\t");
                    set2.add(Integer.parseInt(lines[5]));
                    count++;
                }
            }
            System.out.println("----创号：" + set1.size());
            System.out.println("----登录：" + set2.size());
        }
        System.out.println("+++" + count);
    }


}
