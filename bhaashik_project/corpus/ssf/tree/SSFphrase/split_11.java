

    public String getLexicalSequence(String tag, String compoundTag)
    {
        String lexSeq = "";

        Pattern tp = Pattern.compile(tag);
        Pattern ctp = Pattern.compile(compoundTag);

        boolean goOn = false;

        int scount = countChildren();

        for (int i = 0; i < scount; i++)
        {
            SSFNode ssfNode = getChild(i);

            if(ssfNode instanceof SSFLexItem)
            {
                Matcher tm = tp.matcher(ssfNode.getName());
                Matcher ctm = ctp.matcher(ssfNode.getName());

                if(tm.find() || ctm.find())
                {
                    if(goOn) {
                        lexSeq += "_" + ssfNode.getLexData();
                    }
                    else {
                        lexSeq = ssfNode.getLexData();
                    }

                    goOn = true;
                }
                else
                {
                    goOn = false;

                    if(lexSeq.equals("") == false) {
                        return lexSeq;
                    }
                }
            }
        }

        return lexSeq;
    }

    public String getLexDataForTag(String tag)
    {
        int scount = countChildren();

        for (int i = 0; i < scount; i++)
        {
            SSFNode ssfNode = getChild(i);

            if(ssfNode instanceof SSFLexItem && ssfNode.getName().equalsIgnoreCase(tag)) {
                return ssfNode.getLexData();
            }
        }

        return "";
    }

    public String getStemForTag(String tag)
    {
        int scount = countChildren();

        for (int i = 0; i < scount; i++)
        {
            SSFNode ssfNode = getChild(i);

            if(ssfNode instanceof SSFLexItem && ssfNode.getName().equalsIgnoreCase(tag)) {
                return ssfNode.getAttributeValue("lex");
            }
        }

        return "";
    }

    public LinkedHashMap<String, Integer> getWordFreq()
    {
        LinkedHashMap<String, Integer> words = new LinkedHashMap();

        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int lcount = leaves.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode leafNode = (SSFNode) leaves.get(j);

            if(leafNode instanceof SSFLexItem)
            {
                String lexData = leafNode.getLexData();

                if(words.get(lexData) == null) {
                    words.put(lexData, 1);
                }
                else {
                    words.put(lexData, words.get(lexData) + 1);
                }
            }
        }

        return words;
    }

    public LinkedHashMap<String, Integer> getPOSTagFreq()
    {
        LinkedHashMap<String, Integer> tags = new LinkedHashMap();

        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int lcount = leaves.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode leafNode = (SSFNode) leaves.get(j);

            if(leafNode instanceof SSFLexItem)
            {
                String tag = leafNode.getName();

                if(tags.get(tag) == null) {
                    tags.put(tag, 1);
                }
                else {
                    tags.put(tag, tags.get(tag) + 1);
                }
            }
        }

        return tags;
    }

    public LinkedHashMap<String, Integer> getWordTagPairFreq()
    {
        LinkedHashMap<String, Integer> words = new LinkedHashMap();

        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int lcount = leaves.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode leafNode = (SSFNode) leaves.get(j);

            if(leafNode instanceof SSFLexItem)
            {
                String lexData = leafNode.getLexData();
                String tag = leafNode.getName();

                String wordTagPair = lexData + "/" + tag;

                if(words.get(wordTagPair) == null) {
                    words.put(wordTagPair, 1);
                }
                else {
                    words.put(wordTagPair, words.get(wordTagPair) + 1);
                }
            }
        }

        return words;
    }

    public LinkedHashMap<String, Integer> getChunkTagFreq()
    {
        LinkedHashMap<String, Integer> tags = new LinkedHashMap();

        List<SSFNode> allChildren = getAllChildren();

        int lcount = allChildren.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode childNode = allChildren.get(j);

            if(childNode instanceof SSFPhrase)
            {
                String tag = childNode.getName();

                if(tags.get(tag) == null) {
                    tags.put(tag, 1);
                }
                else {
                    tags.put(tag, tags.get(tag) + 1);
                }

                tags.putAll(((SSFPhrase) childNode).getChunkTagFreq());
            }
        }

        return tags;
    }

    public LinkedHashMap<String, Integer> getGroupRelationFreq()
    {
        LinkedHashMap<String, Integer> rels = new LinkedHashMap();

        List<SSFNode> allChildren = getAllChildren();

        String depAttribs[] = FSProperties.getDependencyAttributes();

        int lcount = allChildren.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode childNode = allChildren.get(j);

            if(childNode instanceof SSFPhrase)
            {
                String refAtVal[] = childNode.getOneOfAttributeValues(depAttribs);

                if(refAtVal == null) {
                    continue;
                }

                String attrib = refAtVal[0];
                String val = refAtVal[1];

                String parts[] = val.split(":");

                String attribVal = attrib + "=" + parts[0];

                if(rels.get(attribVal) == null)
                {
                    rels.put(attribVal, 1);
                }
                else {
                    rels.put(attribVal, rels.get(attribVal) + 1);
                }

                rels.putAll(((SSFPhrase) childNode).getGroupRelationFreq());
            }
        }

        return rels;
    }

    public LinkedHashMap<String, Integer> getChunkRelationFreq()
    {
        LinkedHashMap<String, Integer> rels = new LinkedHashMap();

        List<SSFNode> allChildren = getAllChildren();

        String depAttribs[] = FSProperties.getDependencyAttributes();

        int lcount = allChildren.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode childNode = allChildren.get(j);

            if(childNode instanceof SSFPhrase)
            {
                String refAtVal[] = childNode.getOneOfAttributeValues(depAttribs);

                if(refAtVal == null) {
                    continue;
                }

                String chunk = childNode.makeRawSentence();

                String attrib = refAtVal[0];
                String val = refAtVal[1];

                String parts[] = val.split(":");

                String attribVal = chunk + "::" + attrib + "=" + parts[0];

                if(rels.get(attribVal) == null)
                {
                    rels.put(attribVal, 1);
                }
                else {
                    rels.put(attribVal, rels.get(attribVal) + 1);
                }

                rels.putAll(((SSFPhrase) childNode).getChunkRelationFreq());
            }
        }

        return rels;
    }

    public LinkedHashMap<String, Integer> getAttributeFreq()
    {
        LinkedHashMap<String, Integer> attribs = new LinkedHashMap();

        List<SSFNode> allChildren = getAllChildren();

        int lcount = allChildren.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode childNode = allChildren.get(j);

            List<String> attribNames = childNode.getAttributeNames();

            if(attribNames == null) {
                continue;
            }

            int acount = attribNames.size();

            for (int i = 0; i < acount; i++)
            {
                String attrib = attribNames.get(i);

                if(attribs.get(attrib) == null) {
                    attribs.put(attrib, 1);
                }
                else {
                    attribs.put(attrib, attribs.get(attrib) + 1);
                }
            }

            if(childNode instanceof SSFPhrase)
            {
                attribs.putAll(((SSFPhrase) childNode).getAttributeFreq());
            }
        }

        return attribs;
    }

    public LinkedHashMap<String, Integer> getAttributeValueFreq()
    {
        LinkedHashMap<String, Integer> attribs = new LinkedHashMap();

        List<SSFNode> allChildren = getAllChildren();

        int lcount = allChildren.size();

        for (int j = 0; j < lcount; j++)
        {
            SSFNode childNode = allChildren.get(j);

            List<String> attribVals = childNode.getAttributeValues();

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
                attribs.putAll(((SSFPhrase) childNode).getAttributeValueFreq());
            }
        }

        return attribs;
    }