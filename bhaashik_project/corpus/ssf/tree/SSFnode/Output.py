from typing import List
from abc import ABC, abstractmethod
from java.awt import Color, Stroke
from java.io import File, FileNotFoundException, IOException, PrintStream, Serializable
from java.util import ArrayList, LinkedHashMap
from java.util.regex import Matcher, Pattern
from javax.swing.table import DefaultTableModel
from javax.swing.tree import MutableTreeNode
from bhaashik.corpus.parallel import Alignable, AlignmentUnit
from bhaashik.corpus.ssf import SSFProperties, SSFSentence, SSFCorpus
from bhaashik.corpus.ssf.features import FeatureAttribute, FeatureStructure, FeatureStructures, FeatureValue, FSProperties
from bhaashik.corpus.ssf.features.impl import FeatureAttributeImpl, FeatureStructureImpl, FeatureStructuresImpl, FeatureValueImpl
from bhaashik.corpus.ssf.query import SSFQuery, QueryValue
from bhaashik.corpus.xml import XMLProperties
from bhaashik.gui.common import BhaashikLanguages
from bhaashik.properties import KeyValueProperties
from bhaashik.table.gui import BhaashikJTable
from bhaashik.tree import BhaashikEdge, BhaashikMutableTreeNode
from bhaashik.util import UtilityFunctions
from bhaashik.util.query import FindReplace, FindReplaceOptions
from bhaashik.xml.dom import BhaashikDOMElement, GATEDOMElement, TypeCraftDOMElement
from edu.stanford.nlp.util import HashIndex, Index

class SSFNode(BhaashikMutableTreeNode, MutableTreeNode, Alignable, Serializable, QueryValue, BhaashikDOMElement, TypeCraftDOMElement, GATEDOMElement):
    ssfProps = SSFProperties()
    xmlProps = XMLProperties()
    WORD_SEPARATOR = ' '
    TAG_SEPARATOR = '__'
    COMMENT_SEPARATOR = ' '
    HIGHLIGHTED = 1
    HIGHLIGHT = 'hlt'

    def __init__(self):
        super().__init__()
        self.id = ''
        self.lexIndices = ArrayList()
        self.tagIndices = ArrayList()
        self.commentIndices = ArrayList()
        self.fs = None
        self.alignmentUnit = AlignmentUnit()
        self.nestedFS = False
        self.isTriangle = False
        self.flags = 0

    def __init__(self, userObject):
        super().__init__(userObject)
        self.id = ''
        self.lexIndices = ArrayList()
        self.tagIndices = ArrayList()
        self.commentIndices = ArrayList()
        self.fs = None
        self.alignmentUnit = AlignmentUnit()
        self.nestedFS = False
        self.isTriangle = False
        self.flags = 0

    def __init__(self, userObject, allowsChildren):
        super().__init__(userObject, allowsChildren)
        self.id = ''
        self.lexIndices = ArrayList()
        self.tagIndices = ArrayList()
        self.commentIndices = ArrayList()
        self.fs = None
        self.alignmentUnit = AlignmentUnit()
        self.nestedFS = False
        self.isTriangle = False
        self.flags = 0

    def __init__(self, id, lexdata, name, stringFS):
        super().__init__()
        self.id = id
        self.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, True)
        self.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, True)
        self.commentIndices = ArrayList()
        self.fs = FeatureStructuresImpl()
        self.fs.readString(stringFS)
        self.nestedFS = False
        self.alignmentUnit = AlignmentUnit()

    def __init__(self, id, lexdata, name, fs):
        super().__init__()
        self.id = id
        self.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, True)
        self.commentIndices = ArrayList()
        self.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, True)
        self.fs = fs
        self.nestedFS = False
        self.alignmentUnit = AlignmentUnit()

    def __init__(self, id, lexdata, name, stringFS, userObject):
        super().__init__()
        self.id = id
        self.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, True)
        self.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, True)
        self.commentIndices = ArrayList()
        self.fs = FeatureStructuresImpl()
        self.fs.readString(stringFS)
        self.nestedFS = False
        self.alignmentUnit = AlignmentUnit()

    def __init__(self, id, lexdata, name, fs, userObject):
        super().__init__()
        self.id = id
        self.lexIndices = getIndices(lexdata, vocabIndex, WORD_SEPARATOR, True)
        self.tagIndices = getIndices(name, tagIndex, TAG_SEPARATOR, True)
        self.commentIndices = ArrayList()
        self.fs = fs
        self.nestedFS = False
        self.alignmentUnit = AlignmentUnit()

    def getId(self):
        return self.id

    def setId(self, i):
        self.id = i

    def getLexData(self):
        return getString(self.lexIndices, vocabIndex, WORD_SEPARATOR)

    def setLexData(self, ld):
        self.lexIndices = getIndices(ld, vocabIndex, WORD_SEPARATOR, True)

    def getName(self):
        return getString(self.tagIndices, tagIndex, TAG_SEPARATOR)

    def setName(self, n):
        self.tagIndices = getIndices(n, tagIndex, TAG_SEPARATOR, True)

    def getComment(self):
        return getString(self.commentIndices, commentIndex, COMMENT_SEPARATOR)

    def setComment(self, c):
        self.commentIndices = getIndices(c, commentIndex, COMMENT_SEPARATOR, True)

    def getVocabularySize(self):
        return vocabIndex.size()

    def getTagVocabularySize(self):
        return tagIndex.size()

    def getCommentVocabularySize(self):
        return commentIndex.size()

    @staticmethod
    def getIndices(wds, index, sep, add):
        parts = wds.split(sep)
        indices = ArrayList(parts.length)
        for part in parts:
            wi = index.indexOf(part, add)
            indices.add(wi)
        return indices

    @staticmethod
    def getString(wdIndices, index, sep):
        str = ''
        i = 0
        for wi in wdIndices:
            if i == 0:
                str = index.get(wi)
            else:
                str += sep + index.get(wi)
            i += 1
        return str

    def isTriangle(self):
        return self.isTriangle

    def isTriangle(self, isTriangle):
        self.isTriangle = isTriangle

    def getAlignmentUnit(self):
        return self.alignmentUnit

    def setAlignmentUnit(self, alignmentUnit):
        alignmentUnit.setAligmentObject(self)
        self.alignmentUnit = alignmentUnit

    def getAlignedObject(self, alignmentKey):
        return self.alignmentUnit.getAlignedObject(alignmentKey)
