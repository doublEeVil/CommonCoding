package com._22evil.module_cache.mysql.service;

import java.util.List;

import com._22evil.module_cache.mysql.BaseEntity;
import com._22evil.module_cache.mysql.BaseIdAutoIncEntity;

public interface GenericMySqlService {
    /**
     * 保存
     * @param t
     */
    void save(BaseEntity t);

    /**
     *
     * @param t
     */
    void save(BaseIdAutoIncEntity t);

    /**
     * 查找
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T get(Class<T> clazz, int id);

    /**
     * 特殊查询
     * @param hql
     * @param params
     * @param <T>
     * @return
     */
    <T> T getByHql(String hql, Object ... params);

    /**
     * 查找全部
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getAll(Class<T> clazz);

    /**
     *
     * @param hql
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> getAllByHql(String hql, Object ... params);

    /**
     * update
     * @param t
     */
    void update(BaseEntity t);

    /**
     *
     * @param t
     */
    void update(BaseIdAutoIncEntity t);

    /**
     * 删除
     * @param t
     */
    void delete(BaseEntity t);

    /**
     *
     * @param t
     */
    void delete(BaseIdAutoIncEntity t);

    /**
     *
     */
    void flush();

    /**
     *
     * @param <T>
     * @return
     */
    <T> List<T> getBySql(Class<T> clazz, String sql);

    /**
     * 查询数目
     * @param sql
     * @param <T>
     * @return
     */
    <T> int countBySql(String sql);

    /**
     *
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> getBySql(String sql, Object ... params);
}
