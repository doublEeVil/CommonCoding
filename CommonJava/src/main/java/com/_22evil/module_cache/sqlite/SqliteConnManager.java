package com._22evil.module_cache.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODD 待完成
public class SqliteConnManager {
    private static SqliteConnManager instance;
    private List<Connection> pool;

    private SqliteConnManager() {
        pool = new ArrayList(4);
        try {
            Class.forName("org.sqlite.JDBC");

            for (int i = 0; i < 4; i++) {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:ttt.db");
                pool.add(conn);
            }
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public static SqliteConnManager getInstance() {
        if (null == instance) {
            instance  = SqliteConnBuilder.instance;
        }
        return instance;
    }

    public Connection getConn() {
        if (pool.size() != 0) {
            Connection conn = pool.get(0);
            pool.remove(0);
            return conn;
        }
        return null;
    }

    static class SqliteConnBuilder {
        private static SqliteConnManager instance = new SqliteConnManager();
    }
}