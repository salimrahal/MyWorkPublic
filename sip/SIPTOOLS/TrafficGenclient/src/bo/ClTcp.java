/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

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
    Socket socket = null;
    private static final String ACK = "ACK";

    public ClTcp(String portSig) {
        this.portSig = portSig;
        socket = new Socket();
    }

    public boolean sendParamToServer(String portlat, String porttrf, String codec, String timelength, String custname, String svip, String testUuid) throws IOException, Exception {
        BufferedReader in = null;
        PrintWriter out = null;
        String outmsg;
        boolean success = false;

        try {
            socket.connect(new InetSocketAddress(svip, Integer.parseInt(portSig)), TrfBo.T_T);
            System.out.println("Process Request: connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            success = sendParam(in, out, codec, timelength, custname, testUuid, portlat, porttrf);
        } catch (UnknownHostException e) {
            outmsg = "sendStream: Don't know about host: " + svip;
            System.err.println(outmsg);
            setresultmessage(outmsg);
        } catch (SocketTimeoutException socketTimeout) {
            outmsg = TrfBo.M_U;
            System.err.println("Process Request:socketTimeout" + outmsg);

            setresultmessage(outmsg);
        } catch (IOException iOException) {
            //handling network unreachable
            outmsg = iOException.getLocalizedMessage();
            //"processRequests: Couldn't get I/O for "
            //+ "the connection to: " + serverHostname + "/" + iOException.getLocalizedMessage();
            System.err.println("Process Request:iOException" + outmsg);
            setresultmessage(outmsg);
        } finally {
            if (socket != null) {
                out.close();
                in.close();
                socket.close();
            }
        }
        return success;
    }

    /*
     send param to the server and receive an ACK
     returns true is an ACK is received
     */
    public boolean sendParam(BufferedReader in, PrintWriter out, String codec, String timelength, String custname, String tstid, String portlat, String porttrf) throws Exception {
        boolean ack = false;
        System.out.println("send param..");
        String msgToSend = generateQueryParam(portlat, porttrf, codec, timelength, custname, tstid);
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

    public String generateQueryParam(String portlat, String porttrf, String codec, String timelength, String custname, String tstid) {
        StringBuilder strbuilder = new StringBuilder();
        strbuilder.append("tstid=").append(tstid).append(";codec=").append(codec).append(";timelength=").
                append(timelength).append(";custname=").append(custname).
                append(";portlat=").append(portlat).
                append(";porttrf=").append(porttrf);
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
