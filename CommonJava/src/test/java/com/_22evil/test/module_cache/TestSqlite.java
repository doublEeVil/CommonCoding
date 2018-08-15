package com._22evil.test.module_cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class TestSqlite {

    @Test
    public void baseFunc() {
        Connection conn;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:ttt.db");

            // create table
            stmt = conn.createStatement();
            String sql = "create table tab_test (id int primary key not null" 
                            + ", name varchar(255), age int default 20);";
            stmt.executeUpdate(sql);

            // insert data
            sql = "insert into tab_test(id, name, age) values (1, 'zhujinshan', 14), (2, 'lisi', 19);";
            stmt.executeUpdate(sql);

            // find
            sql = "select * from tab_test";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.printf("id: %d name: %s age: %d\n", id, name, age);
            }

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}