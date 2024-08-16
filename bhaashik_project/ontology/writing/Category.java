/*
 * WebCorpusCategory.java
 *
 * Created on November 10, 2007, 2:06 AM
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
public class Category implements BhaashikDOMElement {
    
    String label;
    
    /** Creates a new instance of WebCorpusCategory */
    public Category() {
    }

    public Category(String l)
    {
        label = l;
    }
    
    public String getType()
    {
        return GlobalProperties.getIntlString("category");
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String l)
    {
        label = l;
    }

    public String toString()
    {
        return label;
    }

    public void printXML(PrintStream ps)
    {
        ps.println(getXML());
    }

    public DOMElement getDOMElement() {
        DOMElement domElement = new DOMElement(GlobalProperties.getIntlString("Category"));
        DOMAttribute attribType = new DOMAttribute(new org.dom4j.QName(GlobalProperties.getIntlString("type")), getType());
        domElement.add(attribType);
        
        domElement.setText(label);
        
        return domElement;
    }

    public String getXML() {
        org.dom4j.dom.DOMElement element = getDOMElement();
        return element.asXML();
    }

    public void readXML(org.w3c.dom.Element domElement) {
        label = domElement.getAttribute(GlobalProperties.getIntlString("label"));
    }
}
