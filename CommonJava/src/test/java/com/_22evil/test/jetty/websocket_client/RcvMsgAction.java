package com._22evil.test.jetty.websocket_client;

import io.netty.buffer.ByteBuf;

public class RcvMsgAction {

    public void action(int code, ByteBuf buf) {
        switch (code) {
            case 10021://
                // 登录成功 loginSuccess
                buf.readByte();
                boolean register = buf.readBoolean();
                buf.readByte();
                boolean hasEmail = buf.readBoolean();
                System.out.println("10021:" + register + " " + hasEmail);
                break;
            default: break;
        }
    }
}
