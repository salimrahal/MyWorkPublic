/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author salim
 */
public class Sender {
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException{
        
        int srcport = 6000;
        int destport = 80;
        DatagramSocket ds = new DatagramSocket(srcport);
        byte[] buf = new byte[1024];
        String msg = "hiiiiiiiiiiiiiiiii";
        buf = msg.getBytes();
         InetAddress inetaddressDest = null;
        //inetaddress1 = InetAddress.getByAddress(abyte1);
         String destAddress = "127.0.0.1";
         inetaddressDest = InetAddress.getByName(destAddress);
       
        inetaddressDest = InetAddress.getByName(destAddress);
        DatagramPacket dp = new DatagramPacket(buf, buf.length, inetaddressDest, destport);
        System.out.println("sending to packet to host"+destAddress);
        for(int i = 0; i<10000; i++){
            System.out.println("sending"+i);
                ds.send(dp);
        }
        
        
    
    }
    
}
