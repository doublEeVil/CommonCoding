package com._22evil.test.datasee;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DataSee {

    public static void main(String ... args) {
        new DataSee().see();
    }

    private void see() {
        // 1. weibo 热点
        HttpUtil.get("https://s.weibo.com/top/summary", new HttpCallback() {
            @Override
            public void fail(String errorMsg) {
                System.out.println("!!!获取微博热点失败");
            }

            @Override
            public void success(int code, String data) {
                System.out.println(data);
                Pattern ptn = Pattern.compile("<[\\u4E00-\\u9FA5]>");
                Matcher matcher = ptn.matcher(data);
                while (matcher.find()) {
                    System.err.println(matcher.group());
                }
            }
        });
    }
}
