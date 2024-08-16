/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.context;

import java.io.IOException;
import java.io.PrintStream;

import bhaashik.context.impl.ContextElement;

/**
 *
 * @author anil
 */
public class ContextElementImpl<E> implements ContextElement<E>, Comparable {

    protected E contextElement;
    protected long freq;

    public ContextElementImpl() {
    }

    public E getContextElement() {
        return contextElement;
    }

    public void setContextElement(E contextElement) {
        this.contextElement = contextElement;
    }


    public long getFreq() {
        return freq;
    }

    public void setFreq(long freq) {
        this.freq = freq;
    }

    public void print(PrintStream ps) throws IOException, Exception
    {
        ps.println("\t" + contextElement.toString() + "\t" + freq);
    }

    @Override
    public int compareTo(Object o)
    {
        return (Long.valueOf(freq)).compareTo(Long.valueOf(((ContextElementImpl) o).freq));
    }
}
