package com._22evil.stone;

import java.util.HashMap;
import java.util.Map;

public class BasicEnv implements Environment {
    protected Map<String, Object> values;

    public BasicEnv() {
        values = new HashMap<>();
    }


    @Override
    public void put(String name, Object val) {
        values.put(name, val);
    }

    @Override
    public Object get(String name) {
        return values.get(name);
    }
}
