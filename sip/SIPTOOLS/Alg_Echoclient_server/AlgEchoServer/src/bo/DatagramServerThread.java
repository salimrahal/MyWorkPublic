
package bo;
import test.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class DatagramServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreRequests = true;

    public DatagramServerThread(Integer port) throws IOException {
	this("DatagramServerThread", port);
    }

    public DatagramServerThread(String name,Integer port) throws IOException {
        super(name);
        socket = new DatagramSocket(port);
    }

    public void run() {
        System.out.println("run begins");
        while (moreRequests) {
            try {
                byte[] buf = new byte[1024];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String recvMsg = new String(packet.getData(), 0, packet.getLength());
                  System.out.println("recvMsg:"+recvMsg);
                buf = recvMsg.getBytes();
                
		// send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
		moreRequests = false;
            }
        }
        socket.close();
        System.out.println("run ends");
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
