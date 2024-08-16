/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik;

import java.io.File;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author anil
 */
public class BhaashikLauncher {
    public static void main(String[] args) {
//        String srcDir = "/home/anil/tmp/feature_based_code/featurengramscode";
        String srcDir = "/home/anil/tmp";
        String tgtDir = "/home/anil/bhaash/Bhaashik";

        String routePath = UtilityFunctions.getRoutePath(new File(srcDir), new File(tgtDir));

        System.out.println(routePath);
    }

}
