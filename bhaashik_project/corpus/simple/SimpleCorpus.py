from abc import ABC, abstractmethod
from typing import List
import pandas as pd

class SimpleCorpusInterface(ABC):

    @abstractmethod
    def getWordTypeTable(self) -> pd.DataFrame:
        pass

    @abstractmethod
    def setWordTypeTable(self):
        pass

    @abstractmethod
    def getPropsManager(self):
        pass

    @abstractmethod
    def setPropsManager(self, propsfilename: str, charset: str) -> int:
        pass

    @abstractmethod
    def countSentences(self) -> int:
        pass

    @abstractmethod
    def countTokens(self, recalculate: bool) -> int:
        pass

    @abstractmethod
    def getSentence(self, num: int) -> 'Sentence':
        pass

    @abstractmethod
    def addSentence(self, s: 'Sentence') -> int:
        pass

    @abstractmethod
    def insertSentence(self, index: int, s: 'Sentence') -> int:
        pass

    @abstractmethod
    def removeSentence(self, num: int) -> 'Sentence':
        pass

    @abstractmethod
    def read(self, file: File, charset: str) -> int:
        pass

    @abstractmethod
    def readSegments(self, file: File, charset: str, segmentSize: int) -> int:
        pass

    @abstractmethod
    def readOverlappingSegments(self, file: File, charset: str, segmentSize: int) -> int:
        pass

    @abstractmethod
    def print(self, ps: PrintStream):
        pass

class SimpleCorpus(SimpleCorpusInterface):

    def getWordTypeTable(self) -> pd.DataFrame:
        pass

    def setWordTypeTable(self):
        pass

    def getPropsManager(self):
        pass

    def setPropsManager(self, propsfilename: str, charset: str) -> int:
        pass

    def countSentences(self) -> int:
        pass

    def countTokens(self, recalculate: bool) -> int:
        pass

    def getSentence(self, num: int) -> 'Sentence':
        pass

    def addSentence(self, s: 'Sentence') -> int:
        pass

    def insertSentence(self, index: int, s: 'Sentence') -> int:
        pass

    def removeSentence(self, num: int) -> 'Sentence':
        pass

    def read(self, file: File, charset: str) -> int:
        pass

    def readSegments(self, file: File, charset: str, segmentSize: int) -> int:
        pass

    def readOverlappingSegments(self, file: File, charset: str, segmentSize: int) -> int:
        pass

    def print(self, ps: PrintStream):
        pass

class Sentence:
    pass

class BhaashikTableModel:
    pass

class PropertiesManager:
    pass