/*
 * AnnotationPhase.java
 *
 * Created on November 11, 2005, 12:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
public final class AnnotationPhase extends BhaashikType implements Serializable {

    public final int ord;
    private static Vector types = new Vector();

    protected AnnotationPhase(String id, String pk) {
        super(id, pk);

        if (AnnotationPhase.last() != null) {
            this.prev = AnnotationPhase.last();
            AnnotationPhase.last().next = this;
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
        return new TypeEnumerator(AnnotationPhase.first());
    }

    public static BhaashikType findFromClassName(String className)
    {
        Enumeration enm = AnnotationPhase.elements();
        return findFromClassName(enm, className);
    }

    public static BhaashikType findFromId(String i)
    {
        Enumeration enm = AnnotationPhase.elements();
        return findFromId(enm, i);
    }

    public static final AnnotationPhase INITIAL = new AnnotationPhase("Initial", "bhaash.annotation.phase");
    public static final AnnotationPhase ANNOTATION = new AnnotationPhase("Annotation", "bhaash.annotation.phase");
    public static final AnnotationPhase ANNOTATED = new AnnotationPhase("Annotated", "bhaash.annotation.phase");
    public static final AnnotationPhase VALIDATION = new AnnotationPhase("Validation", "bhaash.annotation.phase");
    public static final AnnotationPhase VALIDATED = new AnnotationPhase("Validated", "bhaash.annotation.phase");
    public static final AnnotationPhase FINAL = new AnnotationPhase("Final", "bhaash.annotation.phase");
}
