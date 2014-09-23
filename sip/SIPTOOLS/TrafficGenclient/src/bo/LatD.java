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
import java.util.concurrent.Callable;
import vp.bo.VpMethds;
import vp.vo.CdcVo;
import vp.vo.LatVo;

/**
 *
 * @author salim
 * 
 */
public class LatD implements Callable<LatVo>{
        private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfBo trfBo;
    
    
    public LatD(Param param, InetAddress addressDest, int portsrc, int portdest, int clientID) throws SocketException {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     private synchronized float handleClienttraffic() throws IOException, InterruptedException {
        String codec = param.getCodec();
        float packetlostdown = -1;
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
        int expectedPktNum = pps * timelength;// 50 pps* 15 sec = 750 pkt should be received
        incomingPacketLocal = new DatagramPacket(buf, buf.length);
        int count = 0;

        //send a flag packet to the server before start receiving
        byte[] bufFlag = new byte[8];
        int flagsNum = 3;
        DatagramPacket incomingPacketFlag = new DatagramPacket(bufFlag, bufFlag.length);
        System.out.println("TrfDmgCallableD::handleClienttraffic::sending " + flagsNum + " flags packet..to=" + addressDest.getHostAddress() + ":" + portDest);
        DatagramPacket outgoingPacketFlag = new DatagramPacket(bufFlag, bufFlag.length, addressDest, portDest);
        //send flag packet to server
        for (int i = 0; i < flagsNum; i++) {
            dgmsocket.send(outgoingPacketFlag);
        }
        System.out.println("TrfDmgCallableD::handleClienttraffic::waiting to recev the flag....");
        try {
            //set the timeout for the flag 
            dgmsocket.setSoTimeout(timelength * 1000);
            //todo: register the begin time
            dgmsocket.receive(incomingPacketFlag);
            System.out.println("TrfDmgCallableD::handleClienttraffic::flag received");
            //increase the timeout  
            dgmsocket.setSoTimeout(TrfBo.T_P);

            long tStart = System.currentTimeMillis();
            do {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                System.out.println("received pktnum" + count);
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                System.out.println("TrfDmgCallableD::handleClienttraffic:time elapsed:" + elapsedSeconds + " sec");
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= timelength) {
                    System.out.println("TrfDmgCallableD::handleClienttraffic:elapsed time:" + elapsedSeconds + " exceeded test time:" + timelength + ". finish the listening.");
                    morepacket = false;
                }
            } while (morepacket);
            if (count > 0) {
                System.out.println("received pkt: total received count=" + count);
                /*
                 computes the packet lost/down 
                 */
                packetlostdown = VpMethds.computePktLossByCodec(count, pps, timelength);
                System.out.println("packetlossDown=" + packetlostdown);
                System.out.println("receivingPkts:finish receiving function.");

            } else {
                System.out.println("TrfDmgCallableD:receivingPkts:Something goes wrong nothing is received count=" + count);
            }

        } catch (SocketTimeoutException se) {
            System.out.println("Error:TrfDmgCallableD::waiting to recev the flag:" + se.getMessage());
        } finally {
            System.out.println("TrfDmgCallableD:receivingPkts:closing the socket..");
            dgmsocket.close();
        }
        return packetlostdown;
    }
}