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

    public LatRunnable(DatagramSocket dgmsocket, Param param, InetAddress addressDest, int portsrc, int portdest, int clientID) throws SocketException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portdest;
        trfBo = new TrfBo();
        //todo: receive the socket as parameter
        this.dgmsocket = dgmsocket;
        //old version commented
        //dgmsocket = new DatagramSocket(this.portsrc);

    }

    @Override
    public void run() {
        //System.out.println("LatRunnable::Thread name: " + Thread.currentThread().getName() + " Priority=" + Thread.currentThread().getPriority());
        try {
            LatVo latvoDown = handlelat();
            //System.out.println("[" + new Date() + "] LatRunnable: saving latvoDown to DB..");
            if (latvoDown != null) {
                int latpk = VpMethds.safeLongToInt(latvoDown.getPeak());
                int latavg = VpMethds.safeLongToInt(latvoDown.getAvg());
                int jtpk = VpMethds.safeLongToInt(latvoDown.getJitterObj().getPeak());
                int jtavg = VpMethds.safeLongToInt(latvoDown.getJitterObj().getAvg());
                WSBo.svLJD(param.getTstid(), latpk, latavg, jtpk, jtavg);
                //System.out.println("[" + new Date() + "] LatRunnable: finish ws call");
            } else {
                System.out.println("warning: latvoDown is null, save with -1 values");
                WSBo.svLJD(param.getTstid(), -1, -1, -1, -1);
            }
        } catch (IOException ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized LatVo handlelat() throws IOException, InterruptedException {
        LatVo latvoDown = null;
        try {
//1
            //System.out.println("[" + new Date() + "] LatRunnable::handlelat: 1/2: start processLatDown:");
            latvoDown = processLatDown();
           // System.out.println("[" + new Date() + "] LatRunnable::handlelat: 1/2: end processLatDown:");
            //2
            // System.out.println("[" + new Date() + "] LatRunnable::handlelat: 2/2: start echolat:");
            echolatPkt();
            // System.out.println("[" + new Date() + "] LatRunnable::handlelat: 2/2: end echolat:");
        } catch (Exception e) {
            System.out.println("LatRunnable::handlelat: exception=" + e.getMessage());
        } finally {
            //  System.out.println("[" + new Date() + "] LatRunnable:handlelat:3/3closing the socket..");
            dgmsocket.close();
        }
        return latvoDown;
    }
    /* it excuted the below task sequetially
     a- Send 
     b- Listen
     c- Send
     d- compute latency/jitter
     */

    private synchronized LatVo processLatDown() throws IOException, InterruptedException {
        String codec = param.getCodec();
        LatVo latvoDown = null;
        HashMap<Integer, PktVo> pktMap = new HashMap<>();
        PktVo pktObj;
        int timelength = Integer.valueOf(param.getTimelength());
        int listeningWindow = TrfBo.LAT_T;//sec: todo: to be minimized
        //listeningWindows = 2+1; // time frame for receiving packets + app time
        //still true while receiving packets
        boolean morepacketToReceive = true;
        byte[] buf;
        buf = CdcVo.returnPayloadybyCodec(codec);
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
        //System.out.println("[" + new Date() + "] LatRunnable::processLatDown:phase-a:start sending " + packetNumToSend + " packet..to=" + addressDest.getHostAddress() + ":" + portDest);
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
        //send packet to server
        for (int i = 1; i <= packetNumToSend; i++) {
            //System.out.println("LatRunnable::handlelat:phase: a-b(send-listen):sending pkt num=" + i);
            pktObj = new PktVo(i);
            pktObj.setTimeSent(System.nanoTime());
            dgmsocket.send(outgoingPacket);
            pktMap.put(i, pktObj);
        }
        //System.out.println("[" + new Date() + "] LatRunnable::processLatDown::phase: a:finished. start phase-b: waiting to recev....");
        int count = 1;
        try {
            //set the timeout
            dgmsocket.setSoTimeout((listeningWindow) * 1000);
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
                //System.out.println("LatRunnable:handleLat: a-b(send-listen):received pktnum" + count);
                count++;
                //System.out.println("LatRunnable:handleLat: a-b(send-listen): Pkt:" + pktObj.toString());
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.nanoTime();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000000000.0;
                //System.out.println("LatRunnable::handlelat:time elapsed:" + elapsedSeconds + " sec");
                if (elapsedSeconds >= listeningWindow) {
                    System.out.println("LatRunnable::processLatDown:phase b(listen):Ends:No more packet to received. elapsed time:" + elapsedSeconds + " /windows test [time]:" + listeningWindow + ". finish the listening.");
                    morepacketToReceive = false;
                }
            } while (morepacketToReceive);
        } catch (SocketTimeoutException se) {
            System.out.println("[" + new Date() + "]: LatRunnable::processLatDown:SocketTimeoutException: phase b(listen):" + se.getMessage());
        } finally {
            if (count > 0) {
                System.out.println("[" + new Date() + "] LatRunnable:processLatDown: phase b(listen):received pkt: total received count=" + count + ". finish listening.");
            } else {
                System.out.println("LatRunnable:processLatDown:Something goes wrong nothing is received count=" + count);
            }
            //compute the lat up
            List<PktVo> pktL = trfBo.hashtoList(pktMap);
            //clean the list where the rtt = 0, packet not received
            pktL = VpMethds.cll(pktL);
            latvoDown = VpMethds.computeLatJitV2(pktL);
            VpMethds.cvLat(latvoDown);
            if (latvoDown == null) {
                System.out.println("LatRunnable:processLatDown:Result:latency is null!");
            } else {
                System.out.println("LatRunnable:processLatDown:Result:" + latvoDown.toString());
            }
        }//end of finally
        return latvoDown;
    }

    /*
     it just echo back the packets
     */
    public void echolatPkt() throws SocketException, IOException {
        System.out.println("[" + new Date() + "] LatRunnable:echolatPkt:listening..");
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        int listeningWindows = TrfBo.LAT_T;//sec:
        boolean morepacketToReceive = true;
        byte[] buf;
        buf = CdcVo.returnPayloadybyCodec(codec);
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
        int count = 1;
        long tStart = 0;
        try {
            //set the timeout
            dgmsocket.setSoTimeout((listeningWindows) * 1000);
            tStart = System.nanoTime();
            do {
                dgmsocket.receive(incomingPacket);
                //Echo it back to the server
                dgmsocket.send(outgoingPacket);
                count++;
                //System.out.println("LatRunnable:handleLat: a-b(send-listen): Pkt:" + pktObj.toString());
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.nanoTime();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000000000.0;
                //System.out.println("LatRunnable::handlelat:time elapsed:" + elapsedSeconds + " sec");
                if (elapsedSeconds >= listeningWindows) {
                    System.out.println("LatRunnable::echolatPkt:Ends:No more packet to receive. elapsed time:" + elapsedSeconds + " /test window [time]:" + listeningWindows + ". finish the listening.");
                    morepacketToReceive = false;
                }
            } while (morepacketToReceive);

        } catch (SocketTimeoutException se) {
            long tEndEx = System.nanoTime();
            long tDeltaLoopE = tEndEx - tStart;
            double elapsedSecondsEx = tDeltaLoopE / 1000000000.0;
            System.out.println("[" + new Date() + "] LatRunnable:echoLat:SocketTimeoutException:No more Packet to receive:receiving Pkt::" + se.getMessage() + ".elapsed time:" + elapsedSecondsEx + "");
        } catch (IOException iOException) {
            System.out.println("[" + new Date() + "] LatRunnable::echoLat:iOException:" + iOException.getMessage());
        } finally {
            System.out.println("[" + new Date() + "] LatRunnable echolatPkt: finally: pkt: total echoed count=" + count);
        }
    }

    private void sendPkt(DatagramPacket outgoingPacket) throws IOException {
        //Computing the other side latency by sending N packet to server
        System.out.println("[" + new Date() + "] LatRunnable::handlelat:phase: c(Send):Begin");
        for (int i = 1; i <= packetNumToSend; i++) {
            dgmsocket.send(outgoingPacket);
        }
        System.out.println("[" + new Date() + "] LatRunnable::handlelat:phase: c(Send):finish");
    }

}
