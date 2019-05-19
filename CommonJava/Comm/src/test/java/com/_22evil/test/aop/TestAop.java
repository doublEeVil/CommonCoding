package com._22evil.test.aop;

import com._22evil.test.aop.entity.Person;
import com._22evil.test.aop.service.TripService;
import com._22evil.test.aop.service.impl.TripServiceImpl;
import org.junit.Test;

public class TestAop {

    @Test
    public void test1() {
        GenericProxy proxy = new GenericProxy();
        TripService s = (TripService) proxy.bind(new TripServiceImpl());

        Person p = new Person();
        p.setMoney(123);
        s.eat(p);
    }
}
