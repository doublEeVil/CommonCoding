package com._22evil.blog.service.impl;
import com._22evil.blog.entity.Article;
import com._22evil.blog.service.IArticleService;
import com._22evil.module_cache.mysql.service.GenericMySqlService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ArticleServiceImpl implements IArticleService{
    @Autowired
    private GenericMySqlService genericMySqlService;

    public JSONObject getArticle(int articleId) {
        Article article = genericMySqlService.get(Article.class, articleId);
        if (article == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        json.put("title", article.getTitle());
        json.put("content", article.getContent());
        json.put("time", article.getCreateDate());
        return json;
    }
}
