from abc import ABC, abstractmethod
import pickle
import sys
from typing import Union
from edu.stanford.nlp.util import HashIndex
from edu.stanford.nlp.util import Index
from java.io import PrintStream
from java.io import Serializable
from java.lang import Math
from java.util import Comparator
from bhaashik.corpus.ssf.features import FeatureAttribute
from bhaashik.corpus.ssf.features import FeatureValue
from bhaashik.tree import BhaashikMutableTreeNode

class FeatureAttributeImpl(BhaashikMutableTreeNode, FeatureAttribute, Serializable):
    namesIndex: Index[str] = HashIndex()

    def __init__(self, userObject: object, allowsChildren: bool) -> None:
        super().__init__(None, allowsChildren)
        self.nameIndex: int = self.getIndex(userObject, True)

    def getName(self) -> str:
        return self.getString(self.nameIndex)

    def setName(self, n: str) -> None:
        self.nameIndex = self.getIndex(n, True)

    def getNameVocabularySize(self) -> int:
        return Math.toIntExact(self.namesIndex.size())

    @staticmethod
    def getIndex(wd: str, add: bool) -> int:
        wi: int = FeatureAttributeImpl.namesIndex.indexOf(wd, add)
        return wi

    @staticmethod
    def getString(wdIndex: int) -> str:
        return FeatureAttributeImpl.namesIndex.get(wdIndex)

    def getUserObject(self) -> object:
        return self.getName()

    def countAltValues(self) -> int:
        return self.getChildCount()

    def addAltValue(self, v: FeatureValue) -> int:
        self.add(v)
        return self.getChildCount()

    def findAltValue(self, v: FeatureValue) -> int:
        return self.getIndex(v)

    def getAltValue(self, index: int) -> FeatureValue:
        return self.getChildAt(index)

    def modifyAltValue(self, v: FeatureValue, index: int) -> None:
        self.insert(v, index)
        self.remove(index + 1)

    def removeAltValue(self, index: int) -> FeatureValue:
        rem: FeatureValue = self.getAltValue(index)
        self.remove(index)
        return rem

    def removeAllAltValues(self) -> None:
        count: int = self.countAltValues()
        for i in range(count):
            self.removeAltValue(i)

    def hideAttribute(self) -> None:
        self.hide = True

    def unhideAttribute(self) -> None:
        self.hide = False

    def isHiddenAttribute(self) -> bool:
        return self.hide

    def makeString(self, mandatory: bool) -> str:
        str_: str = ''
        fsp: FSProperties = FeatureStructuresImpl.getFSProperties()
        if mandatory:
            str_ += self.getAltValue(0).makeString()
            for i in range(1, self.countAltValues()):
                str_ += fsp.getProperties().getPropertyValueForPrint('attribOR') + self.getAltValue(i).makeString()
            return str_
        else:
            valStr: str = self.getAltValue(0).makeString()
            if valStr is None:
                valStr = ''
            if valStr == "'" or valStr == "''" or (not valStr.startswith("'") or not valStr.endswith("'")):
                str_ += self.getName() + fsp.getProperties().getPropertyValueForPrint('attribEquate') + "'" + valStr + "'"
            else:
                str_ += self.getName() + fsp.getProperties().getPropertyValueForPrint('attribEquate') + valStr
            for i in range(1, self.countAltValues()):
                valStr = self.getAltValue(i).makeString()
                if valStr == "'" or valStr == "''" or (not valStr.startswith("'") or not valStr.endswith("'")):
                    str_ += fsp.getProperties().getPropertyValueForPrint('attribOR') + "'" + valStr + "'"
                else:
                    str_ += fsp.getProperties().getPropertyValueForPrint('attribOR') + valStr
        return str_

    def print_(self, ps: PrintStream, mandatory: bool) -> None:
        ps.println(self.makeString(mandatory))

    def clear(self) -> None:
        self.nameIndex = -1
        self.removeAllChildren()

    @staticmethod
    def getAttributeComparator(sortType: int) -> Comparator:
        if sortType == self.SORT_BY_NAME:
            return Comparator(compare=lambda one, two: one.getName().compareToIgnoreCase(two.getName()))
        return None

    def equals(self, obj: object) -> bool:
        if obj is None or not isinstance(obj, FeatureAttributeImpl):
            return False
        aobj: FeatureAttribute = obj
        if not self.getName().equalsIgnoreCase(aobj.getName()):
            return False
        count: int = self.countAltValues()
        if count != aobj.countAltValues():
            return False
        for i in range(count):
            if not self.getAltValue(i).equals(aobj.getAltValue(i)):
                return False
        return True

    @staticmethod
    def main(args: list[str]) -> None:
        attrib: FeatureAttribute = FeatureAttributeImpl()