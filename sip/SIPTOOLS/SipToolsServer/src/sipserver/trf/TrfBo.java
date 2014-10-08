/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
    public static final Integer U_T = 20000;//millisecond
    public static final Integer Packet_Max_Delay = 20000;//millisecond
    public static final Integer T_P = 50000;//millisecond
    public static final Integer F_DELAY = 500;//millisecond
    public static final String CODEC_KEY = "codec";
    public static final String TST_ID_KEY = "tstid";//only to be accepted
    public static final String ACK = "ACK";
    public static final String REQ_IN_KEY = "REQIN";
    public static final String REQ_OUT_KEY = "REQOUT";
    TrfDao trfdao;

    public TrfBo() {
        trfdao = new TrfDao();
    }

       public synchronized static void closeRess(Socket clientSocket, PrintWriter out, BufferedReader in) {
        if (out != null) {
            out.close();
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
                // System.out.println("Client connection(clientSocket) is closed");
            } catch (IOException ex) {
                Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("TrfBo: closeRess: ressource closed");
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

    public boolean releasePort(String portparam) throws Exception {
        System.out.println("releasePort:" + portparam);
        boolean portreleased = false;
        //release the port  
        int port = Integer.valueOf(portparam);
        //update the port status in DB f->b
        portreleased = trfdao.updateOnePortStatus(port, "f");
        return portreleased;
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
        System.out.println("savingParamsTobean="+param.toString());
        return param;
    }

    public static void main(String[] args) {
    }

    private static String getConfLoc() {
        com.safirasoft.Pivot_Service service = new com.safirasoft.Pivot_Service();
        com.safirasoft.Pivot port = service.getPivotPort();
        return port.getConfLoc();
    }

}
