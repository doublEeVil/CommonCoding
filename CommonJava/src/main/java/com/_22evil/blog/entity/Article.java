package com._22evil.blog.entity;
import com._22evil.module_cache.mysql.BaseIdAutoIncEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "tb_article")
@Data
public class Article extends BaseIdAutoIncEntity{

    @NonNull
    @Column(name = "title")
    private String title;   // 标题
    @NonNull
    @Column(name = "content")
    private String content; // 具体内容
    @NonNull
    @Column(name = "type")
    private String type;    // 文本类型 html or markdown
    @Column(name = "tags")
    private String tags;    // 标签, 用英文','分割
    @Column(name = "status")
    private String status;  // 状态 publish: 公开 private: 私有/草稿
}
