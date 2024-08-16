from bhaashik.table import BhaashikTableModel
from bhaashik.xml.dom import BhaashikDOMElement
from bhaashik.corpus.parallel import AlignmentUnit
from bhaashik.corpus.ssf import SSFCorpus, SSFSentence, SSFStory
from bhaashik.corpus.ssf.features import FeatureAttribute, FeatureStructure, FeatureValue
from bhaashik.corpus.ssf.query import SSFQueryMatchNode
from bhaashik.corpus.ssf.tree import SSFLexItem, SSFNode, SSFPhrase
from bhaashik.tree import BhaashikMutableTreeNode
from bhaashik.util import UtilityFunctions
from bhaashik.xml.dom import BhaashikDOMElement
from java.util import ArrayList, List
import org.dom4j.dom.DOMAttribute
import org.dom4j.dom.DOMElement
import org.w3c.dom.Element
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node

class FeatureStructureImpl(BhaashikMutableTreeNode, FeatureStructure, FeatureValue, BhaashikDOMElement, Serializable):

    def __init__(self):
        super().__init__()
        self.name = ''
        self.has_mandatory = False
        self.version = 2

    def __init__(self, userObject):
        super().__init__(userObject)
        self.has_mandatory = False

    def __init__(self, userObject, n):
        super().__init__(userObject)
        self.name = n
        self.has_mandatory = False

    def __init__(self, userObject, allowsChildren):
        super().__init__(userObject, allowsChildren)
        self.name = ''
        self.has_mandatory = False

    def getValue(self):
        return self

    def setValue(self, v):
        self.setUserObject(self)

    def getName(self):
        return self.name

    def setName(self, n):
        self.name = n

    def setVersion(self, v):
        self.version = v

    def isFeatureStructure(self):
        return True

    def hasMandatoryAttribs(self):
        return self.has_mandatory

    def hasMandatoryAttribs(self, m):
        self.has_mandatory = m

    def checkAndSetHasMandatory(self):
        fsProperties = FeatureStructuresImpl.getFSProperties()
        count = self.countAttributes()
        mcount = fsProperties.countMandatoryAttributes()
        if count < mcount:
            self.hasMandatoryAttribs(False)
            return
        else:
            for i in range(mcount):
                fa = self.searchAttribute(fsProperties.getMandatoryAttribute(i), True)
                if fa == None:
                    self.hasMandatoryAttribs(False)
                    return
        self.hasMandatoryAttribs(True)

    def countAttributes(self):
        return self.getChildCount()

    def addAttribute(self, a):
        self.add(a)
        return self.getChildCount()

    def insertAttribute(self, a, index):
        self.insert(a, index)
        return self.getChildCount()

    def addAttribute(self, a, p):
        fv = self.getAttributeValue(p)
        fs = None
        ret = -1
        if fv.isFeatureStructure() == True:
            fs = fv
            ret = fs.addAttribute(a)
        return ret

    def addAttribute(self, name_, value):
        fa = self.searchAttribute(name_, True)
        ret = 0
        if fa == None:
            fa = FeatureAttributeImpl()
            fa.setName(name_)
            self.addAttribute(fa)
            ret = 1
        fv = None
        if fa.countAltValues() > 0:
            fv = fa.getAltValue(0)
        if fv == None:
            fv = FeatureValueImpl()
            fv.setValue(value)
            fa.addAltValue(fv)
        else:
            fv.setValue(value)
        return ret

    def addMandatoryAttributes(self):
        count = self.countAttributes()
        mcount = FeatureStructuresImpl.getFSProperties().countMandatoryAttributes()
        if self.hasMandatoryAttribs() == False:
            for i in range(mcount):
                fa = FeatureAttributeImpl()
                fa.setName(FeatureStructuresImpl.getFSProperties().getMandatoryAttribute(i))
                fa.addAltValue(FeatureValueImpl(''))
                self.insertAttribute(fa, i)
        self.has_mandatory = True

    def getAttribute(self, index):
        return self.getChildAt(index)

    def findAttribute(self, a):
        return self.getIndex(a)

    def getAttribute(self, p):
        paths = p.split('[.]')
        node = self
        pointer = None
        fv = None
        for i in range(len(paths)):
            parts = paths[i].split('[@]')
            altValIndex = 0
            attribName = paths[i]
            if len(parts) == 2:
                attribName = parts[0]
                altValIndex = int(parts[1])
            for k in range(node.countAttributes()):
                pointer = node.getAttribute(k)
                if pointer.getName().equals(attribName):
                    if i == len(paths) - 1:
                        return pointer
                    fv = pointer.getAltValue(altValIndex)
                    if fv.isFeatureStructure() == True:
                        node = fv
        return None

    def getOneOfAttributes(self, names):
        for i in range(len(names)):
            n = names[i]
            fa = self.getAttribute(n)
            if fa != None:
                return fa
        return None

    def modifyAttribute(self, a, index):
        self.insert(a, index)
        self.remove(index + 1)

    def modifyAttribute(self, a, index, p):
        fv = self.getAttributeValue(p)
        fs = None
        if fv.isFeatureStructure() == True:
            fs = fv
            fs.modifyAttribute(a, index)

    def modifyAttributeValue(self, fv, attribIndex, altValIndex):
        fa = self.getAttribute(attribIndex)
        fa.modifyAltValue(fv, altValIndex)class MyClass(FeatureStructuresImpl):

    def modifyAttributeValue(self, fv, p):
        attrib = self.getAttribute(p)
        paths = p.split('.')
        parts = paths[len(paths) - 1].split('@')
        altValIndex = 0
        if len(parts) == 2:
            altValIndex = int(parts[1])
        attrib.modifyAltValue(fv, altValIndex)

    def removeAttribute(self, index):
        rem = self.getAttribute(index)
        self.remove(index)
        return rem

    def removeAttribute(self, p):
        rem = self.getAttribute(p)
        if rem is not None:
            if FeatureStructuresImpl.getFSProperties().isMandatory(rem.getName()) == False:
                self.remove(self.findAttribute(rem))
            else:
                fv = rem.getAltValue(0)
                if fv is not None:
                    fv.setValue('')
        return rem

    def removeAllAttributes(self):
        while self.countAttributes() > 0:
            self.removeAttribute(0)
        self.hasMandatoryAttribs(False)

    def removeMandatoryAttributes(self):
        if self.hasMandatoryAttribs() == False:
            return
        count = self.countAttributes()
        mcount = FeatureStructuresImpl.getFSProperties().countMandatoryAttributes()
        if mcount > 0 and count >= mcount:
            for i in range(mcount):
                mAtrribName = FeatureStructuresImpl.getFSProperties().getMandatoryAttribute(i)
                fa = self.getAttribute(mAtrribName)
                if fa is not None:
                    self.remove(fa)
        self.hasMandatoryAttribs(False)

    def removeNonMandatoryAttributes(self):
        if self.hasMandatoryAttribs() == False:
            self.removeAllAttributes()
            return
        count = self.countAttributes()
        i = 0
        while i < count:
            fa = self.getAttribute(i)
            if FeatureStructuresImpl.getFSProperties().isMandatory(fa.getName()) == False:
                self.remove(fa)
                i -= 1
                count -= 1
            i += 1

    def removeAttribute(self, index, p):
        fv = self.getAttributeValue(p)
        fs = None
        ret = None
        if fv.isFeatureStructure() == True:
            fs = FeatureStructureImpl(fv)
            ret = fs.removeAttribute(index)
        return ret

    def hideAttribute(self, aname):
        hiddenAttr = self.getAttribute(aname)
        if hiddenAttr is not None:
            hiddenAttr.hideAttribute()

    def unhideAttribute(self, aname):
        hiddenAttr = self.getAttribute(aname)
        if hiddenAttr is not None:
            hiddenAttr.unhideAttribute()

    def getAttributeNames(self):
        v = []
        for k in range(self.countAttributes()):
            v.append(self.getAttribute(k).getName())
        return v

    def getAttributeNames(self, p):
        fv = self.getAttributeValue(p)
        fs = None
        ret = None
        if fv.isFeatureStructure() == True:
            fs = FeatureStructureImpl(fv)
            ret = fs.getAttributeNames()
        return ret

    def getAttributeValues(self):
        v = []
        for k in range(self.countAttributes()):
            fa = self.getAttribute(k)
            attribVal = self.getAttributeValue(fa.getName()).makeString()
            v.append(attribVal)
        return v

    def getAttributeValuePairs(self):
        v = []
        for k in range(self.countAttributes()):
            fa = self.getAttribute(k)
            attribVal = fa.getName() + '=' + self.getAttributeValue(fa.getName()).makeString()
            v.append(attribVal)
        return v

    def getAttributeValueByIndex(self, p):
        attrib = self.getAttribute(p)
        paths = p.split('.')
        parts = paths[len(paths) - 1].split('@')
        altValIndex = 0
        if len(parts) == 2:
            altValIndex = int(parts[1])
        return attrib.getAltValue(altValIndex)

    def getAttributeValue(self, p):
        vals = self.getAttributeValues(p)
        if vals is None or len(vals) <= 0:
            return None
        return vals[0]

    def getAttributeValueString(self, p):
        vals = self.getAttributeValues(p)
        if vals is None or len(vals) <= 0:
            return None
        return vals[0].makeString()

    def setAttributeValue(self, attibName, val):
        if self.hasMandatoryAttribs() == False and FeatureStructuresImpl.getFSProperties().isMandatory(attibName) == True:
            self.addMandatoryAttributes()
        fa = self.getAttribute(attibName)
        if fa is None:
            fa = FeatureAttributeImpl()
            fa.setName(attibName)
            self.addAttribute(fa)
        fv = None
        if fa.countAltValues() == 0:
            fv = FeatureValueImpl()
            fa.addAltValue(fv)
        else:
            fv = fa.getAltValue(0)
        val = SSFQueryMatchNode.stripQuotes(val)
        fv.setValue(val)

    def getAttributeValues(self, p):
        count = self.countAttributes()
        ret = []
        for i in range(count):
            fa = self.getAttribute(p)
            if fa is not None and fa.getName() == p and (fa.countAltValues() > 0):
                fv = fa.getAltValue(i)
                ret.append(fv)
        return ret

    def getPaths(self, name):
        v = []
        for i in range(self.countAttributes()):
            fa = self.getAttribute(i)
            path = fa.getName()
            if fa.getName().lower() == name.lower():
                v.append(fa)
            for j in range(fa.countAltValues()):
                fv = fa.getAltValue(i)
                if fv.isFeatureStructure() == True:
                    fs = FeatureStructureImpl(fv)
                    v.extend(fs.getPaths(name, path))
        return vfrom typing import List
