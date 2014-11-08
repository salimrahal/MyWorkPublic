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
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sipserver.trf.TrfBo.TST_ID_KEY;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;

/**
 *
 * @author salim for signalization: receiving params lauching the sockets for
 * traffic and latency handle the test for server side
 *
 */
public class ClientSignUdpConnection implements Runnable {

    Integer portInco;
    DatagramSocket socket;
    private Integer clientID;
    InetAddress inetAddressInco;
    String recvMsg;
    TrfBo trbo;
    TrfDao trfdao;
    ExecutorService executor;

    public ClientSignUdpConnection(DatagramSocket socket, String recvMsg, InetAddress inetAddressInco, Integer portInco, Integer clientID) {
        this.socket = socket;
        this.recvMsg = recvMsg;
        this.inetAddressInco = inetAddressInco;
        this.portInco = portInco;
        this.clientID = clientID;
        trbo = new TrfBo();
        trfdao = new TrfDao();
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            processconnection();
        } catch (Exception ex) {
            Logger.getLogger(ClientSignUdpConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * processconnection:: - it receives params from the server - it launches
     * the traffic and latency threads
     *
     * @throws Exception
     */
    private synchronized void processconnection() throws Exception {
        boolean recognizedClient = true;
        int porttrfClientup;
        int porttrfClientdown;
        int portlat;
        int[] ports = new int[3];//an array of used ports
        boolean releaseallPorts = false;
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println("ClientSignUdpConnection: threadName ["
                    + threadName + "]" + clientID + ". Waiting to inputs..");

            int i = 0;
            System.out.println("ClientSignUdpConnection:receiving:" + recvMsg);
            if (recvMsg.contains(TST_ID_KEY)) {
                System.out.println("ClientSignUdpConnection:yes it contains testId. sending the ACK..");
                //send ACK to client, means: server is ready and listening on his udp points(traffic, latency)
                byte[] bufAck = TrfBo.ACK_START.getBytes();
                DatagramPacket outgoingpacket = new DatagramPacket(bufAck, bufAck.length, inetAddressInco, portInco);
                //sedning 3 times the ACK to overcome the packet loss
                socket.send(outgoingpacket);
                socket.send(outgoingpacket);
                
                System.out.println("ClientSignUdpConnection: ACK is sent");
                recognizedClient = true;
                //extract the parameters from the client and save them to bean 
                Param param = trbo.savingParamsTobean(recvMsg, inetAddressInco.getHostAddress());
                //todo: working on one port 5092,
                porttrfClientup = Integer.valueOf(param.getPortrfClientU());
                porttrfClientdown = Integer.valueOf(param.getPortrfClientD());
                portlat = Integer.valueOf(param.getPortlat());
                ports[0] = porttrfClientup;
                ports[1] = portlat;
                ports[2] = porttrfClientdown;
                //update the port status in DB f->b
                //todo: update only the port 5092 to busy
                boolean portReserved = trfdao.updatePortStatus(ports, "b");
                if (portReserved) {
                    //TODO: write UDP socket instead
                    //create Udp socket to listen on
                    DatagramSocket socketDglat = new DatagramSocket(portlat);
                    DatagramSocket socketDgIn = new DatagramSocket(porttrfClientup);
                    DatagramSocket socketDgOut = new DatagramSocket(porttrfClientdown);
                    try {

                        //record the test
                        trfdao.createNewTest(param.getTstid(), param.getCustname(), param.getClientIp(), param.getCodec(), param.getTimelength());
                        //lauching latency test
                        System.out.println("[" + new Date() + "] ClientSignUdpConnection: phase-1:begin: latency Up test");
                        LatProcessor processorLat = new LatProcessor(socketDglat, portlat, TrfBo.LAT_KEY);
                        processorLat.processTest(param);
                        System.out.println("[" + new Date() + "] ClientSignUdpConnection: phase-1:End: latency Up test");
                        //launch receive thread
                        System.out.println("[" + new Date() + "] ClientSignUdpConnection: phase-2:begin: trf In in test");
                        TrfProcessorIn processorIn = new TrfProcessorIn(socketDgIn, porttrfClientup, TrfBo.REQ_IN_KEY);
                        processorIn.processTest(param);
                        System.out.println("[" + new Date() + "] ClientSignTcpConnection: phase-2:end:  trf In in test");
//                            launchTrafficPktLossIn(param);
                        //launch send thread
                        System.out.println("[" + new Date() + "] ClientSignUdpConnection: phase-3:begin: trf out test");
                        TrfProcessorOut processorOut = new TrfProcessorOut(socketDgOut, porttrfClientdown, TrfBo.REQ_OUT_KEY);
                        processorOut.processTest(param);
                        System.out.println("[" + new Date() + "] ClientSignUdpConnection: phase-3:end: trf out test");
                    } catch (IOException iOException) {
                        System.out.println("ClientSignUdpConnection: iOException=" + iOException.getMessage());
                    } finally {
                        //re-check  if not closed then close opened server sockets
                        TrfBo.closesockDm(socketDglat, socketDgIn, socketDgOut);
                        //release all ports in DB
                        releaseallPorts = trfdao.updatePortStatus(ports, "f");
                        System.out.println("[" + new Date() + "] ClientSignUdpConnection:finally: releasing the ports=" + releaseallPorts);

                    }
                }//end if portserved
                        /*todo: the server reply with busy: out.write(BUSY+test length);
                 so the client will wait testlength*2 then he retry*/
            }//end clause if key true
            System.out.println("ClientSignUdpConnection: reading/writing message is finished . The loop is ended on line number:" + i);         
        } catch (IOException ex) {
            Logger.getLogger(ClientSignUdpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {             
            if (!releaseallPorts) {
                trfdao.updatePortStatus(ports, "f");
                System.out.println("ClientSignUdpConnection:Finally clause:releasing the ports=" + releaseallPorts);
            }
        }
        if (recognizedClient) {
            System.out.println("ClientSignUdpConnection: [" + new Date() + "]\n - [" + threadName + "] : clientID:" + clientID + ". Connection to client is closed.");
        }
    }
}
