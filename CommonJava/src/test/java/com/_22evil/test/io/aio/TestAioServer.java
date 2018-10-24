package com._22evil.test.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestAioServer {
    private ExecutorService pool;
    private AsynchronousChannelGroup threadGroup;
    public AsynchronousServerSocketChannel serverSocketChannel;
    private final int  PORT = 8080;

    public static void main(String[] args) {
        new TestAioServer().start();
    }

    public void start() {
        try {
            pool = Executors.newCachedThreadPool();
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(pool, 1);
            serverSocketChannel = AsynchronousServerSocketChannel.open(threadGroup);
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            System.out.println("server start ...");
            serverSocketChannel.accept();

            Thread.sleep(1100022);
        } catch (IOException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Handler implements CompletionHandler<AsynchronousSocketChannel, TestAioServer> {
    private final Integer BUFFER_SIZE = 1024;

    @Override
    public void completed(AsynchronousSocketChannel result, TestAioServer attachment) {
        attachment.serverSocketChannel.accept(attachment, this);
        read(result);
    }

    private void read(AsynchronousSocketChannel result) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        result.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                //进行读取之后,重置标识位
                attachment.flip();
                //获取读取的数据
                String resultData = new String(attachment.array()).trim();
                System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
                String response = resultData + " = ";
                write(result, response);
            }
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });

    }

    // 写入数据
    private void write(AsynchronousSocketChannel asynSocketChannel, String response) {
        try {
            // 把数据写入到缓冲区中
            ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
            buf.put(response.getBytes());
            buf.flip();
            // 在从缓冲区写入到通道中
            asynSocketChannel.write(buf).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, TestAioServer attachment) {

    }


}