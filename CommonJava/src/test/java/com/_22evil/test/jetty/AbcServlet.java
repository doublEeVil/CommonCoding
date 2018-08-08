package com._22evil.test.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com._22evil.util.PrintUtil;

public class AbcServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getSession().getId();

        Cookie[] cookies =  req.getCookies();
        if (cookies != null)
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getDomain() + " " + cookie.getName() + " " + cookie.getValue());
            PrintUtil.printVar(cookie);
        }

        String out = "<html><body><a href='/test'>go to test</a><br>sid: +" + sid +"+</body></html>";
        resp.getWriter().write(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}