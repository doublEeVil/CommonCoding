package com._22evil.blog.service.impl;
import com._22evil.blog.common.GlobalData;
import com._22evil.blog.entity.Article;
import com._22evil.blog.service.IArticleService;
import com._22evil.module_cache.mysql.service.GenericMySqlService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService{
    @Autowired
    private GenericMySqlService genericMySqlService;

    private static final int PAGE_SIZE = 6;
    @Override
    public void onStart() {
        int count = genericMySqlService.countBySql("select count(*) from tb_article");
        GlobalData.getInstance().setArticleCount(count);
    }

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
        json.put("tags", article.getTags());
        return json;
    }

    @Override
    public JSONObject getIndex(int page) {
        // List<Article> articleList = genericMySqlService.getAllByHql("from Article ");
        int start = page * PAGE_SIZE;
        int end = page + PAGE_SIZE;
        List<Article> articleList = genericMySqlService.getBySql(Article.class, "select * from tb_article limit " + start + "," + end);
        JSONObject json = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        String content;
        Collections.sort(articleList, (o1, o2) -> (int)(o2.getCreateDate()/1000 - o1.getCreateDate()/1000));
        for (Article article : articleList) {
            JSONObject tmp = new JSONObject();
            tmp.put("articleId", article.getId());
            tmp.put("title", article.getTitle());
            tmp.put("time", new Date(article.getCreateDate()).toLocaleString());
            tmp.put("type", article.getType());
            content =  article.getContent();
            if (content.length() > 50) {
                tmp.put("content", content.substring(0, 50));
                //tmp.put("content", content);
            } else {
                tmp.put("content", content);
            }
            list.add(tmp);
        }
        json.put("title", "南风斜冷");
        json.put("articleList", list);
        json.put("allSize", GlobalData.getInstance().getArticleCount());
        json.put("pageNum", page + 1);
        json.put("pageSize", PAGE_SIZE);
        return json;
    }

    @Override
    public JSONObject getIndex(String keyword) {
        //TODO 直接用locate函数(暂时不做)
        int page = 0;
        int start = page * PAGE_SIZE;
        int end = page + PAGE_SIZE;
        List<Article> articleList = genericMySqlService.getBySql(Article.class, "select * from tb_article limit " + start + "," + end);

        JSONObject json = new JSONObject();
        List<JSONObject> list = new ArrayList<>();
        String content;
        Collections.sort(articleList, (o1, o2) -> (int)(o2.getCreateDate()/1000 - o1.getCreateDate()/1000));
        for (Article article : articleList) {
            JSONObject tmp = new JSONObject();
            tmp.put("articleId", article.getId());
            tmp.put("title", article.getTitle());
            tmp.put("time", new Date(article.getCreateDate()).toLocaleString());
            tmp.put("type", article.getType());
            content =  article.getContent();
            if (content.length() > 50) {
                tmp.put("content", content.substring(0, 50));
                //tmp.put("content", content);
            } else {
                tmp.put("content", content);
            }
            list.add(tmp);
        }
        json.put("title", "南风斜冷");
        json.put("articleList", list);
        json.put("allSize", GlobalData.getInstance().getArticleCount());
        json.put("pageNum", page + 1);
        json.put("pageSize", PAGE_SIZE);
        return json;
    }

    @Override
    public int addArticle(String title, String type, String tags, String status, String content) {
        try {
            Article article = new Article();
            article.setTitle(title);
            article.setType(type);
            article.setTags(tags);
            article.setStatus(status);
            article.setContent(content);
            article.setCreateDate(System.currentTimeMillis());
            article.setUpdateDate(System.currentTimeMillis());
            genericMySqlService.save(article);
            GlobalData.getInstance().addArticleCount();
            return article.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void editArticle(String articleId, String title, String tags, String status, String content) {
        Article article = genericMySqlService.get(Article.class, Integer.valueOf(articleId));
        if (article == null) {
            return;
        }
        if (title != null) article.setTitle(title);
        if (tags != null) article.setTags(tags);
        if (status != null) article.setStatus(status);
        if (content != null) article.setContent(content);
        article.setUpdateDate(System.currentTimeMillis());
        genericMySqlService.update(article);
    }

    public JSONObject getAllTitle() {
        List<Article> list = genericMySqlService.getAll(Article.class);
        JSONObject json = new JSONObject();
        List<JSONObject> titleList = new ArrayList<>();
        for (Article article : list) {
            JSONObject tmp = new JSONObject();
            tmp.put("articleId", article.getId());
            tmp.put("title", article.getTitle());
            tmp.put("type", article.getType());
            titleList.add(tmp);
        }
        json.put("list", titleList);
        return json;
    }
}
