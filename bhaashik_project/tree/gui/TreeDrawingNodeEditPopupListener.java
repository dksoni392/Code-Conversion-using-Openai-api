/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.tree.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.FeatureStructures;
import bhaashik.corpus.ssf.features.impl.FSProperties;
import bhaashik.corpus.ssf.features.impl.FeatureStructuresImpl;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.table.gui.BhaashikJTable;
import bhaashik.table.BhaashikTableModel;

/**
 *
 * @author anil
 */
public class TreeDrawingNodeEditPopupListener extends MouseAdapter
{

    protected JPopupMenu popup;

    protected JMenu nodeEditingJMenu;
    protected JMenu featureNameEditingJMenu;
    protected JMenu featureValueEditingJMenu;
    protected JMenu mandatoryFeatureValueEditingJMenu;
    protected JMenu featureReferentEditingJMenu;
    protected JMenu featureDeleteJMenu;

    protected BhaashikJTable bhaashikJTable;

    protected Hashtable nodeLabelEditors;

    protected BhaashikTableModel fsSchema;

    protected String[] featureValueStrings;

    protected BhaashikTreeDrawingJPanel bhaashikTreeDrawingJPanel;

    protected List namedNodes;
    protected List attribNames;
    
    protected Point point;

    public TreeDrawingNodeEditPopupListener(BhaashikJTable bhaashikJTable, JPopupMenu pm, Hashtable nodeLabelEditors, BhaashikTableModel fsSchema)
    {
        popup = pm;
        this.bhaashikJTable = bhaashikJTable;
        this.nodeLabelEditors = nodeLabelEditors;

        this.fsSchema = fsSchema;

        nodeEditingJMenu = new JMenu(GlobalProperties.getIntlString("Node_Name"));
        featureNameEditingJMenu = new JMenu(GlobalProperties.getIntlString("Attribute_Name"));
        featureValueEditingJMenu = new JMenu(GlobalProperties.getIntlString("Attribute_Value"));
        mandatoryFeatureValueEditingJMenu = new JMenu(GlobalProperties.getIntlString("Mandatory_Attribute_Value"));
        featureReferentEditingJMenu = new JMenu(GlobalProperties.getIntlString("Attribute_Referent"));
        featureDeleteJMenu = new JMenu(GlobalProperties.getIntlString("Delete_Attribute"));
    }

    public TreeDrawingNodeEditPopupListener(JPopupMenu pm, BhaashikTableModel fsSchema)
    {
        popup = pm;

        this.fsSchema = fsSchema;

        nodeEditingJMenu = new JMenu(GlobalProperties.getIntlString("Node_Name"));
        featureNameEditingJMenu = new JMenu(GlobalProperties.getIntlString("Attribute_Name"));
        featureValueEditingJMenu = new JMenu(GlobalProperties.getIntlString("Attribute_Value"));
        mandatoryFeatureValueEditingJMenu = new JMenu(GlobalProperties.getIntlString("Mandatory_Attribute_Value"));
        featureReferentEditingJMenu = new JMenu(GlobalProperties.getIntlString("Attribute_Referent"));
        featureDeleteJMenu = new JMenu(GlobalProperties.getIntlString("Delete_Attribute"));
    }

    private void initMenu()
    {
        if (nodeEditingJMenu != null)
        {
            nodeEditingJMenu.setVisible(false);
            nodeEditingJMenu.removeAll();
        }

        if(featureValueEditingJMenu != null)
        {
            featureValueEditingJMenu.setVisible(false);
            featureValueEditingJMenu.removeAll();
        }

        if(mandatoryFeatureValueEditingJMenu != null)
        {
            mandatoryFeatureValueEditingJMenu.setVisible(false);
            mandatoryFeatureValueEditingJMenu.removeAll();
        }

        if(featureReferentEditingJMenu != null)
        {
            featureReferentEditingJMenu.setVisible(false);
            featureReferentEditingJMenu.removeAll();
        }

        if(featureDeleteJMenu != null)
        {
            featureDeleteJMenu.setVisible(false);
            featureDeleteJMenu.removeAll();
        }

        popup.validate();
    }

