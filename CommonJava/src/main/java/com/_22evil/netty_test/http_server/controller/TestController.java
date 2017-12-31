package com._22evil.netty_test.http_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("/abc")
    public @ResponseBody String abc() {
        return "abc";
    }
}
