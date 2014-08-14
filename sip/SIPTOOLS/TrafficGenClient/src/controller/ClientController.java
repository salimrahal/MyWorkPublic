/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bo.Networking;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author salim
 */
public class ClientController {
     public void sendDatagram(DatagramSocket datagramsocket,Integer portsrc, Integer portdest, String ipServer) throws SocketException {
        String agentName = "test";
        String iplocal = Networking.getLocalIpAddress();
        try {
            //datagramsocket = new DatagramSocket(test.getPortscr());
            String inviteMsg = buildInviteSIPMessage(ipServer, iplocal, "UDP", portsrc, portdest, "callId-value", agentName);
            byte[] abyteInv = inviteMsg.getBytes();
            System.out.println("sendDatagram:: data byte length:"+abyteInv.length);//length: 815
            InetAddress inetaddress1 = null;
            //inetaddress1 = InetAddress.getByAddress(abyte1);
            inetaddress1 = InetAddress.getByName(ipServer);
            //construct a packet that recieve data on the destination Addr and port specified by the constructor
         
            DatagramPacket datagrampacket = new DatagramPacket(abyteInv, abyteInv.length, inetaddress1, portdest);
            System.out.println("sendInvite sending INVITE packet..");
            datagramsocket.send(datagrampacket);
            System.out.println("sendInvite INVITE packet sent");
           

            byte abyteBuff[] = new byte[1024];
            datagramsocket.setSoTimeout(7000);
            //Constructs a DatagramPacket for receiving packets of length length.
            DatagramPacket datagrampacketRec = new DatagramPacket(abyteBuff, abyteBuff.length);
            System.out.println("sendInvite waiting for INVITE response..");
            datagramsocket.receive(datagrampacketRec);
            String recvMsg = new String(datagrampacketRec.getData(), 0, datagrampacketRec.getLength());
            byte recMsgByte[] = recvMsg.getBytes();
            System.out.println("sendInvite received INVITE message = [" + recvMsg + "]");
          
          
            //datagramsocket.close();
        } catch (SocketTimeoutException sockettimeoutexception) {
        
    
            System.out.println("sendRegister excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (IOException exception) {
            System.out.println("sendRegister excpetion:" + exception.getLocalizedMessage());
            //setresultmessage(outmsg);
        }
    }
     
      public String buildInviteSIPMessage(String ipServer, String ipLocalparam,String transport, Integer portsrc, Integer portdest, String callIdSent, String agentName) throws SocketException {
        String inviteMsgInner = (new StringBuilder()).append("v=0\r\no=- 54899656 54899656 IN IP4 ").append(ipLocalparam).append("\r\ns=-\r\nc=IN IP4 ").
                append(ipLocalparam).append("\r\nt=0 0\r\nm=audio 16482 RTP/AVP 0 8 18 100 101\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:18 G729a/8000\r\na=rtpmap:100 NSE/8000\r\na=fmtp:100 192-193\r\na=rtpmap:101 telephone-event/8000\r\na=fmtp:101 0-15\r\na=ptime:30\r\na=sendrecv\r\n").toString();
        int contentLength = inviteMsgInner.getBytes().length;

        String inviteMsg = (new StringBuilder()).append("INVITE sip:58569874@").append(ipServer).append(":").append(portdest).append(" SIP/2.0\r\nVia: SIP/2.0/").append(transport).append(" ").
                append(ipLocalparam).append(":").append(portsrc).append(";branch=z9hG4bK-467cc605\r\nFrom: SIP_ALG_DETECTOR <sip:58569874@").
                append(ipServer).append(portdest).append(">;tag=8e059c0484ff02ado0\r\nTo: <sip:58569874@").append(ipServer).append(":").append(portdest).
                append(">\r\nCall-ID: ").append(callIdSent).
                append("\r\nCSeq: 101 INVITE\r\nMax-Forwards: 70\r\nContact: SIP_ALG_DETECTOR <sip:58569874@").append(ipLocalparam).append(":").append(portsrc).
                append(">\r\nExpires: 60\r\nUser-Agent: ").append(agentName).append("\r\nContent-Length: ").
                append(contentLength).append("\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER\r\nSupported: replaces\r\nContent-Type: application/sdp\r\n\r\n").append(inviteMsgInner).toString();

        return inviteMsg;
    }
}
