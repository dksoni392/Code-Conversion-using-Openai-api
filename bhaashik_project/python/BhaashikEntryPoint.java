/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.python;

import java.util.logging.Level;
import java.util.logging.Logger;

import bhaashik.corpus.ssf.impl.SSFStoryImpl;
import bhaashik.corpus.ssf.query.SSFQuery;
import py4j.GatewayServer;

/**
 *
 * @author anil
 */
public class BhaashikEntryPoint {

    public BhaashikEntryPoint() {
    }

//    public SSFStoryImpl getSSFStory() {
//        return ssfStory;
//    }
//
//    public SSFQuery getQuery()
//    {
//	return ssfQuery;
//    }

    public SSFStoryImpl createSSFStory() {
        return new SSFStoryImpl();
    }

    public SSFQuery createQuery(String qs)
    {
        SSFQuery q = new SSFQuery(qs);
        
        try {
            q.parseQuery();
        } catch (Exception ex) {
            Logger.getLogger(BhaashikEntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
	return q;
    }

    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new BhaashikEntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

}