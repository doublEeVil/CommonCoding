package com._22evil.test.grammar;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;

/**
 * java集合类
 * 1.
 */
public class TestCollections {

    /**
     * 不存在WeakHashSet
     */
    @Test
    public void testWeekHashSet() {
        Map<String, Boolean> map = new WeakHashMap<>();
        Collections.newSetFromMap(map);
    }

    @Test
    public void testUnmodifiedMap() {
        Map map = Collections.unmodifiableMap(new HashMap<>());
        try {
            map.put("", "");
        } catch (Exception e) {
            System.out.println("====不可以添加数据");
        }
    }
}
