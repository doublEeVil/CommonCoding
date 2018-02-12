package com._22evil.netty_test.http_server;

import com._22evil.util.FileUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ChannelHandler.Sharable
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public static Set<String> urlSet = new HashSet<>();
    public static Set<String> fileUrlSet = new HashSet<>();
    private String staticFileDir = "G:\\apps";

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
            path = file.getPath().replace(staticFileDir, "\\");
            urlSet.add(path);
        }
        System.out.println("load....");
        for (String url : urlSet) {
            System.out.println(url);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String url = request.uri();
        System.out.println(url);
        if (!urlSet.contains(url)) {
            //404
            HttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.NOT_FOUND);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,
                    "text/html; charset=UTF-8");
            ctx.write(response);
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            return;
        }
        //是否是文件

    }
}
