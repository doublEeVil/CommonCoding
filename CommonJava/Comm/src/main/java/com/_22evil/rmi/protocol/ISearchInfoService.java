package com._22evil.rmi.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ISearchInfoService extends Remote {
    
    String getName(int id) throws RemoteException;

    int getAge(String name) throws RemoteException;

    List<String> getAllName() throws RemoteException;
}
