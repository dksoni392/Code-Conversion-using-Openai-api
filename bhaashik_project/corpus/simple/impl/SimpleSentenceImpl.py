class SimpleSentenceImpl(Sentence, SimpleSentence, SentenceFeatures):

    def __init__(self, size):
        self.words = [0] * size
        self.sbt_words = [0] * size
        self.sbt_words[0] = -1
        self.sentence_length = 0
        self.signature = 0
        self.weighted_length = 0

    def countWords(self):
        return len(self.words)

    def getWord(self, num):
        return self.words[num]

    def setWord(self, ind, wd):
        self.words[ind] = wd
        return self.words[ind]

    def insertWord(self, ind, wd):
        vec = self.words.copy()
        vec.insert(ind, wd)
        self.words = vec.copy()

    def countSbtWords(self):
        return len(self.sbt_words)

    def getWordSbt(self, num):
        return self.sbt_words[num]

    def setWordSbt(self, ind, wd):
        self.sbt_words[ind] = wd
        return self.sbt_words[ind]

    def calculateSentenceLength(self, wttbl):
        str = self.getSentenceString(wttbl)
        self.sentence_length = len(str)

    def calculateSignature(self, wttbl):
        str = self.getSentenceString(wttbl)
        self.sentence_length = len(str)
        for i in range(self.sentence_length):
            self.signature += ord(str[i])

    def getSentenceLength(self):
        return self.sentence_length

    def setSentenceLength(self, senlen):
        self.sentence_length = senlen

    def setWeightedLength(self, type):
        self.setWeightedLength(None, type)

    def setWeightedLength(self, apcpro, type):
        if type == 's':
            self.weighted_length = int((apcpro.getSignatureWeight() * (self.signature / apcpro.getSMaxSignature()) + apcpro.getCharcntWeight() * (self.sentence_length / apcpro.getSMaxCharcnt()) + apcpro.getWrdcntWeight() * (len(self.words) / apcpro.getSMaxWrdcnt())) * apcpro.getSMaxWrdcnt() * apcpro.getSMaxCharcnt())
        if type == 't':
            self.weighted_length = int((apcpro.getSignatureWeight() * (self.signature / apcpro.getTMaxSignature()) + apcpro.getCharcntWeight() * (self.sentence_length / apcpro.getTMaxCharcnt()) + apcpro.getWrdcntWeight() * (len(self.words) / apcpro.getTMaxWrdcnt())) * apcpro.getTMaxWrdcnt() * apcpro.getSMaxCharcnt())

    def getWeightedLength(self):
        return self.weighted_length

    def getWords(self, wttbl):
        c = self.countWords()
        ret = []
        for i in range(c):
            ind = self.getWord(i)
            row = wttbl.getRow(ind)
            wrd = row[0]
            ret.append(wrd)
        return ret

    def getSentenceString(self, wttbl):
        ret = ''
        c = self.countWords()
        for i in range(c):
            ind = self.getWord(i)
            row = wttbl.getRow(ind)
            wrd = row[0]
            ret += wrd + ' '
        ret = ret.strip()
        return ret

    def getWordString(self, i, wttbl):
        wrd = wttbl.getValueAt(i, 0)
        return wrd

    def getCommonWords(self, sen, apcdata):
        countdef getCommonSynonyms(self, sen, apcdata):
    count = 0
    common = 0.0
    engind = []
    sbsind = []
    engindarr = []
    sbsindarr = []
    wrdtypex = None
    wrdtyp = None
    for i in range(self.countWords()):
        index = self.getWord(i)
        wrdtypex = apcdata.getSrcWTTable().getWT(index)
        for j in range(wrdtypex.countSynonyms()):
            engind.append(wrdtypex.getSynonyms(j))
    for i in range(sen.countWords()):
        index = sen.getWord(i)
        wrdtyp = apcdata.getTgtWTTable().getWT(index)
        engindex = wrdtyp.getEqWord()
        if engindex != -1:
            wrdtypex = apcdata.getSrcWTTable().getWT(engindex)
            for j in range(wrdtypex.countSynonyms()):
                sbsind.append(wrdtypex.getSynonyms(j))
    engindarr = engind.copy()
    sbsindarr = sbsind.copy()
    engindarr.sort()
    sbsindarr.sort()
    count = 0
    common = 0.0
    for i in range(len(engindarr)):
        if int(engindarr[i]) == -1:
            continue
        j = count
        while j < len(sbsindarr):
            if int(sbsindarr[j]) == -1:
                count += 1
                continue
            if int(engindarr[i]) < int(sbsindarr[j]):
                break
            if int(engindarr[i]) == int(sbsindarr[j]):
                count += 1
                common += 1
                break
            count += 1
    common = float(common) / float(self.countWords())
    return common

def removeVowels(self, word, lang):
    trim = ''
    if lang == 'eng':
        buf_src = list(word)
        for j in range(len(buf_src)):
            if buf_src[j] == 'a' or buf_src[j] == 'e' or buf_src[j] == 'i' or (buf_src[j] == 'o') or (buf_src[j] == 'u'):
                pass
            else:
                trim += buf_src[j]
    else:
        buf_tgt = list(word)
        for k in range(len(buf_tgt)):
            if buf_tgt[k] == 'a' or buf_tgt[k] == 'A' or buf_tgt[k] == 'e' or (buf_tgt[k] == 'E') or (buf_tgt[k] == 'i') or (buf_tgt[k] == 'I') or (buf_tgt[k] == 'o') or (buf_tgt[k] == 'O') or (buf_tgt[k] == 'u') or (buf_tgt[k] == 'U'):
                pass
            elif buf_tgt[k] == 'w' or buf_tgt[k] == 'W':
                if buf_tgt[k] == 'W':
                    trim += 'th'
                else:
                    trim += 't'
            elif buf_tgt[k] == 'x' or buf_tgt[k] == 'X':
                if buf_tgt[k] == 'X':
                    trim += 'dh'
                else:
                    trim += 'd'
            elif buf_tgt[k] == 'c' or buf_tgt[k] == 'C':
                if buf_tgt[k] == 'C':
                    trim += 'chh'
                else:
                    trim += 'ch'
            elif buf_tgt[k] == 'M':
                trim += 'n'
            elif buf_tgt[k] == 'z':
                trim += 'n'
            elif buf_tgt[k] == 'q':
                trim += 'r'
            elif buf_tgt[k] == 'R':
                trim += 'sh'
            elif buf_tgt[k].isupper() == true:
                trim += buf_tgt[k].lower() + 'h'
            else:
                trim += buf_tgt[k]
    return trimdef print(self, ps):
    ps.print(GlobalProperties.getIntlString("Index's:____"))
    for i in range(self.countWords()):
        ps.print(self.words[i] + ' ')
    ps.println(GlobalProperties.getIntlString('\nTotal_Word_Count:_') + str(self.countWords()))
    ps.println(GlobalProperties.getIntlString('Sentence_Length:_') + str(self.sentence_length))
    ps.println(GlobalProperties.getIntlString('Signature:_') + str(self.signature))

def printCounts(self, wtTable, ps):
    ps.println('Sentence: ' + self.getSentenceString(wtTable))
    ps.println('\t Character count: ' + str(self.getSentenceLength()))
    ps.println('\t Word count: ' + str(self.countWords()))
    ps.println('\t Signature: ' + str(self.getSignature()))
    ps.println('\t Weighted Sentence Length: ' + str(self.getWeightedLength()))