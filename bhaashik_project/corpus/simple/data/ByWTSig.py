import java.util.Comparator

class ByWTSig(Comparator):

    def compare(self, one, two):
        return one.getWordSig() - two.getWordSig()