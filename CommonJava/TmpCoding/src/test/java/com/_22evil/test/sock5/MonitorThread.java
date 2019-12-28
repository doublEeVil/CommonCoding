package com._22evil.test.sock5;

import java.util.Set;

public class MonitorThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15000); //15秒检测一次
                System.out.println("活动线程总数：" + Thread.activeCount());
                System.out.println("max ram:" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " M");
                System.out.println("free ram:" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " M");
            } catch (Exception e) {

            }
        }
    }
}
