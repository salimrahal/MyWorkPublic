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
        int srcport = 5108;
        DatagramSocket ds = new DatagramSocket(srcport);
        byte[] buf = new byte[1024];
        DatagramPacket pd = new DatagramPacket(buf, buf.length);
        boolean morepkt = true;
        int i = 0;
        while (morepkt) {
            System.out.println("waiting to receive....portsrc=" + ds.getPort());
            ds.receive(pd);
            i++;
            System.out.println("count="+i+" received data=" + Arrays.toString(pd.getData()));
            if (i == 1000000) {
                morepkt = false;
            }
            System.out.println("finish receiving.");

        }
    }
}
