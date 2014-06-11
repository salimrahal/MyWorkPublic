/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algechoserver;

import bo.DatagramServerRunnable;
import bo.ServerDatagram;
import java.io.IOException;
import java.net.InetAddress;
import network.Networking;
import sun.awt.windows.ThemeReader;

/**
 *
 * @author salim
 */
public class AlgEchoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String localIp = Networking.getLocalIpAddress();//"127.0.1.1";//
        //String localIp = "127.0.1.1";
       // try{
       
        Integer defaultport = 5060;
        String portStr;
        if (args.length == 0) {
             
            System.out.println("The server by default run on " + defaultport + ", you can change the port by passing it as parameter: example: java -jar AlgEchoServer.jar 5068."
                    + "");
            ServerDatagram serverUdp = new ServerDatagram(localIp, defaultport);
            serverUdp.processrequests();
        } else {
            portStr = args[0];
            Integer port = Integer.valueOf(portStr);
            ServerDatagram serverUdp = new ServerDatagram(localIp, port);
            serverUdp.processrequests();

        }  
//        }catch(Exception ex){
//            System.out.println("Error: "+ex.getLocalizedMessage());
//        }
        

    }

}
