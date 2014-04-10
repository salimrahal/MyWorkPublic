/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package siplib;

import java.net.InetAddress;
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
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

/**
 *
 * @author salim
 */
public class SipListenerServer implements SipListener{
    private SipFactory sipFactory;
    private SipStack sipStack;
    private SipProvider sipProvider;
    private MessageFactory messageFactory;
    private HeaderFactory headerFactory;
    private AddressFactory addressFactory;
    private ListeningPoint listeningPoint;
    private Properties properties;

    private String ip;
    private int port = 5060;
    private String protocol = "udp";
    private int tag = (new Random()).nextInt();
    private Address contactAddress;
    private ContactHeader contactHeader;
    
     
    public void initializeSipServer(){
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
            this.listeningPoint = this.sipStack.createListeningPoint(this.ip, this.port, this.protocol);
            this.sipProvider = this.sipStack.createSipProvider(this.listeningPoint);
            this.sipProvider.addSipListener(this);

            this.contactAddress = this.addressFactory.createAddress("sip:" + this.ip + ":" + this.port);
            this.contactHeader = this.headerFactory.createContactHeader(contactAddress);
            
           System.out.println("SIP server initiazed: Local address: " + this.ip + ":" + this.port + "\n");
          }catch(Exception e) {
              System.out.println( e.getMessage()); 
        }
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
         // Get the request.
        Request request = requestEvent.getRequest();

        System.out.println("\nRECV " + request.getMethod() + " " + request.getRequestURI().toString());        
       try {
            // Get or create the server transaction.
            ServerTransaction transaction = requestEvent.getServerTransaction();
            if(null == transaction) {
                transaction = this.sipProvider.getNewServerTransaction(request);
            }
          
            // Process the request and send a response.
            Response response;
            if(request.getMethod().equals("REGISTER")) {
                // If the request is a REGISTER.
                response = this.messageFactory.createResponse(200, request);
                ((ToHeader)response.getHeader("To")).setTag(String.valueOf(this.tag));
                response.addHeader(this.contactHeader);
                transaction.sendResponse(response);
                 System.out.println(" / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
            }
            else if(request.getMethod().equals("INVITE")) {
                // If the request is an INVITE.
                response = this.messageFactory.createResponse(200, request);
                ((ToHeader)response.getHeader("To")).setTag(String.valueOf(this.tag));
                response.addHeader(this.contactHeader);
                transaction.sendResponse(response);
                 System.out.println(" / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
            }
            else if(request.getMethod().equals("ACK")) {
                // If the request is an ACK.
            }
            else if(request.getMethod().equals("BYE")) {
                // If the request is a BYE.
                response = this.messageFactory.createResponse(200, request);
                ((ToHeader)response.getHeader("To")).setTag(String.valueOf(this.tag));
                response.addHeader(this.contactHeader);
                transaction.sendResponse(response);
                 System.out.println(" / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
            }
        }
        catch(SipException e) {            
             System.out.println("\nERROR (SIP): " + e.getMessage());
        }
        catch(Exception e) {
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
