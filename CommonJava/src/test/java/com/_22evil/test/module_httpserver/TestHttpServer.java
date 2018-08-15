package com._22evil.test.module_httpserver;

import com._22evil.module_httpserver.netty.MyHttpServer;

import org.junit.Test;

public class TestHttpServer {

    @Test
    public void startHttp() throws Exception {
        MyHttpServer server = new MyHttpServer(8888);
        server.launch("com._22evil.test.module_httpserver");
    }
}