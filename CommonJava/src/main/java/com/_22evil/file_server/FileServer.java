package com._22evil.file_server;

import spark.Spark;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
/**
 * 文件服务器
 */
public class FileServer {

    public static void main(String[] args) {
        // 端口
        Spark.port(8081);

        // 静态文件
        staticFileLocation("file_server/static");

        // 是目录返回目录列表，是文件返回具体文件
        get("/",  ((request, response) -> {
            response.redirect("/index.html");
            return null;
        }));

        // 接收发送过来的文件
        post("/upload", "application/json", ((request, response) -> {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("data"));
            try {
                Collection<Part> partList = request.raw().getParts();
                for (Part part : partList) {
                    if (!part.getSubmittedFileName().equals("config")) {
                        return "上传的文件必须是 config 配置文件";
                    }
                    File file = new File( "/data/tomcat/apache-tomcat-7.0.57/webapps/dddh5/resource/config/" + part.getSubmittedFileName());
                    if (file.exists()) {
                        Files.delete(Paths.get(file.getPath()));
                    }
                    InputStream input = part.getInputStream();
                    Files.copy(input, Paths.get(file.getPath()));
                }
            } catch (Exception e) {
                return "上传失败: " + e.getMessage();
            }
            return "success";
        }));
    }
}
