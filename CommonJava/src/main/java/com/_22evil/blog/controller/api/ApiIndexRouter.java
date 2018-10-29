package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiIndexRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return ServiceManager.getInstance().getArticleService().getIndex();
    }
}
