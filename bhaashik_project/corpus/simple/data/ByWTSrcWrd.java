package bhaashik.corpus.simple.data;

import java.util.*;

// compare() Inconsistent with equals()
public class ByWTSrcWrd implements Comparator
{
	public int compare(Object one, Object two)
	{
		return ( ((WordType) one).getWord().compareTo( ((WordType) two).getWord() ) );
	}
}
