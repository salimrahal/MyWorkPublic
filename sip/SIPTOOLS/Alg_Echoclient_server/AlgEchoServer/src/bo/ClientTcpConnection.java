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
        PrintWriter out = null;
        BufferedReader in = null;
        String threadName = Thread.currentThread().getName();
        try {
           
            System.out.println("Sip ServerTcp: threadName ["
                    + threadName + "] is going to handle TCP connection num " + clientID+". Waiting to inputs..");
            String subStr = null;
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Sip ServerTcp: " + inputLine);
                out.println(inputLine);
                //TODO: if the first line contains OPTIONS then break and dont re-send the message
                if (inputLine.equals("Bye.")) {
                    break;
                }
            }
          
//            if (subStr.contains(registerKey) || subStr.contains(inviteKey)) {
//                //To handle later
//            } else {
//                System.out.println("Sip DatagramServer:unknown client, disregard the packet");
//            }
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
          System.out.println("Sip ServerTcp: [" + new Date() + "]\n - [" + threadName + "] : clientID:" + clientID + ". messages are sent back and the connection is closed.");
    }//end of method

}
