package com._22evil.apache_common_test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于Http, 主要会用到一个HttpClient
 */
public class HttpComponents_Test {

    public static void test_HttpClient() {
        HttpClient client = HttpClients.createDefault();
        // 执行get方法, 打印结果
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 执行post方法， 打印结果
        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        try {
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("name", "zjs"));
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        test_HttpClient();
    }
}
