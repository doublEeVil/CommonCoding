package com._22evil.blog.service;
import com.alibaba.fastjson.JSONObject;
public interface IArticleService {
    /**
     * 根据id 获取具体文章
     * @param articleId
     * @return
     */
    JSONObject getArticle(int articleId);
}
