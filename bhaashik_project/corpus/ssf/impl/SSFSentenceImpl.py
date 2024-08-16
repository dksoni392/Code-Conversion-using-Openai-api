class SSFSentenceImpl(Sentence, QueryValue, SSFSentence, BhaashikDOMElement, TypeCraftDOMElement, GATEDOMElement):

    def __init__(self):
        super().__init__()
        self.id = ''
        self.root = None
        self.featureStructure = FeatureStructureImpl()
        self.alignmentUnit = AlignmentUnit[SSFSentence]()
        self.emptyPhrasesAllowed = False

    def getFeatureStructure(self):
        return self.featureStructure

    def setFeatureStructure(self, featureStructure):
        self.featureStructure = featureStructure
        fvID = featureStructure.getAttributeValue('id')
        if fvID:
            self.setId(fvID.getValue())

    def getId(self):
        return self.id

    def setId(self, i):
        self.id = i
        self.featureStructure.addAttribute('id', self.id)

    def getRoot(self):
        return self.root

    def setRoot(self, r):
        self.root = r

    def emptyPhrasesAllowed(self):
        return self.emptyPhrasesAllowed

    def setEmptyPhrasesAllowed(self, e):
        self.emptyPhrasesAllowed = e

    def findLeafByID(self, id):
        return self.getRoot().findLeafByID(id)

    def findLeafIndexByID(self, id):
        return self.getRoot().findLeafIndexByID(id)

    def findLeafByName(self, id):
        return self.getRoot().findLeafByName(id)

    def findLeafIndexByName(self, id):
        return self.getRoot().findLeafIndexByName(id)

    def findNodeByID(self, id):
        return self.getRoot().findNodeByID(id)

    def findNodeIndexByID(self, id):
        return self.getRoot().findNodeIndexByID(id)def findNodeIndexByName(self, id):
    return
    self.getRoot().findNodeIndexByName(id)

def findChildByID(self, id):
    return self.getRoot().findChildByID(id)

def findChildIndexByID(self, id):
    return self.getRoot().findChildIndexByID(id)

def findChildByName(self, id):
    return self.getRoot().findChildByName(id)

def findChildIndexByName(self, id):
    return self.getRoot().findChildIndexByName(id)

def find(self, nlabel, ntext, attrib, val, nlabelReplace, ntextReplace, attribReplace, valReplace, replace, createAttrib, exactMatch):
    self.root = self.getRoot()
    senNodes = None
    senNodesForLabel = None
    if nlabel != '[.]*':
        senNodesForLabel = self.root.getNodesForName(nlabel)
        if senNodesForLabel is not None and len(senNodesForLabel) > 0:
            senNodes = senNodesForLabel
    senNodesForText = None
    if ntext != '[.]*':
        senNodesForText = self.root.getNodesForText(ntext)
        if senNodesForText is not None:
            senNodes = set(senNodes).intersection(senNodesForText)
        if nlabel == '[.]*' and senNodes is None and (senNodesForText is not None) and (len(senNodesForText) > 0):
            senNodes = senNodesForText
    senNodesForAttrib = None
    if attrib != '[.]*' and val == '[.]*':
        senNodesForAttrib = self.root.getNodesForAttrib(attrib, exactMatch)
        if senNodesForAttrib is not None and (not createAttrib):
            senNodes = set(senNodes).intersection(senNodesForAttrib)
        if nlabel == '[.]*' and ntext == '[.]*' and (senNodes is None) and (senNodesForAttrib is not None) and (len(senNodesForAttrib) > 0):
            senNodes = senNodesForAttrib
    senNodesForVal = None
    if val != '[.]*':
        senNodesForVal = self.root.getNodesForAttribVal(attrib, val, exactMatch)
        if senNodesForVal is not None:
            senNodes = set(senNodes).intersection(senNodesForVal)
        if nlabel == '[.]*' and ntext == '[.]*' and (attrib == '[.]*') and (senNodes is None) and (senNodesForVal is not None) and (len(senNodesForVal) > 0):
            senNodes = senNodesForVal
    if senNodes is not None:
        if replace:
            repCount = len(senNodes)
            for j in range(repCount):
                mnode = senNodes[j]
                if UtilityFunctions.backFromExactMatchRegex(nlabel) != nlabelReplace:
                    mnode.replaceNames(nlabel, nlabelReplace)
                if UtilityFunctions.backFromExactMatchRegex(ntext) != ntextReplace:
                    mnode.replaceLexData(ntext, ntextReplace)
                if UtilityFunctions.backFromExactMatchRegex(attrib) != attribReplace or val != valReplace:
                    mnode.replaceAttribVal(attrib, val, attribReplace, valReplace, createAttrib)
    return senNodes

