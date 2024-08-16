import sys
import re
from abc import ABC, abstractmethod
from xml.dom import minidom
from xml.etree.ElementTree import Element, SubElement, tostring
from bhaashik.GlobalProperties import GlobalProperties
from bhaashik.corpus.parallel.Alignable import Alignable
from bhaashik.corpus.parallel.AlignmentUnit import AlignmentUnit
from bhaashik.corpus.ssf.SSFNode import SSFNode
from bhaashik.corpus.ssf.features.FeatureAttribute import FeatureAttribute
from bhaashik.corpus.ssf.features.FeatureStructure import FeatureStructure
from bhaashik.corpus.ssf.features.FeatureStructures import FeatureStructures
from bhaashik.corpus.ssf.features.FeatureValue import FeatureValue
from bhaashik.corpus.ssf.features.impl.FSProperties import FSProperties
from bhaashik.corpus.ssf.features.impl.FeatureAttributeImpl import FeatureAttributeImpl
from bhaashik.corpus.ssf.features.impl.FeatureStructureImpl import FeatureStructureImpl
from bhaashik.corpus.ssf.features.impl.FeatureStructuresImpl import FeatureStructuresImpl
from bhaashik.corpus.ssf.features.impl.FeatureValueImpl import FeatureValueImpl
from bhaashik.corpus.ssf.query.QueryValue import QueryValue
from bhaashik.corpus.ssf.tree.SSFLexicalItem import SSFLexicalItem
from bhaashik.corpus.ssf.tree.SSFNode import SSFNode
from bhaashik.corpus.xml.XMLProperties import XMLProperties
from bhaashik.text.enc.conv.BhaashikEncodingConverter import BhaashikEncodingConverter
from bhaashik.tree.BhaashikMutableTreeNode import BhaashikMutableTreeNode
from bhaashik.util.UtilityFunctions import UtilityFunctions
from bhaashik.util.query.FindReplace import FindReplace
from bhaashik.util.query.FindReplaceOptions import FindReplaceOptions
from bhaashik.xml.XMLUtils import XMLUtils
from org.apache.xml.serialize import OutputFormat
from org.apache.xml.serialize import XMLSerializer

class SSFPhrase(SSFNode, Alignable, QueryValue, BhaashikDOMElement, TypeCraftDOMElement, GATEDOMElement):

    def __init__(self, userObject=None, allowsChildren=True):
        super().__init__(userObject, allowsChildren)

    def __init__(self, id, lexdata, name, stringFS=None, userObject=None):
        super().__init__(id, lexdata, name, stringFS, userObject)

    def countChildren(self):
        return self.getChildCount()

    def addChild(self, c):
        self.add(c)
        return self.getChildCount()

    def addChildren(self, c):
        ca = list(c)
        for i in range(len(ca)):
            self.add(ca[i])
        return self.getChildCount()

    def addChildAt(self, c, index):
        self.insert(c, index)
        return self.getChildCount()

    def addChildrenAt(self, c, index):
        ca = list(c)
        for i in range(len(ca)):
            self.insert(ca[i], index + i)
        return self.getChildCount()

    def getChild(self, index):
        return self.getChildAt(index)

    def findChild(self, aChild):
        if aChild == None:
            raise ValueError(GlobalProperties.getIntlString('argument_is_null'))
        if not self.isNodeChild(aChild):
            return -1
        count = self.getChildCount()
        for i in range(count):
            if self.getChild(i) == aChild:
                return i
        return -1

    def findLeafByID(self, id):
        leaves = self.getAllLeaves()
        count = len(leaves)
        for i in range(count):
            node = leaves[i]
            if node.getId() == id:
                return node
        return None

    def findLeafIndexByID(self, id):
        leaves = self.getAllLeaves()
        count = len(leaves)
        for i in range(count):
            node = leaves[i]
            if node.getId() == id:
                return i
        return -1

    def findChildByID(self, id):
        allChildren = self.getAllChildren()
        count = len(allChildren)
        for i in range(count):
            node = allChildren[i]
            if node.getId() == id:
                return node
        return None

    def findChildIndexByID(self, id):
        allChildren = self.getAllChildren()
        count = len(allChildren)
        for i in range(count):
            node = allChildren[i]
            if node.getId() == id:
                return i
        return -1

    def findNodeByID(self, id):
        if self.getId() == id:
            return self
        allChildren = self.getAllChildren()
        count = len(allChildren)
        for i in range(count):
            node = allChildren[i]
            if isinstance(node, SSFLexItem):
                if node.getId() == id:
                    return node
            elif isinstance(node, SSFPhrase):
                return node.findNodeByID(id)
        return None

    def findNodeIndexByID(self, id):
        if self.getId() == id:
            prnt = self.getParent()
            if prnt == None:
                return 0
            else:
                return prnt.findChild(self)
        allChildren = self.getAllChildren()
        count = len(allChildren)
        for i in range(count):
            node = allChildren[i]
            if isinstance(node, SSFLexItem):
                if node.getId() == id:
                    return i
            elif isinstance(node, SSFPhrase):
                return node.findNodeIndexByID(id)
        return -1

    def findLeafByName(self, n):
        leaves = self.getAllLeaves()
        count = len(leaves)
        for i in range(count):
            node = leaves[i]
            nodeName = node.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return node
        return None

    def findLeafIndexByName(self, n):
        leaves = self.getAllLeaves()
        count = len(leaves)
        for i in range(count):
            node = leaves[i]
            nodeName = node.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return i
        return -1

    def findChildByName(self, n):
        allChildren = self.getAllChildren()
        count = len(allChildren)
        for i in range(count):
            node = allChildren[i]
            nodeName = node.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return node
        return None

    def findChildIndexByName(self, n):
        allChildren = self.getAllChildren()
        count = len(allChildren)
        for i in range(count):
            node = allChildren[i]
            nodeName = node.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return i
        return -1
import re
from typing import List

