/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.common;

import java.awt.Component;
import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author anil
 */
public class KeystrokeCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer, Serializable {

    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(column == 2)
        {
            Object keyStrokeObj = table.getModel().getValueAt(row, column);
            KeyStroke keyStroke = null;

            if(keyStrokeObj instanceof String)
                keyStroke = KeyStroke.getKeyStroke((String) keyStrokeObj);
            else
                keyStroke = (KeyStroke) keyStrokeObj;

            if(Character.isDefined((char) keyStroke.getKeyCode()))
                setText(KeystrokeCellEditor.getKeyStrokeLabel(keyStroke));
            else
                setText("");
        }
        else
            setText((String) value);

    	return this;
    }
}
