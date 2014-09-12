/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.IOException;
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
     public static final Integer Packet_Max_Delay = 20000;//millisecond

    public static String srIp;

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
}
