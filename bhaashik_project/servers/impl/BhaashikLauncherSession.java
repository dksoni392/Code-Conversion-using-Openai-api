/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers.impl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import bhaashik.servers.BhaashikLauncherSessionRI;

/**
 *
 * @author User
 */
public class BhaashikLauncherSession implements BhaashikLauncherSessionRI {
    
    private static final MainServerCollection mainServerCollection = new MainServerCollection();
    
    public BhaashikLauncherSession() throws RemoteException {
        super();
    }
    
//    public BhaashikLauncherSession(MainServerCollection mainServCollection)  throws RemoteException {
//        super();
//        mainServerCollection = mainServCollection;
//    }
    
    public void initBhaashikMainServer(UUID uuid, BhaashikMainServer mainServer)
    {
        mainServerCollection.execute(uuid, mainServer);
    }

    public BhaashikMainServer getBhaashikMainServerInstance(UUID sessionId) throws SessionException, RemoteException
    {
        if (!LauncherSessionStorage.INSTANCE.sessionIdExists(sessionId)) {
            throw new SessionException("Session id does not exist", 2);
        }

        BhaashikMainServer mainServer = null;
        
        try {
            mainServer = new BhaashikMainServer("", "", "", "UTF-8");

            initBhaashikMainServer(sessionId, mainServer);

//            bhaashikMainServer.getAuthenticationSever().`

            //5. create the shared directory
    //        pp.createDirectory(pp.getDefaultDirectoryPath());
            mainServer.getRMIFileSystem().createDirectory(mainServer.getRMIFileSystem().getDefaultDirectoryPathOnServer());

        } catch (IOException ex) {
            Logger.getLogger(BhaashikMainServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mainServer;
    }
    
    public void logout(UUID sessionId) throws RemoteException {
        LauncherSessionStorage.INSTANCE.removeSessionId(sessionId);
        mainServerCollection.removeAuthenticationSever(sessionId);
    }
    
}
