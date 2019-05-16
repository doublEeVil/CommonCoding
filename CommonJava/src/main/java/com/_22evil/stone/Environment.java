package com._22evil.stone;

public interface Environment {
    void put(String name, Object val);
    Object get(String name);
}
