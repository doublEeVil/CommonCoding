package com._22evil.blog;
import com._22evil.blog.controller.api.*;
import com._22evil.blog.entity.EventLog;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.*;

import java.util.Date;

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
    private static final Logger logger = LoggerFactory.getLogger("AppLog");
    public static BlogConfig BLOG_CONFIG = new BlogConfig();

    public static void main(String[] args) throws Exception{

        System.out.println("BlogApp启动中...");
        logger.info("server start ... " + new Date());

        initExceptionHandler((e) -> {
            System.out.println("exception occur");
            logger.error("", e);
            e.printStackTrace();
        });

        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

        Configurations configs = new Configurations();
        PropertiesConfiguration cfg = configs.properties("app-config.properties");
        BLOG_CONFIG.setConfig(cfg);
        port(BLOG_CONFIG.port());

        // 初始化数据
        ServiceManager.getInstance().initData();

        // 静态文件(不包括上传文件)
        staticFileLocation("static");
        // 网页上传的文件路径
        System.err.println("图片上传路径为:" + BLOG_CONFIG.getUploadPicPath());
        staticFiles.externalLocation(BLOG_CONFIG.getUploadPicPath());

        before("/*", ((request, response) -> {
            logger.info("访问记录:ip:"+request.ip() + " path:" + request.url() + " params:" + request.params());
            EventLog eventLog = new EventLog();
            eventLog.setIp(request.ip());
            eventLog.setAuth(request.session().attribute("login") != null);
            eventLog.setUrl(request.url());
            eventLog.setMethod(request.requestMethod());
            eventLog.setParams(request.params().toString());
            ServiceManager.getInstance().getEventLogService().add(eventLog);
        }));


        // hello world
        get("/hello", "application/json", (request, response) -> {
            //response.redirect("http://127.0.0.1:8888/pic.html", 302);
            JSONObject json = new JSONObject();
            json.put("message", "hello world");
            return json;
            //return "{\"message\": \"Hello World\"}";
        });

        post("/hello", "application/json", (request, response) -> {
            //response.redirect("http://127.0.0.1:8888/pic.html", 401);
            JSONObject json = new JSONObject();
            json.put("message", "hello world");
            return json;
            //return "{\"message\": \"Hello World\"}";
        });


        // 前置拦截器
        String[] limitUrls = new String[] {
                "/api/admin_manager",
                "/api/admin_article",
                "/api/pic_add",
                "/admin_manager.html",
                "/admin_article.html",
                "/pic_add.html"
        };
        for (String url :limitUrls) {
            before(url, ((request, response) -> {
                if (request.session().attribute("login") == null) {
                    if (request.requestMethod() == "GET") {
                        response.redirect("/admin_login.html",302);
                    } else {
                        response.redirect("/admin_login.html",401);
                    }
                    halt(401, "You are not welcome here");
                }
            }));
        }

        // 处理 api开头的请求
        path("/api", () -> {

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
            // 查询
            post("/search", "application/json", new ApiSearchRouter());
            // 反馈信息
            post("/send_feedback", "application/json", new ApiFeekbackRouter());
            // 简历
            post("/jianli", "application/json", new ApiJianliRouter());

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
            System.out.println("发生异常");
            logger.error("", e);
            e.printStackTrace();
            response.body("<html><body>发生未知错误</body></html>");
        });

        System.out.println("BlogApp启动完成...");


        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            stop();
            logger.info("程序被killed " + new Date());
            System.out.println("程序被killed");
        }));

    }
}
