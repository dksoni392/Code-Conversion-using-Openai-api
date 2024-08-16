

    public SSFPhrase convertToPSNode(LinkedHashMap cfgToPSTreeMapping)
    {
        SSFProperties ssfp = getSSFProperties();
        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        if (getName().equals(rootName) || getParent() == null)
        {
            reallocateNames(null, null);
        }


        SSFPhrase psRoot = null;

        try
        {
            psRoot = (SSFPhrase) getCopy();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if (cfgToPSTreeMapping != null)
        {
            getMapping(this, psRoot, cfgToPSTreeMapping);
        }

        String psAttribs[] = FSProperties.getPSTreeAttributes();

//	Vector drelNodes = mmRoot.getNodesForAttrib("drel");
        List<SSFNode> psrelNodes = psRoot.getNodesForOneOfAttribs(psAttribs, true);
        List<SSFNode> namedNodesVec = psRoot.getNodesForAttrib("name", true);

        if (psrelNodes.size() <= 0 || namedNodesVec.size() <= 0)
        {
            return null;
        }

        LinkedHashMap<String, SSFNode> namedNodes = new LinkedHashMap<String, SSFNode>();

        int count = namedNodesVec.size();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) namedNodesVec.get(i);

//            if (node instanceof SSFPhrase)
//            {
                String nm = (String) node.getFeatureStructures().getAltFSValue(0).getAttribute("name").getAltValue(0).getValue();
                namedNodes.put(nm, node);

//            node.collapseLexicalItems();
//            }
        }

        count = psrelNodes.size();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) psrelNodes.get(i);

//            if (node.isLeafNode())
//            {
//                System.out.println("Wrong input node: Only chunks can be part of the MM tree");
//                return null;
//            } else
//            {
                // It is a chunk, so check whether it has a drel attribute.
                // If it does, than find the chunk to which it is related and make that its parent.

//		String drel = (String) node.getFeatureStructures().getAltFSValue(0).getAttribute("drel").getAltValue(0).getValue();
                String psrel = (String) node.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(psAttribs).getAltValue(0).getValue();

                String atval[] = psrel.split("[:]");

                if (atval.length != 2 || atval[1].equals("") == true)
                {
                    System.out.println(GlobalProperties.getIntlString("Wrong_value_of_attribute:_") + psrel);
                    return null;
                }

                String rel = atval[0];
                String nm = atval[1];

//                String chunk = ((SSFPhrase) node).makeRawSentence();

                SSFNode psParent = (SSFNode) namedNodes.get(nm);

                if (psParent == null)
                {
                    continue;
                }

                // New
//                psParent.collapseLexicalItems();

                psParent.add(node);

