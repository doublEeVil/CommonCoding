// package com._22evil.test.netty.embeddedchannel;

// import io.netty.buffer.ByteBuf;
// import io.netty.buffer.Unpooled;
// import io.netty.channel.embedded.EmbeddedChannel;
// import org.junit.Assert;
// import org.junit.Test;

// public class FixedLengthFrameDecoderTest {
//     @Test
//     public void testFrameDecoded() {
//         ByteBuf buf = Unpooled.buffer();
//         for (int i = 0; i < 9 ; i++) {
//             buf.writeByte(i);
//         }
//         ByteBuf input = buf.duplicate();
//         EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
//         // write data
//         Assert.assertTrue(channel.writeInbound(input.retain()));
//         Assert.assertTrue (channel.finish());

//         // read data
//         ByteBuf read = (ByteBuf) channel.readInbound();
//         Assert.assertEquals(buf.readSlice(3),read);
//         read.release();

//         read = (ByteBuf) channel.readInbound();
//         Assert.assertEquals(buf.readSlice(3),read);
//         read.release();

//         read = (ByteBuf) channel.readInbound();
//         Assert.assertEquals(buf.readSlice(3),read);
//         read.release();

//         Assert.assertNull(channel.readInbound());
//         buf.release();
//     }

//     @Test
//     public void testFramesDecoded2() {
//         ByteBuf buf = Unpooled.buffer();
//         for (int i = 0; i < 9; i++) {
//             buf.writeByte(i);
//         }
//         ByteBuf input = buf.duplicate();
//         EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
//         Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
//         Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
//     }
// }
