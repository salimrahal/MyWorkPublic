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
import java.net.*;
import java.io.*;

public class EchoServer2 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        String iplocal = "192.168.0.105";
        try {

//server not binded to a specific Ip            
//serverSocket = new ServerSocket(10007);
            //binding the server to a specific Ip
            InetAddress inetAddrSrc = InetAddress.getByName(iplocal);
            serverSocket = new ServerSocket(10007, 50, inetAddrSrc);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10007.");
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("server listening on: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort() + "Waiting for connection.....");

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println("Connection successful");
        System.out.println("Waiting for input.....");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Server: [" + iplocal + "] " + inputLine);
            out.println(inputLine);

            if (inputLine.equals("Bye.")) {
                break;
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
