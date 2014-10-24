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
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.bo.VpMethds;
import vp.vo.CdcVo;

/**
 *
 * @author salim handling traffic a- responsible for receiving and counting the
 * packet lost b- save the result into the DB: client name, clientpublicIP,
 * uppacketLost, starttime c- release the used for: update Ports table and set
 * the used port to free
 */
public class TrfDgmRunnableU implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    //private DatagramPacket incomingPacket;
    InetAddress addressDest;
    Integer port;
    Integer portDest;
    Param param;

    public TrfDgmRunnableU(Param param, InetAddress addressDest, int clientID) throws IOException {
        this.param = param;
//this.incomingPacket = incomingPacket;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.port = Integer.valueOf(param.getPortrfU());
        this.portDest = Integer.valueOf(param.getPortrfU());
        dgmsocket = new DatagramSocket(this.port);
    }

    @Override
    public void run() {
        try {
            System.out.println("TrfDgmRunnableU::Thread name"+Thread.currentThread().getName()+" Priority=" + Thread.currentThread().getPriority());
            handleClienttrafficV2();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void handleClienttrafficV2() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        /* 1- receive the flag packet from the client
         2- extract the addressInco and portInco
         3- pass them to the sendpacket function
         */
        byte[] buf = new byte[8];
        DatagramPacket incomingPacketLocal = new DatagramPacket(buf, buf.length);
        //System.out.println("TrfDgmRunnableU:handleClienttraffic:: sources address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
            double elapsedSeconds = 0;
        long tStart = 0;
        long tEnd;
        long tDelta;
        try {
             //todo decrease the time out
            dgmsocket.setSoTimeout(TrfBo.P_MX_D);
            tStart = System.currentTimeMillis();
            //it sends the packets
            // close the connection or socket
            //System.out.println("TrfDgmRunnableU: launchTrafficUp: begin of sending packets..");
            sendingPkts(codec, timelength);
        } catch (SocketTimeoutException se) {
            System.out.println("TrfDgmRunnableU::Error::" + se.getMessage());
            System.out.println("TrfDgmRunnableU:in Catch...:closing the socket..");
            dgmsocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("TrfDgmRunnableU:in Catch...:closing the socket..");
            dgmsocket.close();
        }finally{
            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            elapsedSeconds = tDelta / 1000.0;
            System.out.println("TrfDgmRunnableU: finally: time elapsed="+elapsedSeconds+" s");
        }
    }

     private synchronized void handleClienttraffic() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        /* 1- receive the flag packet from the client
         2- extract the addressInco and portInco
         3- pass them to the sendpacket function
         */
        byte[] buf = new byte[8];
        DatagramPacket incomingPacketLocal = new DatagramPacket(buf, buf.length);
        //System.out.println("TrfDgmRunnableU:handleClienttraffic:: waiting for flag Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
            double elapsedSeconds = 0;
        long tStart = 0;
        long tEnd;
        long tDelta;
        try {
             //todo decrease the time out
            dgmsocket.setSoTimeout(TrfBo.P_MX_D);
            tStart = System.currentTimeMillis();
            dgmsocket.receive(incomingPacketLocal);
           // System.out.println("TrfDgmRunnableU:handleClienttraffic:receives a flag packet ");
            InetAddress inetAddrInco = incomingPacketLocal.getAddress();
            int portInco = incomingPacketLocal.getPort();
            DatagramPacket outgoingPacketLocal = new DatagramPacket(buf, buf.length, inetAddrInco, portInco);
            //System.out.println("TrfDgmRunnableU:handleClienttraffic:sending back the flag packet");
            dgmsocket.send(outgoingPacketLocal);
            //it sends the packets
            // close the connection or socket
            //System.out.println("TrfDgmRunnableU: launchTrafficUp::success, begin of sending packets");
            sendingPkts(codec, timelength);
        } catch (SocketTimeoutException se) {
            System.out.println("TrfDgmRunnableU::Error:receiving flag::" + se.getMessage());
            System.out.println("TrfDgmRunnableU:in Catch...:closing the socket..");
            dgmsocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("TrfDgmRunnableU:in Catch...:closing the socket..");
            dgmsocket.close();
        }finally{
            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            elapsedSeconds = tDelta / 1000.0;
            //System.out.println("TrfDgmRunnableU: finally: time elapsed="+elapsedSeconds+" s");
        }
    }
    /**
     * It just send the packets through a thread
     *
     * @param codec
     * @param timelength
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean sendingPkts(String codec, int timelength) throws IOException, InterruptedException {
        boolean res = false;
        PacketControl bc = new PacketControl(dgmsocket, addressDest, portDest);
        res = bc.sndPktForAnGivenTime(codec, timelength);
        return res;
    }
}
