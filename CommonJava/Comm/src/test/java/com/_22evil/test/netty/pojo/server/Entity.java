package com._22evil.test.netty.pojo.server;

public class Entity {
    private int id;
    private int age;
    private String name;
    private String addr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Entity(int id) {
        this.id = id;
        this.age = 0;
        this.name = "";
        this.addr = "";
    }

    public String toString() {
        return "id: " + id + " age: " + age + " name:" + name + " addr:" + addr;
    }
}
