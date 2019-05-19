package com._22evil.db;
import com._22evil.util.ClassUtil2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;
/**
 * Hibernate工具类
 * 脱落spring环境
 */
public class HibernateUtil {
    private static SessionFactory factory;

    public HibernateUtil(String basePackge) {
        Configuration configuration = new Configuration().configure();
        configuration.addPackage(basePackge);
        Set<Class> classSet = ClassUtil2.getClassSetWithAnnotation(basePackge, Entity.class);
        for (Class clazz : classSet) {
            configuration.addAnnotatedClass(clazz);
        }
        factory = configuration.buildSessionFactory();
    }


    public <T> void add(T c) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public <T> T find(Class<T> clazz, int id) {
        Session session = factory.openSession();
        try {
            T t = session.find(clazz, id);
            return t;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public <T> List<T> findAll(Class<T> clazz) {
        Session session = factory.openSession();

        try {
            List<T> list = session.createQuery("FROM " + clazz.getSimpleName()).list();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public <T> void update(T t) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public <T> void delete(T t) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }
}
