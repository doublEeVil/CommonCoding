package com._22evil.test.jvm;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * 说一下JVM相关：
 * 包括：
 *      （1）内存模型
 *      （2）垃圾回收
 *      （3）类加载机制
 *
 * 内存模型：
 *      线程共享模块：
 *              方法区：包含类信息,常量，静态变量，JIT编译后的代码，运行时常量池
 *              堆区：GC堆
 *      线程独有模块：
 *              线程栈：Java 方法执行的内存结构，虚拟机会在每个 Java 方法执行时创建一个“栈桢”，用于存储局部变量表，操作数栈，动态链接，方法出口等信息
 *              本地方法：
 *              程序计数器：
 *
 * 堆区：
 *      （1）新生代（包括 Eden, From Survivor, TO Survivor, 比例是8:1:1）
 *      （2）老年代（经过多次GC残留下来的对象）
 *      （3）永久代（就是方法区, java规定可以对方法区垃圾回收，但JVM都不会去实现这个功能）
 *
 * 判断垃圾是否可以回收算法：
 *      （1）引用计数方法，引用+1，失效-1
 *      （2）可达性分析算法：如果可达，都不是需要回收的
 *          可用GC root
 *              1）虚拟机栈引用的对象
 *              2）方法区静态属性，常量等引用的对象
 *              3）JNI引用的对象
 *
 * 引用类型：
 *      （1）强引用 直接new的对象， 被强引用关联的对象不会被回收
 *      （2）软引用 被软引用关联的对象只有在内存不够的情况下才会被回收
 *              Object obj = new Object();
                SoftReference<Object> sf = new SoftReference<Object>(obj);
                obj = null;  // 使对象只被软引用关联
 *      （3）弱引用 被弱引用关联的对象一定会被回收，也就是说它只能存活到下一次垃圾回收发生之前
 *                  Object obj = new Object();
                    WeakReference<Object> wf = new WeakReference<Object>(obj);
                    obj = null;
 *      （4）虚引用
 *
 * 垃圾回收算法：
 * 1. 标记-清除 （老年代使用） 不整理内存空间，内存不连续
 * 2. 标记-整理 （老年代使用） 整理整个空间，整理后内存连续
 * 3. 复制-回收 （新生代使用） 整理半个空间，直接复制有用对象，内存连续
 *
 * 类加载机制：
 * 加载过程：加载-验证-准备-解析-初始化-使用-卸载
 *
 * 对于A类 static int age = 123, 准备阶段是0，初始化阶段才是123
 *
 * 类加载器：
 *      （1）启动类加载器Bootstrap Class Loader c++实现，主要是加载rt.jar
 *      （2）扩展类加载器：Extension ClassLoader，它负责将 <JAVA_HOME>/lib/ext 或者被 java.ext.dir 系统变量所指定路径中的所有类库加载到内存中，开发者可以直接使用扩展类加载器
 *      （3）它负责加载用户类路径（ClassPath）上所指定的类库
 *
 * 双亲委派机制：主要是为了避免重复加载的问题
 * 如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器去完成，每一个层次的加载器都是如此
 */
public class TestJvm {

    @Test
    public void testClassLoader() {
        // 通过上面了解
        String s = "abc";
        // 结果是NULL
        System.out.println("rt.jar类下加载器：" + s.getClass().getClassLoader());

        // Launcher$AppClassLoader
        com.alibaba.fastjson.JSONObject jsonObject = new JSONObject();
        System.out.println("ext class loader: " + jsonObject.getClass().getClassLoader());

        // Launcher$AppClassLoader
        TestJvm jvm = new TestJvm();
        System.out.println("自己的application class loader: " + jvm.getClass().getClassLoader());

    }
}
