/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.propbank;

import bhaashik.GlobalProperties;
import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMElement;

/**
 *
 * @author anil
 */
public class FramesetExampleRelation extends FramesetAtom implements BhaashikDOMElement {

    public FramesetExampleRelation()
    {
        super();

        leafNode = true;

        attributes.addAttribute(GlobalProperties.getIntlString("f"), "");
    }

    @Override
    public org.dom4j.dom.DOMElement getDOMElement()
    {
        DOMElement domElement = super.getDOMElement();

        domElement.setName(GlobalProperties.getIntlString("rel"));

        return domElement;
    }
}
