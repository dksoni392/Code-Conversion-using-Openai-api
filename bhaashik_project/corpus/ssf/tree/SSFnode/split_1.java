
    
    @Override
    public List<SSFNode> getAlignedObjects()
    {
        return alignmentUnit.getAlignedObjects();
    }

    @Override
    public SSFNode getFirstAlignedObject()
    {
        return alignmentUnit.getFirstAlignedObject();
    }

    @Override
    public SSFNode getAlignedObject(int i)
    {
        return alignmentUnit.getAlignedObject(i);
    }

    @Override
    public SSFNode getLastAlignedObject()
    {
        return alignmentUnit.getLastAlignedObject();
    }

    public void loadAlignments(SSFSentence srcSentence, SSFSentence tgtSentence, int parallelIndex)
    {
        if(fs == null)
        {
            fs = new FeatureStructuresImpl();
            FeatureStructure featureStructure = new FeatureStructureImpl();
            
            fs.addAltFSValue(featureStructure);
        }
        
        AlignmentUnit aunit = fs.loadAlignmentUnit(this, srcSentence, tgtSentence, parallelIndex);

        if(aunit != null) {
            alignmentUnit = aunit;
        }

        if(this instanceof SSFPhrase)
        {
            List<SSFNode> allChildren = ((SSFPhrase) this).getAllChildren();

            int count = allChildren.size();

            for (int i = 0; i < count; i++)
            {
                SSFNode node = allChildren.get(i);

                node.loadAlignments(srcSentence, tgtSentence, parallelIndex);
            }
        }
    }

    public void saveAlignments()
    {
        if(fs == null) {
            return;
        }
        
        fs.setAlignmentUnit(alignmentUnit);

        if(this instanceof SSFPhrase)
        {
            List<SSFNode> allChildren = ((SSFPhrase) this).getAllChildren();

            int count = allChildren.size();

            for (int i = 0; i < count; i++)
            {
                SSFNode node = allChildren.get(i);

                node.saveAlignments();
            }
        }
    }

    public boolean isHighlighted()
    {
        boolean hf = UtilityFunctions.flagOn(flags, HIGHLIGHTED);

        boolean ha = false;

        String hs = getAttributeValue(HIGHLIGHT);

        if(hs != null && hs.equals("true")) {
            ha = true;
        }

        return (hf || ha);
    }

    public void isHighlighted(boolean h)
    {
        if(h)
        {
            flags = UtilityFunctions.switchOnFlags(flags, HIGHLIGHTED);

            setAttributeValue(HIGHLIGHT, "true");

            return;
        }

        setAttributeValue(HIGHLIGHT, "false");

        flags = UtilityFunctions.switchOffFlags(flags, HIGHLIGHTED);
    }

    public void clearHighlights()
    {
        isHighlighted(false);

    	int count = countChildren();

        for (int i = 0; i < count; i++ )
        {
    		SSFNode child = (SSFNode) getChildAt(i);
            child.clearHighlights();
        }
    }

    public boolean hasLeaves()
    {
        return false;
    }

    public void setMorphTags(KeyValueProperties morphTags)
    {
        if(morphTags == null) {
            return;
        }

        if(this instanceof SSFPhrase)
        {
            int depth = getDepth();

            if(depth == 1)
            {
                List<BhaashikMutableTreeNode> leaves = getAllLeaves();

                int count = leaves.size();

                for (int i = 0; i < count; i++)
                {
                    SSFNode leaf = (SSFNode) leaves.get(i);
                    String leafPOSTag = leaf.getName();

                    String mtag = morphTags.getPropertyValue(leafPOSTag);

                    FeatureStructures leafFss = leaf.getFeatureStructures();

                    if(leafFss != null)
                    {
                        if(mtag == null || mtag.equals(""))
                        {
                            SSFNode prevNode = leaf.getPrevious();

                            if(prevNode != null)
                            {
                                mtag = morphTags.getPropertyValue(prevNode.getName());

                                leafFss.setAllAttributeValues("cat", mtag);
                            }
                        }
                        else {
                            leafFss.setAllAttributeValues("cat", mtag);
                        }
                    }
                }
            }
            else if(depth > 1)
            {
                int ccount = countChildren();

                for (int i = 0; i < ccount; i++)
                {
                    SSFNode childNode = (SSFNode) getChildAt(i);
                    childNode.setMorphTags(morphTags);
                }
            }
        }
    }

    public FeatureStructures getFeatureStructures() 
    {
        return fs;
    }

    public String getStringFS() 
    {
        if(fs == null) {
            return "";
        }

        return fs.makeString();
    }

    public void setFeatureStructures(FeatureStructures f) 
    {
        fs = f;
    }

    public List<String> getAttributeNames()
    {
        if(fs == null) {
            return null;
        }

        return fs.getAttributeNames();
    }

    public String getAttributeValue(String attibName)
    {
        if(fs == null) {
            return null;
        }

        return fs.getAttributeValueString(attibName);
    }

    public List<String> getAttributeValues()
    {
        if(fs == null) {
            return null;
        }

        return fs.getAttributeValues();
    }

    public List<String> getAttributeValuePairs()
    {
        if(fs == null) {
            return null;
        }

        return fs.getAttributeValuePairs();
    }

    public String[] getOneOfAttributeValues(String attibNames[])
    {
        if(fs == null) {
            return null;
        }

        return fs.getOneOfAttributeValues(attibNames);
    }

    public void setAttributeValue(String attibName, String val)
    {
        if(fs == null) {
            fs = new FeatureStructuresImpl();
        }

        fs.setAttributeValue(attibName, val);
    }

    public void concatenateAttributeValue(String attibName, String val, String sep)
    {
        if(fs == null) {
            fs = new FeatureStructuresImpl();
        }

        fs.concatenateAttributeValue(attibName, val, sep);
    }

    public FeatureAttribute getAttribute(String attibName)
    {
        if(fs == null) {
            return null;
        }

        return fs.getAttribute(attibName);
    }

