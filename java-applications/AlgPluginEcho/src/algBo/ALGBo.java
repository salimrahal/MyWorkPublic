/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo;

import static algBo.Networking.getLocalIpAddress;
import algBo.config.SAXParserConf;
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
public class ALGBo {

    //combination id: 1, 2, 3, 4
    static public final Integer Comb1Id = 1;
    static public final Integer Comb2Id = 2;
    static public final Integer Comb3Id = 3;
    static public final Integer Comb4Id = 4;

    /*considering we have 4 port source and four port dest,
     and I should read them from the config file
     */
    public Integer portsrc1 = 5060;
    public Integer portsrc2 = 5060;
    public Integer portsrc3 = 5062;
    public Integer portsrc4 = 5062;
    //currently the server is running on the same port
    public Integer portdest1 = 5060;
    public Integer portdest2 = 5060;
    public Integer portdest3 = 5060;
    public Integer portdest4 = 5060;

    public String transport1 = "udp";
    public String transport2 = "tcp";
    public String transport3 = "udp";
    public String transport4 = "tcp";

    /* TODO: config file extraction BO
     In V2 prtocol/port combination should be dynamic and read from a configuration file
     */
    public String agentname = "Cisco/SPA303-8.0.1";
    public String iplocal;
    String ipServer = "209.208.79.151";
    String sipIdLocal = "ALGDetector";

    //messages
    public static final String RESET_OK = "OK";
    public static final String INPROGRESS = "in progress..";
    //No Packet Received - SIP ALG / Firewall issue
    public static final String FIREWALL_MSG = "You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider";
    public static final String PLUGIN_REINSTALL = "Error: You can open only one ALG detector Web page, close other instance, then re-install the plugin!";
    //it gets combination Id from port src/dest and Transport
    ConfVO conVO;
    static SAXParserConf saxparserconf;

    public ALGBo() {

    }

