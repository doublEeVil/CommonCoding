package com._22evil.netty_test.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("netty rocks...", CharsetUtil.UTF_8));
        new Thread(()->{
            while (true) {
                try {
                    System.out.println("input: ");
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    ctx.writeAndFlush(Unpooled.copiedBuffer(in.readLine(), CharsetUtil.UTF_8));
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client rcv: " + msg.toString(CharsetUtil.UTF_8));
    }
}