/*
 * AccessType.java
 *
 * Created on November 10, 2005, 4:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.common.types;

import java.io.*;
import java.util.*;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class AccessType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected AccessType(String id, String pk) {
        super(id, pk);

        if (AccessType.last() != null) {
            this.prev = AccessType.last();
            AccessType.last().next = this;
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
        return new TypeEnumerator(AccessType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = AccessType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = AccessType.elements();
        return findFromId(enm, i);
    }

    public static final AccessType INVISIBLE = new AccessType("Invisible", "bhaash.access");
    public static final AccessType READABLE = new AccessType("Readable", "bhaash.access");
    public static final AccessType CLONEABLE = new AccessType("Cloneable", "bhaash.access");
    public static final AccessType APPENDABLE = new AccessType("Appendable", "bhaash.access");
    public static final AccessType MODIFIABLE = new AccessType("Modifiable", "bhaash.access");
    public static final AccessType FULL = new AccessType("Full", "bhaash.access");
}
