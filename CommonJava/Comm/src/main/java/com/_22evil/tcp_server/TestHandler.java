package com._22evil.tcp_server;

import java.io.IOException;
import java.nio.ByteBuffer;

public class TestHandler extends BaseTcpHandler {
    public TestHandler() throws IOException {
        super();
    }

    @Override
    public void onRead(TcpChannel channel, ByteBuffer buffer) throws IOException{
        String msg = new String(buffer.array(), 0, buffer.position());
        System.out.println("rcv: " + channel.getRomoteAddress() + " " + msg);
        channel.write(("you send " + msg).getBytes());
    }

    @Override
    public void onConnect(TcpChannel channel) throws IOException{
        System.out.println("new channel " + channel.getRomoteAddress());
    }
}