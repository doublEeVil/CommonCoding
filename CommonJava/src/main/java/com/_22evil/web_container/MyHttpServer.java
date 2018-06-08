package com._22evil.web_container;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com._22evil.web_container.global.Global;

/**
 * Created by Administrator on 2016/12/28.
 */
public class MyHttpServer {
    private final static Logger logger = LoggerFactory.getLogger(MyHttpServer.class);

    private final int port;

    public MyHttpServer(int port) {
        this.port = port;
    }

    public void launch() throws Exception {
        initFreeMarker();

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

    /**
     * 初始化模板解析
     */
    private void initFreeMarker() throws Exception{
        Global.getFreeMarkerConfig();
    }
    
}
