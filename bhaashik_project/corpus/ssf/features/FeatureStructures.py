'\nCreated on Aug 15, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import java.io
import java.util.List
import javax.swing.tree
import bhaashik.corpus.ssf.tree.SSFNode
import bhaashik.tree.BhaashikMutableTreeNode
import bhaashik.corpus.parallel.AlignmentUnit
'\n@author Anil Kumar Singh Kumar Singh\n\nTODO To change the template for this generated type comment go to\nWindow - Preferences - Java - Code Style - Code Templates\n'

class FeatureStructures(MutableTreeNode):

    def addAltFSValue(self, f):
        pass

    def clear(self):
        pass

    def countAltFSValues(self):
        pass

    def findAltFSValue(self, fs):
        pass

    def getAltFSValue(self, num):
        pass

    def getCopy(self):
        pass

    def isDeep(self):
        pass

    def makeString(self):
        pass

    def makeStringFV(self):
        pass

    def makeStringForRendering(self):
        pass

    def modifyAltFSValue(self, fs, index):
        pass

    def print(self, ps):
        pass

    def readString(self, fs_str):
        pass

    def readStringFV(self, fs_str):
        pass

    def removeAltFSValue(self, num):
        pass

    def clearAnnotation(self, annoLevelFlags, containingNode):
        pass

    def setToEmpty(self):
        pass

    def getAttributeNames(self):
        pass

    def getAttributeValueString(self, attibName):
        pass

    def getAttributeValues(self):
        pass

    def getAttributeValuePairs(self):
        pass

    def getOneOfAttributeValues(self, attibNames):
        pass

    def setAttributeValue(self, attibName, val):
        pass

    def concatenateAttributeValue(self, attibName, val, sep):
        pass

    def setAllAttributeValues(self, attibName, val):
        pass

    def hideAttribute(self, aname):
        pass

    def unhideAttribute(self, aname):
        pass

    def getAttribute(self, attibName):
        pass

    def getFeatureStructures(self, fss, alignmentUnit):
        pass

    def setAlignmentUnit(self, alignmentUnit):
        pass

    def loadAlignmentUnit(self, srcAlignmentObject, srcAlignmentObjectContainer, tgtAlignmentObjectContainer, parallelIndex):
        pass

    def clone(self):
        pass

    def equals(self, fa):
        pass