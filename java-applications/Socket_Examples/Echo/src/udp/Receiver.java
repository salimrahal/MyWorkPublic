/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 *
 * @author salim
 */
public class Receiver {

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        int srcport = 5068;
        String ip = "127.0.0.1";
        InetAddress inetAddr = InetAddress.getByName(ip);
        DatagramSocket ds = new DatagramSocket(srcport, inetAddr);
        byte[] buf = new byte[1024];
        DatagramPacket pd = new DatagramPacket(buf, buf.length);
        boolean morepkt = true;
        int i = 0;
        String rcvmsg = null;
        while (morepkt) {
            System.out.println("waiting to receive....portsrc=" + ds.getLocalPort()+"/"+ds.getLocalAddress());
            ds.receive(pd);
            i++;
            rcvmsg =  new String (pd.getData());
           
            System.out.println("count="+i+" received data=" +rcvmsg);
            
            if (i == 1000000) {
                morepkt = false;
            }
            System.out.println("finish receiving.");
        }
    }
}
