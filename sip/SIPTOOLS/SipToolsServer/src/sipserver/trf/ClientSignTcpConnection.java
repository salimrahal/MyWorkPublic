/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sipserver.bo.*;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim
 * for signalization: receiving params
 */
public class ClientSignTcpConnection implements Runnable {

    Socket clientSocket;
    String codedkey = "codec";
    String tstidkey = "tstid";//only to be accepted
    private Integer clientID;
    TrfBo trbo;

    public ClientSignTcpConnection(Socket clientSocket, Integer clientID) {
        this.clientSocket = clientSocket;
        this.clientID = clientID;
         trbo = new TrfBo();
    }

    @Override
    public void run() {
        rcvparams();
    }//end run

    private synchronized void rcvparams() {
        boolean recognizedClient = true;
        PrintWriter out = null;
        BufferedReader in = null;
        String threadName = Thread.currentThread().getName();
        try {

            System.out.println("traffic TCPServer: threadName ["
                    + threadName + "] is going to handle TCP connection num " + clientID + ". Waiting to inputs..");
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            int i = 0;
            boolean firstLine = true;
            while ((inputLine = in.readLine()) != null) {
               
                if (firstLine) {
                    if (inputLine.contains(tstidkey)) {
                        recognizedClient = true;
                        System.out.println("traffic TCPServer:receiving:" + inputLine);
                        //extract the parameters from the client and save them to bean 
                        Param param = trbo.savingParamsTobean(inputLine);
                        break;
                    }
                    firstLine = false;
                }
                //if recognized client accept the parameters
                    System.out.println("traffic ServerTcp: send back:" + inputLine);
                    
                i++;
            }//end of while     
            System.out.println("traffic ServerTcp: reading/writing message is finished . The loop is ended on line number:"+i);

        } catch (IOException ex) {
            Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                    // System.out.println("Client connection(clientSocket) is closed");
                } catch (IOException ex) {
                    Logger.getLogger(ClientSignTcpConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if(recognizedClient){
                System.out.println("traffic ServerTcp: [" + new Date() + "]\n - [" + threadName + "] : clientID:" + clientID + ". ALL SIP message is sent back and the connection is closed.");
            }
        
            

    }//end of method

}
