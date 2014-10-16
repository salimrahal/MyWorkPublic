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
import javax.swing.JProgressBar;

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

    public ResVo launchtest(String codecparam, String timeLengthParam, String custnmparam, JProgressBar jprobar) throws IOException, Exception {
        String resmsg = TrfBo.M_CN;
        ResVo resvo = null;//= new ResVo();
        try {
            System.out.println("CC:launchtest::Thread name: " + Thread.currentThread().getName() + " Priority=" + Thread.currentThread().getPriority());
            resultmsgjlabel.setText(resmsg);
            if (TrfBo.uchkr(TrfBo.genul())) {
                PrtMiscVo miscPortObj = WSBo.getMiscPorts();
                String portlat = miscPortObj.getPrtLatNum();
//                String porttrfU = miscPortObj.getPrtTrfNumUp();
//                String porttrfD = miscPortObj.getPrtTrfNumDown();
                String porttrfU = portlat;
                String porttrfD = portlat;
                String portSig = miscPortObj.getPrtSigNum();
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
                    cltcp = new ClTcp(portSig, porttrfU, porttrfD, portlat);
                    boolean success = cltcp.sendParamToServer(portlat, porttrfU, porttrfD, codecparam, timeLengthParam, custnmparam, srip, testUuid);
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
                        trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_LAT_PR);
                        updateJprogressBar(jprobar, 25);
                        System.out.println("CC: phase-1:begin: latency Down test");
                        launchLatDownRunnable(param, inetAddrDest);
                        System.out.println("CC: phase-1:Ends: latency Down test");
                        //4- launch up packet lost test: sending/receiving packets
                        System.out.println("CC: phase-2:begin: traffic Up");
                        trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_PKTUP_PR);
                        updateJprogressBar(jprobar, 50);
                        launchTrafficUp(param, inetAddrDest);
                        System.out.println("CC: phase-2:Ends: traffic Up");
                        System.out.println("CC: phase-3:begin: traffic Down");
                        trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_PKTDO_PR);
                        updateJprogressBar(jprobar, 75);
                        lauchktLossDownRunnable(param, inetAddrDest, wsres);
                        System.out.println("CC: phase-3:Ends: traffic Down");
                        updateJprogressBar(jprobar, 95);
                        trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_COMPUT_RES);
                        System.out.println("CC: phase-4:begins: Retrieving results...");
                        resvo = wsres.retreiveResbyWS(param.getTstid());
                        System.out.println("CC: phase-4:Ends: Retrieving results");
                        //resvo = wsres.getRes();
                        resultmsgjlabel.setText(TrfBo.M_FIN);
                        updateJprogressBar(jprobar, 100);
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
        String resmsg;
        Thread trfDgmUThread = null;
        try {
            boolean successReqToServerTrfIn = cltcp.sendTrfReqToServerUp(TrfBo.getSrIp(), param.getPortrfU(), TrfBo.REQ_IN_KEY);
            if (successReqToServerTrfIn) {
                TrfDgmRunnableU trfDgmU = new TrfDgmRunnableU(param, addressDest, 0);
                trfDgmUThread = new Thread(trfDgmU);
                trfDgmUThread.start();
                System.out.println("CC:launchTrafficUp: waiting to finish the TrfDgmRunnableU thread");
                trfDgmUThread.join();
                //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished
            } else {
                System.out.println("CC:launchTrafficUp: successReqToServerTrfIn: the result is false, cannot lauch the test of traffic Up.");
            }
        } catch (Exception ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
            resmsg = ex.getMessage();
            trfBo.setresultmessage(resultmsgjlabel, resmsg);
        }
        return trfDgmUThread;
    }

    public Thread lauchktLossDownRunnable(Param param, InetAddress addressDest, WsRes wsres) throws SocketException, InterruptedException {
        String resmsg;
        Thread trfDgmDThread = null;
        try {
            int portsrc = Integer.valueOf(param.getPortrfD());
            int portdest = Integer.valueOf(param.getPortrfD());
            boolean successReqToServerTrfOut = cltcp.sendTrfReqToServerDown(TrfBo.getSrIp(), param.getPortrfD(), TrfBo.REQ_OUT_KEY);
            if (successReqToServerTrfOut) {
                TrfDmgRunnableD trfDgmD = new TrfDmgRunnableD(param, addressDest, portsrc, portdest, wsres, 0);
                trfDgmDThread = new Thread(trfDgmD);
                trfDgmDThread.start();
                System.out.println("CC:lauchktLossDownRunnable: waiting to finish the TrfDmgRunnableD thread");
                trfDgmDThread.join();
            } else {
                System.out.println("CC:lauchktLossDownRunnable: successReqToServerTrfOut: the result is false, cannot launch the test of traffic Out.");
            }
        } catch (Exception ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
            resmsg = ex.getMessage();
            trfBo.setresultmessage(resultmsgjlabel, resmsg);
        }
        return trfDgmDThread;
    }

    public void launchLatDownRunnable(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        String resmsg;
        Thread latrunThread = null;
        try {
            int portsrc = Integer.valueOf(param.getPortlat());
            int portdest = Integer.valueOf(param.getPortlat());
            boolean successReqToServerLat = cltcp.sendLattoServer(TrfBo.getSrIp(), param.getPortlat(), TrfBo.LAT_KEY);
            if (successReqToServerLat) {
                LatRunnable latDrun = new LatRunnable(param, addressDest, portsrc, portdest, 0);
                latrunThread = new Thread(latDrun);
                //imp to pass the lat first
                //latrunThread.setPriority(8);
                latrunThread.start();
                //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished
                //imp
                System.out.println("CC:launchLatDownRunnable: waiting to finish the LatRunnable thread");
                latrunThread.join();
            } else {
                System.out.println("CC:launchLatDownRunnable: successReqToServerLat: the result is false, cannot launch the test of Lat.");
            }
        } catch (Exception ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
            resmsg = ex.getMessage();
            trfBo.setresultmessage(resultmsgjlabel, resmsg);
        }
    }

    public void updateJprogressBar(JProgressBar jprobar, int val) {
        jprobar.setValue(val);
    }

    /*
     public void launchLatDownRunnable(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
     int portsrc = Integer.valueOf(param.getPortlat());
     int portdest = Integer.valueOf(param.getPortlat());
     LatRunnable latDrun = new LatRunnable(param, addressDest, portsrc, portdest, 0);
     Thread latrunThread = new Thread(latDrun);
     //imp to pass the lat first
     //latrunThread.setPriority(8);
     latrunThread.start();
     //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished   
     //imp
     System.out.println("CC:launchLatDownRunnable: waiting to finish the LatRunnable thread");
     latrunThread.join();
     }
     */
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
