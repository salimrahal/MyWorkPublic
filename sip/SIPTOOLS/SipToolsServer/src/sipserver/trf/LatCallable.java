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
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.vo.LatVo;

/**
 *
 * @author salim
 *  1-  echo back n packet to client
 *  2- Compute latency Up: send n packets, receives n packet, computes lat and jitter Up
 *
 */
public class LatCallable implements Callable<LatVo> {
        private int clientID;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;
    
       public LatCallable(Param param, InetAddress addressDest, int portsrc, int portDest, int clientID) throws IOException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portDest;
        dgmsocket = new DatagramSocket(this.portsrc);

    }
       
         private synchronized void handleClienttraffic() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        /*
         1- receive the flag packet from the client
         2- extract the addressInco and portInco
         3- pass them to the sendpacket function
         */
        byte[] buf = new byte[8];
        DatagramPacket incomingPacketLocal = new DatagramPacket(buf, buf.length);
        System.out.println("TrfDgmRunnableOut:handleClienttraffic:: waiting for flag Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
        try {
            dgmsocket.setSoTimeout(TrfBo.Packet_Max_Delay);
            dgmsocket.receive(incomingPacketLocal);
            System.out.println("TrfDgmRunnableOut:handleClienttraffic:receives a flag packet ");
            InetAddress inetAddrInco = incomingPacketLocal.getAddress();
            int portInco = incomingPacketLocal.getPort();
            DatagramPacket outgoingPacketLocal = new DatagramPacket(buf, buf.length, inetAddrInco, portInco);
            System.out.println("TrfDgmRunnableOut:handleClienttraffic:sending back the flag packet");
            dgmsocket.send(outgoingPacketLocal);
            //sendingPkts(codec, timelength, addressDest, portDest);
        } catch (SocketTimeoutException se) {
            System.out.println("TrfDgmRunnableOut::Error:receiving flag::" + se.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public LatVo call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
