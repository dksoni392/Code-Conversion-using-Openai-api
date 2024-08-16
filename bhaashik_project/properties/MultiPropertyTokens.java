/*
 * MultiPropertyTokens.java
 *
 * Created on April 2, 2006, 4:54 PM
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
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 *
 * @author anil
 */
public class MultiPropertyTokens extends ResourceImpl implements Serializable, BhaashikProperties, BhaashikDOMElement
{
    protected LinkedHashMap multiprops;
    
    /** Creates a new instance of MultiKeyValueProperties */
    public MultiPropertyTokens()
    {
	multiprops = new LinkedHashMap(0, 3);
    }

    public MultiPropertyTokens(String propFile, String cs) throws FileNotFoundException, IOException
    {
	read(propFile, cs);
    }

    public MultiPropertyTokens(LinkedHashMap multiprops)
    {
	this.multiprops = multiprops;
    }

    public void print(java.io.PrintStream ps)
    {
	PropertyTokens.printMany(multiprops, ps);
    }

    public int read(String f, String charset) throws FileNotFoundException, IOException
    {
	multiprops = PropertyTokens.readMany(f, charset);

	return multiprops.size();
    }
        
    public int save() throws FileNotFoundException, IOException
    {
        PropertyTokens.saveMany(multiprops, filePath, charset);
        
        return 0;
    }

    public int save(String f, String cs) throws FileNotFoundException, IOException, UnsupportedEncodingException
    {
        PropertyTokens.saveMany(multiprops, f, cs);
        
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

    public PropertyTokens getPropertiesValue(String p /* Properties key */)
    {
        return (PropertyTokens) multiprops.get(p);   
    }

    public int addProperties(String p /* properties key */, PropertyTokens v /*properties value */)
    {
        multiprops.put(p, v);
        
        return multiprops.size();   
    }

    public PropertyTokens removeProperties(String p /* properties key */)
    {
        return (PropertyTokens) multiprops.remove(p);   
    }

    public DOMElement getDOMElement() {
        DOMElement domElement = new DOMElement(GlobalProperties.getIntlString("MultiPropertyTokens"));
        Iterator itr = multiprops.keySet().iterator();

        while(itr.hasNext())
        {
            String key = (String) itr.next();
            PropertyTokens val = (PropertyTokens) multiprops.get(key);
            
            DOMElement ptElement = val.getDOMElement();
            DOMAttribute attribName = new DOMAttribute(ptElement, new org.dom4j.QName(GlobalProperties.getIntlString("name")), key);
            domElement.add(ptElement);
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
        NodeList elements = domElement.getElementsByTagName(GlobalProperties.getIntlString("PropertyTokens"));

        int count = elements.getLength();
        
        for (int i = 0; i < count; i++)
        {
            org.w3c.dom.Element propsElement = (org.w3c.dom.Element) elements.item(i);
            String key = propsElement.getAttribute(GlobalProperties.getIntlString("name"));
            
            PropertyTokens props = new PropertyTokens();
            props.readXML(propsElement);

            multiprops.put(key, props);
        }        
    }
}