class SSFNode:

    def getNodesForText(self, ld: str) -> List['SSFNode']:
        nodes = []
        p = re.compile(ld)
        m = p.search(self.makeRawSentence())
        if m:
            nodes.append(self)
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            m = p.search(node.makeRawSentence())
            if m:
                nodes.append(node)
            if not node.isLeafNode():
                nodes += SSFPhrase.getNodesForText(node, ld)
        nodes = list(set(nodes))
        return nodes

    def replaceLabelForText(self, ld: str, replace: str) -> List['SSFNode']:
        nodes = []
        p = re.compile(ld)
        m = p.search(self.makeRawSentence())
        if m:
            self.setName(replace)
            nodes.append(self)
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            m = p.search(node.makeRawSentence())
            if m:
                node.setName(replace)
                nodes.append(node)
            if not node.isLeafNode():
                nodes += SSFPhrase.replaceLabelForText(node, ld, replace)
        nodes = list(set(nodes))
        return nodes

    def getNodesForFS(self, fss: str) -> List['SSFNode']:
        nodes = []
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            if node.getFeatureStructures().makeString().lower() == fss.lower():
                nodes.append(node)
            if not node.isLeafNode():
                nodes += SSFPhrase.getNodesForFS(node, fss)
        nodes = list(set(nodes))
        return nodes

    def getNodesForAttrib(self, attrib: str, exactMatch: bool) -> List['SSFNode']:
        nodes = []
        fss = self.getFeatureStructures()
        if fss and fss.countAltFSValues() > 0:
            if fss.getAltFSValue(0).searchAttribute(attrib, exactMatch):
                nodes.append(self)
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            fss = node.getFeatureStructures()
            if fss and fss.countAltFSValues() > 0:
                if fss.getAltFSValue(0).searchAttribute(attrib, exactMatch):
                    nodes.append(node)
            if not node.isLeafNode():
                nodes += SSFPhrase.getNodesForAttrib(node, attrib, exactMatch)
        nodes = list(set(nodes))
        return nodes

    def getNodeForAttribVal(self, attrib: str, val: str, exactMatch: bool) -> 'SSFNode':
        nodes = self.getNodesForAttribVal(attrib, val, exactMatch)
        if nodes and len(nodes) > 0:
            return nodes[0]
        return None

    def getNodesForAttribVal(self, attrib: str, val: str, exactMatch: bool) -> List['SSFNode']:
        nodes = []
        fss = self.getFeatureStructures()
        if fss and fss.countAltFSValues() > 0:
            if fss.getAltFSValue(0).searchAttributeValue(attrib, val, exactMatch):
                nodes.append(self)
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            fss = node.getFeatureStructures()
            if fss and fss.countAltFSValues() > 0:
                if fss.getAltFSValue(0).searchAttributeValue(attrib, val, exactMatch):
                    nodes.append(node)
            if not node.isLeafNode():
                nodes += SSFPhrase.getNodesForAttribVal(node, attrib, val, exactMatch)
        nodes = list(set(nodes))
        return nodes

    def replaceAttribValForText(self, attrib: str, val: str, ntext: str, attribReplace: str, valReplace: str) -> List['SSFNode']:
        nodes = []
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            if node.makeRawSentence().lower() == ntext.lower():
                fss = node.getFeatureStructures()
                if fss and fss.countAltFSValues() > 0:
                    ifs = fss.getAltFSValue(0)
                    ifs.replaceAttributeValues(attrib, val, attribReplace, valReplace)
                    nodes.append(node)
                else:
                    fss = FeatureStructuresImpl()
                    ifs = FeatureStructureImpl()
                    fa = FeatureAttributeImpl()
                    fa.setName(attribReplace)
                    fv = FeatureValueImpl()
                    fv.setValue(valReplace)
                    fa.addAltValue(fv)
                    ifs.addAttribute(fa)
                    fss.addAltFSValue(ifs)
            if isinstance(node, SSFPhrase):
                nodes += SSFPhrase.replaceAttribValForText(node, attrib, val, ntext, attribReplace, valReplace)
        nodes = list(set(nodes))
        return nodes

    def replaceAttribValForLabel(self, attrib: str, val: str, nlabel: str, attribReplace: str, valReplace: str, createAttrib: bool) -> List['SSFNode']:
        nodes = []
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            if node.getName().lower() == nlabel.lower():
                fss = node.getFeatureStructures()
                if fss and fss.countAltFSValues() > 0:
                    ifs = fss.getAltFSValue(0)
                    fa = ifs.getAttribute(attrib)
                    if createAttrib and fa is None:
                        if FeatureStructuresImpl.getFSProperties().isMandatory(attrib):
                            if not ifs.hasMandatoryAttribs():
                                ifs.addMandatoryAttributes()
                        else:
                            fa = FeatureAttributeImpl()
                            fa.setName(attribReplace)
                            fv = FeatureValueImpl()
                            fv.setValue('')
                            fa.addAltValue(fv)
                            ifs.addAttribute(fa)
                    ifs.replaceAttributeValues(attrib, val, attribReplace, valReplace)
                    nodes.append(node)
                elif createAttrib:
                    fss = FeatureStructuresImpl()
                    ifs = FeatureStructureImpl()
                    fa = FeatureAttributeImpl()
                    fa.setName(attribReplace)
                    fv = FeatureValueImpl()
                    fv.setValue(valReplace)
                    fa.addAltValue(fv)
                    ifs.addAttribute(fa)
                    fss.addAltFSValue(ifs)
                    node.setFeatureStructures(fss)
                    nodes.append(node)
            if isinstance(node, SSFPhrase):
                nodes += SSFPhrase.replaceAttribValForLabel(node, attrib, val, nlabel, attribReplace, valReplace, createAttrib)
        nodes = list(set(nodes))
        return nodes
import re
from typing import List, Optional

def getNodesForValue(fieldnumber: int, val: str) -> Optional[List[SSFNode]]:
    nodes = []
    switch_match_result = {1: [getNodeForId(val)], 2: getNodesForName(val), 3: getNodesForLexData(val), 4: getNodesForFS(val)}
    for i in range(fieldnumber, 0, -1):
        nodes += switch_match_result.get(i, [])
    nodes = list(set(nodes))
    return nodes

def formPhrase(fromChild: int, count: int) -> int:
    ret = -1
    childCount = getChildCount()
    if fromChild >= 0 and fromChild < childCount and (fromChild + count <= childCount):
        ssfp = getSSFProperties()
        chunkStart = ssfp.getProperties().getPropertyValue('chunkStart')
        ssfph = SSFPhrase('0', '', 'NP', '')
        ch = getChildren(fromChild, count)
        insert(ssfph, fromChild)
        removeChildren(fromChild + 1, count)
        ssfph.addChildren(ch)
        ssfph.reallocateId(getId())
        ssfph.getFeatureStructures().setToEmpty()
        clearAlignments(fromChild, count)
    else:
        return ret
    return ret

def clearAlignments(fromChild: int, count: int) -> None:
    childCount = getChildCount()
    if fromChild >= 0 and fromChild < childCount and (fromChild + count <= childCount):
        for i in range(fromChild, fromChild + count):
            node = getChild(i)
            aunit = node.getAlignmentUnit()
            if aunit is not None:
                node.getAlignmentUnit().clearAlignments()

def readFile(f: str, charset: str) -> None:
    pass