import re

class FeatureAttribute:

    def __init__(self, name: str, alt_values: List[str]):
        self.name = name
        self.alt_values = alt_values

class FeatureValue:

    def __init__(self, value):
        self.value = value

    def isFeatureStructure(self) -> bool:
        pass

class FeatureStructureImpl:

    def __init__(self, attributes: List[FeatureAttribute]):
        self.attributes = attributes

    def countAttributes(self) -> int:
        pass

    def getAttribute(self, index: int) -> FeatureAttribute:
        pass

    def getPaths(self, name: str, p: str) -> List[FeatureAttribute]:
        pass

    def searchOneOfAttributes(self, names: List[str], exactMatch: bool) -> FeatureAttribute:
        pass

    def searchAttribute(self, name: str, exactMatch: bool) -> FeatureAttribute:
        pass

    def searchAttributes(self, name: str, exactMatch: bool) -> List[FeatureAttribute]:
        pass

    def searchAttributeValue(self, name: str, val: str, exactMatch: bool) -> FeatureAttribute:
        pass

    def searchAttributeValues(self, name: str, val: str, exactMatch: bool) -> List[FeatureAttribute]:
        pass

    def replaceAttributeValues(self, name: str, val: str, nameReplace: str, valReplace: str) -> List[FeatureAttribute]:
        pass

    def unify(self, f1: FeatureStructure, f2: FeatureStructure) -> FeatureStructure:
        pass

    def merge(self, f1: FeatureStructure, f2: FeatureStructure) -> FeatureStructure:
        pass

    def prune(self):
        pass

    def getFeatureTable(self) -> List[FeatureAttribute]:
        pass