//                node.collapseLexicalItems();
//            }
        }

        if (psRoot.getChildCount() == 0)
        {
            return null;
        }

        return psRoot;
    }

    public SSFPhrase convertToPennDepNode()
    {
        SSFProperties ssfp = getSSFProperties();

        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        SSFPhrase rootCopy = null;
        SSFPhrase pdRoot = null;

        try
        {
            rootCopy = (SSFPhrase) getCopy();
        } catch (Exception ex)
        {
            System.out.println(GlobalProperties.getIntlString("Error_in_node_copying"));
            ex.printStackTrace();
        }

        List<SSFNode> pdNodes = rootCopy.getNodesForAttrib("penndep", true);
        List<SSFNode> namedNodesVec = rootCopy.getNodesForAttrib("name", true);

        if (pdNodes.size() <= 0 || namedNodesVec.size() <= 0)
        {
            return null;
        }

        LinkedHashMap<String, SSFNode> namedNodes = new LinkedHashMap<String, SSFNode>();
        LinkedHashMap<String, SSFNode> parentNodes = new LinkedHashMap<String, SSFNode>();
        LinkedHashMap<String, SSFNode> namedPhraseNodes = new LinkedHashMap<String, SSFNode>();

        int count = namedNodesVec.size();

        List<SSFNode> namedPhraseNodesVec = new ArrayList<SSFNode>(count);

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) namedNodesVec.get(i);

            String nm = (String) node.getFeatureStructures().getAltFSValue(0).getAttribute("name").getAltValue(0).getValue();
            namedNodes.put(nm, node);

            SSFPhrase phraseNode = null;

            try
            {
                phraseNode = new SSFPhrase(node.getId(), node.getLexData(), node.getName(), node.getFeatureStructures());
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }

            namedPhraseNodesVec.add(phraseNode);
            namedPhraseNodes.put(nm, phraseNode);
        }

        count = pdNodes.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) pdNodes.get(i);

            String penndep = (String) node.getFeatureStructures().getAltFSValue(0).getAttribute("penndep").getAltValue(0).getValue();

            String atval[] = penndep.split("[:]");

            if (atval.length != 2 || atval[1].equals("") == true)
            {
                System.out.println(GlobalProperties.getIntlString("Wrong_value_of_penndep_attribute:_") + penndep);
                return null;
            }

            String rel = atval[0];
            String nm = atval[1];

            SSFPhrase phraseNode = (SSFPhrase) namedPhraseNodesVec.get(i);

            SSFNode prnt = (SSFNode) namedNodes.get(nm);

            SSFPhrase phraseParent = (SSFPhrase) namedPhraseNodes.get(nm);

            if (rel.equalsIgnoreCase("ROOT"))
            {
//                phraseParent = new SSFPhrase("0", "", rootName, (FeatureStructures) null);
                pdRoot = phraseNode;
            } else
            {
                phraseParent.add(phraseNode);
            }
        }

        return pdRoot;
    }

    @Override
    public void collapseLexicalItems()
    {
        boolean hasOnlyLeaves = true;

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            if (getChild(i).getClass().equals(SSFPhrase.class))
            {
                hasOnlyLeaves = false;
                i = count;
            }
        }

        if (hasOnlyLeaves && count > 0)
        {
            String rawString = makeRawSentence();
            setLexData(rawString);
            removeAllChildren();
        }
    }

    @Override
    public void collapseLexicalItemsDeep()
    {
        int count = getChildCount();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node instanceof SSFPhrase)
            {
                if (((SSFPhrase) node).hasLexItemChild())
                {
                    String rawString = "";

                    int ccount = ((SSFPhrase) node).countChildren();

                    for (int j = 0; j < ccount; j++)
                    {
                        SSFNode cnode = ((SSFPhrase) node).getChild(j);

                        if (cnode instanceof SSFLexItem)
                        {
                            if (j == ccount - 1)
                            {
                                rawString += cnode.getLexData();
                            } else
                            {
                                rawString += cnode.getLexData() + " ";
                            }

                            ((SSFPhrase) node).removeChild(j--);
                            ccount--;
                        }
                    }

                    node.setLexData(rawString);
                }

                ((SSFPhrase) node).collapseLexicalItemsDeep();
            }
        }
    }

    public boolean isTaggingSame(SSFPhrase ch)
    {
        if (ch == null)
        {
            return false;
        }

        if (getDifferentPOSTags(ch) != null)
        {
            return false;
        }

        return true;
    }

    // Return indices of leaves for which the POS tags are different from those of the arguement.
    public int[] getDifferentPOSTags(SSFPhrase ch)
    {
        int diff[];
        List<BhaashikMutableTreeNode> lvs = getAllLeaves();

        int count = lvs.size();

        if (ch == null)
        {
            diff = new int[count];

            for (int i = 0; i < count; i++)
            {
                diff[i] = i;
            }

            return diff;
        }

        List<BhaashikMutableTreeNode> chlvs = ch.getAllLeaves();

        List<Integer> dvec = new ArrayList<Integer>();

        for (int i = 0; i < count && i < chlvs.size(); i++)
        {
            if (((SSFLexItem) lvs.get(i)).getName().equalsIgnoreCase(((SSFLexItem) chlvs.get(i)).getName()) == false)
            {
                dvec.add(Integer.valueOf(i));
            }
        }

        count = dvec.size();

        if (count <= 0)
        {
            return null;
        }

        diff = new int[count];

        for (int i = 0; i < count; i++)
        {
            diff[i] = ((Integer) dvec.get(i)).intValue();
        }

        return diff;
    }