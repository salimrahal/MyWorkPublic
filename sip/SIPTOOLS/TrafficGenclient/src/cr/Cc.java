/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr;

import bn.Param;
import bo.ClTcp;
import bo.LatRunnable;
import bo.TrfBo;
import bo.TrfDgmRunnableU;
import bo.TrfDmgRunnableD;
import bo.WSBo;
import bo.WsRes;
import com.safirasoft.IOException_Exception;
import com.safirasoft.ParserConfigurationException_Exception;
import com.safirasoft.PrtMiscVo;
import com.safirasoft.ResVo;
import com.safirasoft.SAXException_Exception;
import gui.TrfJPanel;
import static gui.TrfJPanel.resultmsgjlabel;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ResVo launchtest(String codecparam, String timeLengthParam, String custnmparam) throws IOException, Exception {
        String resmsg = TrfBo.M_PR;
        ResVo resvo = null;
        try {
            resultmsgjlabel.setText(resmsg);
            if (TrfBo.uchkr(TrfBo.genul())) {
                PrtMiscVo miscPortObj = WSBo.getMiscPorts();
                String portlat = miscPortObj.getPrtLatNum();
                String porttrfU = miscPortObj.getPrtTrfNumUp();
                String porttrfD = miscPortObj.getPrtTrfNumDown();
                String portSig = miscPortObj.getPrtSigNum();
                //portlat = "null";
                System.out.println("ws miscPortObj= prtSig=" + portSig + ";porttrfU/d=" + porttrfU + "/" + porttrfD + "/prtlat=" + portlat);
                if (portlat.equalsIgnoreCase("null") || porttrfU.equalsIgnoreCase("null")) {
                    trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_PRT_B);
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
                        InetAddress inetAddrDest = InetAddress.getByName(srip);
                        WsRes wsres = new WsRes();
                        //launch lat&jitter test up/down
                        launchLatDownRunnable(param, inetAddrDest);
                        //4- launch up packet lost test: sending/receiving packets
                        Thread thUp = launchTrafficUp(param, inetAddrDest);
                        Thread thDown = lauchktLossDownRunnable(param, inetAddrDest, wsres);
                        //System.out.println("lauchktLossDownRunnable join() started");
                        //thDown.join();
                        // System.out.println("Thread:"+Thread.currentThread().getName()+" waiting..");
                        //Thread.currentThread().wait(timelength);
                        // System.out.println("Thread:"+Thread.currentThread().getName()+" finish wait");

                        resvo = wsres.getRes();
                        resultmsgjlabel.setText(TrfBo.M_FIN);
                    } else {
                        System.out.println("Error:launchtest::success: Failed!");
                        trfBo.setresultmessage(resultmsgjlabel, TrfBo.MSG_CONN_SV_PB);
                    }
                }//end of else
            }//end of if ws ok
            else {
                resmsg = TrfBo.M_NC;
                trfBo.setresultmessage(resultmsgjlabel, resmsg);
            }
        } catch (UnknownHostException ex) {
            resmsg = TrfBo.M_NC;
            trfBo.setresultmessage(resultmsgjlabel, resmsg);
        } catch (ParserConfigurationException_Exception | IOException_Exception | SAXException_Exception ex) {
            Logger.getLogger(TrfJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectException conex) {
            resmsg = TrfBo.MSG_CONN_TO;
            trfBo.setresultmessage(resultmsgjlabel, resmsg);
        }
        return resvo;
    }

    public Thread launchTrafficUp(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        TrfDgmRunnableU trfDgmU = new TrfDgmRunnableU(param, addressDest, 0);
        Thread trfDgmUThread = new Thread(trfDgmU);
        trfDgmUThread.start();
        //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished   
        return trfDgmUThread;
    }

    public Thread lauchktLossDownRunnable(Param param, InetAddress addressDest, WsRes wsres) throws SocketException, InterruptedException {
        int portsrc = Integer.valueOf(param.getPortrfD());
        int portdest = Integer.valueOf(param.getPortrfD());
        TrfDmgRunnableD trfDgmD = new TrfDmgRunnableD(param, addressDest, portsrc, portdest, wsres, 0);
        Thread trfDgmDThread = new Thread(trfDgmD);
        trfDgmDThread.start();
        //trfDgmDThread.join();
        return trfDgmDThread;
    }

    public void launchLatDownRunnable(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        int portsrc = Integer.valueOf(param.getPortlat());
        int portdest = Integer.valueOf(param.getPortlat());
        LatRunnable latDrun = new LatRunnable(param, addressDest, portsrc, portdest, 0);
        Thread latrunThread = new Thread(latDrun);
        //imp to pass the lat first
        latrunThread.setPriority(8);
        latrunThread.start();
        //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished   
        //imp
        latrunThread.join();
    }

//    public String getPktLossDown(Param param, InetAddress addressDest) throws SocketException, InterruptedException {
//        String pktLlossDown = "";
//        int portsrc = Integer.valueOf(param.getPortrfD());
//        int portdest = Integer.valueOf(param.getPortrfD());
//
//        TrfDmgCallableD trfDtask = new TrfDmgCallableD(param, addressDest, portsrc, portdest, 0);
//        Future<String> futureTask = executor.submit(trfDtask);
//        try {
//            pktLlossDown = fJitter: you --> server: peak: 109.9 ms; average: 80 ms
//        } catch (ExecutionException ex) {
//            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return pktLlossDown;
//    }
//    public LatVo launchLatDownCallable(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
//        LatVo latVo = null;
//        int portsrc = Integer.valueOf(param.getPortlat());
//        int portdest = Integer.valueOf(param.getPortlat());
//        LatCallable latDtask = new LatCallable(param, addressDest, portsrc, portdest, 0);
//        Future<LatVo> futureTask = executor.submit(latDtask);
//        try {
//            latVo = futureTask.get();
//        } catch (ExecutionException ex) {
//            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return latVo;
//    }
}
