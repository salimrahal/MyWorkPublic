package network.urls;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressTest {

    /**
     * Desc: this example shows how to get the InetAddress object from the host
     * name or IP usung the getByName(String host) function
     *
     * @param args
     */
    public static void main(String[] args) throws UnknownHostException {
        getIpAdress();
    }

    public static void getIpAddressMeth1() {
        // TODO Auto-generateetIpAdress()d method stub
        NetworkInterface nif = null;
        try {
            nif = NetworkInterface.getByName("wlan0");
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Enumeration<InetAddress> nifAddresses = nif.getInetAddresses();
        while (nifAddresses.hasMoreElements()) {
            try {
                nifAddresses.nextElement();
                System.out.println(InetAddress.getByName("141.138.190.28"));
            } catch (UnknownHostException e) {
                // TODO FAuto-generated catch block
                e.printStackTrace();
            }
        }



    }
/*
 * returns: IP of my system is := 127.0.1.1
 */
    public static void getIpAdress() throws UnknownHostException {
        InetAddress IP=InetAddress.getLocalHost();
System.out.println("IP of my system is := "+IP.getHostAddress());
    }
}
