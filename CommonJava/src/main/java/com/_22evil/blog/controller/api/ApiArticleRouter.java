package com._22evil.blog.controller.api;
import com._22evil.blog.ServiceManager;
import spark.Request;
import spark.Response;
import spark.Route;
/**
 * 请求具体文章
 */
public class ApiArticleRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String articleId = request.params("articleId");
        if (articleId == null) {
            articleId = String.valueOf(1);
        }
        response.header("Access-Control-Allow-Origin", "*");
        return ServiceManager.getInstance().getArticleService().getArticle(Integer.valueOf(articleId));
    }
}
