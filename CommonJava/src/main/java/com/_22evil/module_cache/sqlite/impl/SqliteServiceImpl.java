package com._22evil.module_cache.sqlite.impl;

import java.sql.Connection;
import java.util.List;

import com._22evil.module_cache.sqlite.SqliteConnManager;
import com._22evil.module_cache.sqlite.SqliteService;
import org.springframework.stereotype.Component;

// TODD 待完成

@Component("com._22evil.module_cache.sqlite.impl.SqliteServiceImpl")
public class SqliteServiceImpl implements SqliteService {
    private Connection conn;

    public SqliteServiceImpl() {
        // 初始化一些东西
    }

    public void update(Object obj) {

    }

    public Object save(Object obj) {
        return obj;
    }

    public <T> T find(Class<T> clazz ,int id) {
        return null;
    }

    public <T> List<T>  findAll(Class<T> clazz) {
        return null;
    }

    public <T> List<T> findBySql(String sql) {
        return null;
    }

    private Connection getConn() {
        if (null == conn) {
            conn = SqliteConnManager.getInstance().getConn();
        }
        return conn;
    }
}