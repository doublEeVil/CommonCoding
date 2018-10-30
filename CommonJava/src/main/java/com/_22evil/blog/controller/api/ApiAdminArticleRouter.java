package com._22evil.blog.controller.api;

import com._22evil.blog.ServiceManager;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
/**
 * 增加或者编辑或者删除文章
 */
public class ApiAdminArticleRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        JSONObject json = new JSONObject();
        String action= request.queryParams("action");
        String title = request.queryParams("title");
        String tags = request.queryParams("tags");
        String status = request.queryParams("status");
        String content = request.queryParams("content");
        if ("add".equals(action)) {
            // 增加文章
            String type = request.queryParams("type");
            int articleId = ServiceManager.getInstance().getArticleService().addArticle(title, type, tags, status, content);
            json.put("articleId", articleId);
        } else if ("edit".equals(action)) {
            // 编辑文章
            String articleId = request.queryParams("articleId");
            ServiceManager.getInstance().getArticleService().editArticle(articleId,title, tags, status, content);
            json.put("articleId", articleId);
        } else if ("delete".equals(action)) {
            // 删除文章
        }
        return json;
    }
}