//    public SSFNode getParent() 
//    {
//        return parent;
//    }
//
//    public void setParent(SSFNode p) // #% &get_parent( $node , [$tree] ) -> $parent_node
//    {
//        parent = p;
//    }

    public int countChildren()
    {
//        if(lexdata.equals(""))
//            return getChildCount();

        return 0;
    }

    // other methods

    public boolean isLeafNode()
    {
        return isLeaf();
    }

    public int removeNode ()
    {
        SSFNode prnt = (SSFNode) getParent();
        int ind = ((SSFPhrase) prnt).findChild(this);
        ((SSFPhrase) prnt).removeChild(ind);

        return ind;
    }

    public void removeEmptyPhrases()
    {

    }

    public void removeNonChunkPhrases()
    {
        
    }

    public boolean isNonChunkPhrase()
    {
        return false;
    }

    public void removeDSRedundantPhrases()
    {

    }

    public boolean isDSRedundantPhrase()
    {
        return false;
    }

    public int removeLayer()
    {
        SSFNode prnt = (SSFNode) getParent();

        if(prnt == null) {
            return -1;
        }
	
        int ind = ((SSFPhrase) prnt).findChild(this);

        alignmentUnit.clearAlignments();

        if(isLeafNode()) {
            ((SSFPhrase) prnt).removeChild(ind);
        }
        else
        {
            ((SSFPhrase) prnt).addChildrenAt(((SSFPhrase) this).getAllChildren(), ind);
            this.removeNode();
        }

        return ind;
    }

    public void removeAttribute(String aname)
    {
        if(fs != null && fs.countAltFSValues() > 0)
        {
            FeatureStructure tfs = fs.getAltFSValue(0);
            tfs.removeAttribute(aname);
        }
    }

    public void hideAttribute(String aname)
    {
        if(fs != null && fs.countAltFSValues() > 0) {
            fs.hideAttribute(aname);
        }
    }

    public void unhideAttribute(String aname)
    {
        if(fs != null && fs.countAltFSValues() > 0) {
            fs.unhideAttribute(aname);
        }
    }

    public SSFNode getPrevious()
    {
//        return ((SSFPhrase) getParent()).getPrevious(this);
        return (SSFNode) getPreviousSibling();
    }

    public SSFNode getNext()
    {
//        return ((SSFPhrase) getParent()).getNext(this);
        return (SSFNode) getNextSibling();
    }

    public SSFNode getNodeForId(String id)
    {
        if(this.id.equalsIgnoreCase(id)) {
            return this;
        }
        
        return null;
    }

    public static SSFProperties getSSFProperties()
    {
	if(ssfProps == null) {
            loadSSFProperties();
        }
	
        return ssfProps;
    }