package com._22evil.test.tem;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileOutputStream;

public class Redi {


    public static void main(String ... args) throws Exception{
        Jedis jedis = new Jedis("192.168.3.30", 6379);
        jedis.auth("123");
        int start = 1;
        int end = 2902;
        FileOutputStream fout = new FileOutputStream(new File("redis-data.dat"));
        for (int i=start;i<=end;i++) {
            String s = jedis.get("ACCOUNT:ID:123");
            fout.write(s.getBytes());
            fout.write("\n".getBytes());
            System.out.println(s);
        }
        fout.flush();
        fout.close();
    }
}