def readNodesFromString(string, errorLog, lineNum):
    ssfp = getSSFProperties()
    fieldSeparatorRegex = ssfp.getProperties().getPropertyValue('fieldSeparatorRegex')
    chunkStart = ssfp.getProperties().getPropertyValue('chunkStart')
    chunkEnd = ssfp.getProperties().getPropertyValue('chunkEnd')
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    nodes = []
    lineArray = string.split('\n')
    level = 0
    phraseStack = []
    parent = None
    node = None
    for i in range(len(lineArray)):
        if lineArray[i] != '':
            lineArray[i] = lineArray[i].strip()
            fields = lineArray[i].split(fieldSeparatorRegex, 4)
            if lineArray[i].find(chunkEnd) == -1 and (fields == None or len(fields) <= 1):
                if errorLog != None:
                    errorLog.append(string + '\n')
                    errorLog.append('\nError_in_line_' + str(lineNum + i) + ':\n')
                    errorLog.append('********************\n')
                    errorLog.append(lineArray[i])
                    errorLog.append('********************\n')
                    errorLog.append('Error:_Second_SSF_field_null.\n')
                else:
                    print(string + '\n')
                    print('\nError_in_line_' + str(lineNum + i) + ':')
                    print('********************')
                    print(lineArray[i])
                    print('********************')
                    raise Exception('Error:_Second_SSF_field_null.')
            if len(fields) > 1 and fields[1] == chunkStart:
                level += 1
                if level == 1:
                    parent = None
                elif len(phraseStack) == 0:
                    if errorLog != None:
                        errorLog.append(string + '\n')
                        errorLog.append('\nError_in_line:_' + str(lineNum + i) + '\n')
                        errorLog.append('********************\n')
                        errorLog.append(lineArray[i])
                        errorLog.append('********************\n')
                        errorLog.append('Error:_Null_parent_for_SSFPhrase._Incorrect_format.\n')
                    else:
                        print(string + '\n')
                        print('\nError_in_line_' + str(lineNum + i) + ':')
                        print('********************')
                        print(lineArray[i])
                        print('********************')
                        raise Exception('Error:_Null_parent_for_SSFPhrase._Incorrect_format.')
                else:
                    parent = phraseStack[-1]
                node = SSFPhrase('0', '', rootName, '')
                phraseStack.append(node)
                node.setId(fields[0])
                node.setLexData('')
                if len(fields) > 2:
                    node.setName(fields[2])
                if len(fields) == 4 and fields[3] != '':
                    fss = FeatureStructuresImpl()
                    fss.readString(fields[3])
                    node.setFeatureStructures(fss)
                if level == 1:
                    nodes.append(node)
                else:
                    parent.addChild(node)
            elif fields[0] == chunkEnd:
                level -= 1
                if len(phraseStack) == 0:
                    if errorLog != None:
                        errorLog.append(string + '\n')
                        errorLog.append('\nError_in_line_' + str(lineNum + i) + ':\n')
                        errorLog.append('********************')
                        errorLog.append(lineArray[i])
                        errorLog.append('********************\n')
                        errorLog.append('Error:_Unmatching_ending_bracket._Incorrect_format.\n')
                    else:
                        print(string + '\n')
                        print('\nError_in_line_' + str(lineNum + i) + ':')
                        print('********************')
                        print(lineArray[i])
                        print('********************')
                        raise Exception('Error:_Unmatching_ending_bracket._Incorrect_format.')
                else:
                    phraseStack.pop()
            elif len(fields) > 1 and lineArray[i] != '' and (fields[1] != chunkStart) and (fields[0] != chunkEnd):
                if level == 0:
                    parent = None
                elif len(phraseStack) == 0:
                    if errorLog != None:
                        errorLog.append(string + '\n')
                        errorLog.append('\nError_in_line_' + str(lineNum + i) + ':\n')
                        errorLog.append('********************\n')
                        errorLog.append(lineArray[i])
                        errorLog.append('********************\n')
                        errorLog.append('Error:_Null_parent_for_a_LexicalItem._Incorrect_format.\n')
                    else:
                        print(string + '\n')
                        print('\nError_in_line_' + str(lineNum + i) + ':')
                        print('********************')
                        print(lineArray[i])
                        print('********************')
                        raise Exception('Error:_Null_parent_for_a_LexicalItem._Incorrect_format.')
                else:
                    parent = phraseStack[-1]
                node = SSFLexItem()
                node.readString(lineArray[i])
                ld = node.getLexData()
                ldwrds = ld.split('[ ]')
                if len(ldwrds) > 1:
                    for j in range(len(ldwrds)):
                        node1 = None
                        if node.getFeatureStructures() != None:
                            node1 = SSFLexItem(node.getId(), ldwrds[j], node.getName(), node.getFeatureStructures().makeString())
                        else:
                            node1 = SSFLexItem(node.getId(), ldwrds[j], node.getName(), '')
                        if level == 0:
                            nodes.append(node1)
                        else:
                            parent.addChild(node1)
                elif level == 0:
                    nodes.append(node)
                else:
                    parent.addChild(node)
        else:
            lineNum += 1
    if len(phraseStack) > 0:
        if errorLog != None:
            errorLog.append('...Sentence_string:\n')
            errorLog.append(string + '\n')
            errorLog.append('Error:_Wrong_format._Unmatching_brackets.\n')
        else:
            print('...Sentence_string:')
            print(string + '\n')
            raise Exception('Error:_Wrong_format._Unmatching_brackets.')
    nodes.trimToSize()
    return nodes
def read_nodes_from_string(string):
    return read_nodes_from_string(string, None, 1)

def read_nodes_from_chunked(string):
    return read_nodes_from_chunked(string, None, 1)

def read_nodes_from_chunked(string, error_log, line_num):
    ssfp = get_ssf_properties()
    bracket_form_start = ssfp.get_properties().get_property_value('bracketFormStart')
    bracket_form_end = ssfp.get_properties().get_property_value('bracketFormEnd')
    word_tag_separator = ssfp.get_properties().get_property_value('wordTagSeparator')
    root_name = ssfp.get_properties().get_property_value('rootName')
    nodes = []
    if bracket_form_end + word_tag_separator + root_name in string:
        string = string.replace(bracket_form_end + word_tag_separator + root_name, '')
        string = string[len(bracket_form_start):].strip()
    parts = string.split(' ')
    level = 0
    phrase_stack = []
    parent = None
    node = None
    for i in range(len(parts)):
        if parts[i] != '':
            if parts[i] == bracket_form_start:
                level += 1
                if level == 1:
                    parent = None
                elif len(phrase_stack) == 0:
                    if error_log != None:
                        error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
                    else:
                        print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
                else:
                    parent = phrase_stack[-1]
                node = SSFPhrase('0', '', root_name, '')
                phrase_stack.append(node)
                node.set_id('0')
                node.set_lex_data('')
                if level == 1:
                    nodes.append(node)
                else:
                    parent.add_child(node)
            elif parts[i].startswith(bracket_form_end):
                level -= 1
                if len(phrase_stack) == 0:
                    if error_log != None:
                        error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
                    else:
                        print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
                else:
                    pparts = parts[i].split(word_tag_separator)
                    if len(pparts) != 2:
                        if error_log != None:
                            error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
                        else:
                            print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
                    elif parent != None:
                        parent.set_name(pparts[1])
                        phrase_stack.pop()
                    else:
                        if error_log != None:
                            error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
                        else:
                            print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
                        phrase_stack.pop()
            elif word_tag_separator in parts[i]:
                if level == 0:
                    parent = None
                elif len(phrase_stack) == 0:
                    if error_log != None:
                        error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
                    else:
                        print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
                else:
                    parent = phrase_stack[-1]
                pparts = parts[i].split(word_tag_separator)
                if len(pparts) != 2:
                    if error_log != None:
                        error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
                    else:
                        print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
                else:
                    node = SSFLexItem(GlobalProperties.get_intl_string('0'), pparts[0], pparts[1], '')
                    if level == 0:
                        nodes.append(node)
                    else:
                        parent.add_child(node)
    if len(phrase_stack) > 0:
        if error_log != None:
            error_log.append(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string)
            error_log.append(GlobalProperties.get_intl_string('Error:_Wrong_format._Unmatching_brackets.\n'))
        else:
            print(GlobalProperties.get_intl_string('Error_in_the_bracket_form_file_(line-') + line_num + '): ' + string, file=sys.stderr)
            print(string + '\n')
    nodes.trim_to_size()
    return nodes

