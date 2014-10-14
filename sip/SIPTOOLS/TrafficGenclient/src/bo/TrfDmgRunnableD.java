/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import bn.Param;
import com.safirasoft.ResVo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.bo.VpMethds;
import vp.vo.CdcVo;

/**
 *
 * @author salim
 */
public class TrfDmgRunnableD implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfBo trfBo;
    WsRes wsres;

    public TrfDmgRunnableD(Param param, InetAddress addressDest, int portsrc, int portdest, WsRes wsres, int clientID) throws SocketException {
        this.param = param;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portdest;
        dgmsocket = new DatagramSocket(this.portsrc);
        trfBo = new TrfBo();
        this.wsres = wsres;
    }

    @Override
    public void run() {
        String pktLoss = null;
        System.out.println("TrfDmgRunnableD::Thread name: " + Thread.currentThread().getName() + " Priority=" + Thread.currentThread().getPriority());
        try {
            //if packetlostdown is < 0 then didn't completed
            float packetlostdown = handleClienttraffic();
            System.out.println("TrfDmgRunnableD: saving pktLossdown by WS to DB..");
            String message = WSBo.savePLD(param.getTstid(), packetlostdown);
            System.out.println("TrfDmgRunnableD: finish ws call: saving pkt loss down");
            // call an outside function to retreive the final results
            System.out.println("TrfDmgRunnableD: call ws: final results");
            //todo: in case of g711/60 sec Wifi: the res returned null,
            /*solution: move retreiving result to sending packet module / trfUp, check server log
            ResVo resVo = wsres.retreiveResbyWS(param.getTstid());
            wsres.putRes(resVo);
            if (resVo == null) {
                System.out.println("TrfDmgRunnableD:Warning: res is null ! putting a null value!");
            }*/
        } catch (IOException | InterruptedException e) {
            System.out.println("TrfDmgRunnableD:Exception:" + e.getMessage());
        }
    }

    /*
     1- it sends flags packets to the server
     2- still blocked until it receives a packet back
     3- start receiving other packets by looping on receive() function
     4- break the loop once it achieves the timelength limit
     5- it computes the packet lost Down
     */
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
        double elapsedSeconds = 0;
        DatagramPacket incomingPacketFlag = new DatagramPacket(bufFlag, bufFlag.length);
        System.out.println("TrfDmgRunnableD::handleClienttraffic::sending " + flagsNum + " flags packet..to=" + addressDest.getHostAddress() + ":" + portDest);
        DatagramPacket outgoingPacketFlag = new DatagramPacket(bufFlag, bufFlag.length, addressDest, portDest);
        //send flag packet to server
        for (int i = 0; i < flagsNum; i++) {
            dgmsocket.send(outgoingPacketFlag);
        }
        System.out.println("TrfDmgRunnableD::handleClienttraffic::waiting to recev the flag....");
        try {
            //set the timeout for the flag 
            //todo decrease the time out
            dgmsocket.setSoTimeout(timelength * 1000);
            //register the begin time
            dgmsocket.receive(incomingPacketFlag);
            System.out.println("TrfDmgRunnableD::handleClienttraffic::flag received");
            //increase the timeout  
            dgmsocket.setSoTimeout(TrfBo.T_P);

            long tStart = System.currentTimeMillis();
            do {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                //System.out.println("received pktnum" + count);
                //check the elapsed time whether is greate than test time length then break the test
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000.0;
                //if the elapsed time exceed test time length plus the delay sum of packets 2sec then finish the test
                if (elapsedSeconds >= timelength) {
                    System.out.println("TrfDmgRunnableD::handleClienttraffic:elapsed time:" + elapsedSeconds + " exceeded test time:" + timelength + ". finish the listening.");
                    morepacket = false;
                }
            } while (morepacket);
        } catch (SocketTimeoutException se) {
            System.out.println("Error:TrfDmgRunnableD::waiting to recev the flag:" + se.getMessage());
        } finally {
            System.out.println("TrfDmgRunnableD::handleClienttraffic:time elapsed:" + elapsedSeconds + " sec");
            if (count > 0) {
                System.out.println("TrfDmgRunnableD::handleClienttraffic:received pkt: total received count=" + count);
                /*
                 computes the packet lost/down 
                 */
                packetlostdown = VpMethds.computePktLossByCodec(count, pps, timelength);
                System.out.println("packetlossDown=" + packetlostdown);
                System.out.println("receivingPkts:finish receiving function.");

            } else {
                System.out.println("TrfDmgRunnableD:receivingPkts:Something goes wrong nothing is received count=" + count);
            }
            System.out.println("TrfDmgRunnableD:receivingPkts:closing the socket..");
            dgmsocket.close();
        }
        return packetlostdown;
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        Param param2 = new Param();
        param2.setCodec(CdcVo.CODEC_G729);
        param2.setTimelength("15");
        int portsrc = 5108;
        int portDest = 1111;
        //inetAddress unsused here
//        InetAddress inetAddrDest = InetAddress.getByName("127.0.0.1");
//        TrfDmgRunnableD trfDgmD = new TrfDgmRunnableD(param2, inetAddrDest, portsrc, portDest, 0);
//        Thread trfDgmDThread = new Thread(trfDgmD);
//        trfDgmDThread.start();

    }

}
