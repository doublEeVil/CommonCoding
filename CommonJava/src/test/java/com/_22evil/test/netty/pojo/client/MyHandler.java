package com._22evil.test.netty.pojo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active....");
        for (int i = 0; i < 10; i++) {
            Entity entity = new Entity(i);
            entity.setAge(i + 1);
            entity.setName("name" + i);
            entity.setAddr("addr" + i);
            ctx.writeAndFlush(entity);
        }
    }
}
