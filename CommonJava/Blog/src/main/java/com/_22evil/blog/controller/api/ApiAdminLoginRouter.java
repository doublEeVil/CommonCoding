package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiAdminLoginRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.queryParams("user");
        String pwd = request.queryParams("password");
        boolean ok = ServiceManager.getInstance().getAdminService().login(name, pwd);
        if (ok) {
            request.session().attribute("login", true);
        }
        JSONObject json = new JSONObject();
        json.put("login", ok);
        return json;
    }
}
