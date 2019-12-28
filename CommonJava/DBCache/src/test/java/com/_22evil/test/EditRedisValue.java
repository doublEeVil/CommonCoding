package com._22evil.test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class EditRedisValue {
    public static void main(String ... args) throws Exception {
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        jedis.auth("123456test");
//        Set<String> keys = jedis.keys("com.wyd.*");
//        for (String key : keys) {
//            String val = jedis.get(key);
//            if (val == null) continue;
//            try {
//                int intVal = Integer.valueOf(val);
//                jedis.set(key, "" + (intVal+10000));
//            } catch (Exception e) {
//
//            }
//        }

        System.out.println("---开始检查----");
        Set<HostAndPort> hosts = new HashSet<>();
        //
        HostAndPort h1 = new HostAndPort("192.168.3.30", 8001);
        HostAndPort h2 = new HostAndPort("192.168.3.30", 8002);
        HostAndPort h3 = new HostAndPort("192.168.3.30", 8003);
        HostAndPort h4 = new HostAndPort("192.168.3.30", 8004);
        HostAndPort h5 = new HostAndPort("192.168.3.30", 9001);
        HostAndPort h6 = new HostAndPort("192.168.3.30", 9002);
        HostAndPort h7 = new HostAndPort("192.168.3.30", 9003);
        HostAndPort h8 = new HostAndPort("192.168.3.30", 9004);
        hosts.add(h1);
        hosts.add(h2);
        hosts.add(h3);
        hosts.add(h4);
        hosts.add(h5);
        hosts.add(h6);
        hosts.add(h7);
        hosts.add(h8);

        System.out.println("---开始连接各个node-----");
        JedisCluster cluster = new JedisCluster(hosts);

        String pattern = "PLAYER:CROSSDATA:*";
        Set<String> result = new HashSet<>();
        try {
            Map<String, JedisPool> clusterNodes = cluster.getClusterNodes();
            for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
                Jedis jedis = entry.getValue().getResource();
                // 判断非从节点(因为若主从复制，从节点会跟随主节点的变化而变化)
                if (!jedis.info("replication").contains("role:slave")) {
                    // 搜索单个节点内匹配的Key
                    Set<String> keys = jedis.keys(pattern);
                    // 合并搜索结果
                    result.addAll(keys);
                }
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileWriter writer1 = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\11-2\\11\\a.txt"));
        FileWriter writer2 = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\11-2\\11\\b.txt"));
        String childId;
        String playerId;
        Map<String, String> map = new HashMap<>();
        Pattern pattern1 = Pattern.compile("PLAYER:CROSSDATA:");
        for (String s : result) {
            System.out.println(s);
            childId = cluster.hget(s, "childId");
            playerId = pattern1.matcher(s).replaceAll("");
            if ("0".equals(childId)) continue;
            writer1.append("playerId:" + playerId + " childId:" + childId + "\n");
            if (map.containsKey(childId)) {
                writer2.append("pid:" + playerId + " now:" + childId + " otherpid:" + map.get(childId) + "\n");
            } else {
                map.put(childId, playerId);
            }
        }
        writer1.flush();
        writer1.close();
        writer2.flush();
        writer2.close();
        System.out.println("====检查结束---");
    }
}
