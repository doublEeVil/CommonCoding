package com._22evil.test.arithmetic;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 小顶堆（min-heap）有个重要的性质——每个结点的值均不大于其左右孩子结点的值，则堆顶元素即为整个堆的最小值
 */
public class TestTopK {

    private int K = 10;

    @Test
    public void testMaxK() {
        // PriorityQueue是一种小顶堆实现
        PriorityQueue<Integer> queue = new PriorityQueue<>(K);

        List<Integer> all = new ArrayList<>(123456);
        for (int i = 0; i < 123456; i++) {
            all.add(ThreadLocalRandom.current().nextInt());
        }

        for (int i = 0; i < 123456; i++) {
           if (queue.size() < K || queue.peek() < all.get(i)) {
               queue.offer(all.get(i));
           }
           if (queue.size() > K) {
               queue.poll();
           }
        }
        while (queue.size() > 0) {
            System.out.println(queue.poll());
        }
    }

    @Test
    public void testMinK() {
        // 更改PriorityQueue为大顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>(K, (o1, o2)-> o2 - o1);

        List<Integer> all = new ArrayList<>(123456);
        for (int i = 0; i < 123456; i++) {
            all.add(ThreadLocalRandom.current().nextInt(0, 123456));
        }

        for (int i = 0; i < 123456; i++) {
            if (queue.size() < K || queue.peek() > all.get(i)) {
                queue.offer(all.get(i));
            }
            if (queue.size() > K) {
                queue.poll();
            }
        }
        while (queue.size() > 0) {
            System.out.println(queue.poll());
        }
    }

}
