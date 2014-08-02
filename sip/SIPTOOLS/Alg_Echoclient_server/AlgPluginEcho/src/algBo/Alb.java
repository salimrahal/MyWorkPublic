/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo;

import static algBo.Ntg.getLocalIpAddress;
import algBo.config.Spf;
import algVo.Test;
import algVo.config.ConfVO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author salim
 */
   //TODO: build the confVO
//TODO: get the Hashmap of combinations
public class Alb {

    public static final String C_N = "config.xml";
    public static final String C_D = "siptoolsconfig";

    static public final Integer Comb1Id = 1;
    static public final Integer Comb2Id = 2;
    static public final Integer Comb3Id = 3;
    static public final Integer Comb4Id = 4;

    public Integer portsrc1;//UDP 5060 for remote test

    public Integer portsrc2;//in tcp we create only one socket with the server port number, this port scr will be used in sip body message

    public Integer portsrc3;//UDP 5060 for remote test

    public Integer portsrc4;

    //currently the server is running on the same port
    public Integer portdest1;
    public Integer portdest2;
    public Integer portdest3;
    public Integer portdest4;

    public String transport1;
    public String transport2;
    public String transport3;
    public String transport4;

    public String agentname;
    public String iplocal;
    //String ipServer = "127.0.1.1";//local test
    String ipServer;//remote test
    String sipIdLocal;

    public static final String RG = "REGISTER";
    public static final String IV = "INVITE";

    //messages
    public static final String R_K = "OK";
    public static final String I_P = "in progress..";
    //No Packet Received - SIP ALG / Firewall issue
    public static final String M_I = "The server is not responding";
    public static final String MSG_NETWORK_OR_FW_ISSUE = "You have a Network Problem. Check your Network admin.";
    public static final String M_U = "You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider";
    public static final String MSG_SipALGWarning = "Warning: SIP ALG detected, Is highly recommended to disable SIP ALG in the router";
    public static final String MSG_SipALGError = "Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router";
    public static final String MSG_SipALGNotFound = "No ALG Detected";
    public static final String MSG_SipALGNotFound_Reg = "Register: No ALG Detected, INVITE: In Progress..";
    public static final String MSG_SipALGNotFound_Inv = "Invite: No ALG Detected";
    public static final String PLUGIN_REINSTALL = "Error: You can open only one ALG detector Web page, close other instance, then re-install the plugin!";

    public static final String UDPPAcketNotreceived = "Critical Error : Packet is not received";
    static Spf saxparserconf;
    /*CALLID_PREFIX: one callid in invite and register message
     */
    public static final String C_P = "11256979-ca11b60c";
    public static final Integer T_T = 20000;////millisecond
    public static final Integer U_T = 7000;//millisecond
    public static final long U_E = 500;//millisecond

    Test test1;
    Test test2;
    Test test3;
    Test test4;

    public Alb() {
    }

    public String getCU(String hst) {
        //build the config url: http://localhost/siptoolsconfig/config.xml
        String configUri = new StringBuilder().append(hst).append("/").append(C_D).append("/").append(C_N).toString();
        return configUri;

    }