class BhaashikTableModel:

    def __init__(self, numRows: int, numCols: int):
        self.numRows = numRows
        self.numCols = numCols

    def setColumnIdentifiers(self, identifiers: List[str]):
        pass

    def setValueAt(self, value, row: int, col: int):
        pass

def getPaths(name: str, p: str) -> List[FeatureAttribute]:
    v = []
    for i in range(countAttributes()):
        fa = getAttribute(i)
        path = p + '.' + fa.name
        if fa.name.lower() == name.lower():
            v.append(fa)
        for j in range(fa.countAltValues()):
            fv = fa.getAltValue(i)
            if fv.isFeatureStructure():
                fs = FeatureStructureImpl(fv)
                v.extend(fs.getPaths(name, path))
    return v

def searchOneOfAttributes(names: List[str], exactMatch: bool) -> FeatureAttribute:
    for name in names:
        attribs = searchAttributes(name, exactMatch)
        if attribs is None or len(attribs) <= 0:
            continue
        return attribs[0]
    return None

def searchAttribute(name: str, exactMatch: bool) -> FeatureAttribute:
    attribs = searchAttributes(name, exactMatch)
    if attribs is None or len(attribs) <= 0:
        return None
    return attribs[0]

def searchAttributes(name: str, exactMatch: bool) -> List[FeatureAttribute]:
    v = []
    lname = name
    if exactMatch:
        lname = '^' + lname + '$'
    pAttrib = re.compile(lname)
    for i in range(countAttributes()):
        fa = getAttribute(i)
        m = pAttrib.match(fa.name)
        if m:
            v.append(fa)
        for j in range(fa.countAltValues()):
            fv = fa.getAltValue(j)
            if fv.isFeatureStructure():
                fs = FeatureStructureImpl(fv)
                v.extend(fs.searchAttributes(name, exactMatch))
    return v

def searchAttributeValue(name: str, val: str, exactMatch: bool) -> FeatureAttribute:
    attribs = searchAttributeValues(name, val, exactMatch)
    if attribs is None or len(attribs) <= 0:
        return None
    return attribs[0]

