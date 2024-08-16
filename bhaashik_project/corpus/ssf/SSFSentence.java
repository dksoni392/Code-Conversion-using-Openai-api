/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.corpus.ssf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

import bhaashik.corpus.parallel.Alignable;
import bhaashik.corpus.ssf.features.FeatureStructure;
import bhaashik.corpus.ssf.query.QueryValue;
import bhaashik.corpus.ssf.query.SSFQuery;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.text.enc.conv.BhaashikEncodingConverter;
import bhaashik.common.types.CorpusType;
import bhaashik.corpus.Sentence;
import bhaashik.util.query.FindReplaceOptions;
import bhaashik.util.query.SyntacticCorpusContextQueryOptions;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;
import bhaashik.properties.KeyValueProperties;

/**
 *
 * @author anil
 */
public interface SSFSentence extends Alignable {

    void clear();

    void clearAlignments();

    void clearAnnotation(long annoLevelFlags);

    void clearFeatureStructures();

    void clearHighlights();

    void convertEncoding(BhaashikEncodingConverter encodingConverter, String nullWordString);

    String convertToBracketForm(int spaces);

    void convertToLowerCase();

    void convertToPOSNolex();

    String convertToPOSTagged(String sep);

    String convertToPOSTagged();

    String convertToRawText();

    void setAttributeValue(String attibName, String val);

    int countAttributeValuePairs();

    int countAttributeValues();

    int countAttributes();

    double countCharacters();

    int countChunkRelations();

    int countChunkTags();

    int countGroupRelations();

    int countPOSTags();

    int countUnchunkedWords();

    int countUntaggedChunks();

    int countUntaggedWords();

    int countWordTagPairs();

    int countWords();

    boolean emptyPhrasesAllowed();

    void emptyPhrasesAllowed(boolean e);

    List<SSFNode> find(String nlabel, String ntext, String attrib, String val, String nlabelReplace, String ntextReplace, String attribReplace, String valReplace, boolean replace, boolean createAttrib, boolean exactMatch);

    SSFNode findChildByID(String id);

    SSFNode findChildByName(String id);

    int findChildIndexByID(String id);

    int findChildIndexByName(String id);

    List<SSFNode> findContext(SyntacticCorpusContextQueryOptions contextOptions, boolean exactMatch);

    SSFNode findLeafByID(String id);

    SSFNode findLeafByName(String id);

    int findLeafIndexByID(String id);

    int findLeafIndexByName(String id);

    SSFNode findNodeByID(String id);

    SSFNode findNodeByName(String id);

    int findNodeIndexByID(String id);

    int findNodeIndexByName(String id);

    LinkedHashMap<String, Integer> getAttributeFreq();

    LinkedHashMap<String, Integer> getAttributeValueFreq();

    LinkedHashMap<String, Integer> getAttributeValuePairFreq();

    LinkedHashMap<String, Integer> getChunkRelationFreq();

    LinkedHashMap<String, Integer> getChunkTagFreq();

    Sentence getCopy() throws Exception;

    DOMElement getDOMElement();

    double getEntropyLexical();

    double getEntropyLexicalPOS();

    double getEntropyPOS();

    /**
     * @return the featureStructure
     */
    FeatureStructure getFeatureStructure();

    DOMElement getGATEDOMElement();

    String getGATEXML();

    LinkedHashMap<String, Integer> getGroupRelationFreq();

    String getId();

    LinkedHashMap<QueryValue, String> getMatchingValues(SSFQuery ssfQuery);

    LinkedHashMap<String, Integer> getPOSTagFreq();

    Object getQueryReturnObject();

    Object getQueryReturnValue();

    SSFPhrase getRoot();

    DOMElement getTypeCraftDOMElement();

    String getTypeCraftXML();

    String getUnannotated();

    /**
     *
     * @return
     */
    LinkedHashMap<String, Integer> getUnchunkedWordFreq();

    LinkedHashMap<String, Integer> getUntaggedWordFreq();

    LinkedHashMap<String, Integer> getWordFreq();

    LinkedHashMap<String, Integer> getWordTagPairFreq();

    String getXML();

    void loadAlignments(SSFSentence tgtSentence, int parallelIndex);

    String makePOSNolex();

    void makeSentenceFromChunked(String chunked, List<String> errorLog, int lineNum) throws Exception;

    void makeSentenceFromChunked(String chunked) throws Exception;
    void makeSentenceFromStanfordPSG(String chunked, List<String> errorLog /*Strings*/, int lineNum) throws Exception;
    void makeSentenceFromStanfordPSG(String chunked) throws Exception;
    void markSentenceWithStanfordDG(String chunked, List<String> errorLog /*Strings*/, int lineNum) throws Exception;
    void markSentenceWithStanfordDG(String chunked) throws Exception;

    void makeSentenceFromPOSTagged(String posTagged, List<String> errorLog, int lineNum) throws Exception;

    void makeSentenceFromPOSTagged(String posTagged) throws Exception;

    void makeSentenceFromRaw(String rawSentence) throws Exception;

    String makeString();

    boolean matches(FindReplaceOptions findReplaceOptions);

    void print(PrintStream ps);

    void print(PrintStream ps, CorpusType corpusType);

    void printGATEXML(PrintStream ps);

    void printTypeCraftXML(PrintStream ps);

    void printXML(PrintStream ps);

    // other methods
    void readFile(String fileName) throws Exception, FileNotFoundException, IOException;

    void readGATEXML(Element domElement);

    void readString(String string, List<String> errorLog, int lineNum) throws Exception;

    void readString(String string) throws Exception;

    void readTypeCraftXML(Element domElement);

    void readXML(Element domElement);

    void reallocatePositions(String positionAttribName, String nullWordString);

    void save(String f, String charset) throws FileNotFoundException, UnsupportedEncodingException;

    void saveAlignments();

    /**
     * @param featureStructure the featureStructure to set
     */
    void setFeatureStructure(FeatureStructure featureStructure);

    void setId(String i);

    void setMorphTags(KeyValueProperties morphTags);

    void setQueryReturnObject(Object rv);

    void setQueryReturnValue(Object rv);

    void setRoot(SSFPhrase r);    
}
