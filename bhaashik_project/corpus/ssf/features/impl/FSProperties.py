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
    import io

class FSProperties:
    def __init__(self):
        self.mandatoryAttributes = []
        self.mandatoryAttribvals = []
        self.nonMandatoryAttributes = []
        self.nonMandatoryAttribvals = []
        self.properties = KeyValueProperties()
        self.psAttributes = KeyValueProperties()
        self.dependencyAttributes = KeyValueProperties()

    def read(self, maf, nmaf, pf, paf, daf, saf, charset):
        self.readMandatoryAttribs(maf, charset)
        self.readNonMandatoryAttribs(nmaf, charset)
        self.readProperties(pf, charset)
        self.psAttributes.read(paf, charset)
        self.dependencyAttributes.read(daf, charset)
        self.semanticAttributes.read(saf, charset)
        return 0

    def readProperties(self, f, charset):
        return self.properties.read(f, charset)

    def readMandatoryAttribs(self, f, charset):
        with io.open(f, encoding=charset) if charset else io.open(f) as lnReader:
            for line in lnReader:
                if not line.startswith("#") and line.strip() != "":
                    splitstr = line.split("\t", 2)
                    self.addMandatoryAttribute(splitstr[0], splitstr[1])
        return 0

    def readNonMandatoryAttribs(self, f, charset):
        with io.open(f, encoding=charset) if charset else io.open(f) as lnReader:
            for line in lnReader:
                if not line.startswith("#") and line.strip() != "":
                    splitstr = line.split("\t", 2)
                    self.addNonMandatoryAttribute(splitstr[0], splitstr[1])
        return 0

    def print(self, ps):
        ps.write("#Mandatory_attributes\n")
        for i in range(self.countMandatoryAttributes()):
            ps.write(f"{self.getMandatoryAttribute(i)}\t{self.getMandatoryAttributeValue(i)}\n")

        ps.write("#Non-Mandatory_attributes\n")
        for i in range(self.countNonMandatoryAttributes()):
            ps.write(f"{self.getNonMandatoryAttribute(i)}\t{self.getNonMandatoryAttributeValue(i)}\n")

        ps.write("#Feature_structure_properties\n")
        for key, value in self.properties.getPropertyItems():
            ps.write(f"{key}\t{value}\n")

        ps.write("#PS_attributes\n")
        self.psAttributes.print(ps)

        ps.write("#Dependency_attributes\n")
        self.dependencyAttributes.print(ps)

    def clone(self):
        obj = FSProperties()
        obj.mandatoryAttributes = list(self.mandatoryAttributes)
        obj.mandatoryAttribvals = list(self.mandatoryAttribvals)
        obj.nonMandatoryAttributes = list(self.nonMandatoryAttributes)
        obj.nonMandatoryAttribvals = list(self.nonMandatoryAttribvals)

        for i in range(self.countMandatoryAttributes()):
            obj.addMandatoryAttribute(self.getMandatoryAttribute(i), self.getMandatoryAttributeValue(i))

        for i in range(self.countNonMandatoryAttributes()):
            obj.addNonMandatoryAttribute(self.getNonMandatoryAttribute(i), self.getNonMandatoryAttributeValue(i))

        obj.properties = self.properties.clone()
        obj.psAttributes = self.psAttributes.clone()
        obj.dependencyAttributes = self.dependencyAttributes.clone()

        return obj

    def clear(self):
        self.mandatoryAttributes.clear()
        self.mandatoryAttribvals.clear()
        self.nonMandatoryAttributes.clear()
        self.nonMandatoryAttribvals.clear()
        self.properties.clear()

    # ... (other methods)

class KeyValueProperties:
    def __init__(self):
        self.properties = {}

    def read(self, f, charset):
        with io.open(f, encoding=charset) if charset else io.open(f) as propReader:
            for line in propReader:
                key, value = line.strip().split("\t", 1)
                self.setProperty(key, value)
        return 0

    def getPropertyKeys(self):
        return iter(self.properties.keys())

    def getPropertyItems(self):
        return iter(self.properties.items())

    def setProperty(self, key, value):
        self.properties[key] = value

    def getPropertyValue(self, key):
        return self.properties[key]

    def clone(self):
        new_obj = KeyValueProperties()
        new_obj.properties = dict(self.properties)
        return new_obj

    def clear(self):
        self.properties.clear()

    # ... (other methods)

# Define other missing classes or methods used in the code
# ...

# Instantiate FSProperties and other necessary classes
fs_properties_instance = FSProperties()
# Call the read method or other methods as needed
class FeatureStructuresImpl:
    @staticmethod
    def getFSProperties():
        # Implement this method based on your actual implementation
        pass

