package com._22evil.effective_example.e001;

public class E01_StaticMethodConstuctor {

    /**
     * 1. 
     */
    public static Boolean valueOf(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public static void main(String[] args) {
        Boolean b = E01_StaticMethodConstuctor.valueOf(false);
        System.out.println(b.hashCode());
    }
}