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
import java.net.Socket;

/**
 *
 * @author salim
 */
public class ClientTcp {
    /*
     TODO: 
     - get the parameters from the UI: port, codec, timelength, customer name
     - connect to the TCP server 
     - send the above param to the server
     */

    public static void sendParamToServer(String port, String codec, String timelength, String custname) throws IOException {
        Socket socket = null;
        BufferedReader in;
        PrintWriter out;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader( 
                socket.getInputStream()));
        
    }
}
