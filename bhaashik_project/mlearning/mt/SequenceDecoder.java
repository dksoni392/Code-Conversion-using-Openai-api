/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.mlearning.mt;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author anil
 */
public class SequenceDecoder {
    protected Vector T;
    protected Vector W;

    protected Vector words;
    Vector decodedSequence;

    protected Hashtable PI;

    protected Hashtable A;
    protected Hashtable B;

    protected Hashtable alpha;
    protected Hashtable beta;
    protected Hashtable delta;
    protected Hashtable xi;

    public SequenceDecoder()
    {
    }

    private void toyPOSTaggerInit()
    {
        Collections.addAll(T, "d", "n", "v", "a", "o");
        Collections.addAll(W, "the", "boy", "fell", "pluck", "down", "child", "flower", "cry", "red");

        // Prior probabilities
        PI.put("d", 0.5);
        PI.put("n", 0.2);
        PI.put("v", 0.1);
        PI.put("a", 0.1);
        PI.put("o", 0.1);

        // Transition probabilities
        Hashtable h = new Hashtable();
        A.put("d", h);
        h.put("d", 0.05);
        h.put("n", 0.4);
        h.put("v", 0.05);
        h.put("a", 0.3);
        h.put("o", 0.2);

        h = new Hashtable();
        A.put("n", h);
        h.put("d", 0.1);
        h.put("n", 0.3);
        h.put("v", 0.2);
        h.put("a", 0.05);
        h.put("o", 0.35);

        h = new Hashtable();
        A.put("v", h);
        h.put("d", 0.3);
        h.put("n", 0.3);
        h.put("v", 0.1);
        h.put("a", 0.1);
        h.put("o", 0.2);

        h = new Hashtable();
        A.put("a", h);
        h.put("d", 0.1);
        h.put("n", 0.5);
        h.put("v", 0.1);
        h.put("a", 0.2);
        h.put("o", 0.1);

        h = new Hashtable();
        A.put("o", h);
        h.put("d", 0.2);
        h.put("n", 0.2);
        h.put("v", 0.4);
        h.put("a", 0.05);
        h.put("o", 0.15);

        // Emission probabilities
        h = new Hashtable();
        B.put("d", h);
        h.put("the", 0.4);
        h.put("boy", 0.01);
        h.put("fell", 0.0);
        h.put("pluck", 0.005);
        h.put("down", 0.01);
        h.put("child", 0.0);
        h.put("flower", 0.0);
        h.put("cry", 0.0);
        h.put("red", 0.0);

        h = new Hashtable();
        B.put("n", h);
        h.put("the", 0.01);
        h.put("boy", 0.1);
        h.put("fell", 0.1);
        h.put("pluck", 0.05);
        h.put("down", 0.0);
        h.put("child", 0.1);
        h.put("flower", 0.1);
        h.put("cry", 0.0);
        h.put("red", 0.05);

        h = new Hashtable();
        B.put("v", h);
        h.put("the", 0.0);
        h.put("boy", 0.0);
        h.put("fell", 0.15);
        h.put("pluck", 0.1);
        h.put("down", 0.0);
        h.put("child", 0.0);
        h.put("flower", 0.05);
        h.put("cry", 0.1);
        h.put("red", 0.0);

        h = new Hashtable();
        B.put("a", h);
        h.put("the", 0.0);
        h.put("boy", 0.5);
        h.put("fell", 0.05);
        h.put("pluck", 0.0);
        h.put("down", 0.08);
        h.put("child", 0.01);
        h.put("flower", 0.08);
        h.put("cry", 0.1);
        h.put("red", 0.1);

        h = new Hashtable();
        B.put("o", h);
        h.put("the", 0.0);
        h.put("boy", 0.04);
        h.put("fell", 0.1);
        h.put("pluck", 0.01);
        h.put("down", 0.08);
        h.put("child", 0.02);
        h.put("flower", 0.05);
        h.put("cry", 0.02);
        h.put("red", 0.07);
    }

