

    public List<SSFNode> replaceNames(String n, String replace)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        Pattern p = Pattern.compile(n);
//        Pattern p = Pattern.compile(n, Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES);

        Matcher m = p.matcher(getName());

        if(m.find())
        {
            setName(replace);
            nodes.add(this);
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) getChildAt(i);
    	    m = p.matcher(node.getName());

            if (m.find())
            {
                node.setName(replace);
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChildAt(i)).replaceNames(n, replace));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> replaceLexData(String ld, String replace)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        Pattern p = Pattern.compile(ld);
//        Pattern p = Pattern.compile(ld, Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.UNIX_LINES);

        Matcher m = p.matcher(getLexData());

        if(m.find())
        {
            setLexData(replace);
            nodes.add(this);
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) getChildAt(i);

    	    m = p.matcher(node.getLexData());

            if (m.find())
            {
                node.setLexData(replace);
                nodes.add(node);
            }

            if (node.isLeafNode() == false)
            {
                nodes.addAll(((SSFPhrase) getChildAt(i)).replaceLexData(ld, replace));
            }
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public static List<SSFNode> replaceAttribVal(SSFNode node, String attrib, String val, String attribReplace, String valReplace, boolean createAttrib)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();
        
        FeatureStructures fss = node.getFeatureStructures();

        if (fss != null && fss.countAltFSValues() > 0)
        {
            FeatureStructure ifs = fss.getAltFSValue(0);

//            FeatureAttribute fa = ifs.getAttribute(attrib);

            FeatureAttribute fa = ifs.searchAttributeValue(attrib, val, false);

            if(fa != null)
            {
                if(FeatureStructuresImpl.getFSProperties().isMandatory(UtilityFunctions.backFromExactMatchRegex(attrib)) == false)
                {
                    if(valReplace.equals("")) {
                        ifs.removeAttribute(UtilityFunctions.backFromExactMatchRegex(attrib));
                    }
                }
            }
            else if(createAttrib || fa == null)
            {
                if(FeatureStructuresImpl.getFSProperties().isMandatory(UtilityFunctions.backFromExactMatchRegex(attrib)))
                {
                    if(ifs.hasMandatoryAttribs() == false) {
                        ifs.addMandatoryAttributes();
                    }
                }
                else
                {
                    fa = new FeatureAttributeImpl();
                    fa.setName(attribReplace);

                    FeatureValue fv = new FeatureValueImpl();
                    fv.setValue("");

                    fa.addAltValue(fv);

                    ifs.addAttribute(fa);
                }
            }

            ifs.replaceAttributeValues(attrib, val, attribReplace, valReplace);
            nodes.add(node);
        }
        else if(createAttrib)
        {
            fss = new FeatureStructuresImpl();
            FeatureStructure ifs = new FeatureStructureImpl();

            FeatureAttribute fa = new FeatureAttributeImpl();
            fa.setName(attribReplace);

            FeatureValue fv = new FeatureValueImpl();
            fv.setValue(valReplace);

            fa.addAltValue(fv);

            ifs.addAttribute(fa);

            fss.addAltFSValue(ifs);

            node.setFeatureStructures(fss);
            nodes.add(node);
        }

//        if (node.isLeafNode() == false)
//        {
//            nodes.addAll(((SSFPhrase) node).replaceAttribVal(attrib, val, attribReplace, valReplace, createAttrib));
//        }

//        nodes = (Vector) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public List<SSFNode> replaceAttribVal(String attrib, String val, String attribReplace, String valReplace, boolean createAttrib)
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        FeatureStructures fss = getFeatureStructures();
        FeatureStructure lfs = null;

        if (createAttrib)
        {
            if(fss == null)
            {
                fss = new FeatureStructuresImpl();
                setFeatureStructures(fss);
            }

            if(fss.countAltFSValues() == 0)
            {
                lfs = new FeatureStructureImpl();
                fss.addAltFSValue(lfs);
            }
        }

        if(fss != null && fss.countAltFSValues() > 0)
        {
            lfs = fss.getAltFSValue(0);

            if(createAttrib || lfs.searchAttributeValue(attrib, val, false) != null)
            {
                replaceAttribVal(this, attrib, val, attribReplace, valReplace, createAttrib);
                nodes.add(this);
            }
        }

