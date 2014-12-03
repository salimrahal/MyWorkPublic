/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algcr;

import algBo.Alb;
import static algBo.Alb.M_U_K;
import static algBo.Alb.U_E;
import static algBo.Ntg.getmyPIP;
import algBo.WsBo;
import algConcurrent.SRC;
import algGui.AlgJPanel;
import algVo.Test;
import com.safirasoft.VAlgpam;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author salim
 */
public class Cc {
//BO

    Alb algBo;
    int counter = 0;

    Properties properties;          // Other properties.
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
    int tag = (new Random()).nextInt(); 
    //Address contactAddress;         .
    //ContactHeader contactHeader;    
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

    //use to execute tcp send/rcv message callable method
    ExecutorService executor;

    public Cc() throws SocketException {
        algBo = new Alb();

        algBo.rpfv();

        extlocal = algBo.getSipIdLocal();
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
        executor = Executors.newCachedThreadPool();

    }

    public Alb getAlgBo() {
        return algBo;
    }

    public void setAlgBo(Alb algBo) {
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

    public void preprreq(Test test, JTextArea sentmsgReg, JTextArea recvjtextregister, JTextArea sentmsgInv, JTextArea recvjtextinvite, String custname) throws IOException {
        String outmsg;
        if (Alb.uchkr(Alb.genul())) {
            String testUuid = Alb.genID();//size 36
            Integer portSrc = test.getPortscr();
            Integer portDest = test.getPortDest();
            String transport = test.getTransport();
            String custNm = custname;
            String publicIp = getmyPIP();
            //System.out.println("preprreq saving=" + testUuid + "/" + publicIp);
            int res = WsBo.vam(testUuid, custNm, publicIp, transport, portSrc, portDest);
            //System.out.println("WS:save to WS/DB=" + res);
            prreq(testUuid, test, sentmsgReg, recvjtextregister, sentmsgInv, recvjtextinvite);
        } else {
            outmsg = Alb.M_NC;
            setresultmessage(outmsg);
        }
    }

    public String prreq(String testUuid, Test test, JTextArea sentmsgReg, JTextArea recvjtextregister, JTextArea sentmsgInv, JTextArea recvjtextinvite) throws IOException {
        DatagramSocket datagramsocket = null;
        counter++;

        iplocal = algBo.getIplocal();

        setresultmessage(Alb.I_P);
        String outmsg = null;
        Integer portSrc = test.getPortscr();
        Integer portDest = test.getPortDest();

        String callId = new StringBuilder().append(Alb.C_P).append("@").append(iplocal).toString();
        if (test.getTransport().equalsIgnoreCase("udp")) {
            ResultObj resObjreg = new ResultObj();
            ResultObj resObjinv = new ResultObj();
            try {
                datagramsocket = new DatagramSocket(portSrc);
                resObjreg = sRD(datagramsocket, test, sentmsgReg, recvjtextregister, callId);
                outmsg = resObjreg.getResmessage();
                Thread.currentThread().sleep(U_E);
                resObjinv = sID(datagramsocket, test, sentmsgInv, recvjtextinvite, callId);
                outmsg = resObjinv.getResmessage();
                //}
                setresultmessage(outmsg);
            } catch (SocketException ex) {
                Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
                outmsg = "Unknown Socket Error";
                setresultmessage(outmsg);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
                outmsg = ex.getLocalizedMessage();
                setresultmessage(outmsg);
            } finally {
                svAlgFw(testUuid, resObjreg, resObjinv);
                if (datagramsocket != null) {
                    datagramsocket.close();
                }
            }
        } else if (test.getTransport().equalsIgnoreCase("tcp")) {
            String serverHostname = ipServer;
            Socket echoSocket = null;
            String msgToSendReg = null;
            String msgToSendInv = null;
            PrintWriter out = null;
            BufferedReader in = null;
            ResultObj resObjreg = new ResultObj();
            ResultObj resObjinv = new ResultObj();
            try {

                msgToSendReg = algBo.brm(ipServer, iplocal, "TCP", test.getPortscr(), test.getPortDest(), callId, algBo.getAgentname());
                msgToSendInv = algBo.bim(ipServer, iplocal, "TCP", test.getPortscr(), test.getPortDest(), callId, algBo.getAgentname());

                echoSocket = new Socket();
                echoSocket.connect(new InetSocketAddress(serverHostname, portDest), Alb.T_T);
                System.out.println("Process Request: connected.");
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(
                        echoSocket.getInputStream()));

                resObjreg = sS(Alb.RG, echoSocket, test, sentmsgReg, recvjtextregister, sentmsgInv, recvjtextinvite, callId, out, in, msgToSendReg, msgToSendInv);
                outmsg = resObjreg.getResmessage();

                if (resObjreg.getRescode() == 1) {
                    resObjinv = sS(Alb.IV, echoSocket, test, sentmsgInv, recvjtextinvite, sentmsgInv, recvjtextinvite, callId, out, in, msgToSendReg, msgToSendInv);
                    outmsg = resObjinv.getResmessage();
                }
                setresultmessage(outmsg);
                echoSocket.close();
                out.close();
                in.close();
            } catch (UnknownHostException e) {
                outmsg = "sendStream: Don't know about host: " + serverHostname;
                System.err.println(outmsg);
                setresultmessage(outmsg);

            } catch (SocketTimeoutException socketTimeout) {
                outmsg = Alb.M_U;
                System.err.println("Process Request:socketTimeout" + outmsg);

                setresultmessage(outmsg);
                setJtextRegisterSentRcvTxt(outmsg, msgToSendReg, sentmsgReg, recvjtextregister);
                setJtextInviteSentRcvTxt(outmsg, msgToSendInv, sentmsgInv, recvjtextinvite);
            } catch (IOException iOException) {
                //handling network unreachable
                outmsg = iOException.getLocalizedMessage();
                //"processRequests: Couldn't get I/O for "
                //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
                System.err.println("Process Request:iOException" + outmsg);
                setresultmessage(outmsg);
            } finally {
                svAlgFw(testUuid, resObjreg, resObjinv);
                if (echoSocket != null) {
                    echoSocket.close();
                }
            }
           
        }//end of TCP
        return null;
    }

