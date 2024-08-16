

    public static List<SSFNode> readNodesFromString(String string) throws Exception
    {
        return readNodesFromString(string, null, 1);
    }

    public static List<SSFNode> readNodesFromChunked(String string) throws Exception
    {
        return readNodesFromChunked(string, null, 1);
    }

    public static List<SSFNode> readNodesFromChunked(String string, List<String> errorLog /*Strings*/, int lineNum) throws Exception
    {
        SSFProperties ssfp = getSSFProperties();

        String bracketFormStart = ssfp.getProperties().getPropertyValue("bracketFormStart");
        String bracketFormEnd = ssfp.getProperties().getPropertyValue("bracketFormEnd");
        String wordTagSeparator = ssfp.getProperties().getPropertyValue("wordTagSeparator");
        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        List<SSFNode> nodes = new ArrayList<SSFNode>();

        if (string.contains(bracketFormEnd + wordTagSeparator + rootName))
        {
            string = string.replaceAll(bracketFormEnd + wordTagSeparator + rootName, "");
            string = string.substring(bracketFormStart.length());
            string = string.trim();
        }

        String parts[] = string.split(" ");

        // The first level to be treated differently
        int level = 0;

        List<SSFNode> phraseStack = new ArrayList<SSFNode>();

        SSFNode parent = null;
        SSFNode node;

        for (int i = 0; i < parts.length; i++)
        {
            if (parts[i].equals("") == false)
            {
                if (parts[i].equals(bracketFormStart) == true)
                {
                    level++;

                    if (level == 1)
                    {
                        parent = null;
                    } else
                    {
                        if (phraseStack.isEmpty())
                        {
                            if (errorLog != null)
                            {
                                errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                            } else
                            {
                                System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                            }
                        } else
                        {
                            parent = (SSFPhrase) phraseStack.get(phraseStack.size() - 1);
                        }
                    }

                    node = new SSFPhrase("0", "", rootName, "");
                    phraseStack.add(node);

                    node.setId("0");
                    node.setLexData("");

                    if (level == 1)
                    {
                        nodes.add(node);
                    } else
                    {
                        ((SSFPhrase) parent).addChild(node);
                    }
                } else if (parts[i].startsWith(bracketFormEnd))
                {
                    level--;

                    if (phraseStack.isEmpty())
                    {
                        if (errorLog != null)
                        {
                            errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                        } else
                        {
                            System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                        }
                    } else
                    {
                        String pparts[] = parts[i].split(wordTagSeparator);

                        if (pparts.length != 2)
                        {
                            if (errorLog != null)
                            {
                                errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                            } else
                            {
                                System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                            }
                        } else
                        {
                            if (parent != null)
                            {
                                parent.setName(pparts[1]);
                                phraseStack.remove(phraseStack.size() - 1);
                            } else
                            {
                                if (errorLog != null)
                                {
                                    errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                                } else
                                {
                                    System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                                }

                                phraseStack.remove(phraseStack.size() - 1);
                            }
                        }
                    }
                } else if (parts[i].contains(wordTagSeparator)) // lexical item
                {
                    if (level == 0)
                    {
                        parent = null;
                    } else
                    {
                        if (phraseStack.isEmpty())
                        {
                            if (errorLog != null)
                            {
                                errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                            } else
                            {
                                System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                            }
                        } else
                        {
                            parent = (SSFPhrase) phraseStack.get(phraseStack.size() - 1);
                        }
                    }

                    String pparts[] = parts[i].split(wordTagSeparator);

                    if (pparts.length != 2)
                    {
                        if (errorLog != null)
                        {
                            errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                        } else
                        {
                            System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                        }
                    } else
                    {
                        node = new SSFLexItem(GlobalProperties.getIntlString("0"), pparts[0], pparts[1], "");

                        if (level == 0)
                        {
                            nodes.add(node);
                        } else
                        {
                            ((SSFPhrase) parent).addChild(node);
                        }
                    }
                }
            }
        }

        if (phraseStack.size() > 0)
        {
            if (errorLog != null)
            {
                errorLog.add(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                errorLog.add(GlobalProperties.getIntlString("Error:_Wrong_format._Unmatching_brackets.\n"));
            } else
            {
                System.err.println(GlobalProperties.getIntlString("Error_in_the_bracket_form_file_(line-") + lineNum + "): " + string);
                System.out.println(string + "\n");
            }
        }

        ((ArrayList) nodes).trimToSize();
        return nodes;
    }

    @Override
    public String makeString()
    {
        SSFProperties ssfp = getSSFProperties();

        String fieldSeparatorPrint = ssfp.getProperties().getPropertyValueForPrint("fieldSeparatorPrint");
        String chunkEnd = ssfp.getProperties().getPropertyValueForPrint("chunkEnd");
    	String rootName = ssfp.getProperties().getPropertyValueForPrint("rootName");

        String string = "";

        if(getName().equals(rootName) == false) {
            string = makeTopString() + "\n";
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            string += getChild(i).makeString();
        }

        if(getName().equals(rootName) == false) {
            string += fieldSeparatorPrint + chunkEnd + "\n";
        }

        return string;
    }

    @Override
    public String makeRawSentence()
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();
        String rawsen = getLexData();

//        if(lexdata.equals("") == false)
//            lexdata += " > ";

        int count = leaves.size();
        for (int i = 0; i < count; i++)
        {
            if (i == count - 1)
            {
                rawsen += ((SSFNode) leaves.get(i)).getLexData();
            } else
            {
                rawsen += ((SSFNode) leaves.get(i)).getLexData() + " ";
            }
        }

        return rawsen;
    }