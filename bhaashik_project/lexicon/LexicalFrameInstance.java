/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.lexicon;

import java.util.Iterator;

import bhaashik.xml.dom.BhaashikDOMElement;

/**
 *
 * @author anil
 */
public class LexicalFrameInstance extends DefaultLexicalFrame implements BhaashikDOMElement {

    protected String lex;
    protected String lexEx;

    public LexicalFrameInstance()
    {
        super();
    }

    /**
     * @return the lex
     */
    public String getLex()
    {
        return lex;
    }

    /**
     * @param lex the lex to set
     */
    public void setLex(String lex)
    {
        this.lex = lex;
    }    

    /**
     * @return the lexEx
     */
    public String getLexEx()
    {
        return lexEx;
    }

    /**
     * @param lexEx the lexEx to set
     */
    public void setLexEx(String lexEx)
    {
        this.lexEx = lexEx;
    }

    @Override
    public String makeString()
    {
        String str = "";

        str += stem + ": " + lex + ": " + lexEx + ": ";

        Iterator itr = getSlotKeys();

        while(itr.hasNext())
        {
            String key = (String) itr.next();
            LexicalSlot slot = getSlot(key);

            str += slot.makeString() + " ";
        }

        str = str.trim();

        return str;
    }

    public String makeFrameString()
    {
        String str = "";

        Iterator itr = getSlotKeys();

        while(itr.hasNext())
        {
            String key = (String) itr.next();
            LexicalSlot slot = getSlot(key);

            str += slot.makeString() + " ";
        }

        str = str.trim();

        return str;
    }
}
