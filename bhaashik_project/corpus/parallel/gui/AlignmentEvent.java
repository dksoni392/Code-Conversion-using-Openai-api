/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.parallel.gui;

import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author anil
 */
public class AlignmentEvent extends BhaashikEvent {

    public static final int ALIGNMENT_CHANGED_EVENT = 0;


    /** Creates a new instance of TreeViewerEvent */
    public AlignmentEvent(Object source, int id) {
        super(source, id);
    }
}
