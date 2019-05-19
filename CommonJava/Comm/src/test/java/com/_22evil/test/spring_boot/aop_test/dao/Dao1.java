package com._22evil.test.spring_boot.aop_test.dao;

import org.springframework.stereotype.Repository;

@Repository
public class Dao1 {
    public String retrieveSomething(){
		return "Dao1";
	}
}