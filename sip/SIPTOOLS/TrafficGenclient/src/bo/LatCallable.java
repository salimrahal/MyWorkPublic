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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import vp.bo.VpMethds;
import vp.vo.CdcVo;
import vp.vo.LatVo;
import vp.vo.PktVo;

/**
 *
 * @author salim 1- Compute latency Down: send n packets, receives n packet,
 * computes lat and jitter down 2- echo back n packet to server
 */
public class LatCallable implements Callable<LatVo> {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfBo trfBo;
    static final int packetNumToSend = 4;

    public LatCallable(Param param, InetAddress addressDest, int portsrc, int portdest, int clientID) throws SocketException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portdest;
        trfBo = new TrfBo();
        dgmsocket = new DatagramSocket(this.portsrc);
    }

    @Override
    public LatVo call() throws Exception {
        LatVo latvoUp = handlelat();
        return latvoUp;
    }

    /* it excuted the below task sequetially
     a- Send 
     b- Listen
     c- Send
     d- compute latency/jitter
     */
    private synchronized LatVo handlelat() throws IOException, InterruptedException {
        String codec = param.getCodec();
        LatVo latObj = null;
        Map<Integer, PktVo> pktMap = new HashMap<>();
        List<PktVo> pkL = new ArrayList<>();//render the list after building the Map

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
        System.out.println("LatCallable::handlelat:phase: a-b(send-listen):sending " + packetNumToSend + " packet..to=" + addressDest.getHostAddress() + ":" + portDest);
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, addressDest, portDest);
        //send packet to server
        for (int i = 1; i <= packetNumToSend; i++) {
            pktObj = new PktVo(i);
            pktObj.setTimeSent(System.currentTimeMillis());
            dgmsocket.send(outgoingPacket);
            pktMap.put(i, pktObj);
        }
        System.out.println("LatCallable::handlelat::waiting to recev....");
        int count = 1;
        try {
            //set the timeout
            dgmsocket.setSoTimeout(timelength * 1000);
            long tStart = System.currentTimeMillis();
            do {
                dgmsocket.receive(incomingPacket);
                pktObj = (PktVo) pktMap.get(count);
                pktObj.setTimeArrival(System.currentTimeMillis());
                //update the map
                pktMap.put(count, pktObj);
                count++;
                System.out.println("received pktnum" + count);
                System.out.println("LatCallable:handleLat: a-b(send-listen): Pkt:" + pktObj.toString());
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                System.out.println("LatCallable::handlelat:time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= timelength / 2 || count == packetNumToSend) {
                    System.out.println("LatCallable::handlelat:a-b(send-listen):Ends:No more packet to received. elapsed time:" + elapsedSeconds + " exceeded test time:" + timelength / 2 + ". finish the listening.");
                    morepacketToReceive = false;
                }
            } while (morepacketToReceive);

            //Computing the other side latency by sending N packet to server
            System.out.println("LatCallable::handlelat:phase: c(Send):Begin");
            for (int i = 1; i <= packetNumToSend; i++) {
                dgmsocket.send(outgoingPacket);
            }

            if (count > 0) {
                System.out.println("LatCallable received pkt: total received count=" + count);
                /*
                 todo: computes the Lat down:
                 convert the map to List 
                 compute the rtt per every packet
                 pass the pkt list to the function
                 */
                // packetlostdown = VpMethds.computeLatJitV2();

                System.out.println("handlelat:finish receiving function.");

            } else {
                System.out.println("LatCallable:handlelat:Something goes wrong nothing is received count=" + count);
            }
        } catch (SocketTimeoutException se) {
            System.out.println("Error:LatCallable::waiting to recev the packets:" + se.getMessage());
        } finally {
            System.out.println("LatCallable:handlelat:closing the socket..");
            dgmsocket.close();
        }
        return latObj;
    }
}