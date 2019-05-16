package com._22evil.blog;
import com._22evil.blog.controller.api.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import spark.*;

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

    private static final Logger logger = LogManager.getLogger(BlogApp.class);

    public static BlogConfig BLOG_CONFIG = new BlogConfig();

    public static void main(String[] args) throws Exception{
        System.out.println("BlogApp启动中...");

        BLOG_CONFIG.setConfig(new PropertiesConfiguration("blog/app-config.properties"));
        port(BLOG_CONFIG.port());

        // 初始化数据
        ServiceManager.getInstance().initData();

        // 静态文件(不包括上传文件)
        staticFileLocation("blog/static");
        // 网页上传的文件路径
        System.err.println("图片上传路径为:" + BLOG_CONFIG.getUploadPicPath());
        staticFiles.externalLocation(BLOG_CONFIG.getUploadPicPath());

        // hello world
        get("/hello", "application/json", (request, response) -> {
            System.out.println(request.session().id());
            return "{\"message\": \"Hello World\"}";
        });

        // 处理 api开头的请求
        path("/api", () -> {
            // 前置拦截器
            String[] limitUrls = new String[] {
                "/admin_manager",
                "/admin_article",
                "/pic_add"
            };
            for (String url :limitUrls) {
                before(url, ((request, response) -> {
                    if (request.session().attribute("login") == null) {
                        throw new RuntimeException("22222");
                        //response.redirect("/admin_login.html");
                        //halt(401, "You are not welcome here");
                    }
                }));
            }


            // 首页信息
            post("/index", "application/json", new ApiIndexRouter());
            // 具体文章
            post("/article", "application/json", new ApiArticleRouter());
            // 管理界面-登录
            post("/admin_login", "application/json", new ApiAdminLoginRouter());
            // 管理界面-首页
            post("/admin_manager", "application/json", new ApiAdminManagerRouter());
            // 管理界面-增加,编辑,删除文章
            post("/admin_article", "application/json", new ApiAdminArticleRouter());

            // 增加图片
            post("/pic_add", "application/json", new ApiPicAddRouter());
            // 查看近期图片
            post("/pic","application/json", new ApiPicRouter());

            // 后置拦截器处理
            afterAfter(((request, response) -> {
                response.header("Access-Control-Allow-Credentials", "true");
                response.header("Access-Control-Allow-Headers", "x-requested-with");
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.header("Access-Control-Max-Age", "3600");
                response.header("XDomainRequestAllowed","1");
            }));

        });


        // 异常同一处理
        Spark.exception(Exception.class, (e, request, response) -> {
            logger.error(e, e);
            e.printStackTrace();
        });

        initExceptionHandler((e) -> {
            System.out.println("exception occur");
            logger.error(e, e);
            //e.printStackTrace();
        });

        System.out.println("BlogApp启动完成...");
        logger.error("启动完成...");

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            stop();
            System.out.println("程序被killed");
        }));

    }
}
