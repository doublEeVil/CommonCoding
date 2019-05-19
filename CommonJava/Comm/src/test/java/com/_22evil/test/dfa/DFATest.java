package com._22evil.test.dfa;

import com._22evil.DFA.WordFilter;

import org.junit.Test;

public class DFATest {

    @Test
    public void test() {
        System.out.println("====DFA测试=====");
        try {
            //Thread.sleep(20000);
        } catch (Exception e) {

        }
        String path = "G:\\apps\\file2.txt";
        WordFilter.loadLimitedWords(path);
        WordFilter.addNoSenseWord('+');
        String input = "总所州市，毛泽+东是湖南SB人";
        long t1 = System.nanoTime();
        boolean contain = WordFilter.containLimitWord(input);
        long t2 = System.nanoTime();
        System.out.println("-------" + contain);
        System.out.println("用时：" + (t2 - t1) + " ns");
        String output = WordFilter.getFiltString(input);
        long t3 = System.nanoTime();
        System.out.println(output + " 用时： " + (t3 - t2) + " ns");
        
    }
}