from typing import List
from SSFNode import SSFNode
from AlignmentUnit import AlignmentUnit
from FeatureStructuresImpl import FeatureStructuresImpl
from FeatureStructureImpl import FeatureStructureImpl
from SSFPhrase import SSFPhrase
from UtilityFunctions import UtilityFunctions
from KeyValueProperties import KeyValueProperties

class MyClass(List[SSFNode], SSFNode):

    def __init__(self):
        super().__init__()
        self.alignmentUnit = AlignmentUnit()

    def getAlignedObjects(self) -> List[SSFNode]:
        return self.alignmentUnit.getAlignedObjects()

    def getFirstAlignedObject(self) -> SSFNode:
        return self.alignmentUnit.getFirstAlignedObject()

    def getAlignedObject(self, i: int) -> SSFNode:
        return self.alignmentUnit.getAlignedObject(i)

    def getLastAlignedObject(self) -> SSFNode:
        return self.alignmentUnit.getLastAlignedObject()

    def loadAlignments(self, srcSentence: SSFSentence, tgtSentence: SSFSentence, parallelIndex: int) -> None:
        if self.fs is None:
            self.fs = FeatureStructuresImpl()
            featureStructure = FeatureStructureImpl()
            self.fs.addAltFSValue(featureStructure)
        aunit = self.fs.loadAlignmentUnit(self, srcSentence, tgtSentence, parallelIndex)
        if aunit is not None:
            self.alignmentUnit = aunit
        if isinstance(self, SSFPhrase):
            allChildren = self.getAllChildren()
            count = len(allChildren)
            for i in range(count):
                node = allChildren[i]
                node.loadAlignments(srcSentence, tgtSentence, parallelIndex)

    def saveAlignments(self) -> None:
        if self.fs is None:
            return
        self.fs.setAlignmentUnit(self.alignmentUnit)
        if isinstance(self, SSFPhrase):
            allChildren = self.getAllChildren()
            count = len(allChildren)
            for i in range(count):
                node = allChildren[i]
                node.saveAlignments()

    def isHighlighted(self) -> bool:
        hf = UtilityFunctions.flagOn(self.flags, HIGHLIGHTED)
        ha = False
        hs = self.getAttributeValue(HIGHLIGHT)
        if hs is not None and hs == 'true':
            ha = True
        return hf or ha

    def isHighlighted(self, h: bool) -> None:
        if h:
            self.flags = UtilityFunctions.switchOnFlags(self.flags, HIGHLIGHTED)
            self.setAttributeValue(HIGHLIGHT, 'true')
            return
        self.setAttributeValue(HIGHLIGHT, 'false')
        self.flags = UtilityFunctions.switchOffFlags(self.flags, HIGHLIGHTED)

    def clearHighlights(self) -> None:
        self.isHighlighted(False)
        count = self.countChildren()
        for i in range(count):
            child = self.getChildAt(i)
            child.clearHighlights()

    def hasLeaves(self) -> bool:
        return False

    def setMorphTags(self, morphTags: KeyValueProperties) -> None:
        if morphTags is None:
            return
        if isinstance(self, SSFPhrase):
            depth = self.getDepth()
            if depth == 1:
                leaves = self.getAllLeaves()
                count = len(leaves)
                for i in range(count):
                    leaf = leaves[i]
                    leafPOSTag = leaf.getName()
                    mtag = morphTags.getPropertyValue(leafPOSTag)
                    leafFss = leaf.getFeatureStructures()
                    if leafFss is not None:
                        if mtag is None or mtag == '':
                            prevNode = leaf.getPrevious()
                            if prevNode is not None:
                                mtag = morphTags.getPropertyValue(prevNode.getName())
                                leafFss.setAllAttributeValues('cat', mtag)
                        else:
                            leafFss.setAllAttributeValues('cat', mtag)
            elif depth > 1:
                ccount = self.countChildren()
                for i in range(ccount):
                    childNode = self.getChildAt(i)
                    childNode.setMorphTags(morphTags)

    def getFeatureStructures(self) -> FeatureStructures:
        return self.fs

    def getStringFS(self) -> str:
        if self.fs is None:
            return ''
        return self.fs.makeString()

    def setFeatureStructures(self, f: FeatureStructures) -> None:
        self.fs = f

    def getAttributeNames(self) -> List[str]:
        if self.fs is None:
            return None
        return self.fs.getAttributeNames()

    def getAttributeValue(self, attributeName: str) -> str:
        if self.fs is None:
            return None
        return self.fs.getAttributeValueString(attributeName)

    def getAttributeValues(self) -> List[str]:
        if self.fs is None:
            return None
        return self.fs.getAttributeValues()

    def getAttributeValuePairs(self) -> List[str]:
        if self.fs is None:
            return None
        return self.fs.getAttributeValuePairs()

    def getOneOfAttributeValues(self, attributeNames: List[str]) -> str:
        if self.fs is None:
            return None
        return self.fs.getOneOfAttributeValues(attributeNames)

    def setAttributeValue(self, attributeName: str, value: str) -> None:
        if self.fs is None:
            self.fs = FeatureStructuresImpl()
        self.fs.setAttributeValue(attributeName, value)

    def concatenateAttributeValue(self, attributeName: str, value: str, separator: str) -> None:
        if self.fs is None:
            self.fs = FeatureStructuresImpl()
        self.fs.concatenateAttributeValue(attributeName, value, separator)

    def getAttribute(self, attributeName: str) -> FeatureAttribute:
        if self.fs is None:
            return None
        return self.fs.getAttribute(attributeName)

    def countChildren(self) -> int:
        return 0

    def isLeafNode(self) -> bool:
        return self.isLeaf()

    def removeNode(self) -> int:
        prnt = self.getParent()
        ind = prnt.findChild(self)
        prnt.removeChild(ind)
        return ind

    def removeEmptyPhrases(self) -> None:
        pass

    def removeNonChunkPhrases(self) -> None:
        pass

    def isNonChunkPhrase(self) -> bool:
        return False

    def removeDSRedundantPhrases(self) -> None:
        pass

    def isDSRedundantPhrase(self) -> bool:
        return False

    def removeLayer(self) -> int:
        prnt = self.getParent()
        if prnt is None:
            return -1
        ind = prnt.findChild(self)
        self.alignmentUnit.clearAlignments()
        if self.isLeafNode():
            prnt.removeChild(ind)
        else:
            prnt.addChildrenAt(self.getAllChildren(), ind)
            self.removeNode()
        return ind

    def removeAttribute(self, attributeName: str) -> None:
        if self.fs is not None and self.fs.countAltFSValues() > 0:
            tfs = self.fs.getAltFSValue(0)
            tfs.removeAttribute(attributeName)

    def hideAttribute(self, attributeName: str) -> None:
        if self.fs is not None and self.fs.countAltFSValues() > 0:
            self.fs.hideAttribute(attributeName)

    def unhideAttribute(self, attributeName: str) -> None:
        if self.fs is not None and self.fs.countAltFSValues() > 0:
            self.fs.unhideAttribute(attributeName)

    def getPrevious(self) -> SSFNode:
        return self.getPreviousSibling()

    def getNext(self) -> SSFNode:
        return self.getNextSibling()

    def getNodeForId(self, id: str) -> SSFNode:
        if self.id.lower() == id.lower():
            return self
        return None

    @staticmethod
    def getSSFProperties() -> SSFProperties:
        if ssfProps is None:
            loadSSFProperties()
        return ssfProps
