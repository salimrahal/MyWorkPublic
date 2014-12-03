/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.bo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author salim
 */
public class EchoServerDatagram implements Runnable{

    DatagramSocket socket = null;
    InetAddress address;
    String registerKey = "REGISTER";
    String inviteKey = "INVITE";
    Integer poolsize = 30 * Runtime.getRuntime().availableProcessors();


    public EchoServerDatagram(String localIp, Integer port) throws SocketException, UnknownHostException {
        address = InetAddress.getByName(localIp);
        socket = new DatagramSocket(port, address);
        System.out.println("Sip Echo DatagramServer: listening on port " + port + "/ Ip " + localIp+" / use a cached pool");   
    }
    
       @Override
    public void run() {
        try {
            processrequests();
        } catch (SocketException | UnknownHostException ex) {
            System.out.println("Sip DatagramServer: Error: couldn't run the serverUdp:"+ex.getLocalizedMessage()); 
        }
    }

    public void processrequests() throws UnknownHostException, SocketException {
        int i = 0;
        String subStr;
        ExecutorService poolservice = Executors.newCachedThreadPool();
        System.out.println("Sip DatagramServer(UDP Server) starts..");
        byte[] buf = new byte[1024];//65536
        try {

            System.out.println("Sip DatagramServer: waiting to receive packets");
            while (true) {

                DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
                // receive request
                socket.receive(incomingPacket);
                //DatagramPacket incomingPacketTmp = incomingPacket;
                String recvMsg = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
                System.out.println("["+new Date()+"]\n received packet clientID:" + i + "\n" + recvMsg);
                subStr = recvMsg.substring(0, 8);
                if (subStr.contains(registerKey)||subStr.contains(inviteKey)) {
                    InetAddress addressInco = incomingPacket.getAddress();
                    int portInco = incomingPacket.getPort();
                    
              
                    ClientDatagramConnection clientConn = new ClientDatagramConnection(socket, recvMsg, addressInco, portInco, i);

                    poolservice.execute(clientConn);
                    i++;
                } else {
                    System.out.println("Sip DatagramServer:unknown client, disregard the packet");
                }

            }
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
