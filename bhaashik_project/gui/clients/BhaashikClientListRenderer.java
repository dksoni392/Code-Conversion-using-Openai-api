/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.clients;

import java.awt.Component;
import java.io.Serializable;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bhaashik.common.types.ClientType;

/**
 *
 * @author Anil Kumar Singh
 */
public class BhaashikClientListRenderer extends DefaultListCellRenderer
        implements ListCellRenderer<Object>, Serializable
{
    public BhaashikClientListRenderer() {
	super();
    }

    public Component getListCellRendererComponent(
        JList list,
	Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        JPanel nodeRendered = (JPanel) value;        
        
        ClientType ctype = (ClientType) ClientType.findFromClassName(nodeRendered.getClass().getName());
        
        //setText(ctype.toString());
        setText(nodeRendered.getName());
             
        return this;
    }
}
