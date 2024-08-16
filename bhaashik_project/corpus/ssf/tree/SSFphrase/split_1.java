

    public SSFNode findNodeByName(String n)
    {
        String nodeName = getAttributeValue("name");

        if(nodeName != null && nodeName.equals(n)) {
            return this;
        }

        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            if(node instanceof SSFLexItem)
            {
                nodeName = node.getAttributeValue("name");

                if(nodeName != null && nodeName.equals(n)) {
                    return node;
                }
            }
            else if (node instanceof SSFPhrase) {
                return ((SSFPhrase) node).findNodeByName(n);
            }
        }

        return null;
    }

    public int findNodeIndexByName(String n)
    {
        String nodeName = getAttributeValue("name");

        if(nodeName != null && nodeName.equals(n))
        {
            SSFPhrase prnt = (SSFPhrase) getParent();

            if(prnt == null) {
                return 0;
            }
            else {
                return prnt.findChild(this);
            }
        }

        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            if(node instanceof SSFLexItem)
            {
                nodeName = node.getAttributeValue("name");

                if(nodeName != null && nodeName.equals(n)) {
                    return i;
                }
            }
            else if (node instanceof SSFPhrase) {
                return ((SSFPhrase) node).findNodeIndexByName(n);
            }
        }

        return -1;
    }

    public List<SSFNode> getChildren(int from, int count)
    {
        List<SSFNode> ret = new ArrayList<SSFNode>(count);

        for (int i = 0; i < count; i++)
        {
            ret.add(getChild(from + i));
        }

        return ret;
    }

    public List<SSFNode> getAllChildren()
    {
        List<SSFNode> ret = new ArrayList<SSFNode>();

        ret.addAll(getChildren(0, countChildren()));
        
        return ret;
    }

    public void modifyChild(SSFNode c, int index)
    {
        insert(c, index);
        remove(index + 1);
    }

    public SSFNode removeChild(int index)
    {
        SSFNode rem = getChild(index);
        remove(index);

        return rem;
    }

    public void removeChildren(int index, int count)
    {
        for (int i = 0; i < count; i++)
        {
            removeChild(index);
        }
    }

    @Override
    public void removeAllChildren()
    {
        removeChildren(0, getChildCount());
    }

    @Override
    public void removeAttribute(String aname)
    {
        if(fs != null && fs.countAltFSValues() > 0)
        {
            FeatureStructure tfs = fs.getAltFSValue(0);
            tfs.removeAttribute(aname);
        }

        int count = getChildCount();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);
            child.removeAttribute(aname);
        }
    }

    @Override
    public void hideAttribute(String aname)
    {
        super.hideAttribute(aname);

        int count = getChildCount();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);
            child.hideAttribute(aname);
        }
    }

    @Override
    public void unhideAttribute(String aname)
    {
        super.unhideAttribute(aname);

        int count = getChildCount();

        for (int i = 0; i < count; i++)
        {
            SSFNode child = getChild(i);
            child.unhideAttribute(aname);
        }
    }

    @Override
    public void removeEmptyPhrases()
    {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node instanceof SSFPhrase)
            {
                if (node.countChildren() == 0)
                {
                    removeChild(i--);
                    count--;
                }

                node.removeEmptyPhrases();
            }
        }
    }

    @Override
    public void removeNonChunkPhrases()
    {
        if(isDSRedundantPhrase()) {
            removeLayer();
        }

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) getChild(i);
            node.removeDSRedundantPhrases();
        }
    }

    @Override
    public boolean isNonChunkPhrase()
    {
        if(getDepth() > 1) {
            return true;
        }

        return false;
    }

    @Override
    public void removeDSRedundantPhrases()
    {
        int count = getChildCount();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node instanceof SSFPhrase)
            {
                if (node.isDSRedundantPhrase())
                {
                    removeChild(i--);
                    count--;
                }

                node.removeDSRedundantPhrases();
            }
        }
    }

    @Override
    public boolean isDSRedundantPhrase()
    {
        String drelAttribs[] = FSProperties.getDependencyTreeAttributes();

        boolean hasDSRedundantPhrases = true;

        int count = countChildren();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) getChild(i);

            if(node.getOneOfAttributeValues(drelAttribs) == null)
            {
                hasDSRedundantPhrases = false;
                break;
            }
        }

        return hasDSRedundantPhrases;
    }

    @Override
    public boolean isLeafNode()
    {
        return false;
    }

    public SSFNode getPrevious(SSFNode child)
    {
        return (SSFNode) getChildBefore(child);
    }

    public SSFNode getNext(SSFNode child)
    {
        return (SSFNode) getChildAfter(child);
    }

    public boolean hasLexItemChild()
    {
        int ccount = countChildren();

        for (int i = 0; i < ccount; i++)
        {
            if (getChild(i) instanceof SSFLexItem)
            {
                return true;
            }
        }

        return false;
    }

    public void concat(SSFPhrase ph)
    {
        int count = ph.countChildren();
        for (int i = 0; i < count; i++)
        {
            SSFNode n = ph.removeChild(0);
            addChild(n);
        }
//
//	Vector chvec = getAllChildren();
//	Vector nextChvec = ph.getAllChildren();
//
//	ph.removeAllChildren();
//	addChildren(nextChvec);
    }

    public SSFPhrase splitPhrase(int childIndex) throws Exception
    {
        SSFPhrase splitNode;
        
        splitNode = (SSFPhrase) getCopy();

        int count = getChildCount();
        for (int i = childIndex; i < count; i++)
        {
            removeChild(childIndex);
        }

        for (int i = 0; i < childIndex; i++)
        {
            splitNode.removeChild(0);
        }

        return splitNode;
    }

// Moved to BhaashikMutableTreeNode
//    public Vector getAllLeaves() // &get_leaves( [$tree] )  -> @leaf_nodes;
    @Override
    public SSFNode getNodeForId(String id)
    {
        if (this.id.equalsIgnoreCase(id))
        {
            return this;
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

            if (node.isLeafNode() && node.getId().equalsIgnoreCase(id))
            {
                return node;
            } else
            {
                SSFNode ret = ((SSFNode) getChild(i)).getNodeForId(id);

                if (ret != null)
                {
                    return ret;
                }
            }
        }

        return null;
    }

    public List<SSFNode> getNodesForName(String n)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        Pattern p = Pattern.compile(n);
//        Pattern p = Pattern.compile(n, Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES);

        Matcher m = p.matcher(getName());

        if (m.find()) {
            nodes.add(this);
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);
    	    m = p.matcher(node.getName());

            if (m.find())
            {
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChild(i)).getNodesForName(n));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> getNodesForLexData(String ld)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        Pattern p = Pattern.compile(ld);
//        Pattern p = Pattern.compile(ld, Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES);

        Matcher m = p.matcher(getLexData());

        if (m.find()) {
            nodes.add(this);
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = getChild(i);

    	    m = p.matcher(node.getLexData());

            if (m.find())
            {
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChild(i)).getNodesForLexData(ld));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }