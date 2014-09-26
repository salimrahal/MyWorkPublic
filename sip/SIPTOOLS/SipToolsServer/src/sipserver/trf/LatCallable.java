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
import sipserver.trf.vp.vo.CdcVo;
import sipserver.trf.vp.vo.LatVo;
import sipserver.trf.vp.vo.PktVo;

/**
 *
 * @author salim 1- echo back n packet to client 2- Compute latency Up: send n
 * packets, receives n packet, computes lat and jitter Up
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
    static final int packetNumToSend = 4;

    public LatCallable(Param param, InetAddress addressDest, int portsrc, int portDest, int clientID) throws IOException {
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
        Map<Integer, PktVo> pktMap = new HashMap<>();
        List<PktVo> pkL = new ArrayList<>();//render the list after building the Map
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
        System.out.println("LatCallable:handleLat::phase a-b(listen-echo) waiting for Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
        try {
            dgmsocket.setSoTimeout(0);
            tStart = System.currentTimeMillis();
            do {
                dgmsocket.receive(incomingPacket);
                  System.out.println("LatCallable:handleLat:phase a-b(listen-echo) received pktnum" + countLoop1);
                //Echo it back to the server
                dgmsocket.send(outgoingPacket);
                pktObj = new PktVo(countLoop1);
                pktObj.setTimeSent(System.currentTimeMillis());
                //add Pkt to the map
                pktMap.put(countLoop1, pktObj);
                countLoop1++;
                System.out.println("LatCallable:handleLat:phase a-b(listen-echo) received and resent pktnum" + countLoop1);

                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                System.out.println("LatCallable::handlelat:phase a-b(listen-echo) time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (countLoop1 == packetNumToSend || elapsedSeconds >= timelength  ) {
                    System.out.println("LatCallable::handlelat:phase: a-b(listen-echo) :elapsed time:" + elapsedSeconds + " exceeded test time:" + timelength + ". finish the listening.");
                    morepacketToreceive = false;
                }
            } while (morepacketToreceive);

            boolean morepacketToSend = true;
            long tStartLoopS = System.currentTimeMillis();
            int countLoop2 = 1;
            do {
                System.out.println("LatCallable:handleLat:phase c(listen) start receiving the resent message to record the time arrived");
                dgmsocket.receive(incomingPacket);
                pktObj = (PktVo) pktMap.get(countLoop1);
                pktObj.setTimeArrival(System.currentTimeMillis());
                countLoop2++;
                System.out.println("phase c(listen) received pktnum" + countLoop2);
                System.out.println("LatCallable:handleLat:phase c(listen): Pkt:" + pktObj.toString());
                //check the elapsed time whether is greate than test time length then break the test
                long tEndLoopS = System.currentTimeMillis();
                long tDeltaLoopS = tEndLoopS - tStartLoopS;
                double elapsedSecondsLoopS = tDeltaLoopS / 1000.0;
                System.out.println("LatCallable::handlelat:phase c(listen) time elapsed:" + elapsedSecondsLoopS + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSecondsLoopS >= timelength / 2 || countLoop2 == packetNumToSend) {
                    System.out.println("LatCallable::handlelat:phase c(listen) elapsed time:" + elapsedSecondsLoopS + " exceeded test time:" + timelength / 2 + ". finish the listening.");
                    morepacketToreceive = false;
                }
            } while (morepacketToSend);

//            dgmsocket.receive(incomingPacket);
//            System.out.println("LatCallable:handleLat::receives a packet ");
//            InetAddress inetAddrInco = incomingPacket.getAddress();
//            int portInco = incomingPacket.getPort();
//            DatagramPacket outgoingPacketLocal = new DatagramPacket(buf, buf.length, inetAddrInco, portInco);
//            System.out.println("LatCallable:handleLat::sending back the flag packet");
//            dgmsocket.send(outgoingPacket);
        } catch (SocketTimeoutException se) {
            long tEndEx = System.currentTimeMillis();
            long tDeltaLoopE = tEndEx - tStart;
            double elapsedSecondsEx = tDeltaLoopE / 1000.0;
            System.out.println("LatCallable:handleLat::Error:receiving Pkt::" + se.getMessage()+".elapsed time:" + elapsedSecondsEx + "");
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("LatCallable:handlelat:closing the socket..");
            dgmsocket.close();
        }
        return latvoUp;
    }

    @Override
    public LatVo call() throws Exception {
        LatVo latvoUp = handleLat();
        return latvoUp;
    }

}
