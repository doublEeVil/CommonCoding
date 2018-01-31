package com._22evil.test.sync_test;

public class Sync {
    private int count1;
    private int count2;

    public int getCnt1() {
        synchronized (this) {
            return count1;
        }
    }

    public void setCnt1(int val) {
        synchronized (this) {
            count1 = val;
        }
    }

    public int getCnt2() {
        return count2;
    }

    public void setCnt2(int val) {
        count2 = val;
    }

    public static void main(String[] args) {
        Sync sync = new Sync();
        new Thread(() -> {
            try {
                while (true) {
                    sync.setCnt1(sync.getCnt1() + 1);
                    //Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    sync.setCnt2(sync.getCnt2() + 1);
                    //Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    System.out.println("---" + sync.getCnt1());
                    System.out.println("---" + sync.getCnt2());
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
} 