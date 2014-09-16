package sipserver.trf;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.bo.VpMethds;
import sipserver.trf.vp.vo.CdcVo;

/**
 *
 * @author salim handling traffic a- responsible for receiving and counting the
 * packet lost b- save the result into the DB: client name, clientpublicIP,
 * uppacketLost, starttime c- release the used for: update Ports table and set
 * the used port to free
 */
public class TrfDgmRunnable implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    //private DatagramPacket incomingPacket;
    InetAddress addressDest;
    Integer portTrf;
    Integer portDest;
    Param param;
    TrfDao trfdao;

    public TrfDgmRunnable(Param param, InetAddress addressDest, int clientID) throws IOException {
        this.param = param;
//this.incomingPacket = incomingPacket;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portTrf = Integer.valueOf(param.getPortrf());
        this.portDest = Integer.valueOf(param.getPortrf());
        dgmsocket = new DatagramSocket(this.portTrf);

    }

    @Override
    public void run() {
        try {
            System.out.println("TrfDgmRunnable::thread name=" + Thread.currentThread().getName() + " is Started");
            handleClienttraffic();
            System.out.println("TrfDgmRunnable::thread name=" + Thread.currentThread().getName() + " is completed");
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void handleClienttraffic() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        float packetlossup = receivingPkts(codec, timelength);
        if (packetlossup != -1) {
            //it sends the packet
            // close the connection or socket
            sendingPkts(codec, timelength);
        } else {
            System.out.println("TrfDgmRunnable:handleClienttraffic: could not proceed with sending pkts, something is wrong with receiving pkts![packetlossup=-1]");
        }
    }

    /**
     *  1- it sends the packet
        2- close the connection or socket
     * @param codec
     * @param timelength
     * @throws IOException
     * @throws InterruptedException 
     */
    public void sendingPkts(String codec, int timelength) throws IOException, InterruptedException {
        System.out.println("sendingPkts:start..");
        PacketControl bc = new PacketControl(dgmsocket, addressDest, portDest);
        /*1- it sends back packets to client
         2- then release the port traffic
         */
        bc.sndPktForAnGivenTime(codec, timelength, this.portTrf);
        System.out.println("sendingPkts:finish sending.");

    }

    /*
     it counts only the received packets
     */
    public float receivingPkts(String codec, int testlength) {
        System.out.println("receivingPkts:starts receiving..");
        DatagramPacket incomingPacketLocal = null;
        float packetlossup = -1;
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
            dgmsocket.setSoTimeout(TrfBo.Packet_Max_Delay);

            do {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                if (count == expectedPktNum) {
                    morepacket = false;
                }
            } while (morepacket);
            //System.out.println("[" + new Date() + "]\n - [" + threadName + "] packet: clientID:" + clientID + " is sent.");
            System.out.println("receivingPkts: total received count=" + count);
        } catch (SocketTimeoutException se) {
            System.out.println("Error:receivingPkts::" + se.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
         computes the packet lost/down 
         save the result into the DB
         */
        packetlossup = VpMethds.computePktLossByCodec(count, pps, testlength);
        System.out.println("packetlossup=" + packetlossup + "/%");
        System.out.println("receivingPkts:finish packetlossup function.");
        return packetlossup;
    }

}