def findContext(self, contextOptions, exactMatch):
    senNodes = None
    matchedNodes = None
    nodeOptions = contextOptions.getThisNodeOptions()
    nlabel = '[.]*'
    ntext = '[.]*'
    attrib = '[.]*'
    val = '[.]*'
    nlabelReplace = ''
    ntextReplace = ''
    attribReplace = ''
    valReplace = ''
    ntext = nodeOptions.getLexData()
    nlabel = nodeOptions.getTag()
    matchedNodes = self.find(nlabel, ntext, attrib, val, nlabelReplace, ntextReplace, attribReplace, valReplace, False, False, exactMatch)
    return matchedNodes

def readFile(self, fileName):
    self.clear()
    sentences = self.readSentencesFromFile(fileName)
    if sentences is None or len(sentences) < 1:
        raise Exception()
    sentence = sentences[0]
    id = sentence.getId()
    self.root = sentence.getRoot()

def readString(self, ssfString):
    self.clear()
    sentences = self.readSentencesFromString(ssfString)
    if sentences is None or len(sentences) < 1:
        raise Exception()
    sentence = sentences[0]
    id = sentence.getId()
    self.root = sentence.getRoot()def readString(self, string, errorLog, lineNum):
    self.clear()
    sentences = self.readSentencesFromString(string, errorLog, lineNum)
    if sentences == None or len(sentences) < 1:
        if errorLog != None:
            errorLog.append('************\n')
            errorLog.append(GlobalProperties.getIntlString('Empty_sentences_starting_from_line_') + str(lineNum) + ': \n\n')
            errorLog.append(string)
            errorLog.append('************\n')
        else:
            print('************')
            print(GlobalProperties.getIntlString('Empty_sentences_starting_from_line_') + str(lineNum) + ': \n\n')
            print(string)
            print('************')
        ssfp = SSFNode.getSSFProperties()
        rootName = ssfp.getProperties().getPropertyValue('rootName')
        rnode = SSFPhrase('0', '', rootName, '')
        self.setRoot(rnode)
    else:
        sentence = SSFSentenceImpl(sentences.get(0))
        self.id = sentence.getId()
        self.setFeatureStructure(sentence.getFeatureStructure())
        self.root = sentence.getRoot()

def readString(self, string):
    self.readString(string, None, 1)

@staticmethod
def readSentencesFromFile(fileName, errorLog):
    ssfp = SSFNode.getSSFProperties()
    lnReader = BufferedReader(InputStreamReader(FileInputStream(fileName), ssfp.getProperties().getPropertyValue('encoding')))
    line = ''
    lines = ''
    line = lnReader.readLine()
    while line != None:
        if not line.equals(''):
            lines += line + '\n'
        line = lnReader.readLine()
    return self.readSentencesFromString(lines, errorLog, 1)

@staticmethod
def readSentencesFromFile(fileName):
    return self.readSentencesFromFile(fileName, None)

