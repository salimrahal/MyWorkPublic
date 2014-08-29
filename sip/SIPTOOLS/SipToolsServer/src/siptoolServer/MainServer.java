/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siptoolServer;

import java.io.IOException;
import sipserver.bo.EchoServerDatagram;
import sipserver.bo.EchoServerTcp;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import network.Networking;
import org.xml.sax.SAXException;
import sipserver.trf.TrafficServer;
import sipserver.trf.TrfThreadDgm;
import sipserver.trf.TrfRunnableTcpSig;

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

        try {
            /*
            ************ Launch traffic server**************
            */
            TrafficServer.launchingTrafficServer();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
