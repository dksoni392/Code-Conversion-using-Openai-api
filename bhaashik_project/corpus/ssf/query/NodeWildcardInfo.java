/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.ssf.query;

import bhaashik.common.types.SSFQueryTokenType;

/**
 *
 * @author anil
 */
public class NodeWildcardInfo {
    public String wildcardString;
    public SSFQueryTokenType wildcardTokenType = SSFQueryTokenType.WILDCARD_FIRST;
    public int rangeStart = 1;
    public int rangeEnd = Integer.MAX_VALUE;

    public NodeWildcardInfo()
    {

    }

    public NodeWildcardInfo(String wildcardString, SSFQueryTokenType wildcardTokenType,
            int rangeStart, int rangeEnd)
    {
        this.wildcardString = wildcardString;
        this.wildcardTokenType = wildcardTokenType;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }
}
