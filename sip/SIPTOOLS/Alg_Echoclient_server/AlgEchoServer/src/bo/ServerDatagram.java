/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class ServerDatagram {

    DatagramSocket socket = null;
    BufferedReader in = null;
    InetAddress address;
    String registerKey = "REGISTER";
      String inviteKey = "INVITE";
    
    public ServerDatagram(String localIp, Integer port) throws SocketException, UnknownHostException {
        address = InetAddress.getByName(localIp);
        System.out.println("Sip DatagramServer: listening on port " + port + "/ Ip " + localIp);
        socket = new DatagramSocket(port, address);
    }

    public void processrequests() throws UnknownHostException, SocketException {
        int i = 0;
        String subStr ;
        System.out.println("Sip DatagramServer: run begins");
        //buffer to receive incoming data
        byte[] buf = new byte[1024];//65536
        try {
            DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
            System.out.println("Sip DatagramServer: waiting to receive packets");

            //communication loop
            while (true) {
                // receive request    
                socket.receive(incomingPacket);
                //DatagramPacket incomingPacketTmp = incomingPacket;
                String recvMsg = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
                System.out.println("received packet clientID:" + i + "\n" + recvMsg);
                subStr = recvMsg.substring(0, 8);
                System.out.println("subStr"+subStr);
                
                if(subStr.contains(registerKey)||subStr.contains(inviteKey)){
                       // send the response to the client at "address" and "port"
                InetAddress addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                
                ClientConnection clientConn = new ClientConnection(socket, recvMsg, addressInco, portInco, i);
                Thread clientThread = new Thread(clientConn);
                clientThread.setName("Thread name ID: " + i);
                clientThread.start();
                i++;
                }else{
                     System.out.println("Sip DatagramServer:unknown client, disregard the packet");
                }
             
            }//end of while
        }//end try
        catch (IOException e) {
            System.out.println("Error:" + e.getLocalizedMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
