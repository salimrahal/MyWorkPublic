/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import bn.Param;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.bo.VpMethds;
import vp.vo.CdcVo;
import vp.vo.LatVo;
import vp.vo.PktVo;

/**
 *
 * @author salim 1- Compute latency Down: send n packets, receives n packet,
 * computes lat and jitter down 2- echo back n packet to server
 */
public class LatRunnable implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfBo trfBo;
    static final int packetNumToSend = 30;

    public LatRunnable(Param param, InetAddress addressDest, int portsrc, int portdest, int clientID) throws SocketException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portdest;
        trfBo = new TrfBo();
        dgmsocket = new DatagramSocket(this.portsrc);
    }

    @Override
    public void run() {
        try {
            LatVo latvoUp = handlelat();
            System.out.println("TrfDmgRunnableD: saving pktLossdown by WS to DB..");
            int latpk =VpMethds.safeLongToInt(latvoUp.getPeak());
            int latavg = VpMethds.safeLongToInt(latvoUp.getAvg());;
            int jtpk = VpMethds.safeLongToInt(latvoUp.getJitterObj().getPeak());
            int jtavg = VpMethds.safeLongToInt(latvoUp.getJitterObj().getAvg());
            WSBo.svLJD(param.getTstid(), latpk, latavg, jtpk, jtavg );
            System.out.println("TrfDmgRunnableD: finish ws call");
        } catch (IOException ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* it excuted the below task sequetially
     a- Send 
     b- Listen
     c- Send
     d- compute latency/jitter
     */
    private synchronized LatVo handlelat() throws IOException, InterruptedException {
        String codec = param.getCodec();
        LatVo latvoDown = null;
        HashMap<Integer, PktVo> pktMap = new HashMap<>();
        PktVo pktObj;
        int timelength = Integer.valueOf(param.getTimelength());
        /*
         received section
         */
        //still true while receiving packets
        boolean morepacketToReceive = true;
        byte[] buf;
        buf = CdcVo.returnPayloadybyCodec(codec);

        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
        System.out.println("LatRunnable::handlelat:phase: a-b(send-listen):sending " + packetNumToSend + " packet..to=" + addressDest.getHostAddress() + ":" + portDest);
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
        //send packet to server
        for (int i = 1; i <= packetNumToSend; i++) {
            System.out.println("LatRunnable::handlelat:phase: a-b(send-listen):sending pkt num=" + i);
            pktObj = new PktVo(i);
            pktObj.setTimeSent(System.nanoTime());
            dgmsocket.send(outgoingPacket);
            pktMap.put(i, pktObj);
        }
        System.out.println("LatRunnable::handlelat::waiting to recev....");
        int count = 1;
        try {
            //set the timeout
            dgmsocket.setSoTimeout(timelength * 1000);
            long tStart = System.nanoTime();
            do {
                dgmsocket.receive(incomingPacket);
                pktObj = (PktVo) pktMap.get(count);
                long timeArr = System.nanoTime();
                pktObj.setTimeArrival(timeArr);
                long rtt = timeArr - pktObj.getTimeSent();
                pktObj.setRtt(rtt);
                //update the map
                pktMap.put(count, pktObj);
                System.out.println("LatRunnable:handleLat: a-b(send-listen):received pktnum" + count);
                count++;
                System.out.println("LatRunnable:handleLat: a-b(send-listen): Pkt:" + pktObj.toString());
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.nanoTime();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000000000.0;
                System.out.println("LatRunnable::handlelat:time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= timelength / 2 || count == packetNumToSend) {
                    System.out.println("LatRunnable::handlelat:a-b(send-listen):Ends:No more packet to received. elapsed time:" + elapsedSeconds + " /original test time/2:" + timelength / 2 + ". finish the listening.");
                    morepacketToReceive = false;
                }
            } while (morepacketToReceive);
            //Computing the other side latency by sending N packet to server
            System.out.println("LatRunnable::handlelat:phase: c(Send):Begin");
            for (int i = 1; i <= packetNumToSend; i++) {
                dgmsocket.send(outgoingPacket);
            }

        } catch (SocketTimeoutException se) {
            System.out.println("Error:LatRunnable::waiting to recev the packets:" + se.getMessage());
        } finally {
            if (count > 0) {
                System.out.println("LatRunnable received pkt: total received count=" + count);

                System.out.println("handlelat:finish receiving function.");

            } else {
                System.out.println("LatRunnable:handlelat:Something goes wrong nothing is received count=" + count);
            }
            //compute the lat up
            List<PktVo> pktL = trfBo.hashtoList(pktMap);
            //clean the list where the rtt = 0, packet not received
            pktL = VpMethds.cll(pktL);
            latvoDown = VpMethds.computeLatJitV2(pktL);
            VpMethds.cvLat(latvoDown);
            System.out.println("LatRunnable:handlelat:Result:"+latvoDown.toString());
            System.out.println("LatRunnable:handlelat:closing the socket..");
            dgmsocket.close();
        }
        return latvoDown;
    }

}
