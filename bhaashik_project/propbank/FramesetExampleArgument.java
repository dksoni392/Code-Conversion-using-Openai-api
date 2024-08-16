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
public class FramesetExampleArgument extends FramesetAtom implements BhaashikDOMElement {

    public FramesetExampleArgument()
    {
        super();

        leafNode = true;

        attributes.addAttribute("\n", "");
        attributes.addAttribute(GlobalProperties.getIntlString("f"), "");
    }

    @Override
    public org.dom4j.dom.DOMElement getDOMElement()
    {
        DOMElement domElement = super.getDOMElement();

        domElement.setName(GlobalProperties.getIntlString("arg"));

        return domElement;
    }
}
