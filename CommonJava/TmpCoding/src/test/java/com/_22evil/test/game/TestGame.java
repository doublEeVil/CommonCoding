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
        Pattern ptn_login = Pattern.compile("(.*)2\\t([0-9]*)\\t47\\t1060.*");
        Matcher matcher;
        List<File> fileList = FileUtil.getFileList("C:\\Users\\Administrator\\Desktop\\game_data", true);
        Set<String> set = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        int count = 0;
        for (File file :fileList) {
            System.out.println("---" + file.getName());
            set.clear();
            set2.clear();
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (ptn_login.matcher(line).matches()) {
                    String[] lines = line.split("\t");
                    set.add(lines[5]);
                    set2.add(Integer.parseInt(lines[5]));
                    count++;
                }
            }
            System.out.println("----" + set.size());
            System.out.println("----" + set2.size());
        }
        System.out.println("+++" + count);
    }
}
