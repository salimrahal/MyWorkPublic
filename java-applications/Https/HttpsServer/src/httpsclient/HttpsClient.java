/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpsclient;

/**
 * HttpsClient.java Copyright (c) 2005 by Dr. Herong Yang
 * 
 * Of course, we have to run HttpsClient.java with the server's certificate in a trusted key store file:
 * >\jdk\bin\java -cp . "-Djavax.net.ssl.trustStore=public.jks" 
   HttpsClient

The default SSL socket factory class: 
   class com.sun.net.ssl.internal.ssl.SSLSocketFactoryImpl
Socket class: class com.sun.net.ssl.internal.ssl.SSLSocketImpl
   Remote address = localhost/127.0.0.1
   Remote port = 8888
   Local socket address = /127.0.0.1:2408
   Local address = /127.0.0.1
   Local port = 2408
   Need client authentication = false
   Cipher suite = TLS_DHE_DSS_WITH_AES_128_CBC_SHA
   Protocol = TLSv1
HTTP/1.0 200 OK
Content-Type: text/html

<html><body>Hello world!</body></html>
 */
import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class HttpsClient {

    public static void main(String[] args) {
        PrintStream out = System.out;

        // Getting the default SSL socket factory
        SSLSocketFactory f
                = (SSLSocketFactory) SSLSocketFactory.getDefault();
        out.println("The default SSL socket factory class: "
                + f.getClass());
        try {
            // Getting the default SSL socket factory
            SSLSocket c
                    = (SSLSocket) f.createSocket("localhost", 8888);
            printSocketInfo(c);
            c.startHandshake();
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
                    c.getOutputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            w.write("GET / HTTP/1.0");
            w.newLine();
            w.newLine(); // end of HTTP request
            w.flush();
            String m = null;
            while ((m = r.readLine()) != null) {
                out.println(m);
            }
            w.close();
            r.close();
            c.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private static void printSocketInfo(SSLSocket s) {
        System.out.println("Socket class: " + s.getClass());
        System.out.println("   Remote address = "
                + s.getInetAddress().toString());
        System.out.println("   Remote port = " + s.getPort());
        System.out.println("   Local socket address = "
                + s.getLocalSocketAddress().toString());
        System.out.println("   Local address = "
                + s.getLocalAddress().toString());
        System.out.println("   Local port = " + s.getLocalPort());
        System.out.println("   Need client authentication = "
                + s.getNeedClientAuth());
        SSLSession ss = s.getSession();
        System.out.println("   Cipher suite = " + ss.getCipherSuite());
        System.out.println("   Protocol = " + ss.getProtocol());
    }
}
