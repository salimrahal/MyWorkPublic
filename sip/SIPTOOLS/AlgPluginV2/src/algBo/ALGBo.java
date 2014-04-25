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
    
    static public Integer portsrc1 = 5060;
    static public Integer portsrc2 = 5062;
    static public Integer portdest1 = 5060;
   static public String transporttcp = "TCP";
   static public String transportudp = "udp";
    /* TODO: config file extraction BO
    In V2 prtocol/port combination should be dynamic and read from a configuration file
    */
    static public String comb1ProtoUDP = "UDP";
    static public Integer comb1SrcPort1 = 5060;
    static public Integer comb1DestPort1 = 5060;
    static public String iplocal;
     static public String ipServer;
    static public String extlocal;
      
    static public String comb2ProtoTCP = "TCP";
    static public Integer comb2SrcPort1 = 5060;
    static public Integer comb2DestPort1 = 5060;
    
    static public  String comb3ProtoUDP = "UDP";
    static public Integer comb3SrcPort2 = 5062;
    static public Integer comb3DestPort1 = 5060;
    
    static public String comb4ProtoTCP = "TCP";
    static public Integer comb4SrcPort2 = 5062;
    static public Integer comb4DestPort1 = 5060;

    static public String hostname = "TALKSWITCH";

    
    
    
    public String algdetection(Request request, Response response){
        String res = "";
        System.out.println("["+request.toString()+"]\nres=["+response.toString()+"]");
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

    public static String getTransporttcp() {
        return transporttcp;
    }

    public static void setTransporttcp(String transporttcp) {
        ALGBo.transporttcp = transporttcp;
    }

    public static String getTransportudp() {
        return transportudp;
    }

    public static void setTransportudp(String transportudp) {
        ALGBo.transportudp = transportudp;
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
    
    
    public static String getSimpleSIPMessage(String method) throws SocketException{
        String c= "5060";
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
