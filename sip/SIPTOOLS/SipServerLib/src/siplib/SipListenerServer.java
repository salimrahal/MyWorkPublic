/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siplib;

import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.ListeningPoint;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipFactory;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.header.ContactHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

/**
 *
 * @author salim
 */
public class SipListenerServer implements SipListener {

    private SipFactory sipFactory;
    private SipStack sipStack;
    private SipProvider sipProvider1;
    private SipProvider sipProvider2;
    private MessageFactory messageFactory;
    private HeaderFactory headerFactory;
    private AddressFactory addressFactory;
    private ListeningPoint listeningPointudp;
    private ListeningPoint listeningPointtcp;
    private Properties properties;

    private String ip;
    private int port1 = 5060;
    //private int port2 = 5062;
    private String transportudp = "udp";
    private String transporttcp = "tcp";
    private int tag = (new Random()).nextInt();
    private Address contactAddress;
    private ContactHeader contactHeader;

    public void initializeSipServer() {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();

            this.sipFactory = SipFactory.getInstance();
            this.sipFactory.setPathName("gov.nist");
            this.properties = new Properties();
            this.properties.setProperty("javax.sip.STACK_NAME", "stack");
            this.sipStack = this.sipFactory.createSipStack(this.properties);
            this.messageFactory = this.sipFactory.createMessageFactory();
            this.headerFactory = this.sipFactory.createHeaderFactory();
            this.addressFactory = this.sipFactory.createAddressFactory();
            this.listeningPointudp = this.sipStack.createListeningPoint(this.ip, this.port1, this.transportudp);
            this.listeningPointtcp = this.sipStack.createListeningPoint(this.ip, this.port1, this.transporttcp);

            this.sipProvider1 = this.sipStack.createSipProvider(this.listeningPointudp);
            this.sipProvider2 = this.sipStack.createSipProvider(this.listeningPointtcp);

            this.sipProvider1.addSipListener(this);
            this.sipProvider2.addSipListener(this);

            this.contactAddress = this.addressFactory.createAddress("sip:" + this.ip + ":" + this.port1);
            this.contactHeader = this.headerFactory.createContactHeader(contactAddress);

            System.out.println("SIP server initiazed: Local address: " + this.ip + ":" + this.port1 + "\n Listening on " + transportudp.toUpperCase() + " and " + transporttcp.toUpperCase());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        // Get the request.
        Request request = requestEvent.getRequest();

        System.out.println(new Date() + "\nRECV " + request.getMethod() + " " + request.getRequestURI().toString());
        System.out.println(request.toString());
        try {
            // Get or create the server transaction.
            ServerTransaction transaction = requestEvent.getServerTransaction();
            if (null == transaction) {
                //handling the two sipprovider cases: one uses the TCP , the other: UDP
                ViaHeader viaHd = (ViaHeader) request.getHeader(ViaHeader.NAME);
                String transport = viaHd.getTransport();
                if (transport.equalsIgnoreCase(transportudp)) {
                    transaction = this.sipProvider1.getNewServerTransaction(request);
                } else if (transport.equalsIgnoreCase(transporttcp)) {
                    transaction = this.sipProvider2.getNewServerTransaction(request);
                }
            }

            // Process the request and send a response.
            Response response;
            if (request.getMethod().equals("REGISTER")) {
                // If the request is a REGISTER.
                response = this.messageFactory.createResponse(200, request);
                /*
                 ((ToHeader)response.getHeader("To")).setTag(String.valueOf(this.tag));
                 */
                response.addHeader(this.contactHeader);

                transaction.sendResponse(response);
                System.out.println(" / Response SENT " + response.getStatusCode() + " " + response.getReasonPhrase() + "\n" + response.toString());
            } else if (request.getMethod().equals("INVITE")) {
                // If the request is an INVITE.
                response = this.messageFactory.createResponse(200, request);
                /* 
                 ((ToHeader)response.getHeader("To")).setTag(String.valueOf(this.tag));
                 */
                 response.addHeader(this.contactHeader);

                transaction.sendResponse(response);
                System.out.println(" / Response SENT " + response.getStatusCode() + " " + response.getReasonPhrase() + "\n" + response.toString());
            } else if (request.getMethod().equals("ACK")) {
                // If the request is an ACK.
            } else if (request.getMethod().equals("BYE")) {
                // If the request is a BYE.
                response = this.messageFactory.createResponse(200, request);
                ((ToHeader) response.getHeader("To")).setTag(String.valueOf(this.tag));
                response.addHeader(this.contactHeader);
                transaction.sendResponse(response);
                System.out.println(" / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
            }
        } catch (SipException e) {
            System.out.println("\nERROR (SIP): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processIOException(IOExceptionEvent exceptionEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
