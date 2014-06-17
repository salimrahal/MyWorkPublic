/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algController;

import algBo.ALGBo;
import algGui.AlgJPanel;
import algVo.Test;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author salim
 */
public class ClientController {
//BO

    ALGBo algBo;
    int counter = 0;

    Properties properties;          // Other properties.
    //Extract the config File
    //ConfVO confVO = ConfBO.retrieveConfigurations("./conf/properties.xml");
    // ConfVO confVO = ConfVO.getInstance();
    // Objects keeping local configuration.
    String iplocal = null;
    String hostnameLocal;
    Integer portSrc1;
    Integer portSrc2;
    Integer portSrc3;
    Integer portSrc4;

    String extlocal;
    //Server Info
    String ipServer;
    Integer portServer1;
    Integer portServer2;
    Integer portServer3;
    Integer portServer4;

    String transport1;
    String transport2;
    String transport3;
    String transport4; // 
    int tag = (new Random()).nextInt(); // The local tag.
    //Address contactAddress;         // The contact address.
    //ContactHeader contactHeader;    // The contact header.
    public boolean retryAuth;
    public long seqReg = 0;
    public long seqSub = 0;
    /**
     * ******UI Parameters: **********
     */
    String toSipURI;
    //local SIP URI taken from the config
    String localSipURI = "sip:" + 201 + "@" + ipServer + ":" + transport1;
    String newline;
    //socket time out
    Integer socktimeout = 7000;

    /*
    ClientController: it instanciated an ALGBo and retrieved fron Singleton ConfVo the properties and assign it to BO 
    , then to the controller correspondant properties 
    */
    public ClientController() throws SocketException {
        algBo = new ALGBo();
        /*
        Retrieve confi values from singleton to the algBo properties
        */
        algBo.retrieveParamFromConfVo();
        /*
        read properties from the BO
        */
        extlocal = algBo.getSipIdLocal();
        iplocal = algBo.getIplocal();
        //get server configs
        ipServer = algBo.getIpServer();

        portSrc1 = algBo.getPortsrc1();
        portSrc2 = algBo.getPortsrc2();
        portSrc3 = algBo.getPortsrc3();
        portSrc4 = algBo.getPortsrc4();

        portServer1 = algBo.getPortdest1();
        portServer2 = algBo.getPortdest2();
        portServer3 = algBo.getPortdest3();
        portServer4 = algBo.getPortdest4();

        transport1 = algBo.getTransport1();
        transport2 = algBo.getTransport2();
        transport3 = algBo.getTransport3();
        transport4 = algBo.getTransport4();
        hostnameLocal = algBo.getAgentname();
        newline = "\n";
    }

    public ALGBo getAlgBo() {
        return algBo;
    }

    public void setAlgBo(ALGBo algBo) {
        this.algBo = algBo;
    }

    public String getIplocal() throws SocketException {
        return this.iplocal;
    }

    public String resetIplocal() throws SocketException {
        setIplocal(algBo.getIplocal());
        return getIplocal();
    }

    public void setIplocal(String iplocal) {
        this.iplocal = iplocal;
    }

