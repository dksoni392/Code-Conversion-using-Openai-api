/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.table.gui;

import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author anil
 */
public class FindEvent extends BhaashikEvent {

    protected int sentenceID;

    protected String nodeID;
    protected String file;
    protected String charset;

    public static final int FIND_AND_NAVIGATE = 0;

    /** Creates a new instance of TreeViewerEvent */
    public FindEvent(Object source, int id, int sentenceId, String file, String charset) {
        super(source, id);

        this.sentenceID = sentenceId;
        this.file = file;
        this.charset = charset;
    }

    public FindEvent(Object source, int id, String nodeId, int sentenceId, String file, String charset) {
        super(source, id);

        this.sentenceID = sentenceId;
        this.file = file;
        this.charset = charset;
        this.nodeID = nodeId;
    }

    public int getSentenceID()
    {
        return sentenceID;
    }

    public String getNodeID()
    {
        return nodeID;
    }

    public String getFilePath()
    {
        return file;
    }

    public String getCharset()
    {
        return charset;
    }
}
