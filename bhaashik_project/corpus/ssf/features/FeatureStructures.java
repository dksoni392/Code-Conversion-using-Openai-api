/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features;

import java.io.*;
import java.util.List;
import javax.swing.tree.*;

import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.corpus.parallel.AlignmentUnit;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FeatureStructures extends MutableTreeNode {
    int addAltFSValue(FeatureStructure f);

    void clear();

    int countAltFSValues();

    int findAltFSValue(FeatureStructure fs);

    FeatureStructure getAltFSValue(int num);

    BhaashikMutableTreeNode getCopy() throws Exception;

    boolean isDeep();

    String makeString();

    String makeStringFV();

    String makeStringForRendering();

    void modifyAltFSValue(FeatureStructure fs, int index);

    void print(PrintStream ps);

    int readString(String fs_str) throws Exception;

    int readStringFV(String fs_str) throws Exception;

    FeatureStructure removeAltFSValue(int num);

    void clearAnnotation(long annoLevelFlags, SSFNode containingNode);

    void setToEmpty();

    List<String> getAttributeNames();

    String getAttributeValueString(String attibName);

    List<String> getAttributeValues();

    List<String> getAttributeValuePairs();

    String[] getOneOfAttributeValues(String attibNames[]);

    void setAttributeValue(String attibName, String val);

    void concatenateAttributeValue(String attibName, String val, String sep);

    void setAllAttributeValues(String attibName, String val);

    void hideAttribute(String aname);
    void unhideAttribute(String aname);

    FeatureAttribute getAttribute(String attibName);

    FeatureStructures getFeatureStructures(FeatureStructures fss, AlignmentUnit alignmentUnit);

    void setAlignmentUnit(AlignmentUnit alignmentUnit);

    AlignmentUnit loadAlignmentUnit(Object srcAlignmentObject, Object srcAlignmentObjectContainer, Object tgtAlignmentObjectContainer, int parallelIndex);

    Object clone();
    
    boolean equals(Object fa);
}