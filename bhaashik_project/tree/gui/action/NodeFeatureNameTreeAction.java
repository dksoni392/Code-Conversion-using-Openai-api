/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.tree.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.tree.gui.BhaashikTreeJPanel;

/**
 *
 * @author anil
 */
public class NodeFeatureNameTreeAction extends AbstractAction {

    JTree jtree;
    BhaashikTreeJPanel bhaashikTreeJPanel;

     public NodeFeatureNameTreeAction(JTree jtree, String text, ImageIcon icon,
                      String desc, Integer mnemonic, KeyStroke acclerator)
     {
        super(text, icon);

        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY, acclerator);

        this.jtree = jtree;
    }

    public NodeFeatureNameTreeAction(JTree jtree, String text)
    {
        super(text);

        this.jtree = jtree;
    }

    public NodeFeatureNameTreeAction(JTree jtree, String text, BhaashikTreeJPanel bhaashikTreeJPanel)
    {
        this(jtree, text);

        this.bhaashikTreeJPanel = bhaashikTreeJPanel;
    }

    public void actionPerformed(ActionEvent e)
    {
        TreePath currentSelection = jtree.getSelectionPath();

        if (currentSelection != null)
        {
            SSFNode currentNode = (SSFNode) (currentSelection.getLastPathComponent());

    //                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
    //                {
    ////                    nodeLabelJComboBox.setEnabled(false);
    //                }
    //                else if(mode == BhaashikTreeJPanel.SSF_MODE)
    //                {
    //                    if(currentNode instanceof SSFPhrase)
    //                    {
    //                        ((SSFPhrase) currentNode).setAttributeValue((String) getValue(Action.NAME), "?");
                    currentNode.setAttributeValue((String) getValue(Action.NAME), "?");
    //                        ((DefaultTreeModel) jtree.getModel()).reload(currentNode.getParent());
                    bhaashikTreeJPanel.editTreeNode(null);
                    jtree.updateUI();
    //                    }
    //                    jtree.updateUI();
    //                }
        }
    }
}
