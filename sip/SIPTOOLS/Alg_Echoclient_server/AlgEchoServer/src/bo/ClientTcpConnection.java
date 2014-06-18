/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import algechoserver.AlgEchoServer;

/**
 *
 * @author salim
 */
public class ClientTcpConnection implements Runnable {

    Socket clientSocket;
    String registerKey = "REGISTER";
    String inviteKey = "INVITE";
    String optionKey = "OPTIONS";//to be dropped
    private Integer clientID;

    public ClientTcpConnection(Socket clientSocket, Integer clientID) {
        this.clientSocket = clientSocket;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        sendbackStream();
    }//end run

    private synchronized void sendbackStream() {
        boolean recognizedClient = true;
        PrintWriter out = null;
        BufferedReader in = null;
        String threadName = Thread.currentThread().getName();
        try {

            System.out.println("Sip ServerTcp: threadName ["
                    + threadName + "] is going to handle TCP connection num " + clientID + ". Waiting to inputs..");
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            int i = 0;
            boolean firstLine = true;
            while ((inputLine = in.readLine()) != null) {
                // if the first line contains OPTIONS then break and dont re-send the message
                if (firstLine) {
                    if (inputLine.contains(optionKey)) {
                        recognizedClient = false;
                        System.out.println("Sip ServerTcp: Unrecognized Client, breaking:" + inputLine);
                        break;
                    }
                    firstLine = false;
                }
                //CLIENT_CALLID_HEADER Disregarded :check for the call ID whether recognized or not: disregard all unknown invite and register
//                if (inputLine.contains(AlgEchoServer.CLIENT_CALLID_HEADER)) {
//                    if (!inputLine.contains(AlgEchoServer.CLIENT_RECOGNIZED_CALLID_PREFIX)) {
//                        recognizedClient = false;
//                        System.out.println("Sip ServerTcp: Unrecognized Client, break:" + inputLine);
//                        break;
//                    }
//                }
                //if recognized client send back the message
                    //System.out.println("Sip ServerTcp: send back:" + inputLine);
                    out.println(inputLine);
                i++;
            }//end of while     
            System.out.println("Sip ServerTcp: reading/writing message is finished . The loop is ended on line number:"+i);

        } catch (IOException ex) {
            Logger.getLogger(ClientTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                    // System.out.println("Client connection(clientSocket) is closed");
                } catch (IOException ex) {
                    Logger.getLogger(ClientTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if(recognizedClient){
                System.out.println("Sip ServerTcp: [" + new Date() + "]\n - [" + threadName + "] : clientID:" + clientID + ". ALL SIP message is sent back and the connection is closed.");
            }
        
            

    }//end of method

}
