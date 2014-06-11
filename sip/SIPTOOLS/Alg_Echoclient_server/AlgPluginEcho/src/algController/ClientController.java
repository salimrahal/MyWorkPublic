/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algController;

import algBo.ALGBo;
import algBo.Networking;
import static algBo.Networking.getLocalIpAddress;
import algGui.AlgJPanel;
import algVo.Test;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.TooManyListenersException;
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

    //String requestURITextField = "sip:" + 201 + "@" + ipServer + ":" + portServer;
    public ClientController() throws SocketException {
        algBo = new ALGBo();
        /*parse xml file and retrieve values of port/tranport etc
         >>>>>>>>>>>>>Triger the below Line when using the XML file<<<<<<<<<<<<<
         algBo.performConfiParsing()
         */
        extlocal = algBo.getExtlocal();
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
        newline = new String("\n");
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
        Integer regiseRes;
        Integer inviteRes;
        String outmsg;
        Integer portDest = test.getPortscr();
        if (test.getTransport().equalsIgnoreCase("udp")) {
            try {
                datagramsocket = new DatagramSocket(portDest);
                regiseRes = sendRegisterDatagram(datagramsocket, test, sentmsgReg, recvjtextregister);
                //after send/receive the register successfully then send the invite
                if (regiseRes == 1) {
                    inviteRes = sendInviteDatagram(datagramsocket, test, sentmsgInv, recvjtextinvite);
                } else if (regiseRes == -1) {
                    outmsg = algBo.MSG_FIREWALLISSUE;
                    setresultmessage(outmsg);
                    recvjtextregister.setText(new StringBuilder().append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
                } else if (regiseRes == -2) {
                    outmsg = "Unknown Socket Error";
                    setresultmessage(outmsg);
                    recvjtextregister.setText(new StringBuilder().append(newline).append(outmsg).toString());
                }
            } catch (SocketException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (datagramsocket != null) {
                    datagramsocket.close();
                }
            }
        }//end of udp
        else if (test.getTransport().equalsIgnoreCase("tcp")) {
             String serverHostname = ipServer;
        Socket echoSocket = new Socket(serverHostname, portDest);
            sendRegisterStream(echoSocket, test, sentmsgReg, recvjtextregister);
        }

        return null;
    }

    public Integer sendRegisterStream(Socket echosocket, Test test, JTextArea sentmsgReg, JTextArea recvjtextregister) throws IOException {
        String outmsg = null;
        Integer resCode = 0;
        String callIdSent = new StringBuilder().append("11256979-ca11b60c@").append(iplocal).toString();
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();
        //String serverHostname = new String ("127.0.0.1");
        String serverHostname = ipServer;
        System.out.println("Attemping to connect to host "
                + serverHostname + " on port "+portdest+".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket(serverHostname, portdest);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));

        String registerMsg = algBo.buildRegisterSIPMessage(ipServer, iplocal, portsrc, portdest, callIdSent, agentName);

        out.println(registerMsg);
        //in only reads line I should read all the message
        System.out.println("echo: " + in.readLine());

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
        return resCode;
    }

    public Integer sendRegisterDatagram(DatagramSocket datagramsocket, Test test, JTextArea sentmsgReg, JTextArea recvjtextregister) throws IOException {
        String outmsg = null;
        byte abyteReg[] = null;
        Integer resCode = 0;

        String callIdSent = new StringBuilder().append("11256979-ca11b60c@").append(iplocal).toString();
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
            registerMsg = algBo.buildRegisterSIPMessage(ipServer, iplocal, portsrc, portdest, callIdSent, agentName);
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
                outmsg = algBo.algcheck(recvMsg, callIdSent);
            }
            setresultmessage(outmsg);
            recvjtextregister.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
            recvjtextregister.setCaretPosition(0);
                //close the socket
            //datagramsocket.close();
            resCode = 1;
        } catch (SocketTimeoutException sockettimeoutexception) {
            resCode = -1;
            recvjtextregister.setText(new StringBuilder().append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
            outmsg = algBo.MSG_FIREWALLISSUE;
            setresultmessage(outmsg);
            datagramsocket.close();
            System.out.println("sendRegister excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (Exception exception) {
            resCode = -2;
            System.out.println("sendRegister excpetion:" + exception.getLocalizedMessage());
            outmsg = "Unknown Socket Error";
            setresultmessage(outmsg);
            datagramsocket.close();

        }

        return resCode;
    }

    public Integer sendInviteDatagram(DatagramSocket datagramsocket, Test test, JTextArea sentmsgInv, JTextArea recvjtextinvite) {
        String outmsg;
        Integer resCode = 0;
        String callIdSent = new StringBuilder().append("ce8cc10-3400b211@").append(iplocal).toString();
        //DatagramSocket datagramsocket = null;
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();
        try {
            //datagramsocket = new DatagramSocket(test.getPortscr());
            String inviteMsg = algBo.buildInviteSIPMessage(ipServer, iplocal, portsrc, portdest, callIdSent, agentName);
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
                outmsg = algBo.algcheck(recvMsg, callIdSent);
            }
            setresultmessage(outmsg);
            recvjtextinvite.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
            recvjtextinvite.setCaretPosition(0);
            resCode = 1;
            //datagramsocket.close();
        } catch (SocketTimeoutException sockettimeoutexception) {
            recvjtextinvite.setText(new StringBuilder().append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
            outmsg = "SIP ALG/Firewall Problem Found";
            setresultmessage(outmsg);
            datagramsocket.close();
            System.out.println("sendInvite excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (Exception exception) {
            outmsg = "Unknown Socket Error";
            setresultmessage(outmsg);
            datagramsocket.close();

        }
        return null;
    }

    @Deprecated
    public String sendSipMsgs(Test test, JTextArea sentmsgReg, JTextArea recvjtextregister, JTextArea sentmsgInv, JTextArea recvjtextinvite) throws IOException {
        String outmsg;
        byte abyteReg[] = null;
        counter++;
        String callIdSent = new StringBuilder().append("11256979-ca11b60c@").append(iplocal).toString();
        if (test.getTransport().equalsIgnoreCase("udp")) {
            DatagramSocket datagramsocket = null;
            Integer portdest = test.getPortDest();
            Integer portsrc = test.getPortscr();
            String agentName = algBo.getAgentname();
            DatagramPacket datagrampacket = null;
            InetAddress inetaddress1 = null;
            //inetaddress1 = InetAddress.getByAddress(abyte1);
            inetaddress1 = InetAddress.getByName(ipServer);
            try {
                datagramsocket = new DatagramSocket(test.getPortscr());

                //-------------------------send/receive the register---------------------------//
                String registerMsg = "";
                registerMsg = algBo.buildRegisterSIPMessage(ipServer, iplocal, portsrc, portdest, callIdSent, agentName);
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
                datagramsocket.setSoTimeout(7000);
                //Constructs a DatagramPacket for receiving packets of length length.
                DatagramPacket datagrampacketRecReg = new DatagramPacket(abyteBuff, abyteBuff.length);
                System.out.println("sendRegisterStateful waiting REGISTER for response..");
                datagramsocket.receive(datagrampacketRecReg);
                String recvMsg = new String(datagrampacketRecReg.getData(), 0, datagrampacketRecReg.getLength());
                byte recMsgByte[] = recvMsg.getBytes();
                System.out.println("sendRegisterStateful REGISTER received message = [" + recvMsg + "]");
                if (recvMsg.equals(registerMsg)) {
                    outmsg = algBo.MSG_SipALGNotFound;
                } else {
                    // check the caller-ID
                    //retreive received caller Id
                    outmsg = algBo.algcheck(recvMsg, callIdSent);
                }
                setresultmessage(outmsg);
                recvjtextregister.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
                recvjtextregister.setCaretPosition(0);

            } catch (SocketTimeoutException sockettimeoutexception) {
                recvjtextregister.setText(new StringBuilder().append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
                outmsg = algBo.MSG_FIREWALLISSUE;
                setresultmessage(outmsg);
                datagramsocket.close();
            }
//-------------------------End of send/receive register-------//

            //-------------------------send the invite---------------------------//
            try {
                byte[] abyteInv = null;

                String inviteMsg = algBo.buildInviteSIPMessage(ipServer, iplocal, portsrc, portdest, callIdSent, agentName);
                abyteInv = inviteMsg.getBytes();
                System.out.println("sendInvite inetaddress1=" + inetaddress1.getHostAddress() + " - counter=" + counter);
                datagrampacket = new DatagramPacket(abyteInv, abyteInv.length, inetaddress1, portdest);
                System.out.println("sendInvite sending INVITE packet..");
                datagramsocket.send(datagrampacket);
                System.out.println("sendInvite INVITE packet sent");

                sentmsgInv.setText(new StringBuilder().append("New Packet Sent:").append(newline).append(inviteMsg).toString());
                //set the caret to the top always
                sentmsgInv.setCaretPosition(0);

                byte abyteBuffInv[] = new byte[1024];
                datagramsocket.setSoTimeout(7000);
                //Constructs a DatagramPacket for receiving packets of length length.
                DatagramPacket datagrampacketRecInv = new DatagramPacket(abyteBuffInv, abyteBuffInv.length);
                System.out.println("sendInvite waiting for INVITE response..");
                datagramsocket.receive(datagrampacketRecInv);
                String recvMsgInv = new String(datagrampacketRecInv.getData(), 0, datagrampacketRecInv.getLength());
                byte recMsgByteInv[] = recvMsgInv.getBytes();
                System.out.println("sendInvite received message = [" + recvMsgInv + "]");
                if (recvMsgInv.equals(inviteMsg)) {
                    outmsg = algBo.MSG_SipALGNotFound;
                } else {
                    // check the caller-ID
                    //retreive received caller Id
                    outmsg = algBo.algcheck(recvMsgInv, callIdSent);
                }
                setresultmessage(outmsg);
                recvjtextinvite.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsgInv).toString());
                recvjtextinvite.setCaretPosition(0);
                 //-------------------------End of send/receive Invite-------//

                //close the socket
                datagramsocket.close();

            } catch (SocketTimeoutException sockettimeoutexception) {
                //recvjtextregister.setText(new StringBuilder().append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
                recvjtextinvite.setText(new StringBuilder().append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
                outmsg = algBo.MSG_FIREWALLISSUE;
                setresultmessage(outmsg);
                datagramsocket.close();
            } catch (Exception exception) {
                outmsg = "Unknown Socket Error";
                setresultmessage(outmsg);
                datagramsocket.close();
            }
        } else if (test.getTransport().equalsIgnoreCase("tcp")) {

        }
        return null;
    }

//    public Request generateFreshBasicRequest(String fromAdress, String destAdresstextfield, String ipLocal, SipProvider sippro, String method, Test comb, Integer expiresparam) throws ParseException, InvalidArgumentException, SocketException {
//        /*3261: CSEQ:
//         * “ When a UAC resubmits a request with its credentials after receiving a
//         401 (Unauthorized) or 407 (Proxy Authentication Required) response,
//         it MUST increment the CSeq header field value as it would normally
//         when sending an updated request.
//         “
//         */
//        seqReg++;
//        Integer expires = expiresparam;
//        Address contactAddress;         // The contact address.
//        ContactHeader contactHeader;    // The contact header.
//        // Create the contact address used for all SIP messages.
//        contactAddress = this.addressFactory.createAddress("sip:" + this.extlocal + "@" + ipLocal + ":" + comb.getPortscr() + ";transport=" + comb.getTransport());
//        // Create the contact header used for all SIP messages.
//        contactHeader = this.headerFactory.createContactHeader(contactAddress);
//
//        // Get the destination address from the text field.
//        Address addressTo = this.addressFactory.createAddress(destAdresstextfield);
//        // Create the request URI for the SIP message.
//        javax.sip.address.URI requestURI = addressTo.getURI();
//
//        // Create the SIP message headers.
//        // The "Via" headers.
//        ArrayList viaHeaders = new ArrayList();
//        ViaHeader viaHeader = this.headerFactory.createViaHeader(getIplocal(), comb.getPortscr(), comb.getTransport(), null);
//        viaHeaders.add(viaHeader);
//
//        //Adding the branch param
//        int tagBranch = (new Random()).nextInt(); // The local tag.
//        viaHeader.setBranch("z9hG4bK" + tagBranch);
//
//        // The "Max-Forwards" header.
//        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
//        // The "Call-Id" header.
//        CallIdHeader callIdHeader = sippro.getNewCallId();
//
//        // The "CSeq" header.
//        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seqReg, method);
//
//        // The "From" header.
//        Address addressFrom = this.addressFactory.createAddress(fromAdress);
//        FromHeader fromHeader = this.headerFactory.createFromHeader(addressFrom, String.valueOf(this.tag));
//
//        // The "To" header.
//        ToHeader toHeader = this.headerFactory.createToHeader(addressTo, null);
//
//        // create expires header
//        ExpiresHeader expHd = this.headerFactory.createExpiresHeader(expires);
//        // Create the REGISTER request.
//        Request request = this.messageFactory.createRequest(
//                requestURI,
//                method,
//                callIdHeader,
//                cSeqHeader,
//                fromHeader,
//                toHeader,
//                viaHeaders,
//                maxForwardsHeader);
//        // Add the "Contact" header to the request.
//        request.addHeader(contactHeader);
//        // The "UserAgent" headers
//        ArrayList<String> uaHeaders = new ArrayList();
//        uaHeaders.add(hostnameLocal);
//        UserAgentHeader uaHeader = this.headerFactory.createUserAgentHeader(uaHeaders);
//        request.addHeader(uaHeader);
//        // add expires header
//        request.addHeader(expHd);
//        //add allow header      
//        AllowHeader allowHead = this.headerFactory.createAllowHeader("ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE");
//        request.addHeader(allowHead);
//        SupportedHeader suppHead = this.headerFactory.createSupportedHeader("replaces");
//        request.addHeader(suppHead);
//
//        //request = this.messageFactory.createRequest(ALGBo.getSimpleSIPMessage());
//        System.out.println(request.toString());
//        return request;
//
//    }
//
//   
//
//    //it takes the combination including the sequence number: 1, udp, 5060, 5060
//    public String sendRegisterStateful(Test combination) {
//        String res = "";
//        SipProvider sipPro = null;
//        Request request = null;
//        String iplocaltmp;
//        // A method called when you click on the "Reg (SL)" button.
//        try {
//            // Get the destination address from the text field.
//            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
//            //register here has the same URI to and FROM
//            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortscr();
//            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortDest();
//            sipPro = getSipProvider(combination.getSeqNumber());
//            if (sipPro != null) {
//                iplocaltmp = resetIplocal();
//                System.out.println("after reset:iplocal" + iplocaltmp);
//                if (!iplocaltmp.equalsIgnoreCase(Networking.LOOPBACK)) {
//                    request = generateFreshBasicRequest(addressFromStr, addressToStr, iplocaltmp, sipPro, "REGISTER", combination, 27);
//                    // Create a new SIP client transaction.
//                    ClientTransaction transaction = sipPro.getNewClientTransaction(request);
//                    //Send the request statefully, through the client transaction.
//                    transaction.sendRequest();
//                 
//                    // Display the message in the text area.
//                    res = ("Request sent:\n" + request.toString() + "\n\n");
//                } else {
//                    res = ALGBo.getNetworkError(iplocaltmp);
//                    setresultmessage(res);
//                }//end of else not loopback
//            }//end SIpPro !null
//            else {
//                res = ALGBo.PLUGIN_REINSTALL;
//                setresultmessage(res);
////createSipStack();
//                //createSipFrameWork();
//            }
//
//        } catch (Exception e) {
//            // If an error occurred, display the error.
//            res = "Request sent failed: " + e.getMessage() + "\n";
//            setresultmessage(res);
//        }
//        return res;
//    }
//
//    public String sendInvite(Test combination) {
//        String res = "";
//        SipProvider sipPro = null;
//        String iplocaltmp;
//        // A method called when you click on the "Reg (SL)" button.
//        try {
//            // Get the destination address from the text field.
//            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
//            //register here has the same URI to and FROM
//            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortscr();
//            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortDest();
//            sipPro = getSipProvider(combination.getSeqNumber());
//            if (sipPro != null) {
//                iplocaltmp = resetIplocal();
//                System.out.println("after reset:iplocal" + iplocaltmp);
//                if (!iplocaltmp.equalsIgnoreCase(Networking.LOOPBACK)) {
//                    Request request = generateFreshBasicRequest(addressFromStr, addressToStr, iplocaltmp, sipPro, "INVITE", combination, 27);
//                //TODO:ALG create content Type, header
//                    // Create a new SIP client transaction.
//                    ClientTransaction transaction = sipPro.getNewClientTransaction(request);
//                    //Send the request statefully, through the client transaction.
//                    transaction.sendRequest();
//
//                    // Display the message in the text area.
//                    res = ("Request sent:\n" + request.toString() + "\n\n");
//                } else {
//                    res = ALGBo.getNetworkError(iplocaltmp);
//                    setresultmessage(res);
//                }//end of else not loopback
//            }//end SIpPro !null
//            else {
//                res = ALGBo.PLUGIN_REINSTALL;
//                setresultmessage(res);
////createSipStack();
//                //createSipFrameWork();
//            }
//        } catch (Exception e) {
//            // If an error occurred, display the error.
//            res = "Request sent failed: " + e.getMessage() + "\n";
//            setresultmessage(res);
//        }
//        return res;
//    }
//
//    @Override
//    /**
//     * Desc: Actually it process the request below: 1- Notify request: it send
//     * an 200 OK as response to a Notify request
//     */
//    public void processRequest(RequestEvent requestEvent) {
//
//        Request request = requestEvent.getRequest();
//        System.out.println("Received request: " + request.toString());
//
//    }
//
//    // it handle the response timeout
//    @Override
//    public void processTimeout(TimeoutEvent timeoutEvent) {
//        //The time out was minimize in: SipStackImpl: gov.nist.javax.sip.DIALOG_TIMEOUT_FACTOR 
//        setresultmessage("You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider");
//        ClientTransaction clientTrans = timeoutEvent.getClientTransaction();
//        Request request = clientTrans.getRequest();
//        setFWMsgToCorrespondentOutput(request);
//    }
//
//    @Override
//    public void processIOException(IOExceptionEvent exceptionEvent) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void processResponse(ResponseEvent responseEvent) {
//        processresponseImpl(responseEvent);
//    }
//
//    public synchronized void processresponseImpl(ResponseEvent responseEvent) {
//        // Get the response and the request of a transaction
//        Response response = responseEvent.getResponse();
//        ClientTransaction clientTrans = responseEvent.getClientTransaction();
//        Request request = clientTrans.getRequest();
//
//        //perform the ALG detection
//        Integer resultcode = algBo.algdetection(request, response);
//        String outmsg = "";
//        if (resultcode.equals(1)) {
//            outmsg = "No ALG Detected";
//        } else if (resultcode.equals(-1)) {
//            outmsg = "Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router";
//        }
//        else if (resultcode.equals(-2)) {
//            outmsg = "Warning: SIP ALG detected, Is highly recommended to disable SIP ALG in the router";
//        }
//        setresultmessage(outmsg);
//        //set the received msgs to the correspondent output
//        CSeqHeader cseqHd = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
//        String methodResponse = cseqHd.getMethod();
//        String responseStr = response.toString();
////            //log the request to log file
////            PrintWriterObj printSingleton = PrintWriterObj.getInstance();
////            PrintWriter pw = printSingleton.getSipLogsPW();
////            PrintWriterObj.writePrintWriter(pw, "Process Response...........\n" + response.toString());
//
//        System.out.println("Received response: " + responseStr);
//        // Display the response message in the text area, where the radio button is selected
//        setRcvMsgToCorrespondentOutput(methodResponse, response);
//    }
//
//    public void setFWMsgToCorrespondentOutput(Request request) {
//        Integer combId = algBo.getCombinationIdFromRequest(request);
//        switch (combId) {
//            case 1:
//                AlgJPanel.comb1RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
//                AlgJPanel.comb1RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
//                break;
//            case 2:
//                AlgJPanel.comb2RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
//                AlgJPanel.comb2RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
//                break;
//            case 3:
//                AlgJPanel.comb3RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
//                AlgJPanel.comb3RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
//                break;
//            case 4:
//                AlgJPanel.comb4RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
//                AlgJPanel.comb4RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
//                break;
//        }
//    }
//
//    public void setRcvMsgToCorrespondentOutput(String methodResponse, Response response) {
//        String responseStr = response.toString();
//        Integer combId = algBo.getCombinationIdFromResponse(response);
//
//        switch (combId) {
//            case 1:
//                //filling the output log after sending the messeges
//                //REG
//                if (methodResponse.equals("REGISTER")) {
//                    AlgJPanel.comb1RcvMsgREG.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb1RcvMsgREG.setCaretPosition(0);
//                } else if (methodResponse.equals("INVITE")) {
//                    //INV
//                    AlgJPanel.comb1RcvMsgINV.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb1RcvMsgINV.setCaretPosition(0);
//                }
//                break;
//            case 2:
//                if (methodResponse.equals("REGISTER")) {
//                    AlgJPanel.comb2RcvMsgREG.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb2RcvMsgREG.setCaretPosition(0);
//                } else if (methodResponse.equals("INVITE")) {
//                    //INV
//                    AlgJPanel.comb2RcvMsgINV.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb2RcvMsgINV.setCaretPosition(0);
//                }
//                break;
//            case 3:
//                if (methodResponse.equals("REGISTER")) {
//                    AlgJPanel.comb3RcvMsgREG.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb3RcvMsgREG.setCaretPosition(0);
//                } else if (methodResponse.equals("INVITE")) {
//                    //INV
//                    AlgJPanel.comb3RcvMsgINV.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb3RcvMsgINV.setCaretPosition(0);
//                }
//                break;
//            case 4:
//                if (methodResponse.equals("REGISTER")) {
//                    AlgJPanel.comb4RcvMsgREG.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb4RcvMsgREG.setCaretPosition(0);
//                } else if (methodResponse.equals("INVITE")) {
//                    //INV
//                    AlgJPanel.comb4RcvMsgINV.setText("Received response:\n " + responseStr);
//                    //set the caret to the top always
//                    AlgJPanel.comb4RcvMsgINV.setCaretPosition(0);
//                }
//                break;
//        }
//    }

    /*
    
     */
    protected void setresultmessage(String outmessage) {
        //AlgJPanel.resultmsgjlabel.setText(outmessage);        
        String labelText = String.format("<html><div style=\"width:%dpx;\"><p align=\"center\">%s</p></div><html>", 200, outmessage);
        AlgJPanel.resultmsgjlabel.setText(labelText);
        AlgJPanel.resultmsgjlabel.setBackground(Color.red);
    }

}
