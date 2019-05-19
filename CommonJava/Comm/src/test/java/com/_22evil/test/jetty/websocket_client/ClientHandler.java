package com._22evil.test.jetty.websocket_client;

import com._22evil.util.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.extensions.Frame;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@WebSocket
public class ClientHandler {
    private final CountDownLatch closeLatch;

    private Session session;
    private int id;

    RcvMsgAction action = new RcvMsgAction();

    public ClientHandler(int id) {
        this.closeLatch = new CountDownLatch(1);
        this.id  = id;
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.err.printf("Connection closed %d : %d - %s\n", id, statusCode,reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect %d : %s \n",id, session);
        this.session = session;
        this.closeLatch.countDown();
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.printf("Got msg: %s\n", msg);
    }

    @OnWebSocketFrame
    public void onFrame(Frame frame) {
        System.out.printf("Got msg: %s\n", frame);
        // 解码数据
        // 首先是协议数量
        ByteBuf buf = Unpooled.copiedBuffer(frame.getPayload());

        buf.skipBytes(16); // 跳过前12位
        int protocolNum = buf.readShort();
        System.out.println("protocolNum: " + protocolNum);

        for (int i = 0; i < protocolNum; i++) {
            // 协议头
            byte flag = buf.readByte();
            if (flag == -1) {
                System.out.println("错误协议");

                buf.skipBytes(1 + 1 + 4 + 1); // 跳过协议头
                // 一个code
                buf.readByte();
                int code = buf.readInt();
                // 一个message
                buf.readByte();
                short len = buf.readShort();
                String errorMsg = buf.readCharSequence(len, Charset.forName("utf-8")).toString();
                System.out.println("code: " + code + " msg:" + errorMsg);
            } else {
                byte mainId = buf.readByte();
                byte subId = buf.readByte();
                buf.skipBytes(4+1);// 跳过头

                // 分发到具体逻辑
                action.action(1000 * mainId + subId, buf);
            }
        }
        // 后面的直接释放
        buf.release();
    }

    public void sendString(String msg) {
        try {
            this.session.getRemote().sendString(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFrame(ByteBuffer data) {
        try {
            this.session.getRemote().sendBytes(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(IData data) {
        System.out.printf("send data: mainId: %d subId: %d\n", data.mainId, data.subId);
        try {
            this.session.getRemote().sendBytes(data.packet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
