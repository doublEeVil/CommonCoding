package com._22evil.spring_hibernate.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

import sun.misc.Signal;
import java.util.function.Supplier;

public class MyServer {
    private static final Logger LOGGER = Logger
            .getLogger(MyServer.class);
    private final int port;

    //private static MyServer myServer;
    private static Supplier<MyServer> myServer = MyServer::createMyServer;

    protected  static MyServer getInstance(){
        return myServer.get();
    }
    private  synchronized static  MyServer createMyServer() {
        class MyServerFactory implements Supplier<MyServer> {
            private final MyServer myServerInstance = new MyServer();
            public MyServer get() { return myServerInstance; }
        }
        if(!MyServerFactory.class.isInstance(myServer)) {
            myServer = new MyServerFactory();
        }
        return myServer.get();
    }

    private ServerBootstrap server = new ServerBootstrap();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();

    public MyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        try {
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(DispatcherServletChannelInitializer.getInstance());

            LOGGER.info("Netty server has started on port : " + port);
            System.out.println("server has started : " + port);
            server.bind().sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    protected void shutdown() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
    private static String getOSSignalType() {
        return System.getProperties().getProperty("os.name").
                toLowerCase().startsWith("linux") ? "USR2" : "INT";
    }

    public static void main(String[] args) throws Exception {
        Signal sig = new Signal(getOSSignalType());
        Signal.handle(sig, new ServerShutdown());
        MyServer.getInstance().run();
    }

    private MyServer() {
        port = 8001;
    }
}
