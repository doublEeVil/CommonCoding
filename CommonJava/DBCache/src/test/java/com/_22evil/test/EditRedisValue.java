package com._22evil.test;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class EditRedisValue {
    public static void main(String ... args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456test");
        Set<String> keys = jedis.keys("com.wyd.*");
        for (String key : keys) {
            String val = jedis.get(key);
            if (val == null) continue;
            try {
                int intVal = Integer.valueOf(val);
                jedis.set(key, "" + (intVal+10000));
            } catch (Exception e) {

            }
        }
    }
}
