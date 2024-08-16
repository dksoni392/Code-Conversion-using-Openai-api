/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.mlearning.weka;

import bhaashik.GlobalProperties;
import iitb.crf.DataIter;
import iitb.crf.DataSequence;

/**
 *
 * @author anil
 */
public class DefaultWekaData implements DataIter {
    @Override
    public void startScan()
    {
        throw new UnsupportedOperationException(GlobalProperties.getIntlString("Not_supported_yet."));
    }

    @Override
    public boolean hasNext()
    {
        throw new UnsupportedOperationException(GlobalProperties.getIntlString("Not_supported_yet."));
    }

    @Override
    public DataSequence next()
    {
        throw new UnsupportedOperationException(GlobalProperties.getIntlString("Not_supported_yet."));
    }

}
