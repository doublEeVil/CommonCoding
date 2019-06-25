package com._22evil.test.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        System.out.println("server rcv: " + in.toString(CharsetUtil.UTF_8));
        //ctx.writeAndFlush(in.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(msg);
        //ChannelFuture cf = ctx.writeAndFlush(msg);
//        cf.addListener((future)->{
//            if (future.isSuccess()) {
//                System.out.println("===write success");
//            } else {
//                System.out.println("===write fail");
//            }
//        });
        // 调度任务
        ScheduledFuture<?> future = ctx.channel().eventLoop().schedule(()->{
            System.out.println("do some thing");
        }, 12, TimeUnit.SECONDS);
        future.cancel(false);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        System.out.println("------read complete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }
}