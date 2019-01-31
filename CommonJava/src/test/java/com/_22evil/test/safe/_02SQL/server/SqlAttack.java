package com._22evil.test.safe._02SQL.server;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import spark.Spark;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * sql 注入
 * 简单测试下sql注入的漏洞
 */
public class SqlAttack {

    // @Test
    public static void main(String ... args) {

        // 启动一个服
        Spark.port(8888);

        Spark.before("*/*", (request, response) -> {
            System.out.println(request.url());
        });


        // 登录界面
        Spark.post("/login", (request, response) -> {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement ps = null;
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/world", "zjs", "123456");

            String user = request.queryParams("user");
            String pwd = request.queryParams("pwd");
            //ps = conn.prepareStatement("SELECT * FROM t_stu where `name` = '" + user + "' AND `pwd` = '" + pwd + "'");
            ps = conn.prepareStatement("SELECT * FROM t_stu where `name`=? and `pwd`=?");
            ps.setString(1, user);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();

            boolean loginSuccess = false;
            while (rs.next()) {
                System.out.println(rs);
                loginSuccess = true;
                break;
            }
            return "loginSuccess: " + loginSuccess;
        });
    }

    /**
     * 下面这种写法， 可以绕过登录验证
     * @throws UnsupportedEncodingException
     */
    @Test
    public void test() throws UnsupportedEncodingException {
        HttpPost post = new HttpPost("http://192.168.0.192:8888/login");
        HttpClient client = HttpClients.createMinimal();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("user", "zhangsan"));
        params.add(new BasicNameValuePair("pwd", "abc' or '1' = '1"));
        post.setEntity(new UrlEncodedFormEntity(params));
        try {
            HttpResponse response = client.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
