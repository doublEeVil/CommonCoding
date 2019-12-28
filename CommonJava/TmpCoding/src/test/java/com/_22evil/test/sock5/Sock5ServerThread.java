package com._22evil.test.sock5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sock5ServerThread implements Runnable {
    private Socket clientSocket;
    private InputStream clientIn;
    private OutputStream clientOut;

    public Sock5ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            clientIn = clientSocket.getInputStream();
            clientOut = clientSocket.getOutputStream();
        } catch (IOException e) {
            logExp(e);
        }
    }

    @Override
    public void run() {
        try {
            // client req
            //                   +----+----------+----------+
            //                   |VER | NMETHODS | METHODS  |
            //                   +----+----------+----------+
            //                   | 1  |    1     | 1 to 255 |
            //                   +----+----------+----------+
            // server resp
            //                         +----+--------+
            //                         |VER | METHOD |
            //                         +----+--------+
            //                         | 1  |   1    |
            //                         +----+--------+
            byte[] data = new byte[256];
            clientIn.read(data, 0, 2);
            byte ver = data[0];
            if (ver != Common.SOCK_VERSION) {
                System.out.println("只支持 SOCK5，当前版本" + ver);
                return;
            }
            byte methodNum = data[1];
            if (methodNum <= 0) {
                System.out.println("协议错误");
                return;
            }
            clientIn.read(data, 0, methodNum);
            // 向客户端回应不需要认证
            byte[] authMsg = new byte[]{0x05, 0x00};
            clientOut.write(authMsg);
            clientOut.flush();

            // 处理链接命令
            onClientCMD();

        } catch (Exception e) {
            logExp(e);
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    logExp(e);
                }
            }
        }
    }

    private void onClientCMD() throws IOException {
        //        client req
        //        +----+-----+-------+------+----------+----------+
        //        |VER | CMD |  RSV  | ATYP | DST.ADDR | DST.PORT |
        //        +----+-----+-------+------+----------+----------+
        //        | 1  |  1  | X'00' |  1   | Variable |    2     |
        //        +----+-----+-------+------+----------+----------+
        byte[] data = new byte[255];
        clientIn.read(data, 0, 4);
        byte ver = data[0];
        if (ver != Common.SOCK_VERSION) {
            System.out.println("必须是sock5协议 当前：" + ver);
            return;
        }
        byte rsv = data[2]; // 保留字
        byte cmd = data[1]; // cmd类型
        byte addrType = data[3];

        if (cmd != Common.CMD_CONNECT && cmd != Common.CMD_BIND && cmd != Common.CMD_UDP) {
            System.out.println("不支持的命令的类型 cmd:" + cmd);
            return;
        }

        if (addrType != Common.ADDR_TYPE_DOMAINNAME && addrType != Common.ADDR_TYPE_IPV4 && addrType != Common.ADDR_TYPE_IPV6) {
            System.out.println("不支持的地址类型");
            return;
        }

        String targetAddr;
        int targetPort;
        byte[] addr;
        if (addrType == Common.ADDR_TYPE_IPV4) {
            clientIn.read(data, 0, 6);
            targetAddr = (data[0] & 0xFF) + "." + (data[1] & 0xFF) + "." + (data[2] & 0xFF) + "." + (data[3] & 0xFF);
            targetPort = ((data[4] & 0xff) << 8) | (data[5] & 0xff);
            addr = new byte[1 + 4 + 2];
            addr[0] = Common.ADDR_TYPE_IPV4;
            System.arraycopy(data, 0, addr, 1, 6);
        } else if (addrType == Common.ADDR_TYPE_DOMAINNAME) {
            int addrLen = clientIn.read();
            clientIn.read(data, 0, addrLen);
            targetAddr = new String(data, 0, addrLen);
            addr = new byte[1 + 1 + addrLen + 2];
            addr[0] = Common.ADDR_TYPE_DOMAINNAME;
            addr[1] = (byte) addrLen;
            System.arraycopy(data, 0, addr, 2, addrLen);
            clientIn.read(data, 0, 2);
            targetPort = ((data[0] & 0xff) << 8) | (data[1] & 0xff);
            System.arraycopy(data, 0, addr, 2 + addrLen, 2);
        } else {
            System.out.println("暂时不支持ipv6");
            return;
        }

        if (cmd != Common.CMD_CONNECT) {
            System.out.println("暂时只支持CONNECT命令");
            return;
        }

        // 开始处理链接
        onConnect(targetAddr, targetPort, addr);
    }

    private void onConnect(String addr, int port, byte[] resp) throws IOException {
        System.out.println("---准备链接： " + addr + " " + port);
        Socket targetSocket;
        try {
            targetSocket = new Socket(addr, port);
            sendConnSuccess(resp);
            new Thread(new Sock5ConnThread(false, clientSocket, targetSocket), "Sock5ConnThread").start();
            new Thread(new Sock5ConnThread(true, clientSocket, targetSocket), "Sock5ConnThread").start();
            Thread.sleep(1000L); //让上面的线程可以启动
        } catch (IOException | InterruptedException e) {
            logExp(e);
            sendConnFailed(resp);
        }
    }

    private void sendConnFailed(byte[] resp) throws IOException {
        byte[] send = new byte[3 + resp.length];
        send[0] = Common.SOCK_VERSION;
        send[1] = Common.RESP_NETWORK_UNREACH;
        send[2] = Common.RSV;
        System.arraycopy(resp, 0, send, 3, resp.length);
        clientOut.write(send);
        clientOut.flush();
    }

    private void sendConnSuccess(byte[] resp) throws IOException {
        byte[] send = new byte[3 + resp.length];
        send[0] = Common.SOCK_VERSION;
        send[1] = Common.RESP_SUCCESS;
        send[2] = Common.RSV;
        System.arraycopy(resp, 0, send, 3, resp.length);
        clientOut.write(send);
        clientOut.flush();
    }

    private void logExp(Exception e) {
        System.out.println("exception: " + e.getMessage());
        // e.printStackTrace();
    }
}