//        int count = getChildCount();
//        for (int i = 0; i < count; i++)
//        {
//            SSFNode node = (SSFNode) getChildAt(i);
//
//            nodes.addAll(replaceAttribVal(node, attrib, val, attribReplace, valReplace, createAttrib));
//        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public LinkedHashMap<QueryValue, String> getMatchingValues(SSFQuery ssfQuery)
    {
        LinkedHashMap<QueryValue, String> matches = new LinkedHashMap<QueryValue, String>();

        try
        {
            LinkedHashMap<SSFNode, String> qmatches = ssfQuery.executeQuery(this);

            if(qmatches != null && qmatches.size() > 0)
            {
                matches.putAll(qmatches);
            }
        } catch (Exception ex)
        {
//            System.err.println("Error in processing the SSF query.");
//            Logger.getLogger(SSFNode.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(this instanceof SSFPhrase)
        {
            SSFPhrase phrase = (SSFPhrase) this;

            List<SSFNode> allChildren = phrase.getAllChildren();

            int lcount = allChildren.size();

            for (int j = 0; j < lcount; j++)
            {
                SSFNode childNode = allChildren.get(j);

                matches.putAll(childNode.getMatchingValues(ssfQuery));
            }
        }

        return matches;
    }

    @Override
    public Object getQueryReturnValue()
    {
        return this;
    }

    @Override
    public void setQueryReturnValue(Object rv)
    {

    }

    @Override
    public Object getQueryReturnObject()
    {
        return this;
    }

    @Override
    public void setQueryReturnObject(Object rv)
    {

    }

    public static String getPOSTagsPath(String dirPath, String langEnc)
    {
        return getPropertiesPath("POSTags", dirPath, langEnc);
    }

    public static String getPhraseNamesPath(String dirPath, String langEnc)
    {
        return getPropertiesPath("PhraseNames", dirPath, langEnc);
    }

    public static String getPOSTagLevelsMapPath(String dirPath, String langEnc)
    {
        return getPropertiesPath("POSTagsLevels", dirPath, langEnc);
    }

    public static String getPhraseNamesLevelsMapPath(String dirPath, String langEnc)
    {
        return getPropertiesPath("PhraseNamesLevels", dirPath, langEnc);
    }

    public static String getPropertiesPath(String ptype, String dirPath, String langEnc)
    {
        boolean isRelativePath = GlobalProperties.isRelativePath(dirPath);

        File dpath = new File(dirPath);

        String pathPrefix = dpath.getAbsolutePath().substring(0, (int) (dpath.getAbsolutePath().length() - dirPath.length()));

        String fname = "pos-tags";

        if(ptype.equals("POSTags"))
        {
            fname = "pos-tags";
        }
        else if(ptype.equals("PhraseNames"))
        {
            fname = "phrase-names";
        }
        else if(ptype.equals("POSTagsLevels"))
        {
            fname = "pos-tags-levels";
        }
        else if(ptype.equals("PhraseNamesLevels"))
        {
            fname = "phrase-names-levels";
        }

        String ext = ".txt";
        String lang = BhaashikLanguages.getLanguageCodeFromLECode(langEnc);

        File pfile = new File(dirPath, fname + "-" + lang + ext);

        String rpath = "";

        if(pfile.canRead())
        {
            rpath = pfile.getAbsolutePath();
        }
        else
        {
            pfile = new File(dirPath, fname + ext);
            rpath = pfile.getAbsolutePath();
        }

        if(isRelativePath)
        {
            rpath = rpath.substring(pathPrefix.length(), rpath.length());
        }

        return rpath;
    }