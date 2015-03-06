/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.ntp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author salim
 */
public class NtpMain {

    // Send message
    public static void main(String[] args) throws UnknownHostException, IOException {
        /*
        //ntp.cais.rnp.br"; no reply
        */
        String ntpServer = "rps.yealink.com";//"rps.yealink.com";//fm.grandstream.com//

        ntptest(ntpServer);
    }

    public static void ntptest(String ntpServer) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] incomingbuf = new byte[256];
        InetAddress address = InetAddress.getByName(ntpServer);
        NtpMessage ntpClient = new NtpMessage();
        byte[] buf = ntpClient.toByteArray();
        System.out.println("ntpClient["+ntpClient.toString()+"]");
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
        socket.send(packet);
        // Get response
        DatagramPacket incomingPacket = new DatagramPacket(incomingbuf, incomingbuf.length);
        socket.receive(incomingPacket);
        String msgRecv = new String(incomingPacket.getData());
        System.out.println("Received:"+msgRecv);
        //System.out.println(msg.toString());
    }

}
