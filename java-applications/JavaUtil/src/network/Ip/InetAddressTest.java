package network.Ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws UnknownHostException, SocketException {
        getLocalIpAddress();
    }
    /*
    getLocalIpAddress: It loop thru the Network Interfaces and get the 
    */
public static String getLocalIpAddress() throws SocketException{
            String localIp = "127.0.0.1";
            //getting all network interfaces
            for(Enumeration enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();)
            { 
                System.out.println("NetworkInterface ["+enumeration.toString()+"]");
                NetworkInterface networkinterface = (NetworkInterface)enumeration.nextElement();
                Enumeration enumeration1 = networkinterface.getInetAddresses();
                while(enumeration1.hasMoreElements()) 
                {
                    InetAddress inetaddress = (InetAddress)enumeration1.nextElement();
                    System.out.println("inetaddress["+inetaddress.toString()+"]-->is SiteLocalAddress["+inetaddress.isSiteLocalAddress()+"]");
                    System.out.println("\nisAnyLocalAddress["+ inetaddress.isAnyLocalAddress()+"]");
                    System.out.println("\nisLinkLocalAddress["+ inetaddress.isLinkLocalAddress()+"]");
                    //is siteLocal = 192.168.xxx.xxx add the IP regex
                    if(!inetaddress.isLoopbackAddress()&& true){
                     localIp = inetaddress.getHostAddress();
                     System.out.println("choosen: "+localIp);
                    }
                       
     
                }
            }
           System.out.println("localIp="+localIp);
            return localIp;
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
                System.out.println(InetAddress.getAllByName("ss-host"));
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
