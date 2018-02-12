package com._22evil;

import com._22evil.db.BaseEntity;

import java.util.Map;

/**
 * Created by shangguyi on 24/12/2017.
 */
public class Ab extends BaseEntity {
    public int age = 0;
    public String name = "sd";
    private int cnt = 0;
    private long create;

    public Ab() {
        this.create = System.currentTimeMillis();
        System.out.println("****ctor  used");
    }
    public Map<String, String> map;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public long getCreate() {
        return create;
    }
}
