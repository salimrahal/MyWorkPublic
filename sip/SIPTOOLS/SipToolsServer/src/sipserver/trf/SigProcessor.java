/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author salim ServerTcp : Sig TCP Server
 */
public class SigProcessor implements Runnable {

    //ServerSocket serverSocket = null;
    DatagramSocket socket = null;
    InetAddress address;
    //Remote server 1 CPU::: Sip ServerTcp: listening on port 5060 / poolsize=20
    //Integer poolsize = 20 * Runtime.getRuntime().availableProcessors();// 

    //insert the constructor
    public SigProcessor(String localIp, Integer port) {
        try {
            address = InetAddress.getByName(localIp);
            socket = new DatagramSocket(port, address);
            System.out.println("Traffic UDPServer: listening on port " + port + "/ Ip " + localIp + " / use a cached pool");
            System.out.println("[" + new Date() + "] Traffic UDPServer: listening on port " + port + " /using cachedThreadPoo");
        } catch (IOException e) {
            System.err.println("Traffic UDPServer:: Could not listen on port:" + port);
            System.exit(1);
        }
    }

    public void processrequests() throws IOException {
        int i = 0;
        ExecutorService poolservice = Executors.newCachedThreadPool();
        System.out.println("Traffic UDPServer: starts..");
        //buffer to receive incoming data
        byte[] buf = new byte[1024];//65536
        try {

            System.out.println("Traffic UDPServer: waiting to receive packets");
            //communication loop
            while (true) {
                //create udp packet
                DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
                // receive request
                socket.receive(incomingPacket);
                //DatagramPacket incomingPacketTmp = incomingPacket;
                String recvMsg = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
                System.out.println("Traffic UDPServer: [" + new Date() + "]\n received packet clientID:" + i + "\n" + recvMsg);
                InetAddress addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                //create the thread(Runnable) that sends the message                  
                ClientSignUdpConnection clientConn = new ClientSignUdpConnection(socket, recvMsg, addressInco, portInco, i);
                //and this task to a pool, so clientConnection thread will be started
                poolservice.execute(clientConn);
                i++;
            }//end of while
        }//end try
        catch (IOException e) {
            System.out.println("Traffic UDPServer:Error:" + e.getLocalizedMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }//end of process requests

    @Override
    public void run() {
        try {
            processrequests();
        } catch (IOException ex) {
            System.out.println("Traffic UDPServer: Error: couldn't be run:" + ex.getLocalizedMessage());
        }
    }
}
