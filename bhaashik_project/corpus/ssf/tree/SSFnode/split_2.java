

    public static void setSSFProperties(SSFProperties ssfp)
    {
        ssfProps = ssfp;
    }

    public static void loadSSFProperties()
    {
	ssfProps = new SSFProperties();

	try {
		ssfProps.read(GlobalProperties.resolveRelativePath("props/ssf-props.txt"), GlobalProperties.getIntlString("UTF-8"));
	} catch (FileNotFoundException ex) {
		ex.printStackTrace();
	} catch (IOException ex) {
		ex.printStackTrace();
	}
    }

    public static XMLProperties getXMLProperties()
    {
	if(xmlProps == null) {
            loadXMLProperties();
        }

        return xmlProps;
    }

    public static void setXMLProperties(XMLProperties xmlp)
    {
        xmlProps = xmlp;
    }

    public static void loadXMLProperties()
    {
	xmlProps = new XMLProperties();

	try {
		xmlProps.read(GlobalProperties.resolveRelativePath("props/xml-props.txt"), GlobalProperties.getIntlString("UTF-8"));
	} catch (FileNotFoundException ex) {
		ex.printStackTrace();
	} catch (IOException ex) {
		ex.printStackTrace();
	}
    }

    public int readString(String s) throws Exception
    {
        String fieldSeparatorRegex = ssfProps.getProperties().getPropertyValue("fieldSeparatorRegex");
        String fields[] = s.split(fieldSeparatorRegex, 4);

        id = fields[0];
        lexIndices = getIndices(fields[1], vocabIndex, WORD_SEPARATOR, true);

	if(fields.length > 2) {
            tagIndices = getIndices(fields[2], tagIndex, TAG_SEPARATOR, true);
        }

        if(fields.length == 4 && fields[3].equals("") == false)
        {
            fs = new FeatureStructuresImpl();
            fs.readString(fields[3]);
        }

        return 0;
    }
    
    @Override
    public Object getUserObject()
    {
        if(this instanceof SSFPhrase) {
            return getName();
        }

        return getLexData();
    }

    public void print(PrintStream ps)
    {
        ps.print(makeString());
    }

    protected String makeTopString()
    {
        String toString;
//        String fieldSeparatorPrint = ssfProps.getProperties().getPropertyValueForPrint("fieldSeparatorPrint");
        String fieldSeparatorPrint = getSSFProperties().getProperties().getPropertyValueForPrint("fieldSeparatorPrint");

//        String chunkStart = ssfProps.getProperties().getPropertyValueForPrint("chunkStart");
        String chunkStart = getSSFProperties().getProperties().getPropertyValueForPrint("chunkStart");
        String ld = chunkStart;

        if(isLeafNode()) {
            ld = getLexData();
        }
        
        String name = getName();

        if (fs == null)
        {
            toString = (id + fieldSeparatorPrint + ld + fieldSeparatorPrint + name + fieldSeparatorPrint + "");
        }
        else
        {
            toString = (id + fieldSeparatorPrint + ld + fieldSeparatorPrint + name + fieldSeparatorPrint + fs.makeString());
        }

        return toString;
    }

    public String makeSummaryString()
    {
        String string = "";
        
        String lexdata = getLexData();

        if(lexdata.equals("") == false) {
            string = lexdata;
        }
        
        String name = getName();

        if(name.equals("") == false)
        {
            if(string.equals("") == false) {
                string += " : " + name;
            }
            else {
                string += name;
            }
        }

        if(fs != null && fs.makeString().equals("") == false)
        {
            if(string.equals("") == false) {
                string += " : " + fs.makeString();
            }
            else {
                string += fs.makeString();
            }
        }

        return string;
    }

    public void fillSSFData(SSFNode n)
    {
	id = n.id;
        setLexData(n.getLexData());
        setName(n.getName());
	fs = n.fs;
    }
    
    public void collapseLexicalItems()
    {
    }

    public void collapseLexicalItemsDeep()
    {
        
    }
    
    public String makeString()
    {
        return (makeTopString() + "\n");
    }
    
    public String makeRawSentence()
    {
	return getLexData();
    }

    public void convertToPOSNolex()
    {
        SSFProperties ssfp = SSFNode.getSSFProperties();

	String unknownTag = ssfp.getProperties().getPropertyValueForPrint("unknownTag");

        if(isLeafNode() == false)
	{
            List<BhaashikMutableTreeNode> leaves = getAllLeaves();

            for(int i = 0; i < leaves.size(); i++)
            {
                SSFNode lv = (SSFNode) leaves.get(i);

                if(lv.getName().equals("") == false) {
                    lv.setLexData(lv.getName());
                }
                else {
                    lv.setLexData(unknownTag);
                }
                
                lv.setName("");
            }
	}
        else
        {
            if(getName().equals("") == false) {
                setLexData(getName());
            }
            else {
                setLexData(unknownTag);
            }
        }       
    }
    
    public String makePOSNolex()
    {
        SSFProperties ssfp = SSFNode.getSSFProperties();

	String unknownTag = ssfp.getProperties().getPropertyValueForPrint("unknownTag");

        String posNolex = "";
	
	if(isLeafNode() == false)
	{
            List<BhaashikMutableTreeNode> leaves = getAllLeaves();

            for(int i = 0; i < leaves.size(); i++)
            {
                SSFNode lv = (SSFNode) leaves.get(i);

                if(lv.getName().equals("") == false) {
                    posNolex += lv.getName();
                }
                else {
                    posNolex += unknownTag;
                }

                if(i < leaves.size() - 1) {
                    posNolex += " ";
                }
                else {
                    posNolex += "\n";
                }
            }
	}
        else
        {
            if(getName().equals("") == false) {
                posNolex += getName();
            }
            else {
                posNolex += unknownTag;
            }
        }
	
	return posNolex;                        
    }
    
    public void convertToLowerCase()
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        for(int i = 0; i < leaves.size(); i++)
        {
            SSFNode lv = (SSFNode) leaves.get(i);
            
            lv.setLexData(lv.getLexData().toLowerCase());
        }        
    }

    public String convertToPOSTagged(String sep)
    {
        SSFProperties ssfp = SSFNode.getSSFProperties();

	String wordTagSeparator = ssfp.getProperties().getPropertyValueForPrint("wordTagSeparator");
        
        if(sep != null)
        {
            wordTagSeparator = sep;
        }
        
	String unknownTag = ssfp.getProperties().getPropertyValueForPrint("unknownTag");

        String posTagged = "";
	
	if(isLeafNode() == false)
	{
            List<BhaashikMutableTreeNode> leaves = getAllLeaves();

            for(int i = 0; i < leaves.size(); i++)
            {
                SSFNode lv = (SSFNode) leaves.get(i);

                if(lv.getName().equals("") == false) {
                    posTagged += lv.getLexData() + wordTagSeparator + lv.getName();
                }
                else {
                    posTagged += lv.getLexData() + wordTagSeparator + unknownTag;
                }

                if(i < leaves.size() - 1) {
                    posTagged += " ";
                }
                else {
                    posTagged += "\n";
                }
            }
	}
        else
        {
            if(getName().equals("") == false) {
                posTagged += getLexData() + wordTagSeparator + getName();
            }
            else {
                posTagged += getLexData() + wordTagSeparator + unknownTag;
            }
        }
	
	return posTagged;                
    }

    public String convertToPOSTagged()
    {
        SSFProperties ssfp = SSFNode.getSSFProperties();

        String wordTagSeparator = ssfp.getProperties().getPropertyValueForPrint("wordTagSeparator");
        
        return convertToPOSTagged(wordTagSeparator);
    }
    
    public String convertToBracketForm(int spaces)
    {
	String bracketForm = "";

	String rootName = ssfProps.getProperties().getPropertyValueForPrint("rootName");

	String bracketFormStart = ssfProps.getProperties().getPropertyValueForPrint("bracketFormStart");
	String bracketFormEnd = ssfProps.getProperties().getPropertyValueForPrint("bracketFormEnd");

	String wordTagSeparator = ssfProps.getProperties().getPropertyValueForPrint("wordTagSeparator");
	String unknownTag = ssfProps.getProperties().getPropertyValueForPrint("unknownTag");

//	try
//	{
//	    if(ssfProps != null)
//		rootName = new String(rootName.getBytes(), ssfProps.getProperties().getPropertyValue("encoding"));
//	} catch (UnsupportedEncodingException ex)
//	{
//	    ex.printStackTrace();
//	}
	    
	if(isLeafNode() == false)
	{
//	    if(getName().equals(rootName) == false)
	    bracketForm += bracketFormStart;

	    for(int j = 0; j < spaces; j++) {
                bracketForm += " ";
            }

	    int count = countChildren();
	    for (int i = 0; i < count; i++)
	    {
		SSFNode child = (SSFNode) getChildAt(i);
		
		bracketForm += child.convertToBracketForm(spaces);
		
		if(i < count - 1)
		{
		    for(int j = 0; j < spaces; j++) {
                        bracketForm += " ";
                    }
//		    bracketForm += " ";
		}
	    }

	    for(int j = 0; j < spaces; j++) {
                bracketForm += " ";
            }

//	    if(getName().equals(rootName) == false)
//	    {
	    if(getName().equals("") == false) {
                bracketForm += bracketFormEnd + wordTagSeparator + getName();
            }
//		bracketForm += bracketFormEnd + wordTagSeparator + getName() + " ";
	    else {
                bracketForm += bracketFormEnd + wordTagSeparator + unknownTag;
            }
//		bracketForm += bracketFormEnd + wordTagSeparator + unknownTag + " ";

//	    for(int i = 1; i < spaces; i++)
//		bracketForm += " ";
//	    }
	}
	else
	{
            if(getName().equals("")) {
                    bracketForm += getLexData();
                }
            else {
                    bracketForm += getLexData() + wordTagSeparator + getName();
                }
//	    bracketForm += getLexData();
	}
	
	return bracketForm;
    }