    public static void readFile(String fileToRead, URL codebase) {
        String line;
        URL url = null;
        System.out.println("readFile::codebase:"+codebase);
        try {
            url = new URL(codebase, fileToRead);
        } catch (MalformedURLException e) {
        }

        try {
            InputStream in = url.openStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            StringBuffer strBuff = new StringBuffer();
            while ((line = bf.readLine()) != null) {
                strBuff.append(line + "\n");
            }
            System.out.println("readFile="+strBuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //parse the xml and set the parameters values: server ip, portsrc, port dest, etc
    public void performConfiParsing() {
        //extract the config file to confVO
        saxparserconf = new SAXParserConf();
        try {
            saxparserconf.parseConfVO();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ALGBo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ALGBo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ALGBo.class.getName()).log(Level.SEVERE, null, ex);
        }
        conVO = ConfVO.getInstance();
        
        setparamFromConfig();
    }

    public void setparamFromConfig() {
        this.agentname = conVO.getAgentname();
        this.ipServer = conVO.getIpServer();
        List<Test> testL = conVO.getTestL();
        for (Test t : testL) {
            switch (t.seqNumber) {
                case 1:
                    setPortsrc1(t.getPortscr());
                    setPortdest1(t.getPortDest());
                    setTransport1(t.getTransport());
                    break;
                case 2:
                    setPortsrc2(t.getPortscr());
                    setPortdest2(t.getPortDest());
                    setTransport2(t.getTransport());
                    break;
                case 3:
                    setPortsrc3(t.getPortscr());
                    setPortdest3(t.getPortDest());
                    setTransport3(t.getTransport());
                    break;
                case 4:
                    setPortsrc4(t.getPortscr());
                    setPortdest4(t.getPortDest());
                    setTransport4(t.getTransport());
                    break;
            }
        }
    }

    public Integer getCombinationIdFromResponse(Response response) {
        Integer combId = -1;
        ViaHeader via = (ViaHeader) response.getHeader(SIPHeader.VIA);
        FromHeader from = (FromHeader) response.getHeader(SIPHeader.FROM);
        ToHeader to = (ToHeader) response.getHeader(SIPHeader.TO);
        //get the transport
        String transport = via.getTransport();
        //get the ports
        String fromURI = from.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
        Integer fromPort = extractPort(fromURI);

        String toURI = to.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
        Integer toPort = extractPort(toURI);

        if (transport.equalsIgnoreCase(transport1) && fromPort.equals(portsrc1) && toPort.equals(portdest1)) {
            combId = Comb1Id;
        } else if (transport.equalsIgnoreCase(transport2) && fromPort.equals(portsrc2) && toPort.equals(portdest2)) {
            combId = Comb2Id;
        } else if (transport.equalsIgnoreCase(transport3) && fromPort.equals(portsrc3) && toPort.equals(portdest3)) {
            combId = Comb3Id;
        } else if (transport.equalsIgnoreCase(transport4) && fromPort.equals(portsrc4) && toPort.equals(portdest4)) {
            combId = Comb4Id;
        }
        return combId;
    }

    //it gets combination Id from port src/dest and Transport
    public Integer getCombinationIdFromRequest(Request request) {
        Integer combId = -1;
        ViaHeader via = (ViaHeader) request.getHeader(SIPHeader.VIA);
        FromHeader from = (FromHeader) request.getHeader(SIPHeader.FROM);
        ToHeader to = (ToHeader) request.getHeader(SIPHeader.TO);
        //get the transport
        String transport = via.getTransport();
        //get the ports
        String fromURI = from.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
        Integer fromPort = extractPort(fromURI);

        String toURI = to.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
        Integer toPort = extractPort(toURI);

        if (transport.equalsIgnoreCase(transport1) && fromPort.equals(portsrc1) && toPort.equals(portdest1)) {
            combId = Comb1Id;
        } else if (transport.equalsIgnoreCase(transport2) && fromPort.equals(portsrc2) && toPort.equals(portdest2)) {
            combId = Comb2Id;
        } else if (transport.equalsIgnoreCase(transport3) && fromPort.equals(portsrc3) && toPort.equals(portdest3)) {
            combId = Comb3Id;
        } else if (transport.equalsIgnoreCase(transport4) && fromPort.equals(portsrc4) && toPort.equals(portdest4)) {
            combId = Comb4Id;
        }
        return combId;
    }
    /*
     a- test the port ranges: 1024 -> 65535
     b- callerId should be z same
    
     c- check for missed semicolumns ";" in headers parameters
    
     d- check Via and contact header, it should be the same (except the parameters)
    
     If some value(s) were modified, the client app should indicate 
     - Warning: SIP ALG detected, Is highly recommended to disable SIP ALG in the router â€‌,
     in addition if the Call-ID was modified or there are some missed semi-colon in the header parameters the client app should indicate 
     - Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the routerâ€‌
     */

    public synchronized Integer algdetection(Request request, Response response) {
        Integer res = 1;//No ALG Detected";
        //System.out.println("algdetection\n[request=" + request.toString() + "]\n[response=" + response.toString() + "]");
        /* handling callID 
         callID should be the same
         */
        String callIdReq = request.getHeader(SIPHeader.CALL_ID).toString();
        ViaHeader svia = (ViaHeader) response.getHeader(SIPHeader.VIA);
        ContactHeader scontact = (ContactHeader) response.getHeader(SIPHeader.CONTACT);

        String callIdRes = response.getHeader(SIPHeader.CALL_ID).toString();
        //callIdRes = "newcallID";
        //retrieve the headers
        ViaHeader rvia = (ViaHeader) response.getHeader(SIPHeader.VIA);
        FromHeader from = (FromHeader) response.getHeader(SIPHeader.FROM);
        ToHeader to = (ToHeader) response.getHeader(SIPHeader.TO);
        ContactHeader rcontact = (ContactHeader) response.getHeader(SIPHeader.CONTACT);

        if (!callIdReq.equalsIgnoreCase(callIdRes)) {
            res = -1;
            //res = "Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router";

        } else {
            //checking port number: via, to, from, contact
            Integer viaPort = rvia.getPort();
            //viaPort = 100000;
            String fromURI = from.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
            Integer fromPort = extractPort(fromURI);

            String toURI = to.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
            Integer toPort = extractPort(toURI);

            String contactURI = rcontact.getAddress().getURI().toString();//sip:ALGDetector@209.208.79.151:5062
            Integer contactPort = extractPort(contactURI);

            if (!(checkPortValidity(viaPort) && checkPortValidity(fromPort) && checkPortValidity(toPort) && checkPortValidity(contactPort))) {
                // res = "Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router";
                res = -1;
            } //check the via and contact header: --> Warning ALG SIP detected:
            else {
                boolean viahostchange = ischangedViaHost(svia, rvia);
                boolean contacthostchange = ischangedContactHost(scontact, rcontact);
                if (viahostchange || contacthostchange) {
                    res = -2;
                }
            }
        }
        return res;
    }

    public boolean ischangedViaHost(ViaHeader svia, ViaHeader rvia) {
        boolean res = false;
        if (!(svia.getHost().equalsIgnoreCase(rvia.getHost()))) {
            res = true;
        }
        return res;
    }

    public boolean ischangedContactHost(ContactHeader scontact, ContactHeader rcontact) {
        boolean res = false;
        if (!(scontact.getAddress().toString().equalsIgnoreCase(rcontact.getAddress().toString()))) {
            res = true;
        }
        return res;
    }

    /*It retrive the port numbers from below URI:
     URI: Contact: <sip:ALGDetector@93.185.239.118:5062;transport=udp>
     From: <sip:ALGDetector@209.208.79.151:5062>;tag=-1997789931
     To: <sip:ALGDetector@209.208.79.151:5060>
     Via: SIP/2.0/UDP 93.185.239.118:5062;branch=z9hG4bK-508490564
     */
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

    public String getHostname() {
        return agentname;
    }

    public void setHostname(String hostname) {
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

    public String getExtlocal() {
        return sipIdLocal;
    }

    public void setExtlocal(String extlocal) {
        this.sipIdLocal = extlocal;
    }

    public String getSimpleSIPMessage(String method) throws SocketException {
        String c = "5060";
        String serverIp = getIpServer();
        String s3 = getLocalIpAddress();
        String i = "5060";
        String s4 = (new StringBuilder()).append("REGISTER sip:").append(serverIp).append(":")
                .append(c).append(" SIP/2.0\r\nVia: SIP/2.0/UDP ").append(s3).append(":")
                .append(i).append(";branch=z9hG4bK-7d0f94c9\r\nFrom: \"SIP_ALG_DETECTOR\" <sip:18009834289@")
                .append(serverIp).append(":").append(c).append(">;tag=1b38e99fe68ccce9o0\r\nTo: \"SIP_ALG_DETECTOR\" <sip:18009834289@")
                .append(serverIp).append(":").append(c).
                append(">\r\nCall-ID: 11256979-ca11b60c@").append(s3).
                append("\r\nCSeq: 7990 REGISTER\r\nMax-Forwards: 70\r\nContact: \"SIP_ALG_DETECTOR\" <sip:18009834289@").
                append(s3).append(":").
                append(i).append(">;expires=60\r\nUser-Agent: Cisco/SPA303-7.4.7\r\nContent-Length: 0\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE\r\nSupported: replaces\r\n\r\n").toString();

//        String s5 = (new StringBuilder()).append("").append(method).append(" sip:").append(serverIp).append(":")
//                      .append(c).append(" SIP/2.0\r\nVia: SIP/2.0/UDP ").append(s3).append(":")
//                      .append(i).append(";branch=z9hG4bK-7d0f94c9\r\nFrom: \"ALGDETECTOR\" <sip:18009834289@")
//                      .append(serverIp).append(":").append(c).append(">;tag=1b38e99fe68ccce9o0\r\nTo: \"ALGDETECTOR\" <sip:18009834289@")
//                      .append(serverIp).append(":").append(c).
//                      append(">\r\nCall-ID: 11256979-ca11b60c@").append(s3).
//                      append("\r\nCSeq: 7990 ").append(method).append("\r\nMax-Forwards: 70\r\nContact: \"ALGDETECTOR\" <sip:18009834289@").
//                      append(s3).append(":").
//                      append(i).append(">;expires=60\r\nUser-Agent: FortiVoice/7.31b00\r\nContent-Length: 0\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE\r\nSupported: replaces\r\n\r\n").toString();       
        return s4;
    }

    public static String getNetworkError(String ip) {
        StringBuilder sb = new StringBuilder("Error: Check your network connection [current IP address:");
        sb.append(ip).append("]");
        return sb.toString();
    }

}
