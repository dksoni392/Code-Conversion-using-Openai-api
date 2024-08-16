import java.util.Comparator

class ByWTFreq(Comparator):

    def compare(self, one, two):
        return int(one.getFreq() - two.getFreq())