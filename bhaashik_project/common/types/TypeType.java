/*
 * TypeType.java
 *
 * Created on October 30, 2005, 5:35 PM
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
public final class TypeType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();
    
    /** Creates a new instance of TypeType */
    protected TypeType(String id, String pk) {
        super(id, pk);

        if (TypeType.last() != null) {
            this.prev = TypeType.last();
            TypeType.last().next = this;
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
        return new TypeEnumerator(TypeType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = TypeType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = TypeType.elements();
        return findFromId(enm, i);
    }

    public static final TypeType DATA_TYPE = new TypeType("DataType", "bhaash.common.types");
    public static final TypeType PROPERTY_TYPE = new TypeType("PropertyType", "bhaash.common.types");
    public static final TypeType CORPUS_TYPE = new TypeType("CorpusType", "bhaash.common.types");
    public static final TypeType DSF_TYPE = new TypeType("DSFType", "bhaash.common.types");
    public static final TypeType DSFCONFIG_TYPE = new TypeType("DSFConfigType", "bhaash.common.types");
    public static final TypeType NLI_TYPE = new TypeType("NLIType", "bhaash.common.types");
    public static final TypeType USER_TYPE = new TypeType("UserType", "bhaash.common.types");
    public static final TypeType SERVER_TYPE = new TypeType("ServerType", "bhaash.common.types");
    public static final TypeType CLIENT_TYPE = new TypeType("ClientType", "bhaash.common.types");
    public static final TypeType BLOG_TYPE = new TypeType("BlogType", "bhaash.common.types");
}
