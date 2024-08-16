

    public static void init()
    {
        FSProperties fsp = new FSProperties();
        SSFProperties ssfp = new SSFProperties();

//        SSFText text = null;

        try {
            fsp.read(GlobalProperties.resolveRelativePath("props/fs-mandatory-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/fs-other-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/fs-props.txt"),
                    GlobalProperties.resolveRelativePath("props/ps-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/dep-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/sem-attribs.txt"),
                    GlobalProperties.getIntlString("UTF-8")); //throws java.io.FileNotFoundException;

            ssfp.read(GlobalProperties.resolveRelativePath("props/ssf-props.txt"), GlobalProperties.getIntlString("UTF-8")); //throws java.io.FileNotFoundException;
            FeatureStructuresImpl.setFSProperties(fsp);
            SSFNode.setSSFProperties(ssfp);
        }  catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public DOMElement getDOMElement() {
        XMLProperties xmlProperties = SSFNode.getXMLProperties();
        
        DOMElement domElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("nodeTag"));

        DOMElement idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("nodeIDTag"));
        idomElement.setText(id);
        domElement.add(idomElement);

        idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("nameTag"));
        idomElement.setText(getName());
        domElement.add(idomElement);

        idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("lexDataTag"));
        idomElement.setText(getLexData());
        domElement.add(idomElement);

        if(fs != null)
        {
            idomElement = ((BhaashikDOMElement) fs).getDOMElement();
            domElement.add(idomElement);
        }

        if(getComment() != null)
        {
            idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("commentTag"));
            idomElement.setText(getComment());
            domElement.add(idomElement);
        }

        return domElement;
    }

    @Override
    public DOMElement getTypeCraftDOMElement() {
        XMLProperties xmlProperties = SSFNode.getXMLProperties();

        DOMElement domElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("tcMorphemeTag"));

        DOMElement idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("tcGlossTag"));
        idomElement.setText(getLexData());
        domElement.add(idomElement);

        if(fs != null)
        {
            idomElement = ((BhaashikDOMElement) fs).getDOMElement();
            domElement.add(idomElement);
        }

        if(getComment() != null)
        {
            idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("commentTag"));
            idomElement.setText(getComment());
            domElement.add(idomElement);
        }

        return domElement;
    }

    @Override
    public DOMElement getGATEDOMElement() {
        XMLProperties xmlProperties = SSFNode.getXMLProperties();

        DOMElement domElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("tcMorphemeTag"));

        DOMElement idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("tcGlossTag"));
        idomElement.setText(getLexData());
        domElement.add(idomElement);

        if(fs != null)
        {
            idomElement = ((BhaashikDOMElement) fs).getDOMElement();
            domElement.add(idomElement);
        }

        if(getComment() != null)
        {
            idomElement = new DOMElement(xmlProperties.getProperties().getPropertyValue("commentTag"));
            idomElement.setText(getComment());
            domElement.add(idomElement);
        }

        return domElement;
    }

    @Override
    public String getXML() {
        String xmlString = "";
        org.dom4j.dom.DOMElement element = getDOMElement();
        xmlString = element.asXML();

        return "\n" + xmlString + "\n";
    }

    @Override
    public String getTypeCraftXML() {
        String xmlString = "";
        org.dom4j.dom.DOMElement element = getTypeCraftDOMElement();
        xmlString = element.asXML();

        return "\n" + xmlString + "\n";
    }

    @Override
    public String getGATEXML() {
        String xmlString = "";
        org.dom4j.dom.DOMElement element = getGATEDOMElement();
        xmlString = element.asXML();

        return "\n" + xmlString + "\n";
    }

    @Override
    public void readXML(Element domElement) {
        XMLProperties xmlProperties = SSFNode.getXMLProperties();

        Node node = domElement.getFirstChild();

        while(node != null)
        {
            if(node instanceof Element)
            {
                Element element = (Element) node;

                if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("nodeIDTag")))
                {
                    id = element.getTextContent();
                    id = id.trim();
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("lexDataTag")))
                {
                    String lexdata = element.getTextContent();
                    lexdata = lexdata.trim();
                    setLexData(lexdata);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("nameTag")))
                {
                    String name = element.getTextContent();
                    name = name.trim();
                    setName(name);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("fssTag")))
                {
                    fs = new FeatureStructuresImpl();
                    ((BhaashikDOMElement) fs).readXML(element);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("commentTag")))
                {
                    String comment = element.getTextContent();
                    comment = comment.trim();
                    setComment(comment);
                }
            }

            node = node.getNextSibling();
        }
    }

    @Override
    public void readTypeCraftXML(Element domElement) {
        XMLProperties xmlProperties = SSFNode.getXMLProperties();

        NamedNodeMap domAttribs = domElement.getAttributes();

        int acount = domAttribs.getLength();

        for (int i = 0; i < acount; i++)
        {
            Node node = domAttribs.item(i);
            String name = node.getNodeName();
            String value = node.getNodeValue();

            if(name != null)
            {
                if(name.equals("text"))
                {
                    setLexData(value);
                }
                else if(value != null) {
                    setAttributeValue(name, value);
                }
                else {
                    setAttributeValue(name, "");
                }
            }
        }

        Node node = domElement.getFirstChild();

        while(node != null)
        {
            if(node instanceof Element)
            {
                Element element = (Element) node;

                if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("tcGlossTag")))
                {
                    String name = element.getTextContent();
                    name = name.trim();
                    setName(name);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("lexDataTag")))
                {
                    String lexdata = element.getTextContent();
                    lexdata = lexdata.trim();
                    setLexData(lexdata);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("nameTag")))
                {
                    String name = element.getTextContent();
                    name = name.trim();
                    setName(name);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("fssTag")))
                {
                    fs = new FeatureStructuresImpl();
                    ((BhaashikDOMElement) fs).readXML(element);
                }
                else if(element.getTagName().equals(xmlProperties.getProperties().getPropertyValue("commentTag")))
                {
                    String comment = element.getTextContent();
                    comment = comment.trim();
                    setComment(comment);
                }
            }

            String name = getName();
            
            if(name == null || name.equals("")) {
                setName("NO_GLOSS");
            }

            node = node.getNextSibling();
        }
    }