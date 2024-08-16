/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.table.gui;

import java.util.EventListener;
import javax.accessibility.Accessible;
import javax.swing.JTable;
import javax.swing.Scrollable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;

import bhaashik.corpus.ssf.gui.NavigatetoValidateEvent;
import bhaashik.corpus.ssf.gui.NavigatetoValidateEventListener;
import bhaashik.gui.common.FileDisplayer;
import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author anil
 */
public class BhaashikDefaultJTable extends JTable implements TableModelListener, Scrollable,
    TableColumnModelListener, ListSelectionListener, CellEditorListener,
    Accessible, RowSorterListener {

    protected transient javax.swing.event.EventListenerList listenerListLocal = new javax.swing.event.EventListenerList();
    // This methods allows classes to register for MyEvents

    public void addEventListener(EventListener listener)
    {
        listenerListLocal.add(EventListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeEventListener(EventListener listener)
    {
        listenerListLocal.remove(EventListener.class, listener);
    }

    public void fireEvent(BhaashikEvent evt)
    {
        Object[] listeners = listenerListLocal.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i++)
        {
            if (listeners[i] instanceof FindEventListener)
            {
                ((FindEventListener) listeners[i]).findAndNavigate((FindEvent) evt);
            }
            else if (listeners[i] instanceof FileDisplayer)
            {
                ((FileDisplayer) listeners[i]).displayFile((DisplayEvent) evt);
            }
        }
    }

    public void fireValidationEvent(BhaashikEvent evt)
    {

        Object[] listeners = listenerListLocal.getListenerList();

        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance

        for (int i = 0; i < listeners.length; i++)
        {

            if (listeners[i] instanceof NavigatetoValidateEventListener)
            {
                ((NavigatetoValidateEventListener) listeners[i]).navigateToSentenceToValidate((NavigatetoValidateEvent) evt);
            }
        }
    }

}
