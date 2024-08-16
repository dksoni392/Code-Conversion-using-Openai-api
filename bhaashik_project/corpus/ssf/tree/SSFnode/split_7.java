

    @Override
    public void fillGraphEdges(BhaashikJTable jtbl, int mode)
    {
        String atrrNames[] = null;

        switch (mode)
        {
            case PHRASE_STRUCTURE_MODE:
                atrrNames = FSProperties.getPSGraphAttributes();

                int mcount = getChildCount();

                for (int i = 0; i < mcount; i++)
                {
                    SSFNode mnode = (SSFNode) getChildAt(i);

                    mnode.fillGraphEdges(jtbl, mode);

                    String refAtVal[] = mnode.getOneOfAttributeValues(atrrNames);

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

//                    if (parts[1] == null)
//                    {
//                        continue;
//                    }

                    String rel = "";
                    String nm = "";

                    if(parts.length == 1)
                    {
                        nm = parts[0];
                    }
                    else if(parts.length == 1)
                    {
                        rel = parts[0];
                        nm = parts[1];
                    }

                    SSFNode referredNode = ((SSFPhrase) getRoot()).getNodeForAttribVal("name", nm, true);

                    if (referredNode != null && referredNode.getRowIndex() >= 0)
                    {
                        BhaashikEdge edge = new BhaashikEdge(this, referredNode.getRowIndex(), referredNode.getColumnIndex(), mnode, mnode.getRowIndex(), mnode.getColumnIndex());

                        String prel = rel;
                        edge.setLabel(prel.toUpperCase());
                        Color color = UtilityFunctions.getColor(FSProperties.getPSGraphAttributeProperties(refAtVal[0])[0]);
                        edge.setColor(color);
                        Stroke stroke = UtilityFunctions.getStroke(FSProperties.getPSGraphAttributeProperties(refAtVal[0])[1]);
                        edge.setStroke(stroke);
                        edge.isCurved(true);

                        jtbl.addEdge(edge);
                    }

                    jtbl.setCellObject(mnode.getRowIndex(), mnode.getColumnIndex(), mnode);
                }

                break;
            case DEPENDENCY_RELATIONS_MODE:

                atrrNames = FSProperties.getDependencyGraphAttributes();

                if (isLeaf() == false || jtbl.allowsLeafDependencies())
                {
                    mcount = getChildCount();

                    for (int i = 0; i < mcount; i++)
                    {
                        SSFNode mnode = (SSFNode) getChildAt(i);

                        if (mnode instanceof SSFPhrase || jtbl.allowsLeafDependencies())
                        {
                            mnode.fillGraphEdges(jtbl, mode);

                            String refAtVal[] = mnode.getOneOfAttributeValues(atrrNames);

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

                            String rel = "";
                            String nm = "";

                            if (parts.length == 1)
                            {
                                nm = parts[0];
                            }
                            else if(parts.length == 2)
                            {
                                rel = parts[0];
                                nm = parts[1];
                            }

                            SSFNode referredNode = ((SSFPhrase) getRoot()).getNodeForAttribVal("name", nm, true);

                            if (referredNode != null && referredNode.getRowIndex() >= 0)
                            {
                                BhaashikEdge edge = new BhaashikEdge(this, referredNode.getRowIndex(), referredNode.getColumnIndex(), mnode, mnode.getRowIndex(), mnode.getColumnIndex());

                                String drel = rel;
                                edge.setLabel(drel.toUpperCase());
                                Color color = UtilityFunctions.getColor(FSProperties.getDependencyGraphAttributeProperties(refAtVal[0])[0]);
                                edge.setColor(color);
                                Stroke stroke = UtilityFunctions.getStroke(FSProperties.getDependencyGraphAttributeProperties(refAtVal[0])[1]);
                                edge.setStroke(stroke);
                                edge.isCurved(true);

                                jtbl.addEdge(edge);
                            }

                            jtbl.setCellObject(mnode.getRowIndex(), mnode.getColumnIndex(), mnode);
                        }
                    }
                }

                break;

            default:
                super.fillGraphEdges(jtbl, mode);
        }
    }
    
    public static Index getCurrentVocabulary()
    {
        return UtilityFunctions.getCopy(vocabIndex);
    }
    
    public static Index getCurrentTagVocabulary()
    {
        return UtilityFunctions.getCopy(tagIndex);
    }
}
