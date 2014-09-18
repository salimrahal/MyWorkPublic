/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import bean.Param;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.bo.VpMethds;
import vp.vo.CdcVo;

/**
 *
 * @author salim handling traffic a- responsible for receiving and counting the
 * packet lost b- save the result into the DB: client name, clientpublicIP,
 * uppacketLost, starttime c- release the used for: update Ports table and set
 * the used port to free
 */
public class TrfDgmRunnableD implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer port;
    Param param;

    public TrfDgmRunnableD(Param param, InetAddress addressDest, int clientID) throws IOException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.port = Integer.valueOf(param.getPortrfD());
        //for test
         String destAddress = "127.0.0.1";
         //for test
         InetAddress inetaddressDest = InetAddress.getByName(destAddress);
        //dgmsocket = new DatagramSocket(this.port, inetaddressDest);
         dgmsocket = new DatagramSocket(this.port);
    }

    @Override
    public void run() {
        try {
            handleClienttraffic();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void handleClienttraffic() throws IOException, InterruptedException {
        String codec = param.getCodec();
        float packetlostdown;
        int timelength = Integer.valueOf(param.getTimelength());
            packetlostdown = receivingPkts(codec, timelength);
            dgmsocket.close();
    }
    /*
     it counts only the received packets
     */
    public float receivingPkts(String codec, int testlength) {
        System.out.println("receivingPkts:starts receiving..");
        DatagramPacket incomingPacketLocal = null;
        float packetlostdown = -1;
        //still true while receiving packets
        boolean morepacket = true;
        byte[] buf = null;
        buf = CdcVo.returnPayloadybyCodec(codec);
        int pps = CdcVo.returnPPSbyCodec(codec);
        int expectedPktNum = pps * testlength;// 50 pps* 15 sec = 750 pkt should be received
        incomingPacketLocal = new DatagramPacket(buf, buf.length);
        int count = 0;
        try {
            //dgmsocket.setSoTimeout(TrfBo.Packet_Max_Delay);
            System.out.println("receivingPkts:: listening on address="+dgmsocket.getLocalAddress().getHostAddress()+";port="+dgmsocket.getLocalPort());
            dgmsocket.setSoTimeout(TrfBo.Packet_Max_Delay);

            do {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                if (count == expectedPktNum) {
                    morepacket = false;
                }
            } while (morepacket);
            //System.out.println("[" + new Date() + "]\n - [" + threadName + "] packet: clientID:" + clientID + " is sent.");
            System.out.println("received pkt: total received count=" + count);
        } catch (SocketTimeoutException se) {
            System.out.println("Error:receivingPkts::" + se.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
         computes the packet lost/down 
         save the result into the DB
         */
        packetlostdown = VpMethds.computePktLossByCodec(count, pps, testlength);
        System.out.println("packetlossDown=" + packetlostdown);
        System.out.println("receivingPkts:finish receiving function.");
        System.out.println("TrfDgmRunnable:receivingPkts:closing the socket..");
        return packetlostdown;
    }

}
