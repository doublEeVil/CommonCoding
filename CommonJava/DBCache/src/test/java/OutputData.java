import com.alibaba.fastjson.JSONObject;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OutputData {

    public static void main(String ... args) throws Exception {

        if (args.length < 3) {
            System.out.println("input jdbcurl user pwd, eg. 192.168.0.192/dddfk_vn_test_back_1 root 123456");
            return;
        }
        String url = args[0];
        String user = args[1];
        String pwd = args[2];

        System.out.println("====start=====");
        // 玩家钻石返利
        Class.forName("com.mysql.jdbc.Driver");
        //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.192/dddfk_vn_back_1?useTimezone=true&serverTimezone=GMT%2B8", "zjs", "123456");
        Connection conn = DriverManager.getConnection("jdbc:mysql://" + url+ "?useTimezone=true&serverTimezone=GMT%2B8", user, pwd);
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
                    System.out.println("=robot===" + playerId);
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
        System.out.println("====end=====");
    }

    private static String getRewardStrVN(double num) {
        int val = (int)num * 5;
        return "{\"1\":\"[1,"+ val  +"]&[70," + val + "]\"}";
    }
}
