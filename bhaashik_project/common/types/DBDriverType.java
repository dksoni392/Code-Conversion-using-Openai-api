/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.common.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author User
 */
public final class DBDriverType extends BhaashikType implements Serializable {

    public final int ord;
    
    private static final ArrayList types = new ArrayList();
    
    public final String DRIVER;
    public final String PROTOCOL;

    protected DBDriverType(String id, String drvr, String prtcl, String pk) {
        super(id, pk);
        
        DRIVER = drvr;
        PROTOCOL = prtcl;

        if (DBDriverType.last() != null) {
            this.prev = DBDriverType.last();
            DBDriverType.last().next = this;
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
        return new TypeEnumerator(DBDriverType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = DBDriverType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = DBDriverType.elements();
        return findFromId(enm, i);
    }
    
    public static BhaashikType findFromDriver(Enumeration enm, String drv)
    {
        DBDriverType dt = null;

        while(enm.hasMoreElements())
        {
            dt = (DBDriverType) enm.nextElement();

            if(drv.equals(dt.DRIVER.toString()))
                return dt;
        }

        return null;
    }
    
    @Override
    public String toString()
    {
        return PROTOCOL;
    }

    public static final DBDriverType JDBC_DERBY = new DBDriverType("JDBC_Derby", "org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby", "bhaash.common.types");
    public static final DBDriverType JDBS_SQLITE = new DBDriverType("JDBC_SQLite", "org.sqlite.JDBC", "jdbc:sqlite", "bhaash.common.types");
}
