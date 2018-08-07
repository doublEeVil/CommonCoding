package com._22evil.module_httpserver.session;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com._22evil.util.PrintUtil;

import org.springframework.mock.web.MockHttpSession;

import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.ClientCookieDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

/**
 * 自定义session管理器
 */
public class SessionManager {
    private static SessionManager instance = new SessionManager();
    private Map<String, MySession> sessionMap = new ConcurrentHashMap<>(20);

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        return instance;
    }

    public String getJSessionIdFromCookies(HttpServletRequest request) {
        String cookieStr = request.getHeader(HttpHeaderNames.COOKIE.toString());
        Set<Cookie> cookieSet = ServerCookieDecoder.STRICT.decode(cookieStr);
        for (Cookie cookie : cookieSet) {
            if (cookie.name().equals(MockHttpSession.SESSION_COOKIE_NAME)) {
                return cookie.value();
            }
        }
        return null;
    }

    public MySession solveHttpSession(HttpServletRequest request, String jsessionId) {
        // 基本思路，根据cookie得到jsession
        // 根据jsession得到Session，没有就创建
        MySession session = null;
        if (null == jsessionId) {
            session = new MySession(request.getSession(true));
            sessionMap.put(session.getSessionId(), session);
        } else {
            session = sessionMap.get(jsessionId);
            if (null == session) {
                session = new MySession(request.getSession(true));
                sessionMap.put(session.getSessionId(), session);
            }
        }
        return session;
    }
}