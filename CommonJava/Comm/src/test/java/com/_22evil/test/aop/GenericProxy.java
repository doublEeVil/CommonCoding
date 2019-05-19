package com._22evil.test.aop;

import com._22evil.test.aop.aspect.TripAspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GenericProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // aop before
        System.out.println("===aop before");

        // 不用java 反射了
        new TripAspect().beforeEat(args);

        // action
        Object ret = method.invoke(target, args);

        // aop after
        System.out.println("===aop after");

        // 不用java 反射了
        new TripAspect().afterEat(args);
        return ret;
    }
}
