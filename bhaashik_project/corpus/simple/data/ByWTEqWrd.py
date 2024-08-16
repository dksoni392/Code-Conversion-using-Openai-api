import java.util.Comparator

class ByWTEqWrd(Comparator):

    def compare(self, one, two):
        return one.getEqWord() - two.getEqWord()