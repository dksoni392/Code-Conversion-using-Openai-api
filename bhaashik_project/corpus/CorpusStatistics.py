'\nCorpusStatistics.py\n\nCreated on January 21, 2008, 9:11 PM\n\nTo change this template, choose Tools | Template Manager\nand open the template in the editor.\n'
import sys
from bhaashik import GlobalProperties

class CorpusStatistics:

    def __init__(self):
        self.paragraphs = 0
        self.sentences = 0
        self.words = 0
        self.characters = 0

    def getParagraphs(self):
        return self.paragraphs

    def setParagraphs(self, paragraphs):
        self.paragraphs = paragraphs

    def getSentences(self):
        return self.sentences

    def setSentences(self, sentences):
        self.sentences = sentences

    def getWords(self):
        return self.words

    def setWords(self, words):
        self.words = words

    def getCharacters(self):
        return self.characters

    def setCharacters(self, characters):
        self.characters = characters

    def print(self, ps):
        ps.write(GlobalProperties.getIntlString('Paragraphs:_') + str(self.paragraphs))
        ps.write(GlobalProperties.getIntlString('Sentences:_') + str(self.sentences))
        ps.write(GlobalProperties.getIntlString('Words:_') + str(self.words))
        ps.write(GlobalProperties.getIntlString('Characters:_') + str(self.characters))