/*
 * Time.java
 *
 * Created on December 14, 2007, 5:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.ontology.writing;

import java.io.PrintStream;

import bhaashik.GlobalProperties;
import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;

/**
 *
 * @author anil
 */
public class Time implements BhaashikDOMElement {

    String time;
    
    /** Creates a new instance of Time */
    public Time() {
    }

    public Time(String t) {
        time = t;
    }
    
    public String getTime()
    {
        return time;
    }

    public void setTime(String t)
    {
        time = t;
    }            
        
    public org.dom4j.dom.DOMElement getDOMElement()
    {
        DOMElement domElement = new DOMElement(GlobalProperties.getIntlString("Time"));
        
        DOMAttribute attribTime = new DOMAttribute(new org.dom4j.QName(GlobalProperties.getIntlString("time")), time);
        domElement.add(attribTime);
        
        return domElement;
    }
    
    public String getXML()
    {
        org.dom4j.dom.DOMElement element = getDOMElement();
        return element.asXML();
    }

    public void printXML(PrintStream ps)
    {    
        ps.println(getXML());
    }

    public void readXML(org.w3c.dom.Element domElement) {
        time = domElement.getAttribute(GlobalProperties.getIntlString("time"));
    }
}
