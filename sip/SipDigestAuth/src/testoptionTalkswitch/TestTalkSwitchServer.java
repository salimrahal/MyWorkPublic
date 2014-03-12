/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testoptionTalkswitch;

/**
 *
 * @author salim
 */
import javax.sip.*;
import javax.sip.address.*;
import javax.sip.header.*;
import javax.sip.message.*;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;

public class TestTalkSwitchServer implements SipListener {

    private String username;
    private SipStack sipStack;
    private SipFactory sipFactory;
    private AddressFactory addressFactory;
    private HeaderFactory headerFactory;
    private MessageFactory messageFactory;
    private SipProvider sipProvider;
    private ClientTransaction optionsTId;

   public static final String UACIp = "93.185.237.237";
    public static final int UACPort = 5060;
    /**
     * Here we initialize the SIP stack.
     */
    public TestTalkSwitchServer(String username) {
        setUsername(username);
        sipFactory = SipFactory.getInstance();
        sipFactory.setPathName("gov.nist");
        Properties properties = new Properties();
        properties.setProperty("javax.sip.STACK_NAME", "TextClient");
//properties.setProperty("javax.sip.IP_ADDRESS", ip);
        //SR: outbound not used
       //properties.setProperty("javax.sip.OUTBOUND_PROXY", "proxy.sipthor.net:5060" + "/" + ListeningPoint.UDP);
//DEBUGGING: Information will go to files
//textclient.log and textclientdebug.log
        properties.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "32");
        properties.setProperty("gov.nist.javax.sip.SERVER_LOG", "textclient.txt");
        properties.setProperty("gov.nist.javax.sip.DEBUG_LOG", "textclientdebug.log");
        try {
            sipStack = sipFactory.createSipStack(properties);
            headerFactory = sipFactory.createHeaderFactory();
            addressFactory = sipFactory.createAddressFactory();
            messageFactory = sipFactory.createMessageFactory();
//ListeningPoint tcp = sipStack.createListeningPoint("127.0.0.1", 5070, ListeningPoint.TCP);
            ListeningPoint udp = sipStack.createListeningPoint(UACIp, UACPort, ListeningPoint.UDP);
//sipProvider = sipStack.createSipProvider(tcp);
//sipProvider.addSipListener(this);
            sipProvider = sipStack.createSipProvider(udp);
            sipProvider.addSipListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method uses the SIP stack to send a message.
     */
    public void sendMessage() throws ParseException, InvalidArgumentException, SipException {
//create from header
        SipURI from = addressFactory.createSipURI(getUsername(),getHost() + ":" + getPort());
        Address fromNameAddress = addressFactory.createAddress(from);
        fromNameAddress.setDisplayName(getUsername());
        FromHeader fromHeader = headerFactory.createFromHeader(fromNameAddress, "textclientv1.0");
//create to header
        String username = "299";
        String address = "173.231.103.38";//"usnjpar1askvm01.us.ad.irmc.com";
        SipURI toAddress = addressFactory.createSipURI(username, address);
        Address toNameAddress = addressFactory.createAddress(toAddress);
        toNameAddress.setDisplayName(username);
        ToHeader toHeader = headerFactory.createToHeader(toNameAddress, null);
        SipURI requestURI = addressFactory.createSipURI(username, address);
        requestURI.setTransportParam("udp");
        ArrayList<ViaHeader> viaHeaders = new ArrayList<ViaHeader>();
        ViaHeader viaHeader = headerFactory.createViaHeader(getHost(), getPort(), "udp", "branch1");
        viaHeaders.add(viaHeader);
        CallIdHeader callIdHeader = sipProvider.getNewCallId();
        CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.OPTIONS);
        MaxForwardsHeader maxForwards = headerFactory.createMaxForwardsHeader(70);
        Request request = messageFactory.createRequest(requestURI,
                Request.OPTIONS, callIdHeader, cSeqHeader, fromHeader,
                toHeader, viaHeaders, maxForwards);
        SipURI contactURI = addressFactory.createSipURI(getUsername(), getHost());
        contactURI.setPort(getPort());
        Address contactAddress = addressFactory.createAddress(contactURI);
        contactAddress.setDisplayName(getUsername());
        ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
        request.addHeader(contactHeader);
        System.out.println(request.toString());
//ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("text", "plain");
//request.setContent(message, contentTypeHeader);
        optionsTId = sipProvider.getNewClientTransaction(request);


// send the request out.
        optionsTId.sendRequest();
//sipProvider.sendRequest(request);
//Dialog dialog = optionsTId.getDialog();
//System.out.println("Dialog: " + dialog.getDialogId());
    }

    /**
     * This method is called by the SIP stack when a response arrives.
     */
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        ClientTransaction tid = responseEvent.getClientTransaction();
        CSeqHeader cseq = (CSeqHeader) response.getHeader(CSeqHeader.NAME);


        System.out.println("Response received : Status Code = " + response.getStatusCode() + " " + cseq);
         System.out.println("Response received : "+response.toString());
        if (tid == null) {
            System.out.println("Stray response -- dropping ");
            return;
        }
        try {
            if (response.getStatusCode() == Response.OK) {
                if (cseq.getMethod().equals(Request.OPTIONS)) {
                    System.out.println("OK received for OPTIONS");
                }
            }
        } catch (Exception e) {
            System.out.println("some exceptions");
        }
    }

    /**
     * This method is called by the SIP stack when a new request arrives.
     */
    public void processRequest(RequestEvent evt) {
    }

    /**
     * This method is called by the SIP stack when there's no answer to a
     * message. Note that this is treated differently from an error message.
     */
    public void processTimeout(TimeoutEvent evt) {
        System.out.println("Timeout...");
    }

    /**
     * This method is called by the SIP stack when there's an asynchronous
     * message transmission error.
     */
    public void processIOException(IOExceptionEvent evt) {
        System.out.println("IO Exceptions");
    }

    /**
     * This method is called by the SIP stack when a dialog (session) ends.
     */
    public void processDialogTerminated(DialogTerminatedEvent evt) {
    }

    /**
     * This method is called by the SIP stack when a transaction ends.
     */
    public void processTransactionTerminated(TransactionTerminatedEvent evt) {
    }

    public String getHost() {
        String host = UACIp;
        return host;
    }

    public int getPort() {
        int port = UACPort;
        return port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public static void main(String args[]) {
        TestTalkSwitchServer testsip = new TestTalkSwitchServer("299");
        try {
            System.out.println("message send...");
            testsip.sendMessage();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}
