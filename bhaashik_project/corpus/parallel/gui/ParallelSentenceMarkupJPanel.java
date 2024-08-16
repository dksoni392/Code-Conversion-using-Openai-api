/*
 * ParallelSentenceMarkupJPanel.java
 *
 * Created on April 24, 2006, 8:54 PM
 */

package bhaashik.corpus.parallel.gui;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.*;

import bhaashik.GlobalProperties;
import bhaashik.table.BhaashikTableModel;
import bhaashik.table.gui.BhaashikTableAction;
import bhaashik.table.gui.BhaashikTableJPanel;
import bhaashik.properties.KeyValueProperties;
import bhaashik.resources.aggregate.ParallelMarkupTask;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author  anil
 */
public class ParallelSentenceMarkupJPanel extends javax.swing.JPanel {
    
    protected ParallelMarkupTask parallelMarkupTask;
    
    private JFrame owner;
    private JDialog dialog;

    private DefaultComboBoxModel positions;
    
    private String srcSentence;
    private String tgtSentence;
    private String tgtUTF8Sentence;
    private String comment;
    
    // For mapping table
    private JComboBox srcJComboBoxEditor;
    private JComboBox tgtJComboBoxEditor;
    
    private DefaultComboBoxModel comments;

    // For display, mapping of marker String will be used
    // For storage, mapping of marker indices will be used
    private BhaashikTableModel markerMapping; // Strings
    
    private Color startColor;
    private int colorIncrement;
    
    private boolean utf8Shown;
    private boolean newComboBoxElementAdded;
    
    private KeyValueProperties markerDict;
    private KeyValueProperties reverseMarkerDict;
    
