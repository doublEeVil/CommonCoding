package com._22evil.blog.service.impl;
import com._22evil.blog.entity.Article;
import com._22evil.blog.service.IArticleService;
import com._22evil.module_cache.mysql.service.GenericMySqlService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        json.put("time", new Date(article.getCreateDate()).toLocaleString());
        json.put("type", article.getType());
        return json;
    }

    @Override
    public JSONObject getIndex() {
        List<Article> articleList = genericMySqlService.getAllByHql("from Article ");
        JSONObject json = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        for (Article article : articleList) {
            JSONObject tmp = new JSONObject();
            tmp.put("articleId", article.getId());
            tmp.put("title", article.getTitle());
            tmp.put("time", new Date(article.getCreateDate()).toLocaleString());
            tmp.put("type", article.getType());
            tmp.put("content", article.getContent().substring(0, 100));
            list.add(tmp);
        }
        json.put("title", "南风斜冷");
        json.put("articleList", list);
        return json;
    }
}
