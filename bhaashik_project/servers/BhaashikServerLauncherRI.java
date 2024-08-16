/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bhaashik.servers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import bhaashik.servers.impl.AuthorizationException;

/**
 *
 * @author User
 */
public interface BhaashikServerLauncherRI extends Remote {
    public UUID login(String login, char[] password) throws AuthorizationException, RemoteException;
}
