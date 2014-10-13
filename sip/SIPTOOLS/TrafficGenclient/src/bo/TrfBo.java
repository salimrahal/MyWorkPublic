/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import static bo.Networking.getLocalIpAddress;
import com.safirasoft.ResVo;
import java.awt.Color;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.swing.JLabel;
import vp.vo.PktVo;

/**
 *
 * @author salim
 */
public class TrfBo {

    public static final String M_PR = "in progress..";
    public static final String M_FIN = "completed";
    public static final String M_LAT_PR = "in progress: latency & jitter test";
    public static final String M_PKT_PR = "in progress: packet loss test";
    public static final String M_COMPUT_RES = "computing results..";
      
    public static final String M_PRT_B = "Server is Busy, pleaze try again.";
    public static final String M_I = "The server is not responding";
    public static final String M_NC = "Could not connect to the server! ";
    public static final String MSG_NETWORK_OR_FW_ISSUE = "You have a Network Problem. Check your Network admin.";
    public static final String MSG_E_VAL = "Connection problem, negative results are found, retry the test. If the problem persists contact us.";
    public static final String MSG_CONN_SV_PB = "Server connection problem";
    public static final String MSG_CONN_TO = "connection timed out ; no servers could be reached.";
    public static final String M_U = "You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider";
    public static final String M_U_T = "You have a firewall that might be blocking some traffic during this test. Please check your router or Internet Service Provider";
    public static final String NO_RES = "Could not retreive the results, retry the test please";
    public static final Integer T_T = 20000;////millisecond
    public static final Integer U_T = 7000;//millisecond
    public static final Integer T_P = 50000;//millisecond
    public static final Integer D_T = 30000;//millisecond
    public static final Integer D_S = 1000;//millisecond
    public static final Integer D_P = 2;//sec
    public static final Integer P_MX_D = 20000;//ms
    public static final Integer WS_D = 5000;//ms
    public static final int E_VAL = -1;
    public static String srIp;
    String iplocal;
    public static final String SUCCESS_KEY = "success";
    public static final String FAIL_KEY = "failure";

    private static byte[] bs = new byte[]{104, 116, 116, 112, 58, 47, 47, 115, 105, 112, 116, 111, 111, 108, 115, 46, 110, 101, 120, 111, 103, 121, 46, 99, 111, 109};
    private static byte[] b0 = new byte[]{56, 48, 56, 48};
    private static byte[] b1 = new byte[]{47, 83, 105, 112, 84, 111, 111, 108, 115, 65, 112, 112, 47, 80, 105, 118, 111, 116};
    private final static String newline = "\r\n";

    public static final String ACK = "ACK";
    public static final String REQ_IN_KEY = "REQIN";
    public static final String REQ_OUT_KEY = "REQOUT";

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

    /*
     Jitter: you --> server: peak: 109.9 ms; average: 80 ms
     Jitter: server --> you: peak: 19 ms; average: 17 ms

     Latency: you --> server: peak: 10 ms; average: 8 ms
     Latency: server --> you:  peak: 15 ms; average: 9 ms

     Packet loss: you --> server: 56.6 %
     Packet loss: server --> you: 19.6 %
    
    todo: instead of result -1 show "time out" on UI
 
     */
    public void renderResults(javax.swing.JTextArea testStatTextArea, ResVo resvo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Your Public IP address: ").append(resvo.getPuip()).append(newline);
        sb.append(newline);
        sb.append("Upstream: Packet loss: you --> server: ").append(resvo.getUppkloss()).append("%").append(newline);
        sb.append("Downstream: Packet loss: server --> you: ").append(resvo.getDopkloss()).append("%").append(newline);
        sb.append(newline);
        sb.append("Upstream: Latency: you --> server: Peak: ").append(resvo.getUplatpeak()).append("ms; average: ").append(resvo.getUplatav()).append("ms").append(newline);
        sb.append("Downstream: Latency: server --> you: Peak: ").append(resvo.getDolatpeak()).append("ms; average: ").append(resvo.getDolatav()).append("ms").append(newline);
        sb.append(newline);
        sb.append("Upstream: Jitter: you --> server: Peak: ").append(resvo.getUpjtpeak()).append("ms; average: ").append(resvo.getUpjtav()).append("ms").append(newline);
        sb.append("Downstream: Jitter: server --> you: Peak: ").append(resvo.getDojtpeak()).append("ms; average: ").append(resvo.getDojtav()).append("ms").append(newline);
        
        testStatTextArea.setText(sb.toString());
    }

    public boolean isES(ResVo resvo) {
        boolean res = false;//
        if (resvo.getDopkloss() == E_VAL || resvo.getDolatpeak() == E_VAL || resvo.getDolatav() == E_VAL || resvo.getDojtav() == E_VAL || resvo.getDojtpeak() == E_VAL
                || resvo.getUppkloss() == E_VAL || resvo.getUplatpeak() == E_VAL || resvo.getUplatav() == E_VAL || resvo.getUpjtpeak() == E_VAL || resvo.getUpjtav() == E_VAL) {
            res = true;
        }
        return res;
    }

    public void setresultmessage(JLabel resultmsgjlabel, String outmessage) {
        //AlgJPanel.resultmsgjlabel.setText(outmessage);        
        String labelText = String.format("<html><div style=\"width:%dpx;\"><p align=\"center\">%s</p></div><html>", 200, outmessage);
        resultmsgjlabel.setText(labelText);
        resultmsgjlabel.setBackground(Color.red);
    }
}
