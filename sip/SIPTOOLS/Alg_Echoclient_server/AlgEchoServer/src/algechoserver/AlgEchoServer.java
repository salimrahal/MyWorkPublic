/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algechoserver;

import bo.EchoServerDatagram;
import bo.EchoServerTcp;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import network.Networking;

/**
 *
 * @author salim
 */
public class AlgEchoServer {

    //this callid key is used by the server to accept only the sip message contains this key
    public static final String CLIENT_RECOGNIZED_CALLID_PREFIX = "11256979-ca11b60c";//
    public static final String CLIENT_CALLID_HEADER = "Call-ID";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //String localIp = Networking.getLocalIpAddress();//"127.0.1.1";//
        String localIp = "127.0.1.1";
        // try{

        Integer defaultport = 5060;
        String portStr;
        if (args.length == 0) {
            System.out.println("The server by default run on " + defaultport + ", you can change the port by passing it as parameter: example: java -jar AlgEchoServer.jar 5068."
                    + "");
            //launching UDP TCP echo server thread
            launchingServers(localIp, defaultport);
        } else {
            portStr = args[0];
            Integer port = Integer.valueOf(portStr);
            launchingServers(localIp, port);

        }
    }

    public static void launchingServers(String localIp, Integer port) throws SocketException, UnknownHostException {
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
