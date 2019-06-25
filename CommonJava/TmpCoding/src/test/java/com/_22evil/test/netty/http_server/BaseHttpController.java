package com._22evil.test.netty.http_server;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shangguyi on 16/02/2018.
 */
public abstract class BaseHttpController {
    public abstract void doService(FullHttpRequest request, FullHttpResponse response);

    public Map<String, String> params(FullHttpRequest request) {
        Map<String, String> map = new HashMap<>();
        if (HttpMethod.GET == request.method()) {
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            decoder.parameters().entrySet().forEach((e) -> {
                map.put(e.getKey(), e.getValue().get(0));
            });
        } else if (HttpMethod.POST == request.method()) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
            decoder.offer(request);
            List<InterfaceHttpData> list = decoder.getBodyHttpDatas();
            list.forEach((d) -> {
                try {
                    Attribute attribute = (Attribute)d;
                    map.put(attribute.getName(), attribute.getValue());
                } catch (Exception e) {

                }
            });
        }
        return map;
    }
}
