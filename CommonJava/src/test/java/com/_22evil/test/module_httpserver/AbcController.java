package com._22evil.test.module_httpserver;

import com._22evil.module_httpserver.controller.BaseHttpController;
import com._22evil.module_httpserver.controller.HandleHttp;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@HandleHttp(url = "/abc")
public class AbcController extends BaseHttpController{

    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        System.out.println("this is abc");
        String sid = request.getSession().toString();

        String name = (String)request.getSession().getAttribute("name");
        response.getWriter().write("<html><body>thi is  abc  " + name + " " + sid + " <a href='/test'>go to test</a>" + "</body></html>" );
    }
}