from typing import List
from collections import OrderedDict
from bhaashik.corpus.ssf.features import FeatureAttribute, FeatureStructure, FeatureStructures, FeatureValue
from bhaashik.tree import BhaashikMutableTreeNode
from bhaashik.corpus.ssf.features.impl import SimpleFeatureAttribute, FSProperties, FeatureStructuresImpl
from bhaashik.util import Pair
from bhaashik.xml.dom import BhaashikDOMElement

class SimpleSingleFeatureStructure(FeatureStructures, FeatureStructure, BhaashikDOMElement):
    featureValues: List[Pair[int, int]]
    has_mandatory: bool
    version: int

    def __init__(self):
        self.featureValues = []
        self.has_mandatory = False
        self.version = 2

    def addAltFSValue(self, f: FeatureStructure) -> int:
        self.copy(f)
        return 1

    def clear(self) -> None:
        self.featureValues.clear()

    def countAltFSValues(self) -> int:
        return 1

    def findAltFSValue(self, fs: FeatureStructure) -> int:
        if not self.equals(fs):
            return -1
        return 0

    def getAltFSValue(self, num: int) -> FeatureStructure:
        if num == 0:
            return self
        return None

    def getCopy(self) -> BhaashikMutableTreeNode:
        raise NotImplementedError("Not supported yet.")

    def isDeep(self) -> bool:
        return False

    def makeStringFV(self) -> str:
        fv_str = ""

        fcount = len(self.featureValues)

        for i in range(fcount):
            fv = self.featureValues[i]

            fv_str += (
                SimpleFeatureAttribute.getFeatureString(fv[0])
                + "='"
                + SimpleFeatureAttribute.getValueString(fv[1])
                + "'"
            )

            if i < fcount - 1:
                fv_str += " "

        return fv_str

    def makeString(self) -> str:
        str_ = ""
        count = self.countAttributes()

        if count == 0:
            return ""

        self.checkAndSetHasMandatory()

        fsp = FeatureStructuresImpl.getFSProperties()

        if self.version == 1:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
        elif self.version == 2:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

        if self.hasMandatoryAttribs() == True:
            str_ += (
                fsp.getProperties().getPropertyValueForPrint("basicName")
                + fsp.getProperties().getPropertyValueForPrint("attribEquate")
            )
        else:
            pass

        k = 0
        is_mand = False

        if (
            self.hasMandatoryAttribs() == True
            and count >= fsp.countMandatoryAttributes()
        ):
            is_mand = True

            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and not str_.endswith("'"):
                    str_ = str_ + "'"

                str_ += self.getAttribute(i).makeString(is_mand)

                if i + 1 < fsp.countMandatoryAttributes():
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "defAttribSeparator"
                    )
                else:
                    k = i + 1

                if i == fsp.countMandatoryAttributes() - 1 and not str_.endswith("'"):
                    str_ = str_ + "'"

        else:
            self.hasMandatoryAttribs(False)

        for i in range(k, count):
            if is_mand == True:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

                is_mand = False

            str_ += self.getAttribute(i).makeString(is_mand)

            if i + 1 < count:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

        str_ += fsp.getProperties().getPropertyValueForPrint("nodeEnd")

        return str_

    def makeStringForRendering(self) -> str:
        str_ = ""
        count = self.countAttributes()

        if count == 0:
            return ""

        self.checkAndSetHasMandatory()

        fsp = FeatureStructuresImpl.getFSProperties()

        if self.version == 1:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
        elif self.version == 2:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

        if self.hasMandatoryAttribs() == True:
            str_ += (
                fsp.getProperties().getPropertyValueForPrint("basicName")
                + fsp.getProperties().getPropertyValueForPrint("attribEquate")
            )
        else:
            pass

        k = 0
        is_mand = False

        if (
            self.hasMandatoryAttribs() == True
            and count >= fsp.countMandatoryAttributes()
        ):
            is_mand = True

            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and not str_.endswith("'"):
                    str_ = str_ + "'"

                fa = self.getAttribute(i)

                if fa.isHiddenAttribute():
                    continue

                str_ += fa.makeString(is_mand)

                if i + 1 < fsp.countMandatoryAttributes():
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "defAttribSeparator"
                    )
                else:
                    k = i + 1

                if i == fsp.countMandatoryAttributes() - 1 and not str_.endswith("'"):
                    str_ = str_ + "'"

        else:
            self.hasMandatoryAttribs(False)

        for i in range(k, count):
            if is_mand == True:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

                is_mand = False

            fa = self.getAttribute(i)

            if fa.isHiddenAttribute():
                continue

            str_ += fa.makeString(is_mand)

            if i + 1 < count:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

        str_ += fsp.getProperties().getPropertyValueForPrint("nodeEnd")

        return str_.strip()

    def modifyAltFSValue(self, fs: FeatureStructure, index: int
class SimpleSingleFeatureStructure(FeatureStructures, FeatureStructure, BhaashikDOMElement):
    def __init__(self):
        self.featureValues = []
        self.has_mandatory = False
        self.version = 2

    def addAltFSValue(self, f):
        self.copy(f)
        return 1

    def clear(self):
        self.featureValues.clear()

    def countAltFSValues(self):
        return 1

    def findAltFSValue(self, fs):
        if not self.equals(fs):
            return -1
        return 0

    def getAltFSValue(self, num):
        if num == 0:
            return self
        return None

    def getCopy(self):
        raise NotImplementedError("Not supported yet.")

    def isDeep(self):
        return False

    def makeStringFV(self):
        fv_str = ""
        fcount = len(self.featureValues)

        for i in range(fcount):
            fv = self.featureValues[i]
            fv_str += (
                SimpleFeatureAttribute.getFeatureString(fv[0])
                + "='"
                + SimpleFeatureAttribute.getValueString(fv[1])
                + "'"
            )

            if i < fcount - 1:
                fv_str += " "

        return fv_str

    def makeString(self):
        str_ = ""
        count = self.countAttributes()

        if count == 0:
            return ""

        self.checkAndSetHasMandatory()

        fsp = FeatureStructuresImpl.getFSProperties()

        if self.version == 1:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
        elif self.version == 2:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

        if self.hasMandatoryAttribs() == True:
            str_ += (
                fsp.getProperties().getPropertyValueForPrint("basicName")
                + fsp.getProperties().getPropertyValueForPrint("attribEquate")
            )
        else:
            pass

        k = 0
        is_mand = False

        if (
            self.hasMandatoryAttribs() == True
            and count >= fsp.countMandatoryAttributes()
        ):
            is_mand = True

            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and not str_.endswith("'"):
                    str_ = str_ + "'"

                str_ += self.getAttribute(i).makeString(is_mand)

                if i + 1 < fsp.countMandatoryAttributes():
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "defAttribSeparator"
                    )
                else:
                    k = i + 1

                if i == fsp.countMandatoryAttributes() - 1 and not str_.endswith("'"):
                    str_ = str_ + "'"

        else:
            self.hasMandatoryAttribs(False)

        for i in range(k, count):
            if is_mand == True:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

                is_mand = False

            str_ += self.getAttribute(i).makeString(is_mand)

            if i + 1 < count:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

        str_ += fsp.getProperties().getPropertyValueForPrint("nodeEnd")

        return str_

    def makeStringForRendering(self):
        str_ = ""
        count = self.countAttributes()

        if count == 0:
            return ""

        self.checkAndSetHasMandatory()

        fsp = FeatureStructuresImpl.getFSProperties()

        if self.version == 1:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
        elif self.version == 2:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

        if self.hasMandatoryAttribs() == True:
            str_ += (
                fsp.getProperties().getPropertyValueForPrint("basicName")
                + fsp.getProperties().getPropertyValueForPrint("attribEquate")
            )
        else:
            pass

        k = 0
        is_mand = False

        if (
            self.hasMandatoryAttribs() == True
            and count >= fsp.countMandatoryAttributes()
        ):
            is_mand = True

            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and not str_.endswith("'"):
                    str_ = str_ + "'"

                fa = self.getAttribute(i)

                if fa.isHiddenAttribute():
                    continue

                str_ += fa.makeString(is_mand)

                if i + 1 < fsp.count
