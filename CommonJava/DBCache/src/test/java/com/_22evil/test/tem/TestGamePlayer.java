package com._22evil.test.tem;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class TestGamePlayer {
    private Connection conn = null;
    @Test
    public void test() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");

            PreparedStatement ps1 = conn.prepareStatement("select ext_field from game_player");

            ResultSet resultSet = ps1.executeQuery();

            List<GamePlayer> all = new ArrayList<>();

            while (resultSet.next()) {
                String extInfo = resultSet.getString(1);
                GamePlayer gamePlayer = new GamePlayer();
                gamePlayer.setExtInfo(extInfo);
                all.add(gamePlayer);
            }


            Calendar calendar15 = Calendar.getInstance();
            calendar15.set(Calendar.DAY_OF_MONTH, 15);
            calendar15.set(Calendar.HOUR_OF_DAY, 0);
            calendar15.set(Calendar.MINUTE, 0);
            calendar15.set(Calendar.SECOND, 0);
            calendar15.set(Calendar.MILLISECOND, 0);

            Calendar calendar16 = Calendar.getInstance();
            calendar16.set(Calendar.DAY_OF_MONTH, 16);
            calendar16.set(Calendar.HOUR_OF_DAY, 0);
            calendar16.set(Calendar.MINUTE, 0);
            calendar16.set(Calendar.SECOND, 0);
            calendar16.set(Calendar.MILLISECOND, 0);

            Calendar calendar17 = Calendar.getInstance();
            calendar17.set(Calendar.DAY_OF_MONTH, 17);
            calendar17.set(Calendar.HOUR_OF_DAY, 0);
            calendar17.set(Calendar.MINUTE, 0);
            calendar17.set(Calendar.SECOND, 0);
            calendar17.set(Calendar.MILLISECOND, 0);

            Calendar calendar18 = Calendar.getInstance();
            calendar18.set(Calendar.DAY_OF_MONTH, 18);
            calendar18.set(Calendar.HOUR_OF_DAY, 0);
            calendar18.set(Calendar.MINUTE, 0);
            calendar18.set(Calendar.SECOND, 0);
            calendar18.set(Calendar.MILLISECOND, 0);

            Calendar calendar19 = Calendar.getInstance();
            calendar19.set(Calendar.DAY_OF_MONTH, 19);
            calendar19.set(Calendar.HOUR_OF_DAY, 0);
            calendar19.set(Calendar.MINUTE, 0);
            calendar19.set(Calendar.SECOND, 0);
            calendar19.set(Calendar.MILLISECOND, 0);

            Calendar calendar20 = Calendar.getInstance();
            calendar20.set(Calendar.DAY_OF_MONTH, 20);
            calendar20.set(Calendar.HOUR_OF_DAY, 0);
            calendar20.set(Calendar.MINUTE, 0);
            calendar20.set(Calendar.SECOND, 0);
            calendar20.set(Calendar.MILLISECOND, 0);

            List<GamePlayer> _15create = new ArrayList<>();
            List<GamePlayer> _16create = new ArrayList<>();
            List<GamePlayer> _17create = new ArrayList<>();
            List<GamePlayer> _18create = new ArrayList<>();
            List<GamePlayer> _19create = new ArrayList<>();

            List<GamePlayer> _16login = new ArrayList<>();
            List<GamePlayer> _17login = new ArrayList<>();
            List<GamePlayer> _18login = new ArrayList<>();
            List<GamePlayer> _19login = new ArrayList<>();
            List<GamePlayer> _20login = new ArrayList<>();

            for (GamePlayer player : all) {
                if (player.getAccountId() > 0 && player.getCreateTime() < calendar16.getTimeInMillis()) {
                    _15create.add(player);
                    if (player.getLoginTime() > calendar16.getTimeInMillis() && player.getLevel() > 1) {
                        _16login.add(player);
                    }
                }
                if (player.getCreateTime() >= calendar16.getTimeInMillis() && player.getCreateTime() < calendar17.getTimeInMillis()) {
                    _16create.add(player);
                    if (player.getLoginTime() >= calendar17.getTimeInMillis()) {
                        _17login.add(player);
                    }
                }
                if (player.getCreateTime() >= calendar17.getTimeInMillis() && player.getCreateTime() < calendar18.getTimeInMillis()) {
                    _17create.add(player);
                    if (player.getLoginTime() >= calendar18.getTimeInMillis()) {
                        _18login.add(player);
                    }
                }
                if (player.getCreateTime() >= calendar18.getTimeInMillis() && player.getCreateTime() < calendar19.getTimeInMillis()) {
                    _18create.add(player);
                    System.out.println("--" + new Date(player.getLastLogout()));
                    if (player.getLastLogout() >= calendar19.getTimeInMillis()) {
                        _19login.add(player);
                    }
                }
                if (player.getCreateTime() >= calendar19.getTimeInMillis() && player.getCreateTime() < calendar20.getTimeInMillis()) {
                    _19create.add(player);
                    if (player.getLoginTime() >= calendar20.getTimeInMillis()) {
                        _20login.add(player);
                    }
                }
            }

            System.out.println("-----" + all.size());
            System.out.println("-----" + _15create.size() + " " + _16login.size());
            System.out.println("-----" + _16create.size() + " " + _17login.size());
            System.out.println("-----" + _17create.size() + " " + _18login.size());
            System.out.println("-----" + _18create.size() + " " + _19login.size());
            System.out.println("-----" + _19create.size() + " " + _20login.size());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");

            PreparedStatement ps1 = conn.prepareStatement("select account from tab_id_account where id > 2869");

            ResultSet resultSet = ps1.executeQuery();

            Set<String> set = new HashSet<>();

            while (resultSet.next()) {
                String subs = resultSet.getString(1).substring(8);
                set.add(subs);
            }

            ps1 = conn.prepareStatement("select uid from data_see_ll where event_id=1000");
            resultSet = ps1.executeQuery();

            while (resultSet.next()) {
                if (set.contains(resultSet.getString(1))) {
                    System.out.println(resultSet.getString(1));
                }
            }

        } catch (Exception e) {

        }
    }

    @Test
    public void test3() throws Exception{
        LineNumberReader reader = new LineNumberReader(new FileReader("C:\\Users\\Administrator\\Desktop\\H5\\redis-data.dat"));
        String line;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");
        PreparedStatement ps1 = conn.prepareStatement("insert into tab_id_account(id,account) values(?,?)");
        conn.setAutoCommit(false);
        int id = 0;
        String account = "";
        String[] sts;
        while ((line = reader.readLine()) != null) {
            sts = line.split(":");
            id = Integer.valueOf(sts[0]);
            ps1.setInt(1, id);
            ps1.setString(2, sts[1]);
            ps1.addBatch();
        }
        ps1.executeBatch();
        conn.commit();
    }

    @Test
    public void test4() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");
        PreparedStatement ps1 = conn.prepareStatement("SELECT ll from data_see_ll GROUP BY ll HAVING count(ll) >= 2");
        ResultSet resultSet = ps1.executeQuery();

        Set<String> set = new HashSet<>();
        while (resultSet.next()) {
            set.add(resultSet.getString(1));
        }

        for (String ll : set) {
            ps1 = conn.prepareStatement("SELECT ll from data_see_ll GROUP BY ll HAVING count(ll) >= 2");
        }
    }


    @Test
    public void test5() throws Exception {
        LineNumberReader reader = new LineNumberReader(new FileReader("C:\\Users\\Administrator\\Desktop\\6-6\\worldlog.log"));
        String line;
        String s = "玩家装备数据丢失";
        Set<Integer> set = new HashSet<>();
        while ((line = reader.readLine()) != null) {
            if (line.indexOf(s) > -1) {
                String t = line.replaceAll(".*玩家装备数据丢失 playerid:","");
                String[] tt = t.split(" ");
                set.add(Integer.valueOf(tt[0]));
            }
        }
        System.out.println("---" + set.size());
        for (int t :set) {
            System.out.println(t);
        }
    }

    @Test
    public void test6() throws Exception{
        class T1 {
            private String name;
            private int age;
            private int[] fs;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int[] getFs() {
                return fs;
            }

            public void setFs(int[] fs) {
                this.fs = fs;
            }
        }

        class T2 extends T1 {
            private String[] ss;

            public String[] getSs() {
                return ss;
            }

            public void setSs(String[] ss) {
                this.ss = ss;
            }
        }

        T1 t1 = new T1();
        t1.setAge(12);
        t1.setName("zhjs");
        t1.setFs(new int[] {1,2,3});

        T2 t2 = new T2();
        t2.setAge(122);
        t2.setName("zhjs");
        t2.setFs(new int[] {1,2,3,4});
        t2.setSs(new String[]{"",""});
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            Object jsonObject = JSONObject.toJSON(t1);
            System.out.println(jsonObject);
            jsonObject = JSONObject.toJSON(t2);
            System.out.println(jsonObject);
        }
        System.out.println("---- json " + (System.currentTimeMillis() - ts));

        Class<?> clazz1 = t1.getClass();
        Class<?> clazz2 = t2.getClass();
        Map<String, Field[]> fmap = new HashMap<>();
        Field[] fields = clazz1.getDeclaredFields();
        fmap.put(clazz1.getName(), fields);
        Field[] fields2 = clazz2.getDeclaredFields();
        fmap.put(clazz2.getName(), fields2);

//        ts = System.currentTimeMillis();
//        for (int i = 0; i < 20000; i++) {
//            for (Map.Entry<String, Field[]> entry : fmap.entrySet()) {
//                if (entry.getKey() == clazz1.getName()) {
//                    Field[] tfs = entry.getValue();
//                    for (Field field : tfs) {
//                        field.setAccessible(true);
//                        System.out.println(field.get(t1));
//                    }
//                }
//                if (entry.getKey() == clazz2.getName()) {
//                    Field[] tfs = entry.getValue();
//                    for (Field field : tfs) {
//                        field.setAccessible(true);
//                        System.out.println(field.get(t2));
//                    }
//                }
//            }
//        }
//        System.out.println("---- reflect " + (System.currentTimeMillis() - ts));
    }
}
