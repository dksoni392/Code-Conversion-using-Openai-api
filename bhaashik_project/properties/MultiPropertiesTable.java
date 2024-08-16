/*
 * MultiPropertiesTable.java
 *
 * Created on April 2, 2006, 4:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.properties;

import java.io.*;
import java.util.*;

import bhaashik.GlobalProperties;
import bhaashik.xml.dom.BhaashikDOMElement;
import bhaashik.resources.ResourceImpl;
import bhaashik.table.BhaashikTableModel;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author anil
 */
public class MultiPropertiesTable extends ResourceImpl implements Serializable, BhaashikProperties, BhaashikDOMElement
{
    protected LinkedHashMap multiprops;
    
    /** Creates a new instance of MultiKeyValueProperties */
    public MultiPropertiesTable()
    {
        multiprops = new LinkedHashMap(0, 3);
    }

    public MultiPropertiesTable(String propFile, String cs) throws FileNotFoundException, IOException
    {
    	read(propFile, cs);
    }

    public MultiPropertiesTable(LinkedHashMap multiprops)
    {
        multiprops = new LinkedHashMap(0, 3);
        this.multiprops = multiprops;
    }

    public void print(java.io.PrintStream ps)
    {
	PropertiesTable.printMany(multiprops, ps);
    }

    public int read(String f, String charset) throws FileNotFoundException, IOException
    {
	multiprops = PropertiesTable.readMany(f, charset);

	return multiprops.size();
    }
        
    public int save() throws FileNotFoundException, IOException
    {
        PropertiesTable.saveMany(multiprops, filePath, charset);
        
        return 0;
    }

    public int save(String f, String cs) throws FileNotFoundException, IOException, UnsupportedEncodingException
    {
        PropertiesTable.saveMany(multiprops, f, cs);
        
        return 0;
    }
    
    public LinkedHashMap getMultiPropertiesMap()
    {
	return multiprops;
    }
    
    public int countProperties()
    {
        return multiprops.size();   
    }

    public Iterator getPropertiesKeys()
    {
        return multiprops.keySet().iterator();
    }

    public BhaashikTableModel getPropertiesValue(String p /* Properties key */)
    {
        return (BhaashikTableModel) multiprops.get(p);
    }

    public int addProperties(String p /* properties key */, BhaashikTableModel v /*properties value */)
    {
        multiprops.put(p, v);
        
        return multiprops.size();   
    }

    public BhaashikTableModel removeProperties(String p /* properties key */)
    {
        return (BhaashikTableModel) multiprops.remove(p);
    }

    public DOMElement getDOMElement() {
        DOMElement domElement = new DOMElement(GlobalProperties.getIntlString("MultiPropertiesTable"));
        Iterator itr = multiprops.keySet().iterator();

        while(itr.hasNext())
        {
            String key = (String) itr.next();
            PropertiesTable val = (PropertiesTable) multiprops.get(key);
            
            DOMElement kvpElement = val.getDOMElement();
            DOMAttribute attribName = new DOMAttribute(kvpElement, new org.dom4j.QName(GlobalProperties.getIntlString("name")), key);
            domElement.add(kvpElement);
        }
        
        return domElement;
    }

    public String getXML() {
        org.dom4j.dom.DOMElement element = getDOMElement();
        return element.asXML();
    }

    public void printXML(PrintStream ps) {
        ps.println(getXML());
    }

    public void readXML(Element domElement) {
        NodeList elements = domElement.getElementsByTagName(GlobalProperties.getIntlString("PropertiesTable"));

        int count = elements.getLength();
        
        for (int i = 0; i < count; i++)
        {
            org.w3c.dom.Element propsElement = (org.w3c.dom.Element) elements.item(i);
            String key = propsElement.getAttribute(GlobalProperties.getIntlString("name"));
            
            PropertiesTable props = new PropertiesTable();
            props.readXML(propsElement);

            multiprops.put(key, props);
        }        
    }
}
