/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.math.BigDecimal;
import org.omg.CORBA.TRANSACTION_MODE;
import sipserver.trf.bean.Param;
import sipserver.trf.dao.TrfDao;

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
    TrfDao trfdao;

    public TrfBo() {
        trfdao = new TrfDao();
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
                case "porttrf":
                    param.setPortrf(keyval[1]);
                    break;

            }
            //add the client public Ip to the bean
            param.setClientIp(clientIp);
        }//end for
        System.out.println(param.toString());
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