    public double forward(Vector sequence)
    {
//            my ($aref, $bref, $piref) = @_;
            double p;
            int tp;
            String line;
            double x;
            double y;
            double z;
            double zz;

            initAlpha();

            int t = 1;
            int count = T.size();
            for(int i = 0; i < count; i++)
            {
                    ((Hashtable) alpha.get(T.get(i))).put(Integer.valueOf(t), PI.get(T.get(i)));
//    #               print "alpha{$T[$i]}{$t}: ".$alpha{$T[$i]}{$t}."\n";
            }

            int tcount = T.size();
            int wcount = words.size();

            for(t = 2; t <= wcount + 1; t++)
            {
                    tp = t - 1;

                    for(int j = 0; j < tcount; j++)
                    {
                            for(int i = 0; i < tcount; i++)
                            {
                                    x = ((Double) ((Hashtable) alpha.get(T.get(i))).get(Integer.valueOf(tp))).doubleValue();
                                    y = ((Double) ((Hashtable) A.get(T.get(i))).get(T.get(j))).doubleValue();
                                    z = ((Double) ((Hashtable) B.get(T.get(j))).get(words.get(t - 2))).doubleValue();
//    #                               print "wds[$t-2]".$wds[$t-2]."\n";
                                    zz = (x * y) * z;
                                    double av = ((Double) ((Hashtable) alpha.get(T.get(j))).get(Integer.valueOf(t))).doubleValue();
                                    av += zz;
                                    ((Hashtable) alpha.get(T.get(j))).put(Integer.valueOf(t), av);

//    #                               print "\tx: $x";
//    #                               print "\ty: $y";
//    #                               print "\tbref->{$T[$j]}{$wds[$t-2]}: $bref->{$T[$j]}{$wds[$t-2]}\n";
//    #                               print "\tzz: $zz\n";
//    #                               print "alpha{$T[$j]}{$t}: ".$alpha{$T[$j]}{$t}."\n";
                            }
                    }
//    #               print "t: ".$t."\n";
            }

            p = 0; t--;

            for(int i = 0; i < tcount; i++)
            {
                p += ((Double) ((Hashtable) alpha.get(T.get(i))).get(Integer.valueOf(t))).doubleValue();
//    #               print "alpha{$T[$i]}{$t}: ".$alpha{$T[$i]}{$t}."\n";
//    #               print "p: ".$p."\n";
            }
            return p;
    }

protected void initAlpha()
{
    int i;
    int j;

    int count = W.size();
    int tcount = T.size();

    for(i = 1; i <= count; i++)
    {
        for(j = 0; j < tcount; j++)
        {
            Hashtable alphaTj =(Hashtable) alpha.get(T.get(j));

            if(alphaTj == null)
            {
                alphaTj = new Hashtable(0, 10);
                alpha.put(T.get(j), alphaTj);
            }

            alphaTj.put(Integer.valueOf(i), Double.valueOf(0.0));
        }
    }
}

//sub printAlpha($)
//{
//        my ($alref) = @_;
//        my ($i, $j);
//
//        for($i = 1; $i <= @wds; $i++)
//        {
//                for($j = 0; $j < @T; $j++) { print "aref->{$T[$j]}{$i}: ".$alref->{$T[$j]}{$i}."\n"; }
//        }
//}
//
//sub isAlphaFilled($)
//{
//        my ($alref) = @_;
//        my ($i, $j);
//
//        for($i = 1; $i <= @wds; $i++)
//        {
//                for($j = 0; $j < @T; $j++)
//                {
//                        print "IAF:aref->{$T[$j]}{$i}: ".$alref->{$T[$j]}{$i}."\n";
//                        if($alref->{$T[$j]}{$i} == -1) { return "false" }
//                }
//        }
//
//        return "true";
//}
//

public double backward()
{
        double p;
        int tn;
        String line;
        double x;
        double y;
        double z;
        double zz;

        int t = words.size() + 1;

        int tcount = T.size();

        for(int i = 0; i < tcount; i++)
        {
            Hashtable th = new Hashtable(0, 10);
            th.put(Integer.valueOf(t), Double.valueOf(1.0));
            beta.put(T.get(i), th);
//                #$piref->{$T[$i]};
        }

        int wcount = words.size();

        for(t = wcount; t >= 1; t--)
        {
                tn = t + 1;

                for(int j = 0; j < tcount; j++)
                {
                        for(int i = 0; i < tcount; i++)
                        {
                                x = ((Double) ((Hashtable) beta.get(T.get(i))).get(Integer.valueOf(tn))).doubleValue();
                                y = ((Double) ((Hashtable) A.get(T.get(i))).get(T.get(j))).doubleValue();
                                z = ((Double) ((Hashtable) B.get(T.get(j))).get(words.get(t - 2))).doubleValue();
//    #                               print "wds[$t-2]".$wds[$t-2]."\n";
                                zz = (x * y) * z;
                                double bv = ((Double) ((Hashtable) beta.get(T.get(j))).get(Integer.valueOf(t))).doubleValue();
                                bv += zz;
                                ((Hashtable) beta.get(T.get(j))).put(Integer.valueOf(t), Double.valueOf(bv));
                        }
                }
        }

        p = 0;

        for(int i = 0; i < tcount; i++)
        {
            p += ((Double) ((Hashtable) beta.get(T.get(i))).get(Integer.valueOf(1))).doubleValue() * ((Double) PI.get(T.get(i))).doubleValue();
        }

        return p;
}

public double viterbi()
{
//        my ($p, $i, $j, $t, $tp, $line, $x, $y, $z, $zz, $delmax, @tags);
        double p;
        int tp;
        String line;
        double x;
        double y;
        double z;
        double zz;
        double delmax;

        int wcount = words.size();

        decodedSequence = new Vector(wcount);
        
        for(int i = 0; i < wcount; i++)
        {
            decodedSequence.add("");
        }

        int t = 1;

        int tcount = T.size();

        for(int i = 0; i < tcount; i++)
        {
            Hashtable dh = new Hashtable(0, 10);
            dh.put(Integer.valueOf(t), PI.get(T.get(i)));
            delta.put(T.get(i), dh);
        }

        for(t = 2; t <= wcount + 1; t++)
        {
                tp = t - 1;

                for(int j = 0; j < tcount; j++)
                {
                        delmax = -1.0;
                        
                        for(int i = 0; i < tcount; i++)
                        {
                                x = ((Double) ((Hashtable) delta.get(T.get(i))).get(Integer.valueOf(tp))).doubleValue();
                                y = ((Double) ((Hashtable) A.get(T.get(i))).get(T.get(j))).doubleValue();
                                z = ((Double) ((Hashtable) B.get(T.get(j))).get(words.get(t - 2))).doubleValue();
//    #                               print "wds[$t-2]".$wds[$t-2]."\n";
                                zz = (x * y) * z;

                                if(delmax < zz)
                                {
                                    delmax = zz;
                                    ((Hashtable) delta.get(T.get(j))).put(Integer.valueOf(t), Double.valueOf(delmax));
                                    ((Hashtable) xi.get(T.get(j))).put(Integer.valueOf(t), T.get(i));
                                }
                        }
                }
        }

        p = -1; t--;

        for(int i = 0; i < tcount; i++)
        {
                double dv = ((Double) ((Hashtable) delta.get(T.get(i))).get(Integer.valueOf(t))).doubleValue();

                if(p < dv)
                {
                        p = dv;
                        decodedSequence.setElementAt(T.get(i), wcount - 1);
                }
        }

        for(int i = wcount - 2; i >= 0; i--)
        {
                t = i + 3;
                decodedSequence.setElementAt(((Hashtable) xi.get(decodedSequence.get(i + 1))).get(Integer.valueOf(t)), i);
        }
//        print "Most likely tag sequence: ".join(" ", @tags)."\n";

        return p;
    }
}
