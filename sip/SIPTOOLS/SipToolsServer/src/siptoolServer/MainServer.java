/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siptoolServer;

import sipserver.bo.EchoServerDatagram;
import sipserver.bo.EchoServerTcp;
import java.net.SocketException;
import java.net.UnknownHostException;
import network.Networking;
import sipserver.trf.TrfThreadDgm;
import sipserver.trf.TrfThreadTcp;

/**
 *
 * @author salim
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws SocketException, UnknownHostException {
        //for remote test or on production
        String localIp = Networking.getLocalIpAddress();//"127.0.1.1";//
        //for local host test
        localIp = "127.0.0.1";

        /**
         * ***********ALG Echo Server*********************
         */
        Integer defaultport = 5092;
        String portStr;
        if (args.length == 0) {
            System.out.println("The server by default run on " + defaultport + ", you can change the port by passing it as parameter: example: java -jar AlgEchoServer.jar 5068."
                    + "");
            //launching UDP TCP echo server thread
            launchingEchoServer(localIp, defaultport);
        } else {
            portStr = args[0];
            Integer port = Integer.valueOf(portStr);
            launchingEchoServer(localIp, port);

        }

        /**
         * *********** Traffic Server It listens on five ports, accepts
         * traffic(upload) from multiple clients, and generates download traffic
         * depending on the codec or pps
           *********************
         */
        Integer[] portArr;
        Integer port1 = 5094;
        Integer port2 = 5095;
        Integer port3 = 5096;
        Integer port4 = 5097;
        Integer port5 = 5098;

        portArr = new Integer[]{port1, port2, port3, port4, port5};
        
        launchingTrafficServer(localIp, portArr);
    }

    public static void launchingEchoServer(String localIp, Integer port) throws SocketException, UnknownHostException {
        //launching UDP echo server thread
        EchoServerDatagram serverUdp = new EchoServerDatagram(localIp, port);
        Thread serverUdpThread = new Thread(serverUdp);
        serverUdpThread.start();

        //launching TCP echo server
        EchoServerTcp serverTcp = new EchoServerTcp(port);
        Thread serverTcpThread = new Thread(serverTcp);
        serverTcpThread.start();

    }

    public static void launchingTrafficServer(String localIp, Integer[] portArr) throws SocketException, UnknownHostException {
       System.out.println("Launching Traffic server...on ports: "
                + portArr[0] + ", " + portArr[1] + ", " + portArr[2] + ", " + portArr[3] + ", " + portArr[4]);
        //launching traffic UDP server
        for (Integer port : portArr) {
            TrfThreadDgm trfthreadUdp = new TrfThreadDgm(localIp, port);
            Thread serverTrfThread = new Thread(trfthreadUdp);
            serverTrfThread.start();
        }
         //launching traffic tcp server
        for (Integer port : portArr) {
            TrfThreadTcp trfthreadTcp = new TrfThreadTcp(port);
            Thread serverTrfThread = new Thread(trfthreadTcp);
            serverTrfThread.start();
        }
    }

}
