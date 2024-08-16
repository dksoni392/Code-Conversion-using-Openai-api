/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers.impl;

/**
 *
 * @author User
 */

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import bhaashik.auth.Encryptor;
import bhaashik.auth.SQLiteJDBC;
import bhaashik.servers.AuthenticationEntryRI;
import bhaashik.servers.AuthenticationSessionRI;
import bhaashik.servers.BhaashikLauncherSessionRI;
import bhaashik.servers.BhaashikServerLauncherRI;

/**
 *
 * @author User
 */
public class BhaashikServerLauncher extends UnicastRemoteObject implements BhaashikServerLauncherRI, Serializable {

    private static final String LAUNCHER_LOGIN = "LauncherLogin";
    private static final String LAUNCHER_SESSION = "LauncherSession";
    
    private static final String AUTH_LOGIN = "AuthorizationLogin";
    private static final String AUTH_SESSION = "AuthorizationSession";
    
    private static boolean connected = false;
    
//    private BhaashikLauncherSession bhaashikLauncherSession;
    
    public static boolean isConnected() {
        return connected;
    }

    public static void setConnected(boolean aConnected) {
        connected = aConnected;
    }
    
    public BhaashikServerLauncher() throws RemoteException
    {
        super();
    }
    
    public BhaashikMainServer getBhaashikMainServerInstance() throws RemoteException
    {
        if(connected)
        {
            BhaashikMainServer bhaashikMainServer = null;

            try {
                bhaashikMainServer = new BhaashikMainServer("", "", "", "UTF-8");
    //            bhaashikMainServer.getAuthenticationSever().`
            } catch (IOException ex) {
                Logger.getLogger(BhaashikMainServer.class.getName()).log(Level.SEVERE, null, ex);
            }

            //5. create the shared directory
    //        pp.createDirectory(pp.getDefaultDirectoryPath());
            bhaashikMainServer.getRMIFileSystem().createDirectory(bhaashikMainServer.getRMIFileSystem().getDefaultDirectoryPathOnServer());

            return bhaashikMainServer;
        }
        else
            return null;
    }
    public AuthenticationSever getAuthenticationSeverInstance() throws RemoteException
    {
        if(connected)
        {
            AuthenticationSever authenticationSever = null;

            try {
                authenticationSever = new AuthenticationSever();
    //            bhaashikMainServer.getAuthenticationSever().`
            } catch (IOException ex) {
                Logger.getLogger(BhaashikMainServer.class.getName()).log(Level.SEVERE, null, ex);
            }

            return authenticationSever;
        }
        else
            return null;
    }

    public UUID login(String login, char[] password) throws AuthorizationException, RemoteException {
        String pass = Encryptor.encryptPassword(password);
        try {
            if (SQLiteJDBC.userExists(login, pass)) {
                System.out.println("User " + login + " logged in.");
                
                return LauncherSessionStorage.INSTANCE.generateSessionId(login);
            } else {
                throw new AuthorizationException("User with such username/password combination does not exist", 1);
            }
        } catch (SQLException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            throw new AuthorizationException("Exception while authorization. Root cause: " + e);
        }
    }    
   
    public static void main(String[] args) {
        try {
            // Add:
            // -Djava.security.policy=server.policy
            // to the VM arguments when running the server
//        if(System.getSecurityManager()==null) {
//            System.setSecurityManager(new SecurityManager());
//        }

//1. create our remote object
//        RMIFileSystemImpl p = new RMIFileSystemImpl();
//        BhaashikServerLauncherRI bhaashikServerLauncher = null;

            SQLiteJDBC.createTable();
            SQLiteJDBC.fillTable();
//            DataGenerator dataGenerator = new DataGenerator();

            // "Data generator"
//            BhaashikMainServer bhaashikMainServer = new BhaashikMainServer("", "", "", "UTF-8");
//            AuthenticationSeverRI AuthenticationSever = new AuthenticationSever();
//            MainServerCollection mainServerCollection = new MainServerCollection();
//            AuthenticationServerCollection authenticationServerCollection = new AuthenticationServerCollection();

            // Login service
            BhaashikServerLauncherRI bhaashikServerLauncher = new BhaashikServerLauncher();
            AuthenticationEntryRI authenticationEntry = new AuthenticationEntryImpl();
            
            // Session service
//            BhaashikLauncherSessionRI bhaashikLauncherSession = new BhaashikLauncherSession(mainServerCollection);
//            AuthenticationSessionRI authenticationSession = new AuthenticationSession(authenticationServerCollection);
            BhaashikLauncherSessionRI bhaashikLauncherSession = new BhaashikLauncherSession();
            AuthenticationSessionRI authenticationSession = new AuthenticationSession();

//            ((BhaashikLauncherSession) bhaashikLauncherSession);.setAuthenticationSession((AuthenticationSession) authenticationSession);

            //2. create the registry
            Registry registry;

            registry = LocateRegistry.createRegistry(1099);
                //3. export the object
            //        RMIFileSystemRI pp = (RMIFileSystemRI)UnicastRemoteObject.exportObject((Remote) p,0);
            //4. register the remote object of the registry
            //        registry.rebind("bhaashikRMIProtocol", (Remote) pp);
            registry.rebind(LAUNCHER_LOGIN, bhaashikServerLauncher);
//            AuthenticationEntry authenticationEntryInterface = (AuthenticationEntry)UnicastRemoteObject.exportObject((Remote) authenticationEntry,0);
//            registry.rebind(AUTH_LOGIN, (Remote) authenticationEntryInterface);
            registry.rebind(AUTH_LOGIN, authenticationEntry);

//            registry.rebind(LAUNCHER_SESSION, bhaashikLauncherSession);
//            registry.rebind(AUTH_SESSION, authenticationSession);
            BhaashikLauncherSessionRI bhaashikLauncherSessionRI = (BhaashikLauncherSessionRI)UnicastRemoteObject.exportObject((Remote) bhaashikLauncherSession, 0);
            AuthenticationSessionRI authenticationSessionRI = (AuthenticationSessionRI)UnicastRemoteObject.exportObject((Remote) authenticationSession, 0);
            // Session classes are not Unicast...
            registry.rebind(LAUNCHER_SESSION, bhaashikLauncherSessionRI);
            registry.rebind(AUTH_SESSION, authenticationSessionRI);

            ((BhaashikServerLauncher) bhaashikServerLauncher).setConnected(true);
            
            //5. create the shared directory
            //        pp.createDirectory(pp.getDefaultDirectoryPath());
            //        bhaashikServerLaucher.getRMIFileSystem().createDirectory(bhaashikServerLaucher.getRMIFileSystem().getDefaultDirectoryPathOnServer());

//            Thread bhaashikMainServerThread = new Thread(mainServerCollection);
//            bhaashikMainServerThread.start();
//
//            Thread authenticationServerThread = new Thread(authenticationServerCollection);
//            authenticationServerThread.start();

            System.out.println("Server has started...");

        } catch (SQLException ex) {
                Logger.getLogger(BhaashikServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
                Logger.getLogger(BhaashikServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(BhaashikServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void setBhaashikLauncherSession(BhaashikLauncherSession bhaashikLauncherSession) {
//        this.bhaashikLauncherSession = bhaashikLauncherSession;
//    }
}

