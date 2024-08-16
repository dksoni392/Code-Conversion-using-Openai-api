/*
 * Created on Sep 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf;

import bhaashik.corpus.parallel.Alignable;
import bhaashik.properties.KeyValueProperties;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SSFCorpusCollection  extends Alignable {
    public KeyValueProperties getCorporaPaths();

    public void setCorporaPaths(KeyValueProperties p);

    public KeyValueProperties getProperties();

    public void setProperties(KeyValueProperties p);
}