package com._22evil.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http工具类
 */
public class HttpUtil {

    /**
     * 同步get方法
     * @param url
     * @param cb
     */
    public static void get(String url, HttpCallback cb) {
        get0(url, null, cb);
    }

    /**
     * 同步get方法
     * @param url
     * @param params
     * @param cb
     */
    public static void get(String url, Map<String, Object> params, HttpCallback cb) {
        try {
            if (params != null) {
                String s = getParamString(params);
                get0(url, s.getBytes(), cb);
                return;
            }
            get0(url, null, cb);
        } catch (Exception e) {
            cb.fail(e.getMessage());
        }
    }

    /**
     * 同步get方法
     * @param url
     * @param data
     * @param cb
     */
    public static void get(String url, byte[] data, HttpCallback cb) {
        get0(url, data, cb);
    }

    private static void get0(String url, byte[] data, HttpCallback cb) {
        service0(url, "GET", data, cb);
    }

    private static String getParamString(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(URLEncoder.encode(entry.getKey(), "utf-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
            sb.append("&");
        }
        return sb.toString();
    }

    /**
     * 同步post方法
     * @param url
     * @param data
     * @param cb
     */
    public static void post(String url, byte[] data, HttpCallback cb) {
        post0(url, data, cb);
    }

    private static void post0(String url, byte[] data, HttpCallback cb) {
        service0(url, "POST", data, cb);
    }

    private static void service0(String url, String method, byte[] data, HttpCallback cb) {
        try {
            URL curl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)curl.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            if (data != null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(data);
                out.flush();
                out.close();
            }
            int status = conn.getResponseCode();
            DataInputStream in = new DataInputStream(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            byte[] bytes = new byte[512];
            int c = 0;
            while ((c = in.read(bytes)) > 0) {
                sb.append(new String(bytes, 0, c));
            }
            cb.success(status, sb.toString());
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            cb.fail("MalformedURLException url路径错误");
        } catch (IOException e) {
            e.printStackTrace();
            cb.fail("IOException 网络错误");
        } catch (Exception e) {
            e.printStackTrace();
            cb.fail(e.getMessage());
        }
    }
}
