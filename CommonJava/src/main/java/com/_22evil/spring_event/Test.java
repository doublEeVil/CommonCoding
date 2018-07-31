package com._22evil.spring_event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Test {
    public static void main(String[] args) {
        System.out.println("==== test ====");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        EventPublisher.getPublisher().publishEvent(new MyEvent(new Test(), 1, new Object[]{123, "dd"}));
    }
}