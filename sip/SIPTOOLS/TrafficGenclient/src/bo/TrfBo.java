/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import static bo.Networking.getLocalIpAddress;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import vp.vo.JtrVo;
import vp.vo.LatVo;
import vp.vo.PktVo;

/**
 *
 * @author salim
 */
public class TrfBo {

    public static final String M_PR = "in progress..";
    public static final String M_FIN = "finished";
    
    public static final String M_PRT_B = "Server is Busy, pleaze try again.";
    public static final String M_I = "The server is not responding";
    public static final String M_NC = "Could not connect to the server! ";
    public static final String MSG_NETWORK_OR_FW_ISSUE = "You have a Network Problem. Check your Network admin.";

    public static final String MSG_CONN_TO = "connection timed out ; no servers could be reached.";
    public static final String M_U = "You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider";

    public static final Integer T_T = 20000;////millisecond
    public static final Integer U_T = 7000;//millisecond
    public static final Integer T_P = 50000;//millisecond
    public static final Integer D_T = 30000;//millisecond
    public static final Integer D_S = 1000;//millisecond
    public static final Integer D_P = 2;//sec
    public static final Integer P_MX_D = 20000;//ms
    public static final Integer WS_D = 5000;//ms

    public static String srIp;
    String iplocal;

    private static byte[] bs = new byte[]{104, 116, 116, 112, 58, 47, 47, 115, 105, 112, 116, 111, 111, 108, 115, 46, 110, 101, 120, 111, 103, 121, 46, 99, 111, 109};
    private static byte[] b0 = new byte[]{56, 48, 56, 48};
    private static byte[] b1 = new byte[]{47, 83, 105, 112, 84, 111, 111, 108, 115, 65, 112, 112, 47, 80, 105, 118, 111, 116};

    public List hashtoList(HashMap<Integer, PktVo> pktMap) {
        List<PktVo> list = null;
        if (!pktMap.isEmpty()) {
            list = new ArrayList<>();
            Set<Integer> enume = pktMap.keySet();
            for (Integer key : enume) {
                PktVo pktObj = (PktVo) pktMap.get(key);
                list.add(pktObj);
            }
        }
        return list;
    }

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

    public String genID() {
        //generate random UUIDs
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }

    public static String btS(byte[] bs) {
        return new String(bs);
    }
    /*
     usused
     */

    public String getIplocal() throws SocketException {
        iplocal = getLocalIpAddress();
        return iplocal;
    }

    public static boolean uchkr(String up) throws IOException {
        boolean isg = false;
        final URL url = new URL(up);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = 0;

        responseCode = huc.getResponseCode();
        if (responseCode == 200) {
            isg = true;
        } else {
            isg = false;
        }
        return isg;
    }

    public static String genul() {
        String res = null;
        res = btS(bs) + ":" + btS(b0) + btS(b1);
        return res;
    }
}
