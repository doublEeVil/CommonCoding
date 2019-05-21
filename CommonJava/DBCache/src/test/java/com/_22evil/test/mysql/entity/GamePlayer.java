package com._22evil.test.mysql.entity;

import com._22evil.module_cache.mysql.BaseIdAutoIncEntity;
import com.alibaba.fastjson.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "game_player")
@Entity
public class GamePlayer extends BaseIdAutoIncEntity {
    private String extInfo;
    private JSONObject json;

    @Column(name = "ext_field")
    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
        json = JSONObject.parseObject(extInfo);
    }

    @Transient
    JSONObject getJSON() {
        return json;
    }

    @Transient
    public long getCreateTime() {
        return json.getLong("createTime");
    }

    @Transient
    public int getLevel() {
        return json.getInteger("level");
    }

    @Transient
    public long getLastLogout() {
        return json.getLong("logoutTime");
    }

    @Transient
    public int getAccountId() {
        return json.getIntValue("accountId");
    }

    @Transient
    public long getLoginTime() {
        return json.getLong("loginTime");
    }
}
