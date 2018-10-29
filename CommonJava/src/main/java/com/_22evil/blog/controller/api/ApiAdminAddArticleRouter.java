package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiAdminAddArticleRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String title = request.queryParams("title");
        String type = request.queryParams("type");
        String tags = request.queryParams("tags");
        String status = request.queryParams("status");
        String content = request.queryParams("content");
        int articleId = ServiceManager.getInstance().getArticleService().addArticle(title, type, tags, status, content);
        JSONObject json = new JSONObject();
        json.put("articleId", articleId);

        return json;
    }
}
