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
 * @author salim 
 * This thread used to send down the traffic using the port down of the client
 */
public class TrfDgmRunnableIn implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    //private DatagramPacket incomingPacket;
    Integer portsrc;
    Param param;
    TrfDao trfdao;

    public TrfDgmRunnableIn(Param param, int clientID) throws IOException {
        this.param = param;
        this.clientID = clientID;
        //portsrc could be any port
        this.portsrc = Integer.valueOf(param.getPortrfClientU());
        dgmsocket = new DatagramSocket(this.portsrc);

    }

    @Override
    public void run() {
       
        try {
            System.out.println("TrfDgmRunnableIn::thread name=" + Thread.currentThread().getName() + " is Started");
            handleClienttraffic();
            System.out.println("TrfDgmRunnableIn::thread name=" + Thread.currentThread().getName() + " is completed");
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnableIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TrfDgmRunnableIn.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private synchronized void handleClienttraffic() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        float packetlossup = receivingPkts(codec, timelength);
    }
    /*
     it counts only the received packets
     */
    public float receivingPkts(String codec, int testlength) throws IOException {
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
            Logger.getLogger(TrfDgmRunnableIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
         computes the packet lost/down 
         save the result into the DB
         */
        packetlossup = VpMethds.computePktLossByCodec(count, pps, testlength);
        System.out.println("packetlossup=" + packetlossup + "%");
        System.out.println("receivingPkts:finish packetlossup function.");
        
        InetAddress addressInco = incomingPacketLocal.getAddress();
        int portInco = incomingPacketLocal.getPort();
        System.out.println("TrfDgmRunnableIn: incoming address="+addressInco.getHostAddress()+"/protInco="+portInco);
        //Todo send back the packets using the dest address above
         //run the thread that sends the traffic
//        int portsrcOut = Integer.valueOf(param.getPortrfClientD());
//        
//        TrfDgmRunnableOut trfDgmRunnableOut = new TrfDgmRunnableOut(param, addressInco, portsrcOut,portInco, clientID);
//        Thread trfDgmThreadOut = new Thread(trfDgmRunnableOut);
//        trfDgmThreadOut.start();
        return packetlossup;
    }

}
