import java.io
import java.util
from bhaashik.GlobalProperties import GlobalProperties
from bhaashik.corpus.simple.data import WordType

class WordTypeImpl(WordType):

    def __init__(self):
        super().__init__()
        self.word = None
        self.tag = None
        self.eqword = -1
        self.sbt_words_all = []
        self.wordsig = 0
        self.freq = 0
        self.cutoffreq = 0
        self.wtflags = BitSet(5)

    def getWord(self):
        return self.word

    def setWord(self, w):
        self.word = w
        try:
            i = int(self.word)
            self.setIsNumeric(True)
        except ValueError:
            pass

    def getTag(self):
        return self.tag

    def setTag(self, t):
        self.tag = t

    def getEqWord(self):
        return self.eqword

    def setEqWord(self, w):
        self.eqword = w

    def countAllSbtWords(self):
        return len(self.sbt_words_all)

    def getWordSbtAll(self, num):
        return self.sbt_words_all[num]

    def setWordSbtAll(self, wd):
        if wd not in self.sbt_words_all:
            self.sbt_words_all.append(wd)

    def getWordSig(self):
        return self.wordsig

    def setWordSig(self, s):
        self.wordsig = s

    def getFreq(self):
        return self.freq

    def setFreq(self, f):
        self.freq = f

    def getCutOffFreq(self):
        return self.cutoffreq

    def setCutOffFreq(self, f):
        self.cutoffreq = f

    def getIsCorpusWord(self):
        return self.wtflags.get(CORPUS_WORD)

    def setIsCorpusWord(self, flag):
        if flag:
            self.wtflags.set(CORPUS_WORD)

    def getIsSubstituteWord(self):
        return self.wtflags.get(SUBSTITUTE_WORD)

    def setIsSubstituteWord(self, flag):
        if flag:
            self.wtflags.set(SUBSTITUTE_WORD)

    def getIsHypernymWord(self):
        return self.wtflags.get(HYPERNYM_WORD)

    def setIsHypernymWord(self, flag):
        if flag:
            self.wtflags.set(HYPERNYM_WORD)

    def getIsNumeric(self):
        return self.wtflags.get(NUMERIC)

    def setIsNumeric(self, flag):
        if flag:
            self.wtflags.set(NUMERIC)

    def getIsPhonetic(self):
        return self.wtflags.get(PHONETIC)

    def setIsPhonetic(self, flag):
        if flag:
            self.wtflags.set(PHONETIC)

    def print(self, ps):
        ps.print(GlobalProperties.getIntlString('Word:_'))
        ps.println(self.getWord())
        ps.print(GlobalProperties.getIntlString('Equivalent_Word_Index:_'))
        ps.println(self.getEqWord())
        ps.print(GlobalProperties.getIntlString('Word_Signature:_'))
        ps.println(self.getWordSig())
        ps.print(GlobalProperties.getIntlString('Word_Frequency:_'))
        ps.println(self.getFreq())
        ps.print(GlobalProperties.getIntlString('Is_Corpus_Word:_'))
        ps.println(self.getIsCorpusWord())
        ps.print(GlobalProperties.getIntlString('Is_Substitute_Word:_'))
        ps.println(self.getIsSubstituteWord())
        return 0