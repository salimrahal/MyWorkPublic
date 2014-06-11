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
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    Integer poolsize = 30 * Runtime.getRuntime().availableProcessors();

    public ServerDatagram(String localIp, Integer port) throws SocketException, UnknownHostException {
        address = InetAddress.getByName(localIp);
        System.out.println("Sip DatagramServer: listening on port " + port + "/ Ip " + localIp+" / poolsize="+poolsize);
        socket = new DatagramSocket(port, address);
    }

    public void processrequests() throws UnknownHostException, SocketException {
        int i = 0;
        String subStr;
        ExecutorService poolservice = Executors.newFixedThreadPool(poolsize);
        System.out.println("Sip DatagramServer(UDP Server) starts..");
        //buffer to receive incoming data
        byte[] buf = new byte[1024];//65536
        try {

            System.out.println("Sip DatagramServer: waiting to receive packets");
            //communication loop
            while (true) {
                //create udp packet
                DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
                // receive request    
                socket.receive(incomingPacket);
                //DatagramPacket incomingPacketTmp = incomingPacket;
                String recvMsg = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
                System.out.println("["+new Date()+"]\n received packet clientID:" + i + "\n" + recvMsg);
                //check the message type to ensure it's: register or invite, drop the sip spam
                subStr = recvMsg.substring(0, 8);
                //System.out.println("subStr" + subStr);

                if (subStr.contains(registerKey) || subStr.contains(inviteKey)) {
                    // send the response to the client at "address" and "port"
                    InetAddress addressInco = incomingPacket.getAddress();
                    int portInco = incomingPacket.getPort();
                    
                    //create the thread(Runnable) that sends the message                  
                    ClientDatagramConnection clientConn = new ClientDatagramConnection(socket, recvMsg, addressInco, portInco, i);
                    //and this task to a pool, so clientConnection thread will be started
                    poolservice.execute(clientConn);
                    i++;
                } else {
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
