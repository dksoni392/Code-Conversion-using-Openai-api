/*
 * Created on Aug 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.common.types;

import bhaashik.GlobalProperties;

import java.io.*;
import java.util.*;


public final class PropertyType extends BhaashikType implements Serializable {

    java.util.ResourceBundle bundle = GlobalProperties.getResourceBundle(); // NOI18N

    public final int ord;
    private static Vector types = new Vector();

    protected PropertyType(String id, String pk) {
        super(id, pk);

        if (PropertyType.last() != null) {
            this.prev = PropertyType.last();
            PropertyType.last().next = this;
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
        return new TypeEnumerator(PropertyType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = PropertyType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = PropertyType.elements();
        return findFromId(enm, i);
    }

    // The basic property containers
    // MULTI property containers are Hashtables with name of the container as the key and container as the value
    public static final PropertyType PROPERTY_TOKENS = new PropertyType("PropertyTokens", "bhaashik.properties");
    public static final PropertyType MULTI_PROPERTY_TOKENS = new PropertyType("MultiPropertyTokens", "bhaashik.properties");
    public static final PropertyType KEY_VALUE_PROPERTIES = new PropertyType("KeyValueProperties", "bhaashik.properties");
    public static final PropertyType MULTI_KEY_VALUE_PROPERTIES = new PropertyType("MultiKeyValueProperties", "bhaashik.properties");
    public static final PropertyType PROPERTY_TABLE = new PropertyType("PropertyTable", "bhaashik.properties");
    public static final PropertyType MULTI_PROPERTY_TABLE = new PropertyType("MultiPropertyTable", "bhaashik.properties");
    
    public static final PropertyType PROPERTIES_MANAGER = new PropertyType("PropertiesManager", "bhaashik.properties");
    public static final PropertyType MULTI_PROPERTIES_MANAGER = new PropertyType("MultiPropertiesManager", "bhaashik.properties");
}
