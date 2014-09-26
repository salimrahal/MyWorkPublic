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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.bo.VpMethds;
import sipserver.trf.vp.vo.CdcVo;
import sipserver.trf.vp.vo.LatVo;
import sipserver.trf.vp.vo.PktVo;

/**
 *
 * @author salim 1- echo back n packet to client 2- Compute latency Up: send n
 * packets, receives n packet, computes lat and jitter Up
 *
 */
public class LatRunnable implements Runnable {

    private int clientID;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;
    static final int packetNumToSend = 30;

    public LatRunnable(Param param, InetAddress addressDest, int portsrc, int portDest, int clientID) throws IOException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portDest;
        dgmsocket = new DatagramSocket(this.portsrc);
    }

    /* it excuted the below task sequetially 
     a- Listen
     b- Echo
     c- Listen   
     d- compute latency/jitter - Upload
     */
    private synchronized LatVo handleLat() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        HashMap<Integer, PktVo> pktMap = new HashMap<>();
        PktVo pktObj;
        LatVo latvoUp = null;
        //still true while receiving packets
        boolean morepacketToreceive = true;
        byte[] buf;
        int countLoop1 = 1;
        long tStart = 0;

        buf = CdcVo.returnPayloadybyCodec(codec);
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
        System.out.println("LatRunnable:handleLat::phase a-b(listen-echo) waiting for Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
        try {
            dgmsocket.setSoTimeout((timelength + 2) * 1000);
            tStart = System.nanoTime();
            do {
                dgmsocket.receive(incomingPacket);
                System.out.println("LatRunnable:handleLat:phase a-b(listen-echo) received pktnum" + countLoop1);
                //Echo it back to the server
                dgmsocket.send(outgoingPacket);
                pktObj = new PktVo(countLoop1);
                pktObj.setTimeSent(System.nanoTime());
                //add Pkt to the map
                pktMap.put(countLoop1, pktObj);
                System.out.println("LatRunnable:handleLat:phase a-b(listen-echo) received and resent pktnum" + countLoop1);
                countLoop1++;
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.nanoTime();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000000000.0;
                System.out.println("LatRunnable::handlelat:phase a-b(listen-echo) time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (countLoop1 == packetNumToSend || elapsedSeconds >= timelength) {
                    System.out.println("LatRunnable::handlelat:phase: a-b(listen-echo) :elapsed time:" + elapsedSeconds + " /original test time:" + timelength + ". finish the listening.");
                    morepacketToreceive = false;
                }
            } while (morepacketToreceive);

            boolean morepacketToSend = true;
            long tStartLoopS = System.nanoTime();
            int countLoop2 = 1;
            System.out.println("LatRunnable:handleLat:phase c(listen) start receiving the resent message to record the time arrived");
            dgmsocket.setSoTimeout((timelength + 2) * 1000);
            do {
                dgmsocket.receive(incomingPacket);
                if (pktMap.get(countLoop2) != null) {
                    pktObj = (PktVo) pktMap.get(countLoop2);
                    long timeArr = System.nanoTime();
                    pktObj.setTimeArrival(timeArr);
                    long rtt = timeArr - pktObj.getTimeSent();
                    pktObj.setRtt(rtt);
                    System.out.println("phase c(listen) received pktnum" + countLoop2);
                    System.out.println("LatRunnable:handleLat:phase c(listen): Pkt:" + pktObj.toString());
                    countLoop2++;
                } else {
                    System.out.println("phase c(listen) an WARNING! the pkt with id=" + countLoop2 + " doesnt exists in the Pkt Map, some pkt are lost.");
                }
                //check the elapsed time whether is greate than test time length then break the test
                long tEndLoopS = System.nanoTime();
                long tDeltaLoopS = tEndLoopS - tStartLoopS;
                double elapsedSecondsLoopS = tDeltaLoopS / 1000000000.0;
                System.out.println("LatRunnable::handlelat:phase c(listen) time elapsed:" + elapsedSecondsLoopS + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSecondsLoopS >= timelength) {
                    System.out.println("LatRunnable::handlelat:phase c(listen) elapsed time:" + elapsedSecondsLoopS + " exceeded test time:" + timelength / 2 + ". finish the listening.");
                    morepacketToreceive = false;
                }
            } while (morepacketToSend);
        } catch (SocketTimeoutException se) {
            long tEndEx = System.nanoTime();
            long tDeltaLoopE = tEndEx - tStart;
            double elapsedSecondsEx = tDeltaLoopE / 1000000000.0;
            System.out.println("LatRunnable:handleLat::No more Packet to receive:receiving Pkt::" + se.getMessage() + ".elapsed time:" + elapsedSecondsEx + "");
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //compute the lat up
            List<PktVo> pktL = TrfBo.hashtoList(pktMap);
            //clean the list where the rtt = 0, packet not received
            pktL = VpMethds.cll(pktL);
            latvoUp = VpMethds.computeLatJitV2(pktL);
            //concer to ms the results
            VpMethds.cvLat(latvoUp);
            System.out.println("LatRunnable:handlelat:Result:" + latvoUp.toString());
            System.out.println("LatRunnable:handlelat:closing the socket..");
            dgmsocket.close();
        }
        return latvoUp;
    }

    @Override
    public void run() {
        try {
            handleLat();
        } catch (InterruptedException ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
