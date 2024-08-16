/*
 * Created on Aug 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import bhaashik.corpus.CorpusCollection;
import bhaashik.corpus.parallel.Alignable;
import bhaashik.corpus.parallel.AlignmentUnit;
import bhaashik.corpus.ssf.SSFCorpus;
import bhaashik.corpus.ssf.SSFCorpusCollection;
import bhaashik.corpus.ssf.SSFStory;
import bhaashik.properties.KeyValueProperties;
import java.util.List;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SSFCorpusCollectionImpl extends CorpusCollection
        implements SSFCorpusCollection {

    /**
     * 
     */

    private KeyValueProperties properties;
    private KeyValueProperties corporaPaths;

    public SSFCorpusCollectionImpl() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SSFCorpusCollectionImpl(String pf /*props file*/, String cf /*paths file*/, String charset)  throws FileNotFoundException, IOException {
        super();
        // TODO Auto-generated constructor stub
        properties = new KeyValueProperties(pf, charset);
        corporaPaths = new KeyValueProperties(cf, charset);
    }
	
    public KeyValueProperties getCorporaPaths()
    {
        return properties;
    }

    public void setCorporaPaths(KeyValueProperties p)
    {
        properties = p;
    }

    public KeyValueProperties getProperties()
    {
        return properties;
    }

    public void setProperties(KeyValueProperties p)
    {
        properties = p;
    }
    
    public void clear()
    {
        properties.clear();
        corporaPaths.clear();
    }
	
    public void print(PrintStream ps)
    {

    }

    public CorpusCollection getCopy()
    {
        return null;
    }

    @Override
    public AlignmentUnit getAlignmentUnit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAlignmentUnit(AlignmentUnit alignmentUnit) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getAlignedObject(String alignmentKey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List getAlignedObjects() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getFirstAlignedObject() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getAlignedObject(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getLastAlignedObject() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Alignable<SSFCorpusCollection, List, SSFCorpus> getContainerAlignable()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setContainerAlignable(Alignable<SSFCorpusCollection, List, SSFCorpus> containerAlignable)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Alignable<SSFCorpus, SSFCorpusCollection, SSFStory> insertContainedAlignableObject(Alignable<SSFCorpus, SSFCorpusCollection, SSFStory> containedAlignableObject, int atIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Alignable<SSFCorpus, SSFCorpusCollection, SSFStory> removeContainedAlignableObject(int atIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    int interchangeContainedAlignableObjects(C containedAlignableObject, int atIndex);
    
    public int interchangeContainedAlignableObjects(Alignable<SSFCorpus, SSFCorpusCollection, SSFStory> containedAlignableObject, int atIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
    }
}
