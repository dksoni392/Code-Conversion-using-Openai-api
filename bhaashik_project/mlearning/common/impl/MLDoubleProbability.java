/*
 * MLDoubleProbability.java
 *
 * Created on June 29, 2006, 1:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.common.impl;

import bhaashik.mlearning.common.MLProbability;

/**
 *
 * @author Anil Kumar Singh
 */
public class MLDoubleProbability implements MLProbability
{
    protected Double probability;
    
    /** Creates a new instance of MLDoubleProbability */
    public MLDoubleProbability(double prob)
    {
	probability = Double.valueOf(prob);
    }

    public Double getProbability()
    {
	return probability;
    }

    public void setProbability(Double prob)
    {
	probability = prob;
    }

    public String toString()
    {
        return probability.toString();
    }
}
