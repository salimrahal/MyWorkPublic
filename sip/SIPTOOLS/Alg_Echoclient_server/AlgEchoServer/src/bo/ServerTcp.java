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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author salim
 */
public class ServerTcp {

    ServerSocket serverSocket = null;
    InetAddress address;
    String registerKey = "REGISTER";
    String inviteKey = "INVITE";
    Integer poolsize = 50;// * Runtime.getRuntime().availableProcessors();

    //insert the constructor
    public ServerTcp(Integer port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("ServerTcp: Could not listen on port:" + port);
            System.exit(1);
        }
    }

    public void processrequests() {
        int i = 0;
        String subStr;
        ExecutorService poolservice = Executors.newFixedThreadPool(poolsize);
        System.out.println("Sip ServerTcp starts..");
        System.out.println("Sip ServerTcp: waiting for connections");
        while (true) {
            Socket clientSocket = null;
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
                System.out.println("Server: " + inputLine);
                out.println(inputLine);

                if (inputLine.equals("Bye.")) {
                    break;
                }

                if (subStr.contains(registerKey) || subStr.contains(inviteKey)) {

                    //create the thread(Runnable) that sends the message
                    ClientTcpConnection clientcpConn = new ClientTcpConnection(socket, recvMsg, addressInco, portInco, i);
                    //and this task to a pool, so clientConnection thread will be started
                    poolservice.execute(clientcpConn);
                    i++;
                } else {
                    System.out.println("Sip DatagramServer:unknown client, disregard the packet");
                }

            }
        }
    }
