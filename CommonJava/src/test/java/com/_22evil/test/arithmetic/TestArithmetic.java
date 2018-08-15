package com._22evil.test.arithmetic;

import java.util.ArrayList;
import java.util.List;

import com._22evil.arithmetic.TArrayList;
import com._22evil.arithmetic.TIterator;
import com._22evil.arithmetic.TLinkedList;
import com._22evil.arithmetic.TList;
import com._22evil.arithmetic.TQueue;
import com._22evil.arithmetic.TStack;

import org.junit.Test;

/**
 * 算法测试
 */
public class TestArithmetic {

    /**
     * 测试递归的入栈出栈
     */
    @Test
    public void testRecursive() {
        //fibonacci(5);
        fibonacciLoop(5);
    }

    /**
     * 斐波那契数列的递归写法
     */
    private int fibonacci(int num) {
        System.out.println("---入栈 >> " + num);
        if (num <= 0) {
            throw new UnsupportedOperationException("斐波那契数列不允许输入非自然数");
        }
        
        int ret;
        if (num == 1) {
            ret = 1;
        } else if (num == 2) {
            ret = 1;
        } else {
            ret = fibonacci(num - 1) + fibonacci(num - 2);
        }
        System.out.println("<<出栈---" + num);
        return ret;
    }

    /**
     * 斐波那契数列的非递归写法
     * 任何递归转非递归，
     * 都是通过栈空间操作变成堆空间操作, 同时执行顺序是从下而上循环
     */
    private int fibonacciLoop(int num) {
        if (num <= 0) {
            throw new UnsupportedOperationException("斐波那契数列不允许输入非自然数");
        }
        if (num == 1) {
            return 1;
        }
        if (num == 2) {
            return 1;
        }

        int f1 = 1;
        int f2 = 1;
        int f3 = f1 + f2;
        int i = 2;
        while (i++ < num) {
            f3 = f2 + f1;
            f1 = f2;
            f2 = f3;
        }
        System.out.println("=== " + num + "====" + f3);
        return f3;
    }

    @Test
    public void testTArrayList() {
        TList<String> list = new TArrayList<>(5);
        for (int i = 0; i < 15; i++) {
            list.add("abcd " + i);
        }

        String s = "aaqwr";
        list.add(s);

        System.out.println("index: " + list.indexOf(s));

        list.remove(5);

        list.set("22", 0);

        TIterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("index: " + list.indexOf(s));
    }

