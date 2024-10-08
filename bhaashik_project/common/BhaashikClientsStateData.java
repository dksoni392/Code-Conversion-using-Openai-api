/*
 * BhaashikClientsStateData.java
 *
 * Created on March 15, 2009, 1:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

import bhaashik.GlobalProperties;
import bhaashik.common.types.ClientType;
import bhaashik.properties.KeyValueProperties;
import bhaashik.properties.MultiKeyValueProperties;

/**
 *
 * @author Anil Kumar Singh
 */
public class BhaashikClientsStateData {
    
    protected static String stateDataPath = GlobalProperties.resolveRelativePath("props/state/clients-state.txt");
    protected static MultiKeyValueProperties stateData;
    
    /** Creates a new instance of BhaashikStateData */
    public BhaashikClientsStateData() {
    }
    
    protected static void init() {
        stateData = new MultiKeyValueProperties();
        stateData.setFilePath(stateDataPath);
        stateData.setCharset("UTF-8");
        stateData.setLangEnc("hin::utf8");
        stateData.setName("Bhaashik Clients State");
        
        File stateDataFile = new File(stateDataPath);
        
        if(stateDataFile.exists() && stateDataFile.length() > 0L)
        {
            try {
                stateData.read(stateDataPath, "UTF-8");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            try {
                stateDataFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        Enumeration enm = ClientType.elements();
        
        while(enm.hasMoreElements())
        {
            ClientType clientType = (ClientType) enm.nextElement();

            if(stateData.getPropertiesValue(clientType.toString()) == null) {
                stateData.addProperties(clientType.toString(), new KeyValueProperties());
            }
        }
    }

    public static MultiKeyValueProperties getSateData()
    {
        if(stateData == null)
            init();
            
        return stateData;
    }
    
    public static void reset()
    {
        init();
    }

    public static void save()
    {
        if(stateData == null) {
            init();
        }
        
        try {
            stateData.save();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
