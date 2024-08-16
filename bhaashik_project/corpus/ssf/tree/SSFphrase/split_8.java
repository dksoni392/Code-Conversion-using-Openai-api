

    public SSFPhrase convertToGDepNode(LinkedHashMap cfgToMMTreeMapping)
    {
        return convertToGDepNode(cfgToMMTreeMapping, true);
    }

    public SSFPhrase convertToGDepNode(LinkedHashMap cfgToMMTreeMapping, boolean collapse)
    {
        SSFProperties ssfp = getSSFProperties();
        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        if (getName().equals(rootName) || getParent() == null)
        {
            reallocateNames(null, null);
        }

        SSFPhrase mmRoot = null;

        try
        {
            mmRoot = (SSFPhrase) getCopy();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if (cfgToMMTreeMapping != null)
        {
            getMapping(this, mmRoot, cfgToMMTreeMapping);
        }

//        mmRoot.removeNonChunkPhrases();

        String depAttribs[] = FSProperties.getDependencyTreeAttributes();

//	Vector drelNodes = mmRoot.getNodesForAttrib("drel");
        List<SSFNode> drelNodes = mmRoot.getNodesForOneOfAttribs(depAttribs, true);
        List<SSFNode> namedNodesVec = mmRoot.getNodesForAttrib("name", true);

        if (drelNodes.size() <= 0 || namedNodesVec.size() <= 0)
        {
            return null;
        }

        LinkedHashMap<String, SSFNode> namedNodes = new LinkedHashMap<String, SSFNode>();

        int count = namedNodesVec.size();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) namedNodesVec.get(i);

            if (node instanceof SSFPhrase)
            {
                String nm = (String) node.getAttributeValue("name");
                namedNodes.put(nm, node);

//            node.collapseLexicalItems();
            }
        }

        count = drelNodes.size();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) drelNodes.get(i);

            if (node.isLeafNode())
            {
//                System.out.println(GlobalProperties.getIntlString("Wrong_input_node:_Only_chunks_can_be_part_of_the_dependency_tree"));
//                return null;
            } else
            {
                // It is a chunk, so check whether it has a drel attribute.
                // If it does, than find the chunk to which it is related and make that its parent.

//		String drel = (String) node.getFeatureStructures().getAltFSValue(0).getAttribute("drel").getAltValue(0).getValue();
                String drel = (String) node.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(depAttribs).getAltValue(0).getValue();

                String atval[] = drel.split("[:]");

//                if (atval.length != 2 || atval[1].equals("") == true)
//                {
//                    System.out.println("Wrong value of attribute: " + drel);
//                    return null;
//                }

                String rel = atval[0];
                String nm = "";

                if(atval.length == 1)
                {
                    nm = atval[0];
                    rel = "";
                }
                else {
                    nm = atval[1];
                }

                String chunk = ((SSFPhrase) node).makeRawSentence();

                SSFNode mmParent = (SSFNode) namedNodes.get(nm);

                if (mmParent == null)
                {
                    continue;
                }

                // New
//                mmParent.collapseLexicalItems();
                if(collapse) {
                    mmParent.collapseLexicalItemsDeep();
                }

                mmParent.add(node);

//                node.collapseLexicalItems();
                if(collapse) {
                    node.collapseLexicalItemsDeep();
                }
            }
        }

        if (mmRoot.getChildCount() == 0)
        {
            return null;
        }