    @Test
    public void testTLinkedList() {
        TList<String> list = new TLinkedList<>();
        list.add("001");
        list.add("002");
        list.add("003");
        list.add("004");

        list.set("0033", 2);

        list.remove(1);

        list.add("0012", 0);

        TIterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 测试栈
     */
    @Test
    public void testStack() {
        TStack<String> stack = new TStack<>(4);
        stack.push("111");
        stack.push("222");
        stack.push("333");
        stack.push("444");

        System.out.println(stack.pop());
        System.out.println(stack.pop()); 

        System.out.println(stack);
    }

    /**
     * 栈经典问题， 表达式计算
     */
    @Test
    public void testStack_calculator() {

    }

    /**
     * 栈经典问题， 迷宫查找出路
     * 假设存在迷宫如下，找到出去的路径，其中0代表可以过去，1代码不能过去
     * 0 0 0 0 1
     * 1 0 0 0 0
     * 0 1 0 0 1
     * 1 0 0 0 0
     * 0 0 0 1 0
     * 其中起点是[0,0] 终点是[1,4], 左上为[0,0], 右下为[4,4]
     * 显然存在多个方案可以走出迷宫
     * 
     */
    // TODO 还存在问题。。。。
    @Test
    public void testStack_map() {
        System.out.println("=====");
        TStack<Point> stack = new TStack<>(25);
        int[][] map = new int[5][5];
        map[0][4] = 1;
        map[1][0] = 1;
        map[2][1] = 1;
        map[2][4] = 1;
        map[3][0] = 1;
        map[4][3] = 1;

        Point head = new Point(0, 0);
        stack.push(head);
        doSearch(stack, map, Direction.stop);
    }

    private void doSearch(TStack<Point> stack, int[][] map, String d) {
        
        Point top = stack.peek();
        if (top.x == 1 && top.y == 4) {
            // 判断是否是终点
            System.out.println("game over");
            return;
        }
        // 如果可向上寻找
        if (top.y >= 1 && map[top.y - 1][top.x] == 0 && d != Direction.down) {
            Point p = new Point(top.x, top.y - 1);
            stack.push(p);
            doSearch(stack, map, Direction.up);
        } else if (top.x <= 3 && map[top.y][top.x + 1] == 0 && d != Direction.left) {
            // 可以右
            Point p = new Point(top.x + 1, top.y);
            stack.push(p);
            doSearch(stack, map, Direction.right);
        } else if (top.y <= 3 && map[top.y + 1][top.x] == 0 && d != Direction.up) {
            // 可以下
            Point p = new Point(top.x, top.y + 1);
            stack.push(p);
            doSearch(stack, map, Direction.down);
        } else if (top.x >= 1 && map[top.y][top.x - 1] == 0 && d != Direction.right) {
            // 可以左
            Point p = new Point(top.x - 1, top.y);
            stack.push(p);
            doSearch(stack, map, Direction.left);
        } else {
            // 都不行,退格
            stack.pop();
        }
        System.out.println(top);
    }

    /**
     * 测试队列
     */
    @Test
    public void testQueue() {
        TQueue<String> queue = new TQueue<>(5);
        queue.offer("111");
        queue.offer("222");
        queue.offer("333");
        queue.offer("444");
        queue.offer("555");

        System.out.println(queue.poll());

        queue.offer("666");
        System.out.println("---");
        System.out.println(queue);
    }

    /**
     * 经典问题， 约瑟夫环
     * 41个人排成一个圆圈，由第1个人开始报数，每报数到第3人该人就必须自杀，然后再由下一个重新报数，直到所有人都自杀身亡为止。
     * 最后第16和31可以留下来
     */
    @Test
    public void testQueue_Josephus() {
        // 先分析一下，等同于组成了一个环,这里先思考第一种解法
        // 等同于组成了一个长度为41的循环列表，或者说环
        // 根据规则一个个去掉
        // 可以肯定，最后一定是只会存在2个
        // 而我们需要做的，就是求出到底是哪两个

        // 先组建一个环
        int size = 1;
        Node head = new Node(1);
        Node index = head;
        for (int i = 2; i <= 41; i++) {
            Node t = new Node(i);
            index.next = t;
            index = index.next;
            size++;
        }
        index.next = head;
        // 至此，环组建完成，接下来是做一个计算过程

        int count = 1;
        index = head;

        while (size >= 3) {
            if (count == 2) {
                // 如果报数到2，相当于直接去掉它的下一个
                index.next = index.next.next;
                size--;
                count = 0;
            }
            index = index.next;
            count++;
        }

        System.out.println("game over , result is ");
        Node t = index.next;
        System.out.println(" ==== " + index.val);
        while (t != index) {
            System.out.println(" ==== " + t.val);
            t = t.next;
        }

        // 上面用纯穷举的方法，但是但数量很大时，就有些不合适了，原因很简单，服务器堆栈空间都是有限制的
        // 下面可以尝试用数学方法，理由很简单，能用数学方法的，必然比穷举快很多很多
        // 举个最简单的例子，假设是1,2,3,4,5中，
        // 第一轮， 是3的倍数消失
        // 第二轮是 (m + 余数) % 2 == 0消失
        // 数学归纳还是有点难，我先略过了

    }
    static class Node {
        int val;
        Node next;

        public Node(int v) {
            val = v;
        }
    }

    static class Point {
        int x;
        int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }

    static class Direction {
        static String up = "up";
        static String down = "down";
        static String left = "left";
        static String right = "right";
        static String stop = "stop";
    }
}