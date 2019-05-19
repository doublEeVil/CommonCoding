package com._22evil.test.sofa_rpc.server;
import com._22evil.test.sofa_rpc.api.HelloService;
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String s) {
        return s + "++++";
    }
}