def searchAttributeValues(name: str, val: str, exactMatch: bool) -> List[FeatureAttribute]:
    v = []
    lname = name
    lval = val
    if exactMatch:
        lname = '^' + name + '$'
        lval = '^' + val + '$'
    pAttrib = re.compile(lname)
    pVal = re.compile(lval)
    for i in range(countAttributes()):
        fa = getAttribute(i)
        mAttrib = pAttrib.match(fa.name)
        yes = mAttrib
        for j in range(fa.countAltValues()):
            fv = fa.getAltValue(j)
            if yes and (not fv.isFeatureStructure()):
                if isinstance(fv.value, str):
                    sfv = fv.value
                    valParts = sfv.split(':')
                    if valParts[0] is not None:
                        mVal = pVal.match(valParts[0])
                        if mVal:
                            v.append(fa)
            if fv.isFeatureStructure():
                fs = FeatureStructureImpl(fv)
                v.extend(fs.searchAttributeValues(name, val, exactMatch))
    return v

def replaceAttributeValues(name: str, val: str, nameReplace: str, valReplace: str) -> List[FeatureAttribute]:
    v = []
    pAttrib = re.compile(name)
    pVal = re.compile(val)
    for i in range(countAttributes()):
        fa = getAttribute(i)
        mAttrib = pAttrib.match(fa.name)
        yes = mAttrib
        for j in range(fa.countAltValues()):
            fv = fa.getAltValue(j)
            if yes and (not fv.isFeatureStructure()):
                if isinstance(fv.value, str):
                    sfv = fv.value
                    valParts = sfv.split(':')
                    if valParts[0] is not None:
                        mVal = pVal.match(valParts[0])
                        if mVal:
                            v.append(fa)
                            fa.name = nameReplace
                            fa.getAltValue(0).value = valReplace
            if fv.isFeatureStructure():
                fs = FeatureStructureImpl(fv)
                v.extend(fs.replaceAttributeValues(name, val, nameReplace, valReplace))
    return v

def unify(f1: FeatureStructure, f2: FeatureStructure) -> FeatureStructure:
    return None

def merge(f1: FeatureStructure, f2: FeatureStructure) -> FeatureStructure:
    return None

def prune():
    pass

def getFeatureTable() -> BhaashikTableModel:
    acount = countAttributes()
    table = BhaashikTableModel(acount, 2)
    table.setColumnIdentifiers(['Feature', 'Value'])
    for i in range(acount):
        table.setValueAt(getAttribute(i).name, i, 0)
        table.setValueAt(getAttribute(i).alt_values[0], i, 1)
    return tabledef setFeatureTable(self, ft):
    self.removeAllChildren()
    count = ft.getRowCount()
    for i in range(count):
        fa = FeatureAttributeImpl()
        fa.setName(str(ft.getValueAt(i, 0)))
        fsp = FeatureStructuresImpl.getFSProperties()
        nodeStart = fsp.getProperties().getPropertyValue('nodeStart')
        nodeEnd = fsp.getProperties().getPropertyValue('nodeEnd')
        vstr = str(ft.getValueAt(i, 1))
        fv = None
        if vstr.startswith(nodeStart) and vstr.endswith(nodeEnd):
            fv = FeatureStructureImpl()
            fv.version = 1
        else:
            fv = FeatureValueImpl()
        try:
            fv.readString(vstr)
        except Exception as ex:
            ex.printStackTrace()
        fa.addAltValue(fv)
        self.addAttribute(fa)

