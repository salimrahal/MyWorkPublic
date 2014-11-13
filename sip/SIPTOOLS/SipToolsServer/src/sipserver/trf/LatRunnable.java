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
import java.util.Date;
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
import sipserver.trf.vp.vo.JtrVo;
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

    public LatRunnable(DatagramSocket dgmsocket, Param param, InetAddress addressDest, int portsrc, int portDest, int clientID) throws IOException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portDest;
        //todo: dmsocket should be received as parameters
        this.dgmsocket = dgmsocket;
        trfdao = new TrfDao();
    }

    @Override
    public void run() {
        try {
            LatVo latvoUp = handlelat();
            //releasing the port is done in clientTCPconnection
            //trfdao.updateOnePortStatus(portsrc, "f");
            if (latvoUp == null) {
                latvoUp = new LatVo(-1, -1);
                JtrVo jitterO = new JtrVo(-1, -1);
                latvoUp.setJitterObj(jitterO);
            }
            //update the DB with Lat and jitter results
            trfdao.updateLatJitUp(param.getTstid(), latvoUp, latvoUp.getJitterObj());
        } catch (InterruptedException ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LatRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized LatVo handlelat() throws IOException, InterruptedException, Exception {
        LatVo latvoDown = null;
        try {
//1
            System.out.println("[" + new Date() + "] LatRunnable::handlelat: 1/2: start echolat:");
            echoLat();
            System.out.println("[" + new Date() + "] LatRunnable::handlelat: 1/2: end echolat:");
            //2
            System.out.println("[" + new Date() + "] LatRunnable::handlelat: 2/2: start processLatUp:");
            latvoDown = processLatUp();
            System.out.println("[" + new Date() + "] LatRunnable::handlelat: 2/2: end processLatUp:");
        } catch (Exception exception) {
            System.out.println("LatRunnable::handlelat: exception=" + exception.getMessage());
        } finally {
            System.out.println("[" + new Date() + "] LatRunnable:handlelat:3/3 closing the socket..");
            dgmsocket.close();
        }
        return latvoDown;
    }
    /* it excuted the below task sequetially 
     a- Listen
     b- Echo
     c- Listen   
     d- compute latency/jitter - Upload
     */

    private synchronized LatVo processLatUp() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        int listeningWindows = TrfBo.LAT_T;//sec:
        HashMap<Integer, PktVo> pktMap = new HashMap<>();
        PktVo pktObj;
        LatVo latvoUp = null;
        byte[] buf;
        long tStartLoopS = 0;
        buf = CdcVo.returnPayloadybyCodec(codec);
        DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
        System.out.println("LatRunnable::processLatUp:phase a:sending " + packetNumToSend + " pkts..");
        for (int i = 1; i <= packetNumToSend; i++) {
            pktObj = new PktVo(i);
            pktObj.setTimeSent(System.nanoTime());
            dgmsocket.send(outgoingPacket);
            pktMap.put(i, pktObj);
        }
        try {
            boolean morepacketToRecv = true;
            tStartLoopS = System.nanoTime();
            int countpktRcv = 1;
            System.out.println("[" + new Date() + "] LatRunnable:processLatUp:phase b(listen): waiting for Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
            dgmsocket.setSoTimeout(listeningWindows * 1000);
            double elapsedSecondsLoopS;
            do {
                dgmsocket.receive(incomingPacket);
                if (pktMap.get(countpktRcv) != null) {
                    pktObj = (PktVo) pktMap.get(countpktRcv);
                    long timeArr = System.nanoTime();
                    pktObj.setTimeArrival(timeArr);
                    long rtt = timeArr - pktObj.getTimeSent();
                    pktObj.setRtt(rtt);
                    //add Pkt to the map
                    pktMap.put(countpktRcv, pktObj);
                    //System.out.println("phase c(listen) received pktnum" + countLoop);
                    //System.out.println("LatRunnable:handleLat:phase c(listen): Pkt:" + pktObj.toString());
                    countpktRcv++;
                } else {
                    System.out.println("[" + new Date() + "] processLatUp :phase b(listen) WARNING! the pkt with id=" + countpktRcv + " doesnt exists in the Pkt Map, some pkt are lost. breaking the listeing loop.");
                    morepacketToRecv = false;
                }
                //check the elapsed time whether is greate than test time length then break the test
                long tEndLoopS = System.nanoTime();
                long tDeltaLoopS = tEndLoopS - tStartLoopS;
                elapsedSecondsLoopS = tDeltaLoopS / 1000000000.0;
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSecondsLoopS >= listeningWindows) {
                    System.out.println("[" + new Date() + "] LatRunnable::processLatUp: phase b(listen): finish send/receive, elapsed time:" + elapsedSecondsLoopS + " exceeded test time::" + listeningWindows + ". finish the listening.");
                    morepacketToRecv = false;
                }
            } while (morepacketToRecv);
            System.out.println("[" + new Date() + "] LatRunnable::processLatUp:phase b(listen): finish send/receive: received pkt=" + countpktRcv + "time elapsed:" + elapsedSecondsLoopS + " sec");
        } catch (SocketTimeoutException se) {
            long tEndEx = System.nanoTime();
            long tDeltaLoopE = tEndEx - tStartLoopS;
            double elapsedSecondsEx = tDeltaLoopE / 1000000000.0;
            System.out.println("[" + new Date() + "] LatRunnable:processLatUp:SocketTimeoutException:phase b(listen):No more Packet to receive:receiving Pkt::" + se.getMessage() + ".elapsed time:" + elapsedSecondsEx + "");
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
            if (latvoUp == null) {
                System.out.println("[" + new Date() + "] LatRunnable:processLatUp:Result:latency is null!");
            } else {
                System.out.println("[" + new Date() + "] LatRunnable:processLatUp:Result:" + latvoUp.toString());
            }
        }
        return latvoUp;
    }

    private synchronized void echoLat() {
        System.out.println("[" + new Date() + "] LatRunnable:echoLat::starts..");
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());;
        int listeningWindows = TrfBo.LAT_T;//sec
        //listeningWindows = 2+1; // time frame for receiving packets + app time
//still true while receiving packets
        boolean morepacketToProcess = true;
        byte[] buf;
        int countEchoed = 1;
        long tStart = 0;
        double elapsedSeconds = 0;
        buf = CdcVo.returnPayloadybyCodec(codec);

        DatagramPacket incomingPacket = null;
        DatagramPacket outgoingPacket = null;
        try {
            incomingPacket = new DatagramPacket(buf, buf.length);
            outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
            System.out.println("[" + new Date() + "] LatRunnable:echoLat:: Datagrams instantiated");
        } catch (Exception e) {
            System.out.println(" LatRunnable:echoLat: exception: "+e.getLocalizedMessage());
        }
        System.out.println("[" + new Date() + "] LatRunnable:echoLat:: waiting for Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
        int initial_delay = 2;//init delay debore start the process, this is the time receive the request from the client
        try {
            dgmsocket.setSoTimeout((listeningWindows + initial_delay) * 1000);
            // dgmsocket.setSoTimeout(TrfBo.U_T); time out very soon
            tStart = System.nanoTime();
            do {
                dgmsocket.receive(incomingPacket);
                //Echo it back to the server
                dgmsocket.send(outgoingPacket);
                //System.out.println("LatRunnable:handleLat:phase a-b(listen-echo) received and resent pktnum" + countLoop1);
                countEchoed++;
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.nanoTime();
                long tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000000000.0;
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= listeningWindows) {
                    System.out.println("[" + new Date() + "] LatRunnable::echoLat:phase: a-b(listen-echo) :elapsed time:" + elapsedSeconds + " /listeningWindows time:" + listeningWindows + ". finish the listening.");
                    morepacketToProcess = false;
                }
            } while (morepacketToProcess);
            System.out.println("[" + new Date() + "] LatRunnable:echoLat:phase a-b(listen-echo): finished: received and resent pktnum=" + countEchoed);
            System.out.println("[" + new Date() + "] LatRunnable::echoLat:phase: a-b(listen-echo):finished :elapsed time:" + elapsedSeconds);
        } catch (SocketTimeoutException se) {
            long tEndEx = System.nanoTime();
            long tDeltaLoopE = tEndEx - tStart;
            double elapsedSecondsEx = tDeltaLoopE / 1000000000.0;
            System.out.println("[" + new Date() + "] LatRunnable:echoLat:SocketTimeoutException:No more Packet to receive:receiving Pkt::" + se.getMessage() + ".elapsed time:" + elapsedSecondsEx + "");
        } catch (IOException iOException) {
            System.out.println("[" + new Date() + "] LatRunnable::echoLat:iOException:" + iOException.getMessage());
        } finally {
            System.out.println("[" + new Date() + "] LatRunnable echolatPkt: finally: pkt: total echoed count=" + countEchoed);
        }
    }

}
