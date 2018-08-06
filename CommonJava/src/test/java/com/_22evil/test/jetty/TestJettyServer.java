package com._22evil.test.jetty;

import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

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