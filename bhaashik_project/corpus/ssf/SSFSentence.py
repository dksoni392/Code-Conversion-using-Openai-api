'\nTo change this template, choose Tools | Templates\nand open the template in the editor.\n'
from bhaashik.corpus.parallel import Alignable
from bhaashik.corpus.ssf.features import FeatureStructure
from bhaashik.corpus.ssf.query import QueryValue, SSFQuery
from bhaashik.corpus.ssf.tree import SSFNode, SSFPhrase
from bhaashik.text.enc.conv import BhaashikEncodingConverter
from bhaashik.common.types import CorpusType
from bhaashik.corpus import Sentence
from bhaashik.util.query import FindReplaceOptions, SyntacticCorpusContextQueryOptions
from dom4j.dom import DOMElement
from xml.dom import Element
from bhaashik.properties import KeyValueProperties

class SSFSentence(Alignable):

    def clear(self):
        pass

    def clearAlignments(self):
        pass

    def clearAnnotation(self, annoLevelFlags):
        pass

    def clearFeatureStructures(self):
        pass

    def clearHighlights(self):
        pass

    def convertEncoding(self, encodingConverter, nullWordString):
        pass

    def convertToBracketForm(self, spaces):
        pass

    def convertToLowerCase(self):
        pass

    def convertToPOSNolex(self):
        pass

    def convertToPOSTagged(self, sep):
        pass

    def convertToRawText(self):
        pass

    def setAttributeValue(self, attibName, val):
        pass

    def countAttributeValuePairs(self):
        pass

    def countAttributeValues(self):
        pass

    def countAttributes(self):
        pass

    def countCharacters(self):
        pass

    def countChunkRelations(self):
        pass

    def countChunkTags(self):
        pass

    def countGroupRelations(self):
        pass

    def countPOSTags(self):
        pass

    def countUnchunkedWords(self):
        pass

    def countUntaggedChunks(self):
        pass

    def countUntaggedWords(self):
        pass

    def countWordTagPairs(self):
        pass

    def countWords(self):
        pass

    def emptyPhrasesAllowed(self):
        pass

    def emptyPhrasesAllowed(self, e):
        pass

    def find(self, nlabel, ntext, attrib, val, nlabelReplace, ntextReplace, attribReplace, valReplace, replace, createAttrib, exactMatch):
        pass

    def findChildByID(self, id):
        pass

    def findChildByName(self, id):
        pass

    def findChildIndexByID(self, id):
        pass

    def findChildIndexByName(self, id):
        pass

    def findContext(self, contextOptions, exactMatch):
        pass

    def findLeafByID(self, id):
        pass

    def findLeafByName(self, id):
        pass

    def findLeafIndexByID(self, id):
        pass

    def findLeafIndexByName(self, id):
        pass

    def findNodeByID(self, id):
        pass

    def findNodeByName(self, id):
        pass

    def findNodeIndexByID(self, id):
        pass

    def findNodeIndexByName(self, id):
        pass

    def getAttributeFreq(self):
        pass

    def getAttributeValueFreq(self):
        pass

    def getAttributeValuePairFreq(self):
        pass

    def getChunkRelationFreq(self):
        pass

    def getChunkTagFreq(self):
        pass

    def getCopy(self):
        pass

    def getDOMElement(self):
        pass

    def getEntropyLexical(self):
        pass

    def getEntropyLexicalPOS(self):
        pass

    def getEntropyPOS(self):
        pass

    def getFeatureStructure(self):
        pass

    def getGATEDOMElement(self):
        pass

    def getGATEXML(self):
        pass

    def getGroupRelationFreq(self):
        pass

    def getId(self):
        pass

    def getMatchingValues(self, ssfQuery):
        pass

    def getPOSTagFreq(self):
        pass

    def getQueryReturnObject(self):
        pass

    def getQueryReturnValue(self):
        pass

    def getRoot(self):
        pass

    def getTypeCraftDOMElement(self):
        pass

    def getTypeCraftXML(self):
        pass

    def getUnannotated(self):
        pass

    def getUnchunkedWordFreq(self):
        pass

    def getUntaggedWordFreq(self):
        pass

    def getWordFreq(self):
        pass

    def getWordTagPairFreq(self):
        pass

    def getXML(self):
        pass

    def loadAlignments(self, tgtSentence, parallelIndex):
        pass

    def makePOSNolex(self):
        pass

    def makeSentenceFromChunked(self, chunked, errorLog, lineNum):
        pass

    def makeSentenceFromStanfordPSG(self, chunked, errorLog, lineNum):
        pass

    def markSentenceWithStanfordDG(self, chunked, errorLog, lineNum):
        pass

    def makeSentenceFromPOSTagged(self, posTagged, errorLog, lineNum):
        pass

    def makeSentenceFromRaw(self, rawSentence):
        pass

    def makeString(self):
        pass

    def matches(self, findReplaceOptions):
        pass

    def print(self, ps):
        pass

    def print(self, ps, corpusType):
        pass

    def printGATEXML(self, ps):
        pass

    def printTypeCraftXML(self, ps):
        pass

    def printXML(self, ps):
        pass

    def readFile(self, fileName):
        pass

    def readGATEXML(self, domElement):
        pass

    def readString(self, string, errorLog, lineNum):
        pass

    def readTypeCraftXML(self, domElement):
        pass

    def readXML(self, domElement):
        pass

    def reallocatePositions(self, positionAttribName, nullWordString):
        pass

    def save(self, f, charset):
        pass

    def saveAlignments(self):
        pass

    def setFeatureStructure(self, featureStructure):
        pass

    def setId(self, i):
        pass

    def setMorphTags(self, morphTags):
        pass

    def setQueryReturnObject(self, rv):
        pass

    def setQueryReturnValue(self, rv):
        pass

    def setRoot(self, r):
        pass