@staticmethod
def readSentencesFromString(string, errorLog, lineNum):
    ssfp = SSFNode.getSSFProperties()
    sentences = []
    lines = ''
    lineArray = string.split('\n')
    sentenceStart = ssfp.getProperties().getPropertyValue('sentenceStart')
    sentenceEnd = ssfp.getProperties().getPropertyValue('sentenceEnd')
    start = False
    sentence = None
    for i in range(len(lineArray)):
        lineArray[i] = lineArray[i].strip()
        if lineArray[i].startsWith(sentenceStart):
            start = True
            sentence = SSFSentenceImpl()
            lines = ''
            p = re.compile('\\s+[^=]+=[^=^>^ ]+')
            fvString = ''
            m = p.matcher(lineArray[i])
            while m.find():
                fvString += m.group(0).strip() + ' '
            sentence.featureStructure.readStringFV(fvString.replaceAll('>', '').strip())
            tmp = lineArray[i].split('id=')
            if len(tmp) == 2:
                tmp = tmp[1].split('"')
                if len(tmp) > 2:
                    sentence.setId(tmp[1])
        elif lineArray[i].startsWith(sentenceEnd) and lines.equals('') == False:
            chunkStart = ssfp.getProperties().getPropertyValue('chunkStart')
            rootName = ssfp.getProperties().getPropertyValue('rootName')
            nodes = SSFPhrase.readNodesFromString(lines, errorLog, lineNum + i)
            if nodes != None and len(nodes) > 0:
                if len(nodes) == 1 and isinstance(nodes.get(0), SSFPhrase) and nodes.get(0).getName().equals(rootName):
                    sentence.setRoot(nodes.get(0))
                    sentence.getRoot().removeEmptyPhrases()
                else:
                    rnode = SSFPhrase('0', '', rootName, '')
                    for j in range(len(nodes)):
                        rnode.addChild(nodes.get(j))
                    sentence.setRoot(rnode)
                sentences.append(sentence)
            sentence = None
        elif start == True:
            lines += lineArray[i] + '\n'
    sentences.trimToSize()
    return sentences

@staticmethod
def readSentencesFromString(string):
    return self.readSentencesFromString(string, None, 1)

def makeSentenceFromRaw(self, rawSentence):
    ssfp = SSFNode.getSSFProperties()
    self.clear()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    self.root = SSFPhrase('0', '', rootName, '')
    words = rawSentence.split(' ')
    for i in range(len(words)):
        if words[i].equals('') == False:
            self.root.addChild(SSFLexItem('', words[i], '', ''))
    self.root.reallocateId('')

def makeSentenceFromPOSTagged(self, posTagged, errorLog, lineNum):
    ssfp = SSFNode.getSSFProperties()
    self.clear()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    wordTagSeparator = ssfp.getProperties().getPropertyValueForPrint('wordTagSeparator')
    self.root = SSFPhrase('0', '', rootName, '')
    words = posTagged.split(' ')
    for i in range(len(words)):
        wt = words[i].split('[' + wordTagSeparator + ']')
        if len(wt) == 0:
            continue
        if len(wt) != 2:
            if errorLog == None:
                print('Wrong_format:_Data_is_not_in_simple_word/tag_format.')
            else:
                errorLog.append('Wrong_format:_Data_is_not_in_simple_word/tag_format.\n')
            self.root.addChild(SSFLexItem('', wt[0], '', ''))
        else:
            self.root.addChild(SSFLexItem('', wt[0], wt[1], ''))
    self.root.reallocateId('')def makeSentenceFromChunked(chunked, errorLog, lineNum):
    ssfp = SSFNode.getSSFProperties()
    clear()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    nodes = SSFPhrase.readNodesFromChunked(chunked, errorLog, lineNum)
    if nodes != None and len(nodes) > 0:
        if len(nodes) == 1 and isinstance(nodes.get(0), SSFPhrase) and (nodes.get(0).getName() == rootName):
            setRoot(nodes.get(0))
            getRoot().removeEmptyPhrases()
        else:
            rnode = SSFPhrase('0', '', rootName, '')
            for j in range(len(nodes)):
                rnode.addChild(nodes.get(j))
            setRoot(rnode)

def makeSentenceFromStanfordPSG(parsed, errorLog, lineNum):
    ssfp = SSFNode.getSSFProperties()
    clear()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    nodes = SSFPhrase.readNodesFromChunked(parsed, errorLog, lineNum)
    if nodes != None and len(nodes) > 0:
        if len(nodes) == 1 and isinstance(nodes.get(0), SSFPhrase) and (nodes.get(0).getName() == rootName):
            setRoot(nodes.get(0))
            getRoot().removeEmptyPhrases()
        else:
            rnode = SSFPhrase('0', '', rootName, '')
            for j in range(len(nodes)):
                rnode.addChild(nodes.get(j))
            setRoot(rnode)

