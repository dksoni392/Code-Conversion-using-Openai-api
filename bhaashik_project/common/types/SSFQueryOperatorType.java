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
public  final class SSFQueryOperatorType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected SSFQueryOperatorType(String id, String pk) {
        super(id, pk);

        if (SSFQueryOperatorType.last() != null) {
            this.prev = SSFQueryOperatorType.last();
            SSFQueryOperatorType.last().next = this;
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
        return new TypeEnumerator(SSFQueryOperatorType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = SSFQueryOperatorType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = SSFQueryOperatorType.elements();
        return findFromId(enm, i);
    }

//    public static boolean isAtom(String queryString)
//    {
//        if(queryString == null || queryString.equals(""))
//            return false;
//
//        Enumeration<SSFQueryOperatorType> enm = SSFQueryOperatorType.elements();
//
//        while(enm.hasMoreElements())
//        {
//            SSFQueryOperatorType otype = enm.nextElement();
//
//            if(otype.id.equals(MATCH.id)) || otype.id.equals(EQUAL.id))
//
//            if(queryString.contains(otype.getId()))
//                return false;
//        }
//
//        return true;
//    }
//
    public static final SSFQueryOperatorType ON_DS = new SSFQueryOperatorType("ON_DS", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType PARENTHESIS = new SSFQueryOperatorType("PARENTHESIS", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType AND = new SSFQueryOperatorType("AND", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType OR = new SSFQueryOperatorType("OR", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType NOT = new SSFQueryOperatorType("NOT", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType EQUAL = new SSFQueryOperatorType("EQUAL", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType NOT_EQUAL = new SSFQueryOperatorType("NOT_EQUAL", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType LIKE = new SSFQueryOperatorType("LIKE", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType NOT_LIKE = new SSFQueryOperatorType("NOT_LIKE", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType ASSIGN = new SSFQueryOperatorType("ASSIGN", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType VARIABLE = new SSFQueryOperatorType("VARIABLE", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType VALUE = new SSFQueryOperatorType("VALUE", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType LEAF = new SSFQueryOperatorType("LEAF", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType LEVEL = new SSFQueryOperatorType("LEVEL", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType COMMAND = new SSFQueryOperatorType("COMMAND", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType RETURN = new SSFQueryOperatorType("RETURN", "bhaash.corpus.ssf.query");
    public static final SSFQueryOperatorType DESTINATION = new SSFQueryOperatorType("DESTINATION", "bhaash.corpus.ssf.query");
}
