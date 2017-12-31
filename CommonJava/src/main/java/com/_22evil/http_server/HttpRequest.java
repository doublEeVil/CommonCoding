package com._22evil.http_server;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String srcHeader;       // 原始请求头
    private String method;
    private String host;
    private String url;
    private Map<String, String> paramMap;

    public HttpRequest(String header) {
        this.srcHeader = header;
        String[] data_header = header.split("\r\n");
        String[] data_header_line_1 = data_header[0].split("\\s");
        method = data_header_line_1[0];
        url = data_header_line_1[1].replaceAll("\\?.*", "");
        String[] data_header_line_2 = data_header[1].split("\\s");
        host = data_header_line_2[1];
        String data_content = header.replaceAll("[\\s\\S]*\r\n\r\n", "");
        if (method.equals("GET")) {
            data_content = data_header_line_1[1].replaceAll(".*\\?", "");
        }
        initParam(data_content);
    }

    public String getMethod() {
        return method;
    }

    public String getHost() {
        return host;
    }

    public String getUrl() {
        return url;
    }

    public String getSrcHeader() {return srcHeader;}

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public String getParam(String key) {
        return (String)paramMap.get(key);
    }

    private void initParam(String content) {
        paramMap = new HashMap<>();
        if (content.length() == 0) {
            return;
        }
        try {
            content = java.net.URLDecoder.decode(content, "utf-8");
            String[] data = content.split("&");
            for (String kv : data) {
                String key = kv.replaceAll("=.*", "");
                String value = kv.replaceAll(".*=", "");
                paramMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
