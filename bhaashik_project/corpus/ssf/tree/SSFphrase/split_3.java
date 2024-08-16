

    public List<SSFNode> getNodesForValue(int fieldnumber/* 1 to 4 */, String val)
    /* for the 4th field, otherwise null */ // &get_nodes( $fieldnumber , $value , [$tree] ) -> @required_nodes
    {
        List<SSFNode> nodes = new ArrayList<SSFNode>();

        switch (fieldnumber)
        {
            case 1:
                nodes.add(getNodeForId(val));
            case 2:
                nodes.addAll(getNodesForName(val));
            case 3:
                nodes.addAll(getNodesForLexData(val));
            case 4:
                nodes.addAll(getNodesForFS(val));
            default:
                nodes.add(getNodeForId(val));
        }

        nodes = (List) UtilityFunctions.getUnique(nodes);

        return nodes;
    }

    public int formPhrase(int fromChild, int count) throws Exception
    {
        int ret = -1;

        int childCount = getChildCount();

        if (fromChild >= 0 && fromChild < childCount && (fromChild + count) <= childCount)
        {
            SSFProperties ssfp = getSSFProperties();
            String chunkStart = ssfp.getProperties().getPropertyValue("chunkStart");

//            SSFPhrase ssfph = new SSFPhrase("0", chunkStart, "NP", "");
            SSFPhrase ssfph = new SSFPhrase("0", "", "NP", "");

            List<SSFNode> ch = getChildren(fromChild, count);
            insert(ssfph, fromChild);
            removeChildren(fromChild + 1, count);
            ssfph.addChildren(ch);

            ssfph.reallocateId(getId());

            ssfph.getFeatureStructures().setToEmpty();

            clearAlignments(fromChild, count);
        } else
        {
            return ret;
        }

        return ret;
    }

    public void clearAlignments(int fromChild, int count)
    {
        int childCount = getChildCount();

        if (fromChild >= 0 && fromChild < childCount && (fromChild + count) <= childCount)
        {
            for (int i = fromChild; i < fromChild + count; i++)
            {
                SSFNode node = getChild(i);

                AlignmentUnit aunit = node.getAlignmentUnit();

                if(aunit != null) {
                    node.getAlignmentUnit().clearAlignments();
                }
            }
        }
    }

    public void readFile(String f, String charset) throws Exception
    {
        // Perhaps not needed. Use the method in SSFSentenceImpl
//        BufferedReader lnReader = null;
//
//        if(!charset.equals(""))
//            lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
//        else
//            lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//
//        String line = "";
//        String lines = "";
//
//        while((line = lnReader.readLine()) != null)
//        {
//            if((!line.startsWith("#")) && line.equals("") == false)
//            {
//                lines += line + "\n";
//            }
//        }
//
//        readString(lines);
    }

    public int readString(String string, List<String> errorLog /*Strings*/, int lineNum) throws Exception
    {
        SSFProperties ssfp = getSSFProperties();

        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        List<SSFNode> nodes = SSFPhrase.readNodesFromString(string, errorLog, lineNum);

        if (nodes == null || nodes.size() < 1 || nodes.get(0) == null)
        {
            throw new Exception();
        }

        if (nodes != null && nodes.size() > 0)
        {
            if (nodes.size() == 1 && nodes.get(0).getClass().equals(SSFPhrase.class) && ((SSFNode) nodes.get(0)).getName().equals(rootName))
            {
                fillSSFData((SSFNode) nodes.get(0));
            } else
            {
                for (int j = 0; j < nodes.size(); j++)
                {
                    addChild(((SSFNode) nodes.get(j)));
                }
            }
        }

        removeEmptyPhrases();

        return getChildCount();
    }

    @Override
    public int readString(String string) throws Exception
    {
        return readString(string, null, 1);
    }

    public int readChunkedString(String string, List<String> errorLog /*Strings*/, int lineNum) throws Exception
    {
        SSFProperties ssfp = getSSFProperties();

        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        List<SSFNode> nodes = SSFPhrase.readNodesFromChunked(string, errorLog, lineNum);

        if (nodes == null || nodes.size() < 1 || nodes.get(0) == null)
        {
            throw new Exception();
        }

        if (nodes != null && nodes.size() > 0)
        {
            if (nodes.size() == 1 && nodes.get(0).getClass().equals(SSFPhrase.class) && ((SSFNode) nodes.get(0)).getName().equals(rootName))
            {
                fillSSFData((SSFNode) nodes.get(0));
            } else
            {
                for (int j = 0; j < nodes.size(); j++)
                {
                    addChild(((SSFNode) nodes.get(j)));
                }
            }
        }

        removeEmptyPhrases();

        return getChildCount();
    }

    public int readChunkedString(String string) throws Exception
    {
        return readChunkedString(string, null, 1);
    }

    @Override
    public void fillSSFData(SSFNode n)
    {
        if (n.isLeaf())
        {
            System.out.println("Error in SSFPhrase.fillSSFData: Leaf node given as arguement.");
            return;
        }
        if (n == null)
        {
            System.out.println("Error in SSFPhrase.fillSSFData: Null node given as arguement.");
            return;
        }

        super.fillSSFData(n);

        int count = n.getChildCount();
        while (count > 0)
        {
            add((SSFNode) n.getChildAt(0));
            count--;
        }
    }

    // Implementation parallel to the readNodesFromString method
    public static boolean validateSSF(String string, List<String> errorLog /*Strings*/, int lineNum)
    {
        boolean validated = true;

        SSFProperties ssfp = getSSFProperties();

        String fieldSeparatorRegex = ssfp.getProperties().getPropertyValue("fieldSeparatorRegex");
        String chunkStart = ssfp.getProperties().getPropertyValue("chunkStart");
        String chunkEnd = ssfp.getProperties().getPropertyValue("chunkEnd");
        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        List<SSFNode> nodes = new ArrayList();

        String lineArray[] = string.split("\n");

        // The first level to be treated differently
        int level = 0;

        List<SSFNode> phraseStack = new ArrayList<SSFNode>();

        SSFNode parent = null;
        SSFNode node = null;

        for (int i = 0; i < lineArray.length; i++)
        {
            if (lineArray[i].equals("") == false)
            {
                if (lineArray[i].contains(chunkEnd) == false)
                {
                    lineArray[i] = lineArray[i].trim();
                }

                String fields[] = lineArray[i].split(fieldSeparatorRegex, 4);

//		if(fields.length <= 1 || fields[1] == null)
                if (lineArray[i] != null && lineArray[i].equals("") == false && (fields == null || fields.length <= 1))
                {
                    validated = false;

                    if (errorLog != null)
                    {
                        errorLog.add(string + "\n");
                        errorLog.add("\nError in line " + (lineNum + i) + ":\n");
                        errorLog.add("********************\n");
                        errorLog.add(lineArray[i]);
                        errorLog.add("********************\n");
                        errorLog.add("Error:_Second_SSF_field_null.\n");
                    } else
                    {
                        System.out.println(string + "\n");
                        System.out.println("\nError_in_line_" + (lineNum + i) + ":");
                        System.out.println("********************");
                        System.out.println(lineArray[i]);
                        System.out.println("********************");
                    }
                }

                if (lineArray[i] == null || lineArray[i].equals("") || fields == null || fields.length <= 1)
                {
                    lineNum++;
                } else if (fields[1].equals(chunkStart) == true)
                {
                    level++;

                    if (level == 1)
                    {
                        parent = null;
                    } else
                    {
                        if (phraseStack.isEmpty())
                        {
                            validated = false;

                            if (errorLog != null)
                            {
                                errorLog.add(string + "\n");
                                errorLog.add("\nError_in_line:_" + (lineNum + i) + "\n");
                                errorLog.add("********************\n");
                                errorLog.add(lineArray[i]);
                                errorLog.add("********************\n");
                                errorLog.add("Error:_Null_parent_for_SSFPhrase._Incorrect_format.\n");
                            } else
                            {
                                System.out.println(string + "\n");
                                System.out.println("\nError_in_line_" + (lineNum + i) + ":");
                                System.out.println("********************");
                                System.out.println(lineArray[i]);
                                System.out.println("********************");
                            }
                        } else
                        {
                            parent = (SSFPhrase) phraseStack.get(phraseStack.size() - 1);
                        }
                    }

                    try
                    {
                        node = new SSFPhrase("0", "", rootName, "");
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                    phraseStack.add(node);

                    node.setId(fields[0]);
                    node.setLexData("");

                    if (fields.length > 2)
                    {
                        node.setName(fields[2]);
                    }

                    if (fields.length == 4 && fields[3].equals("") == false)
                    {
                        FeatureStructures fss = new FeatureStructuresImpl();

                        try
                        {
                            fss.readString(fields[3]);
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        node.setFeatureStructures(fss);
                    }

                    if (level == 1)
                    {
                        nodes.add(node);
                    } else
                    {
                        ((SSFPhrase) parent).addChild(node);
                    }
                } else if (fields[1].equals(chunkEnd))
                {
                    level--;

                    if (phraseStack.isEmpty())
                    {
                        validated = false;

                        if (errorLog != null)
                        {
                            errorLog.add(string + "\n");
                            errorLog.add("\nError_in_line_" + (lineNum + i) + ":\n");
                            errorLog.add("********************");
                            errorLog.add(lineArray[i]);
                            errorLog.add("********************\n");
                            errorLog.add("Error:_Unmatching_ending_bracket._Incorrect_format.\n");
                        } else
                        {
                            System.out.println(string + "\n");
                            System.out.println("\nError_in_line_" + (lineNum + i) + ":");
                            System.out.println("********************");
                            System.out.println(lineArray[i]);
                            System.out.println("********************");
                        }
                    } else
                    {
                        phraseStack.remove(phraseStack.size() - 1);
                    }
                } else if (fields.length > 1 && lineArray[i].equals("") == false && fields[1].equals(chunkStart) == false && fields[1].equals(chunkEnd) == false) // lexical item
                {
                    if (level == 0)
                    {
                        parent = null;
                    } else
                    {
                        if (phraseStack.isEmpty())
                        {
                            validated = false;

                            if (errorLog != null)
                            {
                                errorLog.add(string + "\n");
                                errorLog.add("\nError_in_line_" + (lineNum + i) + ":\n");
                                errorLog.add("********************\n");
                                errorLog.add(lineArray[i]);
                                errorLog.add("********************\n");
                                errorLog.add("Error:_Null_parent_for_a_LexicalItem._Incorrect_format.\n");
                            } else
                            {
                                System.out.println(string + "\n");
                                System.out.println("\nError_in_line_" + (lineNum + i) + ":");
                                System.out.println("********************");
                                System.out.println(lineArray[i]);
                                System.out.println("********************");
                            }
                        } else
                        {
                            parent = (SSFPhrase) phraseStack.get(phraseStack.size() - 1);
                        }
                    }

                    node = new SSFLexItem();

                    try
                    {
                        node.readString(lineArray[i]);
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                    // If more than one words are joined in one lexical item, separate them
                    String ld = node.getLexData();
                    String ldwrds[] = ld.split("[ ]");

                    if (ldwrds.length > 1)
                    {
                        for (int j = 0; j < ldwrds.length; j++)
                        {
                            SSFNode node1 = null;

                            try
                            {
                                if (node.getFeatureStructures() != null)
                                {
                                    node1 = new SSFLexItem(node.getId(), ldwrds[j], node.getName(), node.getFeatureStructures().makeString());
                                } else
                                {
                                    node1 = new SSFLexItem(node.getId(), ldwrds[j], node.getName(), "");
                                }
                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }

                            if (level == 0)
                            {
                                nodes.add(node1);
                            } else
                            {
                                ((SSFPhrase) parent).addChild(node1);
                            }
                        }
                    } else
                    {
                        if (level == 0)
                        {
                            nodes.add(node);
                        } else
                        {
                            ((SSFPhrase) parent).addChild(node);
                        }
                    }
                }
            } else
            {
                lineNum++;
            }
        }

        if (phraseStack.size() > 0)
        {
            validated = false;

            if (errorLog != null)
            {
                errorLog.add("...Sentence_string:\n");
                errorLog.add(string + "\n");
                errorLog.add("Error:_Wrong_format._Unmatching_brackets.\n");
            } else
            {
                System.out.println("...Sentence_string:");
                System.out.println(string + "\n");
            }
        }

        return validated;
    }