//        if(mmRoot != null)
//            mmRoot.removeDSRedundantPhrases();
        if(mmRoot.getChildCount() == 1 && collapse) {
            mmRoot.collapseLexicalItemsDeep();
        }

        return mmRoot;
    }

    public SSFPhrase convertToLDepNode(LinkedHashMap cfgToMMTreeMapping)
    {
        return convertToLDepNode(cfgToMMTreeMapping, true);
    }

    public SSFPhrase convertToLDepNode(LinkedHashMap cfgToDepTreeMapping, boolean collapse)
    {
        SSFProperties ssfp = getSSFProperties();
        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        if (getName().equals(rootName) || getParent() == null)
        {
            reallocateNames(null, null);
        }

        SSFPhrase mmRoot = null;

        try
        {
            mmRoot = (SSFPhrase) getCopy();

            mmRoot.flattenChunks();

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if (cfgToDepTreeMapping != null)
        {
            getMapping(this, mmRoot, cfgToDepTreeMapping);
        }

//        mmRoot.removeNonChunkPhrases();

        String depAttribs[] = FSProperties.getDependencyTreeAttributes();

//	Vector drelNodes = mmRoot.getNodesForAttrib("drel");
        List<SSFNode> drelNodes = mmRoot.getNodesForOneOfAttribs(depAttribs, true);
        List<SSFNode> namedNodesVec = mmRoot.getNodesForAttrib("name", true);

        if (drelNodes.size() <= 0 || namedNodesVec.size() <= 0)
        {
            return mmRoot;
        }

        LinkedHashMap<String, SSFNode> namedNodes = new LinkedHashMap();

        int count = namedNodesVec.size();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) namedNodesVec.get(i);

//            if (node instanceof SSFPhrase)
//            if (node instanceof SSFLexItem)
//            {
                String nm = (String) node.getAttributeValue("name");
                namedNodes.put(nm, node);

//            node.collapseLexicalItems();
//            }
        }

        count = drelNodes.size();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) drelNodes.get(i);

            if (node instanceof SSFPhrase && node.getLexData().equals(""))
            {
//                node.removeLayer();
            }
            else
            {
                // It is a chunk, so check whether it has a drel attribute.
                // If it does, than find the chunk to which it is related and make that its parent.

                SSFNode namedNode = namedNodes.get(node.getAttributeValue("name"));

//		String drel = (String) node.getFeatureStructures().getAltFSValue(0).getAttribute("drel").getAltValue(0).getValue();
                String drel = (String) namedNode.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(depAttribs).getAltValue(0).getValue();

                String atval[] = drel.split("[:]");

//                if (atval.length != 2 || atval[1].equals("") == true)
//                {
//                    System.out.println("Wrong value of attribute: " + drel);
//                    return null;
//                }

                String rel = atval[0];
                String nm = "";

                if(atval.length == 1)
                {
                    nm = atval[0];
                    rel = "";
                }
                else {
                    nm = atval[1];
                }

//                String chunk = ((SSFPhrase) node).makeRawSentence();

                SSFNode mmParent = (SSFNode) namedNodes.get(nm);

                SSFNode mmGrandParent = (SSFNode) mmParent.getParent();

//                if (mmParent == null || mmGrandParent == null)
                if (mmParent == null)
                {
                    continue;
                }

                // New
//                mmParent.collapseLexicalItems();
//                if(collapse)
//                    mmParent.collapseLexicalItemsDeep();

                if(mmParent instanceof SSFLexItem)
                {
                    SSFPhrase mmParentPhrase = new SSFPhrase(mmParent.getId(),
                            mmParent.getLexData(), mmParent.getName(), mmParent.getFeatureStructures());

                    int mmParentIndex = mmGrandParent.getIndex((SSFNode) mmParent);

                    mmGrandParent.remove((SSFNode) mmParent);

                    mmGrandParent.insert(mmParentPhrase, mmParentIndex);

                    LinkedHashMap d2cTreeMap = (LinkedHashMap) UtilityFunctions.getReverseMap(cfgToDepTreeMapping);

                    SSFNode cfgNode = (SSFNode) d2cTreeMap.get(mmParent);

                    cfgToDepTreeMapping.put(cfgNode, mmParentPhrase);

                    namedNodes.put(mmParent.getAttributeValue("name"), mmParentPhrase);

    //                int ind = drelNodes.indexOf(mmParent);
    //
    //                if(ind != -1)
    //                    drelNodes.setElementAt(mmParentPhrase, ind);

                    mmParentPhrase.add(namedNode);
                }
                else {
                    mmParent.add(namedNode);
                }

//                node.collapseLexicalItems();
//                if(collapse)
//                    node.collapseLexicalItemsDeep();
            }
        }

        if (mmRoot.getChildCount() == 0)
        {
            return null;
        }

//        if(mmRoot != null)
//            mmRoot.removeDSRedundantPhrases();
//        if(mmRoot.getChildCount() == 1 && collapse)
//            mmRoot.collapseLexicalItemsDeep();

        return mmRoot;
    }