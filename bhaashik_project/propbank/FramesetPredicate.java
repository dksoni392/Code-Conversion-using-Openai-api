/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.propbank;

import java.util.Vector;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.impl.FeatureStructureImpl;
import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author anil
 */
public class FramesetPredicate extends FramesetAtom implements BhaashikDOMElement {

    protected String note = "";

    protected Vector<FramesetRoleset> rolesets;

    public FramesetPredicate()
    {
        attributes = new FeatureStructureImpl();
        rolesets = new Vector<FramesetRoleset>();
    }

    /**
     * @return the note
     */
    public String getNote()
    {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note)
    {
        this.note = note;
    }

    public int countRolesets()
    {
        return rolesets.size();
    }

    public FramesetRoleset getRoleset(int num)
    {
        return (FramesetRoleset) rolesets.get(num);
    }

    public int addRoleset(FramesetRoleset r)
    {
        rolesets.add(r);
        return rolesets.size();
    }

    public FramesetRoleset removeRoleset(int num)
    {
        return (FramesetRoleset) rolesets.remove(num);
    }

    public void removeRoleset(FramesetRoleset r)
    {
        int ind = rolesets.indexOf(r);

        if(ind != -1)
            rolesets.remove(ind);
    }

    @Override
    public org.dom4j.dom.DOMElement getDOMElement()
    {
        DOMElement domElement = super.getDOMElement();

        domElement.setName(GlobalProperties.getIntlString("predicate"));

        DOMElement idomElement = new DOMElement(GlobalProperties.getIntlString("note"));

        idomElement.setText(note);

        domElement.add(idomElement);

        int count = countRolesets();

        for (int i = 0; i < count; i++)
        {
            FramesetRoleset child = getRoleset(i);

            idomElement = child.getDOMElement();

            domElement.add(idomElement);
        }

        return domElement;
    }

    @Override
    public void readXML(Element domElement)
    {
        super.readXML(domElement);

        Node node = domElement.getFirstChild();

        note = "";

        while(node != null)
        {
            if(node instanceof Element)
            {
                Element element = (Element) node;

                if(element.getTagName().equals(GlobalProperties.getIntlString("note")))
                {
                    String n = element.getTextContent();

                    note += n.trim() + "\n";
                }
                else if(element.getTagName().equals(GlobalProperties.getIntlString("roleset")))
                {
                    FramesetRoleset framesetRoleset = new FramesetRoleset();
                    framesetRoleset.readXML(element);
                    addRoleset(framesetRoleset);
                }
            }

            node = node.getNextSibling();
        }

        note = note.trim();
    }
}
