package com._22evil.test.spring_boot.aop_test.service;

import com._22evil.test.spring_boot.aop_test.dao.Dao1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Business1 {

	@Autowired
	private Dao1 dao1;
    public String calculateSomething() {
        String value = dao1.retrieveSomething();
        System.out.printf("In Business - {%s}" , value);
		return value;
    }
}