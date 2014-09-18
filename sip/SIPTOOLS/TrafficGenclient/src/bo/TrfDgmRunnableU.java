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
            handleClienttraffic();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnableU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void handleClienttraffic() throws IOException, InterruptedException {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
        boolean res = sendingPkts(codec, timelength);
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
        System.out.println("sendingPkts:start sending[" + new Date() + "]\n -thread name= [" + Thread.currentThread().getName());
        PacketControl bc = new PacketControl(dgmsocket, addressDest, portDest);
        //bc.beepForAnHour();
        res = bc.sndPktForAnGivenTime(codec, timelength);
        System.out.println("sendingPkts:finish sending.");
        return res;
    }
}
