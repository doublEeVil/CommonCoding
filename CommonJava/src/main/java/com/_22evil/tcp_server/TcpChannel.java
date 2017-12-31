package com._22evil.tcp_server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Queue;

public class TcpChannel {
    public SocketChannel channel;
    public Queue<byte[]> queue;

    public TcpChannel(SocketChannel channel, Queue<byte[]> queue) {
        this.channel = channel;
        this.queue = queue;
    }

    public SocketAddress getRomoteAddress() throws IOException{
        return channel.getRemoteAddress();
    }

    public void write(byte[] bytes) {
        queue.offer(bytes);
    }
}
