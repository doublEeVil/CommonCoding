package com._22evil.blog;
import com._22evil.blog.controller.api.ApiArticleRouter;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static spark.Spark.*;
/**
 * 启动程序
 * net 部分采用 spark
 * 可以具体分为两种模式
 * 1. 服务端渲染
 * 2. 客户端渲染
 *
 * 暂时先尝试一下客户端渲染模式
 */
public class BlogApp {

    public static BlogConfig BLOG_CONFIG = new BlogConfig();

    public static void main(String[] args) throws Exception{
        System.out.println("BlogApp启动中...");

        BLOG_CONFIG.setConfig(new PropertiesConfiguration("blog/app-config.properties"));
        port(BLOG_CONFIG.port());

        // 初始化数据
        ServiceManager.getInstance().initData();

        // 静态文件
        staticFileLocation("blog/static");

        // hello world
        get("/hello", "application/json", (request, response) -> "{\"message\": \"Hello World\"}");

        // 首页

        // 具体文章
        get("/api/article", "application/json", new ApiArticleRouter());
        System.out.println("BlogApp启动完成...");
    }
}
