/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author salim
 */
public class Networking {

public static final String LOOPBACK = "127.0.0.1";//127.0.1.1
    /*
    Desc: it will get the first valid IP address, whether it's a SiteLocal Ip or other type of IP
    */
public static String getLocalIpAddress() throws SocketException{
    IPAddressValidator validator = new IPAddressValidator();
            String localIp = LOOPBACK;
            //getting all network interfaces
            for(Enumeration enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();)
            { 
                //System.out.println("NetworkInterface ["+enumeration.toString()+"]");
                NetworkInterface networkinterface = (NetworkInterface)enumeration.nextElement();
                Enumeration enumeration1 = networkinterface.getInetAddresses();
                while(enumeration1.hasMoreElements()) 
                {
                    InetAddress inetaddress = (InetAddress)enumeration1.nextElement();
                   // System.out.println("inetaddress["+inetaddress.toString()+"]-->is SiteLocalAddress["+inetaddress.isSiteLocalAddress()+"]");
                   // System.out.println("\nisAnyLocalAddress["+ inetaddress.isAnyLocalAddress()+"]");
                   // System.out.println("\nisLinkLocalAddress["+ inetaddress.isLinkLocalAddress()+"]");
                    if(!inetaddress.isLoopbackAddress()){
                        //check if it's a valid IP
                        if(validator.validate(inetaddress.getHostAddress())){
                            localIp = inetaddress.getHostAddress();
                            System.out.println("getLocalIpAddress choosen: "+localIp);
                        } 
                    }
                }
            }
            //localIp = "192.168.5.106";
           //System.out.println("localIp="+localIp);
            return localIp;
}
    public static void main(String[] args) throws UnknownHostException, SocketException {
       getLocalIpAddress();
       String s ="66d2b2fc-6f54-49cf-a495-e158ebb814c7";
        System.out.println(s.getBytes().length);//36 bytes
    }
     public static String getmyPIP() throws IOException {
        String remoteip = null;
        String[] urlArr = new String[]{"http://checkip.amazonaws.com/", "http://www.trackip.net/ip", "http://curlmyip.com/", "http://icanhazip.com/"};
        boolean needPIP = true;
        int i = 0;
        while (needPIP && i < urlArr.length) {
            try {
                URL connection = new URL(urlArr[i]);
                i++;
                URLConnection con = connection.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                remoteip = reader.readLine();
            } catch (IOException iOException) {
            } finally {
                if (remoteip != null) {
                    System.out.println("remoteip="+remoteip);
                        needPIP = false;
                }
            }
        }//end while
        return remoteip;
    }
}
