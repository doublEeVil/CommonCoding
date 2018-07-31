package com._22evil.test.event;

import com._22evil.event.EventManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-event.xml")
public class TestEvent {

    @Autowired 
    private ApplicationContext context;

    @Test
    public void testEvent() {
        System.out.println("=== start");
        EventManager.getInstance().initEvent(context);
        EventManager.getInstance().eventExecute(111, "aaaa", "bbbb");
        EventManager.getInstance().eventExecute(222, 12.3456789);
        System.out.println("=== end");
    }
}