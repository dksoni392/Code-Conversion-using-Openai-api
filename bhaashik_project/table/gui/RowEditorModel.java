/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.table.gui;

import java.util.Hashtable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author anil
 */
public class RowEditorModel
{
    private Hashtable data;

    public RowEditorModel()
    {
        data = new Hashtable();
    }

    public void addEditorForRow(int row, TableCellEditor e)
    {
        data.put(Integer.valueOf(row), e);
    }

    public void removeEditorForRow(int row)
    {
        data.remove(Integer.valueOf(row));
    }

    public TableCellEditor getEditor(int row)
    {
        return (TableCellEditor) data.get(Integer.valueOf(row));
    }
}
