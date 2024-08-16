from bhaashik.corpus.parallel import Alignable
from bhaashik.properties import KeyValueProperties

class SSFCorpusCollection(Alignable):

    def getCorporaPaths(self):
        return self.corporaPaths

    def setCorporaPaths(self, p):
        self.corporaPaths = p

    def getProperties(self):
        return self.properties

    def setProperties(self, p):
        self.properties = p