/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import static gui.TrfJPanel.resultmsgjlabel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 *
 * @author salim
 */
public class ClUdp {
    /*
     - get the parameters from the UI: portSig, portlat, porttrf, codec, timelength, customer name
     - connect to the TCP server thru portSig
     - send the params to the server:  portlat, porttrf, codec, timelength, customer name, testUuid
     - rcv a confirmation message ACK to start the test and sending packets
     */

    String portSig, portlat, porttrf;
    DatagramSocket dmsocketSig = null;
    DatagramSocket dmsocketUp = null;
    DatagramSocket dmsocketD = null;
    DatagramSocket dmsocketL = null;
    InetAddress inetAddrDest;
    TrfBo trfbo;

    public ClUdp(InetAddress inetAddrDest, String portSig) throws SocketException {
        this.portSig = portSig;
        this.inetAddrDest = inetAddrDest;
        trfbo = new TrfBo();
    }

    public boolean sendParamToServer(String portlat, String porttrfU, String porttrfD, String codec, String timelength, String custname, String svip, String testUuid) throws IOException, Exception {
        String outmsg;
        boolean success = false;
        this.dmsocketSig = new DatagramSocket(Integer.valueOf(portSig));
        try {
            success = sendParam(dmsocketSig, inetAddrDest, codec, timelength, custname, testUuid, portlat, porttrfU, porttrfD);
        } catch (Exception e) {
            outmsg = "sendParamToServer:sendStream: Exception: " + e.getMessage();
            System.out.println(outmsg);
            setresultmessageUdp(outmsg);
        } finally {
            if (dmsocketSig != null) {
                dmsocketSig.close();
            }
        }
        return success;
    }

    public boolean sendLattoServer(String portL, String lat_key) throws IOException, Exception {
        String outmsg;
        boolean success = false;
        try {
            success = sendTrfReq(dmsocketL, inetAddrDest, portL, lat_key);
        } catch (Exception e) {
            outmsg = "sendLattoServer: Exception: " + e.getMessage();
            System.out.println(outmsg);
            setresultmessageUdp(outmsg);
        }
        //socket will be closed in the traffic thread
//        finally {
//            if (dmsocketL != null) {
//                dmsocketL.close();
//            }
//        }
        return success;
    }

    public boolean sendTrfReqToServerDown(String portD, String req_out_key) throws IOException, Exception {
        String outmsg;
        boolean success = false;
        try {
            success = sendTrfReq(dmsocketD, inetAddrDest, portD, req_out_key);
        } catch (Exception e) {
            outmsg = "sendTrfReqToServerDown: Exception: " + e.getMessage();
            System.out.println(outmsg);
            setresultmessageUdp(outmsg);
        }
        //socket will be closed in the traffic thread
//        finally {
//            if (dmsocketD != null) {
//                dmsocketD.close();
//            }
//        }
        return success;
    }

    public boolean sendTrfReqToServerUp(String portU, String req_in_key) throws IOException, Exception {
        String outmsg;
        boolean success = false;
        try {
            success = sendTrfReq(dmsocketUp, inetAddrDest, portU, req_in_key);
        } catch (Exception e) {
            outmsg = "sendTrfReqToServerUp: Exception: " + e.getMessage();
            System.out.println(outmsg);
            setresultmessageUdp(outmsg);
        }
        //socket will be closed in the traffic thread
//        finally {
//            if (dmsocketUp != null) {
//                dmsocketUp.close();
//            }
//        }
        return success;
    }

