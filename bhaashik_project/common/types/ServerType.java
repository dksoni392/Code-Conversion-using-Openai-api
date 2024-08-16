/*
 * Created on Sep 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.common.types;

import java.io.*;
import java.util.*;
import java.rmi.*;
//import java.rmi.activation.*;


/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class ServerType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected String title;
    
    protected ServerType(String title, String id, String pk) {
        super(id, pk);
        this.title = title;

        if (ServerType.last() != null) {
            this.prev = ServerType.last();
            ServerType.last().next = this;
        }

        types.add(this);
	ord = types.size();
    }

    public static int size()
    {
        return types.size();
    }
    
    public static BhaashikType first()
    {
        return (BhaashikType) types.get(0);
    }
    
    public static BhaashikType last()
    {
        if(types.size() > 0)
            return (BhaashikType) types.get(types.size() - 1);
        
        return null;
    }

    public static BhaashikType getType(int i)
    {
        if(i >=0 && i < types.size())
            return (BhaashikType) types.get(i);
        
        return null;
    }

    public static Enumeration elements()
    {
        return new TypeEnumerator(ServerType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = ServerType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = ServerType.elements();
        return findFromId(enm, i);
    }
    
    public static Remote getNewServer(ServerType serverType, String address, boolean remote)
    {
        Remote server = null;
//        String ststr = serverType.toString();
        String ststr = serverType.getId();

//        if(System.getSecurityManager() == null)
//            System.setSecurityManager(new RMISecurityManager());

        if(remote)
        {
            try {
                String location = "rmi://" + address + "/" + ststr;
		
		System.out.println("Location: " + location);

                // Since you can't create an instance of an interface, what we get 
                // back from the lookup method is a remote reference to an object
                // that implements a RemoteInterface.
                //  
                // Then we cast the remote reference (serialized stub instance)
                // returned from Naming.lookup to the RemoteInterface so we can
                // call the interface method(s).    
                //         

                server = (Remote) Naming.lookup(location);
		System.out.println("Server Class Name: " + server.getClass().getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
        {
//            try {
//                if(serverType == BHAASHIK_MAIN)
//                    server = new BhaashikMainServer(BhaashikMain.getBhaashikComponentPM(ststr));
//                else if(serverType == USER_MANAGER)
//                    server = new UserManagerServer(BhaashikMain.getBhaashikComponentPM(ststr));
//            } catch (RemoteException ex) {
//                ex.printStackTrace();
//            }
        }
        
        return server;
    }

    public String toString() { return this.title; }

    public static final ServerType BHAASHIK_SERVER = new ServerType("Bhaashik Server", "BhaashikServer", "bhaash.servers.impl");
//    public static final ServerType BHAASHIK_MAIN = new ServerType("BhaashikMainServer", "bhaash.servers");
    public static final ServerType USER_MANAGER = new ServerType("User Manager Server", "UserManagerServer", "bhaash.servers.impl");
    public static final ServerType RESOURCE_MANAGER = new ServerType("Resource Manager Server", "ResourceManagerServer", "bhaash.servers.impl");
//    public static final ServerType TASK_MANAGER = new ServerType("TaskManagerServer", "bhaash.servers");
//    public static final ServerType PROPERTIES_MANAGER = new ServerType("Properties Manager Server", "PropertiesManagerServer", "bhaash.servers");
//    public static final ServerType PARALLEL_MARKUP = new ServerType("ParallelMarkupServer", ParallelMarkup, "bhaash.servers");
//    public static final ServerType SYNTACTIC_ANNOTATION = new ServerType("SyntacticAnnotationServer", "bhaash.servers");
//    public static final ServerType PROPERTIES_MANAGER = new ServerType("PropertiesManagerServer", "bhaash.servers");
//    public static final ServerType UD_MANAGER = new ServerType("UDManagerServer", "bhaash.servers");
//    public static final ServerType CORPUS_MANAGER = new ServerType("CorpusManagerServer", "bhaash.servers");
//    public static final ServerType NGRAMLM = new ServerType("NGramLMServer", "bhaash.servers");
//    public static final ServerType IBM_MODEL = new ServerType("IBMModelServer", "bhaash.servers");
//    public static final ServerType DSF = new ServerType("DSFServer", "bhaash.servers");
//    public static final ServerType GTAC = new ServerType("GTACServer", "bhaash.servers");
//    public static final ServerType PARALLEL_CORPUS = new ServerType("ParallelCorpusServer", "bhaash.servers");
//    public static final ServerType ML_ANNOTATION = new ServerType("MLAnnotationServer", "bhaash.servers");
//    public static final ServerType TEXT_ENCODING = new ServerType("TextEncodingServer", "bhaash.servers");
//    public static final ServerType NLI = new ServerType("NLIServer", "bhaash.servers");
//    public static final ServerType NS = new ServerType("NSServer", "bhaash.servers");
}
