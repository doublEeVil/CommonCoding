package com._22evil.appstore_verify;

import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppStoreVerify {

    public static Map<String, IOSdata> keymap = new HashMap<>();
    public static String srcFile = "C:\\Users\\Administrator\\Desktop\\recharge\\tmp\\16\\all.txt";
    public static String dstFile = "c:\\tuerqi_16.txt";
    public static void main(String[] args) {
        getOrderListInfo();
        verfy();
        writeData();
    }

    public static void appendFile(String path, String value) {
        try {
            FileWriter fileWriter = new FileWriter(path,true);
            fileWriter.append(value);
            fileWriter.append("\n");
            fileWriter.flush();//立即保存
            fileWriter.close();//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeData() {
        Set<String> keys = keymap.keySet();
        try {
            for (String key : keys) {
                String s = "";
                IOSdata data = keymap.get(key);
                com.alibaba.fastjson.JSONObject tt = data.getJson().getJSONObject("receipt");
                if (tt == null) {
                    continue;
                }
                s += data.getPlayerId();
                s += ",";
                s += data.getJson().getJSONObject("receipt").getString("product_id");
                s += ",";
                s += data.getJson().getJSONObject("receipt").getString("original_purchase_date");
                s += ",";
                s += data.getJson().toJSONString();
                appendFile(dstFile, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verfy() {
        Set<String> keys = keymap.keySet();
        try {
            for (String key : keys) {
                verfy(key);
                Thread.sleep(20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verfy(String key) {
        String buy_url = "https://buy.itunes.apple.com/verifyReceipt";
        Map<String, Object> map = new HashMap<>();
        map.put("receipt-data", key);
        byte[] data = net.minidev.json.JSONObject.toJSONString(map).getBytes();
        HttpUtil.post(buy_url, data, new HttpCallback() {
            @Override
            public void fail(String errorMsg) {
                System.out.println(errorMsg);
            }

            @Override
            public void success(int code, String data) {
                com.alibaba.fastjson.JSONObject json = JSON.parseObject(data);
                keymap.get(key).setJson(json);
            }
        });
    }


    public static void getOrderListInfo() {
        try {
            String path = srcFile;
            List<String> orders = FileUtils.readLines(new File(path));
            for (String od : orders) {
                getMap(od);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getMap(String data) {
        String key = data.replaceAll(".*key:", "");
        String player = data.replaceAll(".*验证失败：-----------player:", "").replaceAll("---------order:.*", "");
        keymap.put(key, new IOSdata(player, key));
    }
}
