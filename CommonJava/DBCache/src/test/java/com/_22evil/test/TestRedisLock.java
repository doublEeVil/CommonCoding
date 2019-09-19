package com._22evil.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

public class TestRedisLock {

    private static final String MSG = "OK";
    private static long LOCK_MAX_TIME = 20000; //超过20秒自动解锁
    static Jedis jedis;
    static {
        jedis = new Jedis("192.168.0.192", 6379);
        //jedis.auth("123");
    }
    @Test
    public void test() throws InterruptedException {
        // Thread1 read and write 10s
        new Thread(()->{
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long t1 = System.currentTimeMillis();
            while (System.currentTimeMillis() - t1 < 10000) {
                jedis.set("123","123");
                System.out.println("其他线程  " + jedis.get("123"));
                jedis.del("123");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 线程2, 加一个锁，并解锁
        new Thread(() -> {
            String key = "ky_opp";
            String value = UUID.randomUUID().toString();
            try {
                System.out.println("111 尝试加锁");
                boolean lock = lock(key, value, 5000);
                if (lock) {
                    System.out.println("1111 加锁成功。。。");
                    Thread.sleep(5000);
                    lock = unlock(key, value);
                    System.out.println("1111 释放锁" + lock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 线程3, 加一个锁，并解锁
        new Thread(() -> {
            String key = "ky_opp";
            String value = UUID.randomUUID().toString();
            try {
                Thread.sleep(1000);
                System.out.println("222 尝试加锁");
                boolean lock = lock(key, value, 5000);
                if (lock) {
                    System.out.println("222 加锁成功。。。");
                    Thread.sleep(5000);
                    lock = unlock(key, value);
                    System.out.println("222 释放锁" + lock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(56000);
    }

    private boolean lock(String key, String value, long tryTime) throws InterruptedException {
        String result;
        long t1 = System.currentTimeMillis();
        for (;;) {
            result = jedis.set(key, value, "NX", "PX", LOCK_MAX_TIME);
            if (MSG.equals(result)) {
                return true;
            }
            if (System.currentTimeMillis() - t1 > tryTime) {
                return false;
            }
            //Thread.sleep(10L);
        }
    }

    private boolean unlock(String key, String value) {
        String  script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        if (MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }
}
