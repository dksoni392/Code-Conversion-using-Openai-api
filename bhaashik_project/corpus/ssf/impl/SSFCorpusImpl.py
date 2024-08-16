from abc import ABC, abstractmethod
import os
import sys
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), os.path.pardir)))
import bhaashik.corpus.corpus as Corpus
import bhaashik.corpus.parallel.alignable as Alignable
import bhaashik.corpus.ssf.ssf_corpus as SSFCorpus
import bhaashik.corpus.ssf.ssf_story as SSFStory
import bhaashik.corpus.parallel.alignment_unit as AlignmentUnit
import bhaashik.corpus.ssf.ssf_corpus_collection as SSFCorpusCollection
import bhaashik.properties.key_value_properties as KeyValueProperties

class SSFCorpusImpl(Corpus, SSFCorpus.SSFCorpus, ABC):

    def __init__(self, charset):
        super().__init__(charset)
        self.stories = {}
        self.properties = None
        self.alignmentUnit = None

    def __init__(self, prop_file, charset) -> None:
        super().__init__(charset)
        self.properties = KeyValueProperties.KeyValueProperties(prop_file, charset)
        self.stories = {}
        self.alignmentUnit = None

    def get_properties(self):
        return self.properties

    def set_properties(self, p):
        self.properties = p

    def count_stories(self):
        return len(self.stories)

    def get_story_keys(self):
        return self.stories.keys()

    def get_story(self, p):
        return self.stories.get(p)

    def add_story(self, p, s):
        if s is None:
            s = SSFStoryImpl()
        self.stories[p] = s
        return len(self.stories)

    def remove_story(self, p):
        return self.stories.pop(p)

    def read(self):
        f = os.path.join(self.path)
        self.read(f)

    def read_files(self, f):
        for i in f:
            self.read(f[i])

    def read(self, f):
        if f is None:
            f = self.path
        if os.path.isdir(f) is True:
            files = os.listdir(f)
            for file in files:
                self.read(file)
        elif os.path.isfile(f) is True:
            p = os.path.abspath(f)
            self.add_story(p, None)

    def read_story(self, f):
        if f is None:
            f = os.path.join(self.path)
        if os.path.isdir(f) is True:
            files = os.listdir(f)
            for file in files:
                self.read_story(file)
        elif os.path.isfile(f) is True:
            p = os.path.abspath(f)
            s = SSFStoryImpl()
            s.read_file(p, self.charset)
            self.add_story(p, s)

    def read_stories(self, fs):
        for f in fs:
            p = f.getAbsolutePath()
            s = SSFStoryImpl()
            s.read_file(p, self.charset)
            self.add_story(p, s)

    def clear(self):
        self.path = ''
        self.stories.clear()
        self.properties.clear()

    def print(self, ps):
        pass

    def get_copy(self):
        return None

    def get_alignment_unit(self):
        return self.alignmentUnit

    def set_alignment_unit(self, alignment_unit):
        self.alignmentUnit = alignment_unit

    def get_aligned_object(self, alignment_key):
        return self.alignmentUnit.get_aligned_object(alignment_key)

    def get_aligned_objects(self):
        return self.alignmentUnit.get_aligned_objects()

    def get_first_aligned_object(self):
        return self.alignmentUnit.get_first_aligned_object()

    def get_aligned_object(self, i):
        return self.alignmentUnit.get_aligned_object(i)

    def get_last_aligned_object(self):
        return self.alignmentUnit.get_last_aligned_object()
if __name__ == '__main__':
    pass