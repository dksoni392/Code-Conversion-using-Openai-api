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
public final class DSFConfigType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected DSFConfigType(String id, String pk) {
        super(id, pk);

        if (DSFConfigType.last() != null) {
            this.prev = DSFConfigType.last();
            DSFConfigType.last().next = this;
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
        return new TypeEnumerator(DSFConfigType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = DSFConfigType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = DSFConfigType.elements();
        return findFromId(enm, i);
    }

    public static final DSFConfigType DICTIONARY = new DSFConfigType("DictionaryConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType ENTRY = new DSFConfigType("EntryConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType SENSE = new DSFConfigType("SenseConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType FIELDSET = new DSFConfigType("FieldSetConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType FIELD = new DSFConfigType("FieldConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType SUBFIELD = new DSFConfigType("SubFieldConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType ALTVALUE = new DSFConfigType("AltValueConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType CWORD = new DSFConfigType("CWordConfig", "bhaash.resources.dsf.config");

    public static final DSFConfigType ADDINFO_CONFIG = new DSFConfigType("AddInfoConfig", "bhaash.resources.dsf.config");
    public static final DSFConfigType CONFIG_CONDITIONS = new DSFConfigType("ConfigConditions", "bhaash.resources.dsf.config");
    public static final DSFConfigType GLOBAL_SEPARATORS = new DSFConfigType("GlobalSeparators", "bhaash.resources.dsf.config");
}
