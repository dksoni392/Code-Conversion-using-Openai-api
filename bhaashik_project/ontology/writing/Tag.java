/*
 * BlogTag.java
 *
 * Created on December 9, 2007, 11:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.ontology.writing;

import bhaashik.GlobalProperties;
import bhaashik.xml.dom.BhaashikDOMElement;

/**
 *
 * @author anil
 */
public class Tag extends Category implements BhaashikDOMElement {
    
    /** Creates a new instance of BlogTag */
    public Tag()
    {
    }    

    public Tag(String l)
    {
        super(l);
    }    
    
    public String getType()
    {
        return GlobalProperties.getIntlString("tag");
    }
}
