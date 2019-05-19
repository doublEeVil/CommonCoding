package com._22evil.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TestNioServer implements Runnable{

    private final int BUFFER_SIZE = 1024;
    private final int PORT = 8080;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public static void main(String[] args) {
        new Thread(new TestNioServer()).start();
    }

    public TestNioServer() {
        laungh();
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        onAccept(key);
                    }
                    if (key.isReadable()) {
                        onRead(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onRead(SelectionKey key) {
        try {
            readBuffer.clear();
            SocketChannel socketChannel = (SocketChannel) key.channel();
            int cnt = socketChannel.read(readBuffer);
            if (cnt == -1) {
                socketChannel.close();
                key.cancel();
                return;
            }
            readBuffer.flip();

            byte[] data = new byte[readBuffer.remaining()];
            readBuffer.get(data);
            System.out.println("rcv: " + new String(data));
            socketChannel.write(ByteBuffer.wrap("hhh".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAccept(SelectionKey key) {
        try {
            ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = socketChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void laungh() {
        try {
            selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(PORT));
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
