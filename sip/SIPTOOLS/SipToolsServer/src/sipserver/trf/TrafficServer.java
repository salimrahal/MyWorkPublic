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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import vo.PrtVo;

/**
 *
 * @author salim
 */
public class TrafficServer {

    public static void launchingTrafficServer() throws SocketException, UnknownHostException, ParserConfigurationException, SAXException, IOException {
       
            /**
             * *********** 
             * 1- Traffic Server It listens only on Sig Port, 
             * 2- it receives from client latency/and traffic port and then accepts
             * traffic(upload) from multiple clients, and generates download
             * traffic depending on the codec or pps ********************
             */
           

            Spf saxparserconf = new Spf();
            saxparserconf.parseConfVOPrt(ConfVO.getInstance().getInitialLoc());
            Integer portSig = ConfVO.getInstance().getPortSig();
            SigProcessor trfthreadTcp = new SigProcessor(portSig);
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
