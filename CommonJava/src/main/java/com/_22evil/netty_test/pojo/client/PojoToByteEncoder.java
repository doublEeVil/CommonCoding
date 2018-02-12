package com._22evil.netty_test.pojo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PojoToByteEncoder extends MessageToByteEncoder<Entity> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Entity msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getId());
        out.writeInt(msg.getAge());
        String name = msg.getName();
        String addr = msg.getAddr();
        out.writeInt(name.length());
        out.writeBytes(name.getBytes());
        out.writeInt(addr.length());
        out.writeBytes(addr.getBytes());
    }
}
