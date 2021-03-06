/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;
import sipserver.trf.vp.vo.PktVo;

/**
 *
 * @author salim
 *
 */
public class TrfBo {

    String initialLoc;
    public static final String A = "udptrafficDB";
    public static final String B = "user";
    public static final String C = "password";
    public static final Integer T_T = 20000;////millisecond
    //public static final Integer U_T = 20000;//millisecond
    public static final Integer U_T = 7000;//millisecond
    public static final Integer Packet_Max_Delay = 20000;//millisecond
    public static final Integer T_P = 50000;//millisecond
    public static final Integer F_DELAY = 500;//millisecond
    public static final Integer S_S_T = 20000;//millisecond
    public static final Integer LAT_T = 7;//latency window, sec
    public static final String CODEC_KEY = "codec";
    public static final String TST_ID_KEY = "tstid";//only to be accepted
    public static final String ACK = "ACK";
    public static final String ACK_START = "ACK_START";
    public static final String ACK_LAT = "LAT";
    public static final String ACK_TRFIN = "TRFIN";
    public static final String ACK_TRFOUT = "TRFOUT";
    public static final String REQ_IN_KEY = "REQIN";
    public static final String REQ_OUT_KEY = "REQOUT";
    public static final String LAT_KEY = "LAT";
    public static final String PRT_FREE = "f";
    public static final String PRT_BUSY = "b";
//message to be sent as ACK for sub test
    public static final int AcknumTrfIn = 5;
    public static final int AcknumTrfOut = 5;
    public static final int AcknumLat = 5;

    TrfDao trfdao;

    public TrfBo() {
        trfdao = new TrfDao();
    }

    public synchronized static void closesockDm(DatagramSocket socketDglat, DatagramSocket socketDgIn, DatagramSocket socketDgOut) {
        if (socketDglat != null) {
            if (!socketDglat.isClosed()) {
                System.out.println("[" + new Date() + "] TrfBo: closesockDm: closesockDm closed port=" + socketDglat.getLocalPort());
                socketDglat.close();
            }
        }
        if (socketDgIn != null) {
            if (!socketDgIn.isClosed()) {
                System.out.println("[" + new Date() + "] TrfBo: closesockDm: closesockDm closed port=" + socketDgIn.getLocalPort());
                socketDgIn.close();
            }
        }
        if (socketDgOut != null) {
            if (!socketDgOut.isClosed()) {
                System.out.println("[" + new Date() + "] TrfBo: closesockDm: closesockDm closed port=" + socketDgOut.getLocalPort());
                socketDgOut.close();
            }

        }
    }
    /*
     unused
     */

    public synchronized static void closeServerSock(ServerSocket serverSocketLat, ServerSocket serverSockettrfIn, ServerSocket serverSockettrfOut) {
        if (serverSocketLat != null) {
            try {
                if (!serverSocketLat.isClosed()) {
                    serverSocketLat.close();
                    System.out.println("[" + new Date() + "] TrfBo: closeServerSock: serversocket closed port=" + serverSocketLat.getLocalPort());

                }
            } catch (IOException ex) {
                Logger.getLogger(TrfBo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (serverSockettrfIn != null) {
            try {
                if (!serverSockettrfIn.isClosed()) {
                    serverSockettrfIn.close();
                    System.out.println("[" + new Date() + "] TrfBo: closeServerSock: serversocket closed port=" + serverSockettrfIn.getLocalPort());
                }
            } catch (IOException ex) {
                Logger.getLogger(TrfBo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (serverSockettrfOut != null) {
            try {
                if (!serverSockettrfOut.isClosed()) {
                    serverSockettrfOut.close();
                    System.out.println("[" + new Date() + "] TrfBo: closeServerSock: serversocket closed port=" + serverSockettrfOut.getLocalPort());
                }
            } catch (IOException ex) {
                Logger.getLogger(TrfBo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static List hashtoList(HashMap<Integer, PktVo> pktMap) {
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

    /*
     a- it splits the params
     b- save them to param singleton bean
     */
    public Param savingParamsTobean(String paramquery, String clientIp) {
        Param param = new Param();
        //traffic TCPServer:receiving:tstid=8c0514f3-621e-493d-9a71-c1b836c95dab;codec=SILK;
        //timelength=120;custname=custnamefromAppletclass;portlat=5095;porttrf=5108
        String[] paramsArr = paramquery.split(";");
        String[] keyval;
        for (String s : paramsArr) {
            keyval = s.split("=");
            switch (keyval[0]) {
                case "tstid":
                    param.setTstid(keyval[1]);
                    break;
                case "codec":
                    param.setCodec(keyval[1]);
                    break;
                case "timelength":
                    param.setTimelength(keyval[1]);
                    break;
                case "custname":
                    param.setCustname(keyval[1]);
                    break;
                case "portlat":
                    param.setPortlat(keyval[1]);
                    break;
                case "porttrfu":
                    param.setPortrfClientU(keyval[1]);
                    break;
                case "porttrfd":
                    param.setPortrfClientD(keyval[1]);
                    break;

            }
            //add the client public Ip to the bean
            param.setClientIp(clientIp);
        }//end for
        System.out.println("savingParamsTobean=" + param.toString());
        return param;
    }

    public static void main(String[] args) {
    }

    private static String getConfLoc() {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getConfLoc();
    }

    public String getTotalTlength(String n) {
        return multby2(n);
    }

    /*
     time length: 15, 30, 60, 300, 600
     the time sent as param to the server is time length/2, all are multiple of 2 except 15 sec
     15 sec is sent as 7 sec
     7*2=14+1 = 15 sec
     15*2 = 30
     30*2 = 60
     150*2 =300
     300*2=600
     */
    public String multby2(String n) {
        int timelength = Integer.valueOf(n);
        double resDbl = Math.ceil(timelength * 2);
        if (resDbl == 14) {
            resDbl = resDbl + 1;
        }
        int res = (int) resDbl;
        return String.valueOf(res);
    }

}
