/*
 * BhaashikTableCellEditor.java
 *
 * Created on April 21, 2008, 9:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.table.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import bhaashik.BhaashikMain;
import bhaashik.BhaashikMainEvent;
import bhaashik.GlobalProperties;
import bhaashik.common.types.ClientType;
import bhaashik.corpus.ssf.gui.NavigatetoValidateEvent;
import bhaashik.corpus.ssf.gui.SyntacticAnnotationWorkJPanel;
import bhaashik.gui.clients.BhaashikClient;
import bhaashik.gui.common.DialogFactory;
import bhaashik.gui.common.BhaashikJDialog;
import bhaashik.text.DictionaryFSTMutableNode;
import bhaashik.table.BhaashikTableModel;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author anil
 */
public class BhaashikActionTableCellEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {
    
    protected Component owner;
    protected String langEnc;
    protected String title;
    
    protected BhaashikJDialog dialog;
    protected JButton button;

    protected int row;
    protected int column;
    protected Object currentValue;

    protected BhaashikTableModel currentTable; // This is not the model associated with the JTable below
    protected JTable jtable;
    protected BhaashikTableJPanel editor;
    protected int mode;
    
    protected static final String EDIT = GlobalProperties.getIntlString("edit");
    
    public static final int DICTIONARY_FST_MODE = 0;
    public static final int FIND_AND_NAVIGATE = 1;
    public static final int VALIDATION_MODE = 2;
    private JTable table;
    
    /** Creates a new instance of BhaashikTableCellEditor */
    public BhaashikActionTableCellEditor(Component owner, String langEnc, String title, int mode) {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);
        
