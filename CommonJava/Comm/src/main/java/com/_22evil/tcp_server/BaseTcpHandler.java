package com._22evil.tcp_server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseTcpHandler {
    private static Logger logger = LogManager.getLogger(BaseTcpHandler.class);
    private static final ExecutorService pool = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());
    private Selector selector;
    public Queue<byte[]> msgQueue = new ConcurrentLinkedQueue<>();
    public Map<SocketChannel, TcpChannel> channelMap = new ConcurrentHashMap<>();

    public BaseTcpHandler() throws IOException {
        selector = SelectorProvider.provider().openSelector();
        start();
    }

    public void addChannel(SocketChannel channel) throws ClosedChannelException {
        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    public void wakeup() {
        selector.wakeup();
    }

    public void start() {
        pool.submit(() -> {
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            while (true) {
                if (selector.select(1) <= 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        key.attach(readBuffer);
                        processRead(key);
                    } else if (key.isWritable()) {
                        key.attach(writeBuffer);
                        processWrite(key);
                    }
                    keys.remove(key);
                }
            }
        });
    }

    public void processRead(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        try {
            int count = channel.read(buffer);
            if (count < 0) {
                onDisConnect(getTcpChannel(channel));
                channel.close();
                key.cancel();
                logger.info("rcv invalid data, close...");
                channelMap.remove(channel);
                return;
            } else if (count == 0) {
                return;
            }
            onRead(getTcpChannel(channel), buffer);
            buffer.clear();
        } catch (Exception e) {
            logger.info("{} 关闭了链接" + channel.getRemoteAddress());
            onDisConnect(getTcpChannel(channel));
            channel.close();
            key.cancel();
            channelMap.remove(channel);
        }
    }

    public void processWrite(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer resp = (ByteBuffer)key.attachment();
        resp.clear();
        while (!msgQueue.isEmpty()) {
            byte[] msg = msgQueue.remove();
            if (msg != null) {
                resp.put(msg);
            }
        }
        resp.flip();
        channel.write(resp);
    }

    public TcpChannel getTcpChannel(SocketChannel channel) {
        if (!channelMap.containsKey(channel)) {
            channelMap.put(channel, new TcpChannel(channel, msgQueue));
        }
        return channelMap.get(channel);
    }

    public void onConnect(TcpChannel channel) throws IOException {}

    public void onDisConnect(TcpChannel channel) throws IOException {}

    public abstract void onRead(TcpChannel channel, ByteBuffer buffer) throws IOException;

}
