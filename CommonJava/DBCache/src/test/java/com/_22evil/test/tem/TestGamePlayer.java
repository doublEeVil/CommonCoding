package com._22evil.test.tem;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    PreparedStatement ps = null;

}
