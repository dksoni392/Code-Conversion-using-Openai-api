/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.ssf.gui;

import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author Dave
 */
public class NavigatetoValidateEvent extends BhaashikEvent {

    public static final int NAVIGATE_EVENT = 100;
    protected String sentenceID;
    protected String nodeID;
    protected String fileID;

    public NavigatetoValidateEvent(Object source, int id, String nodeID, String sentenceID, String fileID) {
        super(source, id);
        this.nodeID = nodeID;
        this.sentenceID = sentenceID;        
        this.fileID = fileID;
    }

    public String[] getLocationStringArray()
    {
        return new String[] {this.nodeID, this.sentenceID, this.fileID};
    }

}