def make_string(self):
    ssfp = get_ssf_properties()
    field_separator_print = ssfp.get_properties().get_property_value_for_print('fieldSeparatorPrint')
    chunk_end = ssfp.get_properties().get_property_value_for_print('chunkEnd')
    root_name = ssfp.get_properties().get_property_value_for_print('rootName')
    string = ''
    if self.get_name() != root_name:
        string = make_top_string() + '\n'
    count = self.get_child_count()
    for i in range(count):
        string += self.get_child(i).make_string()
    if self.get_name() != root_name:
        string += field_separator_print + chunk_end + '\n'
    return string

def make_raw_sentence(self):
    leaves = get_all_leaves()
    raw_sen = self.get_lex_data()
    count = len(leaves)
    for i in range(count):
        if i == count - 1:
            raw_sen += leaves[i].get_lex_data()
        else:
            raw_sen += leaves[i].get_lex_data() + ' '
    return raw_sen
def reallocateId(self, parentId):
    count = self.getChildCount()
    for i in range(count):
        node = self.getChild(i)
        if self.getChild(i).isLeafNode():
            node.id = parentId + str(i + 1)
        else:
            node.id = parentId + str(i + 1)
            node.reallocateId(node.id + '.')

def getCopy(self):
    str = self.makeString()
    ssfNode = SSFPhrase()
    ssfNode.readString(str)
    ssfNode.flags = self.flags
    return ssfNode

def joinNodes(self, fromIndex, count):
    joinable = True
    chvec = self.getAllChildren()
    for i in range(fromIndex, fromIndex + count):
        if not isinstance(self.getChild(i), SSFLexItem):
            joinable = False
    if joinable:
        joinedNode = SSFLexItem()
        joinedNode.setName(self.getChild(fromIndex).getName())
        joinedNode.setFeatureStructures(self.getChild(fromIndex).getFeatureStructures())
        ld = ''
        for i in range(fromIndex, fromIndex + count):
            ld += self.getChild(i).getLexData()
        for i in range(count):
            self.remove(fromIndex)
        joinedNode.setLexData(ld)
        self.addChildAt(joinedNode, fromIndex)
    return joinable

def splitLexItem(self, index):
    splittable = isinstance(self.getChild(index), SSFLexItem)
    if splittable:
        node = self.getChild(index)
        node1 = SSFLexItem()
        node1.setName(node.getName())
        node1.setFeatureStructures(node.getFeatureStructures())
        if node.getFeatureStructures() is not None:
            try:
                node2 = SSFLexItem(node.getId(), node.getLexData(), node.getName(), node.getFeatureStructures().makeString())
            except Exception as ex:
                Logger.getLogger(SSFPhrase.__name__).log(Level.SEVERE, None, ex)
        else:
            node2 = SSFLexItem()
            node2.setLexData(node.getLexData())
            node2.setName(node.getName())
        ld = node.getLexData()
        lds = ld.split('-', 2)
        if lds is not None and len(lds) == 2:
            node1.setLexData(lds[0])
            node2.setLexData(lds[1])
        else:
            node1.setLexData(ld)
            node2.setLexData(ld)
        self.remove(index)
        self.addChildAt(node2, index)
        self.addChildAt(node1, index)
        self.reallocateId(self.getId())
    return splittable

def clear(self):
    super().clear()
    self.removeAllChildren()

def flatten(self):
    leaves = self.getAllLeaves()
    self.removeAllChildren()
    count = len(leaves)
    for i in range(count):
        self.addChild(leaves[i])

def flattenChunks(self):
    flattened = False
    if self.getParent() is not None and self.getLexData() == '':
        flattened = True
        self.removeLayer()
    for i in range(self.getChildCount()):
        cnode = self.getChild(i)
        if isinstance(cnode, SSFPhrase):
            cnode.flattenChunks()
    return flattened

def copyAttributesDep2Chunk(self, mmRoot, cfgToMMTreeMapping):
    depAttribs = FSProperties.getDependencyAttributes()
    mcount = mmRoot.getChildCount()
    for i in range(mcount):
        mnode = mmRoot.getChild(i)
        if isinstance(mnode, SSFPhrase):
            refAtVal = mnode.getOneOfAttributeValues(depAttribs)
            if refAtVal is None:
                self.copyAttributesDep2Chunk(mnode, cfgToMMTreeMapping)
                continue
            refVal = refAtVal[1]
            if refVal is None:
                self.copyAttributesDep2Chunk(mnode, cfgToMMTreeMapping)
                continue
            mnodeName = mnode.getAttributeValue('name')
            cnode = self.getNodeForAttribVal('name', mnodeName, True)
            if cnode is not None:
                cnode.setAttributeValue(refAtVal[0], refAtVal[1])
            self.copyAttributesDep2Chunk(mnode, cfgToMMTreeMapping)
def convertToGDepNode(self, cfgToMMTreeMapping: dict) -> SSFPhrase:
    return self.convertToGDepNode(cfgToMMTreeMapping, True)

