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
import java.net.ServerSocket;
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
 * @author salim This thread used to send down the traffic using the port down
 * of the client
 */
public class TrfDgmRunnableIn implements Runnable {

    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;
    ServerSocket serversocket;

    public TrfDgmRunnableIn(Param param, InetAddress addressDest, int portsrc, int portdest) throws IOException {
        this.param = param;
        //portsrc could be any port
        this.portsrc = portsrc;
        this.portDest = portdest;
        this.addressDest = addressDest;
        dgmsocket = new DatagramSocket(this.portsrc);
        trfdao = new TrfDao();
    }

    @Override
    public void run() {
        System.out.println("TrfDgmRunnableIn:: Priority=" + Thread.currentThread().getPriority());
        try {
            //if packetlostup is < 0 then didn't completed
            handleClienttrafficV2();
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnableIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TrfDgmRunnableIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized float handleClienttrafficV2() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        String testid = param.getTstid();
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
        //send a flag packet to the server before start receiving
        byte[] bufFlag = new byte[8];
        int flagsNum = 4;
        boolean flagrcv = false;
        double elapsedSeconds = 0;
        DatagramPacket incomingPacketFlag = new DatagramPacket(bufFlag, bufFlag.length);
        System.out.println("TrfDgmRunnableIn::handleClienttrafficV2::.listening on UDP:" + dgmsocket.getLocalPort());
        DatagramPacket outgoingPacketFlag = new DatagramPacket(bufFlag, bufFlag.length, addressDest, portDest);

        try {
            //decrease the timeout  
            dgmsocket.setSoTimeout(timelength * 1000);//sometime the timeout fires before finishing the test in case where the client close his app while he is sending packets
            //: register the begin time
            long tStart = System.currentTimeMillis();
            do {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                //System.out.println("TrfDgmRunnableIn: received pktnum" + count);
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000.0;
                //System.out.println("TrfDgmRunnableIn::handleClienttraffic:time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= timelength) {
                    System.out.println("TrfDgmRunnableIn::handleClienttraffic:elapsed time:" + elapsedSeconds + " exceeded test time:" + timelength + ". finish the listening.");
                    morepacket = false;
                }
            } while (morepacket);
            //System.out.println("[" + new Date() + "]\n - [" + threadName + "] packet: clientID:" + clientID + " is sent.");
        } catch (SocketTimeoutException se) {
            System.out.println("Error:TrfDgmRunnableIn:" + se.getMessage());
        } finally {
            if (count > 0) {
                System.out.println("TrfDgmRunnableIn: received pkt: total received count=" + count);
                /*
                 computes the packet lost/down 
                 */
                packetlostup = VpMethds.computePktLossByCodec(count, pps, timelength);
                System.out.println("packetlossUp=" + packetlostup);
                System.out.println("receivingPkts:finish receiving function.");
            } else {
                System.out.println("TrfDgmRunnableIn:receivingPkts:Something goes wrong nothing is received count=" + count);
            }
            System.out.println("TrfDgmRunnableIn:receivingPkts:closing the socket..");
            dgmsocket.close();
            //releasing the port is done in clientTCPconnection
            //System.out.println("TrfDgmRunnableIn::releasing port:" + portsrc);
            //trfdao.updateOnePortStatus(portsrc, "f");
            //insert test record: testId,pktLossUp
            trfdao.updateTestPacketLostUp(testid, packetlostup);//todo insert the end time of test: testId, end time
            System.out.println("TrfDgmRunnableIn:receivingPkts:inserting the endtime of test: testId, end time]..");
            trfdao.updateTestEndTime(testid);
        }
        return packetlostup;
    }
}
