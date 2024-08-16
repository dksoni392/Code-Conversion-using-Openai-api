/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.morph.segment;

import java.util.Vector;

import bhaashik.text.spell.InstantiatePhonemeGraph;

/**
 *
 * @author ram
 */
public class FeatureVectorTester {

    public InstantiatePhonemeGraph ipg = new InstantiatePhonemeGraph("hin::utf8", "UTF-8", false);
    
    public static void main(String[] args) {
        FeatureVectorTester fvt = new FeatureVectorTester();
        Vector vec = fvt.ipg.createPhonemeSequence2("दीवानापन");

        System.out.print("Vector's Size:" + vec.size()+"\n");
        
         int i=vec.size();
        while(--i >= 0){
            System.err.print(vec.get(i)+"\n");
        }
        
    }
}