def readString(self, fs_str):
    self.clear()
    fsPtn = re.compile('^<\\s*fs', re.IGNORECASE)
    m = fsPtn.search(fs_str)
    if m:
        return self.readStringV2(fs_str)
    self.version = 1
    fs_str = fs_str.replace("'", '')
    fsp = FeatureStructuresImpl.getFSProperties()
    basicName = fsp.getProperties().getPropertyValue('basicName')
    nodeStart = fsp.getProperties().getPropertyValue('nodeStart')
    nodeEnd = fsp.getProperties().getPropertyValue('nodeEnd')
    defAttribSeparator = fsp.getProperties().getPropertyValue('defAttribSeparator')
    attribSeparatorV1 = fsp.getProperties().getPropertyValue('attribSeparatorV1')
    fsOR = fsp.getProperties().getPropertyValue('fsOR')
    attribOR = fsp.getProperties().getPropertyValue('attribOR')
    attribEquate = fsp.getProperties().getPropertyValue('attribEquate')
    if fs_str == nodeStart or fs_str == nodeEnd or fs_str == nodeStart + nodeEnd:
        return self.readError(fs_str)
    str = ''
    position = 0
    temp = fs_str.split(nodeStart, 2)
    if temp[0] != '':
        return self.readError(fs_str)
    position += 1
    tstr = temp[1]
    temp = tstr.split(attribEquate, 2)
    if temp[0] == '':
        return self.readError(fs_str)
    if temp[0].strip() == basicName:
        self.setName(basicName)
        position += len(basicName) + 1
        tstr = temp[1]
        temp = tstr.split(defAttribSeparator)
        if len(temp) == fsp.countMandatoryAttributes() + 1:
            temp1 = temp[1:]
            temp = temp1
            position += len(defAttribSeparator)
        if len(temp) != fsp.countMandatoryAttributes():
            return self.readError(fs_str)
        position += fsp.countMandatoryAttributes() - 1
        for i in range(fsp.countMandatoryAttributes() - 1):
            fa = FeatureAttributeImpl()
            fa.setName(fsp.getMandatoryAttribute(i))
            fv = FeatureValueImpl()
            fv.setValue(temp[i])
            fa.addAltValue(fv)
            self.addAttribute(fa)
            position += len(temp[i])
        k = fsp.countMandatoryAttributes()
        fa = FeatureAttributeImpl()
        fa.setName(fsp.getMandatoryAttribute(k - 1))
        fv = FeatureValueImpl()
        fv.setValue('')
        fa.addAltValue(fv)
        self.addAttribute(fa)
        end = True
        if attribSeparatorV1 in temp[k - 1]:
            if temp[k - 1].index(nodeEnd) > temp[k - 1].index(attribSeparatorV1):
                end = False
        if end:
            temp1 = temp[k - 1].split(nodeEnd, 2)
            temp[k - 1] = nodeEnd
        else:
            temp1 = temp[k - 1].split(attribSeparatorV1, 2)
            temp[k - 1] = attribSeparatorV1 + temp1[1]
        vl = temp1[0]
        fv.setValue(vl)
        position += len(temp1[0])
        tstr = temp[k - 1]
        temp = tstr.split(attribSeparatorV1, 2)
        if temp[0] == nodeEnd:
            return position + len(temp[0])
        elif temp[0] == '' and temp[1] != '':
            tstr = temp[1]
            position += 1
        else:
            return self.readError(fs_str)
        args = ReadRecurseArgs(tstr, 0, 0, None)
        ret = self.readRecurse(args)
        if ret == -1:
            return self.readError(fs_str)
        else:
            return position + ret
    else:
        args = ReadRecurseArgs(tstr, 0, 0, None)
        ret = self.readRecurse(args)
        if ret == -1:
            return self.readError(fs_str)
        else:
            self.checkAndSetHasMandatory()
            return position + ret

def readStringV2(self, fs_str):
    self.version = 2
    fsp = FeatureStructuresImpl.getFSProperties()
    basicName = fsp.getProperties().getPropertyValue('basicName')
    attribSeparatorV2 = fsp.getProperties().getPropertyValue('attribSeparatorV2')
    pos = len(fs_str)
    fs_str = fs_str.replace('>', '')
    fs_str = fs_str.strip()
    avpairs = fs_str.split(attribSeparatorV2)
    for i in range(1, len(avpairs)):
        av = avpairs[i].split('=')
        if len(av) == 2:
            av[0] = av[0].strip()
            av[0] = SSFQueryMatchNode.stripBoundingStrings(av[0], "'", "'")
            av[1] = av[1].strip()
            av[1] = SSFQueryMatchNode.stripBoundingStrings(av[1], "'", "'")
            if av[0].lower() == basicName.lower():
                ma = av[1].split(',')
                for j in range(len(ma)):
                    a = FeatureAttributeImpl()
                    a.setName(fsp.getMandatoryAttribute(j))
                    v = FeatureValueImpl()
                    v.setValue(ma[j])
                    a.addAltValue(v)
                    self.addAttribute(a)
                count = fsp.countMandatoryAttributes()
                if len(ma) < count:
                    for j in range(len(ma), count):
                        a = FeatureAttributeImpl()
                        a.setName(fsp.getMandatoryAttribute(j))
                        v = FeatureValueImpl()
                        v.setValue('')
                        a.addAltValue(v)
                        self.addAttribute(a)
            else:
                a = FeatureAttributeImpl()
                a.setName(av[0])
                v = FeatureValueImpl()
                v.setValue(av[1])
                a.addAltValue(v)
                self.addAttribute(a)
    self.checkAndSetHasMandatory()
    return pos
