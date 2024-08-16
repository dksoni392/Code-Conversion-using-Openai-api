/*
 * UserType.java
 *
 * Created on October 30, 2005, 5:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.common.types;

import java.io.*;
import java.util.*;

import bhaashik.GlobalProperties;

/**
 *
 *  @author Anil Kumar Singh Kumar Singh
 */
public final class UserType extends BhaashikType implements Serializable {
 
    public final int ord;
    private static Vector types = new Vector();
   
    /** Creates a new instance of UserType */
    protected UserType(String id, String pk) {
        super(id, pk);

        if (UserType.last() != null) {
            this.prev = UserType.last();
            UserType.last().next = this;
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
        return new TypeEnumerator(UserType.first());
    }


    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = UserType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = UserType.elements();
        return findFromId(enm, i);
    }
   
    public static final UserType ANONYMOUS = new UserType(GlobalProperties.getIntlString("Anonymous"), "bhaash.users");
    public static final UserType GUEST = new UserType(GlobalProperties.getIntlString("Guest"), "bhaash.users");
    public static final UserType EDITOR = new UserType(GlobalProperties.getIntlString("Editor"), "bhaash.users");
    public static final UserType ADJUDICATOR = new UserType(GlobalProperties.getIntlString("Adjudicator"), "bhaash.users");
    public static final UserType ADMINISTRATOR = new UserType(GlobalProperties.getIntlString("Administrator"), "bhaash.users");
}
