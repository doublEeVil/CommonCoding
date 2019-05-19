package com._22evil.test.sofa_rpc.server;
import com._22evil.test.sofa_rpc.api.HelloService;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
public class QuickServer {

    public static void main(String ... args) {
        ServerConfig config = new ServerConfig();
        config.setProtocol("bolt");
        config.setPort(8900);
        config.setDaemon(false);
        ProviderConfig<HelloService> providerConfig = new ProviderConfig<>();
        providerConfig.setInterfaceId(HelloService.class.getName())
                .setRef(new HelloServiceImpl())
                .setServer(config);
        providerConfig.export();
    }
}
