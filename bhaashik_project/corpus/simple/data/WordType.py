class WordType:
    ANY_WORD = 0
    CORPUS_WORD = 1
    SUBSTITUTE_WORD = 2
    HYPERNYM_WORD = 3
    CUT_OFF_WORD = 4
    NUMERIC = 5
    PHONETIC = 6
    SORT_BY_SRC_WRD = 0
    SORT_BY_TGT_MNG = 1
    SORT_BY_TAG = 2
    SORT_BY_EQWRD = 3
    SORT_BY_FREQ = 4
    SORT_BY_REVERSE_FREQ = 5
    SORT_BY_SIG = 6
    SORT_BY_CUT_OFF_FREQ = 7

    def getWord(self):
        pass

    def setWord(self, w):
        pass

    def getTag(self):
        pass

    def setTag(self, t):
        pass

    def getEqWord(self):
        pass

    def setEqWord(self, w):
        pass

    def countAllSbtWords(self):
        pass

    def getWordSbtAll(self, num):
        pass

    def setWordSbtAll(self, wd):
        pass

    def getWordSig(self):
        pass

    def setWordSig(self, s):
        pass

    def getFreq(self):
        pass

    def setFreq(self, f):
        pass

    def getCutOffFreq(self):
        pass

    def setCutOffFreq(self, f):
        pass

    def getIsCorpusWord(self):
        pass

    def setIsCorpusWord(self, flag):
        pass

    def getIsSubstituteWord(self):
        pass

    def setIsSubstituteWord(self, flag):
        pass

    def getIsHypernymWord(self):
        pass

    def setIsHypernymWord(self, flag):
        pass

    def getIsNumeric(self):
        pass

    def setIsNumeric(self, flag):
        pass

    def getIsPhonetic(self):
        pass

    def setIsPhonetic(self, flag):
        pass

    def print(self, ps):
        pass