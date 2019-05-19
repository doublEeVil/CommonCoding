package com._22evil.test.reflect;

import com._22evil.test.reflect.cl.AA;
import com._22evil.test.reflect.cl.CC;
import com._22evil.test.reflect.cl.DDSuber;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * java 反射例子
 * 包括常用一些用法以及注意点
 */
public class RefLectDemo {

    ///////////////////////////////////////
    // 要点
    // 1. Constructor，Field, Method如果不可以访问（private属性），可以通过xxx.setAccessible(true)获得
    // 2. 反射中有大量getXXX,以及getDeclaredXXX方法，下面是区别
    //    getXXX(): 可以获取本类以及父类可以访问的字段或者方法，static, final或者public均可，private修饰的不可以访问
    //    getDeclaredXXX(): 获取本类所有变量、常量或者方法，无论是private还是public修饰，但不可以获取父类属性，包括父类的public修饰的字段或者方法

    /**
     * 1. 构造方法
     */
    @Test
    public void testConstructors() {
        // 【1】默认构造函数
        // 普通类，内部类，方法内部类都不太一样
        // 普通类默认是无参数，内部类默认参数是外部类实例，方法内部类同样如此
        class AAMIn {
            void say() {
                System.out.println("222 方法内部类");
            }
        }

        try {
            // 【1.1】 普通类 无参
            System.out.println("==========普通无参构造函数==========");
            Class<?> clazz = Class.forName("com._22evil.test.reflect.cl.AA");
            AA aa = (AA) clazz.newInstance(); // 等同于默认构造函数
            aa.say();
            Constructor<?>[] constructors = clazz.getConstructors();
            printConstructor(constructors);

            // 【1.2】 普通类，多参数
            System.out.println("==========普通多参构造函数==========");
            clazz = Class.forName("com._22evil.test.reflect.cl.BB");
            // BB bb = (BB) clazz.newInstance(); // 这里调用必然报错
            // bb.say();
            constructors = clazz.getConstructors();
            printConstructor(constructors);

            // 【1.3】 私有构造方法情况
            System.out.println("==========私有构造函数==========");
            clazz = Class.forName("com._22evil.test.reflect.cl.CC");
            // CC cc = (CC) clazz.newInstance(); // 这里调用必然报错
            constructors = clazz.getConstructors();
            printConstructor(constructors);
            // 对于私有，其实可以访问
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            CC cc = (CC)constructor.newInstance(); // 生成实例
            System.out.println(cc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 【2】 内部类情况
        try {
            // 【2.1】 普通内部类
            System.out.println("==========普通内部类==========");
            Class<?> clazz = Class.forName("com._22evil.test.reflect.cl.AA$AAIn");
            printConstructor(clazz.getConstructors());  //没有构造方法
            // Object obj = clazz.newInstance();   //生成实例， 必然报错
            // System.out.println(obj);
            // 找到构造方法
            printConstructor(clazz.getDeclaredConstructors());  //可以明显看到，构造函数的默认参数就是外部类的实例

            // 【2.2】 方法内部类
            System.out.println("==========方法内部类==========");
            clazz = Class.forName("com._22evil.test.reflect.RefLectDemo$1AAMIn");
            printConstructor(clazz.getConstructors());  // 可以明显看到，构造函数的默认参数就是外部类的实例
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printConstructor(Constructor<?>[] constructors) {
        for (Constructor<?> constructor : constructors) {
            System.out.println("构造函数参数个数：" + constructor.getParameterCount());
            System.out.println("参数类型如下：");
            for (Class<?> type : constructor.getParameterTypes()) {
                System.out.println(type);
            }
            System.out.println("---------------");
        }
    }

    /**
     * 2. 变量，常量
     */
    @Test
    public void testField() {
        // 【1】 两种获取方法的区别，一种可以获取父类本类可访问字段，一种是本类所有字段
        Class<?> clazz = DDSuber.class;
        Field[] fields = clazz.getFields();
        System.out.println("====getFields()如下");
        printField(fields);
        System.out.println("====getDeclaredFields()如下");
        printField(clazz.getDeclaredFields());


        try {
            DDSuber obj = (DDSuber)clazz.newInstance();
            // 【2】 获取普通属性，赋值
            System.out.println("======获取普通属性，赋值======");
            Field field = clazz.getField("name");
            field.set(obj, "zhujinsahn");
            System.out.println(obj.name);
            System.out.println(field.get(obj));

            // 【3】 获取私有属性，赋值
            System.out.println("======获取私有属性，赋值======");
            field = clazz.getDeclaredField("age");  // 私有的，必须用getDeclaredXXX
            field.setAccessible(true);
            field.set(obj, 12);
            System.out.println(field.get(obj));

            // 【4】 获取静态类属性，赋值
            System.out.println("======获取静态类属性，赋值======");
            field = clazz.getField("cnt");
            System.out.println(field.get(null));
            System.out.println(field.get(1));
            System.out.println(field.get(""));      // 由于类变量属于整个类，这里的参数传任意值都可以得到正确的结果

            // 【5】 获得常量
            System.out.println("======获取常量属性，赋值======");
            field = clazz.getField("MAX");
            System.out.println(field.get(null));    // 常量同样属于整个类，且不可变，这里传任意参数都可以
            System.out.println(field.get(1));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void printField(Field[] fields) {
        for (Field field : fields) {
            System.out.println("变量名：" + field.getName() + " 变量类型：" + field.getType());
        }
    }

    /**
     * 3. 方法，和method极其相似
     */
    @Test
    public void testMethod() {

        Class<?> clazz = DDSuber.class;
        try {
            DDSuber obj = (DDSuber) clazz.newInstance();
            //【1】 调用普通方法，无返回
            System.out.println("=====调用普通方法，无返回=====");
            Method method = clazz.getMethod("f3");
            Object ret = method.invoke(obj);
            System.out.println(ret);    // 无返回方法，则返回null

            //【2】 调用普通方法，有返回
            System.out.println("=====调用普通方法，有返回=====");
            method = clazz.getDeclaredMethod("m3", int.class, int.class);
            method.setAccessible(true);
            int retI = (int) method.invoke(obj, 12, 13);
            System.out.println(retI);

            //【3】 调用静态方法
            System.out.println("=====调用静态方法=====");
            method = clazz.getMethod("f1");
            method.invoke(null);
            method.invoke("");
            method.invoke(1);   //静态方法属于类，因此，传任何参数都是可以的
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
