/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.common.types;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author anil
 */
public final class SSFQueryTokenType extends BhaashikType implements Serializable {
    public final int ord;
    private static Vector types = new Vector();

    protected SSFQueryTokenType(String id, String pk) {
        super(id, pk);

        if (SSFQueryTokenType.last() != null) {
            this.prev = SSFQueryTokenType.last();
            SSFQueryTokenType.last().next = this;
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
        return new TypeEnumerator(SSFQueryTokenType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = SSFQueryTokenType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = SSFQueryTokenType.elements();
        return findFromId(enm, i);
    }

    public static final SSFQueryTokenType ON_DS = new SSFQueryTokenType("ON_DS", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType PARENTHESIS_START = new SSFQueryTokenType("PARENTHESIS_START", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType PARENTHESIS_END = new SSFQueryTokenType("PARENTHESIS_END", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType CARDINALITY_START = new SSFQueryTokenType("CARDINALITY_START", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType CARDINALITY_END = new SSFQueryTokenType("CARDINALITY_END", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType AND = new SSFQueryTokenType("AND", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType OR = new SSFQueryTokenType("OR", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType NOT = new SSFQueryTokenType("NOT", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType ATOM = new SSFQueryTokenType("ATOM", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType COMMAND = new SSFQueryTokenType("COMMAND", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType ARGUMENT_NAME = new SSFQueryTokenType("ARGUMENT_NAME", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType ARGUMENT_VALUE = new SSFQueryTokenType("ARGUMENT_VALUE", "bhaash.corpus.ssf.query");

    public static final SSFQueryTokenType WILDCARD_FIRST = new SSFQueryTokenType("WILDCARD_FIRST", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType WILDCARD_LAST = new SSFQueryTokenType("WILDCARD_LAST", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType WILDCARD_ALL = new SSFQueryTokenType("WILDCARD_ALL", "bhaash.corpus.ssf.query");
    public static final SSFQueryTokenType WILDCARD_RANGE = new SSFQueryTokenType("WILDCARD_RANGE", "bhaash.corpus.ssf.query");
}
