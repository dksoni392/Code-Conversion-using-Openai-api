

    public static List<SSFNode> readNodesFromString(String string, List<String> errorLog /*Strings*/, int lineNum) throws Exception
    {
        SSFProperties ssfp = getSSFProperties();

        String fieldSeparatorRegex = ssfp.getProperties().getPropertyValue("fieldSeparatorRegex");
        String chunkStart = ssfp.getProperties().getPropertyValue("chunkStart");
        String chunkEnd = ssfp.getProperties().getPropertyValue("chunkEnd");
        String rootName = ssfp.getProperties().getPropertyValue("rootName");

        List<SSFNode> nodes = new ArrayList<SSFNode>();

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
                lineArray[i] = lineArray[i].trim();

                String fields[] = lineArray[i].split(fieldSeparatorRegex, 4);

//		if(fields.length <= 1 || fields[1] == null)
                if (lineArray[i].contains(chunkEnd) == false
                        && (fields == null || fields.length <= 1))
                {
                    if (errorLog != null)
                    {
                        errorLog.add(string + "\n");
                        errorLog.add("\nError_in_line_" + (lineNum + i) + ":\n");
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

                        throw new Exception("Error:_Second_SSF_field_null.");
                    }
                }

                if (fields.length > 1 && fields[1].equals(chunkStart) == true)
                {
                    level++;

                    if (level == 1)
                    {
                        parent = null;
                    }
                    else
                    {
                        if (phraseStack.isEmpty())
                        {
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

                                throw new Exception(GlobalProperties.getIntlString("Error:_Null_parent_for_SSFPhrase._Incorrect_format."));
                            }
                        } else
                        {
                            parent = (SSFPhrase) phraseStack.get(phraseStack.size() - 1);
                        }
                    }

                    node = new SSFPhrase("0", "", rootName, "");
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
                        fss.readString(fields[3]);
                        node.setFeatureStructures(fss);
                    }

                    if (level == 1)
                    {
                        nodes.add(node);
                    } else
                    {
                        ((SSFPhrase) parent).addChild(node);
                    }
                } else if (fields[0].equals(chunkEnd))
                {
                    level--;

                    if (phraseStack.isEmpty())
                    {
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

                            throw new Exception(GlobalProperties.getIntlString("Error:_Unmatching_ending_bracket._Incorrect_format."));
                        }
                    } else
                    {
                        phraseStack.remove(phraseStack.size() - 1);
                    }
                } else if (fields.length > 1 && lineArray[i].equals("") == false && fields[1].equals(chunkStart) == false && fields[0].equals(chunkEnd) == false) // lexical item
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

                                throw new Exception("Error:_Null_parent_for_a_LexicalItem._Incorrect_format.");
                            }
                        } else
                        {
                            parent = (SSFPhrase) phraseStack.get(phraseStack.size() - 1);
                        }
                    }

                    node = new SSFLexItem();
                    node.readString(lineArray[i]);

                    // If more than one words are joined in one lexical item, separate them
                    String ld = node.getLexData();
                    String ldwrds[] = ld.split("[ ]");

                    if (ldwrds.length > 1)
                    {
                        for (int j = 0; j < ldwrds.length; j++)
                        {
                            SSFNode node1;

                            if (node.getFeatureStructures() != null)
                            {
                                node1 = new SSFLexItem(node.getId(), ldwrds[j], node.getName(), node.getFeatureStructures().makeString());
                            } else
                            {
                                node1 = new SSFLexItem(node.getId(), ldwrds[j], node.getName(), "");
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
            if (errorLog != null)
            {
                errorLog.add("...Sentence_string:\n");
                errorLog.add(string + "\n");
                errorLog.add("Error:_Wrong_format._Unmatching_brackets.\n");
            } else
            {
                System.out.println("...Sentence_string:");
                System.out.println(string + "\n");

                throw new Exception(GlobalProperties.getIntlString("Error:_Wrong_format._Unmatching_brackets."));
            }
        }

        ((ArrayList) nodes).trimToSize();
        return nodes;
    }