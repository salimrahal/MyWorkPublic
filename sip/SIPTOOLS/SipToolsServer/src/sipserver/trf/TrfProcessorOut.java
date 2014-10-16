/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.BufferedReader;
import sipserver.bo.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sipserver.trf.TrfBo.ACK;
import static sipserver.trf.TrfBo.TST_ID_KEY;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim TrfProcessorIn : processing the In traffic
 */
public class TrfProcessorOut {

    ServerSocket serverSocket = null;
    String trfkey;

    public TrfProcessorOut(Integer port, String trfkey) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TrfProcessorout: listening on port " + port);
            this.trfkey = trfkey;
        } catch (IOException e) {
            System.err.println("TrfProcessorout: Could not listen on port:" + port);
        }
    }

    public void processTest(Param param) throws IOException {
        PrintWriter out;
        BufferedReader in;
        int serverSockTimeout = TrfBo.S_S_T;
        System.out.println("TrfProcessorout: starts..\n waiting for connections trf key=" + trfkey);
        try {
            //wait for connection for a given time then it fires a timeout exception
            System.out.println("TrfProcessorout:waiting for accept..timeout:" + serverSockTimeout + " sec");
            serverSocket.setSoTimeout(serverSockTimeout);
            Socket clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            boolean firstLine = true;
            System.out.println("TrfProcessorout:[port=" + this.serverSocket.getLocalPort() + "] Connection successful\n");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("TrfProcessorout: inputLine=" + inputLine);
                if (firstLine) {
                    System.out.println("TrfProcessorout:receiving:" + inputLine);
                    if (inputLine.contains(trfkey)) {
                        System.out.println("TrfProcessorout: Accepted, sending back the " + ACK + ".Starting the trafficOut");
                        out.write(ACK);
                        InetAddress inetaddressDest = clientSocket.getInetAddress();
                        TrfBo.closeRess(clientSocket, out, in);
                        try {
                            // launch the traffIn thread
                            launchTrafficOut(param, inetaddressDest);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(TrfProcessorOut.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TrfProcessorOut.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                firstLine = false;
            }
        } catch (SocketTimeoutException se) {
            System.out.println("TrfProcessorout::" + se.getMessage());
        } catch (IOException e) {
            System.err.println("TrfProcessorout: Accept failed.");
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }//end of while true
    }//end of process request of IN
        /*
     launchTrafficPktLoss():
     launch the listening Dgms socket which listen on portTrf
     start receiving paquets and computes paquet loss
     sending paquets
     */

    public void launchTrafficOut(Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {
//run the thread that sends the traffic
        int portsrcOutChannel = Integer.valueOf(param.getPortrfClientD());
        int portdestOutChannel = Integer.valueOf(param.getPortrfClientD());
        TrfDgmRunnableOut trfDgmRunnableOut = new TrfDgmRunnableOut(param, inetaddressDest, portsrcOutChannel, portdestOutChannel);
        Thread trfDgmThreadOut = new Thread(trfDgmRunnableOut);
        trfDgmThreadOut.start();
        System.out.println("TrfProcessorout:launchTrafficOut waiting to finish the TrfDgmRunnableOut thread");
        trfDgmThreadOut.join();
    }
}
