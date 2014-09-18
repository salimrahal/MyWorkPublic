package sipserver.trf;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.bo.VpMethds;
import sipserver.trf.vp.vo.CdcVo;

/**
 *
 * @author salim 
 *  This thread used to recieve the traffic using the port ip of the client
 * handling traffic
 * a- responsible for receiving and counting the
 * packet lost 
 * b- save the result into the DB: client name, clientpublicIP,
 * uppacketLost, starttime 
 * c- release the used for: update Ports table and set
 * the used port to free
 */
public class TrfDgmRunnableOut implements Runnable {

    private int clientID;
    //private int bytesToReceive;
    private DatagramSocket dgmsocket;
    //private DatagramPacket incomingPacket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;

    public TrfDgmRunnableOut(Param param, InetAddress addressDest, int clientID) throws IOException {
        this.param = param;
//this.incomingPacket = incomingPacket;
        this.clientID = clientID;
        this.addressDest = addressDest;
        this.portsrc = Integer.valueOf(param.getPortrfClientD());
        this.portDest = Integer.valueOf(param.getPortrfClientD());
        dgmsocket = new DatagramSocket(this.portsrc);

    }

    @Override
    public void run() {
        try {
            System.out.println("TrfDgmRunnableOut::thread name=" + Thread.currentThread().getName() + " is Started");
            handleClienttraffic();
            System.out.println("TrfDgmRunnableOut::thread name=" + Thread.currentThread().getName() + " is completed");
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void handleClienttraffic() throws IOException, InterruptedException, Exception {
        String codec = param.getCodec();
        int timelength = Integer.valueOf(param.getTimelength());
            //it sends the packet
        // close the connection or socket
        sendingPkts(codec, timelength);
    }

    /**
     * 1- it sends the packet 2- close the connection or socket
     *
     * @param codec
     * @param timelength
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendingPkts(String codec, int timelength) throws IOException, InterruptedException {
        System.out.println("sendingPkts:start..");
        PacketControl bc = new PacketControl(dgmsocket, addressDest, portDest);
        /*1- it sends back packets to client
         2- then release the port traffic
         */
        bc.sndPktForAnGivenTime(codec, timelength, this.portsrc);
        System.out.println("sendingPkts:finish sending.");

    }
}