def markSentenceWithStanfordDG(chunked, errorLog, lineNum):
    ssfp = SSFNode.getSSFProperties()
    clear()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    nodes = SSFPhrase.readNodesFromChunked(chunked, errorLog, lineNum)
    if nodes != None and len(nodes) > 0:
        if len(nodes) == 1 and isinstance(nodes.get(0), SSFPhrase) and (nodes.get(0).getName() == rootName):
            setRoot(nodes.get(0))
            getRoot().removeEmptyPhrases()
        else:
            rnode = SSFPhrase('0', '', rootName, '')
            for j in range(len(nodes)):
                rnode.addChild(nodes.get(j))
            setRoot(rnode)

def checkForStart(punct, word, node):
    if word.startswith(punct) == True:
        punctWord = word.split('[' + punct + ']', 2)
        node.add(punct)
        return punctWord[1]
    return word

def checkForEnd(punct, word, node):
    if word.endswith(punct) == True:
        punctWord = word.split(punct, 2)
        node.add(punct)
        return punctWord[1]
    return word

def checkIntermediate(punct, word, node):
    if word != '':
        if word.contains(punct) == True:
            punctWord = word.split('[' + punct + ']', 2)
            node.add(punctWord[0])
            node.add(punct)
            if len(punctWord) == 2 and punctWord[1] != '':
                return punctWord[1]
            else:
                return ''
        else:
            return word
    return ''

def print(ps):
    ps.print(makeString())

def print(ps, corpusType):
    if corpusType == CorpusType.SSF_FORMAT:
        print(ps)
    elif corpusType == CorpusType.CHUNKED:
        ps.print(convertToBracketForm(1))
    elif corpusType == CorpusType.POS_TAGGED:
        ps.print(convertToPOSTagged())
    elif corpusType == CorpusType.RAW and getRoot() != None:
        ps.print(convertToRawText())class MyClass(SSFNode, ConvertToBracketForm, MakePOSNolex, ConvertToLowerCase, ConvertToPOSNolex, ConvertToPOSTagged):

    def __init__(self):
        self.id = ''

    def toString(self):
        return self.convertToBracketForm(1)

    def makeString(self):
        self.root.reallocateId('')
        ssfp = SSFNode.getSSFProperties()
        sentenceStart = ssfp.getProperties().getPropertyValueForPrint('sentenceStart')
        sentenceEnd = ssfp.getProperties().getPropertyValueForPrint('sentenceEnd')
        if sentenceStart.startswith('<'):
            if self.featureStructure.countAttributes() == 0:
                sentenceStart += ' id="' + self.id + '">'
            else:
                sentenceStart += ' ' + self.featureStructure.makeStringFV() + '>'
            sentenceEnd += '>'
        else:
            sentenceStart += ' ' + self.id
        SSF = ''
        SSF += sentenceStart + '\n'
        rootString = self.root.makeString()
        if rootString == '':
            print(GlobalProperties.getIntlString('Empty_sentence:_id=') + self.id)
        SSF += rootString
        SSF += sentenceEnd + '\n'
        return SSF

    def convertToBracketForm(self, spaces):
        bracketForm = ''
        bracketForm += self.getRoot().convertToBracketForm(spaces) + '\n'
        return bracketForm

    def makePOSNolex(self):
        return self.root.makePOSNolex()

    def convertToLowerCase(self):
        self.root.convertToLowerCase()

    def convertToPOSNolex(self):
        self.root.convertToPOSNolex()

    def convertToPOSTagged(self, sep):
        return self.root.convertToPOSTagged(sep)

    def convertToPOSTagged(self):
        return self.root.convertToPOSTagged()class Sentence:

    def __init__(self):
        self.root = None

    def convert_sentence_string(self, sentence_string, in_corpus_type, out_corpus_type):
        converted_string = ''
        sen = SSFSentenceImpl()
        try:
            if in_corpus_type == CorpusType.SSF_FORMAT:
                sen.read_string(sentence_string)
            elif in_corpus_type == CorpusType.CHUNKED:
                sen.make_sentence_from_pos_tagged(sentence_string)
            elif in_corpus_type == CorpusType.POS_TAGGED:
                sen.make_sentence_from_chunked(sentence_string)
            elif in_corpus_type == CorpusType.RAW and sen.get_root() is not None:
                sen.make_sentence_from_raw(sentence_string)
            if out_corpus_type == CorpusType.SSF_FORMAT:
                converted_string = sen.make_string()
            elif out_corpus_type == CorpusType.CHUNKED:
                converted_string = sen.convert_to_pos_tagged()
            elif out_corpus_type == CorpusType.POS_TAGGED():
                converted_string = sen.convert_to_bracket_form(1)
            elif out_corpus_type == CorpusType.RAW and sen.get_root() is not None:
                converted_string = sen.convert_to_raw_text()
        except Exception as ex:
            ex.printStackTrace()
        return converted_string

    def get_unannotated(self):
        sen = ''
        leaves = self.root.get_all_leaves()
        for i in range(len(leaves)):
            sen += leaves[i].get_lex_data()
        return sen

    def get_copy(self):
        str = self.make_string()
        sen = SSFSentenceImpl()
        sen.read_string(str)
        sen.get_root().allow_nested_fs(self.get_root().allows_nested_fs())
        sen.get_root().copy_extra_data(self.get_root())
        sen.alignment_unit = alignment_unit.clone()
        return sen

    def clear(self):
        self.id = ''
        self.root = None

    def clear_feature_structures(self):
        self.get_root().clear_feature_structures()
        count = self.get_root().count_children()
        for i in range(count):
            self.get_root().get_child(i).add_default_attributes()

    def clear_annotation(self, anno_level_flags):
        self.get_root().clear_annotation(anno_level_flags)

    def matches(self, find_replace_options):
        if self.root is None:
            return False
        return self.root.matches(find_replace_options)

    def set_morph_tags(self, morph_tags):
        self.root.set_morph_tags(morph_tags)def getAlignedObject(self, alignmentKey):
    return self.alignmentUnit.getAlignedObject(alignmentKey)