    public static void svAlgFw(String testUuid, ResultObj resObjreg, ResultObj resObjinv) {
        if (resObjreg.getRescode() == 1 && resObjinv.getRescode() == 1) {
            boolean fw = false;
            boolean algdetected = false;
            if (resObjreg.isAlgDetect() || resObjinv.isAlgDetect()) {
                algdetected = true;
            }
           
            WsBo.vae(testUuid, algdetected, fw);
        }
        else if (resObjreg.getRescode() == 0 || resObjinv.getRescode() == 0) {
            boolean fw;
            boolean algdetected = false;
            if (resObjreg.getResmessage().substring(0, 19).toLowerCase().contains(M_U_K) || resObjinv.getResmessage().substring(0, 19).toLowerCase().contains(M_U_K)) {
                fw = true;
               
                WsBo.vae(testUuid, algdetected, fw);
            } else {
                WsBo.dae(testUuid);         
            }
        }
    }

    /**
     *
     * @param SipMethod
     * @param echoSocket
     * @param test
     * @param sentmsgJtext
     * @param recvJtext
     * @param sentmsgInv
     * @param recvjtextinvite
     * @param callId
     * @param out
     * @param in
     * @param msgToSendReg
     * @param msgToSendInv
     * @return
     * @throws IOException
     */
    public ResultObj sS(String SipMethod, Socket echoSocket, Test test, JTextArea sentmsgJtext, JTextArea recvJtext, JTextArea sentmsgInv, JTextArea recvjtextinvite, String callId, PrintWriter out, BufferedReader in, String msgToSendReg, String msgToSendInv) throws IOException {
        String outmsg = null;
        Integer resCode = 0;
        ResultObj resObj = new ResultObj();

        try {
            String msgToSend;

           
            resObj.setMessageTosendReg(msgToSendReg);
           
            resObj.setMessageTosendInv(msgToSendInv);

            if (SipMethod.equalsIgnoreCase(Alb.RG)) {
                msgToSend = msgToSendReg;

            } else {
                msgToSend = msgToSendInv;

            }
            String msgRecv = null;
            

            SRC sendRcvTask = new SRC(msgToSend, in, out);
            Future<String> future = executor.submit(sendRcvTask);

            try {
                msgRecv = future.get(Alb.T_T, TimeUnit.MILLISECONDS);
                
                
                sentmsgJtext.setText(new StringBuilder().append("New Packet Sent:").append(newline).append(msgToSend).toString());
                sentmsgJtext.setCaretPosition(0);
                recvJtext.setText(new StringBuilder().append("New Packet Received:").append(newline).append(msgRecv).toString());
                recvJtext.setCaretPosition(0);
               
                if (msgToSend.equalsIgnoreCase(msgRecv)) {
                    outmsg = Alb.MSG_SipALGNotFound;
                    resObj.setAlgDetect(false);

                } else {
                   
                    outmsg = algBo.ak(msgRecv, callId);
                    resObj.setAlgDetect(true);
                }
                
                resCode = 1;
            } catch (InterruptedException ex) {
                Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                System.err.println("sendStream TimeoutException Error:" + ex.getLocalizedMessage());
                System.out.println("");
                outmsg = Alb.M_U;
                setJtextRegisterSentRcvTxt(outmsg, msgToSend, sentmsgJtext, recvJtext);
                if (SipMethod.equalsIgnoreCase(Alb.RG)) {
                    setJtextInviteSentRcvTxt(outmsg, msgToSendInv, sentmsgInv, recvjtextinvite);
                }
            }
        } catch (Exception ex) {
            System.err.println("sendStream Error:" + ex.getLocalizedMessage());
        }
        resObj.setRescode(resCode);
        resObj.setResmessage(outmsg);
        return resObj;
    }

