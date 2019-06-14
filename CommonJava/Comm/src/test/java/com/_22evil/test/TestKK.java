package com._22evil.test;

import org.json.JSONObject;
import org.junit.Test;

public class TestKK {

    @Test
    public void test2() {
        String s = "{1:1,2:1,1:1}";
        JSONObject object = new JSONObject(s);
        System.out.println(object);
    }
}
