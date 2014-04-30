/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.Ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author salim
 */
public class Networking {


    /*
    Desc: it will get the first valid IP address, whether it's a SiteLocal Ip or other type of IP
    */
public static String getLocalIpAddress() throws SocketException{
    IPAddressValidator validator = new IPAddressValidator();
            String localIp = "127.0.0.1";
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
    }
}