    /** Creates new form ParallelSentenceMarkupJPanel */
    public ParallelSentenceMarkupJPanel(ParallelMarkupTask task, int currentPosition) {
	initComponents();
	
	parallelMarkupTask = task;
	parallelMarkupTask.setCurrentPosition(currentPosition);
	
//	srcTMJComboBox.setKeySelectionManager(new JComboBoxKeySelectionManager());
//	tgtTMJComboBox.setKeySelectionManager(new JComboBoxKeySelectionManager());
	
	srcTMJEditCheckBox.setVisible(false);
	tgtTMJEditCheckBox.setVisible(false);
        
        srcJComboBoxEditor = new JComboBox();
        tgtJComboBoxEditor = new JComboBox();
        startColor = new Color(200, 150, 220);
        colorIncrement = 30;
        
        utf8Shown = true;
        newComboBoxElementAdded = false;
        
        // For now
        srcClearMarkerJButton.setVisible(false);
        tgtClearMarkerJButton.setVisible(false);
        
        int cmds[] = new int[3];
        cmds[0] = BhaashikTableAction.DEL_ROW;
        cmds[1] = BhaashikTableAction.ADD_ROW;
        cmds[2] = BhaashikTableAction.INSERT_ROW;

        markerMappingJPanel.remove(bhaashikTableJPanel);
        bhaashikTableJPanel = new BhaashikTableJPanel(false, cmds, BhaashikTableJPanel.MINIMAL_MODE, parallelMarkupTask.getLangEnc());
        bhaashikTableJPanel.setLayout(new javax.swing.BoxLayout(bhaashikTableJPanel, javax.swing.BoxLayout.X_AXIS));
        bhaashikTableJPanel.setVisible(true);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(false);
        markerMappingJPanel.add(bhaashikTableJPanel, java.awt.BorderLayout.NORTH);
        ((BhaashikTableJPanel) bhaashikTableJPanel).getJTable().setFont(new java.awt.Font(GlobalProperties.getIntlString("Dialog"), 1, 16));
	
//	((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(false);
	
	// For the time being
	ss2JPanel.setVisible(false);
	ts2JPanel.setVisible(false);
	commentJLabel.setVisible(false);
	commentJComboBox.setVisible(false);
	
	displayCurrentPosition();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        senJPanel = new javax.swing.JPanel();
        srcSenJPanel = new javax.swing.JPanel();
        ss1JPanel = new javax.swing.JPanel();
        srcSenJScrollPane = new javax.swing.JScrollPane();
        srcSenJTextArea = new javax.swing.JTextArea();
        srcSenStatusJLabel = new javax.swing.JLabel();
        ss2JPanel = new javax.swing.JPanel();
        srcTMJLabel = new javax.swing.JLabel();
        srcTMJComboBox = new javax.swing.JComboBox();
        srcClearMarkerJButton = new javax.swing.JButton();
        srcTMJEditCheckBox = new javax.swing.JCheckBox();
        tgtSenJPanel = new javax.swing.JPanel();
        ts1JPanel = new javax.swing.JPanel();
        tgtSenJScrollPane = new javax.swing.JScrollPane();
        tgtSenJTextArea = new javax.swing.JTextArea();
        tgtSenStatusJLabel = new javax.swing.JLabel();
        ts1UTF8JPanel = new javax.swing.JPanel();
        tgtSenUTF8JScrollPane = new javax.swing.JScrollPane();
        tgtSenUTF8JTextArea = new javax.swing.JTextArea();
        ts2JPanel = new javax.swing.JPanel();
        tgtTMJLabel = new javax.swing.JLabel();
        tgtTMJComboBox = new javax.swing.JComboBox();
        tgtClearMarkerJButton = new javax.swing.JButton();
        tgtShowUTF8JButton = new javax.swing.JButton();
        tgtTMJEditCheckBox = new javax.swing.JCheckBox();
        markerMappingJPanel = new javax.swing.JPanel();
        bhaashikTableJPanel = new javax.swing.JPanel();
        markerMappingJScrollPane = new javax.swing.JScrollPane();
        markerMappingJTable = new javax.swing.JTable();
        commentJPanel = new javax.swing.JPanel();
        commentJLabel = new javax.swing.JLabel();
        commentJComboBox = new javax.swing.JComboBox();
        commentJScrollPane = new javax.swing.JScrollPane();
        commentJTextArea = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        senJPanel.setLayout(new javax.swing.BoxLayout(senJPanel, javax.swing.BoxLayout.Y_AXIS));

        srcSenJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Source Language Sentence", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        srcSenJPanel.setLayout(new java.awt.BorderLayout());

        ss1JPanel.setLayout(new java.awt.BorderLayout());

        srcSenJScrollPane.setPreferredSize(new java.awt.Dimension(103, 25));

        srcSenJTextArea.setEditable(false);
        srcSenJTextArea.setFont(new java.awt.Font("Dialog", 1, 12));
        srcSenJTextArea.setLineWrap(true);
        srcSenJScrollPane.setViewportView(srcSenJTextArea);

        ss1JPanel.add(srcSenJScrollPane, java.awt.BorderLayout.CENTER);

        srcSenStatusJLabel.setText(" ");
        srcSenStatusJLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ss1JPanel.add(srcSenStatusJLabel, java.awt.BorderLayout.SOUTH);

        srcSenJPanel.add(ss1JPanel, java.awt.BorderLayout.NORTH);

        ss2JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        srcTMJLabel.setText(bundle.getString("Marker:_")); // NOI18N
        ss2JPanel.add(srcTMJLabel);

        srcTMJComboBox.setEditable(true);
        srcTMJComboBox.setFont(new java.awt.Font("Dialog", 1, 14));
        ss2JPanel.add(srcTMJComboBox);

        srcClearMarkerJButton.setText(bundle.getString("Clear_Marker")); // NOI18N
        ss2JPanel.add(srcClearMarkerJButton);

        srcTMJEditCheckBox.setText(bundle.getString("Editable")); // NOI18N
        srcTMJEditCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        srcTMJEditCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ss2JPanel.add(srcTMJEditCheckBox);

        srcSenJPanel.add(ss2JPanel, java.awt.BorderLayout.CENTER);

        senJPanel.add(srcSenJPanel);

        tgtSenJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Target Language Sentence", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        tgtSenJPanel.setLayout(new javax.swing.BoxLayout(tgtSenJPanel, javax.swing.BoxLayout.Y_AXIS));

        ts1JPanel.setLayout(new java.awt.BorderLayout());

        tgtSenJScrollPane.setPreferredSize(new java.awt.Dimension(103, 25));

        tgtSenJTextArea.setEditable(false);
        tgtSenJTextArea.setFont(new java.awt.Font("Dialog", 1, 16));
        tgtSenJTextArea.setLineWrap(true);
        tgtSenJTextArea.setWrapStyleWord(true);
        tgtSenJScrollPane.setViewportView(tgtSenJTextArea);

        ts1JPanel.add(tgtSenJScrollPane, java.awt.BorderLayout.CENTER);

        tgtSenStatusJLabel.setText(" ");
        tgtSenStatusJLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ts1JPanel.add(tgtSenStatusJLabel, java.awt.BorderLayout.SOUTH);

        tgtSenJPanel.add(ts1JPanel);

        ts1UTF8JPanel.setLayout(new java.awt.BorderLayout());

        tgtSenUTF8JScrollPane.setPreferredSize(new java.awt.Dimension(120, 25));

        tgtSenUTF8JTextArea.setEditable(false);
        tgtSenUTF8JTextArea.setFont(new java.awt.Font("Dialog", 1, 16));
        tgtSenUTF8JTextArea.setLineWrap(true);
        tgtSenUTF8JTextArea.setWrapStyleWord(true);
        tgtSenUTF8JScrollPane.setViewportView(tgtSenUTF8JTextArea);

        ts1UTF8JPanel.add(tgtSenUTF8JScrollPane, java.awt.BorderLayout.CENTER);

        tgtSenJPanel.add(ts1UTF8JPanel);

        ts2JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        tgtTMJLabel.setText(bundle.getString("Marker:_")); // NOI18N
        ts2JPanel.add(tgtTMJLabel);

        tgtTMJComboBox.setEditable(true);
        tgtTMJComboBox.setFont(new java.awt.Font("Dialog", 1, 16));
        ts2JPanel.add(tgtTMJComboBox);

        tgtClearMarkerJButton.setText(bundle.getString("Clear_Marker")); // NOI18N
        ts2JPanel.add(tgtClearMarkerJButton);

        tgtShowUTF8JButton.setText(bundle.getString("Hide_UTF8")); // NOI18N
        ts2JPanel.add(tgtShowUTF8JButton);

        tgtTMJEditCheckBox.setText(bundle.getString("Editable")); // NOI18N
        tgtTMJEditCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tgtTMJEditCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ts2JPanel.add(tgtTMJEditCheckBox);

        tgtSenJPanel.add(ts2JPanel);

        senJPanel.add(tgtSenJPanel);

        add(senJPanel, java.awt.BorderLayout.NORTH);

        markerMappingJPanel.setLayout(new java.awt.BorderLayout());

        bhaashikTableJPanel.setLayout(new java.awt.BorderLayout());

        markerMappingJScrollPane.setFont(new java.awt.Font("Dialog", 1, 12));
        markerMappingJScrollPane.setPreferredSize(new java.awt.Dimension(120, 128));

        markerMappingJTable.setFont(new java.awt.Font("Dialog", 1, 16));
        markerMappingJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        markerMappingJTable.setMaximumSize(new java.awt.Dimension(32767, 32767));
        markerMappingJTable.setPreferredSize(new java.awt.Dimension(300, 128));
        markerMappingJScrollPane.setViewportView(markerMappingJTable);

        bhaashikTableJPanel.add(markerMappingJScrollPane, java.awt.BorderLayout.CENTER);

        markerMappingJPanel.add(bhaashikTableJPanel, java.awt.BorderLayout.CENTER);

        commentJPanel.setLayout(new java.awt.BorderLayout());

        commentJLabel.setText(bundle.getString("Comment:_")); // NOI18N
        commentJPanel.add(commentJLabel, java.awt.BorderLayout.WEST);

        commentJComboBox.setEditable(true);
        commentJPanel.add(commentJComboBox, java.awt.BorderLayout.CENTER);

        commentJScrollPane.setPreferredSize(new java.awt.Dimension(120, 25));

        commentJTextArea.setFont(new java.awt.Font("Dialog", 1, 16));
        commentJTextArea.setLineWrap(true);
        commentJTextArea.setWrapStyleWord(true);
        commentJScrollPane.setViewportView(commentJTextArea);

        commentJPanel.add(commentJScrollPane, java.awt.BorderLayout.SOUTH);

        markerMappingJPanel.add(commentJPanel, java.awt.BorderLayout.SOUTH);

        add(markerMappingJPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    public void setCurrentPosition(int cp)
    {
	int currentPosition = parallelMarkupTask.getCurrentPosition();

	int slSize = parallelMarkupTask.getSrcCorpusPT().countTokens();
        if(cp >= 0 && cp < slSize)
        {
            if(cp != currentPosition)
            {
                storeCurrentPosition();
                parallelMarkupTask.setCurrentPosition(cp);
            }
            
            displayCurrentPosition();
        }
    }
    
    private void storeCurrentPosition()
    {
	int currentPosition = parallelMarkupTask.getCurrentPosition();

	parallelMarkupTask.getCommentsPT().modifyToken(commentJTextArea.getText(), currentPosition);
        
        if(markerMapping != null)
        {
            BhaashikTableModel markerIndexMapping = parallelMarkupTask.stringToIndexMappingTable(markerMapping);
            parallelMarkupTask.getMarkerMappingTables().put(Integer.toString(currentPosition + 1), markerIndexMapping);
        }
    }

    private void displayCurrentPosition()
    {
	int currentPosition = parallelMarkupTask.getCurrentPosition();
	    
        srcSenStatusJLabel.setText(" ");
        tgtSenStatusJLabel.setText(" ");

        srcSentence = (String) parallelMarkupTask.getSrcCorpusPT().getToken(currentPosition);
        srcSenJTextArea.setText(srcSentence);
        tgtSentence = (String) parallelMarkupTask.getTgtCorpusPT().getToken(currentPosition);
        tgtSenJTextArea.setText(tgtSentence);
        comment = (String) parallelMarkupTask.getCommentsPT().getToken(currentPosition);
        commentJTextArea.setText(comment);

        highlightCurrentMarkups();

        String currentPositionString = Integer.toString(currentPosition + 1);

        // Mapping at new position
        BhaashikTableModel markerIndexMapping = (BhaashikTableModel) parallelMarkupTask.getMarkerMappingTables().get(Integer.toString(currentPosition + 1));
        markerMapping = parallelMarkupTask.indexToStringMappingTable(markerIndexMapping);

        Vector cols = new Vector(2);
        cols.add(GlobalProperties.getIntlString("Source_Language_Marker"));
        cols.add(GlobalProperties.getIntlString("Target_Language_Marker"));
        markerMapping.setColumnIdentifiers(cols);

        ((BhaashikTableJPanel) bhaashikTableJPanel).setModel(markerMapping);

        srcJComboBoxEditor.removeAllItems();
        tgtJComboBoxEditor.removeAllItems();
        
        srcJComboBoxEditor.addItem("NONE");
        tgtJComboBoxEditor.addItem("NONE");

        BhaashikTableModel srcSenMarkup = (BhaashikTableModel) parallelMarkupTask.getSrcSenMarkups().get(Integer.toString(currentPosition + 1));
        int count = srcSenMarkup.getRowCount();
        for(int i = 0; i < count; i++)
        {
            int marker = Integer.parseInt((String) srcSenMarkup.getValueAt(i, 2));
            String sm = (String) srcTMJComboBox.getItemAt(marker);
            String srcMarker = (i + 1) + "::" + sm;
            UtilityFunctions.addItemToJCoboBox(srcJComboBoxEditor, srcMarker);
        }                

        BhaashikTableModel tgtSenMarkup = (BhaashikTableModel) parallelMarkupTask.getTgtSenMarkups().get(Integer.toString(currentPosition + 1));
        count = tgtSenMarkup.getRowCount();
        for(int i = 0; i < count; i++)
        {
            int marker = Integer.parseInt((String) tgtSenMarkup.getValueAt(i, 2));
            String tm = (String) tgtTMJComboBox.getItemAt(marker);
            String tgtMarker = (i + 1) + "::" + tm;
            UtilityFunctions.addItemToJCoboBox(tgtJComboBoxEditor, tgtMarker);
        }                

        TableColumn srcColumn = ((BhaashikTableJPanel) bhaashikTableJPanel).getJTable().getColumnModel().getColumn(0);
        srcColumn.setCellEditor(new DefaultCellEditor(srcJComboBoxEditor));

        TableColumn tgtColumn = ((BhaashikTableJPanel) bhaashikTableJPanel).getJTable().getColumnModel().getColumn(1);
        tgtColumn.setCellEditor(new DefaultCellEditor(tgtJComboBoxEditor));
        
        toggleTgtUTF8(utf8Shown);
    }
     
    private void highlightCurrentMarkups()
    {
        BhaashikTableModel srcSenMarkup = (BhaashikTableModel) parallelMarkupTask.getSrcSenMarkups().get(Integer.toString(parallelMarkupTask.getCurrentPosition() + 1));
        
        int count = srcSenMarkup.getRowCount();
        for(int i = 0; i < count; i++)
        {
            int start = Integer.parseInt((String) srcSenMarkup.getValueAt(i, 0));
            int end = Integer.parseInt((String) srcSenMarkup.getValueAt(i, 1));

            try
            {
                Highlighter hilite = srcSenJTextArea.getHighlighter();
                Document doc = srcSenJTextArea.getDocument();
                String text = doc.getText(0, doc.getLength());
                Color newColor = UtilityFunctions.generateColor(startColor, i, colorIncrement);
                MarkerHighlightPainter hp = new MarkerHighlightPainter(newColor);
                hilite.addHighlight(start, end + 1, hp);
            }
            catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        BhaashikTableModel tgtSenMarkup = (BhaashikTableModel) parallelMarkupTask.getTgtSenMarkups().get(Integer.toString(parallelMarkupTask.getCurrentPosition() + 1));
        
        count = tgtSenMarkup.getRowCount();
        for(int i = 0; i < count; i++)
        {
            int start = Integer.parseInt((String) tgtSenMarkup.getValueAt(i, 0));
            int end = Integer.parseInt((String) tgtSenMarkup.getValueAt(i, 1));

            try
            {
                Highlighter hilite = tgtSenJTextArea.getHighlighter();
                Document doc = tgtSenJTextArea.getDocument();
                String text = doc.getText(0, doc.getLength());
                Color newColor = UtilityFunctions.generateColor(startColor, i, colorIncrement);
                MarkerHighlightPainter hp = new MarkerHighlightPainter(newColor);
                hilite.addHighlight(start, end + 1, hp);
            }
            catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void clearPosition(int pos)
    {
        BhaashikTableModel srcSenMarkup = (BhaashikTableModel) parallelMarkupTask.getSrcSenMarkups().get(Integer.toString(pos + 1));
        BhaashikTableModel tgtSenMarkup = (BhaashikTableModel) parallelMarkupTask.getTgtSenMarkups().get(Integer.toString(pos + 1));
        BhaashikTableModel markerIndexMapping = (BhaashikTableModel) parallelMarkupTask.getMarkerMappingTables().get(Integer.toString(pos + 1));
        
        while(srcSenMarkup.getRowCount() > 0)
            srcSenMarkup.removeRow(0);

        while(tgtSenMarkup.getRowCount() > 0)
            tgtSenMarkup.removeRow(0);
        
        while(markerIndexMapping.getRowCount() > 0)
            markerIndexMapping.removeRow(0);
        
        parallelMarkupTask.getCommentsPT().modifyToken("", pos);
    }

    private void clearAll()
    {
        Cursor cursor = owner.getCursor();
        owner.setCursor(Cursor.WAIT_CURSOR);

        int count = parallelMarkupTask.getSrcCorpusPT().countTokens();
        
        for(int i = 0; i < count; i++)
            clearPosition(i);

        owner.setCursor(cursor);
    }
    
    private int[] getSelectedTokenBoundaries(JTextArea textArea)
    {
        // In terms of string indices
        int start = textArea.getSelectionStart();
        int end = textArea.getSelectionEnd() - 1;

        String text = textArea.getText();
        
        while(start > 0)
        {
            char cstart = text.charAt(start - 1);
            
            if(UtilityFunctions.isWordBoundary(cstart) == true)
            {
                break;
            }
            else
                start--;
        }
        
        while(end < text.length() - 1)
        {
            char cend = text.charAt(end + 1);
            
            if(UtilityFunctions.isWordBoundary(cend) == true)
            {
                break;
            }
            else
                end++;
        }
        
        int boundaries[] = {start, end};
        
        return boundaries;
    }
    
    public void toggleTgtUTF8(boolean f)
    {
        if(f == true) // show UTF8
        {
            tgtUTF8Sentence = (String) parallelMarkupTask.getTgtCorpusUTF8PT().getToken(parallelMarkupTask.getCurrentPosition());
            tgtSenUTF8JTextArea.setText(tgtUTF8Sentence);
            ts1UTF8JPanel.setVisible(true);
            tgtShowUTF8JButton.setText(GlobalProperties.getIntlString("Hide_UTF8"));
            utf8Shown = true;
        }
        else // hide
        {
            tgtSenUTF8JTextArea.setText("");
//            tgtSenUTF8JTextArea.setVisible(false);
            ts1UTF8JPanel.setVisible(false);
            tgtShowUTF8JButton.setText(GlobalProperties.getIntlString("See_UTF8"));
            utf8Shown = false;
        }
    }
    
    public void configure()
    {
	KeyValueProperties kvTaskProps = (KeyValueProperties) parallelMarkupTask.getProperties();
	
	try {
	    markerDict = new KeyValueProperties(kvTaskProps.getPropertyValue(GlobalProperties.getIntlString("MarkerDictFile")), kvTaskProps.getPropertyValue(GlobalProperties.getIntlString("MarkerDictCharset")));
	} catch (FileNotFoundException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	
	reverseMarkerDict = markerDict.getReverse();

	srcTMJComboBox.setModel(parallelMarkupTask.getSrcTamMarkers());
	tgtTMJComboBox.setModel(parallelMarkupTask.getTgtTamMarkers());

	UtilityFunctions.addItemToJCoboBox(srcTMJComboBox, "NONE");
	UtilityFunctions.addItemToJCoboBox(tgtTMJComboBox, "NONE");

	int currentPosition = parallelMarkupTask.getCurrentPosition();
	comment = parallelMarkupTask.getCommentsPT().getToken(currentPosition);
//                commentJTextArea.setText(comment);

	comments = new DefaultComboBoxModel(parallelMarkupTask.getCommentsPT().getTypes());
	comments.removeElement("");

	commentJComboBox.setModel(comments);

	String pos = kvTaskProps.getPropertyValue("CurrentPosition");
	try
	{
	    int cp = Integer.parseInt(pos);
	    setCurrentPosition(cp - 1);
	}
	catch(NumberFormatException e)
	{
	    setCurrentPosition(0);
	}
    }
    
    public JFrame getOwner()
    {
        return owner;
    }
    
    public void setOwner(JFrame f)
    {
        owner = f;
    }
    
    public void setDialog(JDialog d)
    {
        dialog = d;
    }
    
    public void setTaskName(String tn)
    {
        parallelMarkupTask.setName(tn);
    }
    
    private class MarkerHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        public MarkerHighlightPainter(Color color) {
            super(color);
        }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox commentJComboBox;
    protected javax.swing.JLabel commentJLabel;
    protected javax.swing.JPanel commentJPanel;
    protected javax.swing.JScrollPane commentJScrollPane;
    protected javax.swing.JTextArea commentJTextArea;
    protected javax.swing.JPanel markerMappingJPanel;
    protected javax.swing.JScrollPane markerMappingJScrollPane;
    protected javax.swing.JTable markerMappingJTable;
    protected javax.swing.JPanel bhaashikTableJPanel;
    protected javax.swing.JPanel senJPanel;
    protected javax.swing.JButton srcClearMarkerJButton;
    protected javax.swing.JPanel srcSenJPanel;
    protected javax.swing.JScrollPane srcSenJScrollPane;
    protected javax.swing.JTextArea srcSenJTextArea;
    protected javax.swing.JLabel srcSenStatusJLabel;
    protected javax.swing.JComboBox srcTMJComboBox;
    protected javax.swing.JCheckBox srcTMJEditCheckBox;
    protected javax.swing.JLabel srcTMJLabel;
    protected javax.swing.JPanel ss1JPanel;
    protected javax.swing.JPanel ss2JPanel;
    protected javax.swing.JButton tgtClearMarkerJButton;
    protected javax.swing.JPanel tgtSenJPanel;
    protected javax.swing.JScrollPane tgtSenJScrollPane;
    protected javax.swing.JTextArea tgtSenJTextArea;
    protected javax.swing.JLabel tgtSenStatusJLabel;
    protected javax.swing.JScrollPane tgtSenUTF8JScrollPane;
    protected javax.swing.JTextArea tgtSenUTF8JTextArea;
    protected javax.swing.JButton tgtShowUTF8JButton;
    protected javax.swing.JComboBox tgtTMJComboBox;
    protected javax.swing.JCheckBox tgtTMJEditCheckBox;
    protected javax.swing.JLabel tgtTMJLabel;
    protected javax.swing.JPanel ts1JPanel;
    protected javax.swing.JPanel ts1UTF8JPanel;
    protected javax.swing.JPanel ts2JPanel;
    // End of variables declaration//GEN-END:variables
    
}
