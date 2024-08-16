'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import bhaashik.GlobalProperties
from bhaashik.corpus.simple.data import WordTypeTable

class Sentence:
    bundle = GlobalProperties.getResourceBundle()

    def __init__(self):
        super().__init__()

    def countWords(self):
        return -1

    def getWord(self, num):
        return -1

    def setWord(self, ind, wd):
        return -1

    def insertWord(self, ind, wd):
        pass

    def getWords(self, wttbl):
        return None

    def getSentenceString(self, wttbl):
        return None

    def readString(self, s):
        pass

    def makeString(self):
        return None

    def print(self, ps):
        pass

    def getCopy(self):
        return None

    @staticmethod
    def main(args):
        pass