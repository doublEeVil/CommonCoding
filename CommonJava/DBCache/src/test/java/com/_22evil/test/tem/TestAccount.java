package com._22evil.test.tem;


import net.sf.json.JSONObject;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestAccount {

    @Test
    public void test() throws Exception {
        System.out.println("===start...");
        long t1 = System.currentTimeMillis();
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://192.168.1.16:3306/ddd2_account_vn20190624", "root", "123abcdEf#u90");

        PreparedStatement ps1 = conn.prepareStatement("select `id`, `login_times` from tab_account");
        ResultSet rs = ps1.executeQuery();

        String str;
        JSONObject json;
        int count = 0;
        while (rs.next()) {
            count++;
            str = rs.getString(2);
            try {
                json = JSONObject.fromObject(str);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("---id:" + rs.getInt(1) + " str");
            }

        }
        ps1.close();
        conn.close();
        System.out.println("===耗时：" + (System.currentTimeMillis() - t1) / 1000 + "秒 count:" + count);
    }
}
