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
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sipserver.trf.TrfBo.ACK;
import static sipserver.trf.TrfBo.TST_ID_KEY;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;

/**
 *
 * @author salim for signalization: receiving params lauching the sockets for
 * traffic and latency handle the test for server side
 *
 */
public class ClientSignTcpConnection implements Runnable {

    Socket clientSocket;
    private Integer clientID;
    TrfBo trbo;
    TrfDao trfdao;
    ExecutorService executor;

    public ClientSignTcpConnection(Socket clientSocket, Integer clientID) {
        this.clientSocket = clientSocket;
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
            Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
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
        PrintWriter out = null;
        BufferedReader in = null;
        int porttrfClientup;
        int porttrfClientdown;
        int portlat;
        int[] ports = new int[3];//an array of used ports
        boolean releaseallPorts = false;
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println("ClientSignTcpConnection: threadName ["
                    + threadName + "]clientSocket listening on:" + clientSocket.getPort() + " is going to handle TCP connection num " + clientID + ". Waiting to inputs..");
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            int i = 0;
            boolean firstLine = true;
            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    if (inputLine.contains(TST_ID_KEY)) {
                        recognizedClient = true;
                        System.out.println("ClientSignTcpConnection:receiving:" + inputLine);
                        //extract the parameters from the client and save them to bean 
                        Param param = trbo.savingParamsTobean(inputLine, clientSocket.getInetAddress().getHostAddress());
                        porttrfClientup = Integer.valueOf(param.getPortrfClientU());
                        porttrfClientdown = Integer.valueOf(param.getPortrfClientD());
                        portlat = Integer.valueOf(param.getPortlat());
                        ports[0] = porttrfClientup;
                        ports[1] = portlat;
                        ports[2] = porttrfClientdown;
                        int portUnique = portlat;
                        //update the port status in DB f->b
                        boolean portReserved = trfdao.updatePortStatus(ports, "b");
                        if (portReserved) {
                            //send ACK to client, means: server is ready and listening on his udp points(traffic, latency)
                            System.out.println("ClientSignTcpConnection:sending the ACK..");
                            out.write(ACK);
                            TrfBo.closeRess(clientSocket, out, in);
                            System.out.println("ClientSignTcpConnection:ACK is sent");
                            //record the test
                            trfdao.createNewTest(param.getTstid(), param.getCustname(), param.getClientIp(), param.getCodec(), param.getTimelength());
                            //lauching latency test
                            System.out.println("ClientSignTcpConnection: phase-1:begin: latency Up test");
                            LatProcessor processorLat = new LatProcessor(portlat, TrfBo.LAT_KEY);
                            processorLat.processTest(param);
                            System.out.println("ClientSignTcpConnection: phase-1:End: latency Up test");
                            //launch receive thread
                            System.out.println("ClientSignTcpConnection: phase-2:begin: trf In in test");
                            TrfProcessorIn processorIn = new TrfProcessorIn(porttrfClientup, TrfBo.REQ_IN_KEY);
                            processorIn.processTest(param);
                            System.out.println("ClientSignTcpConnection: phase-2:end:  trf In in test");
//                            launchTrafficPktLossIn(param);
                            //launch send thread
                                System.out.println("ClientSignTcpConnection: phase-3:begin: trf out test");
                            TrfProcessorOut processorOut = new TrfProcessorOut(porttrfClientdown, TrfBo.REQ_OUT_KEY);
                            processorOut.processTest(param);
                            System.out.println("ClientSignTcpConnection: phase-3:end: trf out test");
                            //release all ports in DB
                            releaseallPorts = trfdao.updatePortStatus(ports, "f");
                            System.out.println("ClientSignTcpConnection: releasing the ports=" + releaseallPorts);
                        }
                        break;
                    }
                    firstLine = false;
                }
                i++;
            }//end of while    
            System.out.println("traffic ServerTcp: reading/writing message is finished . The loop is ended on line number:" + i);
            /*run the processor:
             1- launch datagram threads for Port_trf and Port_latency
             2- handle call sequence by using Callable of futur interface
             */
        } catch (IOException ex) {
            Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            TrfBo.closeRess(clientSocket, out, in);
            if (!releaseallPorts) {
                trfdao.updatePortStatus(ports, "f");
                System.out.println("ClientSignTcpConnection:Finally clause:releasing the ports=" + releaseallPorts);
            }
        }
        if (recognizedClient) {
            System.out.println("traffic ServerTcp: [" + new Date() + "]\n - [" + threadName + "] : clientID:" + clientID + ". Connection to client is closed.");
        }
    }
}