def set_ssf_properties(ssfp):
    global ssfProps
    ssfProps = ssfp

def load_ssf_properties():
    global ssfProps
    ssfProps = SSFProperties()
    try:
        ssfProps.read(GlobalProperties.resolveRelativePath('props/ssf-props.txt'), GlobalProperties.getIntlString('UTF-8'))
    except FileNotFoundException as ex:
        ex.printStackTrace()
    except IOException as ex:
        ex.printStackTrace()

def get_xml_properties():
    global xmlProps
    if xmlProps is None:
        load_xml_properties()
    return xmlProps

def set_xml_properties(xmlp):
    global xmlProps
    xmlProps = xmlp

def load_xml_properties():
    global xmlProps
    xmlProps = XMLProperties()
    try:
        xmlProps.read(GlobalProperties.resolveRelativePath('props/xml-props.txt'), GlobalProperties.getIntlString('UTF-8'))
    except FileNotFoundException as ex:
        ex.printStackTrace()
    except IOException as ex:
        ex.printStackTrace()

def read_string(s):
    global ssfProps
    fieldSeparatorRegex = ssfProps.getProperties().getPropertyValue('fieldSeparatorRegex')
    fields = s.split(fieldSeparatorRegex, 4)
    id = fields[0]
    lexIndices = get_indices(fields[1], vocabIndex, WORD_SEPARATOR, True)
    if len(fields) > 2:
        tagIndices = get_indices(fields[2], tagIndex, TAG_SEPARATOR, True)
    if len(fields) == 4 and fields[3] != '':
        fs = FeatureStructuresImpl()
        fs.read_string(fields[3])
    return 0

