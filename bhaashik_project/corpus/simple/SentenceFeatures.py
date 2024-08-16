'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
from abc import ABC, abstractmethod
from bhaashik.corpus.parallel import APCData, APCProperties
from bhaashik.corpus import Sentence

class SentenceFeatures(ABC):

    @abstractmethod
    def countSbtWords(self):
        pass

    @abstractmethod
    def getWordSbt(self, num):
        pass

    @abstractmethod
    def setWordSbt(self, ind, wd):
        pass

    @abstractmethod
    def getSentenceLength(self):
        pass

    @abstractmethod
    def setSentenceLength(self, senlen):
        pass

    @abstractmethod
    def getSignature(self):
        pass

    @abstractmethod
    def setSignature(self, sg):
        pass

    @abstractmethod
    def setWeightedLength(self, apcpro, type):
        pass

    @abstractmethod
    def getWeightedLength(self):
        pass

    @abstractmethod
    def getCommonWords(self, sen, apcdata):
        pass

    @abstractmethod
    def getCommonHypernyms(self, sen, apcdata):
        pass

    @abstractmethod
    def get_Phntc_Num_Match(self, sen, apcdata):
        pass

    @abstractmethod
    def getCommonSynonyms(self, sen, apcdata):
        pass