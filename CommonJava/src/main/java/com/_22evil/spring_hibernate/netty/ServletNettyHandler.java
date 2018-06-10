package com._22evil.spring_hibernate.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.CharsetUtil;
//import org.apache.http.MethodNotSupportedException;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.NOPLogger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ServletNettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger LOGGER = Logger
            .getLogger(ServletNettyHandler.class);
    private final Servlet servlet;

    private final ServletContext servletContext;
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    public ServletNettyHandler(Servlet servlet) {
        this.servlet = servlet;
        this.servletContext = servlet.getServletConfig().getServletContext();
    }

    private MockHttpServletRequest createServletRequest(FullHttpRequest fullHttpRequest) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(fullHttpRequest.getUri()).build();
        // long st = System.currentTimeMillis();
        MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
        servletRequest.setRequestURI(uriComponents.getPath());
        servletRequest.setPathInfo(uriComponents.getPath());
        servletRequest.setMethod(fullHttpRequest.getMethod().name());

        if (uriComponents.getScheme() != null) {
            servletRequest.setScheme(uriComponents.getScheme());
        }
        if (uriComponents.getHost() != null) {
            servletRequest.setServerName(uriComponents.getHost());
        }
        if (uriComponents.getPort() != -1) {
            servletRequest.setServerPort(uriComponents.getPort());
        }

        for (String name : fullHttpRequest.headers().names()) {
            servletRequest.addHeader(name, fullHttpRequest.headers().get(name));
            //System.out.println(name+":"+fullHttpRequest.headers().get(name));
        }
        ByteBuf bbContent = fullHttpRequest.content();
        if (bbContent.hasArray()) {
            byte[] baContent = bbContent.array();
            servletRequest.setContent(baContent);
        }
        String jsonStr = bbContent.toString(CharsetUtil.UTF_8);
        HttpMethod method = fullHttpRequest.getMethod();
        if (HttpMethod.GET == method) {
            QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.getUri());
            decoder.parameters().entrySet().forEach(entry -> {
                servletRequest.addParameter(entry.getKey(), entry.getValue().get(0));
            });
            LOGGER.info("请求链接" + fullHttpRequest.getUri());
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            try {
                String paras = URLDecoder.decode(jsonStr, "UTF-8");
                try {
                    JSONObject.parseObject(paras);
                    servletRequest.addParameter("data", paras);
                } catch (Exception e) {
                    QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.getUri() + "?" + paras);
                    decoder.parameters().entrySet().forEach(entry -> {
                        servletRequest.addParameter(entry.getKey(), entry.getValue().get(0));
                    });
                }
                LOGGER.info("请求链接" + fullHttpRequest.getUri() + "?" + paras);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return servletRequest;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ByteBuf content = Unpooled.copiedBuffer(
                "Failure: " + status.toString() + "\r\n",
                CharsetUtil.UTF_8);

        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HTTP_1_1,
                status,
                content
        );
        fullHttpResponse.headers().add(CONTENT_TYPE, "text/plain; charset=UTF-8");
        //fullHttpResponse.headers().add(CONTENT_TYPE, "application/json; charset=UTF-8");
        //fullHttpResponse.headers().a
        // Close the connection as soon as the error message is sent.
        ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (!fullHttpRequest.getDecoderResult().isSuccess()) {
            sendError(channelHandlerContext, BAD_REQUEST);
            return;
        }
        MockHttpServletRequest servletRequest = createServletRequest(fullHttpRequest);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        this.servlet.service(servletRequest, servletResponse);
        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);

        for (String name : servletResponse.getHeaderNames()) {
            for (Object value : servletResponse.getHeaderValues(name)) {
                response.headers().add(name, value);
            }
        }

        // Write the initial line and the header.
        channelHandlerContext.write(response);

        InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());

        // Write the content and flush it.
        ChannelFuture writeFuture = channelHandlerContext.writeAndFlush(new ChunkedStream(contentStream));
        writeFuture.addListener(ChannelFutureListener.CLOSE);
    }

    public String ip;
}
