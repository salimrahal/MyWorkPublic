/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algBo;

import static algBo.Networking.getLocalIpAddress;
import java.net.SocketException;

/**
 *
 * @author salim
 */
public class ALGBo {
    /* TODO: config file extraction BO
    In V2 prtocol/port combination should be dynamic and read from a configuration file
    */
    static public String comb1ProtoUDP = "UDP";
    static public Integer comb1SrcPort5052 = 5062;
    static public Integer comb1DestPort5060 = 5060;
    static public String iplocal;
     static public String ipServer;
    static public String extlocal;
      
    static public String comb2ProtoTCP = "TCP";
    static public Integer comb2SrcPort5062 = 5062;
    static public Integer comb2DestPort5060 = 5060;
    
    static public  String comb3ProtoUDP = "UDP";
    static public Integer comb3SrcPort5060 = 5060;
    static public Integer comb3DestPort5060 = 5060;
    
    static public String comb4ProtoTCP = "TCP";
    static public Integer comb4SrcPort5060 = 5060;
    static public Integer comb4DestPort5060 = 5060;

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
    
    
    
    
}
