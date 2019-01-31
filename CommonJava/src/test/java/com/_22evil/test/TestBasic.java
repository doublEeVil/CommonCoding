package com._22evil.test;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import com._22evil.util.FileUtil;
import net.sf.json.JSONObject;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)

public class TestBasic {

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

    List<Integer> ret = Arrays.asList(3, 12, 16, 29, 32, 01, 05);

    class CP{
        List<Integer> a = new ArrayList<>(5);
        List<Integer> b = new ArrayList<>(2);

        @Override
        public String toString() {
            return a.get(0) + " " + a.get(1) + " "
                    + a.get(2) + " " + a.get(3) + " "
                    + a.get(4) + " "
                    + b.get(0) + " " + b.get(1);
        }
    }

    @Test
    public void test2() {
        // 验证需要买票多少次才可以中奖

        int cnt = 1;
        while (true) {
            System.out.println("cnt: " + cnt++);
            CP cp = generate();
            // 比较彩票结果是否相同
            if (equal(cp)) {
                System.out.println(".....你中奖了...");
                System.out.println(cp);
                break;
            }
        }
    }

    private boolean equal(CP cp) {
        Collections.sort(cp.a);
        Collections.sort(cp.b);

        System.out.println(cp);

        for (int i = 0; i < 5; i++) {
            if (cp.a.get(i) != ret.get(i)) return false;
        }
        if (cp.b.get(0) != ret.get(5) || cp.b.get(1) != ret.get(6)) return false;
        return true;
    }

    Set<Integer> set = new HashSet<>();

    private CP generate() {
        CP cp = new CP();

        set.clear();
        for (int i = 1; i <= 35; i++) {
            set.add(i);
        }

        for (int i = 0; i < 5; i++) {
            int c = ThreadLocalRandom.current().nextInt(0, set.size());
            int index = 0;
            for (Integer val : set) {
                if (index == c) {
                    cp.a.add(val);
                    set.remove(val);
                    break;
                }
                index++;
            }
        }

        set.clear();
        for (int i = 1; i <= 12; i++) {
            set.add(i);
        }

        for (int i = 0; i < 2; i++) {
            int c = ThreadLocalRandom.current().nextInt(0, set.size());
            int index = 0;
            for (Integer val : set) {
                if (index == c) {
                    cp.b.add(val);
                    set.remove(val);
                    break;
                }
                index++;
            }
        }
        return cp;
    }

    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(0,0,0,0);
        list.set(1,2);
        System.out.println(list);
    }


    @Test
    public void test4() {
        String s = "{0: '000', 2: '333', 0: '000'}";
        JSONObject obj = JSONObject.fromObject(s);
        System.out.println(s);
        obj.put("0", "123");
        System.out.println(obj);
    }
}



