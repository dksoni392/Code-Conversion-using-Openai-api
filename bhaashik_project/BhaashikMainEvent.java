/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik;

import bhaashik.common.types.ClientType;
import bhaashik.gui.clients.BhaashikClient;
import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author anil
 */
public class BhaashikMainEvent extends BhaashikEvent {

    protected BhaashikClient bhaashikClient;
    protected ClientType clientType;

    protected Object displayObject;
    protected String file;
    protected String charset;

    public static final int OPEN_TAB = 0;
    public static final int DISPLAY_FILE = 1;

    /** Creates a new instance of TreeViewerEvent */
    public BhaashikMainEvent(Object source, int id, BhaashikClient bhaashikClient) {
        super(source, id);

        this.bhaashikClient = bhaashikClient;
    }

    public BhaashikMainEvent(Object source, int id, ClientType clientType) {
        super(source, id);

        this.clientType = clientType;
    }

    public BhaashikMainEvent(Object source, int id, ClientType clientType, String file, String charset) {
        super(source, id);

        this.clientType = clientType;
        this.file = file;
        this.charset = charset;
    }

    public BhaashikMainEvent(Object source, int id, ClientType clientType, Object displayObject, String charset) {
        super(source, id);

        this.clientType = clientType;
        this.displayObject = displayObject;
        this.charset = charset;
    }

    public ClientType getClientType()
    {
        return clientType;
    }

    public Object getDisplayObject()
    {
        return displayObject;
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