class SimpleSingleFeatureStructure(FeatureStructures, FeatureStructure, BhaashikDOMElement):
    def __init__(self):
        self.featureValues = []
        self.has_mandatory = False
        self.version = 2

    def addAltFSValue(self, f):
        self.copy(f)
        return 1

    def clear(self):
        self.featureValues.clear()

    def countAltFSValues(self):
        return 1

    def findAltFSValue(self, fs):
        if not self.equals(fs):
            return -1
        return 0

    def getAltFSValue(self, num):
        if num == 0:
            return self
        return None

    def getCopy(self):
        raise NotImplementedError("Not supported yet.")

    def isDeep(self):
        return False

    def makeStringFV(self):
        fv_str = ""
        fcount = len(self.featureValues)

        for i in range(fcount):
            fv = self.featureValues[i]
            fv_str += (
                SimpleFeatureAttribute.getFeatureString(fv[0])
                + "='"
                + SimpleFeatureAttribute.getValueString(fv[1])
                + "'"
            )

            if i < fcount - 1:
                fv_str += " "

        return fv_str

    def makeString(self):
        str_ = ""
        count = self.countAttributes()

        if count == 0:
            return ""

        self.checkAndSetHasMandatory()

        fsp = FeatureStructuresImpl.getFSProperties()

        if self.version == 1:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
        elif self.version == 2:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

        if self.hasMandatoryAttribs() == True:
            str_ += (
                fsp.getProperties().getPropertyValueForPrint("basicName")
                + fsp.getProperties().getPropertyValueForPrint("attribEquate")
            )
        else:
            pass

        k = 0
        is_mand = False

        if (
            self.hasMandatoryAttribs() == True
            and count >= fsp.countMandatoryAttributes()
        ):
            is_mand = True

            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and not str_.endswith("'"):
                    str_ = str_ + "'"

                str_ += self.getAttribute(i).makeString(is_mand)

                if i + 1 < fsp.countMandatoryAttributes():
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "defAttribSeparator"
                    )
                else:
                    k = i + 1

                if i == fsp.countMandatoryAttributes() - 1 and not str_.endswith("'"):
                    str_ = str_ + "'"

        else:
            self.hasMandatoryAttribs(False)

        for i in range(k, count):
            if is_mand == True:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

                is_mand = False

            str_ += self.getAttribute(i).makeString(is_mand)

            if i + 1 < count:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

        str_ += fsp.getProperties().getPropertyValueForPrint("nodeEnd")

        return str_

    def makeStringForRendering(self):
        str_ = ""
        count = self.countAttributes()

        if count == 0:
            return ""

        self.checkAndSetHasMandatory()

        fsp = FeatureStructuresImpl.getFSProperties()

        if self.version == 1:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
        elif self.version == 2:
            str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

        if self.hasMandatoryAttribs() == True:
            str_ += (
                fsp.getProperties().getPropertyValueForPrint("basicName")
                + fsp.getProperties().getPropertyValueForPrint("attribEquate")
            )
        else:
            pass

        k = 0
        is_mand = False

        if (
            self.hasMandatoryAttribs() == True
            and count >= fsp.countMandatoryAttributes()
        ):
            is_mand = True

            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and not str_.endswith("'"):
                    str_ = str_ + "'"

                fa = self.getAttribute(i)

                if fa.isHiddenAttribute():
                    continue

                str_ += fa.makeString(is_mand)

                if i + 1 < fsp.count
                str_ += fsp.getProperties().getPropertyValueForPrint(
                    "defAttribSeparator"
                )
            else:
                k = i + 1

            if i == fsp.countMandatoryAttributes() - 1 and not str_.endswith("'"):
                str_ = str_ + "'"

        else:
            self.hasMandatoryAttribs(False)

        for i in range(k, count):
            if is_mand == True:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

                is_mand = False

            str_ += self.getAttribute(i).makeString(is_mand)

            if i + 1 < count:
                if self.version == 1:
                    str_ += fsp.getProperties().getPropertyValueForPrint(
                        "attribSeparatorV1"
                    )
                elif self.version == 2:
                    str_ += fsp.getProperties().getPropertyValue(
                        "attribSeparatorForPrinting"
                    )

        str_ += fsp.getProperties().getPropertyValueForPrint("nodeEnd")

        return str_

    def setParent(self, newParent):
        raise NotImplementedError("Not supported yet.")

    def getChildAt(self, childIndex):
        raise NotImplementedError("Not supported yet.")

    def getChildCount(self):
        raise NotImplementedError("Not supported yet.")

    def getParent(self):
        raise NotImplementedError("Not supported yet.")

    def getIndex(self, node):
        raise NotImplementedError("Not supported yet.")

    def getAllowsChildren(self):
        raise NotImplementedError("Not supported yet.")

    def isLeaf(self):
        return True

    def children(self):
        raise NotImplementedError("Not supported yet.")

    def addAttribute(self, a):
        sfa = SimpleFeatureAttribute(a)
        pair = Pair(sfa.getFeatureIndex(), sfa.getValueIndex())
        self.featureValues.append(pair)
        return len(self.featureValues)

    def addAttribute(self, a, p):
        return self.addAttribute(a)

    def addAttribute(self, name, value):
        pair = Pair(
            SimpleFeatureAttribute.getFeatureIndex(name, True),
            SimpleFeatureAttribute.getValueIndex(value, True),
        )
        self.featureValues.append(pair)
        return len(self.featureValues)

    def addMandatoryAttributes(self):
        mcount = FeatureStructuresImpl.getFSProperties().countMandatoryAttributes()

        if not self.hasMandatoryAttribs():
            for i in range(mcount):
                name = FeatureStructuresImpl.getFSProperties().getMandatoryAttribute(i)

                pair = Pair(
                    SimpleFeatureAttribute.getFeatureIndex(name, True),
                    SimpleFeatureAttribute.getValueIndex("", True),
                )

                self.featureValues.insert(i, pair)

        self.has_mandatory = True

    def countAttributes(self):
        return len(self.featureValues)

    def findAttribute(self, a):
        raise NotImplementedError("Not supported yet.")

    def getOneOfAttributes(self, names):
        raise NotImplementedError("Not supported yet.")

    def getAttribute(self, index):
        attrib = self.featureValues[index]
        f = SimpleFeatureAttribute(attrib.first, attrib.second)
        return f

    def getAttributeNames(self, p):
        raise NotImplementedError("Not supported yet.")

    def getAttributeValueByIndex(self, p):
        raise NotImplementedError("Not supported yet.")

    def getAttributeValues(self, p):
        raise NotImplementedError("Not supported yet.")

    def getFeatureTable(self):
        raise NotImplementedError("Not supported yet.")

    def getName(self):
        raise NotImplementedError("Not supported yet.")

    def getPaths(self, name):
        raise NotImplementedError("Not supported yet.")

    def getValue(self):
        raise NotImplementedError("Not supported yet.")

    def hasMandatoryAttribs(self):
        raise NotImplementedError("Not supported yet.")

    def setHasMandatoryAttribs(self, m):
        raise NotImplementedError("Not supported yet.")

    def checkAndSetHasMandatory(self):
        fsProperties = FeatureStructuresImpl.getFSProperties()
        count = self.countAttributes()
        mcount = fsProperties.countMandatoryAttributes()

        if count < mcount:
            self.setHasMandatoryAttribs(False)
            return
        else:
            for i in range(mcount):
                fa = self.searchAttribute(fsProperties.getMandatoryAttribute(i), True)

                if fa is None:
                    self.setHasMandatoryAttribs(False)
                    return

        self.setHasMandatoryAttribs(True)

    def merge(self, f1, f2):
        raise NotImplementedError("Not supported yet.")

    def modifyAttribute(self, a, index):
        raise NotImplementedError("Not supported yet.")

    def modifyAttribute(self, a, index, p):
        raise NotImplementedError("Not supported yet.")

    def modifyAttributeValue(self, fv, p):
        raise NotImplementedError("Not supported yet.")

    def modifyAttributeValue(self, fv, attribIndex, altValIndex):
        raise NotImplementedError("Not supported yet.")

    def prune(self):
        raise NotImplementedError("Not supported yet.")

    def removeAllAttributes(self):
        raise NotImplementedError("Not supported yet.")

    def removeAttribute(self, index):
        raise NotImplementedError("Not supported yet.")

    def removeAttribute(self, p):
        raise NotImplementedError("Not supported yet.")

    def removeAttribute(self, index, p):
        raise NotImplementedError("Not supported yet.")

    def removeMandatoryAttributes(self):
        raise NotImplementedError("Not supported yet.")

    def removeNonMandatoryAttributes(self):
        raise NotImplementedError("Not supported yet.")

    def searchAttribute(self, name, exactMatch):
        attribs = self.searchAttributes(name, exactMatch)

        if attribs is None or len(attribs) <= 0:
            return None

        return attribs[0]

    def searchAttributes(self, name, exactMatch):
        v = []

        lname = name

        if exactMatch:
            lname = "^" + lname + "$"

        pAttrib = re.compile(lname)

        for i in range(self.countAttributes()):
            fa = self.getAttribute(i)

            m = pAttrib.match(fa.getName())

            if m:
                v.append(fa)

            for j in range(fa.countAltValues()):
                fv = fa.getAltValue(j)

                if fv.isFeatureStructure():
                    fs = FeatureStructure(fv)
                    v += fs.searchAttributes(name, exactMatch)

        return v

    def searchAttributeValue(self, name, val, exactMatch):
        attribs = self.searchAttributeValues(name, val, exactMatch)

        if attribs is None or len(attribs) <= 0:
            return None

        return attribs[0]

    def searchAttributeValues(self, name, val, exactMatch):
        v = []

        for i in range(self.countAttributes()):
            fa = self.getAttribute(i)

            if fa.getName() == name:
                for j in range(fa.countAltValues()):
                    fv = fa.getAltValue(j)

                    if fv.isFeatureStructure() and val == "":
                        fs = FeatureStructure(fv)
                        v.append(fs)

                    elif fv.isSimpleFeatureValue() and val == fv.makeString():
                        v.append(fa)

        return v
        class SimpleSingleFeatureStructure(FeatureStructures, FeatureStructure, BhaashikDOMElement, Cloneable):
    def __init__(self):
        self.featureValues = []
        self.has_mandatory = False
        self.version = 2

    def searchAttributeValues(self, name, val, exactMatch):
        v = []

        lname = name
        lval = val

        if exactMatch:
            lname = "^" + name + "$"
            lval = "^" + val + "$"

        pAttrib = re.compile(lname)
        pVal = re.compile(lval)

        for i in range(self.countAttributes()):
            fa = self.getAttribute(i)
            mAttrib = pAttrib.match(fa.getName())
            yes = mAttrib is not None

            fv = fa.getAltValue(0)

            if yes:
                if isinstance(fv.getValue(), str):
                    sfv = fv.getValue()

                    valParts = sfv.split(":")

                    if valParts[0] is not None:
                        mVal = pVal.match(valParts[0])

                        if mVal is not None:
                            v.append(fa)

        return v

    def replaceAttributeValues(self, name, val, nameReplace, valReplace):
        v = []

        pAttrib = re.compile(name)
        pVal = re.compile(val)

        for i in range(self.countAttributes()):
            fa = self.getAttribute(i)
            mAttrib = pAttrib.match(fa.getName())
            yes = mAttrib is not None

            for j in range(fa.countAltValues()):
                fv = fa.getAltValue(j)

                if yes and not fv.isFeatureStructure():
                    if isinstance(fv.getValue(), str):
                        sfv = fv.getValue()
                        valParts = sfv.split(":")

                        if valParts[0] is not None:
                            mVal = pVal.match(valParts[0])

                            if mVal is not None:
                                v.append(fa)
                                fa.setName(nameReplace)
                                fa.getAltValue(0).setValue(valReplace)

                if fv.isFeatureStructure():
                    fs = FeatureStructureImpl(fv)
                    v += fs.replaceAttributeValues(name, val, nameReplace, valReplace)

        return v

    def setFeatureTable(self, ft):
        raise NotImplementedError("Not supported yet.")

    def setName(self, n):
        raise NotImplementedError("Not supported yet.")

    def setValue(self, v):
        raise NotImplementedError("Not supported yet.")

    def unify(self, f1, f2):
        raise NotImplementedError("Not supported yet.")

    def sortAttributes(self, sortType):
        raise NotImplementedError("Not supported yet.")

    def getDOMElement(self):
        raise NotImplementedError("Not supported yet.")

    def getXML(self):
        raise NotImplementedError("Not supported yet.")

    def readXML(self, domElement):
        raise NotImplementedError("Not supported yet.")

    def printXML(self, ps):
        raise NotImplementedError("Not supported yet.")

    def clone(self):
        obj = super().clone()
        obj.featureValues = list(self.featureValues)
        obj.has_mandatory = self.has_mandatory
        return obj

    def equals(self, obj):
        if not isinstance(obj, SimpleSingleFeatureStructure):
            return False

        if self.has_mandatory != obj.has_mandatory:
            return False

        if self.featureValues != obj.featureValues:
            return False

        return True

    def copy(self, ssfs):
        self.has_mandatory = ssfs.has_mandatory
        self.featureValues.clear()
        self.featureValues.extend(ssfs.featureValues)

    def readStringFV(self, fs_str):
        raise NotImplementedError("Not supported yet.")

    def getAttributeValues(self):
        raise NotImplementedError("Not supported yet.")

    def searchOneOfAttributes(self, names, exactMatch):
        raise NotImplementedError("Not supported yet.")

