package com._22evil.tcp_client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by shangguyi on 03/12/2017.
 */
public class SimpleTcpClient {
    private String host;
    private int port;

    public SimpleTcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        SocketChannel channel =  SocketChannel.open(new InetSocketAddress(host, port));

        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
        channel.write(buffer);
        channel.configureBlocking(false);

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey key : keys) {
                if (key.isReadable()) {
                    SocketChannel ch = (SocketChannel) key.channel();
                    onRead(ch);
                }
            }
        }
    }

    private void onRead(SocketChannel ch) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(512);
        int c = ch.read(buffer);
        System.out.println("rcv: " + new String(buffer.array(), 0, c));
    }
}
