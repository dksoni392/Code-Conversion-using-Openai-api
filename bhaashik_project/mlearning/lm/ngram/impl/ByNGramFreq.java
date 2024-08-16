/*
 * Created on Jul 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.mlearning.lm.ngram.impl;

import bhaashik.mlearning.lm.ngram.NGramCount;

import java.util.Comparator;
import java.util.Map;


/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

//  compare() Inconsistent with equals()
public class ByNGramFreq implements Comparator {

    /**
     * 
     */
	public int compare(Object one, Object two)
	{
            if(one instanceof Map.Entry)
            {
		return ( (int) ((NGramCount) ((Map.Entry) one).getValue()).getFreq()
                        - (int) ((NGramCount) ((Map.Entry) two).getValue()).getFreq() );
            }
            
            return ( (int) ((NGramCount) one).getFreq() - (int) ((NGramCount) two).getFreq() );
	}
}
