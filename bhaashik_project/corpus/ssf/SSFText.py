'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import java.io.FileNotFoundException
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.LinkedHashMap
import java.util.List
import bhaashik.corpus.parallel.Alignable
import bhaashik.corpus.ssf.impl.SSFTextImpl
import bhaashik.corpus.ssf.query.QueryValue
import bhaashik.corpus.ssf.query.SSFQuery
import bhaashik.corpus.ssf.tree.SSFNode
import bhaashik.text.enc.conv.BhaashikEncodingConverter
import bhaashik.common.types.CorpusType
import bhaashik.corpus.Text
import bhaashik.util.query.FindReplaceOptions
import org.xml.sax.SAXException
import bhaashik.properties.KeyValueProperties

class SSFText(Alignable):

    def __init__(self):
        self.ssfFile = ''
        self.charset = ''
        self.metaData = ''
        self.id = ''

    def getSSFFile(self):
        return self.ssfFile

    def setSSFFile(self, ssfFile):
        self.ssfFile = ssfFile

    def getCharset(self):
        return self.charset

    def setCharset(self, charset):
        self.charset = charset

    def getMetaData(self):
        return self.metaData

    def setMetaData(self, md):
        self.metaData = md

    def getId(self):
        return self.id

    def setId(self, i):
        self.id = i

    def countParagraph(self):
        pass

    def getParagraph(self, index):
        pass

    def getParagraphForSentence(self, index):
        pass

    def getParagraphIndexForSentence(self, index):
        pass

    def addParagraphBoundaries(self, startIndex, endIndex):
        pass

    def addParagraphBoundaries(self, startIndex, endIndex, paraAttribs, paraMetaData):
        pass

    def removeParagraphBoundaries(self, index):
        pass

    def countSentences(self):
        pass

    def countChunks(self):
        pass

    def countWords(self):
        pass

    def getAvgTokenLength(self):
        pass

    def getAvgSentenceLength(self):
        pass

    def getAvgPOSCount(self, tag):
        pass

    def getAvgPOSCount(self, category, tagset):
        pass

    def getWordFreq(self):
        pass

    def getPOSTagFreq(self):
        pass

    def getGroupRelationFreq(self):
        pass

    def getWordTagPairFreq(self):
        pass

    def getChunkTagFreq(self):
        pass

    def getChunkRelationFreq(self):
        pass

    def getAttributeFreq(self):
        pass

    def getAttributeValueFreq(self):
        pass

    def getAttributeValuePairFreq(self):
        pass

    def getUntaggedWordFreq(self):
        pass

    def getUnchunkedWordFreq(self):
        pass

    def countCharacters(self):
        pass

    def addSentence(self, sentence):
        pass

    def addSentences(self, story):
        pass

    def insertSentence(self, sentence, index):
        pass

    def removeSentence(self, index):
        pass

    def removeAttribute(self, aname):
        pass

    def hideAttribute(self, aname):
        pass

    def unhideAttribute(self, aname):
        pass

    def removeEmptySentences(self):
        pass

    def getSentence(self, index):
        pass

    def findSentence(self, s):
        pass

    def findSentence(self, id):
        pass

    def findSentenceIndex(self, id):
        pass

    def modifySentence(self, sentence, index):
        pass

    def getSSFText(self, startSentenceNum, windowSize):
        pass

    def makeTextFromRaw(self, rawText):
        pass

    def makeTextFromPOSTagged(self, posTagged):
        pass

    def makeString(self):
        pass

    def makePOSNolex(self):
        pass

    def convertToPOSNolex(self):
        pass

    def convertToBracketForm(self, spaces):
        pass

    def convertToPOSTagged(self):
        pass

    def convertToPOSTagged(self, sep):
        pass

    def convertToRawText(self):
        pass

    def readXML(self, filePath, charset):
        pass

    def readPOSTagged(self, filePath, charset, corpusType, errorLog):
        pass

    def readHindenCorp(self, filePath, charset, corpusType, errorLog):
        pass

    def readStafordParserOutput(self, filePath, charset, corpusType, errorLog):
        pass

    def readCoNLLUFormat(self, filePath, charset, corpusType, errorLog):
        pass

    def readVerticalPOSTagged(self, filePath, charset, corpusType, errorLog):
        pass

    def readBIFormat(self, filePath, charset, corpusType, errorLog):
        pass

    def readRaw(self, filePath, charset, corpusType, errorLog):
        pass

    def readChunked(self, filePath, charset, corpusType, errorLog):
        pass

    def readSSFFormat(self, filePath, charset, corpusType, errorLog):
        pass

    def print(self, ps):
        pass

    def printLowerCase(self, ps):
        pass

    def printLowerCaseRawText(self, ps):
        pass

    def printXML(self, ps):
        pass

    def printBracketForm(self, ps, spaces):
        pass

    def printPOSTagged(self, ps):
        pass

    def printPOSNolex(self, ps):
        pass

    def printRawText(self, ps):
        pass

    def save(self, f, charset):
        pass

    def saveLowerCase(self, f, charset):
        pass

    def saveRawText(self, f, charset):
        pass

    def saveXML(self, f, charset):
        pass

    def saveBracketForm(self, f, charset, spaces):
        pass

    def savePOSTagged(self, f, charset):
        pass

    def savePOSNolex(self, f, charset):
        pass

    def saveLowerCaseRawText(self, f, charset):
        pass

    def saveAlignments(self):
        pass

    def loadAlignments(self, tgtText, parallelIndex):
        pass

    def clearAlignments(self):
        pass

    def getCopy(self):
        pass

    def clear(self):
        pass

    def reallocateSentenceIDs(self):
        pass

    def reallocateNodeIDs(self):
        pass

    def clearFeatureStructures(self):
        pass

    def clearAnnotation(self, annoLevelFlags):
        pass

    def matchedSentenceCount(self, findReplaceOptions):
        pass

    def getAllOccurrences(self, wrd):
        pass

    def matches(self, findReplaceOptions):
        pass

    def setMorphTags(self, morphTags):
        pass

    def countPOSTags(self):
        pass

    def countChunkTags(self):
        pass

    def countAttributes(self):
        pass

    def countAttributeValues(self):
        pass

    def countAttributeValuePairs(self):
        pass

    def countUntaggedWords(self):
        pass

    def countUnchunkedWords(self):
        pass

    def countUntaggedChunks(self):
        pass

    def countWordsWithoutMorph(self):
        pass

    def countChunksWithoutMorph(self):
        pass

    def countDependencyRelations(self):
        pass

    def getMatchingValues(self, ssfQuery):
        pass

    def reallocatePositions(self, positionAttribName, nullWordString):
        pass

    def clearHighlights(self):
        pass

    def convertEncoding(self, encodingConverter, nullWordString):
        pass

    def makeLowerCase(self):
        pass

    def isTagged(self):
        pass

    def isTagged(self, t):
        pass