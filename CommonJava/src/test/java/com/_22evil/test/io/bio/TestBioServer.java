package com._22evil.test.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO的典型用例
 */
public class TestBioServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        ExecutorService pool = null;
        try {
            server = new ServerSocket(PORT);
            pool = Executors.newCachedThreadPool();
            System.out.println("server start ...");
            while (true) {
                socket = server.accept();
                pool.execute(new Handler(socket));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (null != server) {
                    server.close();
                }
                pool.shutdown();
            } catch (IOException e) {

            }
        }

    }

}

class Handler implements Runnable {
    private Socket socket;
    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream(), true);
            String body = null;
            while (true) {
                body = reader.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("rcv: " + body);
                writer.write(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}