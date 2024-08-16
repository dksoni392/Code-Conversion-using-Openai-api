

    public void reallocateId(String parentId)
    {
        int i;

        int count = getChildCount();
        for (i = 0; i < count; i++)
        {
            SSFNode node = this.getChild(i);

            if (getChild(i).isLeafNode())
            {
                node.id = parentId + (i + 1);
            } else
            {
                // it is a phrase so need to recursively go inside
                node.id = parentId + (i + 1);
                ((SSFPhrase) node).reallocateId(node.id + ".");
            }
        }
    }

    @Override
    public BhaashikMutableTreeNode getCopy() throws Exception
    {
        String str = makeString();

        SSFNode ssfNode = new SSFPhrase();
        ssfNode.readString(str);

        ssfNode.flags = flags;

        return ssfNode;
    }

    // Join child nodes
    public boolean joinNodes(int from, int count)
    {
        boolean joinable = true;

        List<SSFNode> chvec = getAllChildren();

        for (int i = from; i < from + count; i++)
        {
            if ((getChild(i) instanceof SSFLexItem) == false)
            {
                joinable = false;
            }
        }

        if (joinable)
        {
            SSFLexItem joinedNode = new SSFLexItem();
            joinedNode.setName(getChild(from).getName());
            joinedNode.setFeatureStructures(getChild(from).getFeatureStructures());

            String ld = "";

            for (int i = from; i < from + count; i++)
            {
//		if(i < from + count - 1)
//		    ld += getChild(i).getLexData() + " ";
//		else
                ld += getChild(i).getLexData();
            }

            for (int i = 0; i < count; i++)
            {
                remove(from);
            }

            joinedNode.setLexData(ld);

            addChildAt(joinedNode, from);
        }

        return joinable;
    }

    public boolean splitLexItem(int index)
    {
        boolean splittable = (getChild(index) instanceof SSFLexItem);

        if (splittable)
        {
            SSFLexItem node = (SSFLexItem) getChild(index);

            SSFLexItem node1 = new SSFLexItem();
            node1.setName(node.getName());
            node1.setFeatureStructures(node.getFeatureStructures());

            SSFLexItem node2 = null;

            if(node.getFeatureStructures() != null)
            {
                try {
                    node2 = new SSFLexItem(node.getId(), node.getLexData(), node.getName(), node.getFeatureStructures().makeString());
                } catch (Exception ex) {
                    Logger.getLogger(SSFPhrase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                node2 = new SSFLexItem();
                node2.setLexData(node.getLexData());
                node2.setName(node.getName());
            }

            String ld = node.getLexData();

            String lds[] = ld.split("[\\-]", 2);

            if (lds != null && lds.length == 2)
            {
                node1.setLexData(lds[0]);
                node2.setLexData(lds[1]);
            } else
            {
                node1.setLexData(ld);
                node2.setLexData(ld);
            }

            remove(index);

            addChildAt(node2, index);
            addChildAt(node1, index);

            reallocateId(getId());
        }

        return splittable;
    }

    @Override
    public void clear()
    {
        super.clear();

        removeAllChildren();
    }

    public void flatten()
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();
        removeAllChildren();

        int count = leaves.size();
        for (int i = 0; i < count; i++)
        {
            addChild((SSFNode) leaves.get(i));
        }
    }

    public boolean flattenChunks()
    {
        boolean flattened = false;

        if(getParent() !=null && getLexData().equals(""))
        {
            flattened = true;
            removeLayer();
        }

        for (int i = 0; i < getChildCount(); i++)
        {
            SSFNode cnode = getChild(i);

            if(cnode instanceof SSFPhrase)
            {
                ((SSFPhrase) cnode).flattenChunks();
            }
        }

        return flattened;
    }

//    public void copyAttributesMM2Chunk(SSFPhrase mmRoot)
//    {
//
// //        System.out.println("Converting");
////        if(mmRoot == null || mmRoot.countChildren() == 0)
////            return;
//
////        String attribVal = mmRoot.getAttributeValueString(attribName);
//
//    //    SSFPhrase node = (SSFPhrase) ((SSFPhrase) getRoot()).getNodeForId(mmRoot.getId());
//
//    //       SSFNode node = ((SSFPhrase) getRoot()).getNodeForId(mmRoot.getId());
//
////         SSFNode node = getNodeForId(mmRoot.getId());
//
////        if(node != null)
////        {
////            node.setAttributeValue(attribName, attribVal);
////        }
//
//     //   int count = countChildren();
//
////           int count = mmRoot.getChildCount();
//
////        for (int i = 0; i < count; i++)
////        {
//           // SSFPhrase mmChild = (SSFPhrase) mmRoot.getChild(i);
////            SSFNode mmChild = mmRoot.getChild(i);
//
////            if(mmChild instanceof SSFPhrase)
////            {
//           // attribVal = mmChild.getAttributeValueString(attribName);
//
//           // node = ((SSFPhrase) getRoot()).getNodeForId(mmChild.getId());
//
//           // if(node != null)
//           // {
//           //     node.setAttributeValue(attribName, attribVal);
//           // }
//
////            copyAttributesMM2Chunk((SSFPhrase) mmChild, attribName);
////           }
////        }
//
//   /*     if(value==0)
//        {
//             value=1;
//            convertMM2Chunk((SSFPhrase) mmRoot.getChild(0));
//        }
//    */
//        int count=mmRoot.getChildCount();
//
//        SSFNode rootChild=((SSFPhrase) getRoot()).getNodeForId(mmRoot.getId());
//
////        if(count!=0){
////        rootChild.setAttributeValue("name",mmRoot.getAttributeValueString("name"));
////        }
////       value++;
////       int temp=value-1;
//
//        int check=0;
//
//        for(int i=0;i<count;i++)
//        {
//           SSFNode temp = ((SSFPhrase) mmRoot).getChild(i);
//            if(temp instanceof SSFPhrase)
//                check++;
//        }
//
//        String depAttribs[] = FSProperties.getAnnCorraDependencyAttributes();
//
////        String drelAttribVal = mmRoot.getAttributeValueString("drel");
//        String drelAttribVal[] = mmRoot.getOneOfAttributeValues(depAttribs);
//
//        if(drelAttribVal !=null && !drelAttribVal.equals(""))
//        {
//            drelAttribVal[1].replaceAll("[']", "");
//            drelAttribVal[1].replaceAll("[`]", "");
//            drelAttribVal[1].replaceAll("[\"]", "");
//
////            rootChild.setAttributeValue("drel", drelAttribVal);
//            rootChild.setAttributeValue(drelAttribVal[0], drelAttribVal[1]);
//        }
//
//        String nameAttribVal = mmRoot.getAttributeValueString("name");
//
//        if(nameAttribVal != null && nameAttribVal.equals("") == false)
//        {
//            nameAttribVal.replaceAll("[']", "");
//            nameAttribVal.replaceAll("[`]", "");
//            nameAttribVal.replaceAll("[\"]", "");
//
//            if (check!=0)
//                rootChild.setAttributeValue("name", nameAttribVal);
//            else
//            {
//                return;
//            }
//        }
//
//        for (int i=0;i<count;i++)
//        {
//            SSFNode mmChild= mmRoot.getChild(i);
//
//            if(mmChild instanceof SSFPhrase)
//            {
////                rootChild=((SSFPhrase) getRoot()).getNodeForId(mmChild.getId());
//
////                rootChild.setAttributeValue("drel",mmChild.getAttributeValueString("drel"));
//
//                copyAttributesMM2Chunk((SSFPhrase)mmChild);
//            }
//        }
//    }
    public void copyAttributesDep2Chunk(SSFPhrase mmRoot, LinkedHashMap cfgToMMTreeMapping)
    {
//        Iterator itr = cfgToMMTreeMapping.keySet().iterator();
//        String depAttribs[] = FSProperties.getAnnCorraDependencyAttributes();
//
//        while(itr.hasNext())
//        {
//            SSFNode cfgNode = (SSFNode) itr.next();
//
//            SSFNode mmtNode = (SSFNode) cfgToMMTreeMapping.get(cfgNode);
//
//            if((cfgNode instanceof SSFPhrase && mmtNode instanceof SSFPhrase)
//                && (cfgNode != null && mmtNode != null))
//            {
//                String refAtVal[] = mmtNode.getOneOfAttributeValues(depAttribs);
//
//                if(refAtVal != null && refAtVal.length == 2)
//                    cfgNode.setAttributeValue(refAtVal[0], refAtVal[1]);
//            }
//        }

//        removeDSRedundantPhrases();

        String depAttribs[] = FSProperties.getDependencyAttributes();

        int mcount = mmRoot.getChildCount();

        for (int i = 0; i < mcount; i++)
        {
            SSFNode mnode = mmRoot.getChild(i);

            if (mnode instanceof SSFPhrase)
            {
                String refAtVal[] = mnode.getOneOfAttributeValues(depAttribs);

                if (refAtVal == null)
                {
                    copyAttributesDep2Chunk((SSFPhrase) mnode, cfgToMMTreeMapping);
                    continue;
                }

                String refVal = refAtVal[1];

                if (refVal == null)
                {
                    copyAttributesDep2Chunk((SSFPhrase) mnode, cfgToMMTreeMapping);
                    continue;
                }

//                String parts[] = refVal.split(":");
//
//                if (parts[1] == null)
//                {
//                    continue;
//                }

                String mnodeName = mnode.getAttributeValue("name");

                SSFNode cnode = getNodeForAttribVal("name", mnodeName, true);

                if(cnode != null)
                    cnode.setAttributeValue(refAtVal[0], refAtVal[1]);

                copyAttributesDep2Chunk((SSFPhrase) mnode, cfgToMMTreeMapping);
            }
        }
    }