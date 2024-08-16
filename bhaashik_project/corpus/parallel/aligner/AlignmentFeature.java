/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.parallel.aligner;

import bhaashik.corpus.parallel.APCProperties;
import bhaashik.speech.common.Feature;

/**
 *
 * @author anil
 */
public class AlignmentFeature extends Feature {

    protected APCProperties apcProperties;

    protected int index;

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int i)
	{
		index = i;
	}

    public APCProperties getAPCProperties()
	{
		return apcProperties;
	}

	public void setAPCProperties(APCProperties apcprop)
	{
		apcProperties = apcprop;
	}
}