        this.owner = owner;
        this.langEnc = langEnc;
        this.title = title;       
        this.mode = mode;
    }
    
    /**
     * Handles events from the editor button and from
     * the dialog's OK button.
     */
    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand()) && currentTable != null) {
            //The user has clicked the cell, so
            //bring up the dialog.
            if(owner.getClass().equals(JDialog.class))
                dialog = DialogFactory.createTableDialog((JDialog) owner, title, true, currentTable, langEnc, BhaashikTableJPanel.DEFAULT_MODE);
            else if(owner.getClass().equals(JFrame.class))
                dialog = DialogFactory.createTableDialog((JFrame) owner, title, true, currentTable, langEnc, BhaashikTableJPanel.DEFAULT_MODE);
            
            editor = (BhaashikTableJPanel) dialog.getJPanel();
            
            dialog.setVisible(true);

            //Make the renderer reappear.
            fireEditingStopped();

        } else { //User pressed dialog's "OK" button.
            if(currentValue != null && currentValue.getClass().equals(DictionaryFSTMutableNode.class) && mode == DICTIONARY_FST_MODE)
            {
                DictionaryFSTMutableNode node = (DictionaryFSTMutableNode) currentValue;
                node.getDictionaryFSTNode().getFeatureStructure().setFeatureTable(currentTable);
            }
            else if(mode == FIND_AND_NAVIGATE)
            {                
                if(currentValue instanceof String)
                {
                    String sentenceIDString = (String) currentValue;

                    String parts[] = sentenceIDString.split("::");

                    sentenceIDString = parts[0];

                    String nodeIDString="";
                    if(parts.length>2)
                        nodeIDString = parts[1];

                    if(UtilityFunctions.isInteger((String) sentenceIDString))
                    {
                        int fileCol = ((BhaashikTableModel) jtable.getModel()).getColumnIndex("File");
                        
                        //System.out.println("filecol:: "+fileCol);

                        if(fileCol > 0)
                        {
                            if(owner != null && owner instanceof SyntacticAnnotationWorkJPanel)
                            {
                                BhaashikMain main = (BhaashikMain) ((SyntacticAnnotationWorkJPanel) owner).getOwner();

                                Object sfile = jtable.getModel().getValueAt(row, fileCol);
                                String ssfile = sfile.toString();
                                File file = new File(ssfile);

                                if(main != null)
                                {
                                    main.fireEvent(new BhaashikMainEvent(jtable, BhaashikMainEvent.DISPLAY_FILE,
                                            ClientType.SYNTACTIC_ANNOTATION,
                                            ((SyntacticAnnotationWorkJPanel) owner).getSSFSelectedStory(file), "UTF-8"));

                                    BhaashikClient client = main.getBhaashikClient(ClientType.SYNTACTIC_ANNOTATION, file.getAbsolutePath());

                                    ((BhaashikDefaultJTable) jtable).addEventListener((EventListener) client);

                                    ((BhaashikDefaultJTable) jtable).fireEvent(new FindEvent(jtable, FindEvent.FIND_AND_NAVIGATE,
                                            nodeIDString, Integer.parseInt((String) sentenceIDString) - 1, file.getAbsolutePath(), "UTF-8"));
                                    //((BhaashikDefaultJTable) jtable).fireValidationNewEvent(new NavigatetoValidateEvent(this, NavigatetoValidateEvent.NAVIGATE_EVENT, nodeIDString, sentenceIDString, file.getAbsolutePath()));
                            }
                        }
                        else
                        {
                            ((BhaashikDefaultJTable) jtable).fireEvent(new FindEvent(jtable, FindEvent.FIND_AND_NAVIGATE,
                                    Integer.parseInt(((String) sentenceIDString)) - 1, null, null));
                            //((BhaashikDefaultJTable) jtable).fireValidationNewEvent(new NavigatetoValidateEvent(this, NavigatetoValidateEvent.NAVIGATE_EVENT, nodeIDString, sentenceIDString, null));
                        }
                        }
                        else
                        {
                            ((BhaashikDefaultJTable) jtable).fireEvent(new FindEvent(jtable, FindEvent.FIND_AND_NAVIGATE,
                                    nodeIDString, Integer.parseInt(((String) sentenceIDString)) - 1, null, null));
                            //((BhaashikDefaultJTable) jtable).fireValidationNewEvent(new NavigatetoValidateEvent(this, NavigatetoValidateEvent.NAVIGATE_EVENT, nodeIDString, sentenceIDString, null));
                        }
                    }
                }
                else if(currentValue instanceof File && ((File) currentValue).canRead())
                {
                    if(owner != null && owner instanceof SyntacticAnnotationWorkJPanel)
                    {
                        BhaashikMain main = (BhaashikMain) ((SyntacticAnnotationWorkJPanel) owner).getOwner();

                        if(main != null)
                        {
                            main.fireEvent(new BhaashikMainEvent(jtable, BhaashikMainEvent.DISPLAY_FILE,
                                    ClientType.SYNTACTIC_ANNOTATION,
                                    ((SyntacticAnnotationWorkJPanel) owner).getSSFSelectedStory((File) currentValue), "UTF-8"));

                            BhaashikClient client = main.getBhaashikClient(ClientType.SYNTACTIC_ANNOTATION, ((File) currentValue).getAbsolutePath());

                            ((BhaashikDefaultJTable) jtable).addEventListener((EventListener) client);

                            String sentenceIDString = (String) jtable.getModel().getValueAt(row, 0);

                            String parts[] = sentenceIDString.split("::");

                            sentenceIDString = parts[0];
                            String nodeIDString="";
                            if(parts.length>2)
                                nodeIDString = parts[1];


                            if(UtilityFunctions.isInteger(sentenceIDString))
                            {
                                ((BhaashikDefaultJTable) jtable).fireEvent(new FindEvent(jtable, FindEvent.FIND_AND_NAVIGATE,
                                        nodeIDString, Integer.parseInt(sentenceIDString) - 1, ((File) currentValue).getAbsolutePath(), "UTF-8"));
                            }
                        }
                    }
                }
            }

            else if(mode == VALIDATION_MODE)
            {

                button.setText((String) currentValue);

                System.out.println("currentval: "+currentValue);

                if(currentValue != null && table!=null)
                {
                    try{
                        String split[] = currentValue.toString().split("::");
                        if(split.length==3)
                        {
                        String fileID = split[split.length-1];
                        String sentID = split[split.length-2];
                        String nodeID = split[0];
                        System.err.println(fileID+sentID+nodeID);

                        ((BhaashikDefaultJTable) table).fireValidationEvent(new NavigatetoValidateEvent(this, NavigatetoValidateEvent.NAVIGATE_EVENT, nodeID, sentID, fileID));

                        }
                    }
                    catch(Exception ex)
                    {

                    }
                }
            }
            
//            currentColor = colorChooser.getColor();
        }
    }

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    public Object getCellEditorValue() {
//        return editor.getModel().getName();
        if(currentValue != null && currentValue.getClass().equals(DictionaryFSTMutableNode.class) && mode == DICTIONARY_FST_MODE)
        {
            DictionaryFSTMutableNode node = (DictionaryFSTMutableNode) currentValue;
            node.getDictionaryFSTNode().getFeatureStructure().setFeatureTable(currentTable);
        }
        
        return currentValue;
    }

    //Implement the one method defined by TableCellEditor.
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        currentValue = value;
        this.row = row;
        this.column = column;
        
        Object val = table.getValueAt(row, column);

        if(mode == FIND_AND_NAVIGATE)
        {
            if(currentValue instanceof String)
                button.setText((String) currentValue);
            else if(currentValue instanceof File)
                button.setText(((File) currentValue).getName());
        }
        else if(val != null && val.getClass().equals(DictionaryFSTMutableNode.class) && mode == DICTIONARY_FST_MODE)
        {
            DictionaryFSTMutableNode node = (DictionaryFSTMutableNode) table.getValueAt(row, column);
            currentValue = node;
            currentTable = node.getDictionaryFSTNode().getFeatureStructure().getFeatureTable();
        }
        else if(mode == VALIDATION_MODE)
        {
            this.table = table;
            button.setText((String) val);
        }

        jtable = table;

        return button;
    }
}