def read_recurse(args):
    fsp = FeatureStructuresImpl.getFSProperties()

    pointer = ['']
    end = False
    point_str = ''
    attrib_name = ''
    attrib_value = ''

    while not end:
        is_attrib = False

        pointer[0] = args.str[args.position]
        point_str = ''.join(pointer)
        attrib_name = ''

        while not is_attrib:
            args.position += 1

            if re.match("[A-Za-z0-9_\\-]", point_str):
                attrib_name += point_str
            elif point_str == fsp.getProperties().getPropertyValue("attribEquate"):
                is_attrib = True
            elif point_str in ('"', "`", "'"):
                pass  # Quote: ignore
            else:
                return read_error(point_str)

            pointer[0] = args.str[args.position]
            point_str = ''.join(pointer)

        fa = FeatureAttributeImpl()
        fa.setName(attrib_name)
        add_attribute(fa)

        attrib_value = ''
        is_or = True

        while is_or:
            if point_str == fsp.getProperties().getPropertyValue("nodeStart"):
                fs = FeatureStructureImpl()
                fa.addAltValue(fs)
                fs.version = 1
                args1 = ReadRecurseArgs(args.str, args.level + 1, args.position + 1, None)
                args.position = fs.read_recurse(args1)
            else:
                is_value = False
                attrib_value = ''

                while not is_value:
                    if re.match("[A-Za-z0-9':,_ %\\-\u002E\u00C0-\u0F7D1]", point_str):
                        attrib_value += point_str
                    elif point_str == fsp.getProperties().getPropertyValue("attribSeparatorV1"):
                        is_or = False
                        is_value = True
                        args.position -= 1
                        fv = FeatureValueImpl(attrib_value)
                        fa.addAltValue(fv)
                    elif point_str == fsp.getProperties().getPropertyValue("nodeEnd"):
                        is_value = True
                        end = True
                        fv = FeatureValueImpl(attrib_value)
                        fa.addAltValue(fv)
                        return args.position
                    elif point_str == fsp.getProperties().getPropertyValue("attribOR"):
                        is_value = True
                        fv = FeatureValueImpl(attrib_value)
                        fa.addAltValue(fv)
                    elif point_str in ('"', "`", "'"):
                        pass  # Quote: ignore
                    else:
                        return read_error(point_str)

                    args.position += 1
                    pointer[0] = args.str[args.position]
                    point_str = ''.join(pointer)

                args.position += 1
                pointer[0] = args.str[args.position]
                point_str = ''.join(pointer)

                if point_str == fsp.getProperties().getPropertyValue("nodeEnd"):
                    end = True
                    return args.position
                elif point_str == fsp.getProperties().getPropertyValue("attribSeparatorV1"):
                    is_or = False
                    args.position += 1
                elif point_str == fsp.getProperties().getPropertyValue("attribOR"):
                    is_or = True
                    args.position += 1

    return 0

def read_error(fs_str):
    print("Error in FS input:", fs_str)
    return -1

def print(ps):
    ps.println(make_string())

def make_string_fv():
    fv_str = ""
    fcount = count_attributes()

    for i in range(fcount):
        fa = get_attribute(i)

        if fa.count_alt_values() == 0:
            continue

        fv_str += fa.get_name() + "='" + fa.get_alt_value(0).get_value() + "'"

        if i < fcount - 1:
            fv_str += " "

    return fv_str

def make_string():
    str_ = ""
    count = count_attributes()

    if count == 0:
        return ""

    check_and_set_has_mandatory()

    fsp = FeatureStructuresImpl.getFSProperties()

    if version == 1:
        str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart")
    elif version == 2:
        str_ += fsp.getProperties().getPropertyValueForPrint("nodeStart") + "fs "

    if has_mandatory_attribs() is True:
        str_ += fsp.getProperties().getPropertyValueForPrint("basicName") + fsp.getProperties().getPropertyValueForPrint("attribEquate")
    else:
        pass

    k = 0
    is_mand = False

    if has_mandatory_attribs() is True and count_attributes() >= fsp.count_mandatory_attributes():
        is_mand = True

        for i in range(fsp.count_mandatory_attributes()):
            if i == 0 and not str_.endswith("'"):
                str_ = str_ + "'"

            str_ += get_attribute(i).make_string(is_mand)

            if (i + 1) < fsp.count_mandatory_attributes():
                str_ += fsp.getProperties().getPropertyValueForPrint("defAttribSeparator")
            else:
                k = i + 1

            if i == fsp.count_mandatory_attributes() - 1 and not str_.endswith("'"):
                str_ = str_ + "'"

    else:
        has_mandatory_attribs(False)

    for i in range(k, count):
        if is_mand is True:
            if version == 1:
                str_ += fsp.getProperties().getPropertyValueForPrint("attribSeparatorV1")
            elif version == 2:
                str_ += fsp.getProperties().getPropertyValue("attribSeparatorForPrinting")
            is_mand = False

        str_ += get_attribute(i).make_string(is_mand)

        if (i + 1) < count:
            if version == 1:
                str_ += fsp.getProperties().getPropertyValueForPrint("attribSeparatorV1")
            elif version == 2:
                str_ += fsp.getProperties().getPropertyValue("attribSeparatorForPrinting")

    str_ += fsp.getProperties().getPropertyValueForPrint("nodeEnd")

    return str_
