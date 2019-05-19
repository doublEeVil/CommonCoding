package com._22evil.test.jetty.websocket_client;

import com._22evil.util.PrintUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BenchWSClient {

    private static String wsHost = null;
    private static String token = null;

    @Test
    public static void test() throws Exception{
        org.apache.http.client.HttpClient client = HttpClients.createDefault();
        // 执行get方法, 打印结果

        String username = "zsjst1";
        String password = "123456";
        int channel = 1060;
        String version = "2.0.0";
        String id = "00:00:00:00:00:00";
        int isChannel = 1;
        String md5key = "ga!y^d&eh*wgd";
        String key = "qpvytned";

        String sign = DigestUtils.md5Hex(id + username + password + version + channel + isChannel + md5key);

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("channel", channel);
        json.put("version", version);
        json.put("isChannel", isChannel);
        json.put("sign", sign);
        json.put("id", id);

        byte[] en = CryptionUtils.Encrypt(json.toString().getBytes(), key);

        String host = "http://119.29.186.92:6887/";
        //String url = "http://119.29.186.92:6887/load?data=";
        String url = host + "load?data=";
        url += CryptionUtils.bytesToHexString(en);

        //url = java.net.URLEncoder.encode(url, "utf-8");
        System.out.println(url);

        String data = HttpClientUtils.GetData(url);
        System.out.println(data);

        json = JSONObject.parseObject(data);
        if (json.getInteger("state") != 1) {
            System.err.println("登录出错：" + json);
            return;
        }
        String token = json.getString("token");
        System.out.println(token);

        // 可以ipd登录了
        int serverid = 49;
        url = host + "ipd?serverid=" + serverid + "&version=200&token=" + token;
        data = HttpClientUtils.GetData(url);
        System.out.println(data);

        //获取登录的具体ip
        json = JSONObject.parseObject(data);
        if (json.getInteger("state") != 1) {
            System.err.println("ipd请求出错");
            return;
        }
        String wsHost = json.getString("ip");
        token = json.getString("token"); // 理论上说这一步token值不会改变

        //
        BenchWSClient.token = token;
        BenchWSClient.wsHost = wsHost;
    }

    public static void main(String[] args) throws Exception{
        test();

        if (token == null) {
            System.out.println("http获取请求失败");
            return;
        }

        String url = "ws://"+ wsHost + "/ws";

        int num = 1; // 单线程500个毫无压力

        List<WebSocketClient> clientList = new ArrayList<>(num + 12);
        List<ClientHandler> handlerList = new ArrayList<>(num + 12);



        for (int i = 0; i < num; i++) {
            try {
                WebSocketClient client = new WebSocketClient();
                ClientHandler handler = new ClientHandler(i);

                clientList.add(client);
                handlerList.add(handler);

                client.start();
                URI uri = new URI(url);

                //ClientUpgradeRequest request = new ClientUpgradeRequest();
                client.connect(handler, uri);
                System.out.printf("Connecting to : %s\n", uri);

                handler.awaitClose(5, TimeUnit.SECONDS);


                //handler.sendFrame(ByteBuffer.wrap(loginData()));

                Thread.sleep(1000);
                // Login
                IData login = new IData(10, 20);
                login.writeString("Slf4jLog");
                login.writeString("11");
                handler.sendData(login);

                //GetTaskList
                IData getTaskList = new IData(20, 1);
                //handler.sendData(getTaskList);

                for (int j = 0; j < 5; j++) {
//                    login = new IData(10, 20);
//                    login.writeString("b2f84c75-595b-49c8-9b4e-c413a3902d06");
//                    login.writeString("");
//                    handler.sendData(login);
//                    Thread.sleep(1234);
                }

            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
//            try {
//                client.stop();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            }
        }
    }

    /**
     * 假设协议发送的数据是""
     * @return
     */
    private static byte[] loginData() {
        byte[] data = new byte[18 + 8 + 0 + 1];
        data[0] = 'H';
        data[1] = 'O';
        data[2] = 'G';
        data[3] = 'P'; //

        data[4] = 0;
        data[5] = 0;
        data[6] = 0;
        data[7] = 0; // session id

        data[8] = 0;
        data[9] = 0;
        data[10] = 0;
        data[11] = 1;

        data[12] = 0;
        data[13] = 0;
        data[14] = 0;
        data[15] = 8 + 18;

        data[16] = 0;
        data[17] = 1; // 协议数量

        data[18] = 0;

        data[19] = 10; //main id
        data[20] = 20; //sub id

        data[21] = 0;
        data[22] = 0;
        data[23] = 0;
        data[24] = 0; // 协议长度

        data[25] = 1; // 结束
        // 写入具体内容

        // 包尾
        data[26] = 0;

        return data;
    }
}
