/*
 * BlogTags.java
 *
 * Created on December 9, 2007, 11:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.ontology.writing;

import bhaashik.GlobalProperties;
import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;

/**
 *
 * @author anil
 */
public class Tags extends Categories implements BhaashikDOMElement {
    
    /** Creates a new instance of BlogTags */
    public Tags()
    {
        super();
    }

    public DOMElement getDOMElement()
    {
        DOMElement domElement = super.getDOMElement();
        DOMAttribute attribType = new DOMAttribute(new org.dom4j.QName(GlobalProperties.getIntlString("type")), GlobalProperties.getIntlString("tag"));
        domElement.add(attribType);
        
        return domElement;
    }
}