    public String processRequests(Test test, JTextArea sentmsgReg, JTextArea recvjtextregister, JTextArea sentmsgInv, JTextArea recvjtextinvite) throws IOException {
        DatagramSocket datagramsocket = null;
        counter++;
        //result message no effect on the UI 
        setresultmessage(ALGBo.INPROGRESS);
        String outmsg;
        Integer portSrc = test.getPortscr();
        Integer portDest = test.getPortDest();
        //this callID is unique for both register and invite
        String callId = new StringBuilder().append(ALGBo.CALLID_PREFIX).append("@").append(iplocal).toString();
        if (test.getTransport().equalsIgnoreCase("udp")) {
            try {
                datagramsocket = new DatagramSocket(portSrc);
                Integer regiseRes = sendRegisterDatagram(datagramsocket, test, sentmsgReg, recvjtextregister, callId);
                //after send/receive the register successfully then send the invite
                if (regiseRes == 1) {
                    Integer inviteRes = sendInviteDatagram(datagramsocket, test, sentmsgInv, recvjtextinvite, callId);
                }
            } catch (SocketException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                outmsg = "Unknown Socket Error";
                setresultmessage(outmsg);
            } finally {
                if (datagramsocket != null) {
                    datagramsocket.close();
                }
            }
        }//end of udp
        else if (test.getTransport().equalsIgnoreCase("tcp")) {
            String serverHostname = ipServer;
            Socket echoSocket = null;

            PrintWriter out = null;
            BufferedReader in = null;
            try {
                //attemping to connect to the server
                System.out.println("Process Request: Attemping to connect to host " + serverHostname + " on port " + portDest + "/TCP.");
                //echoSocket = new Socket(serverHostname, portDest);
                echoSocket = new Socket();

                echoSocket.connect(new InetSocketAddress(serverHostname, portDest), ALGBo.TCP_TIMEOUT);
                System.out.println("Process Request: connected.");
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(
                        echoSocket.getInputStream()));
                Integer regiseRes = sendStream(ALGBo.REGISTER, echoSocket, test, sentmsgReg, recvjtextregister, callId, out, in);
                //only sends invite in case register is sent successfully
                if (regiseRes == 1) {
                    Integer inviteRes = sendStream(ALGBo.INVITE, echoSocket, test, sentmsgInv, recvjtextinvite, callId, out, in);
                } //todo add errors  
                echoSocket.close();
                out.close();
                in.close();
            } catch (UnknownHostException e) {
                outmsg = "sendStream: Don't know about host: " + serverHostname;
                System.err.println(outmsg);
                setresultmessage(outmsg);

            } catch (IOException iOException) {
                //handling firewall issue of TCP
                outmsg = ALGBo.MSG_FIREWALLISSUE;
                //"processRequests: Couldn't get I/O for "
                //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
                System.err.println(outmsg);
                setresultmessage(outmsg);
                setJtextRegisterTxt(outmsg, sentmsgReg, recvjtextregister);
            } finally {
                if (echoSocket != null) {
                    echoSocket.close();
                }
            }

        }
        return null;
    }

    public Integer sendStream(String SipMethod, Socket echoSocket, Test test, JTextArea sentmsgJtext, JTextArea recvJtext, String callId, PrintWriter out, BufferedReader in) throws IOException {
        String outmsg = null;
        Integer resCode = 0;
        //String callIdSent = new StringBuilder().append("11256979-ca11b60c@").append(iplocal).toString();
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();

        try {
            String msgToSend;
            if (SipMethod.equalsIgnoreCase(ALGBo.REGISTER)) {
                msgToSend = algBo.buildRegisterSIPMessage(ipServer, iplocal, "TCP", portsrc, portdest, callId, agentName);
            } else {
                msgToSend = algBo.buildInviteSIPMessage(ipServer, iplocal, "TCP", portsrc, portdest, callId, agentName);
            }

            StringReader msgreader = new StringReader(msgToSend);
            BufferedReader msgbr = new BufferedReader(msgreader);

            String msgRecv;
            String submsgToSend;
            StringBuilder strbuilder = new StringBuilder();

            int val = 0;
            char[] buffer = new char[256];

//sending the message
            while ((submsgToSend = msgbr.readLine()) != null) {
                //write to the server
                out.println(submsgToSend);
                //recieve from the server,
                //TODO: in some case it will freeze here nothing is received
                try {
                    msgRecv = in.readLine();
                    System.out.println("echo: " + msgRecv);
                    strbuilder.append(msgRecv).append("\r\n");
                } catch (IOException iOException) {
                    System.err.println("sendStream Error:" + iOException.getLocalizedMessage());
                    //setJtextRegisterTxt( outmsg, JTextArea sentmsgReg, JTextArea recvjtextregister) {
                    break;
                } 
            }
            msgRecv = strbuilder.toString();
            System.out.println("all message received=[" + msgRecv + "]");
            sentmsgJtext.setText(new StringBuilder().append("New Packet Sent:").append(newline).append(msgToSend).toString());
            sentmsgJtext.setCaretPosition(0);
            recvJtext.setText(new StringBuilder().append("New Packet Received:").append(newline).append(msgRecv).toString());
            recvJtext.setCaretPosition(0);

            //TODO: fill the sent text field
            if (msgToSend.equalsIgnoreCase(msgRecv)) {
                outmsg = ALGBo.MSG_SipALGNotFound;

            } else {
                // check the caller-ID
                //retreive received caller Id
                outmsg = algBo.algcheck(msgRecv, callId);
            }
            setresultmessage(outmsg);
            //echoSocket.close();
            resCode = 1;
        } catch (IOException ex) {
            System.err.println("sendStream Error:" + ex.getLocalizedMessage());
        }
        return resCode;
    }

    public Integer sendRegisterDatagram(DatagramSocket datagramsocket, Test test, JTextArea sentmsgReg, JTextArea recvjtextregister, String callId) throws IOException {
        String outmsg = null;
        byte abyteReg[] = null;
        Integer resCode = 0;

        //DatagramSocket datagramsocket = null;
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();
        DatagramPacket datagrampacket = null;
        InetAddress inetaddress1 = null;
        //inetaddress1 = InetAddress.getByAddress(abyte1);
        inetaddress1 = InetAddress.getByName(ipServer);
        try {
            //datagramsocket = new DatagramSocket(test.getPortscr());
            //-------------------------send/receive the register---------------------------//
            String registerMsg = "";
            registerMsg = algBo.buildRegisterSIPMessage(ipServer, iplocal, "UDP", portsrc, portdest, callId, agentName);
            abyteReg = registerMsg.getBytes();

            //construct a packet that recieve data on the destination Addr and port specified by the constructor
            System.out.println("sendRegisterStateful inetaddress1=" + inetaddress1.getHostAddress() + "- counter=" + counter);
            datagrampacket = new DatagramPacket(abyteReg, abyteReg.length, inetaddress1, portdest);
            System.out.println("sendRegisterStateful sending REGISTER packet..");
            datagramsocket.send(datagrampacket);
            System.out.println("sendRegisterStateful REGISTER packet sent");
            //int k = datagramsocket.getLocalPort();
            sentmsgReg.setText(new StringBuilder().append("New Packet Sent:").append(newline).append(registerMsg).toString());
            //set the caret to the top always
            sentmsgReg.setCaretPosition(0);

            byte abyteBuff[] = new byte[1024];
            datagramsocket.setSoTimeout(socktimeout);
            //Constructs a DatagramPacket for receiving packets of length length.
            DatagramPacket datagrampacketRecReg = new DatagramPacket(abyteBuff, abyteBuff.length);
            System.out.println("sendRegisterStateful waiting REGISTER for response..");
            datagramsocket.receive(datagrampacketRecReg);
            String recvMsg = new String(datagrampacketRecReg.getData(), 0, datagrampacketRecReg.getLength());
            byte recMsgByte[] = recvMsg.getBytes();
            System.out.println("sendRegister REGISTER received message = [" + recvMsg + "]");
            if (recvMsg.equals(registerMsg)) {
                outmsg = algBo.MSG_SipALGNotFound;
            } else {
                // check the caller-ID
                //retreive received caller Id
                outmsg = algBo.algcheck(recvMsg, callId);
            }
            setresultmessage(outmsg);
            recvjtextregister.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
            recvjtextregister.setCaretPosition(0);
            //close the socket
            //datagramsocket.close();
            resCode = 1;
        } catch (SocketTimeoutException sockettimeoutexception) {
            recvjtextregister.setText(new StringBuilder().append(newline).append("[No Packet Received]").append(algBo.MSG_FIREWALLISSUE).toString());
            outmsg = algBo.MSG_FIREWALLISSUE;
            setresultmessage(outmsg);
            System.out.println("sendRegister excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (IOException exception) {
            System.out.println("sendRegister excpetion:" + exception.getLocalizedMessage());
            outmsg = algBo.MSG_FIREWALLISSUE;
            setresultmessage(outmsg);
        }
        return resCode;
    }

    public Integer sendInviteDatagram(DatagramSocket datagramsocket, Test test, JTextArea sentmsgInv, JTextArea recvjtextinvite, String callId) {
        String outmsg;
        Integer resCode = 0;
        //DatagramSocket datagramsocket = null;
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();
        try {
            //datagramsocket = new DatagramSocket(test.getPortscr());
            String inviteMsg = algBo.buildInviteSIPMessage(ipServer, iplocal, "UDP", portsrc, portdest, callId, agentName);
            byte[] abyteInv = inviteMsg.getBytes();
            InetAddress inetaddress1 = null;
            //inetaddress1 = InetAddress.getByAddress(abyte1);
            inetaddress1 = InetAddress.getByName(ipServer);
            //construct a packet that recieve data on the destination Addr and port specified by the constructor
            System.out.println("sendInvite inetaddress1=" + inetaddress1.getHostAddress() + "- counter=" + counter);
            DatagramPacket datagrampacket = new DatagramPacket(abyteInv, abyteInv.length, inetaddress1, portdest);
            System.out.println("sendInvite sending INVITE packet..");
            datagramsocket.send(datagrampacket);
            System.out.println("sendInvite INVITE packet sent");
            //int k = datagramsocket.getLocalPort();
            sentmsgInv.setText(new StringBuilder().append("New Packet Sent:").append(newline).append(inviteMsg).toString());
            //set the caret to the top always
            sentmsgInv.setCaretPosition(0);

            byte abyteBuff[] = new byte[1024];
            datagramsocket.setSoTimeout(socktimeout);
            //Constructs a DatagramPacket for receiving packets of length length.
            DatagramPacket datagrampacketRec = new DatagramPacket(abyteBuff, abyteBuff.length);
            System.out.println("sendInvite waiting for INVITE response..");
            datagramsocket.receive(datagrampacketRec);
            String recvMsg = new String(datagrampacketRec.getData(), 0, datagrampacketRec.getLength());
            byte recMsgByte[] = recvMsg.getBytes();
            System.out.println("sendInvite received INVITE message = [" + recvMsg + "]");
            if (recvMsg.equals(inviteMsg)) {
                outmsg = algBo.MSG_SipALGNotFound;
            } else {
                // check the caller-ID
                //retreive received caller Id
                outmsg = algBo.algcheck(recvMsg, callId);
            }
            setresultmessage(outmsg);
            recvjtextinvite.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
            recvjtextinvite.setCaretPosition(0);
            resCode = 1;
            //datagramsocket.close();
        } catch (SocketTimeoutException sockettimeoutexception) {
            recvjtextinvite.setText(new StringBuilder().append(newline).append("[No Packet Received]").append(ALGBo.MSG_FIREWALLISSUE).toString());
            outmsg = ALGBo.MSG_FIREWALLISSUE;
            setresultmessage(outmsg);
            System.out.println("sendRegister excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (IOException exception) {
            System.out.println("sendRegister excpetion:" + exception.getLocalizedMessage());
            outmsg = exception.getLocalizedMessage();
            setresultmessage(outmsg);
        }
        return resCode;
    }

    public void setJtextRegisterTxt(String outmsg, JTextArea sentmsgReg, JTextArea recvjtextregister) {
        sentmsgReg.setText(new StringBuilder().append(outmsg).toString());
        sentmsgReg.setCaretPosition(0);
        recvjtextregister.setText(new StringBuilder().append(outmsg).toString());
        recvjtextregister.setCaretPosition(0);
    }
    /*
    
     */

    public void setresultmessage(String outmessage) {
        //AlgJPanel.resultmsgjlabel.setText(outmessage);        
        String labelText = String.format("<html><div style=\"width:%dpx;\"><p align=\"center\">%s</p></div><html>", 200, outmessage);
        AlgJPanel.resultmsgjlabel.setText(labelText);
        AlgJPanel.resultmsgjlabel.setBackground(Color.red);
    }

}
