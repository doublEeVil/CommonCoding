package com._22evil.netty_test.http_server;

import com._22evil.util.FileUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.regex.Pattern;

@ChannelHandler.Sharable
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public static Set<String> URL_SET = new HashSet<>();
    public static Set<String> FILE_URL_SET = new HashSet<>();
    public static Map<String, BaseHttpController> CONTROLLER_MAP = new HashMap<>();
    private String staticFileDir = "/Users/shangguyi/Downloads";
    private static final Pattern PTN = Pattern.compile("\\?.*");

    public HttpHandler() {
        initStaticFiles();
    }

    /**
     * 加载静态文件
     */
    private void initStaticFiles() {
        List<File> fileList = FileUtil.getFileList(staticFileDir);
        String path;
        for (File file : fileList) {
            path = file.getPath().replace(staticFileDir, "");
            URL_SET.add(path);
            FILE_URL_SET.add(path);
        }
        URL_SET.add("/abc");
        CONTROLLER_MAP.put("/abc", new AbcController());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String url = request.uri();
        System.out.println(url);
        url = PTN.matcher(url).replaceAll("");
        System.out.println(url);
        if (!URL_SET.contains(url)) {
            //404
            HttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.NOT_FOUND);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,
                    "text/html; charset=UTF-8");
            ctx.write(response);
            ctx.write("404");
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            future.addListener(ChannelFutureListener.CLOSE);
            return;
        }
        if (FILE_URL_SET.contains(url)) {
            //是否是文件
            doFileServer(ctx, url);
            return;
        }
        doHttpServer(ctx, url,request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK));
    }

    /**
     * 文件服务器
     * @param ctx
     * @param url
     */
    private void doFileServer(ChannelHandlerContext ctx, String url) {
        File file = new File(staticFileDir + url);
        try {
            try{
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, randomAccessFile.length());
                ctx.write(response);
                ctx.write(new ChunkedFile(randomAccessFile, 0, randomAccessFile.length(), 8192), ctx.newProgressivePromise());
                ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                lastContentFuture.addListener(ChannelFutureListener.CLOSE);
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * http服务器
     * @param ctx
     * @param url
     * @param request
     * @param response
     */
    private void doHttpServer(ChannelHandlerContext ctx, String url, FullHttpRequest request, FullHttpResponse response) {
        CONTROLLER_MAP.get(url).doService(request, response);
        ctx.write(response);
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        lastContentFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
