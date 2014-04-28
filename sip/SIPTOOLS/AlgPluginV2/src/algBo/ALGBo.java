/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algBo;

import static algBo.Networking.getLocalIpAddress;
import java.net.SocketException;
import javax.sip.message.Request;
import javax.sip.message.Response;

/**
 *
 * @author salim
 */
public class ALGBo {
    /*considering we have 4 port source and four port dest,
     and I should read them from the config file
     */

    static public Integer portsrc1 = 5060;
    static public Integer portsrc2 = 5060;
    static public Integer portsrc3 = 5062;
    static public Integer portsrc4 = 5062;
    //currently the server is running on the same port
    static public Integer portdest1 = 5060;
    static public Integer portdest2 = 5060;
    static public Integer portdest3 = 5060;
    static public Integer portdest4 = 5060;

    static public String transport1 = "udp";
    static public String transport2 = "tcp";
    static public String transport3 = "udp";
    static public String transport4 = "tcp";

    /* TODO: config file extraction BO
     In V2 prtocol/port combination should be dynamic and read from a configuration file
     */
    static public String hostname = "TALKSWITCH";
    static public String iplocal;
    static String ipServer;
    static String extlocal;

    public String algdetection(Request request, Response response) {
        String res = "";
        System.out.println("algdetection[" + request.toString() + "]\nres=[" + response.toString() + "]");
        return res;
    }

    public static Integer getPortsrc1() {
        return portsrc1;
    }

    public static void setPortsrc1(Integer portsrc1) {
        ALGBo.portsrc1 = portsrc1;
    }

    public static Integer getPortsrc2() {
        return portsrc2;
    }

    public static void setPortsrc2(Integer portsrc2) {
        ALGBo.portsrc2 = portsrc2;
    }

    public static Integer getPortdest1() {
        return portdest1;
    }

    public static void setPortdest1(Integer portdest1) {
        ALGBo.portdest1 = portdest1;
    }

    public static Integer getPortsrc3() {
        return portsrc3;
    }

    public static void setPortsrc3(Integer portsrc3) {
        ALGBo.portsrc3 = portsrc3;
    }

    public static Integer getPortsrc4() {
        return portsrc4;
    }

    public static void setPortsrc4(Integer portsrc4) {
        ALGBo.portsrc4 = portsrc4;
    }

    public static Integer getPortdest2() {
        return portdest2;
    }

    public static void setPortdest2(Integer portdest2) {
        ALGBo.portdest2 = portdest2;
    }

    public static Integer getPortdest3() {
        return portdest3;
    }

    public static void setPortdest3(Integer portdest3) {
        ALGBo.portdest3 = portdest3;
    }

    public static Integer getPortdest4() {
        return portdest4;
    }

    public static void setPortdest4(Integer portdest4) {
        ALGBo.portdest4 = portdest4;
    }

    public static String getTransport1() {
        return transport1;
    }

    public static void setTransport1(String transport1) {
        ALGBo.transport1 = transport1;
    }

    public static String getTransport2() {
        return transport2;
    }

    public static void setTransport2(String transport2) {
        ALGBo.transport2 = transport2;
    }

    public static String getTransport3() {
        return transport3;
    }

    public static void setTransport3(String transport3) {
        ALGBo.transport3 = transport3;
    }

    public static String getTransport4() {
        return transport4;
    }

    public static void setTransport4(String transport4) {
        ALGBo.transport4 = transport4;
    }
    public static String getHostname() {
        return hostname;
    }

    public static void setHostname(String hostname) {
        ALGBo.hostname = hostname;
    }

    public static String getIplocal() throws SocketException {
        iplocal = getLocalIpAddress();
        return iplocal;
    }

    public static void setIplocal(String iplocal) {
        ALGBo.iplocal = iplocal;
    }

    public static String getIpServer() {
        ipServer = "209.208.79.151";
        return ipServer;
    }

    public static void setIpServer(String ipServer) {
        ALGBo.ipServer = ipServer;
    }

    public static String getExtlocal() {
        extlocal = "ALGDetector";
        return extlocal;
    }

    public static void setExtlocal(String extlocal) {
        ALGBo.extlocal = extlocal;
    }

    public static String getSimpleSIPMessage(String method) throws SocketException {
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

}
