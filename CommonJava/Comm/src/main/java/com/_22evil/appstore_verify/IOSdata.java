package com._22evil.appstore_verify;

import com.alibaba.fastjson.JSONObject;

class IOSdata {
    public String playerId;
    public String key;
    public com.alibaba.fastjson.JSONObject json;

    public IOSdata(String playerId, String key) {
        this.playerId = playerId;
        this.key = key;
    }

    public IOSdata(String playerId, String key, com.alibaba.fastjson.JSONObject json) {
        this.playerId = playerId;
        this.key = key;
        this.json = json;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
