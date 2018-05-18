package com._22evil.rmi.server;

import com._22evil.rmi.protocol.ISearchInfoService;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.List;

public class SearchInfoService extends UnicastRemoteObject implements ISearchInfoService {

    public SearchInfoService() throws RemoteException{

    }

    public String getName(int id) throws RemoteException {
        if (id == 0) {
            return "0000";
        } 
        if (id == 1) {
            return "1111";
        }
        return null;
    }

    public int getAge(String name) throws RemoteException {
        if (name == null) {
            return 1;
        }
        return 0;
    }

    public List<String> getAllName() throws RemoteException {
        return null;
    }
}