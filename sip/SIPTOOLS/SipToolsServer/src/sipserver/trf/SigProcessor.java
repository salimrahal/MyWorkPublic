/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipserver.trf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static sipserver.trf.TrfBo.TST_ID_KEY;
import sipserver.trf.bean.Param;

/**
 *
 * @author salim
 */
public class SigProcessor implements Runnable {

    //ServerSocket serverSocket = null;
    DatagramSocket socket = null;
    InetAddress address;
    TrfBo trbo;
    String testIdPrevious = null;
  

    public SigProcessor(String localIp, Integer port) {
        try {
            trbo = new TrfBo();
            address = InetAddress.getByName(localIp);
            socket = new DatagramSocket(port, address);
            System.out.println("Traffic UDPServer: listening on port " + port + "/ Ip " + localIp + " / use a cached pool");
            System.out.println("[" + new Date() + "] Traffic UDPServer: listening on port " + port + " /using cachedThreadPoo");
        } catch (IOException e) {
            System.err.println("Traffic UDPServer:: Could not listen on port:" + port);
            System.exit(1);
        }
    }

    public void processrequests() throws IOException {
        int i = 0;
        ExecutorService poolservice = Executors.newCachedThreadPool();
        System.out.println("Traffic UDPServer: starts..");
        //buffer to receive incoming data
        byte[] buf = new byte[1024];//65536
        try {

            System.out.println("Traffic UDPServer: waiting to receive packets");
            //communication loop
            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
                socket.receive(incomingPacket);
                //DatagramPacket incomingPacketTmp = incomingPacket;
                String recvMsg = new String(incomingPacket.getData(), 0, incomingPacket.getLength());

                System.out.println("Traffic UDPServer: [" + new Date() + "]\n received packet clientID:" + i + "\n" + recvMsg);
                InetAddress addressInco = incomingPacket.getAddress();
                int portInco = incomingPacket.getPort();
                //if (recvMsg.contains(TST_ID_KEY)) {
                 //    Param param = trbo.savingParamsTobean(recvMsg, addressInco.getHostAddress());
                 //   if (param.getTstid() != null && !param.getTstid().isEmpty()) {
                   //     if (testIdPrevious == null) {
                    //        System.out.println("Traffic UDPServer: [" + new Date() + "]\n testIdPrevious is null store the new value= " + param.getTstid());
              //        testIdPrevious = param.getTstid();
                   //     }
                   //     if (testIdPrevious.equalsIgnoreCase(param.getTstid()) && i > 0) {
                    //        System.out.println("Traffic UDPServer: [" + new Date() + "]\n the received testId is equal to testId-Previous [received twice]  . value= " + param.getTstid() + "/don't create a new thread");
                    //    } else {
                       //     System.out.println("Traffic UDPServer: [" + new Date() + "]\n a new test id is received="+param.getTstid());
                            //create the thread(Runnable) that sends the message                  
                            ClientSignUdpConnection clientConn = new ClientSignUdpConnection(socket, recvMsg, addressInco, portInco, i);
                           
                            poolservice.execute(clientConn);
                            i++;
                       // }
                    //}
              // }
                            }//end of while
        }//end try
        catch (IOException e) {
            System.out.println("Traffic UDPServer:Error:" + e.getLocalizedMessage());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }//end of process requests

    @Override
    public void run() {
        try {
            processrequests();
        } catch (IOException ex) {
            System.out.println("Traffic UDPServer: Error: couldn't be run:" + ex.getLocalizedMessage());
        }
    }
}