def get_user_object():
    if isinstance(self, SSFPhrase):
        return get_name()
    return get_lex_data()

def print_output(ps):
    ps.print(make_string())

def make_top_string():
    global ssfProps
    fieldSeparatorPrint = ssfProps.getProperties().getPropertyValueForPrint('fieldSeparatorPrint')
    global chunkStart
    chunkStart = get_ssf_properties().getProperties().getPropertyValueForPrint('chunkStart')
    ld = chunkStart
    if is_leaf_node():
        ld = get_lex_data()
    name = get_name()
    if fs == None:
        return id + fieldSeparatorPrint + ld + fieldSeparatorPrint + name + fieldSeparatorPrint + ''
    return id + fieldSeparatorPrint + ld + fieldSeparatorPrint + name + fieldSeparatorPrint + fs.make_string()

def make_summary_string():
    string = ''
    lexdata = get_lex_data()
    if lexdata != '':
        string = lexdata
    name = get_name()
    if name != '':
        if string != '':
            string += ' : ' + name
        else:
            string += name
    if fs != None and fs.make_string() != '':
        if string != '':
            string += ' : ' + fs.make_string()
        else:
            string += fs.make_string()
    return string

def fill_ssf_data(n):
    global id
    global fs
    id = n.id
    set_lex_data(n.get_lex_data())
    set_name(n.get_name())
    fs = n.fs

def collapse_lexical_items():
    pass

def collapse_lexical_items_deep():
    pass

def make_string():
    return make_top_string() + '\n'

def make_raw_sentence():
    return get_lex_data()

def convert_to_pos_nolex():
    global unknownTag
    ssfp = SSFNode.get_ssf_properties()
    unknownTag = ssfp.getProperties().getPropertyValueForPrint('unknownTag')
    if not is_leaf_node():
        leaves = get_all_leaves()
        for leaf in leaves:
            if leaf.get_name() != '':
                leaf.set_lex_data(leaf.get_name())
            else:
                leaf.set_lex_data(unknownTag)
            leaf.set_name('')
    elif get_name() != '':
        set_lex_data(get_name())
    else:
        set_lex_data(unknownTag)

