'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'

class Corpus:

    def __init__(self):
        self.path = ''
        self.charset = ''
        self.tokenCount = 0

    def __init__(self, charset):
        self.path = ''
        self.charset = charset
        self.tokenCount = 0

    def __init__(self, path, charset):
        self.path = path
        self.charset = charset
        self.tokenCount = 0

    def getPath(self):
        return self.path

    def setPath(self, p):
        self.path = p

    def getCharset(self):
        return self.charset

    def setCharset(self, cs):
        self.charset = cs

    def countSentences(self):
        return -1

    def countTokens(self, recalculate):
        return -1

    def getSentence(self, num):
        return None

    def addSentence(self, s):
        return -1

    def insertSentence(self, index, s):
        return -1

    def removeSentence(self, num):
        return None

    def print(self, ps):
        pass

    def getCopy(self):
        return None

    @staticmethod
    def main(args):
        pass