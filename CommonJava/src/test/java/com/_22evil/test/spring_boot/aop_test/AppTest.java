package com._22evil.test.spring_boot.aop_test;

import com._22evil.test.spring_boot.aop_test.dao.Dao1;
import com._22evil.test.spring_boot.aop_test.dao.Dao2;
import com._22evil.test.spring_boot.aop_test.service.Business1;
import com._22evil.test.spring_boot.aop_test.service.Business2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
// 不加下面这句，基本就没法测试
@ContextConfiguration(classes = {Business1.class, Business2.class, Dao1.class, Dao2.class})
public class AppTest {

	@Autowired
	private Business1 business1;

	@Autowired
	private Business2 business2;

	public void main(String[] args) {
		SpringApplication.run(AppTest.class, args);
	}

	@Test
	public void invokeAOPStuff() {
		System.out.println(business1.calculateSomething());
		System.out.println(business2.calculateSomething());
	}
}