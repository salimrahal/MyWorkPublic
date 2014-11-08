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
public class TrfProcessorOut {

    private DatagramSocket dgmsocketOut;
    String trfkey;
    Integer port;

    public TrfProcessorOut(DatagramSocket dgmsocketOut, Integer portOut, String trfkey) {
        //serverSocket = new ServerSocket(port);
        this.dgmsocketOut = dgmsocketOut;
        this.port = portOut;
        this.trfkey = trfkey;
    }

    public void processTest(Param param) throws IOException {
        int sockTimeout = TrfBo.S_S_T;
        InetAddress addressInco;
        byte[] buf = new byte[256];
        byte[] bufOut = TrfBo.ACK_TRFOUT.getBytes();
        DatagramPacket outgoingPacket;
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);

        System.out.println("[" + new Date() + "] TrfProcessorOut: starts..\n waiting for packet:excpected trf key=" + trfkey);
        try {
            System.out.println("[" + new Date() + "] TrfProcessorOut:waiting for packet..timeout:" + sockTimeout + " sec");
            dgmsocketOut.setSoTimeout(sockTimeout);
            dgmsocketOut.receive(incomingPacket);
            String keyreceived = new String(incomingPacket.getData());
            System.out.println("TrfProcessorOut: keyreceived=" + keyreceived);
            if (keyreceived.contains(trfkey)) {
                System.out.println("TrfProcessorOut:receiving: Accepted, sending back the " + ACK + ".Starting the trafficOut test");
                //send two ACK to ensure receiving the ACK
                addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                outgoingPacket = new DatagramPacket(bufOut, bufOut.length, addressInco, portInco);
                dgmsocketOut.send(outgoingPacket);
                dgmsocketOut.send(outgoingPacket);
                try {
                    // launch the traffIn thread
                    launchTrafficOut(dgmsocketOut, param, addressInco);
                } catch (UnknownHostException | InterruptedException ex) {
                    Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("TrfProcessorOut:ERROR keyreceived=" + keyreceived + "/excpected:" + trfkey);
            }

        } catch (SocketTimeoutException se) {
            System.out.println("TrfProcessorOut::" + se.getMessage());
        } catch (IOException e) {
            System.out.println("TrfProcessorOut: Accept failed.");
        }
    }//end of process request of IN

    /*
     launchTrafficPktLoss():
     launch the listening Dgms socket which listen on portTrf
     start receiving paquets and computes paquet loss
     sending paquets
     */
    public void launchTrafficOut(DatagramSocket dgmsocketOut, Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {
//run the thread that sends the traffic
        int portsrcOutChannel = Integer.valueOf(param.getPortrfClientD());
        int portdestOutChannel = Integer.valueOf(param.getPortrfClientD());
        TrfDgmRunnableOut trfDgmRunnableOut = new TrfDgmRunnableOut(dgmsocketOut, param, inetaddressDest, portsrcOutChannel, portdestOutChannel);
        Thread trfDgmThreadOut = new Thread(trfDgmRunnableOut);
        trfDgmThreadOut.start();
        System.out.println("TrfProcessorout:launchTrafficOut waiting to finish the TrfDgmRunnableOut thread");
        trfDgmThreadOut.join();
    }
//    public void processTest(Param param) throws IOException {
//        PrintWriter out;
//        BufferedReader in;
//        int serverSockTimeout = TrfBo.S_S_T;
//        System.out.println("TrfProcessorout: starts..\n waiting for connections trf key=" + trfkey);
//        try {
//            //wait for connection for a given time then it fires a timeout exception
//            System.out.println("TrfProcessorout:waiting for accept..timeout:" + serverSockTimeout + " sec");
//            serverSocket.setSoTimeout(serverSockTimeout);
//            Socket clientSocket = serverSocket.accept();
//            out = new PrintWriter(clientSocket.getOutputStream(),
//                    true);
//            in = new BufferedReader(
//                    new InputStreamReader(clientSocket.getInputStream()));
//            String inputLine;
//            boolean firstLine = true;
//            System.out.println("TrfProcessorout:[port=" + this.serverSocket.getLocalPort() + "] Connection successful\n");
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("TrfProcessorout: inputLine=" + inputLine);
//                if (firstLine) {
//                    System.out.println("TrfProcessorout:receiving:" + inputLine);
//                    if (inputLine.contains(trfkey)) {
//                        System.out.println("TrfProcessorout: Accepted, sending back the " + ACK + ".Starting the trafficOut");
//                        out.write(ACK);
//                        InetAddress inetaddressDest = clientSocket.getInetAddress();
//                        TrfBo.closeRess(clientSocket, out, in);
//                        try {
//                            // launch the traffIn thread
//                            launchTrafficOut(param, inetaddressDest);
//                        } catch (UnknownHostException ex) {
//                            Logger.getLogger(TrfProcessorOut.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(TrfProcessorOut.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    break;
//                }
//                firstLine = false;
//            }
//        } catch (SocketTimeoutException se) {
//            System.out.println("TrfProcessorout::" + se.getMessage());
//        } catch (IOException e) {
//            System.err.println("TrfProcessorout: Accept failed.");
//        } finally {
//            if (serverSocket != null) {
//                serverSocket.close();
//            }
//        }//end of while true
//    }//end of process request of IN
}
