package com._22evil.test.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

/**
 * 测试脏数据 mysql 一个事务未提交 另一个事务开始查询，得到的有可能是脏数据
 * 表结构Stu(id, name, age) 
 */

public class TestDirtyData {
    private Connection conn = null;
    private Connection conn2 = null;

    /**
     * 相同链接的情况
     * 直接有脏数据
     */
    @Test
    public void test1() {
        
        PreparedStatement ps = null;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.3.30/test", "root", "123456");
            conn.setAutoCommit(false);

            // 清空数据
            ps = conn.prepareStatement("delete from stu where id > -1");
            ps.execute();
            conn.commit();

            // 插入数据
            ps = conn.prepareStatement("insert into stu (id, name, age) values (?, ?, ?)");
            ps.setObject(1, 1);
            ps.setObject(2, "zjs");
            ps.setObject(3, 24);
            ps.execute();
            conn.commit();

            CountDownLatch latch = new CountDownLatch(1);
            // 一个线程写, 不提交，半路回滚
            new Thread(() -> {
                try {
                    PreparedStatement ps1 = conn.prepareStatement("update stu set age = 100 where id = 1");
                    ps1.execute();
                    latch.countDown();
                    Thread.sleep(2000);
                    // conn.commit();
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            }).start();

            // 一个线程查， 得到的就是脏数据
            new Thread(() -> {
                try {
                    latch.await();
                    PreparedStatement ps1 = conn.prepareStatement("select age from stu where id = 1");
                    ResultSet set = ps1.executeQuery();
                    while (set.next()) {
                        System.err.println("====" + set.getInt("age"));
                    }
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            }).start();

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

    /**
     * 不同链接的情况
     * 不会出现脏数据
     */
    @Test
    public void test2() {
        PreparedStatement ps = null;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.3.30/test", "root", "123456");
            conn.setAutoCommit(false);

            conn2 = DriverManager.getConnection("jdbc:mysql://192.168.3.30/test", "root", "123456");
            conn2.setAutoCommit(false);

            // 清空数据
            ps = conn.prepareStatement("delete from stu where id > -1");
            ps.execute();
            conn.commit();

            // 插入数据
            ps = conn.prepareStatement("insert into stu (id, name, age) values (?, ?, ?)");
            ps.setObject(1, 1);
            ps.setObject(2, "zjs");
            ps.setObject(3, 24);
            ps.execute();
            conn.commit();

            CountDownLatch latch = new CountDownLatch(1);
            // 一个线程写, 不提交，半路回滚
            new Thread(() -> {
                try {
                    PreparedStatement ps1 = conn.prepareStatement("update stu set age = 100 where id = 1");
                    ps1.execute();
                    latch.countDown();
                    Thread.sleep(2001);
                    // conn.commit();
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            }).start();

            // 一个线程查， 得到的就是脏数据
            new Thread(() -> {
                try {
                    latch.await();
                    PreparedStatement ps1 = conn2.prepareStatement("select age from stu where id = 1");
                    ResultSet set = ps1.executeQuery();
                    while (set.next()) {
                        System.err.println("==-==" + set.getInt("age"));
                    }
                    conn2.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            }).start();

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
    public void test3() {
        System.out.println("c==");
    }
}