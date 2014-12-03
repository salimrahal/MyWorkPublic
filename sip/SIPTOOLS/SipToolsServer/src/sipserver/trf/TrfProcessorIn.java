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
import static sipserver.trf.TrfBo.AcknumTrfIn;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim
 */
public class TrfProcessorIn {

    DatagramSocket socketDgIn = null;
    String trfkey;
    Integer portIn;

    public TrfProcessorIn(DatagramSocket socketDgIn, Integer portIn, String trfkey) {
        
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
       
        System.out.println("[" + new Date() + "] TrfProcessorIn: starts..\n waiting for packet:excpected trf key=" + trfkey);
        try {
            System.out.println("[" + new Date() + "] TrfProcessorIn:waiting for packet..timeout:" + sockTimeout + " sec");
            socketDgIn.setSoTimeout(sockTimeout);
            socketDgIn.receive(incomingPacket);
            String keyreceived = new String(incomingPacket.getData());
            System.out.println("TrfProcessorIn: keyreceived=" + keyreceived);
            if (keyreceived.contains(trfkey)) {
                System.out.println("TrfProcessorIn:receiving: Accepted, sending back "+AcknumTrfIn+":" + TrfBo.ACK_TRFIN + ".Starting the trafficIn test");
                addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                outgoingPacket = new DatagramPacket(bufOut, bufOut.length, addressInco, portInco);
                 for (int j = 1; j <= AcknumTrfIn; j++) {
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
    }

   
    public void launchTrafficPktLossIn(DatagramSocket dgmsocketIn, Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {

        int portsrcInChannel = Integer.valueOf(param.getPortrfClientU());
        int portdestInChannel = Integer.valueOf(param.getPortrfClientU());
        TrfDgmRunnableIn trfDgmRunnableIn = new TrfDgmRunnableIn(dgmsocketIn, param, inetaddressDest, portsrcInChannel, portdestInChannel);
        Thread trfDgmThreadIn = new Thread(trfDgmRunnableIn);
        trfDgmThreadIn.start();
        System.out.println("TrfProcessorIn:launchTrafficPktLossIn waiting to finish the TrfDgmRunnableIn thread");
        trfDgmThreadIn.join();
    }


}
