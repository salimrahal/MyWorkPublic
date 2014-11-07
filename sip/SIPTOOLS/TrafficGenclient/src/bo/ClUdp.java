/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import static bo.TrfBo.ACK;
import static gui.TrfJPanel.resultmsgjlabel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
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

    public ClUdp(InetAddress inetAddrDest, String portSig, String portUp, String portD, String portL) throws SocketException {
        this.portSig = portSig;
        this.inetAddrDest = inetAddrDest;
        dmsocketSig = new DatagramSocket(Integer.valueOf(portSig));
        dmsocketUp = new DatagramSocket(Integer.valueOf(portUp));
        dmsocketD = new DatagramSocket(Integer.valueOf(portD));
        dmsocketL = new DatagramSocket(Integer.valueOf(portL));
        trfbo = new TrfBo();
    }

    public boolean sendParamToServer(String portlat, String porttrfU, String porttrfD, String codec, String timelength, String custname, String svip, String testUuid) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;

        try {

            success = sendParam(dmsocketSig, codec, timelength, custname, testUuid, portlat, porttrfU, porttrfD);
        } catch (UnknownHostException e) {
            outmsg = "sendParamToServer:sendStream: Don't know about host: " + svip;
            System.out.println(outmsg);
            setresultmessageTcp(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.out.println("sendParamToServer:Process Request:socketTimeout" + outmsg);

            setresultmessageTcp(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.out.println("sendParamToServer:Process Request:iOException" + outmsg);
            setresultmessageTcp(outmsg);
        } finally {
            if (socketSig != null) {
                socketSig.close();
            }
            if (out != null) {
                out.close();
                in.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return success;
    }

    public boolean sendLattoServer(String svip, String portL, String lat_key) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;
        try {
            socketL.connect(new InetSocketAddress(svip, Integer.parseInt(portL)), TrfBo.T_T);
            out = new PrintWriter(socketL.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketL.getInputStream()));
            //System.out.println("sendLattoServer:Req Type:" + lat_key + " Process Request: connected.");
            success = sendTrfReq(in, out, portL, lat_key);
        } catch (UnknownHostException e) {
            outmsg = "sendLattoServer: sendStream: Don't know about host: " + svip;
            System.out.println(outmsg);
            setresultmessageTcp(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.out.println("sendLattoServer:Process Request:socketTimeout" + outmsg);

            setresultmessageTcp(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.out.println("sendLattoServer:Process Request: iOException: " + outmsg);
            setresultmessageTcp(outmsg);
        } finally {
            if (socketL != null) {
                socketL.close();
            }
            if (out != null) {
                out.close();
                in.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return success;
    }

    public boolean sendTrfReqToServerUp(String svip, String portUp, String req_key) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;
        try {
            //todo: to handle the connection refused excption implement retry 3 times
            socketUp.connect(new InetSocketAddress(svip, Integer.parseInt(portUp)), TrfBo.T_T);
            out = new PrintWriter(socketUp.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketUp.getInputStream()));
            //System.out.println("sendTrfReqToServerUp:Req Type:" + req_key + " Process Request: connected.");
            success = sendTrfReq(in, out, portUp, req_key);
        } catch (UnknownHostException e) {
            outmsg = "sendTrfReqToServerUp: sendStream: Don't know about host: " + svip;
            System.out.println(outmsg);
            setresultmessageTcp(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.out.println("sendTrfReqToServerUp: Process Request:socketTimeout" + outmsg);
            setresultmessageTcp(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //todo: frequent bug implement retry
            System.out.println("sendTrfReqToServerUp:Process Request:iOException " + outmsg);
            setresultmessageTcp(outmsg);
        } finally {
            if (socketUp != null) {
                socketUp.close();
            }
            if (out != null) {
                out.close();
                in.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return success;
    }

    public boolean sendTrfReqToServerDown(String svip, String portD, String req_key) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;
        try {
            socketD.connect(new InetSocketAddress(svip, Integer.parseInt(portD)), TrfBo.T_T);
            out = new PrintWriter(socketD.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketD.getInputStream()));
            // System.out.println("sendTrfReqToServerDown:Req Type:" + req_key + " Process Request: connected.");
            success = sendTrfReq(in, out, portD, req_key);
        } catch (UnknownHostException e) {
            outmsg = "sendTrfReqToServerDown: sendStream: Don't know about host: " + svip;
            System.out.println(outmsg);
            setresultmessageTcp(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.out.println("sendTrfReqToServerDown:Process Request:socketTimeout" + outmsg);
            setresultmessageTcp(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.out.println("sendTrfReqToServerDown:Process Request: iOException" + outmsg);
            setresultmessageTcp(outmsg);
        } finally {
            if (socketD != null) {
                socketD.close();
            }
            if (out != null) {
                out.close();
                in.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return success;
    }

    private boolean sendTrfReq(BufferedReader in, PrintWriter out, String port, String req_key) throws Exception {
        boolean ack = false;
        //System.out.println("send sendTrfReq param.. req type:" + req_key);
        String msgToSend = req_key;
        StringReader msgreader = new StringReader(msgToSend);
        BufferedReader msgbr = new BufferedReader(msgreader);
        String msgRecv;
        String submsgToSend;
        boolean firstLine = true;
        while ((submsgToSend = msgbr.readLine()) != null) {
            //write to the server
            out.println(submsgToSend);
            //recieve from the server,
            //System.out.println("sendTrfReq: key sent, waiting for inputs..");
            //in some case it will freeze here nothing is received, so a timeout will be triggered
            msgRecv = in.readLine();
            //System.out.println("sendTrfReq: readLine= " + msgRecv);
            if (firstLine) {
                if (msgRecv.equalsIgnoreCase(ACK)) {
                    ack = true;
                }
                firstLine = false;
            }
            //strbuilder.append(msgRecv).append("\r\n");
        }
        return ack;
    }
    /*
     send param to the server and receive an ACK
     returns true is an ACK is received
     todo: sendParam will return ServerReply Object: ACK, BUSY+:+testLength
     */

    public boolean sendParam(DatagramSocket dmsocketSig, String codec, String timelength, String custname, String tstid, String portlat, String porttrfU, String porttrfD) throws Exception {
        boolean ack = false;
        String msgToSend = generateQueryParam(portlat, porttrfU, porttrfD, codec, timelength, custname, tstid);
        byte[] buf = msgToSend.getBytes();
        byte[] incomingbuf = new byte[256];
        DatagramPacket outgoingPacket = new DatagramPacket(buf, buf.length, inetAddrDest, Integer.valueOf(portSig));
        DatagramPacket incomingPacket = new DatagramPacket(incomingbuf, incomingbuf.length);
        String msgRecv;
        String submsgToSend;
        boolean tryToSend = true;
        int maxTry = 2;
        int i = 0;
        while (tryToSend) {
            try {
                i++;
                dmsocketSig.send(outgoingPacket);
                dmsocketSig.setSoTimeout(TrfBo.T_T);
                dmsocketSig.receive(incomingPacket);
                msgRecv = Arrays.toString(incomingPacket.getData());
                System.out.println("sendParamToServer: " + msgRecv);
                if (msgRecv.equalsIgnoreCase(ACK)) {
                    ack = true;
                }
                tryToSend = false;
            } catch (SocketTimeoutException se) {
                System.out.println("sendParamToServer: " + se.getMessage());
            } finally {
                if (i >= maxTry) {
                    tryToSend = false;
                    break;
                }
            }
        }//end while
        //write to the server
        //recieve from the server,
        //in some case it will freeze here nothing is received, so a timeout will be triggered
        //System.out.println("sendParamToServer:sendparam(): waiting for inputs..");
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

    public void setresultmessageTcp(String outmessage) {
        trfbo.setresultmessage(resultmsgjlabel, outmessage);
    }
}
