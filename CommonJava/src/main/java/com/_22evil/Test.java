package com._22evil;

import com._22evil.util.DbUtil;
import com._22evil.util.HttpCallback;
import com._22evil.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        try {

            Map<Object, Object> map = new HashMap<>();
            map.put("packageName", "com.wyd.gplay.bombheroesen");
            map.put("productId", "yido_item_en002");
            map.put("token", "hhkpnjlhgnoohdpcfklcgooa.AO-J1OyhaxTqXHutgm7brHWTKuP_wBpsxArgsZdU7jGUjZIA8ZwvNCRG6BjvGHzysL4mylhvKvVwlDb0zamkuvc05ZRHPMYEZn3DIIfKonk7ByRUZUP-3DE1jiZOMpkUjDCClGgzDU8b");
            JSONObject obj = new JSONObject(map);
            HttpUtil.post("http://127.0.0.1:8080/wydpay/GooglePlayEuropeNotifyServlet",
             obj.toString().getBytes(), 
             new HttpCallback(){
            
                @Override
                public void success(int code, String data) {
                    System.out.println(data);
                }
            
                @Override
                public void fail(String errorMsg) {
                    
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
