/*
 * Created on Sep 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.apps;

import java.rmi.*;

import bhaashik.properties.PropertiesManager;

/**
 *  @author Anil Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Application {

    public PropertiesManager getPropertiesManager() throws RemoteException;
    public void setPropertiesManager(PropertiesManager pm) throws RemoteException;
}
