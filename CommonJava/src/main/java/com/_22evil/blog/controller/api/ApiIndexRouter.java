package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiIndexRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String pageStr = request.queryParams("page");
        int page = 1;
        try {
            page = Integer.valueOf(pageStr);
        } catch (Exception e) {

        }
        if (page < 1) page = 1;
        // mysql是从0开始计数的，页码是从0开始计数的
        return ServiceManager.getInstance().getArticleService().getIndex(page - 1);
    }
}
