/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.parallel;

import bhaashik.util.Pair;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author anil
 */
// T is the type of the objects of this class.
// P is the class of the object containing the objests of this class
// C is the class of the objects contained in the objests of this class
public interface Alignable<T, P, C> extends Serializable {

    // Operations over the object containing the objects of this class
    P getContainerAlignable();
    void setContainerAlignable(P containerAlignable);

    // Operations over the objects of this class
    AlignmentUnit<T> getAlignmentUnit();
    void setAlignmentUnit(AlignmentUnit<T> alignmentUnit);

    T getAlignedObject(String alignmentKey);

    List<T> getAlignedObjects();

    T getFirstAlignedObject();
    T getAlignedObject(int i);
    T getLastAlignedObject();
    
    // Operations for the objects contained by the objects of this class
    C insertContainedAlignableObject(C containedAlignableObject, int atIndex);
    C removeContainedAlignableObject(int atIndex);
    int interchangeContainedAlignableObjects(C containedAlignableObject, int atIndex);
}
