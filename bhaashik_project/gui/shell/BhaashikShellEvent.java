/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.shell;

import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author anil
 */
public class BhaashikShellEvent extends BhaashikEvent {

    public static final int SHELL_COMMAND_EVENT = 0;
    public static final int HISTORY_NEXT_EVENT = 1;
    public static final int HISTORY_PREVIOUS_EVENT = 2;
    public static final int AUTO_COMPLETION_EVENT = 3;

    /** Creates a new instance of TreeViewerEvent */
    public BhaashikShellEvent(Object source, int id) {
        super(source, id);
    }
}
