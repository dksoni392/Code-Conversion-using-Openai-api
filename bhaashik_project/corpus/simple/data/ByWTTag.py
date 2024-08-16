from typing import List

class WordType:

    def __init__(self, tag: str):
        self.tag = tag

class ByWTTag(Comparator):

    def compare(self, one: WordType, two: WordType) -> int:
        return one.tag.compareto(two.tag)