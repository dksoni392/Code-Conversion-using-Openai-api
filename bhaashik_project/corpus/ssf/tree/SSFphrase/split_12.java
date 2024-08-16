

    public LinkedHashMap<String, Integer> getAttributeValuePairFreq()
    {
        LinkedHashMap<String, Integer> attribs = new LinkedHashMap();

        List<SSFNode> allChildren = getAllChildren();

        int lcount = allChildren.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode childNode = allChildren.get(j);

            List<String> attribVals = childNode.getAttributeValuePairs();

            if(attribVals == null) {
                continue;
            }

            int acount = attribVals.size();

            for (int i = 0; i < acount; i++)
            {
                String attrib = attribVals.get(i);

                if(attribs.get(attrib) == null) {
                    attribs.put(attrib, 1);
                }
                else {
                    attribs.put(attrib, attribs.get(attrib) + 1);
                }
            }

            if(childNode instanceof SSFPhrase)
            {
                attribs.putAll(((SSFPhrase) childNode).getAttributeValuePairFreq());
            }
        }

        return attribs;
    }

    public LinkedHashMap<String, Integer> getUnchunkedWordFreq()
    {
        LinkedHashMap<String, Integer> tags = new LinkedHashMap();

        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int lcount = leaves.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode leafNode = (SSFNode) leaves.get(j);

            if(leafNode instanceof SSFLexItem)
            {
                String lexData = leafNode.getLexData();

                if(leafNode.getParent() != null && leafNode.getParent().getParent() == null)
                {
                    if(tags.get(lexData) == null) {
                        tags.put(lexData, 1);
                    }
                    else {
                        tags.put(lexData, tags.get(lexData) + 1);
                    }
                }
            }
        }

        return tags;
    }

    public void reallocatePositions(String positionAttribName, String nullWordString)
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int lcount = leaves.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode leafNode = (SSFNode) leaves.get(j);

//            if(leafNode instanceof SSFLexItem && leafNode.getLexData().equalsIgnoreCase(nullWordString))
//            {
                leafNode.setAttributeValue(positionAttribName, "" + (j + 1) * 10);
//            }
        }
    }

    public void convertEncoding(BhaashikEncodingConverter encodingConverter, String nullWordString)
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int lcount = leaves.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode leafNode = (SSFNode) leaves.get(j);

            if(leafNode instanceof SSFLexItem && leafNode.getLexData().equalsIgnoreCase(nullWordString) == false)
            {
                String convertedLexData = encodingConverter.convert(leafNode.getLexData());
                leafNode.setLexData(convertedLexData);

                String convertedLex = encodingConverter.convert(leafNode.getAttributeValue("lex"));

                if(convertedLex != null && convertedLex.equals("") == false) {
                    leafNode.setAttributeValue("lex", convertedLex);
                }

                String convertedTAM = encodingConverter.convert(leafNode.getAttributeValue("tam"));

                if(convertedTAM != null && convertedTAM.equals("") == false) {
                    leafNode.setAttributeValue("tam", convertedTAM);
                }

                String convertedVib = encodingConverter.convert(leafNode.getAttributeValue("vib"));

                if(convertedVib != null && convertedVib.equals("") == false) {
                    leafNode.setAttributeValue("vib", convertedVib);
                }

                String convertedName = encodingConverter.convert(leafNode.getAttributeValue("name"));

                if(convertedName != null && convertedName.equals("") == false) {
                    leafNode.setAttributeValue("name", convertedName);
                }
            }
        }
    }

    @Override
    public DOMElement getDOMElement() {
        DOMElement domElement = super.getDOMElement();

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);

            DOMElement idomElement = child.getDOMElement();

            domElement.add(idomElement);
        }

        return domElement;
    }

    @Override
    public DOMElement getTypeCraftDOMElement() {
        DOMElement domElement = super.getTypeCraftDOMElement();

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);

            DOMElement idomElement = child.getTypeCraftDOMElement();

            domElement.add(idomElement);
        }

        return domElement;
    }

    @Override
    public DOMElement getGATEDOMElement() {
        DOMElement domElement = super.getGATEDOMElement();

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);

            DOMElement idomElement = child.getGATEDOMElement();

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
        XMLProperties xmlProperties = getXMLProperties();

        super.readXML(domElement);

        Node node = domElement.getFirstChild();

        while(node != null)
        {
            if(node instanceof Element)
            {
                Element element = (Element) node;

                String tag = xmlProperties.getProperties().getPropertyValue("nodeTag");

                if(element.getTagName().equals(tag))
                {
                    SSFNode child = null;

                    if(XMLUtils.hasChileNode(element, tag))
                    {
                        try {
                            child = new SSFPhrase("0", "", "NP", "");

                            child.readXML(element);

                            addChild(child);
                        } catch (Exception ex) {
                            Logger.getLogger(SSFPhrase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    {
                        try {
                            child = new SSFLexItem("0", "", "NN", "");

                            child.readXML(element);

                            addChild(child);
                        } catch (Exception ex) {
                            Logger.getLogger(SSFLexItem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            node = node.getNextSibling();
        }
    }

    @Override
    public void readTypeCraftXML(Element domElement) {
        XMLProperties xmlProperties = getXMLProperties();

        super.readTypeCraftXML(domElement);

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

                String tag = xmlProperties.getProperties().getPropertyValue("tcMorphemeTag");

                if(element.getTagName().equals(tag))
                {
                    SSFNode child;

                    try {
                        child = new SSFLexItem("0", "", "NN", "");

                        child.readTypeCraftXML(element);

                        addChild(child);
                    } catch (Exception ex) {
                        Logger.getLogger(SSFLexItem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                tag = xmlProperties.getProperties().getPropertyValue("tcPOSTag");

                if(element.getTagName().equals(tag))
                {
                    setName(element.getTextContent().trim());
                }
            }

            node = node.getNextSibling();
        }
    }