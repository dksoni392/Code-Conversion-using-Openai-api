/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.table.gui;

import java.util.Hashtable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author anil
 */
public class RowRendererModel {

    private Hashtable data;

    public RowRendererModel()
    {
        data = new Hashtable();
    }

    public void addRendererForRow(int row, TableCellRenderer r)
    {
        data.put(Integer.valueOf(row), r);
    }

    public void removeEditorForRow(int row)
    {
        data.remove(Integer.valueOf(row));
    }

    public TableCellRenderer getRenderer(int row)
    {
        return (TableCellRenderer) data.get(Integer.valueOf(row));
    }
}
