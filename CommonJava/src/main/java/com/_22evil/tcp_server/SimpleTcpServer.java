package com._22evil.tcp_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class SimpleTcpServer {
    private static Logger logger = LogManager.getLogger(SimpleTcpServer.class);

    private int port;
    private Selector selector;
    private Class handlerClazz;

    public SimpleTcpServer(int port, Class<? extends BaseTcpHandler> handler) {
        this.port = port;
        this.handlerClazz = handler;
    }

    public void start() throws Exception{
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port));
        server.configureBlocking(false);
        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        int core = Runtime.getRuntime().availableProcessors();
        BaseTcpHandler[] handlers = new BaseTcpHandler[core];
        for (int i = 0; i < core; i++) {
            handlers[i] = (BaseTcpHandler) handlerClazz.newInstance();
        }
        int index = 0;
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                keys.remove(key);
                if (key.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel channel = serverChannel.accept();
                    channel.configureBlocking(false);
                    BaseTcpHandler handler = handlers[(index++ % core)];
                    handler.addChannel(channel);
                    handler.wakeup();
                    handler.onConnect(handler.getTcpChannel(channel));
                }
            }
        }
    }
}

