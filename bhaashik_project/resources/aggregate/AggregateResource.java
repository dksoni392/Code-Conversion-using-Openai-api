/*
 * AggregateResource.java
 *
 * Created on November 4, 2005, 5:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.resources.aggregate;

import bhaashik.properties.BhaashikProperties;
import bhaashik.resources.Resource;

/**
 *
 *  @author Anil Kumar Singh
 */
public interface AggregateResource extends Resource
{
    public BhaashikProperties getProperties();
    public void setProperties(BhaashikProperties tp);
}
