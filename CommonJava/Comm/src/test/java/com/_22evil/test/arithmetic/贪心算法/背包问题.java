package com._22evil.test.arithmetic.贪心算法;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 问题描述：
 * 存在一批货物 o1, o2, ... on
 * 重量是 w1, w2, ... wn
 * 价值是 p1, p2, ... pn
 * 海盗船最大承重是G，
 * 最如何才能海盗船价值最大化
 *
 * 分析：
 * 每件货物计算平均价值 p / w, 并排序，依次取出最大，直到最大承重
 * 可以达到近似最优解
 */
public class 背包问题 {

    class Goods implements Comparable<Goods>{
        private int index;
        private int weight;
        private int price;

        public Goods(int index, int weight, int price) {
            this.index = index;
            this.weight = weight;
            this.price = price;
        }

        @Override
        public int compareTo(Goods o) {
            return price * o.weight - o.price * weight;
        }

        public String toString() {
            return "index: " + index + " weight: " + weight + " price: " + price;
        }
    }

    @Test
    public void test() {
        // 1. 随机生成货物，重量随机[1,20]，数量随机[20,30], 价值随机[1, 20]
        // 2. 随机生成承重[25, 100]
        // 3. 排序
        // 4. 得到结果
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int totalNum = random.nextInt(20, 31);
        List<Goods> goods = new ArrayList<>(totalNum);
        int w, p;
        while (totalNum-- > 0) {
            w = random.nextInt(1, 21);
            p = random.nextInt(1, 21);
            goods.add(new Goods(totalNum, w, p));
        }

        System.out.println("good as list: ");
        for (Goods g : goods) {
            System.out.println(g);
        }

        Collections.sort(goods);

        int G = random.nextInt(25, 101);
        System.out.println("G is: " + G);

        int currentG = 0;

        while (true) {
            Goods g = goods.remove(goods.size() - 1);
            if (currentG + g.weight > G) {
                break;
            }
            currentG += g.weight;
            System.out.println(g);
        }
    }
}
