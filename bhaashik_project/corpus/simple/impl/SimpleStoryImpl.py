'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import bhaashik.common.types
import bhaashik.corpus.Story
import bhaashik.corpus.simple.SimpleStory
import bhaashik.corpus.ssf.SSFProperties
import bhaashik.corpus.ssf.tree.SSFNode
import bhaashik.util.UtilityFunctions
import java.io
import java.util

class SimpleStoryImpl(Story, SimpleStory):

    def __init__(self):
        super().__init__()

    @staticmethod
    def getCorpusType(strings):
        ctype = None
        ssfp = SSFNode.getSSFProperties()
        storyStart = ssfp.getProperties().getPropertyValue('storyStart')
        sentenceStart = ssfp.getProperties().getPropertyValue('sentenceStart')
        storyEnd = ssfp.getProperties().getPropertyValue('storyEnd')
        sentenceEnd = ssfp.getProperties().getPropertyValue('sentenceEnd')
        tcTextStart = ssfp.getProperties().getPropertyValue('tcTextStart')
        tcPhraseStart = ssfp.getProperties().getPropertyValue('tcPhraseStart')
        tcTextEnd = ssfp.getProperties().getPropertyValue('tcTextEnd')
        tcPhraseEnd = ssfp.getProperties().getPropertyValue('tcPhraseEnd')
        bracketFormStart = ssfp.getProperties().getPropertyValueForPrint('bracketFormStart')
        bracketFormEnd = ssfp.getProperties().getPropertyValueForPrint('bracketFormEnd')
        wordTagSeparator = ssfp.getProperties().getPropertyValueForPrint('wordTagSeparator')
        storyStarted = False
        sentenceStarted = False
        tcTextStarted = False
        tcPhraseStarted = False
        postagged = True
        isXML = False
        for i in range(len(strings)):
            line = strings[i]
            line = line.strip()
            if line != '':
                if '\t' in line:
                    parts = line.split('[\t]')
                    if len(parts) == 3 and (parts[2].startswith('B-') or parts[2].startswith('I-')):
                        return bhaashik.common.types.CorpusType.BI_FORMAT
                    if len(parts) == 5 and '|' in parts[3] and ('|' in parts[4]) and ('-' in parts[1]):
                        iparts = parts[1].split('-')
                        if UtilityFunctions.isInteger(iparts[0]) and UtilityFunctions.isInteger(iparts[1]):
                            return bhaashik.common.types.CorpusType.HINDENCORP_FORMAT
                        return bhaashik.common.types.CorpusType.BI_FORMAT
                if line.startswith('<?xml version='):
                    isXML = True
                elif not isXML and line.startswith(storyStart):
                    storyStarted = True
                elif not isXML and line.startswith(sentenceStart):
                    sentenceStarted = True
                elif not isXML and storyStarted and line.startswith(storyEnd):
                    return bhaashik.common.types.CorpusType.SSF_FORMAT
                elif not isXML and sentenceStarted and line.startswith(sentenceEnd):
                    return bhaashik.common.types.CorpusType.SSF_FORMAT
                elif isXML and line.startswith(tcTextStart):
                    tcTextStarted = True
                elif isXML and line.startswith(tcPhraseStart):
                    tcPhraseStarted = True
                elif tcTextStarted and line.startswith(tcTextEnd):
                    return bhaashik.common.types.CorpusType.TYPECRAFT_FORMAT
                elif tcPhraseStarted and line.startswith(tcPhraseEnd):
                    return bhaashik.common.types.CorpusType.TYPECRAFT_FORMAT
                elif storyStarted == False and sentenceStarted == False and line.startswith(bracketFormStart) and line.__contains__(bracketFormEnd):
                    return bhaashik.common.types.CorpusType.CHUNKED
                elif not isXML and postagged == True and (storyStarted == False) and (sentenceStarted == False):
                    words = line.split('[ ]')
                    notag = 0
                    for j in range(len(words)):
                        wt = words[j].split('[' + wordTagSeparator + ']')
                        if len(wt) != 2:
                            notag += 1
                    if notag > 0 and len(words) - notag <= notag:
                        postagged = False
        if isXML:
            return bhaashik.common.types.CorpusType.XML_FORMAT
        if storyStarted == False and sentenceStarted == False:
            if postagged:
                return bhaashik.common.types.CorpusType.POS_TAGGED
            return bhaashik.common.types.CorpusType.RAW
        return ctype

    @staticmethod
    def getCorpusType(file, cs):
        ctype = None
        line = ''
        lines = []
        try:
            lnReader = open(file, 'r', encoding=cs)
            for line in lnReader:
                line = line.strip()
                if line != '':
                    lines.append(line)
            ctype = SimpleStoryImpl.getCorpusType(lines)
            lnReader.close()
        except FileNotFoundError as e:
            e.printStackTrace()
        except OSError as e:
            e.printStackTrace()
        return ctype

    @staticmethod
    def getCorpusType(path, cs):
        if path == None or path == '':
            return None
        if cs == None or cs == '':
            return SimpleStoryImpl.getCorpusType(file, 'UTF-8')
        return SimpleStoryImpl.getCorpusType(file, cs)

    @staticmethod
    def main(args):
        pass