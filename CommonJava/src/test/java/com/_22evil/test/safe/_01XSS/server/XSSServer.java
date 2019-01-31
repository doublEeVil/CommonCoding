package com._22evil.test.safe._01XSS.server;
import spark.Spark;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.port;

public class XSSServer {

    public static void main(String ... args) {

        port(8888);

        // 模拟两个账户密码
        Map<String, String> pwdMap = new HashMap<>();
        pwdMap.put("aaa", "aaa");
        pwdMap.put("bbb", "bbb");


        Map<Long, String> msgMap = new HashMap<>();

        Spark.get("/login", (req, resp) -> {
            String html = "";
            html += "<html>"
                    + "<head>"
                    + "<title>Login</title>"
                    + "</head>"
                    + "<body>"
                    + "<form action='/login' method='post'>"
                    + "<input type=text name=user value=user></input>"
                    + "<input type=password name=pwd value=pwd></input>"
                    + "<input type=submit></input>"
                    + "</form>"
                    + "</body>"
                    + "</html>";
            return html;
        });

        Spark.post("/login", ((request, response) -> {
            String user = request.queryParams("user");
            String pwd = request.queryParams("pwd");
            System.out.println("user:" + user + " pwd:" + pwd);
            String dbpwd = pwdMap.get(user);
            if (dbpwd != null && dbpwd.equals(pwd)) {
                request.session().attribute("user", user);
                response.redirect("/");
            } else {
                response.redirect("/login");
            }
            return "+++";
        }));


        Spark.get("/", ((request, response) -> {
            String user = request.session().attribute("user");

            String inner = "";
            for (Map.Entry<Long, String> entry : msgMap.entrySet()) {
                inner += new Date(entry.getKey()).toLocaleString() + ":" + entry.getValue() + "<br>";
            }

            String html = "";
            html += "<html>"
                    + "<head><title>首页</title></head>"
                    + "<body>"
                    + "Welcome, " + user
                    + "<form action='/' method='post'>"
                    + "<textarea name=data value=data></textarea>"
                    + "<input type=submit></input>"
                    + "</form>"
                    + "<hr>"
                    + inner
                    + "</body>"
                    + "</html>";

            return html;
        }));

        Spark.post("/", (request, response) -> {
            String user = request.session().attribute("user");
            if (user == null) {
                // 返回登录
                response.redirect("/login");
                return "5555";
            }
            String data = request.queryParams("data");
            msgMap.put(System.currentTimeMillis(), user + " " + data);
            response.redirect("/");
            return "1234";
        });
    }


}
