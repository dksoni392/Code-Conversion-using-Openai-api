import java.util.Comparator

class ByWTSrcWrd(Comparator):

    def compare(self, one, two):
        return one.getWord().compareTo(two.getWord())