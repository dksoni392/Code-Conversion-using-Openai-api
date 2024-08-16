/*
 * Created on Sep 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.servers;

import java.rmi.*;

import bhaashik.properties.PropertiesTable;

/**
 *  @author Anil Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UserManagerRI extends BhaashikServerRI{
    
    public String addUser(String user, String password) throws RemoteException;
    public String removeUser(String user) throws RemoteException;
    public String authenticateUser(String user, String password) throws RemoteException;
    public boolean isLoggedIn(String user) throws RemoteException;
    public String loginUser(String user, String password) throws RemoteException;
    public String logout(String user) throws RemoteException;

//    public PropertiesTable getUserInfo(String user) throws RemoteException;

    public PropertiesTable getUserTable(String user) throws RemoteException;
    public PropertiesTable getTaskTable(String user) throws RemoteException;
}
