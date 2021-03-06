/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siptoolServer;

import cfg.Spf;
import cfg.vo.ConfVO;
import java.io.IOException;
import sipserver.bo.EchoServerDatagram;
import sipserver.bo.EchoServerTcp;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import network.Networking;
import org.xml.sax.SAXException;
import sipserver.trf.TrafficServer;

/**
 *
 * @author salim
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws SocketException, UnknownHostException, ParserConfigurationException, SAXException, IOException {
        //parsing the config to get IP servers and port-sig etc
        parseConfig();
        //for remote test or on production
        String localIpAlgEcho = ConfVO.getInstance().getIpServerAlg();//"127.0.1.1";//
        String localIpTraffic =ConfVO.getInstance().getIpServerTrf();
        
        //previous method
        //String localIpAlgEcho = Networking.getLocalIpAddress();//"127.0.1.1";//
        //String localIpTraffic = localIpAlgEcho;

        //for local host test
        //localIp = "127.0.0.1";
        /**
         * ***********ALG Echo Server*********************
         */
        Integer defaultport = 5092;
        String portStr;
        if (args.length == 0) {
            System.out.println("[" + new Date() + "] The server by default run on ALG port:" + defaultport + ", you can change the port by passing it as parameter: example: java -jar SipToolServer.jar.jar 5068."
                    + "");
            //comment the code below to disable the echo server
            //launching UDP TCP echo server thread
            launchingEchoServer(localIpAlgEcho, defaultport);
        } else {
            portStr = args[0];
            Integer port = Integer.valueOf(portStr);
            launchingEchoServer(localIpAlgEcho, port);
        }

        try {
            /*
             ************ Launch traffic server**************
             */
            TrafficServer.launchingTrafficServer(localIpTraffic);
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

    public static void parseConfig() throws ParserConfigurationException, SAXException, IOException {
        Spf saxparserconf = new Spf();
        saxparserconf.parseConfVOPrt(ConfVO.getInstance().getInitialLoc());
        System.out.println("Conf parsed:"+ConfVO.getInstance().toString());
    }

}
