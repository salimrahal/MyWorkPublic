/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algplugindg;

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   sipalgdetect.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class sipalg extends Applet
    implements ActionListener
{
 //my server
String serverIp = "209.208.79.151";
    public sipalg()
    {
        introStr = "SIP ALG Detector";
        detect = new Button("Detect");
        clear = new Button("Clear");
        found = "SIP ALG DETECTED";
        notfound = "No SIP ALG";
        detected = false;
        sendarea = new JTextArea("Sent Data Log...");
        receivearea = new JTextArea("Receive Data Log...");
        algstatus = new JLabel("Click Detect To Start");
        algstatus_title = new TitledBorder("ALG Status");
        sendareascroll = new JScrollPane(sendarea);
        receiveareascroll = new JScrollPane(receivearea);
        newline = new String("\n");
        srcport = new JTextField("5060", 5);
        srcportlbl = new JLabel("Src Port:");
        dstportlbl = new JLabel("Dst Port:");
        maxrangelbl = new JLabel("[1024-65535]");
    }

    public void algDetect()
    {
        DatagramSocket datagramsocket = null;
        String s = srcport.getText();
        int i = 0;
        boolean flag = false;
        String s1 = new String();
        boolean flag1 = false;
        try
        {
            i = Integer.parseInt(s.trim());
        }
        catch(NumberFormatException numberformatexception)
        {
            sendarea.append((new StringBuilder()).append(newline).append("[INVALID PORT - Defaulting to 5060]").toString());
            receivearea.append((new StringBuilder()).append(newline).append("[INVALID PORT - Defaulting to 5060]").toString());
            i = 5060;
        }
        try
        {
            datagramsocket = new DatagramSocket(i);
            Object obj = null;
            String s3 = "127.0.0.1";
            for(Enumeration enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();)
            {
                NetworkInterface networkinterface = (NetworkInterface)enumeration.nextElement();
                Object obj1 = null;
                Enumeration enumeration1 = networkinterface.getInetAddresses();
                while(enumeration1.hasMoreElements()) 
                {
                    InetAddress inetaddress = (InetAddress)enumeration1.nextElement();
                    if(!inetaddress.isLoopbackAddress() && inetaddress.isSiteLocalAddress())
                        s3 = inetaddress.getHostAddress();
                }
            }
            System.out.println("s3="+s3);
            char c;
            if(dstRadio5060.getState())
                c = '\u13C4';
            else
                c = '\u13C6';
            String s4 = "";
            System.out.println("c="+c);
            if(radio1.getState())
            {
              s4 = (new StringBuilder()).append("REGISTER sip:").append(serverIp).append(":")
                      .append(c).append(" SIP/2.0\r\nVia: SIP/2.0/UDP ").append(s3).append(":")
                      .append(i).append(";branch=z9hG4bK-7d0f94c9\r\nFrom: \"SIP_ALG_DETECTOR\" <sip:18009834289@")
                      .append(serverIp).append(":").append(">;tag=1b38e99fe68ccce9o0\r\nTo: \"SIP_ALG_DETECTOR\" <sip:18009834289@")
                      .append(serverIp).append(":").append(c).
                      append(">\r\nCall-ID: 11256979-ca11b60c@").append(s3).
                      append("\r\nCSeq: 7990 REGISTER\r\nMax-Forwards: 70\r\nContact: \"SIP_ALG_DETECTOR\" <sip:18009834289@").
                      append(s3).append(":").
                      append(i).append(">;expires=60\r\nUser-Agent: Cisco/SPA303-7.4.7\r\nContent-Length: 0\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE\r\nSupported: replaces\r\n\r\n").toString();
             //s4 = (new StringBuilder()).append("REGISTER sip:209.208.79.151:5060").append(" SIP/2.0\r\nVia: SIP/2.0/UDP ").append(s3).append(":").append(i).append(";branch=z9hG4bK-7d0f94c9\r\nFrom: \"SIP_ALG_DETECTOR\" <sip:18009834289@209.208.79.151>;tag=1b38e99fe68ccce9o0\r\nTo: \"SIP_ALG_DETECTOR\" <sip:18009834289@209.208.79.151:").append(c).append(">\r\nCall-ID: 11256979-ca11b60c@").append(s3).append("\r\nCSeq: 7990 REGISTER\r\nMax-Forwards: 70\r\nContact: \"SIP_ALG_DETECTOR\" <sip:18009834289@").append(s3).append(":").append(i).append(">;expires=60\r\nUser-Agent: Cisco/SPA303-7.4.7\r\nContent-Length: 0\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE\r\nSupported: replaces\r\n\r\n").toString();
            } else
            {
                String s2 = (new StringBuilder()).append("v=0\r\no=- 276061282 276061282 IN IP4 ").append(s3).append("\r\ns=-\r\nc=IN IP4 ").
                        append(s3).append("\r\nt=0 0\r\nm=audio 16482 RTP/AVP 0 8 18 100 101\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:18 G729a/8000\r\na=rtpmap:100 NSE/8000\r\na=fmtp:100 192-193\r\na=rtpmap:101 telephone-event/8000\r\na=fmtp:101 0-15\r\na=ptime:30\r\na=sendrecv\r\n").toString();
                int j = s2.getBytes().length;
                s4 = (new StringBuilder()).append("INVITE sip:18009834289@").append(serverIp).append(":").append(" SIP/2.0\r\nVia: SIP/2.0/UDP ").
                        append(s3).append(":").append(i).append(";branch=z9hG4bK-467cc605\r\nFrom: SIP_ALG_DETECTOR <sip:18009834289@").
                        append(serverIp).append(">;tag=8e059c0484ff02ado0\r\nTo: <sip:18009834289@").append(serverIp).append(":").append(c).
                        append(">\r\nCall-ID: ce8cc10-3400b211@").append(s3).
                        append("\r\nCSeq: 101 INVITE\r\nMax-Forwards: 70\r\nContact: SIP_ALG_DETECTOR <sip:18009834289@").append(s3).append(":").append(i).
                        append(">\r\nExpires: 60\r\nUser-Agent: SIP_ALG_DETECTOR\r\nContent-Length: ").
                        append(j).append("\r\nAllow: ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER\r\nSupported: replaces\r\nContent-Type: application/sdp\r\n\r\nv=0\r\no=- 276061282 276061282 IN IP4 ").
                        append(s3).append("\r\ns=-\r\nc=IN IP4 ").append(s3).append("\r\nt=0 0\r\nm=audio 16482 RTP/AVP 0 8 18 100 101\r\na=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\na=rtpmap:18 G729a/8000\r\na=rtpmap:100 NSE/8000\r\na=fmtp:100 192-193\r\na=rtpmap:101 telephone-event/8000\r\na=fmtp:101 0-15\r\na=ptime:30\r\na=sendrecv\r\n").toString();
            }
            byte abyte0[] = s4.getBytes();
            //the destination/server IP in Byte --> 208.73.148.19
            byte abyte1[] = {
                -48, 73, -108, 19
            };
            InetAddress inetaddress1 = null;
            //inetaddress1 = InetAddress.getByAddress(abyte1);
            inetaddress1 = InetAddress.getByName(serverIp);
            //construct a packet that recieve data on the destination Addr and port specified by the constructor
            System.out.println("inetaddress1="+inetaddress1.getHostAddress());
            DatagramPacket datagrampacket = new DatagramPacket(abyte0, abyte0.length, inetaddress1, c);
            datagramsocket.send(datagrampacket);
            int k = datagramsocket.getLocalPort();
            sendarea.append((new StringBuilder()).append(newline).append("[New Packet Sent]").append(newline).append(s4).toString());
            byte abyte2[] = new byte[1024];
            for(byte byte0 = 5; byte0 > 2; byte0 = 1)
            {
                datagramsocket.setSoTimeout(7000);
                //Constructs a DatagramPacket for receiving packets of length length.
                DatagramPacket datagrampacket1 = new DatagramPacket(abyte2, abyte2.length);
                datagramsocket.receive(datagrampacket1);
                String s5 = new String(datagrampacket1.getData(), 0, datagrampacket1.getLength());
                byte abyte3[] = s5.getBytes();
                if(s5.equals(s4))
                {
                    algstatus.setForeground(Color.black);
                    algstatus.setText(notfound);
                } else
                {
                    algstatus.setForeground(Color.red);
                    algstatus.setText(found);
                }
                receivearea.append((new StringBuilder()).append(newline).append("[New Packet Received]").append(newline).append(s5).toString());
                datagramsocket.close();
            }

        }
        catch(SocketTimeoutException sockettimeoutexception)
        {
            receivearea.append((new StringBuilder()).append(newline).append("[No Packet Received - SIP ALG / Firewall issue]").toString());
            algstatus.setForeground(Color.red);
            algstatus.setText("SIP ALG/Firewall Problem Found");
            datagramsocket.close();
        }
        catch(Exception exception)
        {
            algstatus.setForeground(Color.red);
            algstatus.setText("Unknown Socket Error");
            datagramsocket.close();
        }
    }

    public void init()
    {
        setLayout(null);
        radioGroup = new CheckboxGroup();
        radio1 = new Checkbox("REGISTER", radioGroup, true);
        radio2 = new Checkbox("INVITE", radioGroup, false);
        radioGroupDstPort = new CheckboxGroup();
        dstRadio5060 = new Checkbox("5060", radioGroupDstPort, true);
        dstRadio5062 = new Checkbox("5062", radioGroupDstPort, false);
        radio1.setBounds(50, 175, 100, 20);
        radio2.setBounds(200, 175, 75, 20);
        add(radio1);
        add(radio2);
        srcport.setBounds(75, 203, 50, 20);
        srcportlbl.setBounds(5, 200, 65, 25);
        maxrangelbl.setBounds(140, 200, 100, 25);
        dstportlbl.setBounds(5, 230, 65, 25);
        add(srcport);
        add(maxrangelbl);
        add(srcportlbl);
        add(dstportlbl);
        dstRadio5060.setBounds(70, 230, 50, 25);
        dstRadio5062.setBounds(130, 230, 50, 25);
        add(dstRadio5060);
        add(dstRadio5062);
        sendareascroll.setBounds(300, 10, 495, 185);
        add(sendareascroll);
        receiveareascroll.setBounds(300, 205, 495, 190);
        add(receiveareascroll);
        sendarea.setEditable(false);
        sendarea.setBorder(BorderFactory.createEtchedBorder(1));
        receivearea.setEditable(false);
        receivearea.setBorder(BorderFactory.createEtchedBorder(1));
        detect.setBounds(10, 280, 280, 40);
        add(detect);
        clear.setBounds(50, 340, 200, 40);
        add(clear);
        algstatus.setHorizontalAlignment(0);
        algstatus.setBounds(5, 5, 290, 150);
        algstatus.setBorder(algstatus_title);
        add(algstatus);
    }

    public void start()
    {
        detect.addActionListener(this);
        clear.addActionListener(this);
    }

    public void stop()
    {
        detect.addActionListener(null);
        clear.addActionListener(null);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == detect)
            algDetect();
        if(actionevent.getSource() == clear)
        {
            algstatus.setForeground(Color.black);
            sendarea.setText("Sent Data Log...");
            receivearea.setText("Receive Data Log...");
            algstatus.setText("Click Detect To Start");
        }
    }

    String introStr;
    Button detect;
    Button clear;
    String found;
    String notfound;
    boolean detected;
    JTextArea sendarea;
    JTextArea receivearea;
    JLabel algstatus;
    TitledBorder algstatus_title;
    JScrollPane sendareascroll;
    JScrollPane receiveareascroll;
    CheckboxGroup radioGroup;
    Checkbox radio1;
    Checkbox radio2;
    String newline;
    JTextField srcport;
    JLabel srcportlbl;
    JLabel dstportlbl;
    JLabel maxrangelbl;
    CheckboxGroup radioGroupDstPort;
    Checkbox dstRadio5060;
    Checkbox dstRadio5062;
}
