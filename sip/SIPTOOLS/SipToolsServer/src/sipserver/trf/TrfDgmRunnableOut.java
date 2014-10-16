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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.vo.CdcVo;

/**
 *
 * @author salim This thread used to recieve the traffic using the port ip of
 * the client handling traffic a- responsible for receiving and counting the
 * packet lost b- save the result into the DB: client name, clientpublicIP,
 * uppacketLost, starttime c- release the used for: update Ports table and set
 * the used port to free
 */
public class TrfDgmRunnableOut implements Runnable {

    private int clientID;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;

    public TrfDgmRunnableOut(Param param, InetAddress addressDest, int portsrc, int portDest) throws IOException {
        this.param = param;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portDest;
        dgmsocket = new DatagramSocket(this.portsrc);
        this.trfdao = new TrfDao();
    }

    @Override
    public void run() {
        System.out.println("TrfDgmRunnableOut:: Priority=" + Thread.currentThread().getPriority());
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
        /*
         1- receive the flag packet from the client
         2- extract the addressInco and portInco
         3- pass them to the sendpacket function
         */
        byte[] buf = new byte[8];
        DatagramPacket incomingPacketLocal = new DatagramPacket(buf, buf.length);
        System.out.println("TrfDgmRunnableOut:handleClienttraffic:: waiting for flag Pkt...listening on address=" + dgmsocket.getLocalAddress().getHostAddress() + ";port=" + dgmsocket.getLocalPort());
        try {
            dgmsocket.setSoTimeout(TrfBo.Packet_Max_Delay);
            dgmsocket.receive(incomingPacketLocal);
            System.out.println("TrfDgmRunnableOut:handleClienttraffic:receives a flag packet ");
            InetAddress inetAddrInco = incomingPacketLocal.getAddress();
            int portInco = incomingPacketLocal.getPort();
            DatagramPacket outgoingPacketLocal = new DatagramPacket(buf, buf.length, inetAddrInco, portInco);
            System.out.println("TrfDgmRunnableOut:handleClienttraffic:sending back the flag packet");
            dgmsocket.send(outgoingPacketLocal);
            sendingPkts(codec, timelength, addressDest, portDest);
        } catch (SocketTimeoutException se) {
            //release the port here in case there is a time out exception
            boolean released = trfdao.updateOnePortStatus(this.portsrc, "f");
            System.out.println("TrfDgmRunnableOut::Error:receiving flag::" + se.getMessage() + "/releasing the port=" + portsrc + "/released=" + released);
            System.out.println("TrfDgmRunnableOut:in Catch...:closing the socket..");
            dgmsocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TrfDgmRunnableOut.class.getName()).log(Level.SEVERE, null, ex);
            //releasing the port is done in clientTCPconnection
            //boolean released = trfdao.updateOnePortStatus(this.portsrc, "f");
            //System.out.println("TrfDgmRunnableOut::Error:receiving flag::" + ex.getMessage() + "/releasing the port=" + released);
            System.out.println("TrfDgmRunnableOut:in Catch..:closing the socket..");
            dgmsocket.close();
        }finally{
              System.out.println("TrfDgmRunnableOut:handleClienttraffic: finally clause");
        }
    }

    public void sendingPkts(String codec, int timelength, InetAddress inetAddrInco, int portInco) throws IOException, InterruptedException {
        System.out.println("sending Pkts:start..");
        PacketControl bc = new PacketControl(dgmsocket, inetAddrInco, portInco);
        /*1- it sends back packets to client
         2- then release the port traffic
         */
        bc.sndPktForAnGivenTime(codec, timelength, this.portsrc);
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
    }

    /*
     for test
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        String destAddress = "127.0.0.1";
        Param param2 = new Param();
        param2.setCodec(CdcVo.CODEC_G729);
        param2.setTimelength("15");
        int portsrc = 6000;
        int portdest = 5108;
        InetAddress inetaddressDest = InetAddress.getByName(destAddress);
        //run the thread that sends the traffic
        TrfDgmRunnableOut trfDgmRunnableOut = new TrfDgmRunnableOut(param2, inetaddressDest, portsrc, portdest);
        Thread trfDgmThreadOut = new Thread(trfDgmRunnableOut);
        trfDgmThreadOut.start();
    }
}
