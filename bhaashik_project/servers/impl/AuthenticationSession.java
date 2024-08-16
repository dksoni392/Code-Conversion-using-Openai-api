/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers.impl;

import java.rmi.RemoteException;
import java.util.UUID;

import bhaashik.servers.AuthenticationSessionRI;
import bhaashik.servers.AuthenticationSeverRI;

/**
 *
 * @author User
 */
public class AuthenticationSession implements AuthenticationSessionRI {

    private static final AuthenticationServerCollection authenticationSeverCollection = new AuthenticationServerCollection();

    public AuthenticationSession() throws RemoteException {
        super();
    }

//    public AuthenticationSession(AuthenticationServerCollection authSeverCollection) throws RemoteException {
//        super();
//        this.authenticationSeverCollection = authSeverCollection;
//    }
    
    public static void initAuthenticationServer(UUID uuid, AuthenticationSever authServer)
    {
        authenticationSeverCollection.execute(uuid, authServer);
    }

    public static AuthenticationSeverRI getAuthenticationSeverInstance(UUID sessionId) throws SessionException, RemoteException
    {
        if (!AuthSessionStorage.INSTANCE.sessionIdExists(sessionId)) {
            throw new SessionException("Session id does not exist", 2);
        }

        AuthenticationSeverRI authenticationServer = null;

        authenticationServer = new AuthenticationSever();
        
        initAuthenticationServer(sessionId, (AuthenticationSever) authenticationServer);
//            bhaashikMainServer.getAuthenticationSever().`
//
//        //5. create the shared directory
////        pp.createDirectory(pp.getDefaultDirectoryPath());
//        bhaashikMainServer.getRMIFileSystem().createDirectory(bhaashikMainServer.getRMIFileSystem().getDefaultDirectoryPathOnServer());

        return authenticationServer;
    }

    public void logout(UUID sessionId) throws RemoteException {
        AuthSessionStorage.INSTANCE.removeSessionId(sessionId);
        authenticationSeverCollection.removeAuthenticationSever(sessionId);
    }
}