def convertToGDepNode(self, cfgToMMTreeMapping: dict, collapse: bool) -> SSFPhrase:
    ssfp = self.getSSFProperties()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    if self.getName() == rootName or self.getParent() == None:
        self.reallocateNames(None, None)
    mmRoot = None
    try:
        mmRoot = SSFPhrase(self.getCopy())
    except Exception as ex:
        ex.printStackTrace()
    if cfgToMMTreeMapping != None:
        self.getMapping(self, mmRoot, cfgToMMTreeMapping)
    depAttribs = FSProperties.getDependencyTreeAttributes()
    drelNodes = mmRoot.getNodesForOneOfAttribs(depAttribs, True)
    namedNodesVec = mmRoot.getNodesForAttrib('name', True)
    if len(drelNodes) <= 0 or len(namedNodesVec) <= 0:
        return None
    namedNodes = {}
    count = len(namedNodesVec)
    for i in range(count):
        node = namedNodesVec[i]
        if isinstance(node, SSFPhrase):
            nm = node.getAttributeValue('name')
            namedNodes[nm] = node
    count = len(drelNodes)
    for i in range(count):
        node = drelNodes[i]
        if node.isLeafNode():
            continue
        else:
            drel = node.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(depAttribs).getAltValue(0).getValue()
            atval = drel.split(':')
            rel = atval[0]
            nm = ''
            if len(atval) == 1:
                nm = atval[0]
                rel = ''
            else:
                nm = atval[1]
            chunk = node.makeRawSentence()
            mmParent = namedNodes.get(nm)
            if mmParent == None:
                continue
            if collapse:
                mmParent.collapseLexicalItemsDeep()
            mmParent.add(node)
            if collapse:
                node.collapseLexicalItemsDeep()
    if mmRoot.getChildCount() == 0:
        return None
    if mmRoot.getChildCount() == 1 and collapse:
        mmRoot.collapseLexicalItemsDeep()
    return mmRoot

def convertToLDepNode(self, cfgToMMTreeMapping: dict) -> SSFPhrase:
    return self.convertToLDepNode(cfgToMMTreeMapping, True)

def convertToLDepNode(self, cfgToDepTreeMapping: dict, collapse: bool) -> SSFPhrase:
    ssfp = self.getSSFProperties()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    if self.getName() == rootName or self.getParent() == None:
        self.reallocateNames(None, None)
    mmRoot = None
    try:
        mmRoot = SSFPhrase(self.getCopy())
        mmRoot.flattenChunks()
    except Exception as ex:
        ex.printStackTrace()
    if cfgToDepTreeMapping != None:
        self.getMapping(self, mmRoot, cfgToDepTreeMapping)
    depAttribs = FSProperties.getDependencyTreeAttributes()
    drelNodes = mmRoot.getNodesForOneOfAttribs(depAttribs, True)
    namedNodesVec = mmRoot.getNodesForAttrib('name', True)
    if len(drelNodes) <= 0 or len(namedNodesVec) <= 0:
        return mmRoot
    namedNodes = {}
    count = len(namedNodesVec)
    for i in range(count):
        node = namedNodesVec[i]
        nm = node.getAttributeValue('name')
        namedNodes[nm] = node
    count = len(drelNodes)
    for i in range(count):
        node = drelNodes[i]
        if isinstance(node, SSFPhrase) and node.getLexData() == '':
            continue
        else:
            namedNode = namedNodes.get(node.getAttributeValue('name'))
            drel = namedNode.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(depAttribs).getAltValue(0).getValue()
            atval = drel.split(':')
            rel = atval[0]
            nm = ''
            if len(atval) == 1:
                nm = atval[0]
                rel = ''
            else:
                nm = atval[1]
            mmParent = namedNodes.get(nm)
            mmGrandParent = mmParent.getParent()
            if mmParent == None:
                continue
            if isinstance(mmParent, SSFLexItem):
                mmParentPhrase = SSFPhrase(mmParent.getId(), mmParent.getLexData(), mmParent.getName(), mmParent.getFeatureStructures())
                mmParentIndex = mmGrandParent.getIndex(mmParent)
                mmGrandParent.remove(mmParent)
                mmGrandParent.insert(mmParentPhrase, mmParentIndex)
                d2cTreeMap = UtilityFunctions.getReverseMap(cfgToDepTreeMapping)
                cfgNode = d2cTreeMap.get(mmParent)
                cfgToDepTreeMapping[cfgNode] = mmParentPhrase
                namedNodes[mmParent.getAttributeValue('name')] = mmParentPhrase
                mmParentPhrase.add(namedNode)
            else:
                mmParent.add(namedNode)
    if mmRoot.getChildCount() == 0:
        return None
    return mmRoot
class SSFPhrase(SuperClass1, SuperClass2, Interface1, Interface2):

    def convertToPSNode(self, cfgToPSTreeMapping):
        ssfp = self.getSSFProperties()
        rootName = ssfp.getProperties().getPropertyValue('rootName')
        if self.getName() == rootName or self.getParent() == None:
            self.reallocateNames(None, None)
        psRoot = None
        try:
            psRoot = self.getCopy()
        except Exception as ex:
            ex.printStackTrace()
        if cfgToPSTreeMapping != None:
            getMapping(self, psRoot, cfgToPSTreeMapping)
        psAttribs = FSProperties.getPSTreeAttributes()
        psrelNodes = psRoot.getNodesForOneOfAttribs(psAttribs, True)
        namedNodesVec = psRoot.getNodesForAttrib('name', True)
        if len(psrelNodes) <= 0 or len(namedNodesVec) <= 0:
            return None
        namedNodes = {}
        count = len(namedNodesVec)
        for i in range(count):
            node = namedNodesVec[i]
            nm = node.getFeatureStructures().getAltFSValue(0).getAttribute('name').getAltValue(0).getValue()
            namedNodes[nm] = node
        count = len(psrelNodes)
        for i in range(count):
            node = psrelNodes[i]
            psrel = node.getFeatureStructures().getAltFSValue(0).getOneOfAttributes(psAttribs).getAltValue(0).getValue()
            atval = psrel.split('[:]')
            if len(atval) != 2 or atval[1] == '':
                print(GlobalProperties.getIntlString('Wrong_value_of_attribute:_') + psrel)
                return None
            rel = atval[0]
            nm = atval[1]
            psParent = namedNodes.get(nm)
            if psParent == None:
                continue
            psParent.add(node)
        if psRoot.getChildCount() == 0:
            return None
        return psRoot

    def convertToPennDepNode(self):
        ssfp = self.getSSFProperties()
        rootName = ssfp.getProperties().getPropertyValue('rootName')
        rootCopy = None
        pdRoot = None
        try:
            rootCopy = self.getCopy()
        except Exception as ex:
            print(GlobalProperties.getIntlString('Error_in_node_copying'))
            ex.printStackTrace()
        pdNodes = rootCopy.getNodesForAttrib('penndep', True)
        namedNodesVec = rootCopy.getNodesForAttrib('name', True)
        if len(pdNodes) <= 0 or len(namedNodesVec) <= 0:
            return None
        namedNodes = {}
        parentNodes = {}
        namedPhraseNodes = {}
        count = len(namedNodesVec)
        namedPhraseNodesVec = []
        for i in range(count):
            node = namedNodesVec[i]
            nm = node.getFeatureStructures().getAltFSValue(0).getAttribute('name').getAltValue(0).getValue()
            namedNodes[nm] = node
            phraseNode = None
            try:
                phraseNode = SSFPhrase(node.getId(), node.getLexData(), node.getName(), node.getFeatureStructures())
            except Exception as ex:
                ex.printStackTrace()
            namedPhraseNodesVec.append(phraseNode)
            namedPhraseNodes[nm] = phraseNode
        count = len(pdNodes)
        for i in range(count):
            node = pdNodes[i]
            penndep = node.getFeatureStructures().getAltFSValue(0).getAttribute('penndep').getAltValue(0).getValue()
            atval = penndep.split('[:]')
            if len(atval) != 2 or atval[1] == '':
                print(GlobalProperties.getIntlString('Wrong_value_of_penndep_attribute:_') + penndep)
                return None
            rel = atval[0]
            nm = atval[1]
            phraseNode = namedPhraseNodesVec[i]
            prnt = namedNodes.get(nm)
            phraseParent = namedPhraseNodes.get(nm)
            if rel.equalsIgnoreCase('ROOT'):
                pdRoot = phraseNode
            else:
                phraseParent.add(phraseNode)
        return pdRoot

    def collapseLexicalItems(self):
        hasOnlyLeaves = True
        count = self.getChildCount()
        for i in range(count):
            if self.getChild(i).getClass().equals(SSFPhrase):
                hasOnlyLeaves = False
                break
        if hasOnlyLeaves and count > 0:
            rawString = self.makeRawSentence()
            self.setLexData(rawString)
            self.removeAllChildren()

    def collapseLexicalItemsDeep(self):
        count = self.getChildCount()
        for i in range(count):
            node = self.getChild(i)
            if isinstance(node, SSFPhrase):
                if node.hasLexItemChild():
                    rawString = ''
                    ccount = node.countChildren()
                    for j in range(ccount):
                        cnode = node.getChild(j)
                        if isinstance(cnode, SSFLexItem):
                            if j == ccount - 1:
                                rawString += cnode.getLexData()
                            else:
                                rawString += cnode.getLexData() + ' '
                            node.removeChild(j)
                            ccount -= 1
                    node.setLexData(rawString)
                node.collapseLexicalItemsDeep()

    def isTaggingSame(self, ch):
        if ch == None:
            return False
        if self.getDifferentPOSTags(ch) != None:
            return False
        return True

    def getDifferentPOSTags(self, ch):
        diff = None
        lvs = self.getAllLeaves()
        count = len(lvs)
        if ch == None:
            diff = [i for i in range(count)]
            return diff
        chlvs = ch.getAllLeaves()
        dvec = []
        for i in range(count):
            if lvs[i].getName().equalsIgnoreCase(chlvs[i].getName()) == False:
                dvec.append(i)
        count = len(dvec)
        if count <= 0:
            return None
        diff = [dvec[i] for i in range(count)]
        return diff