    public void setJTable(BhaashikJTable bhaashikJTable)
    {
        this.bhaashikJTable = bhaashikJTable;
    }

    public void setBhaashikTreeJPanel(BhaashikTreeDrawingJPanel bhaashikTreeDrawingJPanel)
    {
        this.bhaashikTreeDrawingJPanel = bhaashikTreeDrawingJPanel;
    }

    public void setNodeLabelEditors(Hashtable nodeLabelEditors)
    {
        this.nodeLabelEditors = nodeLabelEditors;
    }

    public void setNodeEditingJMenu(JMenu pm)
    {
        this.nodeEditingJMenu = pm;
    }

    public void mouseClicked(MouseEvent e)
    {
//        showPopup(e);
    }

    public void mousePressed(MouseEvent e)
    {
        point = e.getPoint();
        showPopup(e);
    }

    public void mouseReleased(MouseEvent e)
    {
        point = e.getPoint();

        if (File.separator.equalsIgnoreCase("\\"))
        {
            showPopup(e);
        }
    }

    private void showPopup(MouseEvent e)
    {
        point = e.getPoint();

        initMenu();

        if (e.isPopupTrigger())
        {
            Point p = e.getPoint();
            int r = bhaashikJTable.rowAtPoint(p);
            int c = bhaashikJTable.columnAtPoint(p);

            SSFNode currentNode = (SSFNode) bhaashikJTable.getCellObject(r, c);

            if (currentNode != null && nodeLabelEditors != null)
            {
                try
                {
                    DefaultComboBoxModel nodeLabelEditor = null;

                    FeatureStructures fss = currentNode.getFeatureStructures();

                    if (currentNode instanceof SSFPhrase)
                    {
                        nodeLabelEditor = (DefaultComboBoxModel) nodeLabelEditors.get(GlobalProperties.getIntlString("PhraseNames"));

                        String featureNames = (String) fsSchema.getValue(GlobalProperties.getIntlString("ColumnName"), GlobalProperties.getIntlString("Feature"), GlobalProperties.getIntlString("EnumValues"));
                        String featureValues = (String) fsSchema.getValue(GlobalProperties.getIntlString("ColumnName"), GlobalProperties.getIntlString("Value"), GlobalProperties.getIntlString("EnumValues"));

                        String featureNameStrings[] = featureNames.split("::");
//                            featureValueStrings = featureValues.split("::");

//                            String featureValueStringsOther[] = new String[featureValueStrings.length + 1];
//
//                            featureValueStringsOther[0] = "Other";
//
//                            for (int i = 1; i < featureValueStringsOther.length; i++)
//                            {
//                                featureValueStringsOther[i] = featureValueStrings[i - 1];
//                            }

//                            featureValueStrings = featureValueStringsOther;

                        namedNodes = ((SSFPhrase) currentNode.getRoot()).getNodesForAttrib(GlobalProperties.getIntlString("name"), true);

                        int count = featureNameStrings.length;

                        if(fss != null)
                        {
                            for (int i = 0; i < count; i++)
                            {
                                // Name attribute editing not allowed
    //                            featureNameEditingJMenu.add(new JMenuItem(new NodeFeatureNameTreeAction(bhaashikJTable, featureNameStrings[i])));
    //                                featureValueEditingJMenu.add(new JMenuItem(new NodeFeatureValueTreeAction(jtree, featureNameStrings[i], featureValueStrings)));
                                if (featureNameStrings[i].equals(GlobalProperties.getIntlString("name")) == false)
                                {
                                    featureReferentEditingJMenu.add(new JMenuItem(new NodeFeatureReferenceTreeAction(bhaashikJTable, featureNameStrings[i], namedNodes)));
                                }
                            }
                        }

                        attribNames = currentNode.getAttributeNames();

                        if(attribNames != null)
                        {
                            count = attribNames.size();

                            for (int i = 0; i < count; i++)
                            {
                                if (FeatureStructuresImpl.getFSProperties().isMandatory((String) attribNames.get(i)) == false)
                                {
                                    if (((String) attribNames.get(i)).equals(GlobalProperties.getIntlString("name")) == false)
                                    {
                                        featureDeleteJMenu.add(new JMenuItem(new NodeFeatureDeleteTreeAction(bhaashikJTable, (String) attribNames.get(i))));
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        nodeLabelEditor = (DefaultComboBoxModel) nodeLabelEditors.get(GlobalProperties.getIntlString("POSTags"));
                    }

                    // If multiple tags are given, show them as options in the node label menu, instead of all tags

                    String tag = currentNode.getName();

                    String tags[] = tag.split("/");

                    if (tags.length > 1)
                    {
                        for (int i = 0; i < tags.length; i++)
                        {
                            String nodeLabel = (String) tags[i];
                            nodeEditingJMenu.add(new JMenuItem(new NodeEditingTreeAction(bhaashikJTable, nodeLabel)));
                        }
                    } else
                    {
                        int count = nodeLabelEditor.getSize();

                        for (int i = 0; i < count; i++)
                        {
                            String nodeLabel = (String) nodeLabelEditor.getElementAt(i);
                            nodeEditingJMenu.add(new JMenuItem(new NodeEditingTreeAction(bhaashikJTable, nodeLabel)));
                        }
                    }

                    // For mandatory (e.g. morph) features
//                    if (fss != null && fss.countAltFSValues() == 1 && fss.getAltFSValue(0).hasMandatoryAttribs() == true)
//                    {
                        FSProperties fsProperties = FeatureStructuresImpl.getFSProperties();
                        int mcount = fsProperties.countMandatoryAttributes();

                        for (int i = 1; i < mcount; i++)
                        {
                            String fname = fsProperties.getMandatoryAttribute(i);
                            String fvalue = fsProperties.getMandatoryAttributeValue(i);

                            JMenu mfMenu = new JMenu(fname);

                            String vals[] = fvalue.split("::");

                            for (int j = 0; j < vals.length; j++)
                            {
                                mfMenu.add(new JMenuItem(new NodeMandatoryFeatureValueTreeAction(bhaashikJTable, fname, vals[j])));
                            }

                            mandatoryFeatureValueEditingJMenu.add(mfMenu);
                        }
//                    }

                    // For non-mandatory (e.g. dependency) features
//                    if (fss != null && fss.countAltFSValues() == 1)
//                    {
                        fsProperties = FeatureStructuresImpl.getFSProperties();
                        mcount = fsProperties.countNonMandatoryAttributes();

                        for (int i = 0; i < mcount; i++)
                        {
                            String fname = fsProperties.getNonMandatoryAttribute(i);

                            if (fname.equals(GlobalProperties.getIntlString("name")) == false)
                            {
                                String fvalue = fsProperties.getNonMandatoryAttributeValue(i);

                                JMenu mfMenu = new JMenu(fname);

                                String vals[] = fvalue.split("::");

                                int maxSize = 25;
                                JMenu moreMenu1 = new JMenu(GlobalProperties.getIntlString("More"));
                                JMenu moreMenu2 = new JMenu(GlobalProperties.getIntlString("More"));
                                JMenu moreMenu3 = new JMenu(GlobalProperties.getIntlString("More"));

                                for (int j = 0; j < vals.length; j++)
                                {
                                    if (j < maxSize)
                                    {
                                        mfMenu.add(new JMenuItem(new NodeFeatureValueTreeAction(bhaashikJTable, fname, vals[j])));
                                    } else if (j < 2 * maxSize)
                                    {
                                        moreMenu1.add(new JMenuItem(new NodeFeatureValueTreeAction(bhaashikJTable, fname, vals[j])));
                                    } else if (j < 3 * maxSize)
                                    {
                                        moreMenu2.add(new JMenuItem(new NodeFeatureValueTreeAction(bhaashikJTable, fname, vals[j])));
                                    } else if (j < 4 * maxSize)
                                    {
                                        moreMenu3.add(new JMenuItem(new NodeFeatureValueTreeAction(bhaashikJTable, fname, vals[j])));
                                    }

                                    if (moreMenu1.getItemCount() > 0)
                                    {
                                        mfMenu.add(moreMenu1);
                                    }
                                    if (moreMenu2.getItemCount() > 0)
                                    {
                                        mfMenu.add(moreMenu2);
                                    }
                                    if (moreMenu3.getItemCount() > 0)
                                    {
                                        mfMenu.add(moreMenu3);
                                    }
//                                    mfMenu.add(new JMenuItem(new NodeFeatureValueTreeAction(bhaashikJTable, fname, vals[j])));
                                }

                                featureValueEditingJMenu.add(mfMenu);
                            }
                        }
//                    }

                    // Adding things to the menu
                    if (featureDeleteJMenu.getMenuComponentCount() > 0)
                    {
                        popup.insert(featureDeleteJMenu, 0);
                        featureDeleteJMenu.setVisible(true);
                    }

                    if (featureReferentEditingJMenu.getMenuComponentCount() > 0)
                    {
                        popup.insert(featureReferentEditingJMenu, 0);
                        featureReferentEditingJMenu.setVisible(true);
                    }

                    if (mandatoryFeatureValueEditingJMenu.getMenuComponentCount() > 0)
                    {
                        popup.insert(mandatoryFeatureValueEditingJMenu, 0);
                        mandatoryFeatureValueEditingJMenu.setVisible(true);
                    }

                    if (featureValueEditingJMenu.getMenuComponentCount() > 0)
                    {
                        popup.insert(featureValueEditingJMenu, 0);
                        featureValueEditingJMenu.setVisible(true);
                    }

                    if (featureNameEditingJMenu.getMenuComponentCount() > 0)
                    {
                        popup.insert(featureNameEditingJMenu, 0);
                        featureNameEditingJMenu.setVisible(true);
                    }

                    popup.insert(nodeEditingJMenu, 0);
                    nodeEditingJMenu.setVisible(true);

                    popup.validate();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            popup.show(e.getComponent(), e.getX(), e.getY());
            popup.setVisible(false);
            popup.setVisible(true);
        }
    }

    protected class NodeEditingTreeAction extends AbstractAction {
        BhaashikJTable bhaashikJTable;

         public NodeEditingTreeAction(BhaashikJTable bhaashikJTable, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator)
         {
            super(text, icon);

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);

            this.bhaashikJTable = bhaashikJTable;
        }

        public NodeEditingTreeAction(BhaashikJTable bhaashikJTable, String text)
        {
            super(text);

            this.bhaashikJTable = bhaashikJTable;
        }

        public void actionPerformed(ActionEvent e)
        {
            int row = bhaashikJTable.rowAtPoint(point);
            int col = bhaashikJTable.columnAtPoint(point);

            Object currentSelection = bhaashikJTable.getCellObject(row, col);

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) currentSelection;

//                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
//                {
////                    nodeLabelJComboBox.setEnabled(false);
//                }
//                else if(mode == BhaashikTreeJPanel.SSF_MODE)
//                {
                    currentNode.setName((String) getValue(Action.NAME));
                    bhaashikJTable.fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
//                    jtree.updateUI();
//                }
            }
        }
    }

    protected class NodeFeatureNameTreeAction extends AbstractAction {
        BhaashikJTable bhaashikJTable;

         public NodeFeatureNameTreeAction(BhaashikJTable bhaashikJTable, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator)
         {
            super(text, icon);

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);

            this.bhaashikJTable = bhaashikJTable;
        }

        public NodeFeatureNameTreeAction(BhaashikJTable bhaashikJTable, String text)
        {
            super(text);

            this.bhaashikJTable = bhaashikJTable;
        }

        public void actionPerformed(ActionEvent e)
        {
            int row = bhaashikJTable.rowAtPoint(point);
            int col = bhaashikJTable.columnAtPoint(point);

            Object currentSelection = bhaashikJTable.getCellObject(row, col);

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) currentSelection;

//                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
//                {
////                    nodeLabelJComboBox.setEnabled(false);
//                }
//                else if(mode == BhaashikTreeJPanel.SSF_MODE)
//                {
                    if(currentNode instanceof SSFPhrase)
                    {
                        ((SSFPhrase) currentNode).setAttributeValue((String) getValue(Action.NAME), "?");
//                        ((DefaultTreeModel) jtree.getModel()).reload(currentNode.getParent());
                        bhaashikJTable.fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                    }
//                    jtree.updateUI();
//                }
            }
        }
    }

