package com._22evil.test.concurrent;

import org.junit.Test;

/**
 * 测试ThreadLocal用法
 *
 * TheadLocal下的主要是ThreadLocalMap ， 弱引用
 * ThreadLocal读取的数据和其他线程无关
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> localString = new ThreadLocal<>();

    @Test
    public void test() {
        new Thread(()->{
            try {
                Thread.sleep(1223);
            } catch (InterruptedException e) {

            }

            System.out.println("111 " + getName()); // 打印NAME
        }).start();

        new Thread(()->{
            System.out.println("222 " + getName()); // 打印NAME
        }).start();

        new Thread(()->{
            setName();
            System.out.println("333 " + getName()); // 打印 Thread-2， 说明Thread间是变量拷贝
        }).start();

        try {
            Thread.sleep(2333);
        } catch (InterruptedException e) {

        }
    }

    public String getName() {
        String name = localString.get();
        if (name == null) {
            name = "NAME";
            localString.set(name);
        }
        return name;
    }

    public void setName() {
        localString.set(Thread.currentThread().getName());
    }

    /**
     * 典型用法
     *
     */
    public void usage() {
//        private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
//
//        //获取Session
//        public static Session getCurrentSession(){
//            Session session =  threadLocal.get();
//            //判断Session是否为空，如果为空，将创建一个session，并设置到本地线程变量中
//            try {
//                if(session ==null&&!session.isOpen()){
//                    if(sessionFactory==null){
//                        rbuildSessionFactory();// 创建Hibernate的SessionFactory
//                    }else{
//                        session = sessionFactory.openSession();
//                    }
//                }
//                threadLocal.set(session);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//            return session;
//        }
    }
}
