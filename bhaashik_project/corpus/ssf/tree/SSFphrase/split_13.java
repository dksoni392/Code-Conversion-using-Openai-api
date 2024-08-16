

    @Override
    public void readGATEXML(Element domElement) {
        XMLProperties xmlProperties = getXMLProperties();

        super.readGATEXML(domElement);

        NamedNodeMap domAttribs = domElement.getAttributes();

        int acount = domAttribs.getLength();

        for (int i = 0; i < acount; i++)
        {
            Node node = domAttribs.item(i);
            String name = node.getNodeName();
            String value = node.getNodeValue();

            if(name != null)
            {
                if(name.equals("text"))
                {
                    setLexData(value);
                }
                else if(value != null) {
                    setAttributeValue(name, value);
                }
                else {
                    setAttributeValue(name, "");
                }
            }
        }

        Node node = domElement.getFirstChild();

        while(node != null)
        {
            if(node instanceof Element)
            {
                Element element = (Element) node;

                String tag = xmlProperties.getProperties().getPropertyValue("tcMorphemeTag");

                if(element.getTagName().equals(tag))
                {
                    SSFNode child;

                    try {
                        child = new SSFLexItem("0", "", "NN", "");

                        child.readGATEXML(element);

                        addChild(child);
                    } catch (Exception ex) {
                        Logger.getLogger(SSFLexItem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                tag = xmlProperties.getProperties().getPropertyValue("tcPOSTag");

                if(element.getTagName().equals(tag))
                {
                    setName(element.getTextContent().trim());
                }
            }

            node = node.getNextSibling();
        }
    }

    @Override
    public void printXML(PrintStream ps) {
        ps.println(getXML());
    }

    @Override
    public void printTypeCraftXML(PrintStream ps) {
        ps.println(getTypeCraftXML());
    }

    @Override
    public void printGATEXML(PrintStream ps) {
        ps.println(getGATEXML());
    }

    public static void main(String[] args)
    {
        SSFPhrase node = new SSFPhrase();
        FSProperties fsp = new FSProperties();
        SSFProperties ssfp = new SSFProperties();

        FeatureStructuresImpl.setFSProperties(fsp);
        setSSFProperties(ssfp);

        System.out.println(GlobalProperties.getIntlString("Testing_SSFPhrase..."));

        try
        {
            fsp.read(GlobalProperties.resolveRelativePath("props/fs-mandatory-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/fs-other-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/fs-props.txt"),
                    GlobalProperties.resolveRelativePath("props/ps-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/dep-attribs.txt"),
                    GlobalProperties.resolveRelativePath("props/sem-attribs.txt"),
                    GlobalProperties.getIntlString("UTF-8")); //throws java.io.FileNotFoundException;
            ssfp.read(GlobalProperties.resolveRelativePath("props/ssf-props.txt"), GlobalProperties.getIntlString("UTF-8")); //throws java.io.FileNotFoundException;
            node.readFile("/home/anil/tmp/ssf-sentence-1.txt", GlobalProperties.getIntlString("UTF-8"));
            System.out.println(node.makeString());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
