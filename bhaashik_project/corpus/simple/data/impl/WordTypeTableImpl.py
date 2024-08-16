import java.io
import java.util
from bhaashik.data.attrib.impl import DefaultTypeTable
from bhaashik.corpus.simple.data import WordTypeTable

class WordTypeTableImpl(DefaultTypeTable, WordTypeTable):

    def __init__(self):
        super().__init__()
        self.wordtypes = []
        self.word_index = {}

    def __init__(self, initial_capacity):
        super().__init__(initial_capacity)
        self.wordtypes = []
        self.word_index = {}

    def countWTs(self, wtflags):
        if wtflags == WordType.ANY_WORD:
            return len(self.wordtypes)
        ret = 0
        wt = None
        for i in range(len(self.wordtypes)):
            wt = self.wordtypes[i]
            if wtflags == WordType.CORPUS_WORD:
                if wt.getIsCorpusWord():
                    ret += 1
            elif wtflags == WordType.SUBSTITUTE_WORD:
                if wt.getIsSubstituteWord():
                    ret += 1
            elif wtflags == WordType.HYPERNYM_WORD and wt.getIsHypernymWord():
                ret += 1
        return ret

    def countTokens(self, wtflags):
        ret = 0
        wt = None
        for i in range(len(self.wordtypes)):
            wt = self.wordtypes[i]
            if wtflags == WordType.ANY_WORD:
                ret += wt.getFreq()
            elif wtflags == WordType.CORPUS_WORD:
                if wt.getIsCorpusWord():
                    ret += wt.getFreq()
            elif wtflags == WordType.CUT_OFF_WORD:
                if wt.getIsCorpusWord():
                    ret += wt.getCutOffFreq()
            elif wtflags == WordType.SUBSTITUTE_WORD:
                if wt.getIsSubstituteWord():
                    ret += wt.getFreq()
            elif wtflags == WordType.HYPERNYM_WORD and wt.getIsHypernymWord():
                ret += wt.getFreq()
        return ret

    def getWT(self, num):
        return self.wordtypes[num]

    def getWT(self, swrd):
        x = None
        i = self.findWT(swrd)
        if i != -1:
            return self.getWT(i)
        else:
            return x

    def addWT(self, wt):
        key = wt.getWord()
        self.wordtypes.append(wt)
        i = self.wordtypes.index(wt)
        self.word_index[key] = i
        return len(self.wordtypes)

    def findWT(self, str):
        if str is None:
            return -1
        if str in self.word_index:
            return self.word_index[str]
        else:
            return -1

    def containsWT(self, str):
        return str in self.wordtypes

    def removeWT(self, num):
        return self.wordtypes.pop(num)

    def removeWT(self, swrd):
        i = self.findWT(swrd)
        return self.wordtypes.pop(i)

    def sort(self, order):
        sorted = self.wordtypes[:]
        if order == WordType.SORT_BY_SRC_WRD:
            sorted.sort(key=lambda x: x.getSrcWrd())
        elif order == WordType.SORT_BY_TGT_MNG:
            sorted.sort(key=lambda x: x.getTgtMng())
        elif order == WordType.SORT_BY_TAG:
            sorted.sort(key=lambda x: x.getTag())
        elif order == WordType.SORT_BY_EQWRD:
            sorted.sort(key=lambda x: x.getEqWrd())
        elif order == WordType.SORT_BY_FREQ:
            sorted.sort(key=lambda x: x.getFreq())
        elif order == WordType.SORT_BY_REVERSE_FREQ:
            sorted.sort(key=lambda x: x.getFreq(), reverse=True)
        elif order == WordType.SORT_BY_SIG:
            sorted.sort(key=lambda x: x.getSig())
        elif order == WordType.SORT_BY_CUT_OFF_FREQ:
            sorted.sort(key=lambda x: x.getCutOffFreq())
        else:
            sorted.sort(key=lambda x: x.getSrcWrd())
        return sorted

    def getShallowCopy(self):
        retwtbl = WordTypeTableImpl()
        retwtbl.wordtypes = self.wordtypes[:]
        retwtbl.word_index = self.word_index.copy()
        return retwtbl

    def print(self, ps):
        count = self.countWTs(WordType.ANY_WORD)
        for i in range(count):
            ps.println(i)
            wordtype = self.getWT(i)
            wordtype.print(ps)
            ps.print('\n\n\n')
        return self.countWTs(WordType.ANY_WORD)