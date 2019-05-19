package com._22evil.spring_event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestService {

    @EventListener
    public void testEvent(MyEvent event) {
        System.out.println("==== 收到事件==" + event + " " + event.getEventId());
    }
}