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
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.bo.VpMethds;
import sipserver.trf.vp.vo.CdcVo;

/**
 *
 * @author salim This thread used to send down the traffic using the port down
 * of the client
 */
public class TrfDgmCallableIn implements Callable<String> {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;

    public TrfDgmCallableIn(Param param, InetAddress addressDest, int portsrc, int portdest, int clientID) throws IOException {
        this.param = param;
        this.clientID = clientID;
        //portsrc could be any port
        this.portsrc = portsrc;
        this.portDest = portdest;
        this.addressDest = addressDest;
        dgmsocket = new DatagramSocket(this.portsrc);
        trfdao = new TrfDao();
    }

    /*
     it receives and computes packet loss of the incoming packets from the client as follows:
     1- send a flag that indicates the start of this session
     2- wait till it receives the flag back
     3- once received so it's start to listen to the other side packets
     4-computes the packet loss
     */
    private synchronized float handleClienttraffic() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        float packetlostup = -1;
        int timelength = Integer.valueOf(param.getTimelength());
        /*
         received section
         */
        DatagramPacket incomingPacketLocal = null;
        //still true while receiving packets
        boolean morepacket = true;
        byte[] buf = null;
        buf = CdcVo.returnPayloadybyCodec(codec);
        int pps = CdcVo.returnPPSbyCodec(codec);
        incomingPacketLocal = new DatagramPacket(buf, buf.length);
        int count = 0;

        //send a flag packet to the server before start receiving
        try {
            //increase the timeout  
            dgmsocket.setSoTimeout(timelength * 1000);//sometime the timeout fires before finishing the test in case where the client close his app while he is sending packets
            //: register the begin time
            long tStart = System.currentTimeMillis();
            double elapsedSeconds;
            do {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                System.out.println("TrfDgmCallableIn: received pktnum" + count);
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000.0;
                //System.out.println("TrfDgmCallableIn::handleClienttraffic:time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= timelength) {
                    System.out.println("TrfDgmCallableIn::handleClienttraffic:elapsed time:" + elapsedSeconds + " exceeded test time:" + timelength + ". finish the listening.");
                    morepacket = false;
                }
            } while (morepacket);
            //System.out.println("[" + new Date() + "]\n - [" + threadName + "] packet: clientID:" + clientID + " is sent.");
            if (count > 0) {
                System.out.println("received pkt: total received count=" + count);
                /*
                 computes the packet lost/down 
                 */
                packetlostup = VpMethds.computePktLossByCodec(count, pps, timelength);
                System.out.println("packetlossUp=" + packetlostup);
                System.out.println("receivingPkts:finish receiving function.");

            } else {
                System.out.println("TrfDgmRunnable:receivingPkts:Something goes wrong nothing is received count=" + count);
            }

        } catch (SocketTimeoutException se) {
            System.out.println("Error:TrfDgmCallableIn::waiting to recev the flag:" + se.getMessage());
        } finally {
            System.out.println("TrfDgmRunnable:receivingPkts:closing the socket..");
            dgmsocket.close();
            //todo insert the end time of test: testId, end time
        }
        return packetlostup;
    }


    @Override
    public String call() throws Exception {
        String  pktLoss = null;
         try {
            //if packetlostup is < 0 then didn't completed
            float packetlossup = handleClienttraffic();
             pktLoss = String.valueOf(packetlossup);
            /*
             release the port
             */
            System.out.println("TrfDgmCallableIn::releasing port:" + portsrc);
            trfdao.updateOnePortStatus(portsrc, "f");
            //todo: insert test record: testId,pktLossUp
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmCallableIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TrfDgmCallableIn.class.getName()).log(Level.SEVERE, null, ex);
        }
         return pktLoss;
    }
}
