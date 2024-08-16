/*
 * TaskType.java
 *
 * Created on November 1, 2005, 5:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.common.types;

import java.io.*;
import java.util.*;

/**
 *
 *  @author Anil Kumar Singh Kumar Singh
 */
public class AggregateResourceType extends BhaashikType implements Serializable
{
 
    public final int ord;
    private static Vector types = new Vector();
   
    /** Creates a new instance of TaskType */
    protected AggregateResourceType(String id, String pk) {
        super(id, pk);

        if (AggregateResourceType.last() != null) {
            this.prev = AggregateResourceType.last();
            AggregateResourceType.last().next = this;
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
        return new TypeEnumerator(AggregateResourceType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = AggregateResourceType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = AggregateResourceType.elements();
        return findFromId(enm, i);
    }
   
    public static final AggregateResourceType PARALLEL_MARKUP = new AggregateResourceType("ParallelMarkupTask", "bhaash.tasks");
    public static final AggregateResourceType SYNTACTIC_ANNOTATION = new AggregateResourceType("SyntacticAnnotationTask", "bhaash.tasks");
}
