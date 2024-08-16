/*
 * BhaashikBackup.java
 *
 * Created on May 22, 2007, 8:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bhaashik.GlobalProperties;

/**
 *
 * @author anil
 */
public class BhaashikBackup implements FileChangeListener {
    
    public static String bakExt = GlobalProperties.getIntlString(".bak");
//    private static final BhaashikBackup instance = new BhaashikBackup();
    
    /** Creates a new instance of BhaashikBackup */
    public BhaashikBackup()
    {
    }

//    public static BhaashikBackup getInstance() {
//        return instance;
//    }
    
    public void fileChanged(String filePath)
    {
        try {
            BhaashikBackup.backup(filePath);
            System.out.println(GlobalProperties.getIntlString("File_") + filePath + GlobalProperties.getIntlString("_backed_up."));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void copy(String src, String dst) throws IOException
    {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024];
        int len;
        
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        
        in.close();
        out.close();
    }    

    public static void backup(String filePath) throws IOException
    {
        String bakFilePath = filePath + bakExt;
        
        File bakFile = new File(bakFilePath);
        bakFile.delete();
        
        BhaashikBackup.copy(filePath, bakFilePath);
    }
}
