package com._22evil.http_server;

import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private OutputStreamWriter out;
    private StringBuilder header;
    private StringBuilder content;
    private Map<String, String> headerMap;

    public HttpResponse(OutputStreamWriter out) {
        this.out = out;
        this.header = new StringBuilder();
        this.content = new StringBuilder();
        this.headerMap = new HashMap<>();
    }

    public void write(String s) {
        this.content.append(s);
    }

    public void write(Object obj) {
        this.content.append(obj);
    }

    public void setHeader(String key, String value) {
        this.headerMap.put(key, value);
    }

    public void flushAndResponse() {
        try {
            header.append("HTTP/1.1 200 OK\n");
            header.append("Server: simple http server \r\n");
            Set<String> keys = headerMap.keySet();
            for (String key : keys) {
                header.append(key);
                header.append(": ");
                header.append(headerMap.get(key));
                header.append('\n');
            }
            header.append("Content-length:");
            header.append(content.length());
            header.append("\r\n");
            header.append("Content-type:");
            header.append("text/plain\r\n\r\n");
            out.write(header.toString());
            out.write(content.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
