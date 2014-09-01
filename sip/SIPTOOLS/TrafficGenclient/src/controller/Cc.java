/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bo.ClTcp;
import bo.TrfGenBo;
import bo.WSBo;
import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtMiscVo;
import com.safirasoft.SAXException_Exception;
import gui.TrfJPanel;
import static gui.TrfJPanel.resultmsgjlabel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class Cc {

    ClTcp cltcp;
    TrfGenBo trfBo;

    public Cc() {

        trfBo = new TrfGenBo();
    }

    public void launchtest(String codecparam, String timeLengthParam, String custnmparam) throws IOException, Exception {
        try {
            //1- call webservice to check for portLat, prtTrf, portSig and srip
                /**/
            PrtMiscVo miscPortObj = WSBo.getMiscPorts();
            String portlat = miscPortObj.getPrtLatNum();
            String porttrf = miscPortObj.getPrtTrfNum();
            String portSig = miscPortObj.getPrtSigNum();

            System.out.println("miscPortObj= " + miscPortObj);
            if (portlat.equalsIgnoreCase("null") || porttrf.equalsIgnoreCase("null")) {
                resultmsgjlabel.setText(bo.TrfGenBo.M_PRT_B);
            } else {
                //sr ip
                String srip = null;
                srip = miscPortObj.getServerIp();
                srip = "127.0.0.1";//local test
                TrfGenBo.setSrIp(srip);
                System.out.println("remote codec config=" + WSBo.getCodecRemoteList().toArray().toString());
                /*TODO: make the codec list enabled/disabled by comparing with the return codecRemote List */
                //2- generate the ran of the test
                String testUuid = trfBo.generateUUID();//size 36

//3- retreive parameters
                cltcp = new ClTcp(portSig);
                cltcp.sendParamToServer(portlat, porttrf, codecparam, timeLengthParam, custnmparam, srip, testUuid);
            }//end of else

            //TODO: call the methode that sends above param to the server
        } catch (ParserConfigurationException_Exception | IOException_Exception | SAXException_Exception ex) {
            Logger.getLogger(TrfJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
