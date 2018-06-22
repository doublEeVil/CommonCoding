package com._22evil.spring_mysql;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration

@ImportResource(value = {"classpath*:/spring-jpa.xml"})
public class Test {
    public static void main(String[] args) {
        new TestApp().test();
    }
}