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
public class TrfProcessorIn {

    ServerSocket serverSocket = null;
    String trfkey;

    public TrfProcessorIn(Integer port, String trfkey) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TrfProcessorIn: listening on port " + port);
            this.trfkey = trfkey;
        } catch (IOException e) {
            System.err.println("TrfProcessorIn: Could not listen on port:" + port);
            System.exit(1);
        }
    }


    public void processTest(Param param) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        System.out.println("TrfProcessorIn: starts..\n waiting for connections trf key=" + trfkey);
        try {
            // a "blocking" call which waits until a connection is requested
            System.out.println("TrfProcessorIn:waiting for accept..");
            Socket clientSocket = serverSocket.accept();
            System.out.println("TrfProcessorIn:connection accepted");
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            boolean firstLine = true;
            System.out.println("TrfProcessorIn:[port=" + this.serverSocket.getLocalPort() + "] Connection successful\n");
            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    if (inputLine.contains(trfkey)) {
                        System.out.println("TrfProcessorIn:receiving:" + inputLine);
                        System.out.println("TrfProcessorIn:receiving: Accepted, sending back the " + ACK+".Starting the trafficIn");
                        out.write(ACK);
                        InetAddress inetaddressDest = clientSocket.getInetAddress();
                        TrfBo.closeRess(clientSocket, out, in);
                        try {
                            // launch the traffIn thread
                            launchTrafficPktLossIn(param, inetaddressDest);
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(TrfProcessorIn.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TrfProcessorIn.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                firstLine = false;
            }
        } catch (IOException e) {
            System.err.println("TrfProcessorIn: Accept failed.");
            System.exit(1);
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

    public void launchTrafficPktLossIn(Param param, InetAddress inetaddressDest) throws UnknownHostException, IOException, InterruptedException {
      
        //run the thread that sends the traffic
        int portsrcInChannel = Integer.valueOf(param.getPortrfClientU());
        int portdestInChannel = Integer.valueOf(param.getPortrfClientU());
        //run the thread that receives the traffic and computes the pktloss up
        TrfDgmRunnableIn trfDgmRunnableIn = new TrfDgmRunnableIn(param, inetaddressDest, portsrcInChannel, portdestInChannel);
        Thread trfDgmThreadIn = new Thread(trfDgmRunnableIn);
        trfDgmThreadIn.start();
    }
    
 
}
