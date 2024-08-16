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
public class FramesetExampleInflections extends FramesetAtom implements BhaashikDOMElement {

    public FramesetExampleInflections()
    {
        super();

        leafNode = true;

        attributes.addAttribute(GlobalProperties.getIntlString("person"), GlobalProperties.getIntlString("ns"));
        attributes.addAttribute(GlobalProperties.getIntlString("tense"), GlobalProperties.getIntlString("ns"));
        attributes.addAttribute(GlobalProperties.getIntlString("aspect"), GlobalProperties.getIntlString("ns"));
        attributes.addAttribute(GlobalProperties.getIntlString("voice"), GlobalProperties.getIntlString("ns"));
        attributes.addAttribute(GlobalProperties.getIntlString("form"), GlobalProperties.getIntlString("ns"));
    }

    @Override
    public org.dom4j.dom.DOMElement getDOMElement()
    {
        DOMElement domElement = super.getDOMElement();

        domElement.setName(GlobalProperties.getIntlString("inflection"));

        return domElement;
    }
}
