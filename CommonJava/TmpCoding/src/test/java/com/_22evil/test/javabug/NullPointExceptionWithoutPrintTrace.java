package com._22evil.test.javabug;

public class NullPointExceptionWithoutPrintTrace {

    public static void main(String ... args) {
        /**
         * java内部应该有优化，但某一异常过多时，便不再打印堆栈
         * -XX:-OmitStackTraceInFastThrow
         */
        int count = 100000;
        String s = null;
        for (int i = 0; i < count; i++) {
            try {
                int val = 0;
                val = s.length();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.err.println("----------------------");
        try {
            int val = 0;
            val = s.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 可以很明显的看到，后面的堆栈不再打印. 初步认为应该是这个bug(或者说future)修复了
         * 给几个值记录下
         *      1W  ：打印
         *      1K  : 打印
         *      100 : 打印
         */
    }
}
