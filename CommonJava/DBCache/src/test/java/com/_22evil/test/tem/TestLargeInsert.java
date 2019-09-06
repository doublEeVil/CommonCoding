package com._22evil.test.tem;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/*
    -- 表结构
    CREATE TABLE `test_game_data` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `server_id` int(11) NOT NULL,
      `mark_num` int(11) NOT NULL,
      `pointer_id` int(11) NOT NULL,
      `msg` varchar(1024) NOT NULL,
      `create_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class TestLargeInsert {

    @Test
    public void test() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world??useServerPrepStmts=false&rewriteBatchedStatements=true", "zjs", "123456");
            //conn.setAutoCommit(false);
            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO test_game_data(server_id,mark_num,pointer_id,msg,create_at) values(?,?,?,?,?)");
            long size = Integer.MAX_VALUE * 2L;
            int count = 0;
            for (long i = 0; i < size; i++) {
                ps1.setInt(1, (int)(i % 10));
                ps1.setInt(2, (int)(i % 333));
                ps1.setInt(3, (int) (i % 100000));
                ps1.setString(4, "45\tzjs\tdd\t456");
                ps1.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()/(i % 1000 == 0 ? 1 : i % 1000)));
                ps1.addBatch();
                if (count++ > 100000) {
                    ps1.executeBatch();
                    //conn.commit();
                    count = 0;
                }
            }
            ps1.executeBatch();
            //conn.commit();
            ps1.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
