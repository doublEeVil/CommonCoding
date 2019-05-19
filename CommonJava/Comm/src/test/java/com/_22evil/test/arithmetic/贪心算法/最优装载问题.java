package com._22evil.test.arithmetic.贪心算法;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 问题描述：
 * 存在货物 o1,o2,o3..on,
 * 重量分别是 w1,w2,w3...wn
 * 存在海盗船，最大承重是G
 * 求解如何能装最大的货物
 *
 * 分析：
 * 对货物按重量进行排序，按照重量大小，依次取剩余的重量最小的货物即可，直到不能再装下任何物品
 *
 * 本质是数量最大化求解
 */
public class 最优装载问题 {

    @Test
    public void test() {
        // 1. 随机生成货物，重量随机[1,20]，数量随机[20,30]
        // 2. 随机生成承重[25, 100]
        // 3. 排序
        // 4. 得到结果

        class Goods implements Comparable<Goods>{
            private int index;
            private int weight;

            public Goods(int index, int weight) {
                this.index = index;
                this.weight = weight;
            }

            @Override
            public int compareTo(Goods o) {
                return weight - o.weight;
            }

            public String toString() {
                return "index: " + index + " weight: " + weight;
            }
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        int totalNum = random.nextInt(20, 31);
        List<Goods> goods = new ArrayList<>(totalNum);
        int w;
        while (totalNum-- > 0) {
            w = random.nextInt(1, 21);
            goods.add(new Goods(totalNum, w));
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
            Goods g = goods.remove(0);
            if (currentG + g.weight > G) {
                break;
            }
            currentG += g.weight;
            System.out.println(g);
        }
    }
}
