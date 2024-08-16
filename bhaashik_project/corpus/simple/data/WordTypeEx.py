from abc import ABC, abstractmethod

class WordTypeEx(ABC):

    @abstractmethod
    def countHypernyms(self):
        pass

    @abstractmethod
    def getHypernym(self, num):
        pass

    @abstractmethod
    def addHypernym(self, h):
        pass

    @abstractmethod
    def removeHypernym(self, num):
        pass

    @abstractmethod
    def countSynonyms(self):
        pass

    @abstractmethod
    def getSynonyms(self, num):
        pass

    @abstractmethod
    def addSynonyms(self, h):
        pass

    @abstractmethod
    def removeSynonyms(self, num):
        pass

    @abstractmethod
    def getPhoneticMatch(self):
        pass

    @abstractmethod
    def setPhoneticMatch(self, pm):
        pass

    @abstractmethod
    def populate(self, apcdata, tag, type):
        pass

    @abstractmethod
    def print(self, ps):
        pass

class WordTypeExImpl(WordTypeEx):

    def countHypernyms(self):
        pass

    def getHypernym(self, num):
        pass

    def addHypernym(self, h):
        pass

    def removeHypernym(self, num):
        pass

    def countSynonyms(self):
        pass

    def getSynonyms(self, num):
        pass

    def addSynonyms(self, h):
        pass

    def removeSynonyms(self, num):
        pass

    def getPhoneticMatch(self):
        pass

    def setPhoneticMatch(self, pm):
        pass

    def populate(self, apcdata, tag, type):
        pass

    def print(self, ps):
        pass