/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sipserver.trf.TrfBo.ACK;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim LatProcessor : processing the In traffic
 */
public class LatProcessor {

    DatagramSocket socketDglat = null;
    String latkey;
    Integer portLat;

    public LatProcessor(DatagramSocket socketDglat, Integer portLat, String latkey) {
        //serverSocket = new ServerSocket(port);
        this.socketDglat = socketDglat;
        System.out.println("[" + new Date() + "] LatProcessor: listening on port " + portLat);
        this.latkey = latkey;
        this.portLat = portLat;
    }

    public void processTest(Param param) throws IOException {
        int sockTimeout = TrfBo.S_S_T;
        InetAddress addressInco = null;
        byte[] buf = new byte[256];
        byte[] bufOut = TrfBo.ACK_LAT.getBytes();
        DatagramPacket outgoingPacket = null;
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);

        System.out.println("[" + new Date() + "] LatProcessor: starts..\n waiting for packet:excpected trf key=" + latkey);
        try {
            System.out.println("[" + new Date() + "] LatProcessor:waiting for packet..timeout:" + sockTimeout + " sec");
            socketDglat.setSoTimeout(sockTimeout);
            socketDglat.receive(incomingPacket);
            String keyreceived = new String(incomingPacket.getData());
            System.out.println("LatProcessor: keyreceived=" + keyreceived);
            if (keyreceived.contains(latkey)) {
                System.out.println("LatProcessor:receiving: Accepted, sending back the " + ACK + ".Starting the LAtency test");
                addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                outgoingPacket = new DatagramPacket(bufOut, bufOut.length, addressInco, portInco);
                //send two ACK to ensure receiving the ACK
                socketDglat.send(outgoingPacket);
                socketDglat.send(outgoingPacket);
                System.out.println("LatProcessor:finish sending back the " + ACK + ".to"+addressInco.getHostAddress()+":"+portInco);
                try {
                    // launch the traffIn thread
                    launchLatUp(socketDglat, param, addressInco);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (SocketTimeoutException se) {
            System.out.println("LatProcessor::" + se.getMessage());
        } catch (IOException e) {
            System.out.println("LatProcessor: Accept failed.");
        }
    }//end of process request of IN

    /*
     launch latency test thread
     */
    public void launchLatUp(DatagramSocket socketDglat, Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {
        int portsrc = Integer.valueOf(param.getPortlat());
        int portdest = Integer.valueOf(param.getPortlat());
        LatRunnable lat = new LatRunnable(socketDglat, param, inetaddressDest, portsrc, portdest, 0);
        Thread latTh = new Thread(lat);
        latTh.start();
        System.out.println("[" + new Date() + "] LatProcessor:launchLatUp waiting to finish the launchLatUp..");
        latTh.join();
    }

}
