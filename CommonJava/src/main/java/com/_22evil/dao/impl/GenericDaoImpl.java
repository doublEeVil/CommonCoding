package com._22evil.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.Collection;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import com._22evil.dao.GenericDao;
/**
 * Created by wangye on 2016/6/1.
 */

public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
    private static final Logger LOGGER = Logger
            .getLogger(GenericDaoImpl.class);
    @Autowired
    public SessionFactory sessionFactory;
    protected Class<T> entityClass;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }


    @Override
    public Session openSession() {
        return this.sessionFactory.openSession();
    }

    protected Class getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    @Override
    public ID save(T t) {
        Session session = this.getSession();
        ID id;
        id = (ID) session.save(t);
        //((BaseEntity)t).setIsUpdate(0);
        return id;
    }

    @Override
    public void merge(T t) {
        this.getSession().merge(t);
    }

    /**
     * <保存或者更新实体>
     *
     * @param t 实体
     * @see com.sprite.dao.GenericDao#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(T t) {
        this.getSession().saveOrUpdate(t);
    }

    /**
     * <load>
     * <加载实体的load方法>
     *
     * @param id 实体的id
     * @return 查询出来的实体
     * @see com.sprite.dao.GenericDao#load(java.io.Serializable)
     */
    @Override
    public T load(ID id) {
        T load = (T) this.getSession().load(getEntityClass(), id);
        return load;
    }

    /**
     * <get>
     * <查找的get方法>
     *
     * @param id 实体的id
     * @return 查询出来的实体
     * @see com.sprite.dao.GenericDao#get(java.io.Serializable)
     */
    @Override
    public T get(ID id) {
        Session session = this.getSession();
        //session.createSQLQuery("SET NAMES utf8mb4").executeUpdate();
        T load = (T) session.get(getEntityClass(), id);
        return load;
    }

    /**
     * <contains>
     *
     * @param t 实体
     * @return 是否包含
     * @see com.sprite.dao.GenericDao#contains(java.lang.Object)
     */
    @Override
    public boolean contains(T t) {
        return this.getSession().contains(t);
    }

    /**
     * <delete>
     * <删除表中的t数据>
     *
     * @param t 实体
     * @see com.sprite.dao.GenericDao#delete(java.lang.Object)
     */
    @Override
    public void delete(T t) {
        this.getSession().delete(t);
    }

    /**
     * <根据ID删除数据>
     *
     * @param Id 实体id
     * @return 是否删除成功
     * @see com.sprite.dao.GenericDao#deleteById(java.io.Serializable)
     */
    @Override
    public boolean deleteById(ID Id) {
        T t = get(Id);
        if (t == null) {
            return false;
        }
        delete(t);
        return true;
    }

    /**
     * <删除所有>
     *
     * @param entities 实体的Collection集合
     * @see com.sprite.dao.GenericDao#deleteAll(java.util.Collection)
     */
    @Override
    public void deleteAll(Collection<T> entities) {
        for (Object entity : entities) {
            this.getSession().delete(entity);
        }
    }

    /**
     * <update>
     *
     * @param t 实体
     * @see com.sprite.dao.GenericDao#update(java.lang.Object)
     */
    @Override
    public void update(T t) {
        Session session = this.getSession();
        // session.createSQLQuery("SET NAMES utf8mb4").executeUpdate();
        session.update(t);
        //LOGGER.debug(t.getClass().toString());
        //this.getSession().update(t);
    }

    /**
     * <执行Sql语句>
     *
     * @param sqlString sql
     * @param values    不定参数数组
     * @see com.sprite.dao.GenericDao#querySql(java.lang.String, java.lang.Object[])
     */
    @Override
    public void querySql(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    /**
     * <执行Hql语句>
     *
     * @param hqlString hql
     * @param values    不定参数数组
     * @see com.sprite.dao.GenericDao#queryHql(java.lang.String, java.lang.Object[])
     */
    @Override
    public void queryHql(String hqlString, Object... values) {
        Session session = this.getSession();
        Query query = session.createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    /**
     * <根据HQL语句查找唯一实体>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     * @see com.sprite.dao.GenericDao#getByHQL(java.lang.String, java.lang.Object[])
     */
    @Override
    public T getByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据SQL语句查找唯一实体>
     *
     * @param sqlString SQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     * @see com.sprite.dao.GenericDao#getBySQL(java.lang.String, java.lang.Object[])
     */
    @Override
    public T getBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString).addEntity(this.getEntityClass());
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据HQL语句，得到对应的list>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询多个实体的List集合
     * @see com.sprite.dao.GenericDao#getListByHQL(java.lang.String, java.lang.Object[])
     */
    @Override
    public List<T> getListByHQL(String hqlString, Object... values) {
        Session session = this.getSession();
        Query query = session.createQuery(hqlString);

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    @Override
    public List<T> getListByHQL2(String hqlString, Object... values) {
        Session session = this.openSession();
        List<T> list;
        try {
            Query query = session.createQuery(hqlString);

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }
            list = query.list();
        } finally {
            session.close();
        }

        return list;
    }

    /**
     * <根据SQL语句，得到对应的list>
     *
     * @param sqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询多个实体的List集合
     * @see com.sprite.dao.GenericDao#getListBySQL(java.lang.String, java.lang.Object[])
     */
    @Override
    public List<T> getListBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * <根据HQL得到记录数>
     *
     * @param hql    HQL语句
     * @param values 不定参数的Object数组
     * @return 记录总数
     * @see com.sprite.dao.GenericDao#countByHql(java.lang.String, java.lang.Object[])
     */
    @Override
    public Long countByHql(String hql, Object... values) {
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Long) query.uniqueResult();
    }

    @Override
    public void flush() {
        this.getSession().flush();
    }

}
