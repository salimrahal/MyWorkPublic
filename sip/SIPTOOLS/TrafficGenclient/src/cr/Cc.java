/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr;

import bn.Param;
import bo.ClTcp;
import bo.TrfBo;
import bo.TrfDgmRunnableD;
import bo.TrfDgmRunnableU;
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
                /*TODO: handle websirvice timeout
            */
            System.out.println("calling ws and waiting for reply....");
            PrtMiscVo miscPortObj = WSBo.getMiscPorts();
            String portlat = miscPortObj.getPrtLatNum();
            String porttrfU = miscPortObj.getPrtTrfNumUp();
            String porttrfD = miscPortObj.getPrtTrfNumDown();
            String portSig = miscPortObj.getPrtSigNum();
            //portlat = "null";
            System.out.println("ws results= miscPortObj= prtSig=" + portSig + ";porttrfU/d=" + porttrfU + "/" + porttrfD + "/prtlat=" + portlat);
            if (portlat.equalsIgnoreCase("null") || porttrfU.equalsIgnoreCase("null")) {
                resultmsgjlabel.setText(bo.TrfBo.M_PRT_B);
            } else {
                //sr ip
                String srip = null;
                srip = miscPortObj.getServerIp();
                TrfBo.setSrIp(srip);
                System.out.println("remote codec config=" + WSBo.getCodecRemoteList().toArray().toString());
                /*TODO: make the codec list enabled/disabled by comparing with the return codecRemote List */
                //2- generate the x of the test
                String testUuid = trfBo.generateUUID();//size 36

//3- send parameters
                cltcp = new ClTcp(portSig);
                boolean success = cltcp.sendParamToServer(portlat, porttrfU, porttrfD, codecparam, timeLengthParam, custnmparam, srip, testUuid);
                //Thread.currentThread().wait(TrfBo.D_S);
                if (success) {
                    Param param = new Param();
                    param.setTimelength(timeLengthParam);
                    param.setCodec(codecparam);
                    param.setPortlat(portlat);
                    param.setPortrfU(porttrfU);
                    param.setPortrfD(String.valueOf(porttrfD));//for test
                    param.setTstid(testUuid);
                    param.setCustname(custnmparam);
                    System.out.println("launchtest::success, begin of sending packets");
                    //4- launch up packet lost test: sending/receiving packets
                    InetAddress inetAddrDest = InetAddress.getByName(srip);
                    launchTrafficTest(param, inetAddrDest);
                    //5- launch lat&jitter test up/down
                } else {
                    System.out.println("Error:launchtest::success: Failed!");
                }
            }//end of else
        } catch (ParserConfigurationException_Exception | IOException_Exception | SAXException_Exception ex) {
            Logger.getLogger(TrfJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void launchTrafficTest(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        /* */
        TrfDgmRunnableU trfDgmU = new TrfDgmRunnableU(param, addressDest, 0);
        Thread trfDgmUThread = new Thread(trfDgmU);
        trfDgmUThread.start();
       
        int portsrc = Integer.valueOf(param.getPortrfD());
        int portdest = Integer.valueOf(param.getPortrfD());
        TrfDgmRunnableD trfDgmD = new TrfDgmRunnableD(param, addressDest, portsrc, portdest, 0);
        Thread trfDgmDThread = new Thread(trfDgmD);
        trfDgmDThread.start();
        //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished
        //System.out.println(Thread.currentThread().getName() + " / before join..");
        //no need to join for now
        trfDgmUThread.join();
        trfDgmDThread.join();
    }

    public void launchLatListeningPoint(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        //TODO latency check
         int portsrc = Integer.valueOf(param.getPortrfD());
        int portdest = Integer.valueOf(param.getPortrfD());
    }

}