class FSProperties:
    def __init__(self):
        self.dependencyAttributesKVP = KeyValueProperties()
        self.semanticAttributesKVP = KeyValueProperties()

    # ... Other methods from the previous conversion

    @staticmethod
    def isDependencyTreeAttribute(a):
        fsProperties = FeatureStructuresImpl.getFSProperties()
        return FSProperties.isTreeAttribute(a, fsProperties.dependencyAttributesKVP)

    @staticmethod
    def getDependencyTreeAttributes():
        fsProperties = FeatureStructuresImpl.getFSProperties()
        attribsKV = fsProperties.dependencyAttributesKVP
        return FSProperties.getTreeAttributes(attribsKV)

    @staticmethod
    def getDependencyTreeAttributeProperties():
        fsProperties = FeatureStructuresImpl.getFSProperties()
        attribsKV = fsProperties.dependencyAttributesKVP
        return FSProperties.getTreeAttributeProperties(attribsKV)

    @staticmethod
    def getDependencyTreeAttributeProperties(name):
        fsProperties = FeatureStructuresImpl.getFSProperties()
        attribsKV = fsProperties.dependencyAttributesKVP
        return FSProperties.getTreeAttributeProperties(name, attribsKV)

    @staticmethod
    def isDependencyGraphAttribute(a):
        fsProperties = FeatureStructuresImpl.getFSProperties()
        return FSProperties.isGraphAttribute(a, fsProperties.dependencyAttributesKVP)

    @staticmethod
    def getDependencyGraphAttributes():
        fsProperties = FeatureStructuresImpl.getFSProperties()
        attribsKV = fsProperties.dependencyAttributesKVP
        return FSProperties.getGraphAttributes(attribsKV)

    @staticmethod
    def getDependencyGraphAttributeProperties():
        fsProperties = FeatureStructuresImpl.getFSProperties()
        attribsKV = fsProperties.dependencyAttributesKVP
        return FSProperties.getGraphAttributeProperties(attribsKV)

    @staticmethod
    def getDependencyGraphAttributeProperties(name):
        fsProperties = FeatureStructuresImpl.getFSProperties()
        attribsKV = fsProperties.dependencyAttributesKVP
        return FSProperties.getGraphAttributeProperties(name, attribsKV)

    # ... Other methods for semantic attributes

# Rest of the code remains the same
from typing import List
from collections import OrderedDict

class KeyValueProperties:
    def __init__(self):
        self.properties = OrderedDict()

    def countProperties(self):
        return len(self.properties)

    def getPropertyKeys(self):
        return iter(self.properties.keys())

    def getPropertyValue(self, key):
        return self.properties.get(key, None)

    def setProperty(self, key, value):
        self.properties[key] = value

    def clone(self):
        new_properties = KeyValueProperties()
        new_properties.properties = self.properties.copy()
        return new_properties

    def clear(self):
        self.properties.clear()


class FSProperties:
    def __init__(self):
        self.dependencyAttributesKVP = KeyValueProperties()

    @staticmethod
    def isGraphAttribute(a, attribsKV):
        val = attribsKV.getPropertyValue(a)
        if val is None:
            return False
        parts = val.split("::")
        return len(parts) == 3 and parts[2].upper() == "GRAPH"

    @staticmethod
    def getGraphAttributes(attribsKV):
        graph_attributes = [key for key, val in attribsKV.properties.items() if FSProperties.isGraphAttribute(key, attribsKV)]
        return graph_attributes

    @staticmethod
    def getGraphAttributeProperties(attribsKV):
        graph_attribute_properties = [val for val in attribsKV.properties.values() if FSProperties.isGraphAttribute(val, attribsKV)]
        return graph_attribute_properties

    @staticmethod
    def getGraphAttributePropertiesByName(name, attribsKV):
        val = attribsKV.getPropertyValue(name)
        if val is not None:
            parts = val.split("::")
            if len(parts) == 3 and parts[2].upper() == "GRAPH":
                return parts
        return None


def main():
    try:
        fsp = FSProperties()
        # Assuming GlobalProperties.resolveRelativePath and GlobalProperties.getIntlString are defined somewhere
        fsp.read(resolve_relative_path("props/fs-mandatory-attribs.txt"),
                resolve_relative_path("props/fs-other-attribs.txt"),
                resolve_relative_path("props/fs-props.txt"),
                resolve_relative_path("props/ps-attribs.txt"),
                resolve_relative_path("props/dep-attribs.txt"),
                resolve_relative_path("props/sem-attribs.txt"),
                "UTF-8")  # throws java.io.FileNotFoundException;

        fsp.print(System.out)
    except Exception as e:
        print(e)


if __name__ == "__main__":
    main()
