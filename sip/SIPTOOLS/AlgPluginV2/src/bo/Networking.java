/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 * @author salim
 */
public class Networking {

    public static String getIpAddressMeth1() throws SocketException, UnknownHostException {
        // TODO Auto-generateetIpAdress()d method stub
        NetworkInterface nif = null;     
            nif = NetworkInterface.getByName("wlan0");
   
        Enumeration<InetAddress> nifAddresses = nif.getInetAddresses();
        String addrs = "";
        while (nifAddresses.hasMoreElements()) {   
                nifAddresses.nextElement();
                System.out.println(InetAddress.getByName("ss-host"));
                addrs += InetAddress.getByName("ss-host");           
        }
          System.out.println(addrs);
        return addrs;
    }
public static String test(){
    return "hiii";
}
    public static void main(String[] args) throws UnknownHostException, SocketException {
        getIpAddressMeth1();
    }
}
