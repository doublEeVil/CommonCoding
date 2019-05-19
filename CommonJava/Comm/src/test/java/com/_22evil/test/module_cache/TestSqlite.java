package com._22evil.test.module_cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com._22evil.blog.entity.Article;
import com._22evil.module_cache.mysql.service.GenericMySqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:jdbc.properties"})
@ContextConfiguration(locations = {"classpath:spring-hibernate.xml"})
public class TestSqlite {

    @Autowired
    private GenericMySqlService genericMysqlService;

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

    @Test
    public void testSqlite2Mysql() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:ttt.db");
            stmt = conn.createStatement();
            // find
            String sql = "select * from t_contents";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("cid");
                String title = rs.getString("title");
                long create = rs.getLong("created");
                long update = rs.getLong("modified");
                String content = rs.getString("content");
                String status = rs.getString("status");
                String tags = rs.getString("tags");
                String type = rs.getString("fmt_type");

                Article article = new Article();
                article.setTitle(title);
                article.setCreateDate(create);
                article.setUpdateDate(update);
                article.setContent(content);
                article.setStatus(status);
                article.setTags(tags);
                article.setType(type);
                genericMysqlService.save(article);
            }

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}