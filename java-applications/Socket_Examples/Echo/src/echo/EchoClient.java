/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo;

/**
 *
 * @author salim
 */
import java.io.*;
import java.net.*;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        //String serverHostname = new String ("127.0.0.1");
        String serverHostname = new String("127.0.0.1");

        if (args.length > 0) {
            serverHostname = args[0];
        }
        System.out.println("Attemping to connect to host "
                + serverHostname + " on port 10007.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket(serverHostname, 10007);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;
        out.println("sfasfasfasfasfasffasffasfasf"
                + "asf"
                + "asfasf"
                + "as"
                + "fas"
                + "f"
                + "as"
                + "fasfasfasfsafffffffffffffffffffffffff");
        System.out.println("echo: " + in.readLine());
//        System.out.print ("input: ");
//	while ((userInput = stdIn.readLine()) != null) {
//	    out.println(userInput);
//	    System.out.println("echo: " + in.readLine());
//            System.out.print ("input: ");
//	}

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
