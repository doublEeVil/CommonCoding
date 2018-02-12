package com._22evil.netty_test.pojo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteToPojoDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("...." + in.readableBytes());
        if (in.readableBytes() >= 4) {
            int id = in.readInt();
            System.out.println("id:" + id);
            int age = in.readInt();
            System.out.println("age:" + age);
            int len = in.readInt();
            byte[] data = new byte[len];
            in.readBytes(data);
            String name = new String(data);
            len = in.readInt();
            data = new byte[len];
            in.readBytes(data);
            String addr = new String(data);
            Entity entity = new Entity(id);
            entity.setAge(age);
            entity.setName(name);
            entity.setAddr(addr);
            out.add(entity);
        }
    }
}
