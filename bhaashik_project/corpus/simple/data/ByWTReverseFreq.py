import java.util

class ByWTReverseFreq(Comparator):

    def compare(self, one, two):
        return int(two.getFreq()) - int(one.getFreq())