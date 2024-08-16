from abc import ABC, abstractmethod
from typing import List
from javax.swing.tree import MutableTreeNode
from bhaashik.corpus.ssf.tree import SSFNode
from bhaashik.corpus.parallel import AlignmentUnit
from bhaashik.table import BhaashikTableModel
from java.io import PrintStream

class FeatureStructure(MutableTreeNode, ABC):

    @abstractmethod
    def addAttribute(self, a) -> int:
        pass

    @abstractmethod
    def addAttribute(self, a, p) -> int:
        pass

    @abstractmethod
    def addAttribute(self, name, value) -> int:
        pass

    @abstractmethod
    def addMandatoryAttributes(self) -> None:
        pass

    @abstractmethod
    def clear(self) -> None:
        pass

    @abstractmethod
    def countAttributes(self) -> int:
        pass

    @abstractmethod
    def findAttribute(self, a) -> int:
        pass

    @abstractmethod
    def getAttribute(self, p):
        pass

    @abstractmethod
    def getOneOfAttributes(self, names) -> FeatureAttribute:
        pass

    @abstractmethod
    def getAttribute(self, index):
        pass

    @abstractmethod
    def getAttributeNames(self) -> List[str]:
        pass

    @abstractmethod
    def getAttributeNames(self, p) -> List[str]:
        pass

    @abstractmethod
    def getAttributeValue(self, p) -> FeatureValue:
        pass

    @abstractmethod
    def getAttributeValueString(self, p) -> str:
        pass

    @abstractmethod
    def setAttributeValue(self, attibName, val) -> None:
        pass

    @abstractmethod
    def getAttributeValueByIndex(self, p) -> FeatureValue:
        pass

    @abstractmethod
    def getAttributeValues(self) -> List[str]:
        pass

    @abstractmethod
    def getAttributeValuePairs(self) -> List[str]:
        pass

    @abstractmethod
    def getAttributeValues(self, p) -> List[FeatureValue]:
        pass

    @abstractmethod
    def getFeatureTable(self) -> BhaashikTableModel:
        pass

    @abstractmethod
    def getName(self) -> str:
        pass

    @abstractmethod
    def getPaths(self, name) -> List[FeatureAttribute]:
        pass

    @abstractmethod
    def getValue(self):
        pass

    @abstractmethod
    def clearAnnotation(self, annoLevelFlags, containingNode) -> None:
        pass

    @abstractmethod
    def hasMandatoryAttribs(self) -> bool:
        pass

    @abstractmethod
    def hasMandatoryAttribs(self, m) -> None:
        pass

    @abstractmethod
    def checkAndSetHasMandatory(self) -> None:
        pass

    @abstractmethod
    def makeStringForRendering(self) -> str:
        pass

    @abstractmethod
    def makeString(self) -> str:
        pass

    @abstractmethod
    def makeStringFV(self) -> str:
        pass

    @abstractmethod
    def merge(self, f1, f2) -> FeatureStructure:
        pass

    @abstractmethod
    def modifyAttribute(self, a, index) -> None:
        pass

    @abstractmethod
    def modifyAttribute(self, a, index, p) -> None:
        pass

    @abstractmethod
    def modifyAttributeValue(self, fv, p) -> None:
        pass

    @abstractmethod
    def modifyAttributeValue(self, fv, attribIndex, altValIndex) -> None:
        pass

    @abstractmethod
    def print(self, ps) -> None:
        pass

    @abstractmethod
    def prune(self) -> None:
        pass

    @abstractmethod
    def readString(self, fs_str) -> int:
        pass

    @abstractmethod
    def readStringFV(self, fs_str) -> int:
        pass

    @abstractmethod
    def removeAllAttributes(self) -> None:
        pass

    @abstractmethod
    def removeAttribute(self, index) -> FeatureAttribute:
        pass

    @abstractmethod
    def removeAttribute(self, p) -> FeatureAttribute:
        pass

    @abstractmethod
    def removeAttribute(self, index, p) -> FeatureAttribute:
        pass

    @abstractmethod
    def removeMandatoryAttributes(self) -> None:
        pass

    @abstractmethod
    def removeNonMandatoryAttributes(self) -> None:
        pass

    @abstractmethod
    def hideAttribute(self, aname) -> None:
        pass

    @abstractmethod
    def unhideAttribute(self, aname) -> None:
        pass

    @abstractmethod
    def searchAttribute(self, name, exactMatch) -> FeatureAttribute:
        pass

    @abstractmethod
    def searchOneOfAttributes(self, names, exactMatch) -> FeatureAttribute:
        pass

    @abstractmethod
    def searchAttributeValue(self, name, val, exactMatch) -> FeatureAttribute:
        pass

    @abstractmethod
    def searchAttributeValues(self, name, val, exactMatch) -> List[FeatureAttribute]:
        pass

    @abstractmethod
    def replaceAttributeValues(self, name, val, nameReplace, valReplace) -> List[FeatureAttribute]:
        pass

    @abstractmethod
    def searchAttributes(self, name, exactMatch) -> List[FeatureAttribute]:
        pass

    @abstractmethod
    def setFeatureTable(self, ft) -> None:
        pass

    @abstractmethod
    def setName(self, n) -> None:
        pass

    @abstractmethod
    def setToEmpty(self) -> None:
        pass

    @abstractmethod
    def setValue(self, v) -> None:
        pass

    @abstractmethod
    def unify(self, f1, f2) -> FeatureStructure:
        pass

    @abstractmethod
    def setAlignmentUnit(self, alignmentUnit) -> None:
        pass

    @abstractmethod
    def loadAlignmentUnit(self, srcAlignmentObject, srcAlignmentObjectContainer, tgtAlignmentObjectContainer, parallelIndex) -> AlignmentUnit:
        pass

    @abstractmethod
    def clone(self) -> object:
        pass

    @abstractmethod
    def sortAttributes(self, sortType) -> None:
        pass

    @abstractmethod
    def equals(self, fa) -> bool:
        pass