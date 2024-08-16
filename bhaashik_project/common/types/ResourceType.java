/*
 * ResourceType.java
 *
 * Created on November 5, 2005, 1:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.common.types;

import java.io.*;
import java.util.*;

/**
 *
 * @author Anil Kumar Singh
 */
public final class ResourceType extends BhaashikType implements Serializable
{
    public final int ord;
    private static Vector types = new Vector();
   
    /** Creates a new instance of TaskType */
    protected ResourceType(String id, String pk) {
        super(id, pk);

        if (ResourceType.last() != null) {
            this.prev = ResourceType.last();
            ResourceType.last().next = this;
        }

        types.add(this);
	ord = types.size();
    }

    public static int size()
    {
        return types.size();
    }
    
    public static BhaashikType first()
    {
        return (BhaashikType) types.get(0);
    }
    
    public static BhaashikType last()
    {
        if(types.size() > 0)
            return (BhaashikType) types.get(types.size() - 1);
        
        return null;
    }

    public static BhaashikType getType(int i)
    {
        if(i >=0 && i < types.size())
            return (BhaashikType) types.get(i);
        
        return null;
    }
  
    public static Enumeration elements()
    {
        return new TypeEnumerator(ResourceType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = ResourceType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = ResourceType.elements();
        return findFromId(enm, i);
    }
   
    public static final ResourceType BHAASHIK_TABLE = new ResourceType("BhaashikTableModel", "bhaash.table");
    public static final ResourceType PROPERTY_TOKENS = new ResourceType("PropertyTokens", "bhaashik.properties");
    public static final ResourceType KEY_VALUE_PROPERTIES = new ResourceType("KeyValueProperties", "bhaashik.properties");
    public static final ResourceType SYNTACTIC_ANNOTATION = new ResourceType("SyntacticAnnotation", "bhaashik.properties");
}
