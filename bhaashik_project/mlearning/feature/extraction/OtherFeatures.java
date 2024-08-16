/*
 * OtherFeatures.java
 *
 * Created on September 5, 2008, 4:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.feature.extraction;

import bhaashik.corpus.ssf.tree.SSFNode;

/**
 *
 * @author Anil Kumar Singh
 */
public interface OtherFeatures {
    
    public boolean isSentenceStart(SSFNode node);
    public boolean isNumber(SSFNode node);
    public boolean isFourNumbers(SSFNode node);
    public int characterCount(SSFNode node);
}
