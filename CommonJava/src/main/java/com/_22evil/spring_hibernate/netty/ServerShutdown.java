package com._22evil.spring_hibernate.netty;

import org.apache.log4j.Logger;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ServerShutdown implements SignalHandler {
    private static final Logger LOGGER = Logger
            .getLogger(ServerShutdown.class);

    @Override
    public void handle(Signal signal) {
        invokeShutdownHook();
        Runtime.getRuntime().exit(0);
        //System.exit(0);
    }

    private void invokeShutdownHook() {
        Thread t = new Thread(() -> {
            try {
                MyServer.getInstance().shutdown();
                LOGGER.info("netty停止成功");
                LOGGER.info("服务退出成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "ShutdownHook-Thread");
        Runtime.getRuntime().addShutdownHook(t);
    }
}
