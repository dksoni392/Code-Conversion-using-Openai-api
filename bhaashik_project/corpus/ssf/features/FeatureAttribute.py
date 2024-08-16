'\nCreated on Aug 15, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import sys
from abc import ABC, abstractmethod

class FeatureAttribute(MutableTreeNode, ABC):
    SORT_BY_NAME = 0

    def addAltValue(self, v):
        pass

    def clear(self):
        pass

    def countAltValues(self):
        pass

    def findAltValue(self, v):
        pass

    def getAltValue(self, index):
        pass

    def getName(self):
        pass

    def makeString(self, mandatory):
        pass

    def modifyAltValue(self, v, index):
        pass

    def print(self, ps, mandatory):
        pass

    def removeAllAltValues(self):
        pass

    def removeAltValue(self, index):
        pass

    def hideAttribute(self):
        pass

    def unhideAttribute(self):
        pass

    def isHiddenAttribute(self):
        pass

    def setName(self, n):
        pass

    @abstractmethod
    def clone(self):
        pass

    def __eq__(self, obj):
        pass