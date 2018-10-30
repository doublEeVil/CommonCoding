package com._22evil.blog.service;
import com.alibaba.fastjson.JSONObject;
public interface IArticleService {
    /**
     * 根据id 获取具体文章
     * @param articleId
     * @return
     */
    JSONObject getArticle(int articleId);

    /**
     * 获取首页信息
     * @return
     */
    JSONObject getIndex();

    /**
     * 增加文章
     * @param title
     * @param type
     * @param tags
     * @param status
     * @param content
     */
    int addArticle(String title, String type, String tags, String status, String content);

    /**
     * 编辑文章
     * @param articleId
     * @param title
     * @param tags
     * @param status
     * @param content
     */
    void editArticle(String articleId, String title, String tags, String status, String content);

    /**
     * 获得所有文章标题
     * @return
     */
    JSONObject getAllTitle();
}
