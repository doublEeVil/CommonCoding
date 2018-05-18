package com._22evil.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import com._22evil.rmi.protocol.ISearchInfoService;

public class RmiServer {
    public static void main(String[] args) {
        try {
            ISearchInfoService searchInfo = new SearchInfoService();
            LocateRegistry.createRegistry(8899);
            Naming.bind("rmi://localhost:8899/RSearchInfo", searchInfo);
            System.out.println("===创建服务成功===");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}