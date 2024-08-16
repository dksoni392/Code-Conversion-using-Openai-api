

    public List<SSFNode> getNodesForText(String ld)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

//        Pattern p = Pattern.compile(ld, Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES);
        Pattern p = Pattern.compile(ld);

        Matcher m = p.matcher(makeRawSentence());

        if (m.find()) {
            nodes.add(this);
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

    	    m = p.matcher(node.makeRawSentence());

            if (m.find())
            {
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChild(i)).getNodesForText(ld));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List replaceLabelForText(String ld, String replace)
    {
        List nodes = new ArrayList<SSFNode>();

        Pattern p = Pattern.compile(ld);
//        Pattern p = Pattern.compile(ld, Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES);

        Matcher m = p.matcher(makeRawSentence());

        if (m.find())
        {
            setName(replace);
            nodes.add(this);
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

    	    m = p.matcher(node.makeRawSentence());

            if (m.find())
            {
                node.setName(replace);
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChild(i)).replaceLabelForText(ld, replace));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> getNodesForFS(String fss)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node.getFeatureStructures().makeString().equalsIgnoreCase(fss))
            {
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChild(i)).getNodesForFS(fss));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> getNodesForAttrib(String attrib, boolean exactMatch)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        FeatureStructures fss = getFeatureStructures();

        if (fss != null && fss.countAltFSValues() > 0)
        {
            if (fss.getAltFSValue(0).searchAttribute(attrib, exactMatch) != null)
            {
                nodes.add(this);
            }
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            fss = node.getFeatureStructures();

            if (fss != null && fss.countAltFSValues() > 0)
            {
                if (fss.getAltFSValue(0).searchAttribute(attrib, exactMatch) != null)
                {
                    nodes.add(node);
                }
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) node).getNodesForAttrib(attrib, exactMatch));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public SSFNode getNodeForAttribVal(String attrib, String val, boolean exactMatch)
    {
        List nodes = getNodesForAttribVal(attrib, val, exactMatch);

        if (nodes == null || nodes.size() <= 0)
        {
            return null;
        }

        return (SSFNode) nodes.get(0);
    }

    public List<SSFNode> getNodesForAttribVal(String attrib, String val, boolean exactMatch)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        FeatureStructures fss = getFeatureStructures();

        if (fss != null && fss.countAltFSValues() > 0)
        {
            if (fss.getAltFSValue(0).searchAttributeValue(attrib, val, exactMatch) != null)
            {
                nodes.add(this);
            }
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            fss = node.getFeatureStructures();

            if (fss != null && fss.countAltFSValues() > 0)
            {
                if (fss.getAltFSValue(0).searchAttributeValue(attrib, val, exactMatch) != null)
                {
                    nodes.add(node);
                }
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) node).getNodesForAttribVal(attrib, val, exactMatch));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> replaceAttribValForText(String attrib, String val, String ntext, String attribReplace, String valReplace)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node.makeRawSentence().equalsIgnoreCase(ntext))
            {
                FeatureStructures fss = node.getFeatureStructures();

                if (fss != null && fss.countAltFSValues() > 0)
                {
                    FeatureStructure ifs = fss.getAltFSValue(0);

                    ifs.replaceAttributeValues(attrib, val, attribReplace, valReplace);
                    nodes.add(node);
                } else
                {
                    fss = new FeatureStructuresImpl();
                    FeatureStructure ifs = new FeatureStructureImpl();

                    FeatureAttribute fa = new FeatureAttributeImpl();
                    fa.setName(attribReplace);

                    FeatureValue fv = new FeatureValueImpl();
                    fv.setValue(valReplace);

                    fss.addAltFSValue(ifs);
                }

            }

            if (node instanceof SSFPhrase)
            {
                nodes.addAll(((SSFPhrase) node).replaceAttribValForText(attrib, val, ntext, attribReplace, valReplace));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> replaceAttribValForLabel(String attrib, String val, String nlabel, String attribReplace, String valReplace, boolean createAttrib)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node.getName().equalsIgnoreCase(nlabel))
            {
                FeatureStructures fss = node.getFeatureStructures();

                if (fss != null && fss.countAltFSValues() > 0)
                {
                    FeatureStructure ifs = fss.getAltFSValue(0);

                    FeatureAttribute fa = ifs.getAttribute(attrib);

                    if(createAttrib && fa == null)
                    {
                        if(FeatureStructuresImpl.getFSProperties().isMandatory(attrib))
                        {
                            if(ifs.hasMandatoryAttribs() == false) {
                                ifs.addMandatoryAttributes();
                            }
                        }
                        else
                        {
                            fa = new FeatureAttributeImpl();
                            fa.setName(attribReplace);

                            FeatureValue fv = new FeatureValueImpl();
                            fv.setValue("");

                            fa.addAltValue(fv);

                            ifs.addAttribute(fa);
                        }
                    }

                    ifs.replaceAttributeValues(attrib, val, attribReplace, valReplace);
                    nodes.add(node);
                }
                else if(createAttrib)
                {
                    fss = new FeatureStructuresImpl();
                    FeatureStructure ifs = new FeatureStructureImpl();

                    FeatureAttribute fa = new FeatureAttributeImpl();
                    fa.setName(attribReplace);

                    FeatureValue fv = new FeatureValueImpl();
                    fv.setValue(valReplace);

                    fa.addAltValue(fv);

                    ifs.addAttribute(fa);

                    fss.addAltFSValue(ifs);

                    node.setFeatureStructures(fss);
                    nodes.add(node);
                }
            }

            if (node instanceof SSFPhrase)
            {
                nodes.addAll(((SSFPhrase) node).replaceAttribValForLabel(attrib, val, nlabel, attribReplace, valReplace, createAttrib));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }