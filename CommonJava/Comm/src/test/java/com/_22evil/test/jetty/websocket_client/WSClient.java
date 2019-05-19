package com._22evil.test.jetty.websocket_client;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * websocket client
 */
public class WSClient {

    public static void main(String[] args) {
        String url = "ws://192.168.0.192:29990/ws";

        WebSocketClient client = new WebSocketClient();
        ClientHandler handler = new ClientHandler(1);

        try {
            client.start();
            URI uri = new URI(url);

            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(handler, uri, request);
            System.out.printf("Connecting to : %s\n", uri);

            handler.awaitClose(5, TimeUnit.SECONDS);


            // Login
            IData login = new IData(10, 20);
            login.writeString("b2f84c75-595b-49c8-9b4e-c413a3902d06");
            login.writeString("");
            handler.sendData(login);

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
//            try {
//                client.stop();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
