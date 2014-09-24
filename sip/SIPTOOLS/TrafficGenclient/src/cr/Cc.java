/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr;

import bn.Param;
import bo.ClTcp;
import bo.LatCallable;
import bo.TrfBo;
import bo.TrfDgmRunnableU;
import bo.TrfDmgCallableD;
import bo.WSBo;
import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtMiscVo;
import com.safirasoft.SAXException_Exception;
import gui.TrfJPanel;
import static gui.TrfJPanel.resultmsgjlabel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vp.vo.LatVo;

/**
 *
 * @author salim
 */
public class Cc {

    ClTcp cltcp;
    TrfBo trfBo;
    //use to execute tcp send/rcv message callable method
    ExecutorService executor;

    public Cc() {

        trfBo = new TrfBo();
        executor = Executors.newCachedThreadPool();
    }

    public void launchtest(String codecparam, String timeLengthParam, String custnmparam) throws IOException, Exception {
        String resmsg = "in progress..";
        try {
            resultmsgjlabel.setText(resmsg);
            if (TrfBo.uchkr(TrfBo.genul())) {
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
                    String testUuid = trfBo.genID();//size 36

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
                        //4- launch up packet lost test: sending/receiving packets
                        InetAddress inetAddrDest = InetAddress.getByName(srip);
                        //launchTrafficUp(param, inetAddrDest);
                        //String pkLossDown = getPktLossDown(param, inetAddrDest);
                        //System.out.println("CC::pkLossDown=" + pkLossDown);
                        //todo: 5- launch lat&jitter test up/down
                        getlatDown(param, inetAddrDest);
                    } else {
                        System.out.println("Error:launchtest::success: Failed!");
                    }
                }//end of else
            }//end of if
            else {
                resmsg = TrfBo.M_NC;
                resultmsgjlabel.setText(resmsg);
            }
        } catch (UnknownHostException ex) {
            resmsg = TrfBo.M_NC;
            resultmsgjlabel.setText(resmsg);
        } catch (ParserConfigurationException_Exception | IOException_Exception | SAXException_Exception ex) {
            Logger.getLogger(TrfJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void launchTrafficUp(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        System.out.println("launchtest::success, begin of sending packets");
        TrfDgmRunnableU trfDgmU = new TrfDgmRunnableU(param, addressDest, 0);
        Thread trfDgmUThread = new Thread(trfDgmU);
        trfDgmUThread.start();
        //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished   
        trfDgmUThread.join();
    }

    public String getPktLossDown(Param param, InetAddress addressDest) throws SocketException, InterruptedException {
        String pktLlossDown = "";
        int portsrc = Integer.valueOf(param.getPortrfD());
        int portdest = Integer.valueOf(param.getPortrfD());

        TrfDmgCallableD trfDtask = new TrfDmgCallableD(param, addressDest, portsrc, portdest, 0);
        Future<String> futureTask = executor.submit(trfDtask);
        try {
            pktLlossDown = futureTask.get();
        } catch (ExecutionException ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pktLlossDown;
    }

    public LatVo getlatDown(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        LatVo latVo = null;
        int portsrc = Integer.valueOf(param.getPortlat());
        int portdest = Integer.valueOf(param.getPortlat());
         LatCallable latDtask = new LatCallable(param, addressDest, portsrc, portdest, 0);
        Future<LatVo> futureTask = executor.submit(latDtask);
        try {
            latVo = futureTask.get();
        } catch (ExecutionException ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return latVo;
    }

}
