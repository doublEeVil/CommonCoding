package com._22evil.netty_test.http_server_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TestController {

    @RequestMapping("/abc")
    public @ResponseBody String abc() {
        return "abc";
    }

    @RequestMapping("/hello")
    public @ResponseBody String hello() {
        return "hello";
    }

    @RequestMapping("/abc/{name}")
    public @ResponseBody
    String abc(@PathVariable String name) {
        return name + new Date().toString();
    }

    @RequestMapping("/a")
    public @ResponseBody
    String a(HttpServletRequest request) {
        String name = request.getParameter("name");
        System.out.println(name);
        String str = "hhh heloo ww" + name;
        return str;
    }

    @RequestMapping("/b")
    public @ResponseBody
    String b() {
        return "this is b";
    }

    @RequestMapping("/c")
    public @ResponseBody
    String c() {
        return "this is c";
    }
}
