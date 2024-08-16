import bhaashik.GlobalProperties
from bhaashik.corpus.ssf.impl import SimpleStoryImpl
from bhaashik.corpus.ssf.features.impl import FSProperties
from bhaashik.corpus.ssf.features.impl import FeatureStructuresImpl
from bhaashik.corpus.ssf.tree import SSFLexItem
from bhaashik.corpus.ssf.tree import SSFNode
from bhaashik.corpus.ssf.tree import SSFPhrase
from bhaashik.common.types import CorpusType
from bhaashik.corpus.ssf import SSFProperties
from bhaashik.corpus.ssf import SSFSentence
from bhaashik.corpus.ssf import SSFStory
from bhaashik.corpus.ssf import SSFText
from bhaashik.tree import BhaashikMutableTreeNode
from bhaashik.xml.dom import BhaashikDOMElement
from java.io import *
from java.util import *
from java.util.logging import Level, Logger
import bhaashik.properties.KeyValueProperties as KeyValueProperties

class SSFStoryImpl(SSFTextImpl, Serializable, SSFStory, BhaashikDOMElement):

    def __init__(self):
        super(SSFStoryImpl, self).__init__()

    def getSSFText(filePath, startSentenceNum, count):
        s = SSFStoryImpl()
        s.readFile(filePath)
        return s.getSSFText(startSentenceNum, count)

    def validateSSF(filePath, cs, errorLog):
        ssfp = SSFclass SSFInterface(object):

    def readFile(self, filePath, cs, corpusType):
        pass

    def readFile(self, filePath, cs):
        pass

    def readFile(self, filePath):
        pass

    def readString(self, string, errorLog):
        pass

    def readString(self, string):
        pass

    def makeString(self):
        pass

class SimpleStoryImpl(SSFInterface):

    def __init__(self, id):
        self.id = id
        self.xmlDeclaration = ''
        self.dtdDeclaration = ''
        self.metaData = ''
        self.paragraphs = []
        self.sentences = []

    @staticmethod
    def getCorpusType(filePath, cs):
        pass

    def addParagraphBoundaries(self, paraBeginIndex, sentencesEnded, paraAttribs, paraMetaData):
        pass

    def removeEmptySentences(self):
        pass

    def clear(self):
        pass

    def makeString(self):
        SSF = ''
        metaDataStart = '<metaDataStart>'
        metaDataEnd = '<metaDataEnd>'
        storyStart = '<storyStart>'
        storyEnd = '<storyEnd>'
        bodyStart = '<bodyStart>'
        bodyEnd = '<bodyEnd>'
        paragraphStart = '<paragraphStart>'
        paragraphEnd = '<paragraphEnd>'
        paragraphTextStart = '<paragraphTextStart>'
        paragraphTextEnd = '<paragraphTextEnd>'
        if storyStart.startswith('<'):
            storyStart += ' id="{}">'.format(self.id)
            storyEnd += '>'
        else:
            storyStart += ' ' + self.id
        SSF += storyStart + '\n'
        if metaDataStart != '':
            if self.xmlDeclaration != '':
                SSF += self.xmlDeclaration + '\n\n'
            if self.dtdDeclaration != '':
                SSF += self.dtdDeclaration + '\n\n'
            SSF += metaDataStart + '>\n'
            SSF += self.metaData + '\n'
            SSF += metaDataEnd + '>\n'
        paragraphPresent = False
        if paragraphStart != '' and len(self.paragraphs) > 0:
            paragraphPresent = True
        if paragraphPresent:
            SSF += bodyStart + '>\n'
            for para in self.paragraphs:
                if para.getAttribsString() != '':
                    SSF += paragraphStart + ' ' + para.getAttribsString().strip() + '>\n'
                SSF += para.getMetaData() + '\n'
                SSF += paragraphTextStart + '>\n'
                for j in range(para.getStartSentence(), para.getEndSentence()):
                    SSFS = self.sentences[j]
                    SSF += SSFS.makeString() + '\n'
                SSF += paragraphTextEnd + '>\n'
                SSF += paragraphEnd + '>\n'
            SSF += bodyEnd + '>\n'
        else:
            for SSFS in self.sentences:
                SSF += SSFS.makeString() + '\n'
        SSF += storyEnd + '\n'
        return SSF

