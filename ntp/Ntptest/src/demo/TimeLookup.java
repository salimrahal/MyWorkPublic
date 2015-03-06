/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.NtpV3Packet;
import org.apache.commons.net.ntp.TimeInfo;

/**
 *
 * @author salim
 */
public class TimeLookup {

    // List of time servers: http://tf.nist.gov/tf-cgi/servers.cgi
    // Do not query time server more than once every 4 seconds
    public static final String TIME_SERVER = "rps.yealink.com";//"nist1-pa.ustiming.org";

    public static void main(String[] args) throws Exception {
        String timeServer = "rps.yealink.com";//no reply
        timeServer = "fm.grandstream.com";//no reply
        timeServer = "firmware.grandstream.com";//no reply
        timeServer = "mians01.nexogy.net";//no reply
        timeServer = "nist1-pa.ustiming.org";//OKI
        timeServer = "miandp.nexogy.net";//no reply
        timeServer = "ntp1.glb.nist.gov";//used by yeaLink
        timeServer = "time.nist.gov";
        queryTimeServer(timeServer);
    }

    public static void queryTimeServer(String timeServer) throws UnknownHostException, IOException {
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(timeServer);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);
        NtpV3Packet message = timeInfo.getMessage();
        long serverTime = message.getTransmitTimeStamp().getTime();
        Date time = new Date(serverTime);
        System.out.println("Time from " + timeServer + ": " + time);
    }
}
