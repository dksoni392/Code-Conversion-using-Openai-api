/*
 * TaggedWordContextElement.java
 *
 * Created on January 28, 2009, 2:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.context;

import bhaashik.context.impl.FunctionalContextElement;

/**
 *
 * @author Anil Kumar Singh
 */
public class TaggedWordContextElement extends FunctionalContextElementImpl implements FunctionalContextElement {
    
    protected Object tag;
    
    /** Creates a new instance of TaggedWordContextElement */
    public TaggedWordContextElement() {
    }
    
    public Object getTag()
    {
        return tag;
    }    
    
    public void setTag(Object t)
    {
        tag = t;
    }    
}
