package com._22evil.test.hibernate;
import com._22evil.db.HibernateUtil;
import com._22evil.test.hibernate.entity.TestEntityC;

public class TestHibernate {

    public static void main(String ... args) {
        HibernateUtil util = new HibernateUtil("com._22evil.test.hibernate.entity");
        TestEntityC c = new TestEntityC();
        c.setId(4);
        c.setAge(12);
        c.setName("aaa");
        util.add(c);
    }

}
