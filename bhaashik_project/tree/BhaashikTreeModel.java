/*
 * BhaashikTree.java
 *
 * Created on October 7, 2005, 6:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.tree;

import java.io.*;
import javax.swing.tree.*;

/**
 *
 *  @author Anil Kumar Singh
 */
public class BhaashikTreeModel extends DefaultTreeModel
        implements Serializable, TreeModel
{
    
    /** Creates a new instance of BhaashikTree */
    public BhaashikTreeModel(TreeNode root) {
        super(root);
    }
    
    public BhaashikTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }
}