def get_lexical_sequence(self, tag, compound_tag):
    lex_seq = ''
    tp = re.compile(tag)
    ctp = re.compile(compound_tag)
    go_on = False
    scount = self.count_children()
    for i in range(scount):
        ssf_node = self.get_child(i)
        if isinstance(ssf_node, SSFLexItem):
            tm = tp.match(ssf_node.get_name())
            ctm = ctp.match(ssf_node.get_name())
            if tm or ctm:
                if go_on:
                    lex_seq += '_' + ssf_node.get_lex_data()
                else:
                    lex_seq = ssf_node.get_lex_data()
                go_on = True
            else:
                go_on = False
                if not lex_seq == '':
                    return lex_seq
    return lex_seq

def get_lex_data_for_tag(self, tag):
    scount = self.count_children()
    for i in range(scount):
        ssf_node = self.get_child(i)
        if isinstance(ssf_node, SSFLexItem) and ssf_node.get_name().lower() == tag.lower():
            return ssf_node.get_lex_data()
    return ''

def get_stem_for_tag(self, tag):
    scount = self.count_children()
    for i in range(scount):
        ssf_node = self.get_child(i)
        if isinstance(ssf_node, SSFLexItem) and ssf_node.get_name().lower() == tag.lower():
            return ssf_node.get_attribute_value('lex')
    return ''

def get_word_freq(self):
    words = {}
    leaves = self.get_all_leaves()
    lcount = len(leaves)
    for j in range(lcount):
        leaf_node = leaves[j]
        if isinstance(leaf_node, SSFLexItem):
            lex_data = leaf_node.get_lex_data()
            if lex_data not in words:
                words[lex_data] = 1
            else:
                words[lex_data] += 1
    return words

def get_pos_tag_freq(self):
    tags = {}
    leaves = self.get_all_leaves()
    lcount = len(leaves)
    for j in range(lcount):
        leaf_node = leaves[j]
        if isinstance(leaf_node, SSFLexItem):
            tag = leaf_node.get_name()
            if tag not in tags:
                tags[tag] = 1
            else:
                tags[tag] += 1
    return tags

def get_word_tag_pair_freq(self):
    words = {}
    leaves = self.get_all_leaves()
    lcount = len(leaves)
    for j in range(lcount):
        leaf_node = leaves[j]
        if isinstance(leaf_node, SSFLexItem):
            lex_data = leaf_node.get_lex_data()
            tag = leaf_node.get_name()
            word_tag_pair = lex_data + '/' + tag
            if word_tag_pair not in words:
                words[word_tag_pair] = 1
            else:
                words[word_tag_pair] += 1
    return words

def get_chunk_tag_freq(self):
    tags = {}
    all_children = self.get_all_children()
    lcount = len(all_children)
    for j in range(lcount):
        child_node = all_children[j]
        if isinstance(child_node, SSFPhrase):
            tag = child_node.get_name()
            if tag not in tags:
                tags[tag] = 1
            else:
                tags[tag] += 1
            tags.update(child_node.get_chunk_tag_freq())
    return tags

def get_group_relation_freq(self):
    rels = {}
    all_children = self.get_all_children()
    dep_attribs = FSProperties.get_dependency_attributes()
    lcount = len(all_children)
    for j in range(lcount):
        child_node = all_children[j]
        if isinstance(child_node, SSFPhrase):
            ref_at_val = child_node.get_one_of_attribute_values(dep_attribs)
            if ref_at_val is None:
                continue
            attrib = ref_at_val[0]
            val = ref_at_val[1]
            parts = val.split(':')
            attrib_val = attrib + '=' + parts[0]
            if attrib_val not in rels:
                rels[attrib_val] = 1
            else:
                rels[attrib_val] += 1
            rels.update(child_node.get_group_relation_freq())
    return rels

