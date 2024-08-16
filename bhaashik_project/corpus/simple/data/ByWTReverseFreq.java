package bhaashik.corpus.simple.data;

import java.util.*;

// compare() Inconsistent with equals()
public class ByWTReverseFreq implements Comparator
{
	public int compare(Object one, Object two)
	{
		return ( (int) ((WordType) two).getFreq() - (int) ((WordType) one).getFreq() );
	}
}
