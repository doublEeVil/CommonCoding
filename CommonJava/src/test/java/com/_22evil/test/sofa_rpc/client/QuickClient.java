package com._22evil.test.sofa_rpc.client;
import com._22evil.test.sofa_rpc.api.HelloService;
import com.alipay.sofa.rpc.config.ConsumerConfig;
public class QuickClient {

    public static void main(String[] args) {
        ConsumerConfig<HelloService> config = new ConsumerConfig<>();
        config.setInterfaceId(HelloService.class.getName())
                .setProtocol("bolt")
                .setDirectUrl("bolt://127.0.0.1:8900");
        HelloService helloService = config.refer();
        int i = 12;
        while (i-- > 0) {
            System.out.println(helloService.sayHello(i + ""));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
