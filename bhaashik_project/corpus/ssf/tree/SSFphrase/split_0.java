package bhaashik.corpus.ssf.tree;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.MutableTreeNode;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.FeatureAttribute;
import bhaashik.corpus.ssf.features.FeatureStructure;
import bhaashik.corpus.ssf.features.FeatureStructures;
import bhaashik.corpus.ssf.features.FeatureValue;
import bhaashik.corpus.ssf.SSFProperties;
import bhaashik.text.enc.conv.BhaashikEncodingConverter;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.util.query.FindReplace;
import bhaashik.util.query.FindReplaceOptions;
import bhaashik.xml.XMLUtils;
import bhaashik.xml.dom.GATEDOMElement;
import bhaashik.xml.dom.BhaashikDOMElement;
import bhaashik.xml.dom.TypeCraftDOMElement;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import bhaashik.corpus.parallel.Alignable;
import bhaashik.corpus.parallel.AlignmentUnit;
import bhaashik.corpus.ssf.features.impl.FSProperties;
import bhaashik.corpus.ssf.features.impl.FeatureAttributeImpl;
import bhaashik.corpus.ssf.features.impl.FeatureStructureImpl;
import bhaashik.corpus.ssf.features.impl.FeatureStructuresImpl;
import bhaashik.corpus.ssf.features.impl.FeatureValueImpl;
import bhaashik.corpus.ssf.query.QueryValue;
import bhaashik.corpus.xml.XMLProperties;
import bhaashik.util.UtilityFunctions;

public class SSFPhrase extends SSFNode
        implements MutableTreeNode, Alignable, Serializable, QueryValue,
        BhaashikDOMElement, TypeCraftDOMElement, GATEDOMElement
{

    // Children could be of type SSFPhrase or SSFLexicalItem

    public SSFPhrase()
    {
        super();
    }

    public SSFPhrase(Object userObject)
    {
        super(userObject);
    }

    public SSFPhrase(Object userObject, boolean allowsChildren)
    {
        super(userObject, allowsChildren);
    }

    public SSFPhrase(String id, String lexdata, String name, String stringFS) throws Exception
    {
        super(id, lexdata, name, stringFS);
    }

    public SSFPhrase(String id, String lexdata, String name, FeatureStructures fs)
    {
        super(id, lexdata, name, fs);
    }

    public SSFPhrase(String id, String lexdata, String name, String stringFS, Object userObject) throws Exception
    {
        super(id, lexdata, name, stringFS, userObject);
    }

    public SSFPhrase(String id, String lexdata, String name, FeatureStructures fs, Object userObject)
    {
        super(id, lexdata, name, fs, userObject);
    }

    @Override
    public int countChildren()
    {
        return getChildCount();
    }

    public int addChild(SSFNode c)
    {
        add((SSFNode) c);
        return getChildCount();
    }

    public int addChildren(Collection c)
    {
        Object ca[] = c.toArray();

        for (int i = 0; i < ca.length; i++)
        {
            add((SSFNode) ca[i]);
        }

        return getChildCount();
    }

    public int addChildAt(SSFNode c, int index)
    {
        insert(c, index);
        return getChildCount();
    }

    public int addChildrenAt(Collection c, int index)
    {
        Object ca[] = c.toArray();

        for (int i = 0; i < ca.length; i++)
        {
            insert((SSFNode) ca[i], index + i);
        }

        return getChildCount();
    }

    public SSFNode getChild(int index)
    {
        return (SSFNode) getChildAt(index);
    }

    public int findChild(SSFNode aChild)
    {
        // Needed because getIndex relies on equals(), but we need to check references

        if (aChild == null)
        {
            throw new IllegalArgumentException(GlobalProperties.getIntlString("argument_is_null"));
        }

        if (!isNodeChild(aChild))
        {
            return -1;
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            if (getChild(i) == aChild)
            {
                return i;
            }
        }

        return -1;
    }

    public SSFNode findLeafByID(String id)
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int count = leaves.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) leaves.get(i);

            if(node.getId().equals(id)) {
                return node;
            }
        }

        return null;
    }

    public int findLeafIndexByID(String id)
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int count = leaves.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) leaves.get(i);

            if(node.getId().equals(id)) {
                return i;
            }
        }

        return -1;
    }

    public SSFNode findChildByID(String id)
    {
        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            if(node.getId().equals(id)) {
                return node;
            }
        }

        return null;
    }

    public int findChildIndexByID(String id)
    {
        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            if(node.getId().equals(id)) {
                return i;
            }
        }

        return -1;
    }

    public SSFNode findNodeByID(String id)
    {
        if(getId().equals(id)) {
            return this;
        }

        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            if(node instanceof SSFLexItem)
            {
                if(node.getId().equals(id)) {
                    return node;
                }
            }
            else if (node instanceof SSFPhrase) {
                return ((SSFPhrase) node).findNodeByID(id);
            }
        }

        return null;
    }

    public int findNodeIndexByID(String id)
    {
        if(getId().equals(id))
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
                if(node.getId().equals(id)) {
                    return i;
                }
            }
            else if (node instanceof SSFPhrase) {
                return ((SSFPhrase) node).findNodeIndexByID(id);
            }
        }

        return -1;
    }

    public SSFNode findLeafByName(String n)
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int count = leaves.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) leaves.get(i);

            String nodeName = node.getAttributeValue("name");

            if(nodeName != null && nodeName.equals(n)) {
                return node;
            }
        }

        return null;
    }

    public int findLeafIndexByName(String n)
    {
        List<BhaashikMutableTreeNode> leaves = getAllLeaves();

        int count = leaves.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = (SSFNode) leaves.get(i);

            String nodeName = node.getAttributeValue("name");

            if(nodeName != null && nodeName.equals(n)) {
                return i;
            }
        }

        return -1;
    }

    public SSFNode findChildByName(String n)
    {
        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            String nodeName = node.getAttributeValue("name");

            if(nodeName != null && nodeName.equals(n)) {
                return node;
            }
        }

        return null;
    }

    public int findChildIndexByName(String n)
    {
        List<SSFNode> allChildren = getAllChildren();

        int count = allChildren.size();

        for (int i = 0; i < count; i++)
        {
            SSFNode node = allChildren.get(i);

            String nodeName = node.getAttributeValue("name");

            if(nodeName != null && nodeName.equals(n)) {
                return i;
            }
        }

        return -1;
    }