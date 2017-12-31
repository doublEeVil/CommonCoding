package com._22evil;

import com._22evil.util.DbUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        try {
            List<Ab> abList = new ArrayList<>();
            abList.add(new Ab());
            abList.add(new Ab());
            Ab c = new Ab();
            Map<String, String> map = new HashMap<>();
            map.put("name", "zhujinshan");
            c.map = map;
            abList.add(c);
            //System.out.println(DbUtil.objectToJson(map));
            //DbUtil.writeToMongo(abList, "test");
            //DbUtil.writeToMysql(abList);
            DbUtil.readFromMysql(Ab.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
