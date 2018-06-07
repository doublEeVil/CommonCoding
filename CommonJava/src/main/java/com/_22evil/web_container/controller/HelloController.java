package com._22evil.web_container.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import freemarker.template.Template;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import org.springframework.web.bind.annotation.PathVariable;
import com._22evil.web_container.global.Global;

@Controller("/a")
public class HelloController {

    @RequestMapping(value = "abcd",  produces = "text/html;charset=UTF-8") //方法1 强制设置为UTF-8，（spring 默认编码不是这个）
    public @ResponseBody String abc() {
        return "abc你好啊";
    }

    @RequestMapping("123")
    public @ResponseBody String abc1() {
        return "a123你好";
    }


    @RequestMapping("/hello1")
    public @ResponseBody String hello() {
        return "hello";
    }

    @RequestMapping("/abc1/{name}")
    public @ResponseBody
    String abc(@PathVariable String name) {
        return name + new Date().toString();
    }

    @RequestMapping("/a1")
    public @ResponseBody
    String a(HttpServletRequest request) {
        String name = request.getParameter("name");
        System.out.println(name);
        String str = "hhh heloo ww" + name;
        return str;
    }

    @RequestMapping("/b1")
    public @ResponseBody
    String b() {
        return "this is b";
    }

    @RequestMapping("/c1")
    public @ResponseBody
    String c() {
        return "this is c";
    }

    @RequestMapping(value = "/testabc", produces = "text/html;charset=UTF-8")
    public  void testabc(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.err.println("-----testabc----");
            Map<String, Object> root = new HashMap<>();
            root.put("username", "zhujisna");
            root.put("age", 25);
            // Stu stu = new Stu();
            // stu.setAddr("GZ");
            // stu.setTel("13467952"); // 方法不可行

            Map<String, Object> stu = new HashMap<>();
            stu.put("addr", "GZ");
            stu.put("tel", "1186459513");
            root.put("stu", stu);

            response.setCharacterEncoding("UTF-8"); //不加入这句，中文就是?显示
            Template temp = Global.getFreeMarkerConfig().getTemplate("testabc.html");
            temp.process(root, response.getWriter()); // 此时得到的便是testabc.html内容
            
            response.setContentType("text/html; charset=" + temp.getEncoding()); //中文乱码问题
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Stu {
        String tel;
        String addr;

        public void setTel(String val) {
            this.tel = val;
        }

        public void setAddr(String val) {
            this.addr = val;
        }

        public String getTel() {
            return tel;
        }

        public String getAddr() {
            return addr;
        }
    }
}

