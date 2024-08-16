/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers.impl;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 *
 * @author User
 */
//public class MainServerCollection implements Runnable {
public class MainServerCollection {

//    final ExecutorService service = Executors.newCachedThreadPool();
    private final static LinkedHashMap <UUID, BhaashikMainServer> serverCollection = new LinkedHashMap<UUID, BhaashikMainServer>();
    private final static LinkedHashMap <UUID, Thread> serverThreadCollection = new LinkedHashMap<UUID, Thread>();
//    private volatile boolean stop;
    
    public MainServerCollection() 
    {
    }

    public void execute(UUID uuid, BhaashikMainServer mainServer) {
        serverCollection.put(uuid, mainServer);

        System.out.println("Bhaashik Main server: " + uuid);

        BhaashikServerThread thread = new BhaashikServerThread(mainServer);
        serverThreadCollection.put(uuid, thread);
        
        thread.start();
    }
//
//    public void run() {
//    }

    public static BhaashikMainServer getAuthenticationSever(UUID uuid) {
        synchronized (serverCollection) {
            return serverCollection.get(uuid);
        }
    }

    public BhaashikMainServer removeAuthenticationSever(UUID uuid) {
        synchronized (serverCollection) {
            Thread serverThread = serverThreadCollection.remove(uuid);
            serverThread.interrupt();
            return serverCollection.remove(uuid);
        }
    }

//    public void stop() {
//        stop = true;
//    }        
    
//    final class MainServerTask implements Runnable {
//
//        @Override
//        public void run() {
//            System.out.println("Running main server task ...");
//        }
//    };
}
