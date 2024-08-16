
    
    public String convertToBracketFormHTML(int spaces)
    {
	String bracketFormHTML = "<html><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><font color=#0000ff>"
		+ convertToBracketForm(spaces) + "</font></html>";
	
	return bracketFormHTML;
    }

    @Override
    public BhaashikMutableTreeNode getCopy() throws Exception
    {
        String str = makeString();
        
        SSFNode ssfNode = new SSFNode();
        ssfNode.readString(str);

        ssfNode.flags = flags;

        return ssfNode;
    }

    public void copyExtraData(SSFNode node)
    {
        int count = countChildren();
        int ncount = node.countChildren();

        if(count != ncount) {
            return;
        }

        flags = node.flags;

        for (int i = 0; i < count; i++)
        {
            ((SSFNode) getChildAt(i)).copyExtraData((SSFNode) node.getChildAt(i));
        }
    }
    
    public boolean allowsNestedFS()
    {
	return nestedFS;
    }
    
    public void allowNestedFS(boolean b)
    {
	nestedFS = b;
    }

    public void clear()
    {
        id = "";
        lexIndices = new ArrayList<Integer>();
        tagIndices = new ArrayList<Integer>();
        fs = null;
    }

    // Add hoc
    public void addDefaultAttributes()
    {
	FeatureStructureImpl lfs = (FeatureStructureImpl) getFeatureStructures().getAltFSValue(0);
	
	if(getName().equals("VG"))
	{
	    FeatureAttributeImpl fa = new FeatureAttributeImpl();
	    fa.setName("name");
	    fa.addAltValue(new FeatureValueImpl(""));
	    lfs.addAttribute(fa);
	}
	else if(getName().equals("NP") || getName().equals("PP") || getName().equals("JJP"))
	{
	    FeatureAttributeImpl fa = new FeatureAttributeImpl();
	    fa.setName("drel");
	    fa.addAltValue(new FeatureValueImpl(""));
	    lfs.addAttribute(fa);
	}
    }

    public void clearFeatureStructures()
    {
	int count = countChildren();
	
        for (int i = 0; i < count; i++ )
        {
            SSFNode node = (SSFNode) getChildAt(i);
	    
	    FeatureStructures fss = node.getFeatureStructures();
	    
	    if(fss != null) {
                fss.setToEmpty();
            }
	    else
	    {
		fss = new FeatureStructuresImpl();
		fss.setToEmpty();
		node.setFeatureStructures(fss);
	    }
	    
	    node.clearFeatureStructures();
        }
    }

    public void clearAnnotation(long annoLevelFlags)
    {
	int count = countChildren();

	if(
	    ((this instanceof SSFLexItem) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.POS_TAGS))
		|| ((this instanceof SSFPhrase) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_NAMES))
	) {
            setName("");
        }

	if(getFeatureStructures() != null && fs.makeString().equals("") == false
	    && (
		((this instanceof SSFLexItem) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEXITEM_FEATURE_STRUCTURES))
		    || ((this instanceof SSFPhrase) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_FEATURE_STRUCTURES))
		    || ((this instanceof SSFLexItem) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES))
		    || ((this instanceof SSFPhrase) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES))
		    || ((this instanceof SSFLexItem) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES))
		    || ((this instanceof SSFPhrase) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES))
		    || ((this instanceof SSFNode) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.ALL_EXCEPT_THE_FIRST_FS))
		    || ((this instanceof SSFNode) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.PRUNE_THE_FS))
	    )
	)
    {
        if((this instanceof SSFLexItem) && (UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES))
                || (UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES)) )
        {
            long modifiedAnnotationFlags = UtilityFunctions.switchOffFlags(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES);
            modifiedAnnotationFlags = UtilityFunctions.switchOffFlags(modifiedAnnotationFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES);
    	    getFeatureStructures().clearAnnotation(modifiedAnnotationFlags, this);
        }
        else if((this instanceof SSFPhrase) && (UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES))
                || (UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES)) )
        {
            long modifiedAnnotationFlags = UtilityFunctions.switchOffFlags(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES);
            modifiedAnnotationFlags = UtilityFunctions.switchOffFlags(modifiedAnnotationFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES);
    	    getFeatureStructures().clearAnnotation(modifiedAnnotationFlags, this);
        }
        
        getFeatureStructures().clearAnnotation(annoLevelFlags, this);
    }

	if(UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.COMMENTS)) {
            setComment("");
        }
	
        for (int i = 0; i < count; i++ )
        {
            SSFNode node = (SSFNode) getChildAt(i);
	    node.clearAnnotation(annoLevelFlags);
        }

	// Inefficient, but will do for the time being
	if(
	    ((this instanceof SSFPhrase) && UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNKS))
		    && (getParent() == null || (getParent() != null && getParent().getParent() == null))
	) {
            ((SSFPhrase) this).flatten();
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof SSFNode)) {
            return false;
        }
        
        if(obj == null) {
            return false;
        }
        
	SSFNode nobj = (SSFNode) obj;

	if(getId().equals(nobj.getId()) == false) {
            return false;
        }

	if(getLexData().equals(nobj.getLexData()) == false) {
            return false;
        }

	if(getName().equalsIgnoreCase(nobj.getName()) == false) {
            return false;
        }

	if(getFeatureStructures() == null && nobj.getFeatureStructures() == null) {
            return true;
        }

	if(getFeatureStructures() == null || nobj.getFeatureStructures() == null) {
            return false;
        }

	if(getFeatureStructures().equals(nobj.getFeatureStructures()) == false) {
            return false;
        }

	return true;
    }
    
    @Override
    public void setValuesInTable(DefaultTableModel tbl, int mode)
    {
	if(rowIndex == -1 || tbl.getRowCount() <= 0 || tbl.getColumnCount() <= 0) {
            return;
        }
	
	if(getName().equals("")) {
            tbl.setValueAt(getLexData(), rowIndex, columnIndex);
        }
	else if(getLexData().equals("")) {
            tbl.setValueAt(getName(), rowIndex, columnIndex);
        }
	else {
            tbl.setValueAt(getLexData(), rowIndex, columnIndex);
        }
//	    tbl.setValueAt(getLexData() + ":" + getName(), rowIndex, columnIndex);
    }

    @Override
    public String toString()
    {
        return makeRawSentence();
    }
    
    public boolean matches(FindReplaceOptions findReplaceOptions)
    {
        boolean match = false;
        
        Pattern pattern = FindReplace.compilePattern(findReplaceOptions.findText, findReplaceOptions);
        Matcher matcher = null;
        
        String text = getLexData();
        
        if(text != null && text.equals("") == false)
        {        
            matcher = pattern.matcher(text);

            if(matcher.find()) {
                match = true;
            }
        }
        else {
            match = false;
        }

//        if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.tag != null
//                && findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.tag.equals("") == false)
//        {
//            pattern = FindReplace.compilePattern(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.tag, findReplaceOptions);
//
//            String tag = getName();
//
//            if(tag != null && tag.equals("") == false)
//            {
//                matcher = pattern.matcher(tag);
//
//                if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr1.equals("And"))
//                    match = match && matcher.find();
//                else
//                    match = match || matcher.find();
//            }
//            else
//            {
//                if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr1.equals("And"))
//                    match = match && false;
//                else
//                    match = match || false;
//            }
//        }
//
//        if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature != null
//                && findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature.equals("") == false)
//        {
//            if(getAttribute(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature) != null)
//            {
//                if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr2.equals("And"))
//                    match = match && true;
//                else
//                    match = match || true;
//            }
//            else
//            {
//                if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr2.equals("And"))
//                    match = match && false;
//                else
//                    match = match || false;
//            }
//        }
//
//        if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.featureValue != null
//                && findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.featureValue.equals("") == false)
//        {
//            pattern = FindReplace.compilePattern(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.featureValue, findReplaceOptions);
//            String val = (String) getAttributeValueString(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.feature);
//
//            if(val != null && val.equals("") == false)
//            {
//                matcher = pattern.matcher(val);
//
//                if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr3.equals("And"))
//                    match = match && matcher.find();
//                else
//                    match = match || matcher.find();
//            }
//            else
//            {
//                if(findReplaceOptions.resourceQueryOptions.syntacticCorpusQueryOptions.andOr3.equals("And"))
//                    match = match && false;
//                else
//                    match = match || false;
//            }
//        }

        if(match) {
            isHighlighted(true);
        }
        
        return match;
    }