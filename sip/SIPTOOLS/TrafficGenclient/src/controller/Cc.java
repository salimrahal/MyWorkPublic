/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bo.ClientTcp;
import bo.Networking;
import bo.TrfGenBo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author salim
 */
public class Cc {
    
     public static void sendParamToServer(String port, String codec, String timelength, String custname) throws IOException{
         ClientTcp.sendParamToServer(port, codec, timelength, custname);
    }
}