    @Deprecated
    public String handleSendReceiveTcp(String msgToSend, BufferedReader in, PrintWriter out) throws IOException {
        StringReader msgreader = new StringReader(msgToSend);
        BufferedReader msgbr = new BufferedReader(msgreader);

        String msgRecv;
        String submsgToSend;
        StringBuilder strbuilder = new StringBuilder();

        while ((submsgToSend = msgbr.readLine()) != null) {
            
            out.println(submsgToSend);
            
            msgRecv = in.readLine();
            System.out.println("echo: " + msgRecv);
            strbuilder.append(msgRecv).append("\r\n");
        }
        return strbuilder.toString();
    }

    public ResultObj sRD(DatagramSocket datagramsocket, Test test, JTextArea sentmsgReg, JTextArea recvjtextregister, String callId) throws IOException {
        String outmsg = null;
        byte abyteReg[] = null;
        Integer resCode = 0;
        ResultObj resObj = new ResultObj();
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();
        DatagramPacket datagrampacket = null;
        InetAddress inetaddress1 = null;
        inetaddress1 = InetAddress.getByName(ipServer);
        try {
           
            String registerMsg = "";
            registerMsg = algBo.brm(ipServer, iplocal, "UDP", portsrc, portdest, callId, agentName);
            abyteReg = registerMsg.getBytes();

            datagrampacket = new DatagramPacket(abyteReg, abyteReg.length, inetaddress1, portdest);

            datagramsocket.send(datagrampacket);

            
            sentmsgReg.setText(new StringBuilder().append("New Packet Sent:").append(newline).append(registerMsg).toString());
            sentmsgReg.setCaretPosition(0);

            byte abyteBuff[] = new byte[1024];
            datagramsocket.setSoTimeout(Alb.U_T);
            DatagramPacket datagrampacketRecReg = new DatagramPacket(abyteBuff, abyteBuff.length);

            datagramsocket.receive(datagrampacketRecReg);
            String recvMsg = new String(datagrampacketRecReg.getData(), 0, datagrampacketRecReg.getLength());
            byte recMsgByte[] = recvMsg.getBytes();

            if (recvMsg.equals(registerMsg)) {
                outmsg = algBo.MSG_SipALGNotFound;
                resObj.setAlgDetect(false);
            } else {
                outmsg = algBo.ak(recvMsg, callId);
                resObj.setAlgDetect(true);
            }
            //setresultmessage(outmsg);
            recvjtextregister.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
            recvjtextregister.setCaretPosition(0);
            resCode = 1;
        } catch (SocketTimeoutException sockettimeoutexception) {
            recvjtextregister.setText(new StringBuilder().append(newline).append("[No Packet Received]").append(algBo.M_U).toString());
            recvjtextregister.setCaretPosition(0);
            outmsg = algBo.M_U;
            System.out.println("sendRegister excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (IOException exception) {
            System.out.println("sendRegister excpetion:" + exception.getLocalizedMessage());//Network is unreachable
            outmsg = exception.getLocalizedMessage();
            //setresultmessage(outmsg);
        }
        resObj.setRescode(resCode);
        resObj.setResmessage(outmsg);
        return resObj;
    }

    public ResultObj sID(DatagramSocket datagramsocket, Test test, JTextArea sentmsgInv, JTextArea recvjtextinvite, String callId) {
        String outmsg;
        Integer resCode = 0;
        ResultObj resObj = new ResultObj();
        //DatagramSocket datagramsocket = null;
        Integer portdest = test.getPortDest();
        Integer portsrc = test.getPortscr();
        String agentName = algBo.getAgentname();
        try {
            String inviteMsg = algBo.bim(ipServer, iplocal, "UDP", portsrc, portdest, callId, agentName);
            byte[] abyteInv = inviteMsg.getBytes();
            InetAddress inetaddress1 = null;
          
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
            datagramsocket.setSoTimeout(Alb.U_T);
            //Constructs a DatagramPacket for receiving packets of length length.
            DatagramPacket datagrampacketRec = new DatagramPacket(abyteBuff, abyteBuff.length);
            System.out.println("sendInvite waiting for INVITE response..");
            datagramsocket.receive(datagrampacketRec);
            String recvMsg = new String(datagrampacketRec.getData(), 0, datagrampacketRec.getLength());
            byte recMsgByte[] = recvMsg.getBytes();
            System.out.println("sendInvite received INVITE message = [" + recvMsg + "]");
            //test alg detected
            //recvMsg = recvMsg + "kdkdkd";
            if (recvMsg.equals(inviteMsg)) {
                outmsg = algBo.MSG_SipALGNotFound;
                resObj.setAlgDetect(false);
            } else {
                outmsg = algBo.ak(recvMsg, callId);
                resObj.setAlgDetect(true);
            }
            //setresultmessage(outmsg);
            recvjtextinvite.setText(new StringBuilder().append("New Packet Received:").append(newline).append(recvMsg).toString());
            recvjtextinvite.setCaretPosition(0);
            resCode = 1;
            //datagramsocket.close();
        } catch (SocketTimeoutException sockettimeoutexception) {
            recvjtextinvite.setText(new StringBuilder().append(newline).append("[No Packet Received]").append(Alb.M_U).toString());
            outmsg = Alb.M_U;
            //setresultmessage(outmsg);
            System.out.println("sendRegister excpetion:" + sockettimeoutexception.getLocalizedMessage());
        } catch (IOException exception) {
            System.out.println("sendRegister excpetion:" + exception.getLocalizedMessage());
            outmsg = exception.getLocalizedMessage();
            //setresultmessage(outmsg);
        }
        resObj.setRescode(resCode);
        resObj.setResmessage(outmsg);
        return resObj;
    }

    public void setJtextRegisterSentRcvTxt(String outmsg, String msgTosend, JTextArea sentmsgReg, JTextArea recvjtextregister) {
        sentmsgReg.setText(msgTosend);
        sentmsgReg.setCaretPosition(0);
        recvjtextregister.setText(outmsg);
        recvjtextregister.setCaretPosition(0);
    }

    public void setJtextInviteSentRcvTxt(String outmsg, String msgTosend, JTextArea sentmsgInv, JTextArea recvjtextInv) {
        System.out.println("setJtextInviteSentRcvTxt:filling the text");
        sentmsgInv.setText(msgTosend);
        sentmsgInv.setCaretPosition(0);
        recvjtextInv.setText(outmsg);
        recvjtextInv.setCaretPosition(0);
    }

    public void setJtextRegisterTxt(String msg, JTextArea jtext) {
        jtext.setText(msg);
        jtext.setCaretPosition(0);
    }

    public void setresultmessage(String outmessage) {
        //AlgJPanel.resultmsgjlabel.setText(outmessage);        
        String labelText = String.format("<html><div style=\"width:%dpx;\"><p align=\"center\">%s</p></div><html>", 200, outmessage);
        AlgJPanel.resultmsgjlabel.setText(labelText);
        AlgJPanel.resultmsgjlabel.setBackground(Color.red);
    }

    public class ResultObj {

        String messageTosendReg;
        String messageTosendInv;
        Integer rescode;
        String resmessage;
        boolean algDetect;

        public boolean isAlgDetect() {
            return algDetect;
        }

        public void setAlgDetect(boolean algDetect) {
            this.algDetect = algDetect;
        }

        public Integer getRescode() {
            return rescode;
        }

        public void setRescode(Integer rescode) {
            this.rescode = rescode;
        }

        public String getResmessage() {
            return resmessage;
        }

        public void setResmessage(String resmessage) {
            this.resmessage = resmessage;
        }

        public String getMessageTosendReg() {
            return messageTosendReg;
        }

        public void setMessageTosendReg(String messageTosendReg) {
            this.messageTosendReg = messageTosendReg;
        }

        public String getMessageTosendInv() {
            return messageTosendInv;
        }

        public void setMessageTosendInv(String messageTosendInv) {
            this.messageTosendInv = messageTosendInv;
        }

    }

}
