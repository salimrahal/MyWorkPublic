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
 * @author salim
 */
public class TrfDgmRunnableOut implements Runnable {

    private int clientID;
    private DatagramSocket dgmsocket;
    InetAddress addressDest;
    Integer portsrc;
    Integer portDest;
    Param param;
    TrfDao trfdao;

    public TrfDgmRunnableOut(DatagramSocket dgmsocketOut,Param param, InetAddress addressDest, int portsrc, int portDest) throws IOException {
        this.param = param;
        this.addressDest = addressDest;
        this.portsrc = portsrc;
        this.portDest = portDest;
        this.dgmsocket = dgmsocketOut;
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
          
            System.out.println("TrfDgmRunnableOut:in Catch..:closing the socket..");
            dgmsocket.close();
        }finally{
              System.out.println("TrfDgmRunnableOut:handleClienttraffic: finally clause");
        }
    }

    public void sendingPkts(String codec, int timelength, InetAddress inetAddrInco, int portInco) throws IOException, InterruptedException {
        System.out.println("sending Pkts:start..");
        PacketControl bc = new PacketControl(dgmsocket, inetAddrInco, portInco);
       
        bc.sndPktForAnGivenTime(codec, timelength, this.portsrc);
    }

 
    public void sendingPkts(String codec, int timelength) throws IOException, InterruptedException {
        System.out.println("sendingPkts:start..");
        PacketControl bc = new PacketControl(dgmsocket, addressDest, portDest);
    
        bc.sndPktForAnGivenTime(codec, timelength, this.portsrc);
    }

    /*
     for test
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
     
    }
}
