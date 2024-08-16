/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author User
 */
public interface AuthenticationSeverRI extends Remote {
    /** Returns session id **/
    public Serializable authenticateUser(String userName, String password)  throws RemoteException;
//    public void logout(UUID sessionId) throws RemoteException;
}
