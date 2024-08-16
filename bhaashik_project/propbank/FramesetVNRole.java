/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.propbank;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.impl.FeatureStructureImpl;
import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMElement;

/**
 *
 * @author anil
 */
public class FramesetVNRole extends FramesetAtom implements BhaashikDOMElement {

    public FramesetVNRole()
    {
        leafNode = true;
        
        attributes = new FeatureStructureImpl();

        attributes.addAttribute(GlobalProperties.getIntlString("vncls"), "");
        attributes.addAttribute(GlobalProperties.getIntlString("vntheta"), "");
    }

    @Override
    public org.dom4j.dom.DOMElement getDOMElement()
    {
        DOMElement domElement = super.getDOMElement();

        domElement.setName(GlobalProperties.getIntlString("vnrole"));

        return domElement;
    }
}
