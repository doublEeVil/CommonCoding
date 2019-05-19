package com._22evil.test.jetty.websocket_client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class IData {
    private static final byte startFlag = 0;
    public int mainId;
    public int subId;
    private int len;
    private static final byte endFlag = 1;
    public static int INDEX = 1;

    private static final byte[] HEAD = { 'H', 'O', 'G', 'P'};
    private ByteBuf data;

    public IData(int mainId, int subId) {
        this.mainId = mainId;
        this.subId = subId;
        this.data = Unpooled.directBuffer();
    }

    public ByteBuffer packet() {
        ByteBuf packetHead = Unpooled.directBuffer(18);
        packetHead.writeBytes(HEAD); // 0-3

        packetHead.writeInt(0); // sessionId
        packetHead.writeInt(INDEX++);

        packetHead.writeInt(18 + 8 + data.readableBytes());// 包长度
        packetHead.writeShort((short) 1);// 协议数量


        ByteBuf buffer = Unpooled.directBuffer();

        //写入包头
        buffer.writeBytes(packetHead);
        //写入协议头
        buffer.writeByte(startFlag);
        buffer.writeByte((byte)mainId);
        buffer.writeByte((byte)subId);
        buffer.writeInt(8 + data.readableBytes());
        buffer.writeByte(endFlag);
        //写入协议具体内容
        buffer.writeBytes(data);
        //协议尾
        buffer.writeByte(0);

        return buffer.nioBuffer();
    }

    public void writeByte(byte b) {
        data.writeByte(AllType.BYTE);
        data.writeByte(b);
    }

    public void writeBytes(byte[] bs) {
        data.writeByte(AllType.BYTES);
        data.writeShort(bs.length);
        for (byte b : bs) {
            data.writeByte(b);
        }
    }

    public void writeBool(boolean b) {
        data.writeByte(AllType.BOOL);
        data.writeBoolean(b);
    }

    public void writeBools(boolean[] bs) {
        data.writeByte(AllType.BOOLS);
        data.writeShort(bs.length);
        for (boolean b : bs) {
            data.writeBoolean(b);
        }
    }

    public void writeShort(short s) {
        data.writeByte(AllType.SHORT);
        data.writeShort(s);
    }

    public void writeShorts(short[] ss) {
        data.writeByte(AllType.SHORTS);
        data.writeShort(ss.length);
        for (short s : ss) {
            data.writeShort(s);
        }
    }

    public void writeInt(int i) {
        data.writeByte(AllType.INT);
        data.writeInt(i);
    }

    public void writeInts(int[] is) {
        data.writeByte(AllType.INTS);
        data.writeShort(is.length);
        for (int i : is) {
            data.writeInt(i);
        }
    }

    public void writeLong(long l) {

    }

    public void writeLongs(long[] ls) {

    }

    public void writeString(String s) {
        data.writeByte(AllType.STRING);
        try {
            byte[] bytes = s.getBytes("utf-8");
            data.writeShort(bytes.length);
            data.writeBytes(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void writeStrings(String[] ss) {
        data.writeByte(AllType.STRINGS);
        data.writeShort(ss.length);
        for (String s : ss) {
            byte[] bytes = new byte[0];
            try {
                bytes = s.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            data.writeShort(bytes.length);
            data.writeBytes(bytes);
        }
    }

}

class AllType {
    static byte BYTE = 1;
    static byte BYTES = -127;

    static byte BOOL = 2;
    static byte BOOLS = -126;

    static byte SHORT = 3;
    static byte SHORTS = -125;

    static byte INT = 4;
    static byte INTS = -124;

    static byte LONG = 5;
    static byte LONGS = -123;

    static byte STRING = 6;
    static byte STRINGS = -122;
}
