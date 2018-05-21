package com._22evil.third_data;

import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;

/**
 * 金价查询测试
 * 使用第三方接口 聚合数据
 * 接口有使用次数限制1天100次，大概平均15分钟一次
 */
public class GoldPriceTest {
    public static void main(String[] args) {
        // String url = "http://web.juhe.cn:8080/finance/gold/shgold?key=b815fef371cbac0262038e76737323e0";
        // HttpUtil.get(url, new HttpCallback() {

        //     public void fail(String errorMsg) {
        //         System.out.println("数据查询失败：" + errorMsg);
        //     }

        //     public void success(int code, String data) {
        //         System.out.println("数据请求成功：" + data);
        //     }
        // });

        String data;
    }
}