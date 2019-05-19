package com._22evil.test.safe._01XSS.hackserver;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
/**
 * 模拟黑客服务器，获得第三方上传的内容
 */
public class XSSHackServer {
    public static void main(String ... args) {

        Map<String, String> cookieMap = new HashMap<>();
        Spark.port(9000);

        Spark.post("/data", (request, response) -> {
            String data = request.queryParams("data");
            System.out.println(data);
            return "";
        });

        Spark.get("/data", (request, response) -> {
            return "";
        });
    }
}
