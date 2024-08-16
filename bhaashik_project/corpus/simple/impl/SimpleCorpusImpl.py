from abc import ABC, abstractmethod
import io
import csv
from collections import OrderedDict

class Corpus(ABC):

    @abstractmethod
    def read(self, file, charset):
        pass

class Sentence(ABC):

    @abstractmethod
    def countWords(self):
        pass

class SimpleCorpus(ABC):

    @abstractmethod
    def getWordTypeTable(self):
        pass

    @abstractmethod
    def setWordTypeTable(self):
        pass

    @abstractmethod
    def getPropsManager(self):
        pass

    @abstractmethod
    def setPropsManager(self, propsfilename, charset):
        pass

    @abstractmethod
    def countSentences(self):
        pass

    @abstractmethod
    def countTokens(self, recalculate):
        pass

    @abstractmethod
    def getSentence(self, num):
        pass

    @abstractmethod
    def addSentence(self, s):
        pass

    @abstractmethod
    def insertSentence(self, index, s):
        pass

    @abstractmethod
    def removeSentence(self, num):
        pass

    @abstractmethod
    def print(self, ps):
        pass

class BhaashikTableModel:

    def __init__(self, data, columns):
        self.data = data
        self.columns = columns
        self.editable = False

    def getColumnIndex(self, column_name):
        return self.columns.index(column_name)

    def addRow(self):
        self.data.append([])

    def setValueAt(self, value, row, col):
        self.data[row][col] = value

    def getRowCount(self):
        return len(self.data)

    def getValueAt(self, row, col):
        return self.data[row][col]

    def setEditable(self, editable):
        self.editable = editable

class PropertiesManager:

    def __init__(self, propsfilename, charset):
        self.propsfilename = propsfilename
        self.charset = charset

    def getPropertyContainer(self, propname, proptype):
        if proptype == PropertyType.KEY_VALUE_PROPERTIES:
            return KeyValueProperties(self.propsfilename, self.charset)
        elif proptype == PropertyType.PROPERTY_TABLE:
            return PropertiesTable(self.propsfilename, self.charset)

class KeyValueProperties:

    def __init__(self, propsfilename, charset):
        self.propsfilename = propsfilename
        self.charset = charset

    def getPropertyValue(self, key):
        with open(self.propsfilename, 'r', encoding=self.charset) as propsfile:
            properties = dict(csv.reader(propsfile, delimiter='='))
            return properties[key]

class PropertiesTable:

    def __init__(self, propsfilename, charset):
        self.propsfilename = propsfilename
        self.charset = charset

class SimpleCorpusImpl(Corpus, SimpleCorpus):

    def __init__(self, propsfilename, charset):
        self.sentences = []
        self.wordTypeTable = None
        self.propManager = None
        self.words = OrderedDict()
        try:
            self.propManager = PropertiesManager(propsfilename, charset)
        except IOError as e:
            print('Could not find the keyvalue property file in SimpleCorpusImp...')
            return

    def getWordTypeTable(self):
        return self.wordTypeTable

    def setWordTypeTable(self):
        return

    def getPropsManager(self):
        return self.propManager

    def setPropsManager(self, propsfilename, charset):
        try:
            self.propManager = PropertiesManager(propsfilename, charset)
        except IOError as e:
            print('Could not find the keyvalue property file in SimpleCorpusImp...')
            return 1
        return 0

    def countSentences(self):
        return len(self.sentences)

    def countTokens(self, recalculate):
        if recalculate == False:
            return self.tokenCount
        tokenCount = 0
        for sen in self.sentences:
            tokenCount += sen.countWords()
        self.tokenCount = tokenCount
        return self.tokenCount

    def getSentence(self, num):
        return self.sentences[num]

    def addSentence(self, s):
        self.sentences.append(s)
        return len(self.sentences)

    def insertSentence(self, index, s):
        self.sentences.insert(index, s)
        return len(self.sentences)

    def removeSentence(self, num):
        return self.sentences.pop(num)

    def read(self, file, charset):
        dataKVP = self.propManager.getPropertyContainer('DataProps', PropertyType.KEY_VALUE_PROPERTIES)
        resourceTable = self.propManager.getPropertyContainer('Resources', PropertyType.PROPERTY_TABLE)
        lnReader = None