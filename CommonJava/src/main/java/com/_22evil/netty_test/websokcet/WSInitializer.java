package com._22evil.netty_test.websokcet;

import com._22evil.netty_test.websokcet.handler.HttpHandler;
import com._22evil.netty_test.websokcet.handler.WebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;


/**
 * Created by shangguyi on 17/02/2018.
 * url  ws://127.0.0.1:8081/ws
 */
public class WSInitializer extends ChannelInitializer<SocketChannel> {
    private String WEBSOCKET_PATH = "/ws";
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        pip.addLast(new HttpServerCodec());
        pip.addLast(new HttpObjectAggregator(65535));
        pip.addLast(new WebSocketServerCompressionHandler());
        pip.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        pip.addLast(new HttpHandler(WEBSOCKET_PATH));
        pip.addLast(new WebSocketFrameHandler());
    }
}
