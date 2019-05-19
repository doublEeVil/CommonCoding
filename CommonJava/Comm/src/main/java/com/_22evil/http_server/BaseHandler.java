package com._22evil.http_server;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseHandler {
    public static Map<String, BaseHandler> handlerMap = new HashMap<>();

    public void doService(String method, HttpRequest request, HttpResponse response) {
        if (method.equalsIgnoreCase("GET")) {
            doGet(request, response);
        } else if (method.equalsIgnoreCase("POST")) {
            doPost(request, response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);
    public abstract void doPost(HttpRequest request, HttpResponse response);
}
