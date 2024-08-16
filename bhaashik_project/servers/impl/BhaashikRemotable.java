/*
 * BhaashikActivable.java
 *
 * Created on November 3, 2005, 10:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.servers.impl;

import java.io.*;
import java.rmi.*;
//import java.rmi.activation.*;

import bhaashik.GlobalProperties;
import bhaashik.servers.BhaashikServerRI;
import bhaashik.properties.PropertiesManager;

/**
 *
 *  @author Anil Kumar Singh
 */
//public abstract class BhaashikActivable
//public abstract class BhaashikRemotable extends RemoteServer
public abstract class BhaashikRemotable
    implements BhaashikServerRI, Serializable {

    protected final PropertiesManager propman;
    
    protected BhaashikRemotable(String propManPath, String cs) throws RemoteException, IOException {
        super();
        
        File propManFile = new File(propManPath);
        
        if(propManFile.exists())       
            propman = new PropertiesManager(propManPath, cs);
        else
            propman = null;
    }    
	
    public PropertiesManager getPropertiesManager() throws RemoteException
    {
        return propman;
    }
    
    public String checkConnection()
    {
        return GlobalProperties.getIntlString("Connected_successfully");
    }
}
