/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.tree.gui.action;

import java.awt.event.ActionEvent;
import java.util.List;
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
public class NodeFeatureReferenceTreeAction extends AbstractAction {
    JTree jtree;
    List namedNodes;

    BhaashikTreeJPanel bhaashikTreeJPanel;

     public NodeFeatureReferenceTreeAction(JTree jtree, String text, ImageIcon icon,
                      String desc, Integer mnemonic, KeyStroke acclerator, List namedNodes)
     {
        super(text, icon);

        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY, acclerator);

        this.jtree = jtree;
        this.namedNodes = namedNodes;
    }

    public NodeFeatureReferenceTreeAction(JTree jtree, String text, List namedNodes)
    {
        super(text);

        this.jtree = jtree;
        this.namedNodes = namedNodes;
    }

    public NodeFeatureReferenceTreeAction(JTree jtree, String text, List namedNodes, BhaashikTreeJPanel bhaashikTreeJPanel)
    {
        this(jtree, text, namedNodes);

        this.bhaashikTreeJPanel = bhaashikTreeJPanel;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(namedNodes == null || namedNodes.size() == 0)
            return;

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
//                 if(currentNode instanceof SSFPhrase)
//                 {
                SSFNode selectedValue = (SSFNode) JOptionPane.showInputDialog(null,
                    GlobalProperties.getIntlString("Select_the_referent_node"), GlobalProperties.getIntlString("Attibute_Referent"), JOptionPane.INFORMATION_MESSAGE, null,
                    namedNodes.toArray(), namedNodes.toArray()[0]);

                if(selectedValue == null)
                    return;

//                    String prevValue = ((SSFPhrase) currentNode).getAttributeValue((String) getValue(Action.NAME));
                String prevValue = currentNode.getAttributeValue((String) getValue(Action.NAME));

                if(prevValue != null && prevValue.equals("") == false)
                {
                    String parts[] = prevValue.split(":");

//                        ((SSFPhrase) currentNode).setAttributeValue((String) getValue(Action.NAME), parts[0] + ":" + selectedValue.getAttributeValue("name"));
                    currentNode.setAttributeValue((String) getValue(Action.NAME), parts[0] + ":" + selectedValue.getAttributeValue(GlobalProperties.getIntlString("name")));
//                        ((DefaultTreeModel) jtree.getModel()).reload(currentNode.getParent());
                    bhaashikTreeJPanel.editTreeNode(null);
                    jtree.updateUI();
                }
//                 }
//                    jtree.updateUI();
//                }
        }
    }
}
