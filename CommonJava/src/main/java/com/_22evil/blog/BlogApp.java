package com._22evil.blog;
import org.apache.commons.configuration.PropertiesConfiguration;

import static spark.Spark.*;
/**
 * 启动程序
 * net 部分采用 spark
 */
public class BlogApp {

    public static BlogConfig BLOG_CONFIG = new BlogConfig();

    public static void main(String[] args) throws Exception{
        System.out.println("BlogApp启动中...");
        BLOG_CONFIG.setConfig(new PropertiesConfiguration("blog/app.properties"));
        port(BLOG_CONFIG.port());
        // 静态文件
        staticFileLocation("blog/static");
        //
        get("/hello", "application/json", (request, response) -> "{\"message\": \"Hello World\"}");
    }
}