    protected class NodeFeatureValueTreeAction extends AbstractAction {
        BhaashikJTable bhaashikJTable;
        String fname;

         public NodeFeatureValueTreeAction(BhaashikJTable bhaashikJTable, String fname, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator)
         {
            super(text, icon);

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);

            this.bhaashikJTable = bhaashikJTable;
            this.fname = fname;
        }

        public NodeFeatureValueTreeAction(BhaashikJTable bhaashikJTable, String fname, String text)
        {
            super(text);

            this.bhaashikJTable = bhaashikJTable;
            this.fname = fname;
        }

        public void actionPerformed(ActionEvent e)
        {
            int row = bhaashikJTable.rowAtPoint(point);
            int col = bhaashikJTable.columnAtPoint(point);

            Object currentSelection = bhaashikJTable.getCellObject(row, col);

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) currentSelection;

//                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
//                {
////                    nodeLabelJComboBox.setEnabled(false);
//                }
//                else if(mode == BhaashikTreeJPanel.SSF_MODE)
//                {
                    String val = (String) getValue(Action.NAME);

                    if(val.equalsIgnoreCase(GlobalProperties.getIntlString("other")))
                    {
                        val = JOptionPane.showInputDialog(GlobalProperties.getIntlString("Please_enter_the_attribute_value"), "");
                    }

