/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import static bo.Networking.getLocalIpAddress;
import java.io.IOException;
import java.net.SocketException;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author salim
 */
public class TrfBo {

    public static final String M_PRT_B = "Server is Busy, pleaze try again.";
    public static final String M_I = "The server is not responding";
    public static final String MSG_NETWORK_OR_FW_ISSUE = "You have a Network Problem. Check your Network admin.";
    public static final String M_U = "You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider";

    public static final Integer T_T = 20000;////millisecond
    public static final Integer U_T = 7000;//millisecond
    public static final Integer T_P = 50000;//millisecond
    public static final Integer D_T = 30000;//millisecond
    public static final Integer D_S = 1000;//millisecond
    public static final Integer D_P = 2;//sec

    public static String srIp;
    String iplocal;

    public static String getSrIp() {
        return srIp;
    }

    public static void setSrIp(String srIp) {
        TrfBo.srIp = srIp;
    }

    public String generaterandomnumber() {
        Random rnd = new Random();
        Integer num = rnd.nextInt(1000000000);
        return String.valueOf(num);
    }

    public String generateUUID() {
        //generate random UUIDs
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }
    
    /*
    usused
    */
      public String getIplocal() throws SocketException {
        iplocal = getLocalIpAddress();
        return iplocal;
    }
}
