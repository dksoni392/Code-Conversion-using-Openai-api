from abc import ABC, abstractmethod
import os

class CorpusCollection(ABC):

    @abstractmethod
    def print(self, ps):
        pass

    @abstractmethod
    def getCopy(self):
        pass

class SSFCorpusCollection(CorpusCollection, Alignable):

    def __init__(self):
        super().__init__()
        self.properties = None
        self.corporaPaths = None
        self.alignmentUnit = None
        self.containerAlignable = None

    def __init__(self, pf, cf, charset):
        super().__init__()
        self.properties = KeyValueProperties(pf, charset)
        self.corporaPaths = KeyValueProperties(cf, charset)
        self.alignmentUnit = None
        self.containerAlignable = None

    def getCorporaPaths(self):
        return self.properties

    def setCorporaPaths(self, p):
        self.properties = p

    def getProperties(self):
        return self.properties

    def setProperties(self, p):
        self.properties = p

    def clear(self):
        self.properties.clear()
        self.corporaPaths.clear()

    def print(self, ps):
        pass

    def getCopy(self):
        return None

    def getAlignmentUnit():
        return self.alignmentUnit

    def setAlignmentUnit(self, alignmentUnit):
        self.alignmentUnit = alignmentUnit

    def getAlignedObject(self, alignmentKey):
        raise NotImplementedError('Not supported yet.')

    def getAlignedObjects(self):
        raise NotImplementedError('Not supported yet.')

    def getFirstAlignedObject(self):
        raise NotImplementedError('Not supported yet.')

    def getAlignedObject(self, i):
        raise NotImplementedError('Not supported yet.')

    def getLastAlignedObject(self):
        raise NotImplementedError('Not supported yet.')

    def getContainerAlignable(self):
        return self.containerAlignable

    def setContainerAlignable(self, containerAlignable):
        self.containerAlignable = containerAlignable

    def insertContainedAlignableObject(self, containedAlignableObject, atIndex):
        raise NotImplementedError('Not supported yet.')

    def removeContainedAlignableObject(self, atIndex):
        raise NotImplementedError('Not supported yet.')

    def interchangeContainedAlignableObjects(self, containedAlignableObject, atIndex):
        raise NotImplementedError('Not supported yet.')

class SSFCorpusCollectionImpl(SSFCorpusCollection):

    def __init__(self):
        super().__init__()

    def __init__(self, pf, cf, charset):
        super().__init__(pf, cf, charset)

    def main(self, args):
        pass