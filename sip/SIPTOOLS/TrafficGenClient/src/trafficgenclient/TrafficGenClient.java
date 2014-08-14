/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trafficgenclient;

import bo.Networking;
import controller.ClientController;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import javax.swing.JTextArea;
import sun.misc.Cleaner;


/**
 *
 * @author salim
 */
public class TrafficGenClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        // TODO code application logic here
         Integer portsrc = 6000;
        Integer portdest = 5099 ;
        String ipserver = "127.0.1.1";
       DatagramSocket datagramsocket = new DatagramSocket(portsrc);
        ClientController cc = new ClientController();
       cc.sendDatagram(datagramsocket, portsrc, portdest, ipserver);
    }
    
    
}
