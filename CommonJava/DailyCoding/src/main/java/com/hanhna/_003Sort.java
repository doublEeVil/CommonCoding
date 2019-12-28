package com.hanhna;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 排序方法
 */
public class _003Sort {
    public static void main(String ... args) {
        new _003Sort().test();
    }

    public void test() {
        int[] arr = generateArr(12);
        printArr("排序前：" , arr);
        babbleSort(arr);
        printArr("排序后：", arr);
    }

    private int[] generateArr(int num) {
        int[] arr = new int[num];
        for (int i = 0; i < num; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(0, 123456);
        }
        return arr;
    }

    private void printArr(String msg, int[] arr) {
        System.out.println(msg);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("");
    }

    /**
     * 冒泡排序
     * @param arr
     */
    private void babbleSort(int[] arr) {
        int t;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j-1]) {
                    t = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = t;
                }
            }
        }
    }
}