def make_pos_nolex():
    global unknownTag, wordTagSeparator
    ssfp = SSFNode.get_ssf_properties()
    unknownTag = ssfp.getProperties().getPropertyValueForPrint('unknownTag')
    posNolex = ''
    if not is_leaf_node():
        leaves = get_all_leaves()
        for i, leaf in enumerate(leaves):
            if leaf.get_name() != '':
                posNolex += leaf.get_name()
            else:
                posNolex += unknownTag
            if i < len(leaves) - 1:
                posNolex += ' '
            else:
                posNolex += '\n'
    elif get_name() != '':
        posNolex += get_name()
    else:
        posNolex += unknownTag
    return posNolex

def convert_to_lower_case():
    leaves = get_all_leaves()
    for leaf in leaves:
        leaf.set_lex_data(leaf.get_lex_data().lower())

def convert_to_pos_tagged(sep):
    global wordTagSeparator
    ssfp = SSFNode.get_ssf_properties()
    wordTagSeparator = ssfp.getProperties().getPropertyValueForPrint('wordTagSeparator')
    if sep != None:
        wordTagSeparator = sep
    unknownTag = ssfp.getProperties().getPropertyValueForPrint('unknownTag')
    posTagged = ''
    if not is_leaf_node():
        leaves = get_all_leaves()
        for i, leaf in enumerate(leaves):
            if leaf.get_name() != '':
                posTagged += leaf.get_lex_data() + wordTagSeparator + leaf.get_name()
            else:
                posTagged += leaf.get_lex_data() + wordTagSeparator + unknownTag
            if i < len(leaves) - 1:
                posTagged += ' '
            else:
                posTagged += '\n'
    elif get_name() != '':
        posTagged += get_lex_data() + wordTagSeparator + get_name()
    else:
        posTagged += get_lex_data() + wordTagSeparator + unknownTag
    return posTagged

def convert_to_bracket_form(spaces):
    bracketForm = ''
    global rootName
    rootName = ssfProps.getProperties().getPropertyValueForPrint('rootName')
    bracketFormStart = ssfProps.getProperties().getPropertyValueForPrint('bracketFormStart')
    bracketFormEnd = ssfProps.getProperties().getPropertyValueForPrint('bracketFormEnd')
    global wordTagSeparator, unknownTag
    wordTagSeparator = ssfProps.getProperties().getPropertyValueForPrint('wordTagSeparator')
    unknownTag = ssfProps.getProperties().getPropertyValueForPrint('unknownTag')
    if not is_leaf_node():
        bracketForm += bracketFormStart
    for j in range(spaces):
        bracketForm += ' '
    count = count_children()
    for i in range(count):
        child = get_child_at(i)
        bracketForm += child.convert_to_bracket_form(spaces)
        if i < count - 1:
            for j in range(spaces):
                bracketForm += ' '
    for j in range(spaces):
        bracketForm += ' '
    if get_name() != '':
        bracketForm += bracketFormEnd + wordTagSeparator + get_name()
    else:
        bracketForm += bracketFormEnd + wordTagSeparator + unknownTag
    return bracketForm
def convertToBracketFormHTML(self, spaces):
    bracketFormHTML = '<html><META http-equiv="Content-Type" content="text/html; charset=UTF-8"><font color=#0000ff>' + self.convertToBracketForm(spaces) + '</font></html>'
    return bracketFormHTML

def getCopy(self):
    str = self.makeString()
    ssfNode = SSFNode()
    ssfNode.readString(str)
    ssfNode.flags = self.flags
    return ssfNode

def copyExtraData(self, node):
    count = self.countChildren()
    ncount = node.countChildren()
    if count != ncount:
        return
    self.flags = node.flags
    for i in range(count):
        self.getChildAt(i).copyExtraData(node.getChildAt(i))

def allowsNestedFS(self):
    return self.nestedFS

def allowNestedFS(self, b):
    self.nestedFS = b

def clear(self):
    self.id = ''
    self.lexIndices = []
    self.tagIndices = []
    self.fs = None

