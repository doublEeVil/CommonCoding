package com._22evil.test.mysql;

import com._22evil.test.cache.entity.GamePlayer;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
            List<GamePlayer> _15create = new ArrayList<>();
            List<GamePlayer> _16login = new ArrayList<>();
            for (GamePlayer player : all) {
                if (player.getAccountId() > 0 && player.getCreateTime() < calendar16.getTimeInMillis()) {
                    _15create.add(player);
                    if (player.getLoginTime() > calendar16.getTimeInMillis() && player.getLevel() > 1) {
                        _16login.add(player);
                    }
                }
            }

            System.out.println("-----" + all.size());
            System.out.println("-----" + _15create.size());
            System.out.println("-----" + _16login.size());

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
    PreparedStatement ps = null;

}
