package bhaashik.corpus.simple.data;

import java.util.*;

// compare() Inconsistent with equals()
public class ReverseIntSort implements Comparator
{
	public int compare(Object one, Object two)
	{
		return ( (int) ( ((Integer)two).intValue() ) - (int) ( ((Integer)one).intValue() ));
	}
}
