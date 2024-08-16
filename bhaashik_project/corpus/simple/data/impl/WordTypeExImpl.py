import os
import subprocess
from typing import List
from bhaashik.corpus.simple.data import WordTypeEx
from bhaashik.corpus.simple.data.impl import WordTypeImpl
from bhaashik.corpus.parallel import APCData
from bhaashik import GlobalProperties

class WordTypeExImpl(WordTypeImpl, WordTypeEx):
    def __init__(self, initial_capacity=0):
        super().__init__()
        self.hypernyms = []
        self.synonyms = []
        self.phonetic_match = -1

    def countHypernyms(self):
        return len(self.hypernyms)

    def getHypernym(self, num):
        return self.hypernyms[num]

    def addHypernym(self, h):
        self.hypernyms.append(h)
        return len(self.hypernyms)

    def removeHypernym(self, num):
        return self.hypernyms.pop(num)

    def countSynonyms(self):
        return len(self.synonyms)

    def getSynonyms(self, num):
        return self.synonyms[num]

    def addSynonyms(self, h):
        self.synonyms.append(h)
        return len(self.synonyms)

    def removeSynonyms(self, num):
        return self.synonyms.pop(num)

    def getPhoneticMatch(self):
        return self.phonetic_match

    def setPhoneticMatch(self, pm):
        self.phonetic_match = pm

    def populate(self, apcdata, tag, type):
        s = None
        file = file2 = None
        cmd = None
        notag = False
        ps = None

        if tag:
            notag = False
            if type == "hyp":
                file = "/home/samar/bhaash/java/output/hyp_tmp.txt"
                cmd = f"/usr/local/wordnet1.7/bin/linux/wn {self.word} -hype{tag.lower()}"
            else:
                if tag == "Adv":
                    tag = "a"
                file = "/home/samar/bhaash/java/output/synonyms_tmp.txt"
                cmd = f"/usr/local/wordnet1.7/bin/linux/wn {self.word} -syns{tag.lower()}"

            try:
                with open(file, "w") as ps:
                    process = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
                    for line in process.stdout:
                        ps.write(line.decode())
            except Exception as e:
                print(e)

        else:
            notag = True

            for twice in range(2):
                try:
                    if twice == 0:
                        if type == "hyp":
                            cmd = f"/usr/local/wordnet1.7/bin/linux/wn {self.word} -hypen"
                            file = "/home/samar/bhaash/java/output/hyn_tmp.txt"
                        else:
                            cmd = f"/usr/local/wordnet1.7/bin/linux/wn {self.word} -synsn"
                            file = "/home/samar/bhaash/java/output/synonyms_tmp.txt"

                        ps = open(file, "w")
                    else:
                        if type == "hyp":
                            cmd = f"/usr/local/wordnet1.7/bin/linux/wn {self.word} -hypev"
                            file2 = "/home/samar/bhaash/java/output/hyn_tmp2.txt"
                        else:
                            cmd = f"/usr/local/wordnet1.7/bin/linux/wn {self.word} -synsv"
                            file2 = "/home/samar/bhaash/java/output/synonyms_tmp2.txt"
                        ps = open(file2, "w")

                    process = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
                    for line in process.stdout:
                        ps.write(line.decode())
                except Exception as e:
                    print(e)
                finally:
                    ps.close()

        contains = level = 0

        if notag:
            try:
                with open(file, "r", encoding="UTF-8") as lnReader:
                    for line in lnReader:
                        if "Sense " in line:
                            line = next(lnReader)
                            splitstr1 = [s.strip() for s in line.split(", ")]
                            level = 0

                            if len(splitstr1) > 1:
                                for i in range(len(splitstr1)):
                                    splitstr1[i] = splitstr1[i].lower()
                                    contains = apcdata.getSrcWTTable().findWT(splitstr1[i])

                                    if contains == -1:
                                        wdtyex = WordTypeExImpl()
                                        wdtyex.setWord(splitstr1[i])
                                        wdtyex.setIsCorpusWord(False)
                                        index = apcdata.getSrcWTTable().addWT(wdtyex) - 1
                                        if type == "hyp":
                                            self.addHypernym(index)
                                        else:
                                            self.addSynonyms(index)
                                    else:
                                        wrdtypex = apcdata.getSrcWTTable().getWT(contains)
                                        if type == "hyp":
                                            self.addHypernym(contains)
                                        else:
                                            self.addSynonyms(contains)
                            else:
                                line = line.lower()
                                contains = apcdata.getSrcWTTable().findWT(line)

                                if contains == -1:
                                    wdtyex = WordTypeExImpl()
                                    wdtyex.setWord(line)
                                    wdtyex.setIsCorpusWord(False)
                                    index = apcdata.getSrcWTTable().addWT(wdtyex) - 1
                                    if type == "hyp":
                                        self.addHypernym(index)
                                    else:
                                        self.addSynonyms(index)
                                else:
                                    wrdtypex = apcdata.getSrcWTTable().getWT(contains)
                                    if type == "hyp":
                                        self.addHypernym(contains)
                                    else:
                                        self.addSynonyms(contains)

                        if "=>" in line and level < 3:
                            splitstr2 = line.split("=>")
                            tempstr = [s.strip() for s in splitstr2[1].split(", ")]
                            level += 1

                            if len(tempstr) > 1:
                                for i in range(len(tempstr)):
                                    tempstr[i] = tempstr[i].lower()
                                    contains = apcdata.getSrcWTTable().findWT(tempstr[i])

                                    if contains == -1:
                                        wdtyex = WordTypeExImpl()
                                        wdtyex.setWord(tempstr[i])
                                        wdtyex.setIsCorpusWord(False)
                                        index = apcdata.getSrcWTTable().addWT(wdtyex) - 1
                                        if type == "hyp":
                                            self.addHypernym(index)
                                        else:
                                            self.addSynonyms(index)
                                    else:
                                        wrdtypex = apcdata.getSrcWTTable().getWT(contains)
                                        if type == "hyp":
                                            self.addHypernym(contains)
                                        else:
                                            self.addSynonyms(contains)
                            else:
                                splitstr2[1] = splitstr2[1].lower()
                                contains = apcdata.getSrcWTTable().findWT(splitstr2[1])

                                if contains == -1:
                                    wdtyex = WordTypeExImpl()
                                    wdtyex.setWord
