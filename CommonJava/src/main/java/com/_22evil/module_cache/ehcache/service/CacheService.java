package com._22evil.module_cache.ehcache.service;

import java.util.Map;

import com._22evil.module_cache.mysql.BaseEntity;

public interface CacheService {

    void buildCache(String basePackage);

    void loadAllConfigEntity();

    <T> T get(Class<T> clazz, int id);

    <T> Map<Integer, T> getAll(Class<T> clazz);

    void update(BaseEntity entity);

    <T extends BaseEntity> T saveToDB(T entity);

    void updateToDB(BaseEntity entity);
}
