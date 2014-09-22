/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network.urls;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.naming.NamingException;

/**
 *
 * @author salim
 */
public class DnsLookup {
    public static void main(String args[])
    {
        String host = "stackoverflow.com";
        try
        {
            InetAddress inetAddress = InetAddress.getByName(host);
            // show the Internet Address as name/address
            System.out.println(inetAddress.getHostName() + " " + inetAddress.getHostAddress());
        }
        catch (UnknownHostException exception)
        {
            System.err.println("ERROR: Cannot access '" + host + "'");
        }
    }
}
