package com._22evil.test.sofa_rpc.server;
import com._22evil.test.sofa_rpc.api.AbcService;
public class AbcServiceImpl implements AbcService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
