package com._22evil.module_httpserver.session;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mock.web.MockHttpSession;

public class MySession extends MockHttpSession{
    private String sessionId;
    private HttpSession session;

    public MySession(HttpSession session) {
        this.session = session;
        // 生成sessionId
        this.sessionId = RandomStringUtils.randomAlphanumeric(15) + session.getId();
    }

    public String getSessionId() {
        return sessionId;
    }
}