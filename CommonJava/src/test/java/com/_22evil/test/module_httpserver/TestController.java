package com._22evil.test.module_httpserver;

import com._22evil.module_httpserver.controller.BaseHttpController;
import com._22evil.module_httpserver.controller.HandleHttp;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@HandleHttp(url = "/test")
public class TestController extends BaseHttpController{

    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        System.out.println("this is test");
        request.getSession(true).setAttribute("name", "zhjs");
        String sid = request.getSession().toString();

        response.getWriter().write("<html><body>thi is  test " + sid + "<a href='/abc'>go to abc</a></body></html>");
    }
}