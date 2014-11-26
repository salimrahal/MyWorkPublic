/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr;

import bn.Param;
import bo.ClTcp;
import bo.ClUdp;
import bo.LatRunnable;
import bo.Networking;
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
import java.net.DatagramSocket;
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

    //ClTcp cltcp;
    ClUdp cludp;
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
            trfBo.setSimpleresultmessage(resultmsgjlabel, resmsg);
            if (TrfBo.uchkr(TrfBo.genul())) {
                //todo: call the new method and retrieve only one port
                PrtMiscVo miscPortObj = WSBo.getMiscPorts();
                String portlat = miscPortObj.getPrtLatNum();
                String porttrfU = miscPortObj.getPrtTrfNumUp();
                String porttrfD = miscPortObj.getPrtTrfNumDown();
//                String porttrfU = portlat;
//                String porttrfD = portlat;
                String portSig = miscPortObj.getPrtSigNum();
                System.out.println("ws miscPortObj= prtSig=" + portSig + ";porttrfU/d=" + porttrfU + "/" + porttrfD + "/prtlat=" + portlat);
                if (portlat.equalsIgnoreCase("null") || porttrfU.equalsIgnoreCase("null")) {
                    trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_PRT_B);
                } else {
                    //post the port to be used for this test:
                    trfBo.renderJTextAreaPortUsed(TrfJPanel.jTextAreaPortused, portSig, portlat, porttrfU, porttrfD);
                    //sr ip
                    String srip = null;
                    //retrieve the traffic server Ip
                    srip = miscPortObj.getServerIp();
                    InetAddress inetAddrDest = InetAddress.getByName(srip);
                    TrfBo.setSrIp(srip);
                    //2- generate the x of the test
                    String testUuid = trfBo.genID();//size 36
                    //3- send parameters
                    //cltcp = new ClTcp(portSig, porttrfU, porttrfD, portlat);
                    cludp = new ClUdp(inetAddrDest, portSig);
                    boolean success = cludp.sendParamToServer(portlat, porttrfU, porttrfD, codecparam, timeLengthParam, custnmparam, srip, testUuid);
                    //todo: retrieve the ServerReply, it the result is busy for 60 sec the retry after 60 sec
                    if (success) {
                        Param param = new Param();
                        param.setTimelength(timeLengthParam);
                        param.setCodec(codecparam);
                        param.setPortlat(portlat);
                        param.setPortrfU(porttrfU);
                        param.setPortrfD(String.valueOf(porttrfD));//for test
                        param.setTstid(testUuid);
                        param.setCustname(custnmparam);

                        WsRes wsres = new WsRes();
                        //launch lat&jitter test up/down
                        String portlatStr = "[Port=" + portlat + "]";
                        trfBo.setresultmessage(resultmsgjlabel, "Step 1 of 4 - Latency & Jitter Test " + portlatStr + ": In Progress ....");
                        updateJprogressBar(jprobar, 25);
                        //System.out.println("CC: phase-1:begin: latency Down test");
                        boolean isLatLaunched = launchLatDownRunnable(param, inetAddrDest);
                        if (isLatLaunched) {
                             //System.out.println("CC: phase-1:Ends: latency Down test");
                            //4- launch up packet lost test: sending/receiving packets
                            //System.out.println("CC: phase-2:begin: traffic Up");
                            String portUpStr = "[Port=" + porttrfU + "]";
                            trfBo.setresultmessage(resultmsgjlabel, "Step 2 of 4 - Upstream Packet Loss Test " + portUpStr + ": In Progress....");
                            updateJprogressBar(jprobar, 50);
                            launchTrafficUp(param, inetAddrDest);
                        //System.out.println("CC: phase-2:Ends: traffic Up");
                            //System.out.println("CC: phase-3:begin: traffic Down");
                            String portDoStr = "[Port=" + porttrfD + "]";
                            trfBo.setresultmessage(resultmsgjlabel, "Step 3 of 4 - Downstream Packet Loss Test " + portDoStr + ": In Progress....");
                            updateJprogressBar(jprobar, 75);
                            lauchktLossDownRunnable(param, inetAddrDest, wsres);
                            // System.out.println("CC: phase-3:Ends: traffic Down");
                            updateJprogressBar(jprobar, 95);
                            trfBo.setresultmessage(resultmsgjlabel, bo.TrfBo.M_COMPUT_RES);
                            //System.out.println("CC: phase-4:begins: Retrieving results...");
                            resvo = wsres.retreiveResbyWS(param.getTstid());
                        //System.out.println("CC: phase-4:Ends: Retrieving results");
                            //resvo = wsres.getRes();
                            trfBo.setSimpleresultmessage(resultmsgjlabel, TrfBo.M_FIN);
                            updateJprogressBar(jprobar, 100);
                        } else {    
                            resvo = new ResVo();
                            //just set a -1 value a indicator in order to post the firewall message in text area
                            resvo.setDolatpeak(-1);
                            System.out.println("Error:launchtest::success: Failed!");
                            trfBo.setresultmessage(resultmsgjlabel, TrfBo.MSG_CONN_SV_PB_V2);
                        }

                    } else {
                        resvo = new ResVo();
                        //just set a -1 value a indicator in order to post the firewall message in text area
                        resvo.setDolatpeak(-1);
                        System.out.println("Error:launchtest::success: Failed!");
                        trfBo.setresultmessage(resultmsgjlabel, TrfBo.MSG_CONN_SV_PB);
                    }
                }//end of else
            }//end of if ws ok
            else {
                resvo = new ResVo();
                //just set a -1 value a indicator in order to post the firewall message in text area
                resvo.setDolatpeak(-1);
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
        try (DatagramSocket dmsocketU = new DatagramSocket(Integer.valueOf(param.getPortrfU()))) {
            cludp.setDmsocketUp(dmsocketU);
            boolean successReqToServerTrfIn = cludp.sendTrfReqToServerUp(param.getPortrfU(), TrfBo.REQ_IN_KEY);
            if (successReqToServerTrfIn) {
                TrfDgmRunnableU trfDgmU = new TrfDgmRunnableU(cludp.getDmsocketUp(), param, addressDest, 0);
                trfDgmUThread = new Thread(trfDgmU);
                trfDgmUThread.start();
                //System.out.println("CC:launchTrafficUp: waiting to finish the TrfDgmRunnableU thread");
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
        try (DatagramSocket dmsocketD = new DatagramSocket(Integer.valueOf(param.getPortrfD()))) {
            cludp.setDmsocketD(dmsocketD);
            int portsrc = Integer.valueOf(param.getPortrfD());
            int portdest = Integer.valueOf(param.getPortrfD());
            boolean successReqToServerTrfOut = cludp.sendTrfReqToServerDown(param.getPortrfD(), TrfBo.REQ_OUT_KEY);
            if (successReqToServerTrfOut) {
                TrfDmgRunnableD trfDgmD = new TrfDmgRunnableD(dmsocketD, param, addressDest, portsrc, portdest, wsres, 0);
                trfDgmDThread = new Thread(trfDgmD);
                trfDgmDThread.start();
                //System.out.println("CC:lauchktLossDownRunnable: waiting to finish the TrfDmgRunnableD thread");
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

    public boolean launchLatDownRunnable(Param param, InetAddress addressDest) throws UnknownHostException, IOException, InterruptedException {
        boolean res = false;
        String resmsg;
        Thread latrunThread = null;
        try (DatagramSocket dmsocketL = new DatagramSocket(Integer.valueOf(param.getPortlat()))) {
            cludp.setDmsocketL(dmsocketL);
            int portsrc = Integer.valueOf(param.getPortlat());
            int portdest = Integer.valueOf(param.getPortlat());
            boolean successReqToServerLat = cludp.sendLattoServer(param.getPortlat(), TrfBo.LAT_KEY);
            if (successReqToServerLat) {
                res = true;
                LatRunnable latDrun = new LatRunnable(dmsocketL, param, addressDest, portsrc, portdest, 0);
                latrunThread = new Thread(latDrun);
                //imp to pass the lat first
                //latrunThread.setPriority(8);
                latrunThread.start();
                //Swing worker thread will wait until the trafficThread finished, i.e.: traffic Thread join the current thread once he finished
                //imp
                //System.out.println("CC:launchLatDownRunnable: waiting to finish the LatRunnable thread");
                latrunThread.join();
            } else {
                res = false;
                System.out.println("CC:launchLatDownRunnable: successReqToServerLat: the result is false, cannot launch the test of Lat.");
            }
        } catch (Exception ex) {
            Logger.getLogger(Cc.class.getName()).log(Level.SEVERE, null, ex);
            resmsg = ex.getMessage();
            trfBo.setresultmessage(resultmsgjlabel, resmsg);
        }
        return res;
    }

    public void updateJprogressBar(JProgressBar jprobar, int val) {
        jprobar.setValue(val);
    }
}
