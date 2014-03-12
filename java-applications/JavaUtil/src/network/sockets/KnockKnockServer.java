package network.sockets;

import java.net.*;
import java.io.*;
 /**
  * file:///home/salim/Documents/Java_F/tutorial_JavaSE_7/networking/sockets/clientServer.html
  * @author salim
  *
  */
public class KnockKnockServer {
    public static void main(String[] args) throws IOException {
 
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }
 
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        //Accepting multiple client - Basic logic
      /**The thread reads from and writes to the client connection as necessary.
        while (true) {
            accept a connection;
            create a thread to deal with the client;
        }
        */
 
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
        String inputLine, outputLine;
        KnockKnockProtocol kkp = new KnockKnockProtocol();
 
        outputLine = kkp.processInput(null);
        out.println(outputLine);
 
        while ((inputLine = in.readLine()) != null) {
             outputLine = kkp.processInput(inputLine);
             out.println(outputLine);
            // outputLine="Bye.";
             if (outputLine.equals("Bye."))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