def addDefaultAttributes(self):
    lfs = self.getFeatureStructures().getAltFSValue(0)
    if self.getName() == 'VG':
        fa = FeatureAttributeImpl()
        fa.setName('name')
        fa.addAltValue(FeatureValueImpl(''))
        lfs.addAttribute(fa)
    elif self.getName() == 'NP' or self.getName() == 'PP' or self.getName() == 'JJP':
        fa = FeatureAttributeImpl()
        fa.setName('drel')
        fa.addAltValue(FeatureValueImpl(''))
        lfs.addAttribute(fa)

def clearFeatureStructures(self):
    count = self.countChildren()
    for i in range(count):
        node = self.getChildAt(i)
        fss = node.getFeatureStructures()
        if fss is not None:
            fss.setToEmpty()
        else:
            fss = FeatureStructuresImpl()
            fss.setToEmpty()
            node.setFeatureStructures(fss)
        node.clearFeatureStructures()

def clearAnnotation(self, annoLevelFlags):
    count = self.countChildren()
    if isinstance(self, SSFLexItem) and flagOn(annoLevelFlags, SSFCorpus.POS_TAGS) or (isinstance(self, SSFPhrase) and flagOn(annoLevelFlags, SSFCorpus.CHUNK_NAMES)):
        self.setName('')
    if self.getFeatureStructures() is not None and fs.makeString() != '':
        if isinstance(self, SSFLexItem) and flagOn(annoLevelFlags, SSFCorpus.LEXITEM_FEATURE_STRUCTURES) or (isinstance(self, SSFPhrase) and flagOn(annoLevelFlags, SSFCorpus.CHUNK_FEATURE_STRUCTURES)) or (isinstance(self, SSFLexItem) and flagOn(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES)) or (isinstance(self, SSFPhrase) and flagOn(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES)) or (isinstance(self, SSFLexItem) and flagOn(annoLevelFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES)) or (isinstance(self, SSFPhrase) and flagOn(annoLevelFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES)) or (isinstance(self, SSFNode) and flagOn(annoLevelFlags, SSFCorpus.ALL_EXCEPT_THE_FIRST_FS)) or (isinstance(self, SSFNode) and flagOn(annoLevelFlags, SSFCorpus.PRUNE_THE_FS)):
            if isinstance(self, SSFLexItem) and (flagOn(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES) or flagOn(annoLevelFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES)):
                modifiedAnnotationFlags = switchOffFlags(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES)
                modifiedAnnotationFlags = switchOffFlags(modifiedAnnotationFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES)
                self.getFeatureStructures().clearAnnotation(modifiedAnnotationFlags, self)
            elif isinstance(self, SSFPhrase) and (flagOn(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES) or flagOn(annoLevelFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES)):
                modifiedAnnotationFlags = switchOffFlags(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES)
                modifiedAnnotationFlags = switchOffFlags(modifiedAnnotationFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES)
                self.getFeatureStructures().clearAnnotation(modifiedAnnotationFlags, self)
            self.getFeatureStructures().clearAnnotation(annoLevelFlags, self)
    if flagOn(annoLevelFlags, SSFCorpus.COMMENTS):
        self.setComment('')
    for i in range(count):
        node = self.getChildAt(i)
        node.clearAnnotation(annoLevelFlags)
    if isinstance(self, SSFPhrase) and flagOn(annoLevelFlags, SSFCorpus.CHUNKS) and (getParent() is None or (getParent() is not None and getParent().getParent() is None)):
        self.flatten()

def equals(self, obj):
    if not isinstance(obj, SSFNode):
        return False
    if obj is None:
        return False
    nobj = obj
    if self.getId() != nobj.getId():
        return False
    if self.getLexData() != nobj.getLexData():
        return False
    if self.getName().casefold() != nobj.getName().casefold():
        return False
    if self.getFeatureStructures() is None and nobj.getFeatureStructures() is None:
        return True
    if self.getFeatureStructures() is None or nobj.getFeatureStructures() is None:
        return False
    if not self.getFeatureStructures().equals(nobj.getFeatureStructures()):
        return False
    return True

def setValuesInTable(self, tbl, mode):
    if self.rowIndex == -1 or tbl.getRowCount() <= 0 or tbl.getColumnCount() <= 0:
        return
    if self.getName() == '':
        tbl.setValueAt(self.getLexData(), self.rowIndex, self.columnIndex)
    elif self.getLexData() == '':
        tbl.setValueAt(self.getName(), self.rowIndex, self.columnIndex)
    else:
        tbl.setValueAt(self.getLexData(), self.rowIndex, self.columnIndex)

