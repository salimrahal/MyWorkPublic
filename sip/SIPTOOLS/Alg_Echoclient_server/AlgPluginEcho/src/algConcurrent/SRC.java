/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algConcurrent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.concurrent.Callable;

/**
 *
 * @author salim this task handle the tcp send and received message, it should
 * be surrounded with timeout exception in case the ReadLine blocks
 */
public class SRC implements Callable<String> {

    String msgToSend;
    BufferedReader in;
    PrintWriter out;

    public SRC(String msgToSend, BufferedReader in, PrintWriter out) {
        this.msgToSend = msgToSend;
        this.in = in;
        this.out = out;
    }

    @Override
    public String call() throws Exception {
        System.out.println("call:: the task begins");
        StringReader msgreader = new StringReader(msgToSend);
        BufferedReader msgbr = new BufferedReader(msgreader);
        String msgRecv;
        String submsgToSend;
        StringBuilder strbuilder = new StringBuilder();
        boolean firstLine = true;
        while ((submsgToSend = msgbr.readLine()) != null) {
            //write to the server
            out.println(submsgToSend);
            //recieve from the server,
            //in some case it will freeze here nothing is received, so a timeout will be triggered
            msgRecv = in.readLine();
            if (firstLine) {
                System.out.println("echo: " + msgRecv);
                firstLine = false;
            }
            strbuilder.append(msgRecv).append("\r\n");
        }
        return strbuilder.toString();
    }

}
