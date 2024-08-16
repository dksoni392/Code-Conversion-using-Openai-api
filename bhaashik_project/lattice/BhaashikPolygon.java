/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.lattice;

import bhaashik.tree.BhaashikEdges;

/**
 *
 * @author Anil Kumar Singh
 */
public class BhaashikPolygon {
    protected BhaashikEdges edges;

    public BhaashikPolygon()
    {

    }

    /**
     * @return the edges
     */
    public BhaashikEdges getEdges()
    {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(BhaashikEdges edges)
    {
        this.edges = edges;
    }
}
