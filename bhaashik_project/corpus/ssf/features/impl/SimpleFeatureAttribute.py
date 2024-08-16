from typing import Optional
from enum import Enum
from typing import Any, List
from copy import copy
from collections import OrderedDict
from typing import Iterator
from itertools import islice

class FeatureAttribute(Enum):
    def addAltValue(self, v: 'FeatureValue') -> int:
        raise NotImplementedError

    def clear(self) -> None:
        raise NotImplementedError

    def countAltValues(self) -> int:
        raise NotImplementedError

    def findAltValue(self, v: 'FeatureValue') -> int:
        raise NotImplementedError

    def getAltValue(self, index: int) -> 'FeatureValue':
        raise NotImplementedError

    def getName(self) -> str:
        raise NotImplementedError

    def makeString(self, mandatory: bool) -> str:
        raise NotImplementedError

    def modifyAltValue(self, v: 'FeatureValue', index: int) -> None:
        raise NotImplementedError

    def print(self, ps: Any, mandatory: bool) -> None:
        raise NotImplementedError

    def removeAllAltValues(self) -> None:
        raise NotImplementedError

    def removeAltValue(self, index: int) -> 'FeatureValue':
        raise NotImplementedError

    def hideAttribute(self) -> None:
        raise NotImplementedError

    def unhideAttribute(self) -> None:
        raise NotImplementedError

    def isHiddenAttribute(self) -> bool:
        raise NotImplementedError

    def setName(self, n: str) -> None:
        raise NotImplementedError

    def insert(self, child: 'MutableTreeNode', index: int) -> None:
        raise NotImplementedError

    def remove(self, index: int) -> None:
        raise NotImplementedError

    def remove(self, node: 'MutableTreeNode') -> None:
        raise NotImplementedError

    def setUserObject(self, obj: Any) -> None:
        raise NotImplementedError

    def removeFromParent(self) -> None:
        raise NotImplementedError

    def setParent(self, newParent: 'MutableTreeNode') -> None:
        raise NotImplementedError

    def getChildAt(self, childIndex: int) -> 'TreeNode':
        raise NotImplementedError

    def getChildCount(self) -> int:
        raise NotImplementedError

    def getParent(self) -> Optional['TreeNode']:
        raise NotImplementedError

    def getIndex(self, node: 'TreeNode') -> int:
        raise NotImplementedError

    def getAllowsChildren(self) -> bool:
        raise NotImplementedError

    def isLeaf(self) -> bool:
        raise NotImplementedError

    def children(self) -> Iterator['TreeNode']:
        raise NotImplementedError

    def clone(self) -> Any:
        raise NotImplementedError

    def equals(self, obj: Any) -> bool:
        raise NotImplementedError

    def isFeatureStructure(self) -> bool:
        raise NotImplementedError

    def getValue(self) -> Any:
        raise NotImplementedError

    def makeString(self) -> str:
        raise NotImplementedError

    def makeStringForRendering(self) -> str:
        raise NotImplementedError

    def print(self, ps: Any) -> None:
        raise NotImplementedError

    def readString(self, s: str) -> int:
        raise NotImplementedError

    def setValue(self, v: Any) -> None:
        raise NotImplementedError

class MutableTreeNode(FeatureAttribute):
    pass

class TreeNode(FeatureAttribute):
    pass

class SimpleFeatureAttribute(MutableTreeNode, TreeNode):
    featureIndex = OrderedDict()
    valueIndex = OrderedDict()

    def __init__(self, f: int = 0, v: int = 0) -> None:
        self.feature = f
        self.value = v
        self.hide = False

    @classmethod
    def getFeatureIndex(cls, wd: str, add: bool) -> int:
        fi = cls.featureIndex.get(wd, None)
        if fi is None and add:
            fi = len(cls.featureIndex)
            cls.featureIndex[wd] = fi
        return fi

    @classmethod
    def getFeatureString(cls, fIndex: int) -> str:
        return cls.featureIndex.get(fIndex, '')

    @classmethod
    def getValueIndex(cls, wd: str, add: bool) -> int:
        vi = cls.valueIndex.get(wd, None)
        if vi is None and add:
            vi = len(cls.valueIndex)
            cls.valueIndex[wd] = vi
        return vi

    @classmethod
    def getValueString(cls, vIndex: int) -> str:
        return cls.valueIndex.get(vIndex, '')

    def addAltValue(self, v: 'FeatureValue') -> int:
        self.value = self.getValueIndex(str(v.getValue()), True)
        return 1

    def clear(self) -> None:
        self.value = -1

    def countAltValues(self) -> int:
        return 1

    def findAltValue(self, v: 'FeatureValue') -> int:
        return 0

    def getAltValue(self, index: int) -> 'FeatureValue':
        if index == 0:
            return self
        return None

    def getName(self) -> str:
        return self.getFeatureString(self.feature)

    def makeString(self, mandatory: bool) -> str:
        str_ = ''
        fsp = FeatureStructuresImpl.getFSProperties()

        if mandatory:
            return self.getValueString(self.value)
        else:
            valStr = self.getValueString(self.value)

            if (
                valStr == "'" or valStr == "''"
                or (valStr.startswith("'") == False or valStr.endswith("'") == False)
            ):
                str_ += (
                    self.getName()
                    + fsp.getProperties().getPropertyValueForPrint("attribEquate")
                    + "'"
                    + valStr
                    + "'"
                )
            else:
                str_ += (
                    self.getName()
                    + fsp.getProperties().getPropertyValueForPrint("attribEquate")
                    + valStr
                )

        return str_

    def modifyAltValue(self, v: 'FeatureValue', index: int) -> None:
        if index == 0:
            self.setValue(v)

    def print(self, ps: Any, mandatory: bool) -> None:
        ps.print(self.makeString(mandatory))

    def removeAllAltValues(self) -> None:
        self.feature = -1
        self.value = -1

    def removeAltValue(self, index: int) -> 'FeatureValue':
        if index == 0:
            self.feature = -1
            self.value = -1
            return self
        return None

    def hideAttribute(self) -> None:
        self.hide = True

    def unhideAttribute(self) -> None:
        self.hide = False

    def isHiddenAttribute(self) -> bool:
        return self.hide

    def setName(self, n: str) -> None:
        self.feature = self.getFeatureIndex(n, True)

    def insert(self, child: 'MutableTreeNode', index: int) -> None:
        raise NotImplementedError("Not supported yet.")

    def remove(self, index: int) -> None:
        if index == 0:
            self.feature = -1
            self.value = -1

    def remove(self, node: 'MutableTreeNode') -> None:
        raise NotImplementedError("Not supported yet.")

    def setUserObject(self, obj: Any) -> None:
        raise NotImplementedError("Not supported yet.")

    def removeFromParent(self) -> None:
        raise NotImplementedError("Not supported yet.")

    def set
