package com._22evil.netty_test.pojo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyHandler extends SimpleChannelInboundHandler<Entity> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Entity msg) throws Exception {
        System.out.println("rcv: " + msg.toString());
    }
}
