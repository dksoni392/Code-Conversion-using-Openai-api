package bhaashik.corpus.ssf.tree;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.FeatureAttribute;
import bhaashik.corpus.ssf.features.FeatureStructure;
import bhaashik.corpus.ssf.features.FeatureStructures;
import bhaashik.corpus.ssf.features.FeatureValue;
import bhaashik.corpus.ssf.query.SSFQuery;
import edu.stanford.nlp.util.HashIndex;
import edu.stanford.nlp.util.Index;
import java.awt.Color;
import java.awt.Stroke;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.MutableTreeNode;

import bhaashik.corpus.ssf.SSFCorpus;
import bhaashik.corpus.ssf.SSFProperties;
import bhaashik.corpus.ssf.SSFSentence;
import bhaashik.table.gui.BhaashikJTable;
import bhaashik.tree.BhaashikEdge;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.util.query.FindReplace;
import bhaashik.util.query.FindReplaceOptions;
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
import bhaashik.gui.common.BhaashikLanguages;
import bhaashik.properties.KeyValueProperties;
import bhaashik.util.UtilityFunctions;

public class SSFNode extends BhaashikMutableTreeNode
        implements MutableTreeNode, Alignable, Serializable, QueryValue,
        BhaashikDOMElement, TypeCraftDOMElement, GATEDOMElement
{
    private static SSFProperties ssfProps;
    private static XMLProperties xmlProps;

    protected String id; // e.g., 5.1
//    protected String lexdata; // e.g., fair
    protected List<Integer> tagIndices;
    protected List<Integer> lexIndices;

    protected List<Integer> commentIndices;

    protected FeatureStructures fs; // Corresponds to stringFS

    protected AlignmentUnit<SSFNode> alignmentUnit;
    
    static Index<String> vocabIndex = new HashIndex<String>();    
    static Index<String> tagIndex = new HashIndex<String>();    
    static Index<String> commentIndex = new HashIndex<String>();    
    
    public static String WORD_SEPARATOR = " ";
    public static String TAG_SEPARATOR = "__";
    public static String COMMENT_SEPARATOR = " ";

    protected boolean nestedFS;

    protected boolean isTriangle;

    protected long flags = 0;

    public static final int HIGHLIGHTED = 0x00000000001;

    public static final String HIGHLIGHT = "hlt";

    public SSFNode() {
        super();

        this.id = "";
        this.lexIndices = new ArrayList<Integer>();
        this.tagIndices = new ArrayList<Integer>();
        this.commentIndices = new ArrayList<Integer>();
        this.fs = null;
	
    	nestedFS = false;
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }

    public SSFNode(Object userObject) {
        super(userObject);

        this.id = "";
        this.lexIndices = new ArrayList<Integer>();
        this.tagIndices = new ArrayList<Integer>();
        this.commentIndices = new ArrayList<Integer>();
        this.fs = null;
	
        nestedFS = false;
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }
    
    public SSFNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);

        this.id = "";
        this.lexIndices = new ArrayList<Integer>();
        this.tagIndices = new ArrayList<Integer>();
        this.commentIndices = new ArrayList<Integer>();
        this.fs = null;
	
    	nestedFS = false;
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }

    public SSFNode(String id, String lexdata, String name, String stringFS) throws Exception
    {
        super();

        this.id = id;
        this.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, true);
        this.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, true);
        this.commentIndices = new ArrayList<Integer>();
        this.fs = new FeatureStructuresImpl();

        fs.readString(stringFS);
	
    	nestedFS = false;
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }

    public SSFNode(String id, String lexdata, String name, FeatureStructures fs)
    {
        super();

        this.id = id;
        this.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, true);
        this.commentIndices = new ArrayList<Integer>();
        this.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, true);
        this.fs = fs;
	
    	nestedFS = false;
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }

    public SSFNode(String id, String lexdata, String name, String stringFS, Object userObject) throws Exception
    {
        super();

        this.id = id;
        this.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, true);
        this.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, true);
        this.commentIndices = new ArrayList<Integer>();
        this.fs = new FeatureStructuresImpl();
        fs.readString(stringFS);

    	nestedFS = false;

//        setUserObject(userObject);
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }

    public SSFNode(String id, String lexdata, String name, FeatureStructures fs, Object userObject)
    {
        super();

        this.id = id;
        this.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, true);
        this.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, true);
        this.commentIndices = new ArrayList<Integer>();
        this.fs = fs;

    	nestedFS = false;

//        setUserObject(userObject);
        
        alignmentUnit = new AlignmentUnit<SSFNode>();
    }

    public String getId() 
    {
        return id;
    }

    public void setId(String i) 
    {
        id = i;
    }

    public String getLexData() 
    {
        return getString(lexIndices, vocabIndex, WORD_SEPARATOR);
    }

    public void setLexData(String ld) 
    {
        lexIndices = getIndices(ld, vocabIndex, WORD_SEPARATOR, true);
    }

    public String getName() 
    {
        return getString(tagIndices, tagIndex, TAG_SEPARATOR);
    }

    public void setName(String n) 
    {
        tagIndices = getIndices(n, tagIndex, TAG_SEPARATOR, true);
    }

    public String getComment() 
    {
        return getString(commentIndices, commentIndex, COMMENT_SEPARATOR);
    }

    public void setComment(String c) 
    {
        commentIndices = getIndices(c, commentIndex, COMMENT_SEPARATOR, true);
    }

    public long getVocabularySize()
    {
        long vocabularySize = vocabIndex.size();
        
        return vocabularySize;
    }

    public long getTagVocabularySize()
    {
        long vocabularySize = tagIndex.size();
        
        return vocabularySize;
    }

    public long getCommentVocabularySize()
    {
        long vocabularySize = commentIndex.size();
        
        return vocabularySize;
    }
    
    public static List<Integer> getIndices(String wds, Index<String> index, String sep, boolean add)
    {
        String parts[] = wds.split(sep);
        
        List<Integer> indices = new ArrayList<Integer>(parts.length);
        
        for (int i = 0; i < parts.length; i++) {
            int wi = index.indexOf(parts[i], add);
            
            indices.add(wi);
        }
        
        return indices;
    }

    public static String getString(List<Integer> wdIndices, Index<String> index, String sep)
    {
        String str = "";
        
        int i = 0;
        for (Integer wi : wdIndices) {
            if(i == 0)
            {
                str = index.get(wi);
            }
            else
            {
                str += sep + index.get(wi);
            }
            
            i++;
        }
        
        return str;
    }

    public boolean isTriangle()
    {
        return isTriangle;
    }

    public void isTriangle(boolean isTriangle)
    {
        this.isTriangle = isTriangle;
    }

    @Override
    public AlignmentUnit getAlignmentUnit()
    {
        return alignmentUnit;
    }

    @Override
    public void setAlignmentUnit(AlignmentUnit alignmentUnit)
    {
        alignmentUnit.setAlignmentObject(this);
        this.alignmentUnit = alignmentUnit;
    }

    @Override
    public SSFNode getAlignedObject(String alignmentKey)
    {
        return alignmentUnit.getAlignedObject(alignmentKey);
    }