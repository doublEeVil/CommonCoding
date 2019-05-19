package com._22evil.test.event.service;

import com._22evil.event.Event;

import org.springframework.stereotype.Service;

@Service("testAService")
public class TestAService {
    @Event(eventId = 111)
    public void onEvent111(String a, String b) {
        System.out.println("===rcv event id 111 " + a + " " + b);
    }

    @Event(eventId = 222)
    public void onEvent222(double num) {
        System.out.println("===rcv event id 222 " + num);
    }
}