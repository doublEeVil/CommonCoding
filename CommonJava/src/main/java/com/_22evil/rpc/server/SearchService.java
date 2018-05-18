package com._22evil.rpc.server;

import com._22evil.rpc.common.RpcService;
import com._22evil.rpc.protocol.ISearchService;

@RpcService(value = ISearchService.class)
public class SearchService implements ISearchService {
    public String getName(int id) {
        return "hello";
    }

    public int getAge(String name) {
        return 0;
    }
}