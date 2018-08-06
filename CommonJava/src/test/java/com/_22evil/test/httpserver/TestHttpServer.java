package com._22evil.test.httpserver;

import com._22evil.module_httpserver.netty.MyHttpServer;

import org.junit.Test;

public class TestHttpServer {

    @Test
    public void startHttp() throws Exception {
        MyHttpServer server = new MyHttpServer(9000);
        server.launch("com._22evil.test.httpserver");
    }
}