def getAlignedObjects(self):
    return self.alignmentUnit.getAlignedObjects()

def getFirstAlignedObject(self):
    return self.alignmentUnit.getFirstAlignedObject()

def getAlignedObject(self, i):
    return self.alignmentUnit.getAlignedObject(i)

def getLastAlignedObject(self):
    return self.alignmentUnit.getLastAlignedObject()

def getAlignmentUnit(self):
    return self.alignmentUnit

def setAlignmentUnit(self, alignmentUnit):
    self.alignmentUnit = alignmentUnit

def loadAlignments(self, tgtSentence, parallelIndex):
    if self.getRoot() == None:
        return
    self.getRoot().loadAlignments(self, tgtSentence, parallelIndex)def clearAlignments():
    if self.getRoot() is None:
        return
    self.getRoot().clearAlignments(0, self.getRoot().countChildren())

def getQueryReturnValue():
    return self

def setQueryReturnValue(rv):
    pass

def getQueryReturnObject():
    return self

def setQueryReturnObject(rv):
    pass

def setAttributeValue(attibName, val):
    if self.featureStructure is None:
        self.featureStructure = FeatureStructureImpl()
    self.featureStructure.setAttributeValue(attibName, val)

def getMatchingValues(ssfQuery):
    rNode = self.getRoot()
    matches = {}
    if ssfQuery.getRootMatchNode().getOperator() == SSFQueryOperatorType.ON_DS:
        cfgToMMTreeMapping = {}
        mmNode = rNode.convertToGDepNode(cfgToMMTreeMapping, False)
        if mmNode is None:
            return matches
        matches = mmNode.getMatchingValues(ssfQuery)
    elif ssfQuery.getRootMatchNode().getOperator() == SSFQueryOperatorType.COMMAND:
        commandString = ssfQuery.getRootMatchNode().getUserObject()
        cmdReallocateIDsMatcher = SSFQueryLexicalAnalyser().cmdReallocateIDs.match(commandString)
        cmdReallocateNamesMatcher = SSFQueryLexicalAnalyser().cmdReallocateNames.match(commandString)
        cmdReallocatePosMatcher = SSFQueryLexicalAnalyser().cmdReallocatePosn.match(commandString)
        if cmdReallocateIDsMatcher.find():
            self.getRoot().reallocateId('')
        elif cmdReallocateNamesMatcher.find():
            self.getRoot().reallocateNames(None, None)
        elif cmdReallocatePosMatcher.find():
            self.getRoot().reallocatePositions('posn', 'null')
    else:
        matches = rNode.getMatchingValues(ssfQuery)
    return matches

