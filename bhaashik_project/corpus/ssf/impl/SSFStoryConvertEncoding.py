import os
import shutil
from bhaashik.corpus.ssf.features.impl import FSProperties
from bhaashik.corpus.ssf.features.impl import FeatureStructuresImpl
from bhaashik.corpus.ssf.impl import SSFStoryImpl
from bhaashik.corpus.ssf.tree import SSFNode
from bhaashik.text.enc.conv import EncodingConverterUtils
from bhaashik.text.enc.conv import BhaashikEncodingConverter
from bhaashik.corpus.ssf import SSFProperties
from bhaashik.GlobalProperties import resolveRelativePath, getIntlString

class SSFStoryConvertEncoding(BhaashikEncodingConverter):

    def __init__(self):
        super().__init__()
        self.encodingConverter = self.createEncodingConverter()

    def init(self, srcLangEnc, tgtLangEnc):
        self.encodingConverter = EncodingConverterUtils.createEncodingConverter(srcLangEnc, tgtLangEnc)
        fsp = FSProperties()
        ssfp = SSFProperties()
        try:
            fsp.read(resolveRelativePath('props/fs-mandatory-attribs.txt'), resolveRelativePath('props/fs-other-attribs.txt'), resolveRelativePath('props/fs-props.txt'), resolveRelativePath('props/ps-attribs.txt'), resolveRelativePath('props/dep-attribs.txt'), resolveRelativePath('props/sem-attribs.txt'), getIntlString('UTF-8'))
            ssfp.read(resolveRelativePath('props/ssf-props.txt'), getIntlString('UTF-8'))
            FeatureStructuresImpl.setFSProperties(fsp)
            SSFNode.setSSFProperties(ssfp)
        except FileNotFoundError as e:
            e.printStackTrace()
        except IOException as e:
            e.printStackTrace()
        except Exception as e:
            e.printStackTrace()

    def convertEncoding(self, srcFilePath, srcCharset, tgtFilePath, tgtCharset):
        print('Converting: ' + srcFilePath)
        story = SSFStoryImpl()
        story.readFile(srcFilePath, srcCharset)
        story.convertEncoding(self.encodingConverter, 'NULL')
        story.save(tgtFilePath, tgtCharset)

    def convertEncoding(self, story, nullNodeString):
        story.convertEncoding(self.encodingConverter, nullNodeString)

    def main(self):
        srcLangEnc = 'hin::utf8'
        tgtLangEnc = 'hin::wx'
        self.init(srcLangEnc, tgtLangEnc)
        srcFilePath = '/home/anil/bhaash-debug-data/validation-test'
        tgtFilePath = '/home/anil/bhaash-debug-data/validation-test-wx'
        srcFile = os.path.join(srcFilePath)
        tgtFile = os.path.join(tgtFilePath)
        os.makedirs(tgtFile, exist_ok=True)
        srcCharset = 'UTF-8'
        tgtCharset = 'UTF-8'
        try:
            if os.path.isfile(srcFile) and os.path.isfile(tgtFile):
                self.convertEncoding(srcFilePath, srcCharset, tgtFilePath, tgtCharset)
            elif os.path.isdir(srcFile) and os.path.isdir(tgtFile):
                files = os.listdir(srcFile)
                for file in files:
                    tgtFl = os.path.join(tgtFile, file)
                    self.convertEncoding(os.path.join(srcFile, file), srcCharset, tgtFl, tgtCharset)
        except FileNotFoundError as e:
            e.printStackTrace()
        except IOException as e:
            e.printStackTrace()
        except Exception as e:
            e.printStackTrace()