class ClassName(object):

    def __init__(self):
        self.name = ''
        self.version = 0
        self.children = []

    def makeString(self):
        str = ''
        count = self.countAttributes()
        if count == 0:
            return ''
        self.checkAndSetHasMandatory()
        fsp = FeatureStructuresImpl.getFSProperties()
        if self.version == 1:
            str += fsp.getProperties().getPropertyValueForPrint('nodeStart')
        elif self.version == 2:
            str += fsp.getProperties().getPropertyValueForPrint('nodeStart') + 'fs '
        if self.hasMandatoryAttribs() == True:
            str += fsp.getProperties().getPropertyValueForPrint('basicName') + fsp.getProperties().getPropertyValueForPrint('attribEquate')
        else:
            pass
        k = 0
        is_mand = False
        if self.hasMandatoryAttribs() == True and self.countAttributes() >= fsp.countMandatoryAttributes():
            is_mand = True
            for i in range(fsp.countMandatoryAttributes()):
                if i == 0 and str.endswith("'") == False:
                    str += "'"
                fa = self.getAttribute(i)
                if fa.isHiddenAttribute():
                    continue
                str += fa.makeString(is_mand)
                if i + 1 < fsp.countMandatoryAttributes():
                    str += fsp.getProperties().getPropertyValueForPrint('defAttribSeparator')
                else:
                    k = i + 1
                if i == fsp.countMandatoryAttributes() - 1 and str.endswith("'") == False:
                    str += "'"
        else:
            self.hasMandatoryAttribs(False)
        for i in range(k, count):
            if is_mand == True:
                if self.version == 1:
                    str += fsp.getProperties().getPropertyValueForPrint('attribSeparatorV1')
                elif self.version == 2:
                    str += fsp.getProperties().getPropertyValue('attribSeparatorForPrinting')
                is_mand = False
            fa = self.getAttribute(i)
            if fa.isHiddenAttribute():
                continue
            str += fa.makeString(is_mand)
            if i + 1 < count:
                if self.version == 1:
                    str += fsp.getProperties().getPropertyValueForPrint('attribSeparatorV1')
                elif self.version == 2:
                    str += fsp.getProperties().getPropertyValue('attribSeparatorForPrinting')
        str += fsp.getProperties().getPropertyValueForPrint('nodeEnd')
        return str.strip()

    def toString(self):
        return self.makeString()

    def clear(self):
        self.name = ''
        self.removeAllAttributes()

    def setToEmpty(self):
        self.removeAllAttributes()

    def clearAnnotation(self, annoLevelFlags, containingNode):
        count = self.countAttributes()
        if isinstance(containingNode, SSFLexItem) and UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEX_MANDATORY_ATTRIBUTES) or (isinstance(containingNode, SSFPhrase) and UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES)):
            self.removeMandatoryAttributes()
        if isinstance(containingNode, SSFLexItem) and UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.LEX_EXTRA_ATTRIBUTES) or (isinstance(containingNode, SSFPhrase) and UtilityFunctions.flagOn(annoLevelFlags, SSFCorpus.CHUNK_EXTRA_ATTRIBUTES)):
            self.removeNonMandatoryAttributes()

    def sortAttributes(self, sortType):
        self.children.sort(key=lambda x: x, cmp=FeatureAttributeImpl.getAttributeComparator(sortType))

    def readStringFV(self, fs_str):
        self.clear()
        fvPairs = fs_str.split('[\\s]+')
        for i in range(len(fvPairs)):
            fvPair = fvPairs[i]
            fvparts = fvPair.split('=')
            if len(fvparts) != 2:
                continue
            f = fvparts[0]
            v = fvparts[1]
            v = SSFQueryMatchNode.stripBoundingStrings(v, "'", "'")
            v = SSFQueryMatchNode.stripBoundingStrings(v, '"', '"')
            self.addAttribute(f, v)
        return self.countAttributes()

    def setAlignmentUnit(self, alignmentUnit):
        alignmentObject = alignmentUnit.getAlignmentObject()
        alignName = 'alignedTo'
        alignVal = ''
        if isinstance(alignmentObject, SSFNode):
            itr = alignmentUnit.getAlignedUnitKeys()
            while itr.hasNext():
                key = itr.next()
                alignedUnit = alignmentUnit.getAlignedUnit(key)
                alignedNode = SSFNode(alignedUnit.getAlignmentObject())
                alignVal += alignedNode.getAttributeValue('name') + ';'
        elif isinstance(alignmentObject, SSFSentence):
            itr = alignmentUnit.getAlignedUnitKeys()
            while itr.hasNext():
                key = itr.next()
                alignedUnit = alignmentUnit.getAlignedUnit(key)
                alignedSentence = SSFSentence(alignedUnit.getAlignmentObject())
                alignVal += alignedSentence.getId() + ';'
        if alignVal.equals('') == False:
            addAttribute = self.addAttribute(alignName, alignVal)
        else:
            removeAttribute('alignedTo')

    def loadAlignmentUnit(self, srcAlignmentObject, srcAlignmentObjectContainer, tgtAlignmentObjectContainer, parallelIndex):
        alignFV = self.getAttributeValue('alignedTo')
        if alignFV == None:
            return None
        alignVal = alignFV.getValue()
        alignVal = SSFQueryMatchNode.stripBoundingStrings(alignVal, "'", "'")
        if alignVal.charAt(alignVal.length() - 1) == ';':
            alignVal = alignVal.substring(0, alignVal.length())
        alignVals = alignVal.split(';')
        alignmentUnit = AlignmentUnit()
        if isinstance(srcAlignmentObjectContainer, SSFStory):
            srcDocument = SSFStory(srcAlignmentObjectContainer)
            alignmentSentence = SSFSentence(srcAlignmentObject)
            alignmentIndex = srcDocument.findSentenceIndex(alignmentSentence.getId())
            if alignmentIndex < 0:
                return None
            alignmentUnit.setAlignmentObject(alignmentSentence)
            alignmentUnit.setParallelIndex(parallelIndex)
            alignmentUnit.setIndex(alignmentIndex)
        elif isinstance(srcAlignmentObjectContainer, SSFSentence):
            srcSentence = SSFSentence(srcAlignmentObjectContainer)
            alignmentNode = SSFNode(srcAlignmentObject)
            alignmentIndex = srcSentence.findChildIndexByName(alignmentNode.getAttributeValue('name'))
            if alignmentIndex < 0:
                return None
            alignmentUnit.setAlignmentObject(alignmentNode)
            alignmentUnit.setParallelIndex(parallelIndex)
            alignmentUnit.setIndex(alignmentIndex)
        for i in range(alignVals):
            string = alignVals[i]
            alignedUnit = AlignmentUnit()
            if isinstance(tgtAlignmentObjectContainer, SSFStory):
                tgtDocument = SSFStory(tgtAlignmentObjectContainer)
                alignedSentence = tgtDocument.findSentence(string)
                if alignedSentence != None:
                    alignedIndex = tgtDocument.findSentenceIndex(alignedSentence.getId())
                    alignedUnit.setAlignmentObject(alignedSentence)
                    if parallelIndex == 0:
                        alignedUnit.setParallelIndex(2)
                    elif parallelIndex == 2:
                        alignedUnit.setParallelIndex(0)
                    alignedUnit.setIndex(alignedIndex)
                    alignmentUnit.addAlignedUnit(alignedUnit)
                    alignedUnit.addAlignedUnit(alignmentUnit)
            elif isinstance(tgtAlignmentObjectContainer, SSFSentence):
                tgtSentence = SSFSentence(tgtAlignmentObjectContainer)
                alignedNode = tgtSentence.findChildByName(string)
                if alignedNode != None:
                    alignedIndex = tgtSentence.findChildIndexByName(alignedNode.getAttributeValue('name'))
                    alignedUnit.setAlignmentObject(alignedNode)
                    if parallelIndex == 0:
                        alignedUnit.setParallelIndex(2)
                    elif parallelIndex == 2:
                        alignedUnit.setParallelIndex(0)
                    alignedUnit.setIndex(alignedIndex)
                    alignmentUnit.addAlignedUnit(alignedUnit)
                    alignedUnit.addAlignedUnit(alignmentUnit)
        return alignmentUnitdef __eq__(self, obj):
    if obj is None:
        return False
    fsobj = obj
    if self.getName().lower() != fsobj.getName().lower():
        return False
    count = self.countAttributes()
    if count != fsobj.countAttributes():
        return False
    for i in range(count):
        if self.getAttribute(i) != fsobj.getAttribute(i):
            return False
    return True

