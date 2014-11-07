/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpecho;

/**
 *
 * @author salim
 */
import java.io.*;
import java.net.*;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        //String serverHostname = new String ("127.0.0.1");
//        String serverHostname = new String("127.0.0.1");
        String serverHostname = new String("141.138.189.250");
        Integer port = 10007;
        if (args.length > 0) {
            serverHostname = args[0];
        }
        System.out.println("Attemping to connect to host "
                + serverHostname + " on port " + port + ".");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));

        EchoClient client = new EchoClient();

        String msgSent = client.buildRegisterSIPMessage("sereverIp", "10101010", 5060, 5060, "11256979-ca11b60c", "agent");
        StringReader msgreader = new StringReader(msgSent);
        BufferedReader msgbr = new BufferedReader(msgreader);

        String msgRecv;
        String submsgToSend;
        StringBuilder strbuilder = new StringBuilder();
        //out.println(msg);
        //System.out.println("echo: " + in.readLine());
//        System.out.print ("input: ");
        while ((submsgToSend = msgbr.readLine()) != null) {
            //write to the server
            out.println(submsgToSend);
            //recieve from the server
            msgRecv = in.readLine();
            System.out.println("echo: " + msgRecv);

            //append the recv msg to string builder, and add new line: \r\n after every line
            strbuilder.append(msgRecv).append("\r\n");
        }

        msgRecv = strbuilder.toString();
        System.out.println("all message received=[" + msgRecv + "]");
        //msgRecv = msgRecv.trim();
        System.out.println("sent=[" + msgSent + "] received=[" + msgRecv + "]");
        if (msgSent.equalsIgnoreCase(msgRecv)) {
            System.out.println("equal");
        } else {
            System.out.println("NOT equal");
        }
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    public String buildRegisterSIPMessage(String ipServer, String ipLocalparam, Integer portsrc, Integer portdest, String callIdSent, String agentName) throws SocketException {
        String registerMsg = "";
        registerMsg = (new StringBuilder()).append("OPTIONS sip:").append(ipServer).append(":")
                .append(portdest).append(" SIP/2.0\r\nVia: SIP/2.0/UDP ").append(ipLocalparam).append(":")
                .append(portsrc).append(";branch=z9hG4bK-7d0f94c9\r\nFrom: \"SIP_ALG_DETECTOR\" <sip:58569874@")
                .append(ipServer).append(":").append(portdest).append(">;tag=1b38e99fe68ccce9o0\r\nTo: \"SIP_ALG_DETECTOR\" <sip:58569874@")
                .append(ipServer).append(":").append(portdest).
                append(">\r\nCall-ID: ").append(callIdSent).
                append("\r\nCSeq: 6999 REGISTER\r\nMax-Forwards: 70\r\nContact: \"SIP_ALG_DETECTOR\" <sip:58569874@").
                append(ipLocalparam).append(":").
                append(portsrc).append(">;expires=60\r\nUser-Agent: ").append(agentName).append("\r\nContent-Length: 0\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE\r\nSupported: replaces\r\n\r\n").toString();//removed from the end:\r\n\r\n

        return registerMsg;
    }
}
