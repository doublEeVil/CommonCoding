package com._22evil.test.javassist;
import com._22evil.classloader.SimpleClassLoader;
import javassist.*;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * 利用 javassist包动态生成java代码
 * 在一些动态生成的情景下十分有用
 */
public class JavaSsistTest {

    /**
     * 动态增加方法
     */
    @Test
    public void testAddMethod() {
        String className = "Obj";
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cc = pool.get(className);
            CtMethod mthd = CtNewMethod.make("public void say() {System.out.println(\"this is new method\");}", cc);
            cc.addMethod(mthd);
            SimpleClassLoader classLoader = SimpleClassLoader.getInstance();
            Class<?> clazz = classLoader.load(cc.toBytecode());
            Method method = clazz.getMethod("say");
            // 这里将得到结果，是否会打印新方法
            method.invoke(clazz.newInstance());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态增加变量
     */
    @Test
    public void testAddField() {
        String className = "Obj";
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass cc = pool.get(className);
            CtField field = new CtField(CtClass.intType, "age", cc);
            field.setModifiers(Modifier.PUBLIC);
            cc.addField(field, "15");
            SimpleClassLoader classLoader = SimpleClassLoader.getInstance();
            Class<?> clazz = classLoader.load(cc.toBytecode());
            Field field1 = clazz.getField("age");
            // 打印结果，得到变量名 age
            System.out.println(field1.get(clazz.newInstance()));
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态改变方法
     */
    @Test
    public void testReloadMethod() {
//        String className = "Obj";
//        ClassPool pool = ClassPool.getDefault();
//        try {
//            CtClass cc = pool.get(className);
//            CtMethod mthd = cc.getMethod("say", "String input");
//            mthd.setBody("System.out.println(\"===\") + input;");
//            SimpleClassLoader classLoader = SimpleClassLoader.getInstance();
//            Class<?> clazz = classLoader.load(cc.toBytecode());
//            Method method = clazz.getMethod("say", String.class);
//            // 这里将得到结果，是否会打印新方法
//            method.invoke(clazz.newInstance(), "hahaha");
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (CannotCompileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 动态计算 例如 1+2*6 + 9-0.3这样的算数表达式
     * 或者更复杂一点的 1.5+0.5*min(max(({5}-{106}),0)/(0+100*{116}),1) 这样的表达式
     */
    @Test
    public void testCalcuteString() {
        // 初始步骤，得到可用于计算的表达式
        // 第一种方法，直接将语句丢入js引擎中，并返回计算结果
        // 第二种方法，生成java语句，得到结果

        ///------------第一种方法----费时费力
        String input = "100 + max(50/24 + 1, 2)";
        input = getCode(input);
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            double val = (double)jse.eval(input);
            System.out.println("==== val: " + val);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    /**
     * 例如 max(2,12-3) 将转为 Math.max(2, 12 - 3)
     * @param input
     * @return
     */
    private String getCode(String input) {
        // 已知表达式运算符存在如下
        // 1. ( )
        // 2. + - * /
        // 3. min()
        // 4. max()
        // {}是占位符号
        StringBuffer out = new StringBuffer();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
            case '{':
                String param = "(double)param.get(\"";
                while (chars[i] != '}') {
                    param += chars[i++];
                }
                param += "}\")";
                out.append(param);
                break;
            case 'm':
                out.append("Math.m");
                break;
            default:
                out.append(c);
                break;
            }
        }
        return out.toString();
    }
}
