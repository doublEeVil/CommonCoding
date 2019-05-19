package com._22evil.test.netty.http_server_spring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/12/28.
 */
public class MyHttpServer {
    private final static Logger logger = LoggerFactory.getLogger(MyHttpServer.class);

    private final int port;

    public MyHttpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        ServerBootstrap server = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            server.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new MyNettyInitalizer());

            logger.info("Netty server has started on port : " + port);

            server.bind().sync().channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
