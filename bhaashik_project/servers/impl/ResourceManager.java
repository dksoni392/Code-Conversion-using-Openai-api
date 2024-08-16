/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers.impl;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bhaashik.servers.ResourceManagerRI;

/**
 *
 * @author User
 */
public class ResourceManager extends BhaashikServer
    implements ResourceManagerRI, Serializable {

    private final BhaashikMainServer bhaashikMainServer;

    public ResourceManager(String propManPath, String cs, BhaashikMainServer bhaashikMainServer) throws RemoteException, IOException {
        super(propManPath, cs);
        this.bhaashikMainServer = bhaashikMainServer;
    }
    
    public final static ResourceManager getResourceManagerServerInstance(String propManPath, String cs, BhaashikMainServer bhaashikMainServer)
    {
        ResourceManager resourceManagerServer = null;
        
        try {
            resourceManagerServer = new ResourceManager(propManPath, cs, bhaashikMainServer);
        } catch (IOException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resourceManagerServer;
    }
    
}
