import bhaashik.GlobalProperties
from bhaashik.corpus.ssf.features import FeatureStructures
from bhaashik.xml.dom import BhaashikDOMElement
from bhaashik.corpus.parallel import Alignable
from bhaashik.corpus.ssf.query import QueryValue

class SSFLexItem(SSFNode, MutableTreeNode, Alignable, Serializable, QueryValue, BhaashikDOMElement):

    def __init__(self):
        super().__init__()

    def __init__(self, userObject):
        super().__init__(userObject)

    def __init__(self, userObject, allowsChildren):
        super().__init__(userObject, allowsChildren)

    def __init__(self, id, lexdata, name, stringFS):
        super().__init__(id, lexdata, name, stringFS)

    def __init__(self, id, lexdata, name, fs):
        super().__init__(id, lexdata, name, fs)

    def __init__(self, id, lexdata, name, stringFS, userObject):
        super().__init__(id, lexdata, name, stringFS, userObject)

    def __init__(self, id, lexdata, name, fs, userObject):
        super().__init__(id, lexdata, name, fs, userObject)

    def isLeaf(self):
        return True

    def getCopy(self):
        str = self.makeString()
        ssfNode = SSFLexItem()
        ssfNode.readString(str)
        ssfNode.flags = self.flags
        return ssfNode

    @staticmethod
    def main(args):
        node = SSFLexItem()
        print(GlobalProperties.getIntlString('Testing_SSFLexicalItem...'))