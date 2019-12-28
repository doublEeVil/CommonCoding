package com._22evil.test.sock5_local;

import java.net.ServerSocket;

public class Sock5Local {
    private int port;

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
    }

    public static void main(String ... args) throws Exception {
        new Sock5Local().start();
    }
}
