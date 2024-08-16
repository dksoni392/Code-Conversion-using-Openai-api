/*
 * BlogComment.java
 *
 * Created on December 26, 2007, 4:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.corpus.blog;

import java.io.PrintStream;

import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMElement;

/**
 *
 * @author Anil Kumar Singh
 */
public class BlogComment extends BlogEntry implements BhaashikDOMElement {
    
    /** Creates a new instance of BlogComment */
    public BlogComment() {
    }
    
    
    public void print(PrintStream ps)
    {
        ps.println("<BlogComment>\n");
        
//        categories
        
        int pcount = segments.size();
        for (int i = 0; i < pcount; i++)
        {
            
        }

        ps.println("\t\t</BlogComment>\n");
    }

    public DOMElement getDOMElement() {
        DOMElement domElement = (DOMElement) super.getDOMElement();
        
        domElement.setName("BlogComment");
        
        return domElement;
    }
}
