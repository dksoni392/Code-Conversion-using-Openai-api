/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import bhaashik.properties.KeyValueProperties;

import javax.swing.*;
import bhaashik.util.BhaashikStringUtils;

/**
 *
 * @author anil
 */
public class GlobalProperties implements Serializable {

    public static KeyValueProperties properties = new KeyValueProperties();
    public static KeyValueProperties clientModes = new KeyValueProperties();
//    public static ResourceBundle bhaashikResourceBundle = ResourceBundle.getBundle("bhaash");
    public static ResourceBundle bhaashikResourceBundle = ResourceBundle.getBundle("bhaashik");

    protected static String BHAASHIK_HOME;
    protected static String USER_HOME;

    public static KeyValueProperties getProperties()
    {
        if(properties.countProperties() == 0)
        {
            try
            {
                properties.read(GlobalProperties.getHomeDirectory() + "/" + "props/bhaashik-props.txt", bhaashikResourceBundle.getString("UTF-8"));
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex)
            {
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return properties;
    }

    public static void setProperties(KeyValueProperties props)
    {
        properties = props;
    }

    public static void readProperties(String path, String cs) throws FileNotFoundException, IOException
    {
        properties.read(path, cs);
    }

    public static KeyValueProperties getClientModes()
    {
        if(clientModes.countProperties() == 0)
        {
            try
            {
                clientModes.read(GlobalProperties.getHomeDirectory() + "/" + "props/client-modes.txt", bhaashikResourceBundle.getString("UTF-8"));
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex)
            {
                Logger.getLogger(GlobalProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return clientModes;
    }
    public static void setClientModes(KeyValueProperties props)
    {
        clientModes = props;
    }

    public static void readClientModes(String path, String cs) throws FileNotFoundException, IOException
    {
        clientModes.read(path, cs);
    }

    public static String getHomeDirectory()
    {
        BHAASHIK_HOME = System.getenv("BHAASHIK_HOME");
        
        System.out.println("BAASHIK_HOME=" + BHAASHIK_HOME);

        Class myClass = BhaashikMain.class;
        
        if(BHAASHIK_HOME != null && !BHAASHIK_HOME.isEmpty())
        {
            return BHAASHIK_HOME;
        }

        String className = BhaashikMain.class.getName();

        className = className.replaceAll("bhaashik.", "");

        URL url = myClass.getResource(className + ".class");

        String path = url.getPath();

//        String bhaashikJarSubpath = null;
        int lastIndexLongOf = -1;
        int lastIndexShortOf = -1;

        String bhaashikPathName = null;
        String bhaashikPathNameShort = null;

        String bhaashikNameRegexLong = "/Bhaashik[^/]*/";
        String bhaashikNameRegexShort = "/Bhaashik/";
 
        if(BHAASHIK_HOME == null)
        {        
    //        System.out.println(path);

//            if(path.contains(".jar")) {
//                System.out.println("Bhaashik path: " + path);

                lastIndexLongOf = BhaashikStringUtils.getLastStartIndexOfRegex(path, bhaashikNameRegexLong);
                bhaashikPathName = BhaashikStringUtils.getLastOccurrenceOfRegex(path, bhaashikNameRegexLong);

                lastIndexShortOf = BhaashikStringUtils.getLastStartIndexOfRegex(path, bhaashikNameRegexShort);
                bhaashikPathNameShort = BhaashikStringUtils.getLastOccurrenceOfRegex(path, bhaashikNameRegexShort);

                if(bhaashikPathNameShort.length() < bhaashikPathName.length())
                {
                    bhaashikPathName = bhaashikNameRegexShort;
                }

//                bhaashikJarSubpath = BhaashikStringUtils.getLastStartIndexOfRegex(path, "/Bhaashik", "");
//                bhaashikJarName = StringUtils.substringBetween(path, "/", ".jar");

//                System.out.println("Bhaashik path: " + path);

//                if (bhaashikJarSubpath == null || bhaashikJarSubpath.equals(path)) {
//                    bhaashikJarSubpath = BhaashikStringUtils.getLastOccurrenceOfRegex(path, "/Bhaashik", ".jar");
//                    bhaashikJarSubpath = StringUtils.substringBetween(path, "\\", ".jar");
//                }
//            }
//            else if(path.contains(".class")) {
//                System.out.println("Bhaashik path: " + path);
//
//                bhaashikJarSubpath = BhaashikStringUtils.getLastOccurrenceOfRegex(path, "/Bhaashik", ".class");
////                bhaashikJarName = StringUtils.substringBetween(path, "/", ".class");
//
//                System.out.println("Bhaashik path: " + path);
//
//                if (bhaashikJarSubpath == null || bhaashikJarSubpath.equals(path)) {
//                    bhaashikJarSubpath = BhaashikStringUtils.getLastOccurrenceOfRegex(path, "/Bhaashik", ".class");
////                    bhaashikJarName = StringUtils.substringBetween(path, "\\", ".class");
//                }
//            }

//            System.out.println("Bhaashik path: "+ path);

//            if(bhaashikJarSubpath != null)
//            {
//                System.out.println("Bhaashik jar name: "+ bhaashikJarSubpath);
////                int ind = path.lastIndexOf("Bhaashik.jar");
//                int ind = path.lastIndexOf(bhaashikJarSubpath);
//
//    //            System.out.println(path.substring(0, ind - 5));
//    //            System.out.println(path.substring(0, ind));
//
//                if(path.contains("dist/" + bhaashikJarSubpath))
//                    path = path.substring(0, ind - 5);
//                else if(path.contains("lib/" + bhaashikJarSubpath))
//                    path = path.substring(0, ind - 4);
//                else
//                    path = path.substring(0, ind);
//
//                System.out.println("Bhaashik path: "+ path);
//            }
        }
//            else
//            {
//                int ind = path.lastIndexOf("Bhaashik/");
//
//                 path = path.substring(0, ind + 7);
//
//    //             System.out.println(path);
//            }

        int ind = path.lastIndexOf(bhaashikPathName);

//        BHAASHIK_HOME = path.substring(0, ind + 8);

        String bhaashikName = "Bhaashik";

//        BHAASHIK_HOME = path.substring(0, ind + 9);
        BHAASHIK_HOME = path.substring(0, ind + bhaashikName.length());
//        path = path.substring(0, path.length() - className.length() + 1);

        if(BHAASHIK_HOME.startsWith("/"))
        {
            BHAASHIK_HOME = BHAASHIK_HOME.substring(1);
        }

        if(BHAASHIK_HOME == null) {
            JOptionPane.showMessageDialog(null, "BHAASHIK_HOME not define.\nPlease set the environment variable BHAASHIK_HOME to the\npath of the Bhaashik folder.", GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }

        BHAASHIK_HOME = BHAASHIK_HOME.replaceAll("file:", "");

//        System.out.println(BHAASHIK_HOME);

//        KeyValueProperties props = getProperties();
//
//        return props.getPropertyValue(bhaashikResourceBundle.getString("BHAASHIK_HOME"));
        return BHAASHIK_HOME;
    }

//    public static void setHomeDirectory(String d)
//    {
//        BHAASHIK_HOME = d;
////        KeyValueProperties props = getProperties();
////
////        props.addProperty(bhaashikResourceBundle.getString("BHAASHIK_HOME"), d);
//    }

    public static String getWorkspaceDirectory()
    {
        KeyValueProperties props = getProperties();

        return props.getPropertyValue(bhaashikResourceBundle.getString("WORKSPACE"));
    }

    public static void setWorkspaceDirectory(String d)
    {
        KeyValueProperties props = getProperties();

        props.addProperty(bhaashikResourceBundle.getString("WORKSPACE"), d);
    }

    public static ResourceBundle getResourceBundle()
    {
        if(bhaashikResourceBundle == null)
            bhaashikResourceBundle = ResourceBundle.getBundle("bhaashik");

        return bhaashikResourceBundle;
    }

    public static String getIntlString(String key)
    {
        if(bhaashikResourceBundle == null)
            bhaashikResourceBundle = ResourceBundle.getBundle("bhaashik");

        if(!bhaashikResourceBundle.containsKey(key))
            return key;

        return bhaashikResourceBundle.getString(key);
    }

    public static String resolveRelativePath(String path)
    {
        return resolveRelativePath(path, true);
    }

    public static String resolveRelativePath(String path, boolean writable)
    {
        if((new File(path)).isAbsolute())
            return path;

        USER_HOME = System.getProperty("user.home");

        File p = new File(USER_HOME, ".bhaash");

        if(!p.exists())
        {
            p.mkdir();
        }

        if(!p.isDirectory())
        {
            if(p.isFile())
                p.delete();
            
            p.mkdir();
        }

        File f = new File(p, path);

        if(writable && !f.canWrite())
            f.getParentFile().mkdirs();

        if((!writable && !f.canRead()) || (writable && !f.canWrite()))
        {
            p = new File(getHomeDirectory());
            f = new File(p, path);
        }

        if((!writable && !f.canRead()) || (writable && !f.canWrite()))
            return (new File(path)).getAbsolutePath();

//        System.out.println(f.getAbsolutePath());
        
        return f.getAbsolutePath();
    }

    public static boolean isRelativePath(String path)
    {
        File fpath = new File(path);

        return !fpath.getAbsolutePath().equals(path);
    }

    public static void main(String args[])
    {
        GlobalProperties.resolveRelativePath("props/fs-schema.txt");
    }
}
