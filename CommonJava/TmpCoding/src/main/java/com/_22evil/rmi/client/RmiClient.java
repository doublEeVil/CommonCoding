package com._22evil.rmi.client;

import java.rmi.Naming;

import com._22evil.rmi.protocol.ISearchInfoService;

public class RmiClient {
    public static void main(String[] args) {
        try {
            ISearchInfoService searchInfo = (ISearchInfoService)Naming.lookup("rmi://localhost:8899/RSearchInfo");
            System.out.println(searchInfo.getName(0));
            System.out.println(searchInfo.getName(1));
            System.out.println(searchInfo.getAge(""));
            System.out.println(searchInfo.getAllName());
        } catch (Exception e) {

        }
    }
}