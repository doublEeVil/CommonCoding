package com._22evil.test.sock5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sock5ConnThread implements Runnable {

    private Socket clientSocket;
    private Socket targetSocket;
    private InputStream in;
    private OutputStream out;

    public Sock5ConnThread(boolean flag, Socket clientSocket, Socket targetSocket) {
        try {
            this.clientSocket = clientSocket;
            this.targetSocket = targetSocket;
            if (flag) {
                this.in = clientSocket.getInputStream();
                this.out = targetSocket.getOutputStream();
            } else {
                this.in = targetSocket.getInputStream();
                this.out = clientSocket.getOutputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] data = new byte[512];
        while (true) {
            if (clientSocket.isClosed()) {
                break;
            }
            if (targetSocket.isClosed()) {
                break;
            }
            int c = 0;
            try {
                c = in.read(data);
                if (c > 0) {
                    out.write(data, 0, c);
                    out.flush();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        System.out.println("----线程结束---");
    }
}
