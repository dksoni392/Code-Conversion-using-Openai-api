/*
 * BhaashikTask.java
 *
 * Created on November 1, 2005, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.resources.aggregate;


import bhaashik.GlobalProperties;
import bhaashik.properties.BhaashikProperties;
import bhaashik.resources.ResourceImpl;

/**
 *
 *  @author Anil Kumar Singh
 * Tasks will be used locally. If some specific has to shared by users
 * then it will have a corresponding remote object (server).
 */
public class AggregateResourceImpl extends ResourceImpl implements AggregateResource {

    java.util.ResourceBundle bundle = GlobalProperties.getResourceBundle(); // NOI18N

    protected BhaashikProperties taskProps;
    
    /** Creates a new instance of AggregateResourceImpl */
    public AggregateResourceImpl() {
	super();
    }

    public AggregateResourceImpl(String fp, String cs) {
	filePath = fp;
	charset = cs;
    }

    public AggregateResourceImpl(String fp, String cs, String langEnc, String nm) {
        filePath = fp;
        charset = cs;

        this.langEnc = langEnc;
        name = nm;
    }

    public BhaashikProperties getProperties()
    {
	return taskProps;
    }
    
    public void setProperties(BhaashikProperties tp)
    {
	taskProps = tp;
    }
}
