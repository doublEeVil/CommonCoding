package com._22evil.test.sock5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sock5Server {
    private int port;

    public Sock5Server(int port) {
        this.port = port;
    }

    public void launch() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ExecutorService pool = Executors.newFixedThreadPool(20);
            new Thread(new MonitorThread()).start();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new Sock5ServerThread(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("端口绑定失败，已被占用 port" + port);
        }

    }

    public static void main(String ... args) {
        new Sock5Server(10086).launch();
    }
}
