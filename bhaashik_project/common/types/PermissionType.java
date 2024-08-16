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
public final class PermissionType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    /** Creates a new instance of PermissionType */
    protected PermissionType(String id, String pk) {
        super(id, pk);

        if (PermissionType.last() != null) {
            this.prev = PermissionType.last();
            PermissionType.last().next = this;
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
        return new TypeEnumerator(PermissionType.first());
    }


    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = PermissionType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = PermissionType.elements();
        return findFromId(enm, i);
    }

    public static final PermissionType CREATION = new PermissionType("Creation", "bhaash.users");
    public static final PermissionType DELETION = new PermissionType("Deletion", "bhaash.users");
    public static final PermissionType FULL_EDITING = new PermissionType("FullEditing", "bhaash.users");
    public static final PermissionType ADJUDICATION = new PermissionType("Adjudication", "bhaash.users");
    public static final PermissionType LIMITED_EDITING = new PermissionType("LimitedEditing", "bhaash.users");
}
