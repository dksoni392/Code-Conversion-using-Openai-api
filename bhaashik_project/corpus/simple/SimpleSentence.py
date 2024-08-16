import bhaashik.corpus.parallel.APCData
import bhaashik.corpus.parallel.APCProperties
import bhaashik.corpus.Sentence
import bhaashik.table.BhaashikTableModel
from typing import List
from abc import ABC, abstractmethod

class SimpleSentence(ABC):

    @abstractmethod
    def countWords(self) -> int:
        pass

    @abstractmethod
    def getWord(self, num: int) -> int:
        pass

    @abstractmethod
    def setWord(self, ind: int, wd: int) -> int:
        pass

    @abstractmethod
    def insertWord(self, ind: int, wd: int):
        pass

    @abstractmethod
    def countSbtWords(self) -> int:
        pass

    @abstractmethod
    def getWordSbt(self, num: int) -> int:
        pass

    @abstractmethod
    def setWordSbt(self, ind: int, wd: int) -> int:
        pass

    @abstractmethod
    def calculateSentenceLength(self, wttbl: BhaashikTableModel):
        pass

    @abstractmethod
    def getSentenceLength(self) -> int:
        pass

    @abstractmethod
    def setSentenceLength(self, senlen: int):
        pass

    @abstractmethod
    def calculateSignature(self, wttbl: BhaashikTableModel):
        pass

    @abstractmethod
    def getSignature(self) -> int:
        pass

    @abstractmethod
    def setSignature(self, sg: int):
        pass

    @abstractmethod
    def setWeightedLength(self, apcpro: APCProperties, type: str):
        pass

    @abstractmethod
    def setWeightedLength(self, type: str):
        pass

    @abstractmethod
    def getWeightedLength(self) -> int:
        pass

    @abstractmethod
    def getWords(self, wttbl: BhaashikTableModel) -> List:
        pass

    @abstractmethod
    def getSentenceString(self, wttbl: BhaashikTableModel) -> str:
        pass

    @abstractmethod
    def getWordString(self, i: int, wttbl: BhaashikTableModel) -> str:
        pass

    @abstractmethod
    def getCommonWords(self, sen: Sentence, apcdata: APCData) -> float:
        pass

    @abstractmethod
    def getCommonHypernyms(self, sen: Sentence, apcdata: APCData) -> float:
        pass

    @abstractmethod
    def get_Phntc_Num_Match(self, sen: Sentence, apcdata: APCData) -> float:
        pass

    @abstractmethod
    def getCommonSynonyms(self, sen: Sentence, apcdata: APCData) -> float:
        pass

    @abstractmethod
    def removeVowels(self, word: str, lang: str) -> str:
        pass

    @abstractmethod
    def print(self, ps):
        pass

    @abstractmethod
    def printCounts(self, wtTable: BhaashikTableModel, ps):
        pass