class SSFSentenceImpl(object):

    @staticmethod
    def readSentencesFromString(lines):
        pass

    def makeString(self):
        pass

class SimpleStoryImplWithSuper(SimpleStoryImpl):

    def __init__(self, id):
        super().__init__(id)

    def readFile(self, filePath, cs, corpusType, *args, **kwargs):
        self.readFile(filePath, cs, corpusType, None)

    def readFile(self, filePath, cs, corpusType, *args, **kwargs):
        corpusType = SimpleStoryImpl.getCorpusType(filePath, cs)
        self.readFile(filePath, cs, corpusType)

    def readFile(self, filePath):
        ssfp = SSFNode.getSSFProperties()
        self.readFile(filePath, ssfp.getProperties().getPropertyValue('encoding'))

    def readString(self, string, errorLog, *args, **kwargs):
        ssfp = SSFNode.getSSFProperties()
        self.clear()
        lines = ''
        lineArray = string.split('\n')
        metaDataStart = ssfp.getProperties().getPropertyValue('metaDataStart')
        storyStart = ssfp.getProperties().getPropertyValue('storyStart')
        bodyStart = ssfp.getProperties().getPropertyValue('bodyStart')
        paragraphStart = ssfp.getProperties().getPropertyValue('paragraphStart')
        sentenceStart = ssfp.getProperties().getPropertyValue('sentenceStart')
        paragraphMetaDataStart = ssfp.getProperties().getPropertyValue('paragraphMetaDataStart')
        metaDataEnd = ssfp.getProperties().getPropertyValue('metaDataEnd')
        storyEnd = ssfp.getProperties().getPropertyValue('storyEnd')
        bodyEnd = ssfp.getProperties().getPropertyValue('bodyEnd')
        paragraphEnd = ssfp.getProperties().getPropertyValue('paragraphEnd')
        sentenceEnd = ssfp.getProperties().getPropertyValue('sentenceEnd')
        metaDataStarted = False
        storyStarted = False
        bodyStarted = False
        sentencesStarted = 0
        sentencesEnded = 0
        paraBeginIndex = 0
        paraAttribs = ''
        paraMetaData = ''
        sentenceHash = {}
        for i in range(len(lineArray)):
            lineArray[i] = lineArray[i].strip()
            if lineArray[i].startswith(sentenceStart):
                sentencesStarted += 1
                sentenceHash[sentencesStarted] = False
            elif lineArray[i].startswith(sentenceEnd):
                sentencesEnded += 1
                if sentenceHash.get(sentencesEnded) is not None:
                    sentenceHash[sentencesEnded] = True
            if lineArray[i].startswith(metaDataStart):
                metaDataStarted = True
            elif lineArray[i].startswith(metaDataEnd):
                metaDataStarted = False
            elif lineArray[i].startswith(paragraphStart):
                lineArray[i] = lineArray[i].replace(paragraphStart, '')
                lineArray[i] = lineArray[i].replace('<', '')
                lineArray[i] = lineArray[i].replace('>', '')
                paraAttribs = lineArray[i]
                paraBeginIndex = sentencesEnded
                paraMetaData = ''
            elif lineArray[i].startswith(paragraphMetaDataStart):
                paraMetaData = lineArray[i]
            elif lineArray[i].startswith(paragraphEnd):
                self.addParagraphBoundaries(paraBeginIndex, sentencesEnded, paraAttribs, paraMetaData)
            elif lineArray[i].startswith(bodyStart):
                bodyStarted = True
            elif lineArray[i].startswith(storyStart):
                storyStarted = True
                tmp = lineArray[i].split('id=')
                if len(tmp) == 2:
                    tmp = tmp[1].split('"')
                    if len(tmp) > 2:
                        self.setId(tmp[1])
            elif lineArray[i].startswith(storyEnd) and lines != '':
                sents = SSFSentenceImpl.readSentencesFromString(lines)
                self.sentences.extend(sents)
                break
            elif metaDataStarted and storyStarted and (not bodyStarted):
                self.metaData += lineArray[i] + '\n'
            elif storyStarted or (sentencesStarted > 0 and (sentencesStarted == sentencesEnded or sentencesStarted == sentencesEnded + 1)):
                lines += lineArray[i] + '\n'
        if not storyStarted:
            if sentencesStarted == sentencesEnded:
                sents = SSFSentenceImpl.readSentencesFromString(lines)
                self.sentences.extend(sents)
            else:
                print('Sentences_started:', sentencesStarted)
                print('Sentences_ended:', sentencesEnded)
                for key, val in sentenceHash.items():
                    if not val:
                        print('\tSentence_not_ended:', key)
                if errorLog is not None:
                    errorLog.append("\nIncorrect_format:_Doesn't_seem_to_be_correct_SSF_format.\n")
                else:
                    raise Exception("\nIncorrect_format:_Doesn't_seem_to_be_correct_SSF_format.\n")
        self.removeEmptySentences()

    def readString(self, string, *args, **kwargs):
        self.readString(string, None)

    def makeString(self, *args, **kwargs):
        ssfp = SSFNode.getSSFProperties()
        SSF = ''
        metaDataStart = '<metaDataStart>'
        metaDataEnd = '<metaDataEnd>'
        storyStart = '<storyStart>'
        storyEnd = '<storyEnd>'
        bodyStart = '<bodyStart>'
        bodyEnd = '<bodyEnd>'
        paragraphStart = '<paragraphStart>'
        paragraphEnd = '<paragraphEnd>'
        paragraphTextStart = '<paragraphTextStart>'
        paragraphTextEnd = '<paragraphTextEnd>'
        if storyStart.startswith('<'):
            storyStart += ' id="{}">'.format(self.id)
            storyEnd += '>'
        else:
            storyStart += ' ' + self.id
        SSF += storyStart + '\n'
        if metaDataStart != '':
            if self.xmlDeclaration != '':
                SSF += self.xmlDeclaration + '\n\n'
            if self.dtdDeclaration != '':
                SSF += self.dtdDeclaration + '\n\n'
            SSF += metaDataStart + '>\n'
            SSF += self.metaData + '\n'
            SSF += metaDataEnd + '>\n'
        paragraphPresent = False
        if paragraphStart != '' and len(self.paragraphs) > 0:
            paragraphPresent = True
        if paragraphPresent:
            SSF += bodyStart + '>\n'
            for para in self.paragraphs:
                if para.getAttribsString() != '':
                    SSF += paragraphStart + ' ' + para.getAttribsString().strip() + '>\n'
                SSF += paradef clear_annotation_recursive(dir, cs, anno_level_flags):
    import os
    from SSFStoryImpl import SSFStoryImpl
    from SSFStory import CorpusType
    if not os.path.exists(dir):
        print("File doesn't exist: " + os.path.abspath(dir))
        return False
    if not os.access(dir, os.W_OK):
        print('No write permission: ' + os.path.abspath(dir))
        return False
    if os.path.isfile(dir):
        if SSFStoryImpl.get_corpus_type(dir, cs) == CorpusType.SSF_FORMAT:
            ssf_story = SSFStoryImpl()
            try:
                ssf_story.read_file(dir, cs, CorpusType.SSF_FORMAT)
            except Exception as ex:
                print(ex)
            ssf_story.clear_annotation(anno_level_flags)
            ssf_story.save(dir, cs)
            return True
        else:
            return False
    success = True
    files = os.listdir(dir)
    if files is None:
        return success
    for file in files:
        success &= clear_annotation_recursive(os.path.join(dir, file), cs, anno_level_flags)
    return success

