package com._22evil.blog.controller.api;

import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiAdminManagerRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        System.out.println("manager: " + request.session());
        Boolean login = request.session().attribute("login");
        JSONObject json = new JSONObject();
        json.put("login", login);
        return json;
    }
}
