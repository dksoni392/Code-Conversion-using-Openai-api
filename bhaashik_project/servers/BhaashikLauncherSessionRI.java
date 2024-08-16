/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bhaashik.servers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import bhaashik.servers.impl.BhaashikMainServer;
import bhaashik.servers.impl.SessionException;

/**
 *
 * @author User
 */
public interface BhaashikLauncherSessionRI extends Remote {

    public BhaashikMainServer getBhaashikMainServerInstance(UUID sessionId) throws SessionException, RemoteException;

    public void logout(UUID sessionId) throws RemoteException;    
    
}
