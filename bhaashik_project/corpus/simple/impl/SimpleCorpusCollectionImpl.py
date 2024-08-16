'\nCreated on Sep 24, 2005\n\nTODO To change the template for this generated file go to\nWindow - Preferences - Java - Code Style - Code Templates\n'
from bhaashik.corpus import CorpusCollection
from bhaashik.corpus.simple import SimpleCorpusCollection

class SimpleCorpusCollectionImpl(CorpusCollection, SimpleCorpusCollection):

    def __init__(self):
        super().__init__()
if __name__ == '__main__':
    pass