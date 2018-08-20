package com._22evil.test.math;

import com._22evil.math.Geometric;

import org.junit.Test;

import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestMath {

    /**
     * 三角形面积
     */
    @Test
    public void testTriangleArea() {
        double s = Geometric.getTriangleArea(3, 4, 5);
        System.out.println("===" + s);
    }

    /**
     * 验证 自然数n的个位数总与的它的五次方n^5相同
     */
    @Test
    public void testN5() {
        // 下面的代码会得不到正确结果，因为内存溢出,当74^5已经超过int最大值了
        long cnt = IntStream.rangeClosed(1, 1_0000_0000).filter(x -> getLastNum(x) != getLastNum(x * x * x * x * x)).count();
        System.out.println(cnt);

        // 尝试修正结果, 把数值缩小，但还是得不到正确的值
        cnt = IntStream.rangeClosed(1, 100).filter(x -> getLastNum(x) != getLastNum(x * x * x * x * x)).count();
        System.out.println(cnt);

        // 缩小范围，改为long，得到正确的结果
        cnt = LongStream.rangeClosed(1, 100).filter(x -> getLastNum1(x) != getLastNum1(x * x * x * x * x)).count();
        System.out.println(cnt);
        System.out.println(Long.MAX_VALUE);
    }

    /**
     * 得到个位数
     * @param num
     * @return
     */
    private int getLastNum(int num) {
        while (num > 9) {
            num %= 10;
        }
        return num;
    }

    /**
     * 得到个位数
     * @param num
     * @return
     */
    private int getLastNum1(long num) {
        while (num > 9) {
            num %= 10;
        }
        return (int)num;
    }

    /**
     * 验证
     * 当且仅当自然数n所有位数的数字之和是9的倍数，n是9的倍数
     */
    @Test
    public void testN9() {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            allSum1(i);
        }
        System.out.println("===" + (System.currentTimeMillis() - t1) + " ms");

        t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            allSum(i);
            //System.out.println(allSum(i));
        }
        System.out.println("===" + (System.currentTimeMillis() - t1) + " ms");

        t1 = System.currentTimeMillis();
        int num;
        for (int i = 0; i < 10000000; i++) {
            num = allSum2(i);
            if (num % 9 == 0 && i % 9 != 0) {
                System.err.println("====i: " + i + " num: " + num);
            }
        }
        System.out.println("===" + (System.currentTimeMillis() - t1) + " ms");
    }

    /**
     * 如何得到一个数字所有位数之和
     * 100w数据64ms
     * @param num
     * @return
     */
    private int allSum(long num) {
        int div = 10;
        while (div < num) {
            div *= 10;
        }
        int ret = 0;

        while (num > 9) {
            if (num == div) {
                ret++;
                return ret;
            } else {
                div /= 10;
                ret += num / div;
                num %= div;
            }
        }
        return ret + (int)num;
    }

    /**
     * 这也是一种方法, 显然性能不可能好
     * 1W数据8ms
     * @param num
     * @return
     */
    private int allSum1(long num) {
        int ret = 0;
        char[] chars = String.valueOf(num).toCharArray();
        for (char c : chars) {
            ret += Integer.valueOf(c + "");
        }
        return ret;
    }

    /**
     * 效率最高
     * @param num
     * @return
     */
    private int allSum2(long num) {
        int sum = 0;
        while (num >= 10) {
            sum += num % 10;
            num /= 10;
        }
        sum += num;
        return sum;
    }

    /**
     * 当且仅当n是一个素数，(n-1)!+1 是n的倍数
     */
    @Test
    public void testWelson() {
        BigInteger.
    }
}