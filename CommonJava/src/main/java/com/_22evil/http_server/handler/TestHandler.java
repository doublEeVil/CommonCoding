package com._22evil.http_server.handler;

import com._22evil.http_server.BaseHandler;
import com._22evil.http_server.Handler;
import com._22evil.http_server.HttpRequest;
import com._22evil.http_server.HttpResponse;

import java.util.Map;

@Handler(value = "/Test")
public class TestHandler extends BaseHandler{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        System.out.println("----- post -----");
        Map<String, String> map= request.getParamMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":__" + entry.getValue());
        }
        String str = "hello, test user";
        response.write(str);
        //response.write("hello post" + request.getParam("data"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.flushAndResponse();
    }
}
