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
   

    String portSig, portlat, porttrf;
    Socket socketSig = null;
    Socket socketUp = null;
    Socket socketD = null;
    Socket socketL = null;
    TrfBo trfbo;

    public ClTcp(String portSig, String portUp, String portD, String portL) {
        this.portSig = portSig;
        socketSig = new Socket();
        socketUp = new Socket();
        socketD = new Socket();
        socketL = new Socket();
        trfbo = new TrfBo();
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

    public boolean sendParamToServer(String portlat, String porttrfU, String porttrfD, String codec, String timelength, String custname, String svip, String testUuid) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;

        try {
            socketSig.connect(new InetSocketAddress(svip, Integer.parseInt(portSig)), TrfBo.T_T);
            out = new PrintWriter(socketSig.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socketSig.getInputStream()));
           // System.out.println("sendParamToServer:Process Request: connected.");
            success = sendParam(in, out, codec, timelength, custname, testUuid, portlat, porttrfU, porttrfD);
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


    public boolean sendParam(BufferedReader in, PrintWriter out, String codec, String timelength, String custname, String tstid, String portlat, String porttrfU, String porttrfD) throws Exception {
        boolean ack = false;
        //System.out.println("sendParamToServer:sendparam()");
        String msgToSend = generateQueryParam(portlat, porttrfU, porttrfD, codec, timelength, custname, tstid);
        StringReader msgreader = new StringReader(msgToSend);
        BufferedReader msgbr = new BufferedReader(msgreader);
        String msgRecv;
        String submsgToSend;
        StringBuilder strbuilder = new StringBuilder();
        boolean firstLine = true;
        while ((submsgToSend = msgbr.readLine()) != null) {
            out.println(submsgToSend);
            msgRecv = in.readLine();
            //System.out.println("sendParamToServer readLine: " + msgRecv);
            if (firstLine) {
                if (msgRecv.equalsIgnoreCase(ACK)) {
                    ack = true;
                }//todo: add elseif result  equal = busy
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

    public void setresultmessageTcp(String outmessage) {
      trfbo.setresultmessage(resultmsgjlabel, outmessage);
    }
}
