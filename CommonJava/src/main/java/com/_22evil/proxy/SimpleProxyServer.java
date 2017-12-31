package com._22evil.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * java io 编写，性能极差，而且只支持http, https直接失败
 * 百度网盘等也是失败
 */
public class SimpleProxyServer {
    private int port;

    public static void main(String[] args) throws Exception {
        new SimpleProxyServer(8900).start();
    }

    public SimpleProxyServer(int port) {
        this.port = port;
    }

    public void start() throws IOException{
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        while (true) {
            Socket socket = server.accept();
            new SocketThread(socket).start();
        }
    }
}

class SocketThread extends Thread {
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] data = new byte[4096];
            // 1. 握手第一步
            byte ver;
            byte nmethods;
            byte[] methods;
            int c = in.read(data);
            ver = data[0];
            nmethods = data[1];
            methods = new byte[nmethods];
            System.out.println("ver: " + ver + " nmethods: " + nmethods);
            for (byte i = 0; i < nmethods; i++) {
                methods[i] = data[2 + i];
                System.out.println("method: " + data[2 + i]);
            }
            byte[] rdata1 = {5, 0};
            out.write(rdata1);

            //2. 握手第二步
            c = in.read(data);
            ver = data[0];
            String host = findHost(data, 4, 7);
            int port = findPort(data, 8, 9);
            System.out.println("ver: " + ver);
            System.out.println("host: " + host + " port:" + port);
            byte[] rdata2 = {5, 0, 0, 1, data[4], data[5], data[6], data[7], data[8], data[9]};
            out.write(rdata2);

            //3. 第三步
            Socket proxy2Server = new Socket(host, port);
            InputStream stream_server2proxy = proxy2Server.getInputStream();
            OutputStream stream_proxy2server = proxy2Server.getOutputStream();
            new SendThread(in, stream_proxy2server).start();
            new SendThread(stream_server2proxy, out).start();
        } catch (Exception e) {
            e.printStackTrace();
            interrupt();
        }
    }

    public static String findHost(byte[] bArray, int begin, int end) {
        StringBuffer sb = new StringBuffer();
        for (int i = begin; i <= end; i++) {
            sb.append(Integer.toString(0xFF & bArray[i]));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static int findPort(byte[] bArray, int begin, int end) {
        int port = 0;
        for (int i = begin; i <= end; i++) {
            port <<= 16;
            port += bArray[i];
        }
        return port;
    }
}

class SendThread extends Thread {
    private InputStream in;
    private OutputStream out;

    public SendThread(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        try {
            byte[] data = new byte[409600];
            int len;
            while ((len = in.read(data)) != -1) {
                if (len > 0) {
                    out.write(data, 0, len);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
           interrupt();
        }
    }
}