    private boolean sendTrfReq(DatagramSocket dmsocket, InetAddress inetAddrDestparam, String port, String req_key) throws Exception {
        String outmsg;
        boolean ack = false;
        System.out.println("[" + new Date() + "] send sendTrfReq param.. req type:" + req_key+""
                + ":dmsocket:"+dmsocket.getLocalAddress()+":"+dmsocket.getLocalPort()+";inetAddrDest="+inetAddrDestparam.getHostAddress());
        String msgToSend = req_key;
        byte[] buf = msgToSend.getBytes();
        byte[] incomingbuf = new byte[256];
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, inetAddrDestparam, Integer.valueOf(port));
        DatagramPacket incomingPacket = new DatagramPacket(incomingbuf, incomingbuf.length);
        String msgRecv;
        boolean tryToSend = true;
        int maxTry = 1;
        int packetShotsNum = 2;
        int i = 0;
            try {
                i++;
                System.out.println("[" + new Date() + "] sendTrfReq:trying to send params..send " + packetShotsNum+" successive packets");
                for (int j = 1; j <= packetShotsNum; j++) {
                    dmsocket.send(outgoingPacket);
                }
                dmsocket.setSoTimeout(TrfBo.T_T);// 20 sec
                dmsocket.receive(incomingPacket);
                msgRecv = new String(incomingPacket.getData());
                System.out.println("[" + new Date() + "] sendTrfReq:Received=[" + msgRecv+"]");
                msgRecv = msgRecv.trim();
                System.out.println("[" + new Date() + "] sendTrfReq:Received After trim=[" + msgRecv+"]");
                if (msgRecv.contains(TrfBo.ACK_LAT) || msgRecv.contains(TrfBo.ACK_TRFIN) || msgRecv.contains(TrfBo.ACK_TRFOUT)) {
                    ack = true;
                    System.out.println("[" + new Date() + "] sendTrfReq: ACK recieved no more retry:" + msgRecv);
                    tryToSend = false;
                }
            } catch (SocketTimeoutException se) {
                outmsg = TrfBo.M_U;
                System.out.println("[" + new Date() + "] sendLattoServer:Process Request:socketTimeout" + se.getMessage());
                setresultmessageUdp(outmsg);
            } finally {
                System.out.println("[" + new Date() + "] sendTrfReq: finally..");
                if (i >= maxTry) {
                    System.out.println("[" + new Date() + "] sendTrfReq: no more retry..max reached");
                    tryToSend = false;
                }
            }
        return ack;
    }
    /*
     send param to the server and receive an ACK
     returns true is an ACK is received
     todo: sendParam will return ServerReply Object: ACK, BUSY+:+testLength
     */

    public boolean sendParam(DatagramSocket dmsocketSig, InetAddress inetAddrDestparam, String codec, String timelength, String custname, String tstid, String portlat, String porttrfU, String porttrfD) throws Exception {
        boolean ack = false;
        String msgToSend = generateQueryParam(portlat, porttrfU, porttrfD, codec, timelength, custname, tstid);
        byte[] buf = msgToSend.getBytes();
        byte[] incomingbuf = new byte[256];
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, inetAddrDestparam, Integer.valueOf(portSig));
        DatagramPacket incomingPacket = new DatagramPacket(incomingbuf, incomingbuf.length);
        String msgRecv;
        boolean tryToSend = true;
        int maxTry = 1;
        int i = 0;
        while (tryToSend) {
            try {
                i++;
                System.out.println("[" + new Date() + "]sendParamToServer:trying to send params..send num=" + i);
                dmsocketSig.send(outgoingPacket);
                dmsocketSig.setSoTimeout(TrfBo.T_T);// 20 sec
                dmsocketSig.receive(incomingPacket);
                msgRecv = new String(incomingPacket.getData());
                System.out.println("[" + new Date() + "] sendParamToServer: received:" + msgRecv);
                if (msgRecv.contains(TrfBo.ACK_START)) {
                    ack = true;
                    System.out.println("[" + new Date() + "] sendParamToServer: ACK received no more retry");
                    tryToSend = false;
                }
            } catch (SocketTimeoutException se) {
                System.out.println("[" + new Date() + "] sendParamToServer:Process Request:SocketTimeoutException: " + se.getMessage());
            } finally {
                System.out.println("[" + new Date() + "] sendParamToServer: finally..");
                if (i >= maxTry) {
                    System.out.println("[" + new Date() + "] sendParamToServer: no more retry..max reached");
                    tryToSend = false;
                }
            }
        }//end while
        return ack;
    }

    public String generateQueryParam(String portlat, String porttrfu, String porttrfd, String codec, String timelength, String custname, String tstid) {
        StringBuilder strbuilder = new StringBuilder();
        strbuilder.append("tstid=").append(tstid).append(";codec=").append(codec).append(";timelength=").
                append(timelength).append(";custname=").append(custname).
                append(";portlat=").append(portlat).
                append(";porttrfu=").append(porttrfu).
                append(";porttrfd=").append(porttrfd);
        String msgToSend = strbuilder.toString();
        return msgToSend;
    }

    public void setresultmessageUdp(String outmessage) {
        trfbo.setresultmessage(resultmsgjlabel, outmessage);
    }

    public DatagramSocket getDmsocketUp() {
        return dmsocketUp;
    }

    public void setDmsocketUp(DatagramSocket dmsocketUp) {
        this.dmsocketUp = dmsocketUp;
    }

    public DatagramSocket getDmsocketD() {
        return dmsocketD;
    }

    public void setDmsocketD(DatagramSocket dmsocketD) {
        this.dmsocketD = dmsocketD;
    }

    public DatagramSocket getDmsocketL() {
        return dmsocketL;
    }

    public void setDmsocketL(DatagramSocket dmsocketL) {
        this.dmsocketL = dmsocketL;
    }

}
