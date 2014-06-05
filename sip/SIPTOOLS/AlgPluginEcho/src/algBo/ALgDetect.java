/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo;

import algController.ClientController;
import algGui.AlgJPanel;
import static algGui.AlgJPanel.comb1RcvMsgREG;
import static algGui.AlgJPanel.comb1SentMsgREG;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.util.Enumeration;

/**
 *
 * @author salim
 */
public class ALgDetect {

    public String algDetect() {
        String res = "";
        String found = "SIP ALG DETECTED";
        String notfound = "No SIP ALG";
        String serverIp = "209.208.79.151";
        ALGBo alBo = new ALGBo();
        Integer serverPort = alBo.portdest1;
        ALGBo algbo = new ALGBo();
        DatagramSocket datagramsocket = null;
        int i = 0;
        boolean flag = false;
        String s1 = new String();
        boolean flag1 = false;

        try {
           String sipmessageREG = algbo.getSimpleSIPMessage("REGISTER");
            System.out.println("Message to send:"+sipmessageREG);
           //String sipmessageINV = ALGBo.getSimpleSIPMessage("INVITE");
            datagramsocket = new DatagramSocket(i);

            byte[] abyteREG = sipmessageREG.getBytes();
            //byte[] abyteINV = sipmessageINV.getBytes();
            
            InetAddress inetaddress1 = null;
            //inetaddress1 = InetAddress.getByAddress(abyte1);
            inetaddress1 = InetAddress.getByName(serverIp);
            //construct a packet that recieve data on the destination Addr and port specified by the constructor
            System.out.println("Server inetaddress=" + inetaddress1.getHostAddress());
            DatagramPacket datagrampacket = new DatagramPacket(abyteREG, abyteREG.length, inetaddress1, serverPort);
            //DatagramPacket datagrampacketINV = new DatagramPacket(abyteINV, abyteINV.length, inetaddress1, serverPort);
            datagramsocket.send(datagrampacket);
            //datagramsocket.send(datagrampacketINV);
            int k = datagramsocket.getLocalPort();
            StringBuilder msgsent = new StringBuilder("\n");
            msgsent.append("[New Packet Sent]").append("\n").append(sipmessageREG);
            AlgJPanel.comb1SentMsgREG.setText(msgsent.toString());
            comb1SentMsgREG.setCaretPosition(0);
            
            byte abyte2[] = new byte[1024];
            for (byte byte0 = 5; byte0 > 2; byte0 = 1) {
                datagramsocket.setSoTimeout(7000);
                //Constructs a DatagramPacket for receiving packets of length length.
                DatagramPacket datagrampacket1 = new DatagramPacket(abyte2, abyte2.length);
                datagramsocket.receive(datagrampacket1);
                String s5 = new String(datagrampacket1.getData(), 0, datagrampacket1.getLength());
                System.out.println("Packet received:" + s5);
                
                AlgJPanel.comb1RcvMsgREG.setText(s5);
                comb1RcvMsgREG.setCaretPosition(0);
                byte abyte3[] = s5.getBytes();
                if (s5.equals(sipmessageREG)) {
                    System.out.println("No sip ALG");
                    res = notfound;
                } else {
                    System.out.println("Found sip ALG");
                    res = found;
                }
                datagramsocket.close();
            }

        } catch (SocketTimeoutException sockettimeoutexception) {
//            receivearea.append((new StringBuilder()).append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
//            algstatus.setForeground(Color.red);
//            algstatus.setText("SIP ALG/Firewall Problem Found");
            datagramsocket.close();
        } catch (Exception exception) {
//            algstatus.setForeground(Color.red);
//            algstatus.setText("Unknown Socket Error");
            datagramsocket.close();
        }
        return res;
    }
    
    public static void sendData(String sipmessage){
        
    }

}
