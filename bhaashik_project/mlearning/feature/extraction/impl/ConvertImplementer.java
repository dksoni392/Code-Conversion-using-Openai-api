/*
 * ConvertImplementer.java
 *
 * Created on September 1, 2008, 3:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.feature.extraction.impl;

import bhaashik.formats.converters.ArffToSSF;
import bhaashik.mlearning.feature.extraction.Converter;

/**
 *
 * @author Anil Kumar Singh
 */
public class ConvertImplementer implements Converter {
    
    /** Creates a new instance of ConvertImplementer */
    public ConvertImplementer() {
    }
    
    public void convert(String sourceFileWithCorrectExtension, String destinationFileWithCorrectExtension)
    {
        //write some condition to detect whether SSF to SRFF or SRFF to SSF(I am assuming for now that we have to convert from ARFF to SSF
        ArffToSSF obj = new ArffToSSF(sourceFileWithCorrectExtension, destinationFileWithCorrectExtension);
    }
}
