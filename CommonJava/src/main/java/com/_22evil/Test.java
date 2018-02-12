package com._22evil;

import com._22evil.util.DbUtil;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;

import java.util.*;

import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        try {

//            Map<Object, Object> map = new HashMap<>();
//            map.put("packageName", "com.wyd.gplay.heroibomba");
//            map.put("productId", "yido_item_en301");
//            map.put("token", "djekjcdkcgebphcnljibjodb.AO-J1OyrrmNoUiKeIeBPwyb1WvaqAJqeT3YZME3P5n3mLPR7TtL90aWsbOWlLAiYAgHO7lbUFkfbQ1tYwlBcF4VqiDrnF2ix-59uNtzwNq-zx96btZEtLpwwvTxPa-71d6Av_Qjemxj2");
//            JSONObject obj = new JSONObject(map);
//            HttpUtil.post("http://127.0.0.1:8080/wydpay/GooglePlayEuropeNotifyServlet",
//             obj.toString().getBytes(),
//             new HttpCallback(){
//
//                @Override
//                public void success(int code, String data) {
//                    System.out.println(data);
//                }
//
//                @Override
//                public void fail(String errorMsg) {
//                    System.out.println("---" + errorMsg);
//                }
//            });
            System.out.println("---" + send(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int send(int num) {

        try {
            if (num == 0) {
                return 1;
            }
            if (num < 0) {
                return 1;
            }
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1,2);
            map.put(2,3);
            map.forEach((k,v) -> {
                System.out.println("111");
                if (k == 1 && num < v) {
                    return;
                }
                System.out.println("222");
            });
            System.out.println("----");
            return 0;
        } catch (Exception e) {

        }
        return 1;
    }
}
