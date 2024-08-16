import java.io.PrintStream
import java.io.Serializable
import java.util.ArrayList
import java.util.List
import edu.stanford.nlp.util.HashIndex
import edu.stanford.nlp.util.Index
from bhaashik.corpus.ssf.features import FeatureValue
from bhaashik.tree import BhaashikMutableTreeNode
from bhaashik import GlobalProperties

class FeatureValueImpl(BhaashikMutableTreeNode, FeatureValue, Cloneable, Serializable):
    valueIndex = HashIndex[str]()
    VALUE_SEPARATOR = '__'

    def __init__(self):
        super().__init__()
        self.bundle = GlobalProperties.getResourceBundle()
        self.valueIndices = []

    def __init__(self, userObject):
        super().__init__()
        self.valueIndices = self.getIndices(userObject, True)

    def __init__(self, userObject, allowsChildren):
        super().__init__(None, allowsChildren)
        self.valueIndices = self.getIndices(userObject, True)

    def getValue(self):
        return self.getString(self.valueIndices)

    def setValue(self, v):
        self.valueIndices = self.getIndices(str(v), True)

    def getUserObject(self):
        return self.getString(self.valueIndices)

    def setUserObject(self, userObject):
        self.valueIndices = self.getIndices(str(userObject), True)

    def getVocabularySize(self):
        vocabularySize = len(valueIndex)
        return vocabularySize

    def getIndices(self, wds, add):
        parts = wds.split(VALUE_SEPARATOR)
        indices = [self.valueIndex.indexOf(part, add) for part in parts]
        return indices

    def getString(self, wdIndices):
        str_ = ''
        for i, wi in enumerate(wdIndices):
            if i == 0:
                str_ = self.valueIndex.get(wi)
            else:
                str_ += VALUE_SEPARATOR + self.valueIndex.get(wi)
        return str_

    def isFeatureStructure(self):
        return False

    def readString(self, str_):
        self.setValue(str_)
        return 0

    def makeString(self):
        if self.getUserObject() is None:
            return None
        return str(self.getUserObject())

    def makeStringForRendering(self):
        return self.makeString()

    def toString(self):
        return str(self.makeString())

    def clone(self):
        obj = super().clone()
        return obj

    def print(self, ps):
        ps.println(self.makeString())

    def clear(self):
        self.setUserObject('')

    def equals(self, obj):
        if not isinstance(obj, FeatureValueImpl):
            return False
        if obj is None and self.getValue() is None:
            return True
        if obj is None or self.getValue() is None:
            return False
        if str(self.getValue()).casefold() == str(obj.getValue()).casefold():
            return True
        return False

    def main(self, *args):
        pass