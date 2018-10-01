package com._22evil.test.jetty.http_server;

import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/**
 * 通过这里，我们可以看到session的生命周期
 * 浏览器发送请求， 其中头部带coolie信息，包含了一系列数据，包括sessionid
 * 服务端收到请求，返回头部信息，把cookie数据改动了一下，包括sessionid, 这一步是cookie reset
 * 客户端下次请求发送新的cookie,也就是服务器第一次发过来的那个信息
 */
public class TestJettyServer {

    @Test
    public void startServer() {
        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8888);
        server.addConnector(connector);
        Context root = new Context(server, "/", 1);
        root.addServlet(new ServletHolder(new TestServlet()), "/test");
        root.addServlet(new ServletHolder(new AbcServlet()), "/abc");
        try {
            server.start();
            Thread.sleep(123456);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
    }
}