

    public boolean areLexItemFeaturesSame(SSFPhrase ch)
    {
        if (ch == null)
        {
            return false;
        }

        if (getDifferentLexItemFeatures(ch) != null)
        {
            return false;
        }

        return true;
    }

    // Return indices of leaves for which the Features are different from those of the arguement.
    public int[] getDifferentLexItemFeatures(SSFPhrase ch)
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
            FeatureStructures fstrs = ((SSFLexItem) lvs.get(i)).getFeatureStructures();
            FeatureStructures chunk_fstrs = ((SSFLexItem) chlvs.get(i)).getFeatureStructures();

            if ((fstrs != null && fstrs.equals(chunk_fstrs) == false) || (fstrs == null && chunk_fstrs != null))
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

    public boolean areChunkFeaturesSame(SSFPhrase ch)
    {
        if (ch == null)
        {
            return false;
        }

        if (getDifferentChunkFeatures(ch) != null)
        {
            return false;
        }

        return true;
    }

    // Return indices of chunks for which the Features are different from those of the arguement.
    public int[] getDifferentChunkFeatures(SSFPhrase ch)
    {
        int diff[];

        int count = countChildren();

        if (ch == null || isChunkingSame(ch) == false)
        {
            diff = new int[count];

            for (int i = 0; i < count; i++)
            {
                diff[i] = i;
            }

            return diff;
        }

        List<Integer> dvec = new ArrayList<Integer>();

        for (int i = 0; i < count; i++)
        {
            FeatureStructures fstrs = getChild(i).getFeatureStructures();
            FeatureStructures chunk_fstrs = ch.getChild(i).getFeatureStructures();

            if ((fstrs != null && fstrs.equals(chunk_fstrs) == false) || (fstrs == null && chunk_fstrs != null))
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

    /*
     * Check whether the chunk has the same lexical items, irrespective of their POS tags.
     */
    public boolean isChunkingSame(SSFPhrase ch)
    {
        if (ch == null)
        {
            return false;
        }

        if (getLexData().equals(ch.getLexData()) == false)
        {
            return false;
        }

        if (getName().equals(ch.getName()) == false)
        {
            return false;
        }

        if (countChildren() != ch.countChildren())
        {
            return false;
        }

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            if ((getChild(i).isLeafNode() == false && ch.getChild(i).isLeafNode() == true) || (getChild(i).isLeafNode() == true && ch.getChild(i).isLeafNode() == false))
            {
                return false;
            } else if (getChild(i).isLeafNode() == true && ch.getChild(i).isLeafNode() == true)
            {
                if (((SSFNode) getChild(i)).getLexData().equals(((SSFNode) ch.getChild(i)).getLexData()) == false)
                {
                    return false;
                }
            } else if (getChild(i).isLeafNode() == false && ch.getChild(i).isLeafNode() == false)
            {
                return ((SSFPhrase) getChild(i)).isChunkingSame(((SSFPhrase) ch.getChild(i)));
            }
        }

        return true;
    }

    @Override
    public void setValuesInTable(DefaultTableModel tbl, int mode)
    {
//	if(requiredColumnCount == -1 || rowIndex == -1 || columnIndex == -1)
//	    return;

        super.setValuesInTable(tbl, mode);

        if (isLeaf() == false)
        {
            int chcount = getChildCount();

            for (int i = 0; i < chcount; i++)
            {
                SSFNode child = (SSFNode) getChildAt(i);
                child.setValuesInTable(tbl, mode);
            }
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof SSFPhrase)) {
            return false;
        }
        
        if (super.equals(obj) == false)
        {
            return false;
        }

        SSFPhrase pobj = (SSFPhrase) obj;

        int count = countChildren();
        if (count != pobj.countChildren())
        {
            return false;
        }

        for (int i = 0; i < count; i++)
        {
            if (getChild(i).equals(pobj.getChild(i)) == false)
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean matches(FindReplaceOptions findReplaceOptions)
    {
        boolean match = false;

        Pattern pattern = FindReplace.compilePattern(findReplaceOptions.findText, findReplaceOptions);
        Matcher matcher = null;

        String text = makeRawSentence();

        if (text != null && text.equals("") == false)
        {
            matcher = pattern.matcher(text);

            if (matcher.find())
            {
                match = true;
            }
        } else
        {
            match = false;
        }

//        if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.tag != null && findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.tag.equals("") == false)
//        {
//            pattern = FindReplace.compilePattern(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.tag, findReplaceOptions);
//
//            String tag = getName();
//
//            if (tag != null && tag.equals("") == false)
//            {
//                matcher = pattern.matcher(tag);
//
//                if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr1.equals("And"))
//                {
//                    match = match && matcher.find();
//                } else
//                {
//                    match = match || matcher.find();
//                }
//            } else
//            {
//                if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr1.equals("And"))
//                {
//                    match = match && false;
//                } else
//                {
//                    match = match || false;
//                }
//            }
//        }
//
//        if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature != null && findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature.equals("") == false)
//        {
//            if (getAttribute(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature) != null)
//            {
//                if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr2.equals("And"))
//                {
//                    match = match && true;
//                } else
//                {
//                    match = match || true;
//                }
//            } else
//            {
//                if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr2.equals("And"))
//                {
//                    match = match && false;
//                } else
//                {
//                    match = match || false;
//                }
//            }
//        }
//
//        if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.featureValue != null && findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.featureValue.equals("") == false)
//        {
//            pattern = FindReplace.compilePattern(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.featureValue, findReplaceOptions);
//            String val = (String) getAttributeValueString(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature);
//
//            if (val != null && val.equals("") == false)
//            {
//                matcher = pattern.matcher(val);
//
//                if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr3.equals("And"))
//                {
//                    match = match && matcher.find();
//                } else
//                {
//                    match = match || matcher.find();
//                }
//            } else
//            {
//                if (findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr3.equals("And"))
//                {
//                    match = match && false;
//                } else
//                {
//                    match = match || false;
//                }
//            }
//        }

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);
            match = match || child.matches(findReplaceOptions);
        }

        if(match) {
            isHighlighted(true);
        }

        return match;
    }