def replace_words_not_in_list_with_tags(story, prune_word_list):
    from SSFSentenceImpl import SSFSentenceImpl
    from SSFNode import SSFNode
    from SSFProperties import BhaashikMutableTreeNode
    scount = story.count_sentences()
    if prune_word_list is None or prune_word_list.count_properties() == 0:
        return
    for i in range(scount):
        sentence = story.get_sentence(i)
        words = sentence.get_root().get_all_leaves()
        wcount = len(words)
        for j in range(wcount):
            word = words[j]
            if prune_word_list.get_property_value(word.get_lex_data()) is None:
                tag = word.get_name()
                if tag != '':
                    word.set_lex_data(tag)

def read_stories(sel_files, cs):
    from SSFStoryImpl import SSFStoryImpl
    sel_stories = {}
    if sel_files is not None and len(sel_files) > 1:
        for file in sel_files:
            story = SSFStoryImpl()
            sel_stories[file] = story
            story.set_ssf_file(os.path.abspath(file))
            try:
                story.read_file(os.path.abspath(file), cs)
            except Exception as ex:
                print(ex)
    return sel_stories

def load_giza_data(giza_file_path, cs, src_ssf_story, tgt_ssf_story):
    import codecs
    from SSFPhrase import SSFPhrase
    from SSFLexItem import SSFLexItem
    from SSFSentenceImpl import SSFSentenceImpl
    from SSFNode import SSFNode
    from SSFProperties import SSFProperties
    try:
        if cs is not None and cs != '':
            in_reader = codecs.open(os.path.abspath(giza_file_path), 'r', cs)
        else:
            in_reader = open(os.path.abspath(giza_file_path), 'r')
        lines = in_reader.readlines()
        ssfp = SSFNode.get_ssf_properties()
        root_name = ssfp.get_properties().get_property_value('rootName')
        start = False
        src_sen = None
        tgt_sen = None
        rnode = None
        lex_item = None
        tags = None
        words = None
        for line in lines:
            line = line.strip()
            if line.startswith('# Sentence pair'):
                start = True
            elif start:
                src_sen = SSFSentenceImpl()
                rnode = SSFPhrase('0', '', root_name, '')
                src_sen.set_root(rnode)
                wrds = line.split(' ')
                for wrd in wrds:
                    lex_item = SSFLexItem('0', wrd, '', '')
                    src_sen.get_root().add_child(lex_item)
                src_sen.get_root().reallocate_names(None, None)
                src_ssf_story.add_sentence(src_sen)
                start = False
            elif line.startswith('NULL'):
                tgt_sen = SSFSentenceImpl()
                rnode = SSFPhrase('0', '', root_name, '')
                tgt_sen.set_root(rnode)
                wrds = line.split(' ')
                align_indices = []
                in_align = False
                null_node = False
                tags = {}
                words = {}
                for wrd in wrds:
                    wrd = wrd.strip()
                    if wrd == 'NULL':
                        null_node = True
                    else:
                        null_node = False
                    if not in_align and wrd != '({' and (wrd != '})') and (not null_node):
                        lex_item = SSFLexItem('0', wrd, '', '')
                        tgt_sen.get_root().add_child(lex_item)
                    elif wrd == '({':
                        in_align = True
                    elif in_align and wrd != '})' and (not null_node):
                        align_indices.append(int(wrd) - 1)
                    elif wrd == '})' and (not null_node):
                        acount = len(align_indices)
                        ccount = tgt_sen.get_root().count_children()
                        if ccount > 0:
                            tgt_sen.get_root().reallocate_names(tags, words, ccount - 1)
                        align_name = 'alignedTo'
                        align_sep = ';'
                        for j in range(acount):
                            aligned_src_node = src_sen.get_root().get_child(align_indices[j])
                            aligned_src_node.concatenate_attribute_value(align_name, lex_item.get_attribute_value('name'), align_sep)
                            lex_item.concatenate_attribute_value(align_name, aligned_src_node.get_attribute_value('name'), align_sep)
                        align_indices.clear()
                        in_align = False
                tgt_ssf_story.add_sentence(tgt_sen)
                start = True
        src_ssf_story.reallocate_sentence_ids()
        src_ssf_story.reallocate_node_ids()
        tgt_ssf_story.reallocate_sentence_ids()
        tgt_ssf_story.reallocate_node_ids()
    except FileNotFoundError as ex:
        logger.error(ex)
    except Exception as ex:
        logger.error(ex)