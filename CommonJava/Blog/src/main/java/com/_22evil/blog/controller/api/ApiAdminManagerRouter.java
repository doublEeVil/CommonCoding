package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiAdminManagerRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        System.out.println(request.cookies());
        System.out.println("manager: " + request.session());
        System.out.println(request.session().lastAccessedTime());
        //System.out.println("+++" + request.attribute("foo").toString());
        Boolean login = request.session().attribute("login");
        JSONObject json = ServiceManager.getInstance().getArticleService().getAllTitle();
        json.put("login", login);
        return json;
    }
}
