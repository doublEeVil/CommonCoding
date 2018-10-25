package com._22evil.test.netty.websokcet.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by shangguyi on 17/02/2018.
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String WEBSOCKETPATH;

    public HttpHandler(String wsPath) {
        this.WEBSOCKETPATH = wsPath;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if("/".equals(request.uri()) || "/index.html".equals(request.uri())) {
            ByteBuf content = Unpooled.copiedBuffer("hello , java", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            response.content().writeBytes(content);
            ChannelFuture future = ctx.writeAndFlush(response);
            future.addListener((f) -> {
                if (!f.isSuccess()) {
                    System.out.println(f.cause().getMessage());
                }
            });
        }
    }
}
