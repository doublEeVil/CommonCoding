package com._22evil.test;

import com._22evil.util.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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

    @Test
    public void test7() throws Exception {

    }

    @Test
    public void test8() throws Exception {
        // 玩家钻石返利
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.192/dddfk_vn_back_2?useTimezone=true&serverTimezone=GMT%2B8", "zjs", "123456");
        PreparedStatement ps = conn.prepareStatement("SELECT player_id, sum(price) from game_order where `status`=3 GROUP BY player_id");
        ResultSet rs = ps.executeQuery();
        Map<Integer, Double> playerIdRecharge = new HashMap<>();
        Map<Integer, Integer> playerIdVIPEXP = new HashMap<>();
        // 全部充值金额
        while (rs.next()) {
            playerIdRecharge.put(rs.getInt(1), rs.getDouble(2));
        }

        // 查询玩家对应id
        JSONObject json;
        Map<Integer, String> playerIdAccountName = new HashMap<>();
        for (Integer playerId : playerIdRecharge.keySet()) {
            ps = conn.prepareStatement("select ext_field from game_player where id=" + playerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                json = JSONObject.parseObject(rs.getString(1));
                String accountName = json.getString("accountname");
                int vipExp = json.getInteger("vipExp");
                playerIdAccountName.put(playerId, accountName);
                playerIdVIPEXP.put(playerId, vipExp);
                if (accountName.equals("robot")) {
                    System.out.println("====" + playerId);
                }
            }
        }

        // 批量插入
        ps = conn.prepareStatement("insert into game_account_reward(account,mail_tiltle,mail_content,reward) values(?,?,?,?)");
        int i = 0;

        //String mailTitleCN = "内测活动奖励";
        //String mailContentCN = "这是你的专属内测活动奖励";

        String mailTitleVN = "Hoàn Trả AlphaTest";
        String mailContentVN = "Alice xin gửi tặng xạ thủ quà hoàn trả AlphaTest." +
                "Chúc xạ thủ chơi game vui vẻ.";
        for (Integer playerId : playerIdRecharge.keySet()) {
            ps.setString(1, playerIdAccountName.get(playerId));
            ps.setString(2, mailTitleVN);
            ps.setString(3, mailContentVN);
            ps.setString(4, getRewardStrVN(playerIdRecharge.get(playerId)));
            ps.addBatch();
            i++;
            if (i % 50 == 0) {
                ps.executeBatch();
            }
        }
        ps.executeBatch();
        ps.close();
        conn.close();
    }

    private String getRewarVIPEXP(double num) {
        int val = (int)num;
        return "{\"1\":\"[17,"+ val  +"]\"}";
    }

    private String getRewardStr(double num) {
        int val = (int)num * 20;
        return "{\"1\":\"[1,"+ val  +"]\"}";
    }

    private String getRewardStrVN(double num) {
        int val = (int)num * 5;
        return "{\"1\":\"[1,"+ val  +"]&[70," + val + "]\"}";
    }

    @Test
    public void test9() {

    }

    @Test
    public void test10() throws Exception {

    }
}
