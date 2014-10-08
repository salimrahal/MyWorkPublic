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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 * @author salim
 */
public class ClTcp {
    /*
     - get the parameters from the UI: portSig, portlat, porttrf, codec, timelength, customer name
     - connect to the TCP server thru portSig
     - send the params to the server:  portlat, porttrf, codec, timelength, customer name, testUuid
     - rcv a confirmation message ACK to start the test and sending packets
     */

    String portSig, portlat, porttrf;
    Socket socketSig = null;
    Socket socketUp = null;
    Socket socketD = null;

    public ClTcp(String portSig, String portUp, String portD) {
        this.portSig = portSig;
        socketSig = new Socket();
        socketUp = new Socket();
        socketD = new Socket();
    }

    public boolean sendTrfReqToServerUp(String svip, String portUp, String req_key) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;
        try {
            socketUp.connect(new InetSocketAddress(svip, Integer.parseInt(portUp)), TrfBo.T_T);
            System.out.println("sendTrfReqToServerUp:Req Type:" + req_key + " Process Request: connected.");
            out = new PrintWriter(socketUp.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketUp.getInputStream()));
            success = sendTrfReq(in, out, portUp, req_key);
        } catch (UnknownHostException e) {
            outmsg = "sendTrfReqToServerUp: sendStream: Don't know about host: " + svip;
            System.err.println(outmsg);
            setresultmessage(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.err.println("sendTrfReqToServerUp: Process Request:socketTimeout" + outmsg);
            setresultmessage(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.err.println("sendTrfReqToServerUp:Process Request:iOException" + outmsg);
            setresultmessage(outmsg);
        } finally {
            if (socketUp != null) {
                out.close();
                in.close();
                socketUp.close();
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
            System.out.println("sendTrfReqToServerDown:Req Type:" + req_key + " Process Request: connected.");
            out = new PrintWriter(socketD.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketD.getInputStream()));
            success = sendTrfReq(in, out, portD, req_key);
        } catch (UnknownHostException e) {
            outmsg = "sendTrfReqToServerDown: sendStream: Don't know about host: " + svip;
            System.err.println(outmsg);
            setresultmessage(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.err.println("sendTrfReqToServerDown:Process Request:socketTimeout" + outmsg);

            setresultmessage(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.err.println("sendTrfReqToServerDown:Process Request:iOException" + outmsg);
            setresultmessage(outmsg);
        } finally {
            if (socketD != null) {
                out.close();
                in.close();
                socketD.close();
            }
        }
        return success;
    }

    public boolean sendParamToServer(String portlat, String porttrfU, String porttrfD, String codec, String timelength, String custname, String svip, String testUuid) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;

        try {
            socketSig.connect(new InetSocketAddress(svip, Integer.parseInt(portSig)), TrfBo.T_T);
            System.out.println("sendParamToServer:Process Request: connected.");
            out = new PrintWriter(socketSig.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketSig.getInputStream()));

            success = sendParam(in, out, codec, timelength, custname, testUuid, portlat, porttrfU, porttrfD);
        } catch (UnknownHostException e) {
            outmsg = "sendParamToServer:sendStream: Don't know about host: " + svip;
            System.err.println(outmsg);
            setresultmessage(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.err.println("sendParamToServer:Process Request:socketTimeout" + outmsg);

            setresultmessage(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.err.println("sendParamToServer:Process Request:iOException" + outmsg);
            setresultmessage(outmsg);
        } finally {
            if (socketSig != null) {
                out.close();
                in.close();
                socketSig.close();
            }
        }
        return success;
    }

    private boolean sendTrfReq(BufferedReader in, PrintWriter out, String port, String req_key) throws Exception {
        boolean ack = false;
        System.out.println("send sendTrfReq param.. req type:" + req_key);
        String msgToSend = req_key;
        StringReader msgreader = new StringReader(msgToSend);
        BufferedReader msgbr = new BufferedReader(msgreader);
        String msgRecv;
        String submsgToSend;
        StringBuilder strbuilder = new StringBuilder();
        boolean firstLine = true;
        while ((submsgToSend = msgbr.readLine()) != null) {
            //write to the server
            out.println(submsgToSend);
            //recieve from the server,
            //in some case it will freeze here nothing is received, so a timeout will be triggered
            msgRecv = in.readLine();
            if (firstLine) {
                System.out.println("echo: " + msgRecv);
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
     */

    public boolean sendParam(BufferedReader in, PrintWriter out, String codec, String timelength, String custname, String tstid, String portlat, String porttrfU, String porttrfD) throws Exception {
        boolean ack = false;
        System.out.println("sendParamToServer:sendparam()");
        String msgToSend = generateQueryParam(portlat, porttrfU, porttrfD, codec, timelength, custname, tstid);
        StringReader msgreader = new StringReader(msgToSend);
        BufferedReader msgbr = new BufferedReader(msgreader);
        String msgRecv;
        String submsgToSend;
        StringBuilder strbuilder = new StringBuilder();
        boolean firstLine = true;
        while ((submsgToSend = msgbr.readLine()) != null) {
            //write to the server
            out.println(submsgToSend);
            //recieve from the server,
            //in some case it will freeze here nothing is received, so a timeout will be triggered
            System.out.println("sendParamToServer:sendparam(): waiting for inputs..");
            msgRecv = in.readLine();
            System.out.println("echo: " + msgRecv);
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

    public void setresultmessage(String outmessage) {
        //AlgJPanel.resultmsgjlabel.setText(outmessage);        
        String labelText = String.format("<html><div style=\"width:%dpx;\"><p align=\"center\">%s</p></div><html>", 200, outmessage);
        resultmsgjlabel.setText(labelText);
        resultmsgjlabel.setBackground(Color.red);
    }
}