def getDOMElement(self):
    xmlProperties = SSFNode.getXMLProperties()
    domElement = DOMElement(xmlProperties.getProperties().getPropertyValue('fsTag'))
    acount = self.countAttributes()
    for i in range(acount):
        fa = self.getAttribute(i)
        fv = fa.getAltValue(0)
        aname = fa.getName()
        if fv is not None:
            value = str(fv.getValue())
            attribDOM = DOMAttribute(QName(aname), value)
            domElement.add(attribDOM)
    return domElement

def getXML(self):
    xmlString = ''
    element = self.getDOMElement()
    xmlString = element.asXML()
    return '\n' + xmlString + '\n'

def readXML(self, domElement):
    domAttribs = domElement.getAttributes()
    acount = domAttribs.getLength()
    for i in range(acount):
        node = domAttribs.item(i)
        aname = node.getNodeName()
        value = node.getNodeValue()
        if aname is not None:
            if value is not None:
                self.addAttribute(aname, value)
            else:
                self.addAttribute(aname, '')
    count = FeatureStructuresImpl.getFSProperties().countMandatoryAttributes()
    for i in range(count):
        mname = FeatureStructuresImpl.getFSProperties().getMandatoryAttribute(i)
        fa = self.searchAttribute(mname, True)
        if fa is not None:
            self.remove(fa)
            self.insertAttribute(fa, i)

def printXML(self, ps):
    ps.println(self.getXML())

class ReadRecurseArgs:

    def __init__(self, s, l, p, fa):
        self.str = s
        self.level = l
        self.position = p
        self.fa = fa