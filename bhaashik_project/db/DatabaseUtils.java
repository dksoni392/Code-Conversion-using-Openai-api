/*
 * DatabaseUtils.java
 *
 * Created on July 25, 2008, 11:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import bhaashik.GlobalProperties;
import bhaashik.db.gui.ConnectToDBJPanel;
import bhaashik.gui.common.BhaashikJDialog;
import bhaashik.gui.common.DialogFactory;
import bhaashik.table.BhaashikTableModel;

/**
 *
 * @author Anil Kumar Singh
 */
public class DatabaseUtils {
    
    public static String DERBY_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String DERBY_PROTCOL = "jdbc:derby:";
    public static String DERBY_DB_NAME = "derbyDB";
    public static String DERBY_URL = "jdbc:derby:derbyDB";
    
    public static int NUM_SCHEMA_COLUMNS = 7;
    
    /** Creates a new instance of DatabaseUtils */
    public DatabaseUtils()
    {
    }
    
    public static ConnectToDBJPanel connectToDatabase(JFrame owner)
    {
        BhaashikJDialog dbConnDialog = (BhaashikJDialog) DialogFactory.showDialog(ConnectToDBJPanel.class, owner, GlobalProperties.getIntlString("Connect_to_Database"), true);
        
        return (ConnectToDBJPanel) dbConnDialog.getJPanel();        
    }

    public static ConnectToDBJPanel connectToDatabase(JDialog owner)
    {
        BhaashikJDialog dbConnDialog = (BhaashikJDialog) DialogFactory.showDialog(ConnectToDBJPanel.class, owner, GlobalProperties.getIntlString("Connect_to_Database"), true);
        
        return (ConnectToDBJPanel) dbConnDialog.getJPanel();        
    }
    
    public static BhaashikTableModel createDatabaseSchemaTableModel(JTable tableJTable, String langEnc)
    {
        BhaashikTableModel bhaashikTableModel = (BhaashikTableModel) tableJTable.getModel();
        
//        bhaashikTableModel.setValueAt("INT", 0, 1);
        
//        clearTableMode(bhaashikTableModel);

        ArrayList<String> columnName = new ArrayList<>();
        
        columnName.add("Column Name");
        columnName.add("Data Type");
        columnName.add("Primary Key");
        columnName.add("Not NULL");
        columnName.add("Unique");
        columnName.add("Default");
        columnName.add("Check");        
        
//        bhaashikTableModel.addColumns(NUM_SCHEMA_COLUMNS, "");
        
        bhaashikTableModel.setColumnIdentifiers(columnName.toArray());
                
        Field[] fields = java.sql.Types.class.getFields();
        
        Vector fieldsVec = new Vector();
        
        for (Object object : fields) {
            fieldsVec.add(object);
        }
        
//        String[] dataTypes = new String[] {"INT", "VARCHAR", "String"};
        
//        setTableCellEditor(1, "Data Type", fields, langEnc, tableJTable);
        setTableCellEditor(1, "Data Type", fieldsVec, langEnc, tableJTable);

        bhaashikTableModel.addRow();

        bhaashikTableModel.fireTableStructureChanged();
        bhaashikTableModel.fireTableDataChanged();
                        
        return bhaashikTableModel;
    }
    
    public static void clearTableMode(BhaashikTableModel tableModel)
    {
        int numRow = tableModel.getRowCount();
        
        for (int i = 0; i < numRow; i++) {
            tableModel.removeRow(0);
        }

        int numCol = tableModel.getColumnCount();
        
        for (int i = 0; i < numCol; i++) {
            tableModel.removeColumn(0);
        }
        
        tableModel.fireTableStructureChanged();
    }

    public static void setTableCellEditor(int colIndex, String identifier, Vector values, String langEnc, JTable tableJTable)
    {
        DefaultComboBoxModel labelEditorModel = new DefaultComboBoxModel(values);
        
//        for (Object value : values) {
//            labelEditorModel.addElement(value);
//        }
        labelEditorModel.insertElementAt("[.]*", 0);
        JComboBox labelEditor = new JComboBox();
        labelEditor.setModel(labelEditorModel);
//        UtilityFunctions.setComponentFont(labelEditor, langEnc);
        
        DefaultTableModel tableModel = (DefaultTableModel) tableJTable.getModel();
        
        System.out.println("Number of columns: " + tableJTable.getModel().getColumnCount());
        System.out.println("Number of rows: " + tableJTable.getModel().getRowCount());
        
        System.out.println("Number of columns: " + tableModel.getColumnCount());
        System.out.println("Number of rows: " + tableModel.getRowCount());

//        TableColumn schemaCol = tableJTable.getColumnModel().getColumn(colIndex);
        TableColumn schemaCol = tableJTable.getColumn(identifier);
        labelEditor.setEditable(true);
        schemaCol.setCellEditor(new DefaultCellEditor(labelEditor));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        schemaCol.setCellRenderer(renderer);
        
//        tableModel.setValueAt("INT", 0, 1);
        
        tableJTable.setVisible(false);
        tableJTable.setVisible(true);
        
        tableModel.fireTableStructureChanged();
    }
    
    
}
