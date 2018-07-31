package com._22evil;
import java.util.Random;

import com._22evil.netty_test.http_server_spring.MyHttpServer;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
public class Test {
    public static void main(String[] args) {
        try {
            System.out.println(randomString(-229985452)+' '+randomString(-147909649));
            System.err.println(new File("template").exists());

            System.err.println("---" + new File("spring.xml").exists());
            System.out.println(System.getProperty("user.dir")); 

            new MyHttpServer(9001).run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUtil.get("http://www.baidu.com", new HttpCallback() {
            public void fail(String errorMsg) {

            }

            /**
             * 发送请求后回调
             * @param code 返回码
             * @param data 原有数据
             */
            public void success(int code, String data) {
                System.out.println(data);
            }
        });
    }

    public static String randomString(int seed) {
        Random rand = new Random(seed);
        StringBuilder sb = new StringBuilder();
        while(true) {
            int n = rand.nextInt(27);
            if (n == 0) break;
            sb.append((char) ('`' + n));
        }
        return sb.toString();
    }
}