def get_chunk_relation_freq(self):
    rels = {}
    all_children = self.get_all_children()
    dep_attribs = FSProperties.get_dependency_attributes()
    lcount = len(all_children)
    for j in range(lcount):
        child_node = all_children[j]
        if isinstance(child_node, SSFPhrase):
            ref_at_val = child_node.get_one_of_attribute_values(dep_attribs)
            if ref_at_val is None:
                continue
            chunk = child_node.make_raw_sentence()
            attrib = ref_at_val[0]
            val = ref_at_val[1]
            parts = val.split(':')
            attrib_val = chunk + '::' + attrib + '=' + parts[0]
            if attrib_val not in rels:
                rels[attrib_val] = 1
            else:
                rels[attrib_val] += 1
            rels.update(child_node.get_chunk_relation_freq())
    return rels

def get_attribute_freq(self):
    attribs = {}
    all_children = self.get_all_children()
    lcount = len(all_children)
    for j in range(lcount):
        child_node = all_children[j]
        attrib_names = child_node.get_attribute_names()
        if attrib_names is None:
            continue
        acount = len(attrib_names)
        for i in range(acount):
            attrib = attrib_names[i]
            if attrib not in attribs:
                attribs[attrib] = 1
            else:
                attribs[attrib] += 1
        if isinstance(child_node, SSFPhrase):
            attribs.update(child_node.get_attribute_freq())
    return attribs

def get_attribute_value_freq(self):
    attribs = {}
    all_children = self.get_all_children()
    lcount = len(all_children)
    for j in range(lcount):
        child_node = all_children[j]
        attrib_vals = child_node.get_attribute_values()
        if attrib_vals is None:
            continue
        acount = len(attrib_vals)
        for i in range(acount):
            attrib = attrib_vals[i]
            if attrib not in attribs:
                attribs[attrib] = 1
            else:
                attribs[attrib] += 1
        if isinstance(child_node, SSFPhrase):
            attribs.update(child_node.get_attribute_value_freq())
    return attribs
from collections import OrderedDict

class SSFNode:

    def __init__(self, id, name, tag, lexData):
        self.id = id
        self.name = name
        self.tag = tag
        self.lexData = lexData
        self.attributes = OrderedDict()
        self.children = []

    def getAttribute(self, attributeName):
        return self.attributes.get(attributeName)

    def setAttribute(self, attributeName, attributeValue):
        self.attributes[attributeName] = attributeValue

    def addChild(self, childNode):
        self.children.append(childNode)

    def getChild(self, index):
        return self.children[index]

    def countChildren(self):
        return len(self.children)

    def getAllChildren(self):
        allChildren = []
        for child in self.children:
            allChildren.append(child)
            allChildren.extend(child.getAllChildren())
        return allChildren

    def getAttributeValuePairs(self):
        attribVals = list(self.attributes.values())
        return attribVals

class SSFPhrase(SSFNode):

    def __init__(self, id, name, tag, lexData):
        super().__init__(id, name, tag, lexData)

    def getDOMElement(self):
        domElement = super().getDOMElement()
        count = self.countChildren()
        for i in range(count):
            child = self.getChild(i)
            idomElement = child.getDOMElement()
            domElement.add(idomElement)
        return domElement

    def getTypeCraftDOMElement(self):
        domElement = super().getTypeCraftDOMElement()
        count = self.countChildren()
        for i in range(count):
            child = self.getChild(i)
            idomElement = child.getTypeCraftDOMElement()
            domElement.add(idomElement)
        return domElement

    def getGATEDOMElement(self):
        domElement = super().getGATEDOMElement()
        count = self.countChildren()
        for i in range(count):
            child = self.getChild(i)
            idomElement = child.getGATEDOMElement()
            domElement.add(idomElement)
        return domElement

class SSFLexItem(SSFNode):

    def __init__(self, id, name, tag, lexData):
        super().__init__(id, name, tag, lexData)

class BhaashikMutableTreeNode:

    def __init__(self):
        self.children = []

    def getAllLeaves(self):
        leaves = []
        for child in self.children:
            if isinstance(child, BhaashikMutableTreeNode):
                leaves.extend(child.getAllLeaves())
            else:
                leaves.append(child)
        return leaves

class SSFSuperClass(BhaashikMutableTreeNode):

    def __init__(self):
        super().__init__()

    def getAttributeValuePairFreq(self):
        attribs = OrderedDict()
        allChildren = self.getAllChildren()
        lcount = len(allChildren)
        for j in range(lcount):
            childNode = allChildren[j]
            attribVals = childNode.getAttributeValuePairs()
            if attribVals is None:
                continue
            acount = len(attribVals)
            for i in range(acount):
                attrib = attribVals[i]
                if attrib not in attribs:
                    attribs[attrib] = 1
                else:
                    attribs[attrib] += 1
            if isinstance(childNode, SSFPhrase):
                attribs.update(childNode.getAttributeValuePairFreq())
        return attribs

    def getUnchunkedWordFreq(self):
        tags = OrderedDict()
        leaves = self.getAllLeaves()
        lcount = len(leaves)
        for j in range(lcount):
            leafNode = leaves[j]
            if isinstance(leafNode, SSFLexItem):
                lexData = leafNode.lexData
                if leafNode.parent is not None and leafNode.parent.parent is None:
                    if lexData not in tags:
                        tags[lexData] = 1
                    else:
                        tags[lexData] += 1
        return tags

    def reallocatePositions(self, positionAttribName, nullWordString):
        leaves = self.getAllLeaves()
        lcount = len(leaves)
        for j in range(lcount):
            leafNode = leaves[j]

    def convertEncoding(self, encodingConverter, nullWordString):
        leaves = self.getAllLeaves()
        lcount = len(leaves)
        for j in range(lcount):
            leafNode = leaves[j]
            if isinstance(leafNode, SSFLexItem) and leafNode.lexData.equalsIgnoreCase(nullWordString) == False:
                convertedLexData = encodingConverter.convert(leafNode.lexData)
                leafNode.lexData = convertedLexData
                convertedLex = encodingConverter.convert(leafNode.getAttribute('lex'))
                if convertedLex is not None and convertedLex != '':
                    leafNode.setAttribute('lex', convertedLex)
                convertedTAM = encodingConverter.convert(leafNode.getAttribute('tam'))
                if convertedTAM is not None and convertedTAM != '':
                    leafNode.setAttribute('tam', convertedTAM)
                convertedVib = encodingConverter.convert(leafNode.getAttribute('vib'))
                if convertedVib is not None and convertedVib != '':
                    leafNode.setAttribute('vib', convertedVib)
                convertedName = encodingConverter.convert(leafNode.getAttribute('name'))
                if convertedName is not None and convertedName != '':
                    leafNode.setAttribute('name', convertedName)

    def getXML(self):
        xmlString = ''
        element = self.getDOMElement()
        xmlString = element.asXML()
        return '\n' + xmlString + '\n'

    def getTypeCraftXML(self):
        xmlString = ''
        element = self.getTypeCraftDOMElement()
        xmlString = element.asXML()
        return '\n' + xmlString + '\n'

    def getGATEXML(self):
        xmlString = ''
        element = self.getGATEDOMElement()
        xmlString = element.asXML()
        return '\n' + xmlString + '\n'

    def readXML(self, domElement):
        xmlProperties = self.getXMLProperties()
        super().readXML(domElement)
        node = domElement.firstChild
        while node is not None:
            if isinstance(node, Element):
                element = node
                tag = xmlProperties.getProperties().getPropertyValue('nodeTag')
                if element.tagName == tag:
                    child = None
                    if XMLUtils.hasChileNode(element, tag):
                        try:
                            child = SSFPhrase('0', '', 'NP', '')
                            child.readXML(element)
                            self.addChild(child)
                        except Exception as ex:
                            getLogger().log(Level.SEVERE, None, ex)
                    else:
                        try:
                            child = SSFLexItem('0', '', 'NN', '')
                            child.readXML(element)
                            self.addChild(child)
                        except Exception as ex:
                            getLogger().log(Level.SEVERE, None, ex)
            node = node.nextSibling

    def readTypeCraftXML(self, domElement):
        xmlProperties = self.getXMLProperties()
        super().readTypeCraftXML(domElement)
        domAttribs = domElement.attributes
        acount = len(domAttribs)
        for i in range(acount):
            node = domAttribs.item(i)
            name = node.nodeName
            value = node.nodeValue
            if name is not None:
                if name == 'text':
                    self.lexData = value
                elif value is not None:
                    self.setAttribute(name, value)
                else:
                    self.setAttribute(name, '')
        node = domElement.firstChild
        while node is not None:
            if isinstance(node, Element):
                element = node
                tag = xmlProperties.getProperties().getPropertyValue('tcMorphemeTag')
                if element.tagName == tag:
                    try:
                        child = SSFLexItem('0', '', 'NN', '')
                        child.readTypeCraftXML(element)
                        self.addChild(child)
                    except Exception as ex:
                        getLogger().log(Level.SEVERE, None, ex)
                tag = xmlProperties.getProperties().getPropertyValue('tcPOSTag')
                if element.tagName == tag:
                    self.name = element.text.strip()
            node = node.nextSibling
