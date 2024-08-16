/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 * @author User
 */
public interface AuthenticationSessionRI extends Remote {

//    public List<Integer> getData(boolean reverse, UUID sessionId) throws SessionException, RemoteException;

//    public AuthenticationSeverRI getAuthenticationSeverInstance(UUID sessionId) throws SessionException, RemoteException;

    public void logout(UUID sessionId) throws RemoteException;    
}
