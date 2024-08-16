/*
 * BhaashikEncodingConverter.java
 *
 * Created on January 16, 2008, 4:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.text.enc.conv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author anil
 */
public interface BhaashikEncodingConverter {
    String convert(String in);    
    void convert(File inFile, File outFile) throws FileNotFoundException, IOException;
    void convertBatch(File inFile, File outFile) throws FileNotFoundException, IOException;
}
