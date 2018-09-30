package com._22evil.test.aop.aspect;

import com._22evil.test.aop.Before;

public class TripAspect {

    @Before(execution = "com._22evil.test.aop.service.impl.*Impl.*()")
    public void beforeEat(Object[] args) {
        System.out.println("=== 吃饭前, 检测钱是否足够");
    }

    @Before(execution = "com._22evil.test.aop.service.impl.*Impl.*()")
    public void afterEat(Object[] args) {
        System.out.println("=== 吃饭后, 检测钱剩余多少");
    }
}