    public void pc(String configUri) {

        //extract the config file to confVO
        saxparserconf = new Spf();
        try {
            saxparserconf.parseConfVO(configUri);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Alb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Alb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Alb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Test getTestfromCombId(int id) {
        Test resT = null;
        switch (id) {
            case 1:
                resT = test1;
                break;
            case 2:
                resT = test2;
                break;
            case 3:
                resT = test3;
                break;
            case 4:
                resT = test4;
                break;
        }
        return resT;
    }

    public static void readFile(String fileToRead, URL codebase) {
        String line;
        URL url = null;
        System.out.println("readFile::codebase:" + codebase);
        try {
            url = new URL(codebase, fileToRead);
        } catch (MalformedURLException e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
            InputStream in = url.openStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            StringBuffer strBuff = new StringBuffer();
            while ((line = bf.readLine()) != null) {
                strBuff.append(line + "\n");
            }
            System.out.println("readFile=" + strBuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ak(String recvMsg, String callIdSent) {
        String callIdRecv;
        String resMsg;
        Integer callidindex = recvMsg.indexOf("Call-ID: ");
        // retreive the value of callId. 9 is the lenght of [Call-ID: ]
        callIdRecv = recvMsg.substring(callidindex + 9, callidindex + 9 + callIdSent.length());
        if (!callIdSent.equals(callIdRecv)) {
            resMsg = MSG_SipALGError;
        } else {
            //sip alg detected
            resMsg = MSG_SipALGWarning;
        }
        return resMsg;
    }

    public void rpfv() {
        ConfVO conVO = ConfVO.getInstance();
        this.agentname = conVO.getAgentname();
        this.ipServer = conVO.getIpServer();
        this.sipIdLocal = conVO.getSipIdLocal();
        List<Test> testL = conVO.getTestL();
        for (Test t : testL) {
            switch (t.seqNumber) {
                case 1:
                    test1 = t;
                    setPortsrc1(t.getPortscr());
                    setPortdest1(t.getPortDest());
                    setTransport1(t.getTransport());
                    break;
                case 2:
                    test2 = t;
                    setPortsrc2(t.getPortscr());
                    setPortdest2(t.getPortDest());
                    setTransport2(t.getTransport());
                    break;
                case 3:
                    test3 = t;
                    setPortsrc3(t.getPortscr());
                    setPortdest3(t.getPortDest());
                    setTransport3(t.getTransport());
                    break;
                case 4:
                    test4 = t;
                    setPortsrc4(t.getPortscr());
                    setPortdest4(t.getPortDest());
                    setTransport4(t.getTransport());
                    break;
            }
        }
    }

    /**
     *
     * @param uri
     * @return
     */
    public Integer extractPort(String uri) {
        Integer port = 0;
        String portStr = uri.split(":")[2];
        if (portStr != null) {
            port = Integer.valueOf(portStr);
        }
        return port;
    }

    public boolean checkPortValidity(Integer port) {
        if (port > 1024 && port <= 65535) {
            return true;
        } else {
            return false;
        }
    }

    public Integer getPortsrc1() {
        return portsrc1;
    }

    public void setPortsrc1(Integer portsrc1) {
        this.portsrc1 = portsrc1;
    }

    public Integer getPortsrc2() {
        return portsrc2;
    }

    public void setPortsrc2(Integer portsrc2) {
        this.portsrc2 = portsrc2;
    }

    public Integer getPortdest1() {
        return portdest1;
    }

    public void setPortdest1(Integer portdest1) {
        this.portdest1 = portdest1;
    }

    public Integer getPortsrc3() {
        return portsrc3;
    }

    public void setPortsrc3(Integer portsrc3) {
        this.portsrc3 = portsrc3;
    }

    public Integer getPortsrc4() {
        return portsrc4;
    }

    public void setPortsrc4(Integer portsrc4) {
        this.portsrc4 = portsrc4;
    }

    public Integer getPortdest2() {
        return portdest2;
    }

    public void setPortdest2(Integer portdest2) {
        this.portdest2 = portdest2;
    }

    public Integer getPortdest3() {
        return portdest3;
    }

    public void setPortdest3(Integer portdest3) {
        this.portdest3 = portdest3;
    }

    public Integer getPortdest4() {
        return portdest4;
    }

    public void setPortdest4(Integer portdest4) {
        this.portdest4 = portdest4;
    }

    public String getTransport1() {
        return transport1;
    }

    public void setTransport1(String transport1) {
        this.transport1 = transport1;
    }

    public String getTransport2() {
        return transport2;
    }

    public void setTransport2(String transport2) {
        this.transport2 = transport2;
    }

    public String getTransport3() {
        return transport3;
    }

    public void setTransport3(String transport3) {
        this.transport3 = transport3;
    }

    public String getTransport4() {
        return transport4;
    }

    public void setTransport4(String transport4) {
        this.transport4 = transport4;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String hostname) {
        this.agentname = hostname;
    }

    public String getIplocal() throws SocketException {
        iplocal = getLocalIpAddress();
        return iplocal;
    }

    public void setIplocal(String iplocal) {
        this.iplocal = iplocal;
    }

    public String getIpServer() {

        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getSipIdLocal() {
        return sipIdLocal;
    }

    public void setSipIdLocal(String extlocal) {
        this.sipIdLocal = extlocal;
    }

    public String brm(String ipServer, String ipLocalparam, String transport, Integer portsrc, Integer portdest, String callIdSent, String agentName) {
        String registerMsg = "";
        registerMsg = (new StringBuilder()).append("REGISTER sip:").append(ipServer).append(":")
                .append(portdest).append(" SIP/2.0\r\nVia: SIP/2.0/").append(transport).append(" ").append(ipLocalparam).append(":")
                .append(portsrc).append(";branch=z9hG4bK-7d0f94c9\r\nFrom: \"SIP_ALG_DETECTOR\" <sip:58569874@")
                .append(ipServer).append(":").append(portdest).append(">;tag=1b38e99fe68ccce9o0\r\nTo: \"SIP_ALG_DETECTOR\" <sip:58569874@")
                .append(ipServer).append(":").append(portdest).
                append(">\r\nCall-ID: ").append(callIdSent).
                append("\r\nCSeq: 6999 REGISTER\r\nMax-Forwards: 70\r\nContact: \"SIP_ALG_DETECTOR\" <sip:58569874@").
                append(ipLocalparam).append(":").
                append(portsrc).append(">;expires=60\r\nUser-Agent: ").append(agentName).append("\r\nContent-Length: 0\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE\r\nSupported: replaces\r\n\r\n").toString();

        return registerMsg;
    }

    public String bim(String ipServer, String ipLocalparam, String transport, Integer portsrc, Integer portdest, String callIdSent, String agentName) throws SocketException {
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

    public static String getNetworkError(String ip) {
        StringBuilder sb = new StringBuilder("Error: Check your network connection [current IP address:");
        sb.append(ip).append("]");
        return sb.toString();
    }

}