                    if(val != null)
                    {
                        currentNode.setAttributeValue(fname, val);
                        bhaashikJTable.fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                    }
//                }
            }
        }
    }

    protected class NodeMandatoryFeatureValueTreeAction extends AbstractAction {
        BhaashikJTable bhaashikJTable;
        String fname;

         public NodeMandatoryFeatureValueTreeAction(BhaashikJTable bhaashikJTable, String fname, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator)
         {
            super(text, icon);

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);

            this.bhaashikJTable = bhaashikJTable;
            this.fname = fname;
        }

        public NodeMandatoryFeatureValueTreeAction(BhaashikJTable bhaashikJTable, String fname, String text)
        {
            super(text);

            this.bhaashikJTable = bhaashikJTable;
            this.fname = fname;
        }

        public void actionPerformed(ActionEvent e)
        {
            int row = bhaashikJTable.rowAtPoint(point);
            int col = bhaashikJTable.columnAtPoint(point);

            Object currentSelection = bhaashikJTable.getCellObject(row, col);

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) currentSelection;

//                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
//                {
////                    nodeLabelJComboBox.setEnabled(false);
//                }
//                else if(mode == BhaashikTreeJPanel.SSF_MODE)
//                {
                    String val = (String) getValue(Action.NAME);

                    if(val.equalsIgnoreCase(GlobalProperties.getIntlString("other")))
                    {
                        val = JOptionPane.showInputDialog(GlobalProperties.getIntlString("Please_enter_the_attribute_value"), "");
                    }

                    if(val != null)
                    {
                        currentNode.setAttributeValue(fname, val);
                        bhaashikJTable.fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                    }
