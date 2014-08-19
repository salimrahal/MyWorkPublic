/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.bo.trf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class ClientTrfDgmConnection implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket socket;
    //private DatagramPacket incomingPacket;
    String recvMsg;
    InetAddress address;
    Integer port;

    public ClientTrfDgmConnection(DatagramSocket socket, String recvMsg, InetAddress address, Integer port, int clientID) throws IOException {
        this.socket = socket;
        //this.incomingPacket = incomingPacket;
        this.clientID = clientID;
        this.recvMsg = recvMsg;
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        sendbackpacket();
    }

    private synchronized void sendbackpacket() {
        DatagramPacket incomingPacketLocal;
        String threadName = Thread.currentThread().getName();
        System.out.println("threadName ["
                + threadName + "] is going to handle packet num " + clientID);
        byte[] buf;
        try {

            //System.out.println("["+ threadName + "] received packet clientID:" + clientID + "\n" + recvMsg);
            buf = recvMsg.getBytes();

            incomingPacketLocal = new DatagramPacket(buf, buf.length, address, port);
           // retreive the rtppkt identifiers:
           
            //send the packet back to the client
            socket.send(incomingPacketLocal);
            System.out.println("[" + new Date() + "]\n - [" + threadName + "] packet: clientID:" + clientID + " is sent.");

        } catch (IOException ex) {
            Logger.getLogger(ClientTrfDgmConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
