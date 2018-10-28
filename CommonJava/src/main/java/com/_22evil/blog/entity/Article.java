package com._22evil.blog.entity;
import com._22evil.module_cache.mysql.BaseIdAutoIncEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "tb_article")
public class Article extends BaseIdAutoIncEntity {
    private String title;   // 标题
    private String content; // 具体内容
    private String type;    // 文本类型 html or markdown
    private String tags;    // 标签, 用英文','分割
    private String status;  // 状态 publish: 公开 private: 私有/草稿

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content", columnDefinition = "mediumtext")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
