/*
 * BhaashikServerRI.java
 *
 * Created on October 31, 2005, 6:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.servers;

import java.rmi.*;

import bhaashik.properties.PropertiesManager;

/**
 *
 *  @author Anil Kumar Singh
 */
//public interface BhaashikServerRI extends Remote {
public interface BhaashikServerRI  {
    public PropertiesManager getPropertiesManager() throws RemoteException;
    public String checkConnection() throws RemoteException;
    
}
