import java.util.Comparator

class ByWTCutOffFreq(Comparator):

    def compare(self, one, two):
        return int(two.getCutOffFreq()) - int(one.getCutOffFreq())