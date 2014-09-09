/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import bean.Param;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.vo.CdcVo;

/**
 *
 * @author salim handling traffic a- responsible for receiving and counting the
 * packet lost b- save the result into the DB: client name, clientpublicIP,
 * uppacketLost, starttime c- release the used for: update Ports table and set
 * the used port to free
 */
public class TrfDgmRunnable implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    //private DatagramPacket incomingPacket;
    InetAddress addressDest;
    Integer port;
    Integer portDest;
    Param param;

    public TrfDgmRunnable(Param param, InetAddress addressDest, Integer portTrf, int clientID) throws IOException {
        this.param = param;
//this.incomingPacket = incomingPacket;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.port = portTrf;
        this.portDest = portTrf;
        dgmsocket = new DatagramSocket(portTrf);
    }

    @Override
    public void run() {
        try {
            handleClienttraffic();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void handleClienttraffic() throws IOException, InterruptedException {
        sendingPkts();
        receivingPkts();
    }

    public void sendingPkts() throws IOException, InterruptedException {
        System.out.println("start sendingPkts");
        String codec = param.getCodec();
        DatagramPacket outgoingPacketLocal = null;
        //still true while receiving packets
        int testlength = Integer.valueOf(param.getTimelength());
        //CdcVo cd = new CdcVo();
        int pps = CdcVo.returnPPSbyCodec(codec);
        byte[] buf = null;
        buf = CdcVo.returnPayloadybyCodec(codec);
        //sending
        Thread.currentThread().wait(20);
        outgoingPacketLocal = new DatagramPacket(buf, buf.length, addressDest, portDest);
        //send the packet back to the client
        dgmsocket.send(outgoingPacketLocal);

    }

    public void receivingPkts() {
        DatagramPacket incomingPacketLocal = null;
        DatagramPacket outgoingPacketLocal = null;
        //still true while receiving packets
        boolean isreceiving = true;
        int testlength = Integer.valueOf(param.getTimelength());
        //CdcVo cd = new CdcVo();
        int pps = CdcVo.returnPPSbyCodec(param.getCodec());
        byte[] buf = null;
        try {
            dgmsocket.setSoTimeout(TrfGenBo.U_T);
            int count = 0;
            while (isreceiving) {
                dgmsocket.receive(incomingPacketLocal);
                count++;
                System.out.println("count=" + count);
            }
            //System.out.println("[" + new Date() + "]\n - [" + threadName + "] packet: clientID:" + clientID + " is sent.");

        } catch (SocketTimeoutException se) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, se);
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
