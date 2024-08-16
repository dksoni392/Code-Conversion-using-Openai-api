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
public final class BlogType extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected BlogType(String id, String pk) {
        super(id, pk);

        if (BlogType.last() != null) {
            this.prev = BlogType.last();
            BlogType.last().next = this;
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
        return new TypeEnumerator(BlogType.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = BlogType.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = BlogType.elements();
        return findFromId(enm, i);
    }

    public static final BlogType WORD_PRESS = new BlogType("WordPress", "bhaash.corpus.blog");
    public static final BlogType BLOGGER = new BlogType("Blogger", "bhaash.corpus.blog");
    public static final BlogType HUFFINGTON_POST = new BlogType("HuffingtonPost", "bhaash.corpus.blog");
}
