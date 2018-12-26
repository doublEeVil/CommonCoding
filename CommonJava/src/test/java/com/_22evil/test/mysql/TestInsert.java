package com._22evil.test.mysql;
import org.junit.Test;
import java.sql.*;
public class TestInsert {
    /***********************
     * 以测试1w代码为例
     * *********************
     ***********************/



    /*********
     * 不好的写法
     *********/
    @Test
    public void test1w_bad() {
        // 下面是十分不推荐的写法
        long t1 = System.currentTimeMillis();

        PreparedStatement ps;
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");
            // 插入数据
            int SIZE = 10000;

            for (int i = 0; i < SIZE; i++) {
                String sql = "insert into stu (`id`, `name`, `age`) values (" + i + ", 'zjs', 12)";
                conn.createStatement().execute(sql);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("time: " + (System.currentTimeMillis() - t1));

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************
     * 推荐下面这种写法  大概是38秒
     ****************/
    @Test
    public void test1w() {
        long t1 = System.currentTimeMillis();

        PreparedStatement ps = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");
            // 插入数据
            int SIZE = 10000;
            String sql = "insert into stu (id, name, age) values (?, ?, ?)";
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < SIZE; i++) {
                ps.setObject(1,     i);
                ps.setObject(2, "zjs");
                ps.setObject(3, i % 100);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("time: " + (System.currentTimeMillis() - t1));

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****************
     * 推荐下面这种写法  大概是1w2秒, 10w14秒
     * 加了参数进去后， 大概是1w 580ms， 10w 1400ms， 100w 10秒（10w的批量插入可以缩短为7秒）， 1000w 97秒（1000的批量插入） 1kw 57秒（10w的批量插入）
     * 总而言之，相比于上面，多了两个步骤
     * 1. url多了参数 useServerPrepStmts=false&rewriteBatchedStatements=true
     * 2. conn.setAutoCommit(false);
     ****************/
    @Test
    public void test1w_good() {
        long t1 = System.currentTimeMillis();

        PreparedStatement ps = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world?useServerPrepStmts=false&rewriteBatchedStatements=true", "zjs", "123456");
            conn.setAutoCommit(false);  // 必须设置为false,否则比较慢
            // 插入数据
            int SIZE = 1000_0000;
            int batchSize = 10_0000;
            String sql = "insert into stu (id, name, age) values (?, ?, ?)";
            ps = conn.prepareStatement(sql);

            for (int i = 1; i < SIZE; i++) {
                ps.setObject(1,     i);
                ps.setObject(2, "zjs");
                ps.setObject(3, i % 100);
                ps.addBatch();
                if (i % batchSize == 0) {
                    ps.executeBatch();
                    conn.commit();
                }
            }
            ps.executeBatch();
            conn.commit();
            ps.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("time: " + (System.currentTimeMillis() - t1));

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
