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
        ServerDatagram serverUdp = new ServerDatagram(localIp, 5060);
        serverUdp.processrequests();
    }
    

}
