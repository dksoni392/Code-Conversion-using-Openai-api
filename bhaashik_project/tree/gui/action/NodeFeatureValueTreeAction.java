/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.tree.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.tree.gui.BhaashikTreeJPanel;

/**
 *
 * @author anil
 */
public class NodeFeatureValueTreeAction extends AbstractAction {
    JTree jtree;
    String fname;

    BhaashikTreeJPanel bhaashikTreeJPanel;

     public NodeFeatureValueTreeAction(JTree jtree, String fname, String text, ImageIcon icon,
                      String desc, Integer mnemonic, KeyStroke acclerator)
     {
        super(text, icon);

        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY, acclerator);

        this.jtree = jtree;
        this.fname = fname;
    }

    public NodeFeatureValueTreeAction(JTree jtree, String fname, String text)
    {
        super(text);

        this.jtree = jtree;
        this.fname = fname;
    }

    public NodeFeatureValueTreeAction(JTree jtree, String fname, String text, BhaashikTreeJPanel bhaashikTreeJPanel)
    {
        this(jtree, fname, text);

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
                String val = (String) getValue(Action.NAME);

                if(val.equalsIgnoreCase("other"))
                {
                    val = JOptionPane.showInputDialog(GlobalProperties.getIntlString("Please_enter_the_attribute_value"), "");
                }

                if(val != null)
                {
                    currentNode.setAttributeValue(fname, val);
                    bhaashikTreeJPanel.editTreeNode(null);
                    jtree.updateUI();
                }
//                }
        }
    }
}
