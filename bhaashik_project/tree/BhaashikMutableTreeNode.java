/*
 * BhaashikNode.java
 *
 * Created on October 7, 2005, 6:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package bhaashik.tree;

import java.io.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.tree.*;

import bhaashik.table.gui.BhaashikJTable;

/**
 *
 *  @author Anil Kumar Singh
 */
public class BhaashikMutableTreeNode extends DefaultMutableTreeNode
        implements Cloneable, MutableTreeNode, Serializable {

    public static final int CHUNK_MODE = 0;
    public static final int FS_MODE = 1;
    public static final int DEPENDENCY_RELATIONS_MODE = 2;
    public static final int PHRASE_STRUCTURE_MODE = 3;
    public static final int DICT_FST_MODE = 4;
//    protected int requiredColumnCount;
    protected int rowIndex;
    protected int columnIndex;

    /** Creates a new instance of BhaashikNode */
    public BhaashikMutableTreeNode() {
        super();

//	requiredColumnCount = -1;
        rowIndex = -1;
        columnIndex = -1;
    }

    public BhaashikMutableTreeNode(Object userObject) {
        super(userObject);

//	requiredColumnCount = -1;
        rowIndex = -1;
        columnIndex = -1;
    }

    public BhaashikMutableTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);

//	requiredColumnCount = -1;
        rowIndex = -1;
        columnIndex = -1;
    }

    public BhaashikMutableTreeNode getCopy() throws Exception {
        return null;
    }

    public List<BhaashikMutableTreeNode> getAllLeaves() // &get_leaves( [$tree] )  -> @leaf_nodes;
    {
        List<BhaashikMutableTreeNode> leaves = new ArrayList<BhaashikMutableTreeNode>();

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            BhaashikMutableTreeNode node = (BhaashikMutableTreeNode) getChildAt(i);

            if (node.isLeaf()) {
                leaves.add(node);
            } else {
                leaves.addAll(((BhaashikMutableTreeNode) getChildAt(i)).getAllLeaves());
            }
        }

        return leaves;
    }

    public boolean isDeep() {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            if (getChildAt(i).getChildCount() > 0) {
                return true;
            }
        }

        return false;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int i) {
        rowIndex = i;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int i) {
        columnIndex = i;
    }

    public BhaashikMutableTreeNode getLeftNode()
    {
        BhaashikMutableTreeNode prevNode = (BhaashikMutableTreeNode) getPreviousSibling();

        if(prevNode != null)
            return prevNode;

        BhaashikMutableTreeNode prevParent = (BhaashikMutableTreeNode) ((BhaashikMutableTreeNode) parent).getPreviousSibling();

        if(parent == null || prevParent == null)
            return null;

        return (BhaashikMutableTreeNode) prevParent.getLastChild();
    }

    public BhaashikMutableTreeNode getRightNode()
    {
        BhaashikMutableTreeNode nextNode = (BhaashikMutableTreeNode) getNextSibling();

        if(nextNode != null)
            return nextNode;

        BhaashikMutableTreeNode nextParent = (BhaashikMutableTreeNode) ((BhaashikMutableTreeNode) parent).getNextSibling();

        if(parent == null || nextParent == null)
            return null;

        return (BhaashikMutableTreeNode) nextParent.getFirstChild();
    }

    public void shiftLeft()
    {
        BhaashikMutableTreeNode leftNode = getLeftNode();
        
        if(leftNode == null || leftNode == getPreviousSibling())
            return;

        parent.remove(this);

        ((BhaashikMutableTreeNode) leftNode.getParent()).add(this);
    }

    public void shiftRight()
    {
        BhaashikMutableTreeNode rightNode = getRightNode();

        if(rightNode == null || rightNode == getNextSibling())
            return;

        parent.remove(this);

        ((BhaashikMutableTreeNode) rightNode.getParent()).insert(this, 0);
    }

//    public int getRequiredColumnCount()
//    {
//	return requiredColumnCount;
//    }
//     
//    public void clearRequiredColumnCounts()
//    {
//	requiredColumnCount = -1;
//	
//	int chcount = getChildCount();
//
//	for (int i = 0; i < chcount; i++)
//	{
//	    BhaashikMutableTreeNode child = (BhaashikMutableTreeNode) getChildAt(i);
//	    child.clearRequiredColumnCounts();
//	}
//    }
//   
//    public void calculateRequiredColumnCounts()
//    {
//	if(isLeaf() == false)
//	{
//	    Vector leaves = getAllLeaves();
//	    int lcount = leaves.size();
//
//	    if(lcount%2 == 0)
//		lcount++;
//
//	    if(lcount > getRequiredColumnCount())
//	    {
//		requiredColumnCount = lcount;
//		
//		BhaashikMutableTreeNode parent = (BhaashikMutableTreeNode) getParent();
//		
//		while(parent != null)
//		{
//		    parent.requiredColumnCount++;
//		    parent = (BhaashikMutableTreeNode) parent.getParent();
//		}
//	    }
//
//	    int chcount = getChildCount();
//	    
//	    for (int i = 0; i < chcount; i++)
//	    {
//		BhaashikMutableTreeNode child = (BhaashikMutableTreeNode) getChildAt(i);
//		child.calculateRequiredColumnCounts();
//	    }
//	}
//	else
//	{
//	    requiredColumnCount = 1;
//	}
//    }
    public void setValuesInTable(DefaultTableModel tbl, int mode, boolean reverse) {
        tbl.setValueAt(this, rowIndex, columnIndex);

        if (isLeaf() == false) {
            int chcount = getChildCount();

            for (int i = 0; i < chcount; i++) {
                BhaashikMutableTreeNode child = (BhaashikMutableTreeNode) getChildAt(i);
                child.setValuesInTable(tbl, mode, reverse);
            }
        }
    }

    public void setValuesInTable(DefaultTableModel tbl, int mode) {
        tbl.setValueAt(this, rowIndex, columnIndex);

        if (isLeaf() == false) {
            int chcount = getChildCount();

            for (int i = 0; i < chcount; i++) {
                BhaashikMutableTreeNode child = (BhaashikMutableTreeNode) getChildAt(i);
                child.setValuesInTable(tbl, mode);
            }
        }
    }

    public void fillTreeEdges(BhaashikJTable jtbl, int mode) {
//	if(requiredColumnCount == -1 || rowIndex == -1 || columnIndex == -1)
//	    return;

        if (isLeaf() == false) {
            int chcount = getChildCount();

            for (int i = 0; i < chcount; i++) {
                BhaashikMutableTreeNode child = (BhaashikMutableTreeNode) getChildAt(i);
                jtbl.addEdge(new BhaashikEdge(this, rowIndex, columnIndex, child, child.rowIndex, child.columnIndex));

                jtbl.setCellObject(rowIndex, columnIndex, child);
                child.fillTreeEdges(jtbl, mode);
            }
        }
    }

    public void fillGraphEdges(BhaashikJTable jtbl, int mode) {
//        if (isLeaf() == false) {
//            int chcount = getChildCount();
//
//            for (int i = 0; i < chcount; i++) {
//                BhaashikMutableTreeNode child = (BhaashikMutableTreeNode) getChildAt(i);
//                jtbl.addEdge(new BhaashikEdge(this, rowIndex, columnIndex, child, child.rowIndex, child.columnIndex));
//
//                jtbl.setCellObject(rowIndex, columnIndex, child);
//                child.fillTreeEdges(jtbl, mode);
//            }
//        }

    }
}
