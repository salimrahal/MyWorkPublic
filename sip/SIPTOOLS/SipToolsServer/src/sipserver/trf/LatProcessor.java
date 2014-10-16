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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sipserver.trf.TrfBo.ACK;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim LatProcessor : processing the In traffic
 */
public class LatProcessor {

    ServerSocket serverSocket = null;
    String latkey;

    public LatProcessor(Integer port, String latkey) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("LatProcessor: listening on port " + port);
            this.latkey = latkey;
        } catch (IOException e) {
            System.err.println("LatProcessor: Could not listen on port:" + port);
        }
    }

    public void processTest(Param param) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        int serverSockTimeout = TrfBo.S_S_T;
        System.out.println("LatProcessor: starts..\n waiting for connections trf key=" + latkey);
        try {
            System.out.println("LatProcessor:waiting for accept..timeout:" + serverSockTimeout + " sec");
            serverSocket.setSoTimeout(serverSockTimeout);
            Socket clientSocket = serverSocket.accept();
            System.out.println("LatProcessor:connection accepted");
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            boolean firstLine = true;
            System.out.println("LatProcessor:[port=" + this.serverSocket.getLocalPort() + "] Connection successful\n");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("LatProcessor: inputLine=" + inputLine);
                if (firstLine) {
                    if (inputLine.contains(latkey)) {
                        System.out.println("LatProcessor:receiving:" + inputLine);
                        System.out.println("LatProcessor:receiving: Accepted, sending back the " + ACK + ".Starting the LAtency test");
                        out.write(ACK);
                        InetAddress inetaddressDest = clientSocket.getInetAddress();
                        TrfBo.closeRess(clientSocket, out, in);
                        try {
                            // launch the traffIn thread
                            launchLatUp(param, inetaddressDest);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LatProcessor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                firstLine = false;
            }
        } catch (SocketTimeoutException se) {
            System.out.println("LatProcessor::" + se.getMessage());
        } catch (IOException e) {
            System.out.println("LatProcessor: Accept failed.");
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }//end of while true
    }//end of process request of IN

    /*
     launch latency test thread
     */
    public void launchLatUp(Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {
        int portsrc = Integer.valueOf(param.getPortlat());
        int portdest = Integer.valueOf(param.getPortlat());
        LatRunnable lat = new LatRunnable(param, inetaddressDest, portsrc, portdest, 0);
        Thread latTh = new Thread(lat);
        latTh.start();
        System.out.println("LatProcessor:launchLatUp waiting to finish the launchLatUp..");
        latTh.join();
    }

}