def __str__(self):
    return self.makeRawSentence()

def matches(self, findReplaceOptions):
    match = False
    pattern = FindReplace.compilePattern(findReplaceOptions.findText, findReplaceOptions)
    matcher = None
    text = self.getLexData()
    if text is not None and text != '':
        matcher = pattern.matcher(text)
        if matcher.find():
            match = True
    else:
        match = False
    if match:
        isHighlighted(True)
    return match
class MyClass(SuperClass1, SuperClass2, Interface1, Interface2):

    def fillGraphEdges(self, jtbl, mode):
        atrrNames = None
        if mode == PHRASE_STRUCTURE_MODE:
            atrrNames = FSProperties.getPSGraphAttributes()
            mcount = self.getChildCount()
            for i in range(mcount):
                mnode = self.getChildAt(i)
                mnode.fillGraphEdges(jtbl, mode)
                refAtVal = mnode.getOneOfAttributeValues(atrrNames)
                if refAtVal is None:
                    continue
                refVal = refAtVal[1]
                if refVal is None:
                    continue
                parts = refVal.split(':')
                rel = ''
                nm = ''
                if len(parts) == 1:
                    nm = parts[0]
                elif len(parts) == 2:
                    rel = parts[0]
                    nm = parts[1]
                referredNode = self.getRoot().getNodeForAttribVal('name', nm, True)
                if referredNode is not None and referredNode.getRowIndex() >= 0:
                    edge = BhaashikEdge(self, referredNode.getRowIndex(), referredNode.getColumnIndex(), mnode, mnode.getRowIndex(), mnode.getColumnIndex())
                    prel = rel
                    edge.setLabel(prel.upper())
                    color = UtilityFunctions.getColor(FSProperties.getPSGraphAttributeProperties(refAtVal[0])[0])
                    edge.setColor(color)
                    stroke = UtilityFunctions.getStroke(FSProperties.getPSGraphAttributeProperties(refAtVal[0])[1])
                    edge.setStroke(stroke)
                    edge.isCurved(True)
                    jtbl.addEdge(edge)
                jtbl.setCellObject(mnode.getRowIndex(), mnode.getColumnIndex(), mnode)
        elif mode == DEPENDENCY_RELATIONS_MODE:
            atrrNames = FSProperties.getDependencyGraphAttributes()
            if not self.isLeaf() or jtbl.allowsLeafDependencies():
                mcount = self.getChildCount()
                for i in range(mcount):
                    mnode = self.getChildAt(i)
                    if isinstance(mnode, SSFPhrase) or jtbl.allowsLeafDependencies():
                        mnode.fillGraphEdges(jtbl, mode)
                        refAtVal = mnode.getOneOfAttributeValues(atrrNames)
                        if refAtVal is None:
                            continue
                        refVal = refAtVal[1]
                        if refVal is None:
                            continue
                        parts = refVal.split(':')
                        rel = ''
                        nm = ''
                        if len(parts) == 1:
                            nm = parts[0]
                        elif len(parts) == 2:
                            rel = parts[0]
                            nm = parts[1]
                        referredNode = self.getRoot().getNodeForAttribVal('name', nm, True)
                        if referredNode is not None and referredNode.getRowIndex() >= 0:
                            edge = BhaashikEdge(self, referredNode.getRowIndex(), referredNode.getColumnIndex(), mnode, mnode.getRowIndex(), mnode.getColumnIndex())
                            drel = rel
                            edge.setLabel(drel.upper())
                            color = UtilityFunctions.getColor(FSProperties.getDependencyGraphAttributeProperties(refAtVal[0])[0])
                            edge.setColor(color)
                            stroke = UtilityFunctions.getStroke(FSProperties.getDependencyGraphAttributeProperties(refAtVal[0])[1])
                            edge.setStroke(stroke)
                            edge.isCurved(True)
                            jtbl.addEdge(edge)
                        jtbl.setCellObject(mnode.getRowIndex(), mnode.getColumnIndex(), mnode)
        else:
            super().fillGraphEdges(jtbl, mode)

    @staticmethod
    def getCurrentVocabulary():
        return UtilityFunctions.getCopy(vocabIndex)

    @staticmethod
    def getCurrentTagVocabulary():
        return UtilityFunctions.getCopy(tagIndex)