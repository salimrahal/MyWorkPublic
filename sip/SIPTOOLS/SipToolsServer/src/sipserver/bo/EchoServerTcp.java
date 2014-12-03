/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.bo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author salim 
 */
public class EchoServerTcp implements Runnable {

    ServerSocket serverSocket = null;
    InetAddress address;
  
    public EchoServerTcp(Integer port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Sip Echo ServerTcp: listening on port " + port + " /using cachedThreadPoo");
        } catch (IOException e) {
            System.err.println("ServerTcp: Could not listen on port:" + port);
            System.exit(1);
        }
    }

    public void processrequests() throws IOException {
        int i = 0;// when it attemps 90 the client cannot sende / receive data to the server: DEAD Lock
        boolean processrequets = true;
      
        ExecutorService poolservice = Executors.newCachedThreadPool();
        System.out.println("Sip ServerTcp starts..\n waiting for connections");
        while (processrequets) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Sip ServerTcp: Connection successful\n creating new thread for the clientId=" + i);          
                ClientTcpConnection clientcpConn = new ClientTcpConnection(clientSocket, i);
                poolservice.execute(clientcpConn);  
                i++;
            } catch (IOException e) {
                System.err.println("Sip ServerTcp: Accept failed.");
                System.exit(1);
            }

            //processrequets = false;
        }//end of while true
        if (serverSocket != null) {
            serverSocket.close();
        }
    }//end of process requests

    @Override
    public void run() {
        try {
            processrequests();
        } catch (IOException ex) {
            System.out.println("Sip ServerTcp: Error: couldn;t run the ServerTcp:" + ex.getLocalizedMessage());
        }
    }
}
