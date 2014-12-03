/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import cfg.Spf;
import cfg.vo.ConfVO;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import sipserver.trf.dao.TrfDao;

/**
 *
 * @author salim
 */
public class TrafficServer {

    public static void launchingTrafficServer(String localIp) throws SocketException, UnknownHostException, ParserConfigurationException, SAXException, IOException {

       
       
        TrfDao trfdao = new TrfDao();
        try {
           // trfdao.updateALLPortStatus(TrfBo.PRT_FREE);
        } catch (Exception ex) {
            Logger.getLogger(TrafficServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer portSig = ConfVO.getInstance().getPortSig();
        SigProcessor trfthreadTcp = new SigProcessor(localIp, portSig);
        Thread serverTrfThread = new Thread(trfthreadTcp);
        serverTrfThread.start();
    }

//    @Deprecated
//    private static void launchingThreadsTrafficServer(String localIp, List<Integer> portL) throws SocketException, UnknownHostException {
//        System.out.println("Launching Traffic server...on ports: "+Arrays.toString(portL.toArray()));
//              
//        //launching traffic UDP server
//        for (Integer port : portL) {
//            TrfThreadDgm trfthreadUdp = new TrfThreadDgm(localIp, port);
//            Thread serverTrfThread = new Thread(trfthreadUdp);
//            serverTrfThread.start();
//        }
//        //launching traffic tcp server
//        for (Integer port : portL) {
//            TrfRunnableTcpSig trfthreadTcp = new TrfRunnableTcpSig(port);
//            Thread serverTrfThread = new Thread(trfthreadTcp);
//            serverTrfThread.start();
//        }
//    }
}
