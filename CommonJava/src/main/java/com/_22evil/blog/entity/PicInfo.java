package com._22evil.blog.entity;

import com._22evil.module_cache.mysql.BaseIdAutoIncEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_pic_info")
public class PicInfo extends BaseIdAutoIncEntity {
    private String path;    //相对路径
    private String extInfo; //额外信息

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "ext_info")
    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
