from abc import ABC, abstractmethod
from typing import List
from bhaashik.data.attrib import TypeTable

class WordTypeTable(TypeTable, ABC):
    @abstractmethod
    def countWTs(self, wtflags: int) -> int:
        pass

    @abstractmethod
    def countTokens(self, wtflags: int) -> int:
        pass

    @abstractmethod
    def getWT(self, num: int):
        pass

    @abstractmethod
    def getWT(self, swrd: str):
        pass

    @abstractmethod
    def addWT(self, wt) -> int:
        pass

    @abstractmethod
    def findWT(self, str: str) -> int:
        pass

    @abstractmethod
    def containsWT(self, str: str) -> bool:
        pass

    @abstractmethod
    def removeWT(self, num: int):
        pass

    @abstractmethod
    def removeWT(self, swrd: str):
        pass

    @abstractmethod
    def sort(self, order: int) -> List:
        pass

    @abstractmethod
    def getShallowCopy(self):
        pass

    @abstractmethod
    def print(self, ps) -> int:
        pass