//                }
            }
        }
    }

    protected class NodeFeatureReferenceTreeAction extends AbstractAction {
        BhaashikJTable bhaashikJTable;
        List namedNodes;

         public NodeFeatureReferenceTreeAction(BhaashikJTable bhaashikJTable, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator, List namedNodes)
         {
            super(text, icon);

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);

            this.bhaashikJTable = bhaashikJTable;
            this.namedNodes = namedNodes;
        }

        public NodeFeatureReferenceTreeAction(BhaashikJTable bhaashikJTable, String text, List namedNodes)
        {
            super(text);

            this.bhaashikJTable = bhaashikJTable;
            this.namedNodes = namedNodes;
        }

        public void actionPerformed(ActionEvent e)
        {
            if(namedNodes == null || namedNodes.size() == 0)
                return;

            int row = bhaashikJTable.rowAtPoint(point);
            int col = bhaashikJTable.columnAtPoint(point);

            Object currentSelection = bhaashikJTable.getCellObject(row, col);

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) currentSelection;

//                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
//                {
////                    nodeLabelJComboBox.setEnabled(false);
//                }
//                else if(mode == BhaashikTreeJPanel.SSF_MODE)
//                {
                 if(currentNode instanceof SSFPhrase)
                 {
                    SSFNode selectedValue = (SSFNode) JOptionPane.showInputDialog(null,
                        GlobalProperties.getIntlString("Select_the_referent_node"), GlobalProperties.getIntlString("Attibute_Referent"), JOptionPane.INFORMATION_MESSAGE, null,
                        namedNodes.toArray(), namedNodes.toArray()[0]);

                    if(selectedValue == null)
                        return;

                    String prevValue = ((SSFPhrase) currentNode).getAttributeValue((String) getValue(Action.NAME));

                    if(prevValue != null & prevValue.equals("") == false)
                    {
                        String parts[] = prevValue.split(":");

                        ((SSFPhrase) currentNode).setAttributeValue((String) getValue(Action.NAME), parts[0] + ":" + selectedValue.getAttributeValue(GlobalProperties.getIntlString("name")));
    //                        ((DefaultTreeModel) jtree.getModel()).reload(currentNode.getParent());
                        bhaashikJTable.fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                    }
                 }
