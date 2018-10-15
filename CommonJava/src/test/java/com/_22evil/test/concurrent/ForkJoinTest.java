package com._22evil.test.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;


/**
 * ForkJoin框架
 * 典型的分治算法
 * 需要继承 RecursiveTask
 */
public class ForkJoinTest {

    @Test
    public void test() {
        class MinTask extends RecursiveTask<Integer> {

            private int startFlag;
            private int endFlag;

            public MinTask(int startFlag, int endFlag) {
                this.startFlag = startFlag;
                this.endFlag = endFlag;
            }

            @Override
            protected Integer compute() {
                int ret = 0;
                if (endFlag - startFlag < 5) {
                    for (int i = startFlag; i <= endFlag; i++) {
                        ret += i;
                    }
                } else {
                    int mid = startFlag + (endFlag - startFlag) / 2;
                    MinTask minTask1 = new MinTask(startFlag, mid);
                    MinTask minTask2 = new MinTask(mid + 1, endFlag);
                    minTask1.fork();
                    minTask2.fork();
                    ret = minTask1.join() + minTask2.join();
                }
                return ret;
            }
        }

        MinTask minTask = new MinTask(1, 100);
        ForkJoinPool pool = new ForkJoinPool();
        Future future = pool.submit(minTask);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
