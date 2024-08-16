/*
 * Created on Aug 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.properties;

import java.io.*;
import java.util.*;

import bhaashik.GlobalProperties;
import bhaashik.xml.dom.BhaashikDOMElement;

import bhaashik.table.BhaashikTableModel;

/**
 *  @author Anil Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PropertiesTable extends BhaashikTableModel implements Serializable, BhaashikProperties, BhaashikDOMElement {

    /**
     * 
     */
    
    public PropertiesTable()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public PropertiesTable(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public PropertiesTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public PropertiesTable(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public PropertiesTable(Vector columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public PropertiesTable(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    public PropertiesTable(String propFile, String charset) throws FileNotFoundException, IOException
    {
        super(propFile, charset);
    }

    public Object clone() throws CloneNotSupportedException// copyFS($fs)
    {
        PropertiesTable obj = null;

        String sfname = GlobalProperties.getHomeDirectory() + "/" + "PropertiesTable-tmp.tmp";
        
        try {
            FileOutputStream out = new FileOutputStream(sfname);
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(this);
            os.flush();

            FileInputStream in = new FileInputStream(sfname);
            ObjectInputStream is = new ObjectInputStream(in);
            obj = (PropertiesTable) is.readObject();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;
    }
    
    public static void main(String[] args) {
        PropertiesTable pt = null;
        
        try {
            pt = new PropertiesTable(GlobalProperties.getHomeDirectory() + "/" + "userData/userdata1.txt", GlobalProperties.getIntlString("UTF-8"));
            pt.print(System.out);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
