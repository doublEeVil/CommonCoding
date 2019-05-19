package com._22evil.test.aop.service.impl;

import com._22evil.test.aop.entity.Person;
import com._22evil.test.aop.service.TripService;

public class TripServiceImpl implements TripService {

    @Override
    public String eat(Person person) {
        System.out.println("====eat, left money:" + person.getMoney());
        return "happy";
    }
}