def reallocatePositions(positionAttribName, nullWordString):
    self.getRoot().reallocatePositions(positionAttribName, nullWordString)def getDOMElement(self):
    xmlProperties = SSFNode.getXMLProperties()
    domElement = DOMElement(xmlProperties.getProperties().getPropertyValue('sentenceTag'))
    attribDOM = DOMAttribute(org.dom4j.QName('id'), id)
    domElement.add(attribDOM)
    idomElement = featureStructure.getDOMElement()
    domElement.add(idomElement)
    idomElement = root.getDOMElement()
    domElement.add(idomElement)
    return domElement

def getTypeCraftDOMElement(self):
    xmlProperties = SSFNode.getXMLProperties()
    domElement = DOMElement(xmlProperties.getProperties().getPropertyValue('tcPhraseTag'))
    attribDOM = DOMAttribute(org.dom4j.QName('id'), id)
    domElement.add(attribDOM)
    idomElement = featureStructure.getDOMElement()
    domElement.add(idomElement)
    idomElement = root.getDOMElement()
    domElement.add(idomElement)
    return domElement

def getGATEDOMElement(self):
    xmlProperties = SSFNode.getXMLProperties()
    domElement = DOMElement(xmlProperties.getProperties().getPropertyValue('tcPhraseTag'))
    attribDOM = DOMAttribute(org.dom4j.QName('id'), id)
    domElement.add(attribDOM)
    idomElement = featureStructure.getDOMElement()
    domElement.add(idomElement)
    idomElement = root.getDOMElement()
    domElement.add(idomElement)
    return domElement

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
    xmlProperties = SSFNode.getXMLProperties()
    ssfp = SSFNode.getSSFProperties()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    node = domElement.getFirstChild()
    while node != None:
        if isinstance(node, Element):
            element = node
            if element.getTagName() == xmlProperties.getProperties().getPropertyValue('fsTag'):
                featureStructure.readXML(element)
            elif element.getTagName() == xmlProperties.getProperties().getPropertyValue('nodeTag'):
                root = SSFPhrase('0', '', rootName, '')
                root.readXML(element)
        node = node.getNextSibling()

def readTypeCraftXML(self, domElement):
    xmlProperties = SSFNode.getXMLProperties()
    ssfp = SSFNode.getSSFProperties()
    rootName = ssfp.getProperties().getPropertyValue('rootName')
    root = SSFPhrase('0', '', rootName, '')
    root.setLexData(xmlProperties.getProperties().getPropertyValue('tcPhraseTag'))
    word = None
    node = domElement.getFirstChild()
    while node != None:
        if isinstance(node, Element):
            element = node
            if element.getTagName() == xmlProperties.getProperties().getPropertyValue('fsTag'):
                featureStructure.readXML(element)
            elif element.getTagName() == xmlProperties.getProperties().getPropertyValue('tcWordTag'):
                word = SSFPhrase('0', '', 'word', '')
                word.readTypeCraftXML(element)
                root.addChild(word)
        node = node.getNextSibling()class MyClass(PrintMixin, XMLMixin):

    def __init__(self):
        self.possible_type_craft_xml = None
        self.possible_gate_xml = None

    def printXML(self, ps):
        ps.println(self.getXML())

    def printTypeCraftXML(self, ps):
        ps.println(self.getTypeCraftXML())

    def printGATEXML(self, ps):
        ps.println(self.getGATEXML())

    def getWordFreq(self):
        return self.getRoot().getWordFreq()

    def getPOSTagFreq(self):
        return self.getRoot().getPOSTagFreq()

    def countWords(self):
        return len(self.getRoot().getAllLeaves())

    def countPOSTags(self):
        tags = self.getPOSTagFreq()
        return len(tags)

    def getWordTagPairFreq(self):
        return self.getRoot().getWordTagPairFreq()class MyClass(ChunkTagFreq, GroupRelationFreq, ChunkRelationFreq, AttributeFreq):

    def getChunkTagFreq(self):
        return self.getRoot().getChunkTagFreq()

    def countChunkTags(self):
        tags = self.getChunkTagFreq()
        return len(tags)

    def getGroupRelationFreq(self):
        return self.getRoot().getGroupRelationFreq()

    def countGroupRelations(self):
        rels = self.getGroupRelationFreq()
        return len(rels)

    def getChunkRelationFreq(self):
        return self.getRoot().getChunkRelationFreq()

    def countChunkRelations(self):
        rels = self.getChunkRelationFreq()
        return len(rels)

    def getAttributeFreq(self):
        return self.getRoot().getAttributeFreq()

    def countAttributes(self):
        attribs = self.getAttributeFreq()
        return len(attribs)def countAttributeValues(self):
    attribs = self.getAttributeValueFreq()
    return len(attribs)