//                    jtree.updateUI();
//                }
            }
        }
    }

    protected class NodeFeatureDeleteTreeAction extends AbstractAction {
        BhaashikJTable bhaashikJTable;

         public NodeFeatureDeleteTreeAction(BhaashikJTable bhaashikJTable, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator)
         {
            super(text, icon);

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);

            this.bhaashikJTable = bhaashikJTable;
        }

        public NodeFeatureDeleteTreeAction(BhaashikJTable bhaashikJTable, String text)
        {
            super(text);

            this.bhaashikJTable = bhaashikJTable;
        }

        public void actionPerformed(ActionEvent e)
        {
            int row = bhaashikJTable.rowAtPoint(point);
            int col = bhaashikJTable.columnAtPoint(point);

            Object currentSelection = bhaashikJTable.getCellObject(row, col);

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) currentSelection;

//                if(mode == BhaashikTreeJPanel.DEFAULT_MODE)
//                {
////                    nodeLabelJComboBox.setEnabled(false);
//                }
//                else if(mode == BhaashikTreeJPanel.SSF_MODE)
//                {
                 if(currentNode instanceof SSFPhrase && attribNames.size() > 0)
                 {
                     if(FeatureStructuresImpl.getFSProperties().isMandatory(Action.NAME) == false)
                     {
                       ((SSFPhrase) currentNode).getFeatureStructures().getAltFSValue(0).removeAttribute((String) getValue(Action.NAME));
    //                        ((DefaultTreeModel) jtree.getModel()).reload(currentNode.getParent());
                        bhaashikJTable.fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                     }
                 }
//                    jtree.updateUI();
//                }
            }
        }
    }
}
