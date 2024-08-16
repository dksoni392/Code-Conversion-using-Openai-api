'\nCreated on Aug 14, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import bhaashik.GlobalProperties
import bhaashik.properties.KeyValueProperties

class SSFProperties:

    def __init__(self):
        self.properties = KeyValueProperties()

    def __init__(self, pf, charset):
        self.properties = KeyValueProperties(pf, charset)

    def getProperties(self):
        return self.properties

    def setProperties(self, p):
        self.properties = p

    def readProperties(self, f, charset):
        return self.properties.read(f, charset)

    def read(self, pf, charset):
        self.readProperties(pf, charset)
        return 0

    def print(self, ps):
        ps.println(GlobalProperties.getIntlString('#SSF_properties'))
        enm = self.properties.getPropertyKeys()
        while enm.hasNext():
            key = enm.next()
            ps.println(key + '\t' + self.properties.getPropertyValue(key))

    def clone(self):
        obj = super().clone()
        obj.properties = self.properties.clone()
        return obj

    def clear(self):
        self.properties.clear()

    @staticmethod
    def main(args):
        pass