/*
 * MorphFeatures.java
 *
 * Created on June 20, 2008, 9:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.feature.extraction;

import bhaashik.corpus.ssf.tree.SSFNode;

/* *
 * @author Anil Kumar Singh
 */
public interface MorphFeatures {

    public static final int MORPH_FEATURE_ROOT = 0;
    public static final int MORPH_FEATURE_POS_TAG = 1;
    public static final int MORPH_FEATURE_SUFFIX = 2;
    public static final int MORPH_FEATURE_PREFIX = 3;
    
    /**
     * Creates a new instance of MorphFeatures
     */
   
    public String getMorphFeature(SSFNode ssfNode, int feature);
    public String getRootWord(SSFNode ssfNode);
    public boolean checkPOSTag(SSFNode ssfNode, String tag);
    public String getSuffix(SSFNode ssfNode);
    public String getPrefix(SSFNode ssfNode);
    //public String getFeatureStructure(FeatureAttribute fattrib);
}
