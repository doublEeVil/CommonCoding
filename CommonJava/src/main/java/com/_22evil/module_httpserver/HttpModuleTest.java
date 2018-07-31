package com._22evil.module_httpserver;

import com._22evil.module_httpserver.controller.BaseHttpController;
import com._22evil.module_httpserver.controller.HandleHttp;
import com._22evil.module_httpserver.netty.MyHttpServer;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@HandleHttp(url = "/hell")
public class HttpModuleTest extends BaseHttpController{
    public static void main(String[] args) throws Exception {
        System.out.println("=====test====");
        System.out.println("====启动完成====");
        new MyHttpServer(10100).launch("com._22evil");
    }

    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        response.getWriter().write("this 是 http module 测试... test");
    }
}