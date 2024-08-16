/*
 * BhaashikActivable.java
 *
 * Created on November 2, 2005, 7:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.servers.impl;

import java.io.*;
import java.rmi.*;
//import java.rmi.activation.*;
import bhaashik.servers.BhaashikServerRI;

/**
 *
 *  @author Anil Kumar Singh
 */
public abstract class BhaashikServer extends BhaashikRemotable
    implements BhaashikServerRI, Serializable {

    /**
     * 
     */
    protected BhaashikServer(String propManPath, String cs) throws RemoteException, IOException
    {
        super(propManPath, cs);
    }    
}
