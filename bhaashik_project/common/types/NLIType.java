package bhaashik.common.types;

import java.io.*;
import java.util.*;

public final class NLIType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();
    
    protected String alias; // the alias XML tag
    protected String modelIdAttribute; // the attribute name whose value is the modelId

    protected NLIType(String id, String al, String at, String pk) {
        super(id, pk);

        this.alias = al;
        modelIdAttribute = at;

        if (NLIType.last() != null) {
            this.prev = NLIType.last();
            NLIType.last().next = this;
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
        return new TypeEnumerator(NLIType.first());
    }

    public String getAlias() { return this.alias; }
    public String getModelIdAttribute() { return this.modelIdAttribute; }

    public static NLIType findFromAlias(String al)
    {
        Enumeration enm = NLIType.elements();
        NLIType nlic = null;

        while(enm.hasMoreElements())
        {
            nlic = (NLIType) enm.nextElement();

            if(al.equals(nlic.getAlias()))
                return nlic;
        }

        return null;
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = NLIType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = NLIType.elements();
        return findFromId(enm, i);
    }

    // Aliases can later be read from the model files
    public static final NLIType NLIGENERIC = new NLIType("NLIGeneric", null, null, "bhaash.nli.lm");

    public static final NLIType NLIOBJECT = new NLIType("NLIObject", null, null, "bhaash.nli.common");
    public static final NLIType NLIREFERENCE = new NLIType("NLIReference", null, null, "bhaash.nli.common");

    public static final NLIType FEATURES = new NLIType("Features", "Ftrs", "features", "bhaash.nli.lm");
    public static final NLIType SYNONYM = new NLIType("Synonym", "Syn", "synonym", "bhaash.nli.lm.lmodel");
    public static final NLIType LMODELOBJECT = new NLIType("LModelObject", null, null, "bhaash.nli.lm.lmodel");
    public static final NLIType WORD = new NLIType("Word", "Wrd", "word", "bhaash.nli.lm.lmodel");
    public static final NLIType MWEX = new NLIType("MWE", "Mwe", "mwe", "bhaash.nli.lm.lmodel");

    public static final NLIType DMODELOBJECT = new NLIType("DModelObject", null, null, "bhaash.nli.lm.dmodel");
    public static final NLIType UNIT = new NLIType("Unit", "Unt", "unit", "bhaash.nli.lm.dmodel");
    public static final NLIType VALUE = new NLIType("Value", "Val", "value", "bhaash.nli.lm.dmodel");
    public static final NLIType ARGUMENT = new NLIType("Argument", "Arg", "argument", "bhaash.nli.lm.dmodel");
    public static final NLIType INTERACTION = new NLIType("Interaction", "Intrn", "interaction", "bhaash.nli.lm.dmodel");

    public static final NLIType SMODELOBJECT = new NLIType("SModelObject", null, null, "bhaash.nli.lm.smodel");
    public static final NLIType CHUNK = new NLIType("Chunk", "Chnk", "chunk", "bhaash.nli.lm.smodel");
    public static final NLIType TEMPLATE = new NLIType("Template", "Tmplt", "template", "bhaash.nli.lm.smodel");
}
