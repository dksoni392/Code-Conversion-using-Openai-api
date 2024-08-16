

    public void copyDataDep2Chunk(SSFNode mmRoot, LinkedHashMap cfgToMMTreeMapping, boolean leafDependencies)
    {
        String depAttribs[] = FSProperties.getDependencyAttributes();

        int mcount = mmRoot.getChildCount();

        for (int i = 0; i < mcount; i++)
        {
            SSFNode mnode = (SSFNode) mmRoot.getChildAt(i);

            if ((mnode instanceof SSFPhrase && leafDependencies == false)
                    || (leafDependencies == true))
            {
                String refAtVal[] = mnode.getOneOfAttributeValues(depAttribs);

                if (refAtVal == null)
                {
                    copyDataDep2Chunk(mnode, cfgToMMTreeMapping, leafDependencies);
                    continue;
                }

                String refVal = refAtVal[1];

                if (refVal == null)
                {
                    copyDataDep2Chunk(mnode, cfgToMMTreeMapping, leafDependencies);
                    continue;
                }

                String mnodeName = mnode.getAttributeValue("name");

                SSFNode cnode = getNodeForAttribVal("name", mnodeName, true);

                if(cnode != null)
                {
//                    cnode.setAttributeValue(refAtVal[0], refAtVal[1]);
                    FeatureStructures mfss = mnode.getFeatureStructures();

                    if(mfss != null) {
                        cnode.setFeatureStructures(mfss);
                    }

                    cnode.setName(mnode.getName());
                }

                copyDataDep2Chunk(mnode, cfgToMMTreeMapping, leafDependencies);
            }
        }
    }

    public void copyAttributesPS2Chunk(SSFPhrase psRoot, LinkedHashMap cfgToPSTreeMapping)
    {
        String psAttribs[] = FSProperties.getPSAttributes();

        int pcount = psRoot.getChildCount();

        for (int i = 0; i < pcount; i++)
        {
            SSFNode pnode = psRoot.getChild(i);

//            if (pnode instanceof SSFPhrase)
//            {
                String refAtVal[] = pnode.getOneOfAttributeValues(psAttribs);

                if (refAtVal == null)
                {
                    continue;
                }

                String refVal = refAtVal[1];

                if (refVal == null)
                {
                    continue;
                }

//                String parts[] = refVal.split(":");
//
//                if (parts[1] == null)
//                {
//                    continue;
//                }

                String pnodeName = pnode.getAttributeValue("name");

                SSFNode cnode = getNodeForAttribVal("name", pnodeName, true);

                cnode.setAttributeValue(refAtVal[0], refAtVal[1]);

                copyAttributesPS2Chunk((SSFPhrase) pnode, cfgToPSTreeMapping);
//            }
        }
    }

    // Recursive
    public void reallocateNames(LinkedHashMap tags, LinkedHashMap words /* both null at the top level call */)
    {
        reallocateNames(tags, words, 0);
    }

    public void reallocateNames(LinkedHashMap tags, LinkedHashMap words /* both null at the top level call */, int fromChild)
    {
        SSFProperties ssfp = getSSFProperties();
        String chunkStart = ssfp.getProperties().getPropertyValue("chunkStart");

        int i;

        if(tags == null)
        {
            tags = new LinkedHashMap(0, 10);
            words = new LinkedHashMap(0, 10);
        }

        int count = getChildCount();

        for (i = fromChild; i < count; i++)
        {
            SSFNode node = this.getChild(i);

//            if(getChild(i) instanceof SSFPhrase)
//            {
            String tag = node.getName();
            String word = node.getLexData();

            if (tag == null || tag.equals("")) {
                tag = "XP";
            }

            if(node instanceof SSFPhrase && word.equals("") == false) {
                tag = node.getLexData();
            }

            if (word == null || word.equals("")) {
                word = "NULL";
            }

            if (word.equals(":")) {
                word = "symColon";
            }

            String oldName = node.getAttributeValue("name");
            String newName = "";

            if(node instanceof SSFPhrase)
            {
                Integer prevTagNum = (Integer) tags.get(tag);

                if (prevTagNum == null)
                {
                    newName = tag;
                    tags.put(tag, Integer.valueOf(1));
                } else
                {
                    newName = tag + (prevTagNum.intValue() + 1);
                    tags.put(tag, Integer.valueOf(prevTagNum.intValue() + 1));
                }
            }
            else
            {
                Integer prevTagNum = (Integer) words.get(word);

                if (prevTagNum == null)
                {
                    newName = word;
                    words.put(word, Integer.valueOf(1));
                } else
                {
                    newName = word + (prevTagNum.intValue() + 1);
                    words.put(word, Integer.valueOf(prevTagNum.intValue() + 1));
                }
            }

//            SSFPhrase rootNode = (SSFPhrase) getRoot();

//            Vector refNodes = rootNode.getReferringNodes(node, PHRASE_STRUCTURE_MODE);
            List<SSFNode> refNodes = getReferringNodes(node, PHRASE_STRUCTURE_MODE);

            int rcount = refNodes.size();

            for (int j = 0; j < rcount; j++)
            {
                SSFPhrase rnode = (SSFPhrase) refNodes.get(j);

                if (rnode != null)
                {
                    rnode.setReferredName(newName, PHRASE_STRUCTURE_MODE);
                }
            }

//            refNodes = rootNode.getReferringNodes(node, DEPENDENCY_STRUCTURE_MODE);
            refNodes = getReferringNodes(node, DEPENDENCY_RELATIONS_MODE);

            rcount = refNodes.size();

            for (int j = 0; j < rcount; j++)
            {
//                SSFPhrase rnode = (SSFPhrase) refNodes.get(j);
                SSFNode rnode = refNodes.get(j);

                if (rnode != null)
                {
                    rnode.setReferredName(newName, DEPENDENCY_RELATIONS_MODE);
                }
            }

            node.setAttributeValue("name", newName);
//            }

            if(node instanceof SSFPhrase) {
                ((SSFPhrase) node).reallocateNames(tags, words);
            }
        }
    }

    public static void getMapping(SSFNode node1, SSFNode node2, LinkedHashMap mapping)
    {
        if ((node1 instanceof SSFLexItem && node2 instanceof SSFLexItem) || (node1 instanceof SSFPhrase && node2 instanceof SSFPhrase))
        {
            if (node1.getName().equals(node2.getName()) && node1.countChildren() == node2.countChildren())
            {
                mapping.put(node1, node2);
            }

            if (node1 instanceof SSFPhrase)
            {
                int ccount = node1.countChildren();

                for (int i = 0; i < ccount; i++)
                {
                    SSFNode cnode1 = ((SSFPhrase) node1).getChild(i);
                    SSFNode cnode2 = ((SSFPhrase) node2).getChild(i);

                    mapping.put(cnode1, cnode2);

                    getMapping(cnode1, cnode2, mapping);
                }
            }
        }
    }

    public void expandMMTree(LinkedHashMap cfgToMMTreeMapping)
    {
        LinkedHashMap mmtToCFGMapping = (LinkedHashMap) UtilityFunctions.getReverseMap(cfgToMMTreeMapping);

        int ccount = countChildren();

        for (int i = 0; i < ccount; i++)
        {
            SSFNode mmtNode = getChild(i);
            SSFNode cfgNode = (SSFNode) mmtToCFGMapping.get(mmtNode);

            if (cfgNode == null)
            {
                if (mmtNode instanceof SSFPhrase)
                {
                    ((SSFPhrase) mmtNode).expandMMTree(cfgToMMTreeMapping);
                }

                continue;
            }

            if (cfgNode instanceof SSFPhrase && ((SSFPhrase) mmtNode).hasLexItemChild() == false)
            {
                mmtNode.setLexData("");

                List<BhaashikMutableTreeNode> leaves = cfgNode.getAllLeaves();

                int iccount = leaves.size();

                for (int j = 0; j < iccount; j++)
                {
                    SSFNode cnode = (SSFNode) leaves.get(j);

                    SSFNode mnode = null;

                    try
                    {
                        mnode = (SSFNode) cnode.getCopy();
                    } catch (Exception ex)
                    {
                        Logger.getLogger(SSFPhrase.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try
                    {
                        mmtNode.add(mnode);
                        cfgToMMTreeMapping.put(cnode, mnode);
                    } catch (Exception ex)
                    {
                        Logger.getLogger(SSFPhrase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if (mmtNode instanceof SSFPhrase)
            {
                ((SSFPhrase) mmtNode).expandMMTree(cfgToMMTreeMapping);
            }
        }
    }