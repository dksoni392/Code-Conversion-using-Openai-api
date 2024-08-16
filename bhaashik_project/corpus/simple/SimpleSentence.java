/*
 * Created on Sep 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.simple;

import java.io.PrintStream;
import java.util.Vector;

import bhaashik.corpus.parallel.APCData;
import bhaashik.corpus.parallel.APCProperties;
import bhaashik.corpus.Sentence;
import bhaashik.table.BhaashikTableModel;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SimpleSentence {

	int countWords();

	int getWord(int num);

	int setWord(int ind /* sentence index */, int wd /* table index */);

    void insertWord(int ind /* sentence index */, int wd /* table index */);

	int countSbtWords();

	int getWordSbt(int num);

	int setWordSbt(int ind , int wd ); // sentence index ,  table index

	void calculateSentenceLength(BhaashikTableModel wttbl);

    int getSentenceLength();

    void setSentenceLength(int senlen);

	void calculateSignature(BhaashikTableModel wttbl);

    int getSignature();

	void setSignature(int sg);

	void setWeightedLength(APCProperties apcpro, char type);

	void setWeightedLength(char type);

    int getWeightedLength();

    Vector getWords(BhaashikTableModel wttbl);

    String getSentenceString(BhaashikTableModel wttbl);

    String getWordString(int i, BhaashikTableModel wttbl);

    double getCommonWords(Sentence sen, APCData apcdata);

    double getCommonHypernyms(Sentence sen, APCData apcdata);

	double get_Phntc_Num_Match(Sentence sen, APCData apcdata);

	double getCommonSynonyms(Sentence sen, APCData apcdata);

    String removeVowels(String word, String lang);

    void print(PrintStream ps);

    void printCounts(BhaashikTableModel wtTable, PrintStream ps);
}
