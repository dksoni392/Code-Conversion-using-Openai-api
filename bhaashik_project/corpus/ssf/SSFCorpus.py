'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
import os
from enum import Enum
from typing import List, Dict
from bhaashik.corpus.parallel.Alignable import Alignable
from bhaashik.properties.KeyValueProperties import KeyValueProperties
from bhaashik.corpus.ssf.SSFStory import SSFStory

class SSFCorpus(Alignable):

    class AnnotationLevels(Enum):
        NONE = 0
        POS_TAGS = 1
        CHUNK_NAMES = 2
        TAGS = POS_TAGS | CHUNK_NAMES
        CHUNKS = 4
        LEX_MANDATORY_ATTRIBUTES = 8
        LEX_EXTRA_ATTRIBUTES = 16
        LEXITEM_FEATURE_STRUCTURES = LEX_MANDATORY_ATTRIBUTES | LEX_EXTRA_ATTRIBUTES
        CHUNK_MANDATORY_ATTRIBUTES = 32
        CHUNK_EXTRA_ATTRIBUTES = 64
        CHUNK_FEATURE_STRUCTURES = CHUNK_MANDATORY_ATTRIBUTES | CHUNK_EXTRA_ATTRIBUTES
        ALL_EXCEPT_THE_FIRST_FS = 128
        PRUNE_THE_FS = 256
        COMMENTS = 512
        OVERALL_ANNOTATION = POS_TAGS | CHUNK_NAMES | CHUNKS | LEX_MANDATORY_ATTRIBUTES | LEX_EXTRA_ATTRIBUTES | LEXITEM_FEATURE_STRUCTURES | CHUNK_MANDATORY_ATTRIBUTES | CHUNK_EXTRA_ATTRIBUTES | CHUNK_FEATURE_STRUCTURES | ALL_EXCEPT_THE_FIRST_FS | COMMENTS

    def __init__(self):
        self.properties = KeyValueProperties()
        self.path = ''
        self.charset = ''

    def getProperties(self) -> KeyValueProperties:
        return self.properties

    def setProperties(self, p: KeyValueProperties):
        self.properties = p

    def getPath(self) -> str:
        return self.path

    def setPath(self, p: str):
        self.path = p

    def getCharset(self) -> str:
        return self.charset

    def setCharset(self, cs: str):
        self.charset = cs

    def countStories(self) -> int:
        ...

    def getStoryKeys(self) -> List[str]:
        ...

    def getStory(self, p: str) -> SSFStory:
        ...

    def addStory(self, p: str, s: SSFStory) -> int:
        ...

    def removeStory(self, p: str) -> str:
        ...

    def read(self):
        ...

    def read_file(self, f: File):
        ...

    def read_files(self, f: List[File]):
        ...

    def read_story(self, f: File):
        ...