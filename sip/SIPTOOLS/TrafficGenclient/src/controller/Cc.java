/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Param;
import bo.ClTcp;
import bo.TrfDgmRunnable;
import bo.TrfBo;
import bo.WSBo;
import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtMiscVo;
import com.safirasoft.SAXException_Exception;
import gui.TrfJPanel;
import static gui.TrfJPanel.resultmsgjlabel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class Cc {

    ClTcp cltcp;
    TrfBo trfBo;

    public Cc() {

        trfBo = new TrfBo();
    }

    public void launchtest(String codecparam, String timeLengthParam, String custnmparam) throws IOException, Exception {
        try {
            resultmsgjlabel.setText("test begins..");
            //1- call webservice to check for portLat, prtTrf, portSig and srip
                /**/
            PrtMiscVo miscPortObj = WSBo.getMiscPorts();
            String portlat = miscPortObj.getPrtLatNum();
            String porttrf = miscPortObj.getPrtTrfNum();
            String portSig = miscPortObj.getPrtSigNum();
            //portlat = "null";
            System.out.println("ws results= miscPortObj= prtSig=" + miscPortObj.getPrtSigNum() + ";prttrf=" + miscPortObj.getPrtTrfNum() + ";prtlat=" + miscPortObj.getPrtLatNum());
            if (portlat.equalsIgnoreCase("null") || porttrf.equalsIgnoreCase("null")) {
                resultmsgjlabel.setText(bo.TrfBo.M_PRT_B);
            } else {
                //sr ip
                String srip = null;
                srip = miscPortObj.getServerIp();
                TrfBo.setSrIp(srip);
                System.out.println("remote codec config=" + WSBo.getCodecRemoteList().toArray().toString());
                /*TODO: make the codec list enabled/disabled by comparing with the return codecRemote List */
                //2- generate the ran of the test
                String testUuid = trfBo.generateUUID();//size 36

//3- send parameters
                cltcp = new ClTcp(portSig);
                boolean success = cltcp.sendParamToServer(portlat, porttrf, codecparam, timeLengthParam, custnmparam, srip, testUuid);
                if (success) {
                    Param param = new Param();
                    param.setTimelength(timeLengthParam);
                    param.setCodec(codecparam);
                    param.setPortlat(portlat);
                    param.setPortrf(porttrf);
                    param.setTstid(testUuid);
                    param.setCustname(custnmparam);
                    System.out.println("launchtest::success, begin of sending packets");
                    //4- launch up packet lost test: sending/receiving packets
                    InetAddress inetAddrDest = InetAddress.getByName(srip);
                    launchTrafficListeningPoint(param, inetAddrDest);
                    //5- launch lat&jitter test up/down
                } else {
                    System.out.println("Error:launchtest::success: Failed!");
                }
            }//end of else
        } catch (ParserConfigurationException_Exception | IOException_Exception | SAXException_Exception ex) {
            Logger.getLogger(TrfJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void launchTrafficListeningPoint(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        TrfDgmRunnable trfDgmInOut = new TrfDgmRunnable(param, addressDest, 0);
        Thread trfDgmInOutThread = new Thread(trfDgmInOut);
        trfDgmInOutThread.start();
        //trfDgmInOutThread.join();
    }
    
     public void launchLatListeningPoint(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
       //TODO
    }

}
