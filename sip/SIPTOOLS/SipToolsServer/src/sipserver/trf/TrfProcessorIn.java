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
 * @author salim TrfProcessorIn : processing the In traffic
 */
public class TrfProcessorIn {

    DatagramSocket socketDgIn = null;
    String trfkey;
    Integer portIn;

    public TrfProcessorIn(DatagramSocket socketDgIn, Integer portIn, String trfkey) {
        //serverSocket = new ServerSocket(port);
        this.socketDgIn = socketDgIn;
        this.trfkey = trfkey;
        this.portIn = portIn;
    }

    public void processTest(Param param) throws IOException {
        int sockTimeout = TrfBo.S_S_T;
        InetAddress addressInco;
        byte[] buf = new byte[256];
        byte[] bufOut = TrfBo.ACK_TRFIN.getBytes();
        DatagramPacket outgoingPacket;
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
        int inAcknum = 2;
        System.out.println("[" + new Date() + "] TrfProcessorIn: starts..\n waiting for packet:excpected trf key=" + trfkey);
        try {
            System.out.println("[" + new Date() + "] TrfProcessorIn:waiting for packet..timeout:" + sockTimeout + " sec");
            socketDgIn.setSoTimeout(sockTimeout);
            socketDgIn.receive(incomingPacket);
            String keyreceived = new String(incomingPacket.getData());
            System.out.println("TrfProcessorIn: keyreceived=" + keyreceived);
            if (keyreceived.contains(trfkey)) {
                System.out.println("TrfProcessorIn:receiving: Accepted, sending back "+inAcknum+":" + TrfBo.ACK_TRFIN + ".Starting the trafficIn test");
                addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                outgoingPacket = new DatagramPacket(bufOut, bufOut.length, addressInco, portInco);
//send two ACK to ensure receiving the ACK
                 for (int j = 1; j <= inAcknum; j++) {
                   socketDgIn.send(outgoingPacket);
                }
                try {
                    // launch the traffIn thread
                    launchTrafficPktLossIn(socketDgIn, param, addressInco);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("TrfProcessorIn:ERROR keyreceived=" + keyreceived + "/excpected:" + trfkey);
            }

        } catch (SocketTimeoutException se) {
            System.out.println("TrfProcessorIn::" + se.getMessage());
        } catch (IOException e) {
            System.out.println("TrfProcessorIn: Accept failed.");
        }
    }//end of process request of IN

    /*
     launchTrafficPktLoss():
     launch the listening Dgms socket which listen on portTrf
     start receiving paquets and computes paquet loss
     sending paquets
     */
    public void launchTrafficPktLossIn(DatagramSocket dgmsocketIn, Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {

        //run the thread that sends the traffic
        int portsrcInChannel = Integer.valueOf(param.getPortrfClientU());
        int portdestInChannel = Integer.valueOf(param.getPortrfClientU());
        //run the thread that receives the traffic and computes the pktloss up
        TrfDgmRunnableIn trfDgmRunnableIn = new TrfDgmRunnableIn(dgmsocketIn, param, inetaddressDest, portsrcInChannel, portdestInChannel);
        Thread trfDgmThreadIn = new Thread(trfDgmRunnableIn);
        trfDgmThreadIn.start();
        System.out.println("TrfProcessorIn:launchTrafficPktLossIn waiting to finish the TrfDgmRunnableIn thread");
        trfDgmThreadIn.join();
    }

//    public void processTest(Param param) throws IOException {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        int serverSockTimeout = TrfBo.S_S_T;
//        System.out.println("[" + new Date() + "] TrfProcessorIn: starts..\n waiting for connections trf key=" + trfkey);
//        try {
//            //wait for connection for a given time then it fires a timeout exception
//            System.out.println("[" + new Date() + "] TrfProcessorIn:waiting for accept..timeout:" + serverSockTimeout + " sec");
//            serverSocket.setSoTimeout(serverSockTimeout);
//            Socket clientSocket = serverSocket.accept();
//            System.out.println("TrfProcessorIn:connection accepted");
//            out = new PrintWriter(clientSocket.getOutputStream(),
//                    true);
//            in = new BufferedReader(
//                    new InputStreamReader(clientSocket.getInputStream()));
//            String inputLine;
//            boolean firstLine = true;
//            System.out.println("TrfProcessorIn:[port=" + this.serverSocket.getLocalPort() + "] Connection successful\n");
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("[" + new Date() + "] TrfProcessorIn: inputLine=" + inputLine);
//                if (firstLine) {
//                    if (inputLine.contains(trfkey)) {
//                        System.out.println("TrfProcessorIn:receiving:" + inputLine);
//                        System.out.println("TrfProcessorIn:receiving: Accepted, sending back the " + ACK + ".Starting the trafficIn");
//                        out.write(ACK);
//                        InetAddress inetaddressDest = clientSocket.getInetAddress();
//                        TrfBo.closeRess(clientSocket, out, in);
//                        try {
//                            // launch the traffIn thread
//                            launchTrafficPktLossIn(param, inetaddressDest);
//                        } catch (UnknownHostException ex) {
//                            Logger.getLogger(TrfProcessorIn.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(TrfProcessorIn.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    break;
//                }
//                firstLine = false;
//            }
//        } catch (SocketTimeoutException se) {
//            System.out.println("TrfProcessorIn::" + se.getMessage());
//        } catch (IOException e) {
//            System.err.println("TrfProcessorIn: Accept failed.");
//        } finally {
//            if (serverSocket != null) {
//                serverSocket.close();
//            }
//        }//end of while true
//    }//end of process request of IN
}