from abc import ABC, abstractmethod
import logging
import re
from typing import List, Union
from bhaashik.GlobalProperties import GlobalProperties
from bhaashik.corpus.parallel import Alignable, AlignmentUnit
from bhaashik.corpus.ssf.features.impl import FSProperties, FeatureAttributeImpl, FeatureStructureImpl, FeatureStructuresImpl, FeatureValueImpl
from bhaashik.corpus.ssf.features import FeatureAttribute, FeatureStructure, FeatureStructures, FeatureValue
from bhaashik.corpus.ssf import SSFNode, SSFProperties
from bhaashik.corpus.ssf.query import QueryValue
from bhaashik.corpus.ssf.tree import SSFLexicalItem
from bhaashik.text.enc.conv import BhaashikEncodingConverter
from bhaashik.tree import BhaashikMutableTreeNode
from bhaashik.util.query import FindReplace, FindReplaceOptions
from bhaashik.util.UtilityFunctions import UtilityFunctions
from bhaashik.xml import XMLProperties
from bhaashik.xml.dom import BhaashikDOMElement, GATEDOMElement, TypeCraftDOMElement
from org.dom4j.dom import DOMElement
from org.w3c.dom import Element, NamedNodeMap, Node
from xml.dom.minidom import Element as MinidomElement

class SSFPhrase(SSFNode, MutableTreeNode, Alignable, Serializable, QueryValue, BhaashikDOMElement, TypeCraftDOMElement, GATEDOMElement):

    def __init__(self, userObject=None, allowsChildren=True):
        super().__init__(userObject=userObject, allowsChildren=allowsChildren)

    def countChildren(self) -> int:
        return self.getChildCount()

    def addChild(self, c) -> int:
        self.add(c)
        return self.getChildCount()

    def addChildren(self, c) -> int:
        for child in c:
            self.add(child)
        return self.getChildCount()

    def addChildAt(self, c, index) -> int:
        self.insert(c, index)
        return self.getChildCount()

    def addChildrenAt(self, c, index) -> int:
        for i, child in enumerate(c, start=index):
            self.insert(child, i)
        return self.getChildCount()

    def getChild(self, index) -> SSFNode:
        return self.getChildAt(index)

    def findChild(self, aChild) -> int:
        if aChild is None:
            raise ValueError(GlobalProperties.getIntlString('argument_is_null'))
        if not self.isNodeChild(aChild):
            return -1
        count = self.getChildCount()
        for i in range(count):
            if self.getChild(i) is aChild:
                return i
        return -1

    def findLeafByID(self, id: str) -> SSFNode:
        leaves = self.getAllLeaves()
        for node in leaves:
            if node.getId() == id:
                return node
        return None

    def findLeafIndexByID(self, id: str) -> int:
        leaves = self.getAllLeaves()
        for i, node in enumerate(leaves):
            if node.getId() == id:
                return i
        return -1

    def findChildByID(self, id: str) -> SSFNode:
        allChildren = self.getAllChildren()
        for child in allChildren:
            if child.getId() == id:
                return child
        return None

    def findChildIndexByID(self, id: str) -> int:
        allChildren = self.getAllChildren()
        for i, child in enumerate(allChildren):
            if child.getId() == id:
                return i
        return -1

    def findNodeByID(self, id: str) -> SSFNode:
        if self.getId() == id:
            return self
        allChildren = self.getAllChildren()
        for child in allChildren:
            if isinstance(child, SSFLexItem):
                if child.getId() == id:
                    return child
            elif isinstance(child, SSFPhrase):
                node = child.findNodeByID(id)
                if node is not None:
                    return node
        return None

    def findNodeIndexByID(self, id: str) -> int:
        if self.getId() == id:
            parent = self.getParent()
            if parent is None:
                return 0
            else:
                return parent.findChild(self)
        allChildren = self.getAllChildren()
        for i, child in enumerate(allChildren):
            if isinstance(child, SSFLexItem):
                if child.getId() == id:
                    return i
            elif isinstance(child, SSFPhrase):
                index = child.findNodeIndexByID(id)
                if index != -1:
                    return index
        return -1

    def findLeafByName(self, n: str) -> SSFNode:
        leaves = self.getAllLeaves()
        for node in leaves:
            nodeName = node.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return node
        return None

    def findLeafIndexByName(self, n: str) -> int:
        leaves = self.getAllLeaves()
        for i, node in enumerate(leaves):
            nodeName = node.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return i
        return -1

    def findChildByName(self, n: str) -> SSFNode:
        allChildren = self.getAllChildren()
        for child in allChildren:
            nodeName = child.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return child
        return None

    def findChildIndexByName(self, n: str) -> int:
        allChildren = self.getAllChildren()
        for i, child in enumerate(allChildren):
            nodeName = child.getAttributeValue('name')
            if nodeName is not None and nodeName == n:
                return i
        return -1