package com._22evil.util;

public interface HttpCallback {
    /**
     * 未发送请求回调
     * @param errorMsg
     */
    void fail(String errorMsg);

    /**
     * 发送请求后回调
     * @param code 返回码
     * @param data 原有数据
     */
    void success(int code, String data);
}
