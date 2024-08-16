/*
 * Created on Sep 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
public final class DSFType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected DSFConfigType dsfct;

    protected DSFType(String id, DSFConfigType dsfct, String pk) {
        super(id, pk);
        this.dsfct = dsfct;

        if (DSFType.last() != null) {
            this.prev = DSFType.last();
            DSFType.last().next = this;
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
        return new TypeEnumerator(DSFType.first());
    }

    public DSFConfigType getDSFConfigType() { return this.dsfct; }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = DSFType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = DSFType.elements();
        return findFromId(enm, i);
    }
    
    public static final DSFType DICTIONARY = new DSFType("Dictionary", DSFConfigType.DICTIONARY, "bhaash.resources.dsf");
    public static final DSFType ENTRY = new DSFType("Entry", DSFConfigType.ENTRY, "bhaash.resources.dsf");
    public static final DSFType SENSE = new DSFType("Sense", DSFConfigType.SENSE, "bhaash.resources.dsf");
    public static final DSFType FIELDSET = new DSFType("FieldSet", DSFConfigType.FIELDSET, "bhaash.resources.dsf");
    public static final DSFType FIELD = new DSFType("Field", DSFConfigType.FIELD, "bhaash.resources.dsf");
    public static final DSFType SUBFIELD = new DSFType("SubField", DSFConfigType.SUBFIELD, "bhaash.resources.dsf");
    public static final DSFType ALTVALUE = new DSFType("AltValue", DSFConfigType.ALTVALUE, "bhaash.resources.dsf");
    public static final DSFType CWORD = new DSFType("CWord", DSFConfigType.CWORD, "bhaash.resources.dsf");
}
