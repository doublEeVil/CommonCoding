package com._22evil.test.spring_boot.aop_test.service;

import com._22evil.test.spring_boot.aop_test.dao.Dao2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Business2 {

    @Autowired
    private Dao2 dao2;
    
    public String calculateSomething() {
        return dao2.retrieveSomething();
    }
}