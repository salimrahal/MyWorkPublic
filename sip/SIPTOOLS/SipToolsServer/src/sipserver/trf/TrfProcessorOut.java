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
            System.exit(1);
        }
    }

    public void processTest(Param param) throws IOException {
        PrintWriter out ;
        BufferedReader in ;
        System.out.println("TrfProcessorout: starts..\n waiting for connections trf key=" + trfkey);
        try {
            // a "blocking" call which waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            boolean firstLine = true;
            System.out.println("TrfProcessorout:[port=" + this.serverSocket.getLocalPort() + "] Connection successful\n");
            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                     System.out.println("TrfProcessorout:receiving:" + inputLine);
                    if (inputLine.contains(trfkey)) {
                        System.out.println("TrfProcessorout: Accepted, sending back the " + ACK+".Starting the trafficOut");
                        out.write(ACK);
                        try {
                            // launch the traffIn thread
                            launchTrafficOut(param, clientSocket);
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
        } catch (IOException e) {
            System.err.println("TrfProcessorout: Accept failed.");
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
      public void launchTrafficOut(Param param, Socket clientSocket) throws UnknownHostException, IOException, InterruptedException {
        InetAddress inetaddressDest = clientSocket.getInetAddress();
//run the thread that sends the traffic
        int portsrcOutChannel = Integer.valueOf(param.getPortrfClientD());
        int portdestOutChannel = Integer.valueOf(param.getPortrfClientD());
        TrfDgmRunnableOut trfDgmRunnableOut = new TrfDgmRunnableOut(param, inetaddressDest, portsrcOutChannel, portdestOutChannel);
        Thread trfDgmThreadOut = new Thread(trfDgmRunnableOut);
        trfDgmThreadOut.start();
    }
}
