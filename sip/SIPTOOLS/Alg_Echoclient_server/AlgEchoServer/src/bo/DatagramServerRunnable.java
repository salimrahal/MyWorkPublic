package bo;

import test.*;
import java.io.*;
import java.net.*;
import java.util.*;
import network.Networking;

public class DatagramServerRunnable implements Runnable {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreRequests = true;

    public DatagramServerRunnable(String localIp, Integer port) throws IOException {
        //super(name);
        InetAddress address = InetAddress.getByName(localIp);
        System.out.println("Sip DatagramServer: listening on port " + port + "/ Ip " + localIp);
        socket = new DatagramSocket(port, address);
    }

    @Override
    public void run() {
        int i = 0;
        System.out.println("Sip DatagramServer: run begins");
        //buffer to receive incoming data
        byte[] buf = new byte[1024];//65536
        try {
            DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);

            System.out.println("Sip DatagramServer: waiting to receive packets");
            //communication loop
            while (true) {
                // receive request    
                socket.receive(incomingPacket);

                String recvMsg = new String(incomingPacket.getData(), 0, incomingPacket.getLength());
                System.out.println("received packet Num:" + i + "\n" + recvMsg);
                buf = recvMsg.getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                incomingPacket = new DatagramPacket(buf, buf.length, address, port);
                socket.send(incomingPacket);
                System.out.println("sent packet: Num:" + i + "\n" + recvMsg);
                i++;
            }//end of while
        }//end try
        catch (IOException e) {
            System.out.println("Error:" + e.getLocalizedMessage());
            moreRequests = false;
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
        //socket.close();
        //System.out.println("run ends");
    }

//    protected String getNextQuote() {
//        String returnValue = null;
//        try {
//            if ((returnValue = in.readLine()) == null) {
//                in.close();
//		moreQuotes = false;
//                returnValue = "No more quotes. Goodbye.";
//            }
//        } catch (IOException e) {
//            returnValue = "IOException occurred in server.";
//        }
//        return returnValue;
//    }
}