def getAttributeValuePairFreq(self):
    return self.getRoot().getAttributeValuePairFreq()

def countAttributeValuePairs(self):
    attribs = self.getAttributeValuePairFreq()
    return len(attribs)

def getUntaggedWordFreq(self):
    allWords = {}
    return allWords

def countUntaggedWords(self):
    count = 0
    nodes = self.getRoot().getNodesForName('')
    ncount = len(nodes)
    for i in range(ncount):
        node = nodes[i]
        if isinstance(node, SSFLexItem):
            count += 1
    return count

def getUnchunkedWordFreq(self):
    return self.getRoot().getUnchunkedWordFreq()

def countUnchunkedWords(self):
    return len(self.getRoot().getUnchunkedWordFreq())

def countUntaggedChunks(self):
    count = 0
    nodes = self.getRoot().getNodesForName('')
    ncount = len(nodes)
    for i in range(ncount):
        node = nodes[i]
        if isinstance(node, SSFPhrase):
            count += 1
    return countdef getEntropyLexical(self):
    entropy = 0.0
    wordFreq = self.getWordFreq()
    wcount = UtilityFunctions.getTotalValue(wordFreq)
    for word, freq in wordFreq.items():
        prob = freq / wcount
        entropy += prob * math.log(prob)
    return -1 * entropy

def getEntropyPOS(self):
    entropy = 0.0
    posTagFreq = self.getPOSTagFreq()
    pcount = UtilityFunctions.getTotalValue(posTagFreq)
    for pos, freq in posTagFreq.items():
        prob = freq / pcount
        entropy += prob * math.log(prob)
    return -1 * entropy

def getEntropyLexicalPOS(self):
    entropy = 0.0
    wordTagFreq = self.getWordTagPairFreq()
    wtcount = UtilityFunctions.getTotalValue(wordTagFreq)
    for wordTag, freq in wordTagFreq.items():
        prob = freq / wtcount
        entropy += prob * math.log(prob)
    return -1 * entropy
if __name__ == '__main__':
    fsp = FSProperties()
    ssfp = SSFProperties()
    stc = SSFSentenceImpl()
    try:
        fsp.read(GlobalProperties.resolveRelativePath('props/fs-mandatory-attribs.txt'), GlobalProperties.resolveRelativePath('props/fs-other-attribs.txt'), GlobalProperties.resolveRelativePath('props/fs-props.txt'), GlobalProperties.resolveRelativePath('props/ps-attribs.txt'), GlobalProperties.resolveRelativePath('props/dep-attribs.txt'), GlobalProperties.resolveRelativePath('props/sem-attribs.txt'), GlobalProperties.getIntlString('UTF-8'))
        ssfp.read(GlobalProperties.resolveRelativePath('props/ssf-props.txt'), GlobalProperties.getIntlString('UTF-8'))
        SSFNode.setSSFProperties(ssfp)
        FeatureStructuresImpl.setFSProperties(fsp)
        stc.readFile('/home/anil/tmp/ssf-sentence-1.txt')
        stc.print(System.out)
    except FileNotFoundError as e:
        e.printStackTrace()
    except IOException as e:
        e.printStackTrace()
    except Exception as e:
        e.printStackTrace()