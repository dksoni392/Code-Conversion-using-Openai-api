

    @Override
    public void readGATEXML(Element domElement) {
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

    @Override
    public void printXML(PrintStream ps) {
        ps.print(getXML());
    }

    @Override
    public void printTypeCraftXML(PrintStream ps) {
        ps.print(getTypeCraftXML());
    }

    @Override
    public void printGATEXML(PrintStream ps) {
        ps.print(getGATEXML());
    }

    public boolean hasLexicalDependencies(String relAttrib)
    {
        String relVal = getAttributeValue(relAttrib);

        if(relVal != null && relVal.equals("") == false)
        {
            String parts[] = relVal.split(":");

            if(parts.length == 2) {
                return true;
            }
        }

        int ccount = countChildren();

        for (int i = 0; i < ccount; i++) {
            SSFNode cnode = (SSFNode) getChildAt(i);

            if(cnode.hasLexicalDependencies(relAttrib) == true) {
                return true;
            }
        }

        return false;
    }

    public boolean hasContituentDependencies(String relAttrib)
    {
        if(this instanceof SSFLexItem) {
            return false;
        }

        String relVal = getAttributeValue(relAttrib);

        if(relVal == null || relVal.equals("")) {
            return false;
        }

        String parts[] = relVal.split(":");

        if(parts.length == 2) {
            return true;
        }

        int ccount = countChildren();

        for (int i = 0; i < ccount; i++) {
            SSFNode cnode = (SSFNode) getChildAt(i);

            if(cnode.hasContituentDependencies(relAttrib) == true) {
                return true;
            }
        }

        return false;
    }

    public List<SSFNode> getReferringNodes(SSFNode referredNode, int mode)
    {
        String attribs[] = null;

        if (mode == PHRASE_STRUCTURE_MODE)
        {
            attribs = FSProperties.getPSAttributes();
        } else if (mode == DEPENDENCY_RELATIONS_MODE)
        {
            attribs = FSProperties.getDependencyAttributes();
        }

        String nm = referredNode.getAttributeValue("name");

        List<SSFNode> refNodes = getNodesForOneOfAttribs(attribs, true);
        List<SSFNode> referringNodes = new ArrayList<SSFNode>();

        int count = refNodes.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode refNode = (SSFNode) refNodes.get(i);

            String refAtVal[] = refNode.getOneOfAttributeValues(attribs);

            if (refAtVal == null)
            {
                continue;
            }

            String refVal = refAtVal[1];

            if (refVal == null)
            {
                continue;
            }

            String parts[] = refVal.split(":");

//            if (parts.length == 1)
//            {
//                continue;
//            }

            if ((parts.length == 2 && parts[1].equals(nm)) || (parts.length == 1 && parts[0].equals(nm)))
            {
                referringNodes.add(refNode);
            }
        }

        return referringNodes;
    }

    // For dependency
    public void setReferredName(String name, int mode)
    {
        String attribs[] = null;

        if (mode == PHRASE_STRUCTURE_MODE)
        {
            attribs = FSProperties.getPSAttributes();
        } else if (mode == DEPENDENCY_RELATIONS_MODE)
        {
            attribs = FSProperties.getDependencyAttributes();
        }

        String refAtVal[] = getOneOfAttributeValues(attribs);

        if(refAtVal == null) {
            return;
        }

        String refVal = refAtVal[1];

        String parts[] = refVal.split(":");

        if(parts.length == 1) {
            setAttributeValue(refAtVal[0], name);
        }
        else {
            setAttributeValue(refAtVal[0], parts[0] + ":" + name);
        }
    }

    public SSFNode getReferredNode(String attribName)
    {
        String attribVal = getAttributeValue(attribName);

        if(attribVal == null || !attribVal.contains(":")) {
            return null;
        }

        String attribValParts[] = attribVal.split(":");

        String referredName = attribValParts[1];

        return ((SSFPhrase) getRoot()).getNodeForAttribVal("name", referredName, true);
    }

    public List<SSFNode> getNodesForOneOfAttribs(String attribs[], boolean exactMatch)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        FeatureStructures fss = getFeatureStructures();

        if (fss != null && fss.countAltFSValues() > 0)
        {
            if (fss.getAltFSValue(0).searchOneOfAttributes(attribs, exactMatch) != null)
            {
                nodes.add(this);
            }
        }

        int count = getChildCount();
        
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) getChildAt(i);

            fss = node.getFeatureStructures();

            if (fss != null && fss.countAltFSValues() > 0)
            {
                if (fss.getAltFSValue(0).searchOneOfAttributes(attribs, exactMatch) != null)
                {
                    nodes.add(node);
                }
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) node).getNodesForOneOfAttribs(attribs, exactMatch));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    @Override
    public void fillTreeEdges(BhaashikJTable jtbl, int mode)
    {
//	if(requiredColumnCount == -1 || rowIndex == -1 || columnIndex == -1)
//	    return;

        String atrrNames[] = null;

        switch (mode)
        {
            case PHRASE_STRUCTURE_MODE:
                if(getParent() == null) {
                    jtbl.setCellObject(getRowIndex(), getColumnIndex(), this);
                }

                atrrNames = FSProperties.getPSTreeAttributes();
                int chcount = getChildCount();

                for (int i = 0; i < chcount; i++)
                {
                    SSFNode child = (SSFNode) getChildAt(i);

                    // Skipping the root SSF node
                    if (rowIndex >= 0)
                    {
                        BhaashikEdge edge = new BhaashikEdge(this, rowIndex, columnIndex, child, child.getRowIndex(), child.getColumnIndex());

                        edge.isTriangle(child.isTriangle);

                        if (child.getFeatureStructures() != null && child.getFeatureStructures().countAltFSValues() > 0 && child.getFeatureStructures().getAltFSValue(0) != null)
                        {
                            FeatureAttribute fa = child.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(atrrNames);

                            if (fa != null)
                            {
                                String prel = (String) child.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(atrrNames).getAltValue(0).getValue();
                                prel = prel.split("[:]")[0];
                                edge.setLabel(prel.toUpperCase());
                                Color color = UtilityFunctions.getColor(FSProperties.getPSTreeAttributeProperties(fa.getName())[0]);
                                edge.setColor(color);
                                Stroke stroke = UtilityFunctions.getStroke(FSProperties.getPSTreeAttributeProperties(fa.getName())[1]);
                                edge.setStroke(stroke);
                            }
                        }

                        jtbl.addEdge(edge);
                    }

                    jtbl.setCellObject(child.getRowIndex(), child.getColumnIndex(), child);
                    child.fillTreeEdges(jtbl, mode);
                }

                break;
            case DEPENDENCY_RELATIONS_MODE:
                atrrNames = FSProperties.getDependencyTreeAttributes();

                if (isLeaf() == false || jtbl.allowsLeafDependencies())
                {
                    chcount = getChildCount();

                    for (int i = 0; i < chcount; i++)
                    {
                        SSFNode child = (SSFNode) getChildAt(i);

                        // Skipping the root SSF node
                        if (rowIndex >= 0)
                        {
                            BhaashikEdge edge = new BhaashikEdge(this, rowIndex, columnIndex, child, child.getRowIndex(), child.getColumnIndex());

                            if (child.getFeatureStructures() != null && child.getFeatureStructures().countAltFSValues() > 0 && child.getFeatureStructures().getAltFSValue(0) != null)
                            {
                                FeatureAttribute fa = child.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(atrrNames);

                                if (fa != null)
                                {
                                    String drel = (String) child.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(atrrNames).getAltValue(0).getValue();
                                    drel = drel.split("[:]")[0];
                                    edge.setLabel(drel.toUpperCase());
                                    Color color = UtilityFunctions.getColor(FSProperties.getDependencyTreeAttributeProperties(fa.getName())[0]);
                                    edge.setColor(color);
                                    Stroke stroke = UtilityFunctions.getStroke(FSProperties.getDependencyTreeAttributeProperties(fa.getName())[1]);
                                    edge.setStroke(stroke);
                                }
                            }

                            jtbl.addEdge(edge);
                        }

                        jtbl.setCellObject(child.getRowIndex(), child.getColumnIndex(), child);
                        child.fillTreeEdges(jtbl, mode);
                    }
                }

                break;
            default:
                super.fillTreeEdges(jtbl, mode);
        }

    }