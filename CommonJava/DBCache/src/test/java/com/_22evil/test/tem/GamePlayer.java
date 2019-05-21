package com._22evil.test.tem;



import com.alibaba.fastjson.JSONObject;


public class GamePlayer  {
    private String extInfo;
    private JSONObject json;


    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
        json = JSONObject.parseObject(extInfo);
    }


    JSONObject getJSON() {
        return json;
    }


    public long getCreateTime() {
        return json.getLong("createTime");
    }


    public int getLevel() {
        return json.getInteger("level");
    }


    public long getLastLogout() {
        return json.getLong("logoutTime");
    }

    public int getAccountId() {
        return json.getIntValue("accountId");
    }

    public long getLoginTime() {
        return json.getLong("loginTime");
    }
}
