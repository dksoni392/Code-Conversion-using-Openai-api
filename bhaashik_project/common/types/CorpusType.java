/*
 * Created on Sep 23, 2005
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
public final class CorpusType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected CorpusType(String id, String pk) {
        super(id, pk);

        if (CorpusType.last() != null) {
            this.prev = CorpusType.last();
            CorpusType.last().next = this;
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
        return new TypeEnumerator(CorpusType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = CorpusType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = CorpusType.elements();
        return findFromId(enm, i);
    }

    public static final CorpusType RAW = new CorpusType("RawCorpus", "bhaash.corpus");
    public static final CorpusType POS_TAGGED = new CorpusType("POSTaggedCorpus", "bhaash.corpus");
    public static final CorpusType VERTICAL_POS_TAGGED = new CorpusType("VerticalPOSTaggedCorpus", "bhaash.corpus");
    public static final CorpusType CHUNKED = new CorpusType("Chunked", "bhaash.corpus");
    public static final CorpusType BI_FORMAT = new CorpusType("BI_FORMAT", "bhaash.corpus");
    public static final CorpusType SSF_FORMAT = new CorpusType("SSFFormat", "bhaash.corpus");
    public static final CorpusType CONLLU_FORMAT = new CorpusType("CONLLUFormat", "bhaash.corpus");

    public static final CorpusType STANDFORD_PARSER = new CorpusType("STANDFORD_PARSER", "bhaash.corpus");
    public static final CorpusType XML_FORMAT = new CorpusType("XMLFormat", "bhaash.corpus");
    public static final CorpusType TYPECRAFT_FORMAT = new CorpusType("TypeCraftFormat", "bhaash.corpus");
    public static final CorpusType GATE_FORMAT = new CorpusType("GATEFormat", "bhaash.corpus");
//    public static final CorpusType XML_SSF_TAGGED = new CorpusType("XMLSSFCorpus", "bhaash.corpus");
    public static final CorpusType NGRAM = new CorpusType("NGram", "bhaash.corpus");
    public static final CorpusType DICTIONARY = new CorpusType("Dictionary", "bhaash.corpus");
    public static final CorpusType HINDENCORP_FORMAT = new CorpusType("HindenCorpFormat", "bhaash.corpus");
}
