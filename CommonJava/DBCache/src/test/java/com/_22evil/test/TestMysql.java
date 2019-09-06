package com._22evil.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestMysql {

    @Test
    public void test6() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.19.132/ww1?useTimezone=true&serverTimezone=GMT%2B8", "zjs", "123456");
        PreparedStatement ps = conn.prepareStatement("select NOW()");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }
}
