/*
 * Created on Sep 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.manager;

import bhaashik.apps.Application;
import bhaashik.corpus.Corpus;
import bhaashik.properties.PropertiesManager;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CorpusManager implements Application {
    
    private PropertiesManager propman;
    
    private Corpus currentCorpus;

    /**
     * 
     */
    public CorpusManager() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
    }

    public PropertiesManager getPropertiesManager()
    {
        return propman;
    }
    
    public void setPropertiesManager(PropertiesManager pm)
    {
        propman = pm;
    }
}
