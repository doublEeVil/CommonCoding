package com._22evil.netty_test.http_server;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

import java.util.Map;

/**
 * Created by shangguyi on 16/02/2018.
 */
public class AbcController extends BaseHttpController {
    @Override
    public void doService(FullHttpRequest request, FullHttpResponse response) {
        Map<String, String> parmMap = params(request);
        System.out.println(parmMap.get("name"));
        response.content().writeCharSequence("hhh", CharsetUtil.UTF_8);
    }
}
