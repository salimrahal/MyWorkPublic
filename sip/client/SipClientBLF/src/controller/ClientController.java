/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bo.Networking1;
import bo.PresenceCallable;
import bo.ResponseProcessor;
import bo.SipClientBO;
import bo.StatusRunnable;
import bo.xmlparser.ConfBO;
import vo.PresenceVO;
import bo.xmlparser.SAXParserPresence;
import buddyListplugin.buddylist.BuddyAdapter;
import buddyListplugin.buddylist.BuddyCellRenderer;
import gui.SipBLFUI;
import static gui.SipBLFUI.jList1;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import java.util.Properties;
import java.util.Random;
import java.util.TooManyListenersException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sip.ClientTransaction;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.PeerUnavailableException;
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
import javax.sip.TransportNotSupportedException;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.header.AcceptHeader;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;

import javax.sip.header.ContentTypeHeader;
import javax.sip.header.EventHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.MaxForwardsHeader;

import javax.sip.header.ProxyAuthorizationHeader;
import javax.sip.header.ProxyRequireHeader;
import javax.sip.header.RequireHeader;
import javax.sip.header.SupportedHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.UserAgentHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import buddyListplugin.buddylist.Buddy;
import java.awt.Color;
import vo.BuddyList;
import vo.ConfVO;
import vo.PrintWriterObj;
import vo.Status;

/**
 *
 * @author salim
 */
public class ClientController implements SipListener {
    // Objects used to communicate to the JAIN SIP API.

    SipFactory sipFactory;          // Used to access the SIP API.
    SipStack sipStack;              // The SIP stack.
    SipProvider sipProvider;        // Used to send SIP messages.
    MessageFactory messageFactory;  // Used to create SIP message factory.
    HeaderFactory headerFactory;    // Used to create SIP headers.
    AddressFactory addressFactory;  // Used to create SIP URIs.
    ListeningPoint listeningPoint;  // SIP listening IP address/port.
    Properties properties;          // Other properties.
    //Extract the config File
    //ConfVO confVO = ConfBO.retrieveConfigurations("./conf/properties.xml");
    ConfVO confVO = ConfVO.getInstance();
    // Objects keeping local configuration.
    String iplocal ; // The local IP its now reading dynamiccaly the IP
    String hostnameLocal = confVO.getHostNameLocal();// The local IP address.
    //String requestURI ;//= "sip:299@173.231.103.38:5060";
    int portlocal = confVO.getUACPort();
    String extLocal = confVO.getExtSipLocal();
    String username = confVO.getUsername();
    String password = confVO.getPassword();
    String sipInstance = ConfBO.getSipInstanceUUID();
    //Server Info
    String ipServer = confVO.getUASIp();
    int portServer = confVO.getUASPort();
    String protocol = confVO.getProtocol();        // The local protocol (UDP).
    int tag = (new Random()).nextInt(); // The local tag.
    Address contactAddress;         // The contact address.
    ContactHeader contactHeader;    // The contact header.
    public boolean retryAuth;
    public long seqReg = 0;
    public long seqSub = 0;
    //String  = null;
    //Business Object
    SipClientBO sipClientBO;
    ResponseProcessor responseProcessor;
    SAXParserPresence saxparser;
    /**
     * ******UI Parameters: **********
     */
    String toSipURI;
    //local SIP URI taken from the config
    String localSipURI = "sip:" + extLocal + "@" + ipServer + ":" + portlocal;
    // String requestURITextField = "sip:" + extLocal + "@" + ipServer + ":" + portServer;
    //ClientTransaction transactionGlobal = null;
    //declare the service
    final ExecutorService service;

    public ClientController() throws Exception {

        sipClientBO = new SipClientBO();
        responseProcessor = new ResponseProcessor();
        //initialize the Sax parser
        saxparser = new SAXParserPresence();
        //initialize the Thread Pool for PresenceVO callable
        service = Executors.newFixedThreadPool(2);
        
        //iplocal = Networking1.getLocalIpAddress();// for LAN it's returning the loop back
        iplocal = confVO.getUACIp();
    }

    /**
     * It removes the listening point and sip provider in runtime. so the user can change his IP address at runtime
     * @return res message
     * @throws Exception 
     */
    public String reset() throws Exception {
       // System.out.println("reset");
        String msg = "OK";
        try {
            this.sipProvider.removeListeningPoint(listeningPoint);
            this.sipProvider.removeSipListener(this);
            this.sipStack.deleteSipProvider(this.sipProvider);  
            this.sipFactory.resetFactory();
            this.sipStack = null;
        } catch (Exception ex) {
            msg = ex.getLocalizedMessage();
        }
         // System.out.println("reset msg:"+msg);
        return msg;
    }
 public void createSipStack() throws PeerUnavailableException, TransportNotSupportedException, ObjectInUseException, InvalidArgumentException, TooManyListenersException, ParseException {

        // A method called when you open your application.
        // Create the SIP factory and set the path name.
        this.sipFactory = SipFactory.getInstance();
        this.sipFactory.setPathName("gov.nist");
        // Create and set the SIP stack properties.
        this.properties = new Properties();
        this.properties.setProperty("javax.sip.STACK_NAME", "stack");
        // Create the SIP stack.ext
        this.sipStack = this.sipFactory.createSipStack(this.properties);     
    }

    public void createSipFrameWork() throws PeerUnavailableException, TransportNotSupportedException, ObjectInUseException, InvalidArgumentException, TooManyListenersException, ParseException {
        // Create the SIP message factory.
        this.messageFactory = this.sipFactory.createMessageFactory();
        // Create the SIP header factory.
        this.headerFactory = this.sipFactory.createHeaderFactory();
        // Create the SIP address factory.
        this.addressFactory = this.sipFactory.createAddressFactory();
        // Create the SIP listening point and bind it to the local IP address, port and protocol.
        this.listeningPoint = this.sipStack.createListeningPoint(this.iplocal, this.portlocal, this.protocol);
        // Create the SIP provider.
        this.sipProvider = this.sipStack.createSipProvider(this.listeningPoint);
        // Add our application as a SIP listener.
        this.sipProvider.addSipListener(this);
        // Create the contact address used for all SIP messages.
        this.contactAddress = this.addressFactory.createAddress("sip:" + this.extLocal + "@" + this.iplocal + ":" + this.portlocal + ";transport=" + this.protocol);
        // Create the contact header used for all SIP messages.
        this.contactHeader = this.headerFactory.createContactHeader(contactAddress);
        //add instance-id to contact header
        this.contactHeader.setParameter("+sip.instance", sipInstance);
    }

    public Request registerStateful(String sipURIRegisterstr, Response response, Integer expires) throws ParseException, InvalidArgumentException, SipException, Exception {
        //register here has the same URI to and FROM
        String addressFromStr = sipURIRegisterstr;
        String addressToStr = sipURIRegisterstr;
        Request request = generateFreshBasicRequestRegister(addressFromStr, addressToStr, Request.REGISTER, expires);
        System.out.println("registerStateful....Begin\n");
        //check if there is a response recieved then add to the request Proxy-Authorisation header
        if (response != null) {
            retryAuth = true;
            if (response.getStatusCode() == Response.PROXY_AUTHENTICATION_REQUIRED) {
                System.out.print(" registerStateful 407");
                ProxyAuthorizationHeader authorHeader = (ProxyAuthorizationHeader) sipClientBO.makeAuthHeader(headerFactory, response, request, this.username, this.password);
                request.addHeader(authorHeader);
            } else {
                System.out.print("other status code");
            }
        }//end of response!null

        //log the request to log file
        PrintWriterObj printSingleton = PrintWriterObj.getInstance();
        PrintWriter pw = printSingleton.getSipLogsPW();
        PrintWriterObj.writePrintWriter(pw, request.toString());

        System.out.print(request.toString());
        // Create a new SIP client transaction.
        ClientTransaction transaction = this.sipProvider.getNewClientTransaction(request);
// Send the request statefully, through the client transaction.
        transaction.sendRequest();
        return request;
    }

    public Request generateFreshBasicRequestRegister(String fromAdress, String destAdresstextfield, String method, Integer expiresparam) throws ParseException, InvalidArgumentException {
        /*3261: CSEQ:
         * “ When a UAC resubmits a request with its credentials after receiving a
         401 (Unauthorized) or 407 (Proxy Authentication Required) response,
         it MUST increment the CSeq header field value as it would normally
         when sending an updated request.
         “
         */
        seqReg++;
        Integer expires = expiresparam;

        // Get the destination address from the text field.
        Address addressTo = this.addressFactory.createAddress(destAdresstextfield);
        // Create the request URI for the SIP message.
        javax.sip.address.URI requestURI = addressTo.getURI();

        // Create the SIP message headers.
        // The "Via" headers.
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portlocal, protocol, null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = this.sipProvider.getNewCallId();

        // The "CSeq" header.
        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seqReg, method);

        // The "From" header.
        Address addressFrom = this.addressFactory.createAddress(fromAdress);
        FromHeader fromHeader = this.headerFactory.createFromHeader(addressFrom, String.valueOf(this.tag));

        // The "To" header.
        ToHeader toHeader = this.headerFactory.createToHeader(addressTo, null);
        // create expires header
        ExpiresHeader expHd = this.headerFactory.createExpiresHeader(expires);

        // Create the REGISTER request.
        Request request = this.messageFactory.createRequest(
                requestURI,
                method,
                callIdHeader,
                cSeqHeader,
                fromHeader,
                toHeader,
                viaHeaders,
                maxForwardsHeader);
        // Add the "Contact" header to the request.
        request.addHeader(contactHeader);
        // The "UserAgent" headers
        ArrayList<String> uaHeaders = new ArrayList();
        uaHeaders.add(hostnameLocal);
        UserAgentHeader uaHeader = this.headerFactory.createUserAgentHeader(uaHeaders);
        request.addHeader(uaHeader);
        // add expires header
        request.addHeader(expHd);
        return request;

    }

    /**
     * Desc: Always register(Sign in) before Subscribe
     *
     * @param subscribeToURI
     * @param response
     * @param expires
     * @return
     * @throws ParseException
     * @throws InvalidArgumentException
     * @throws SipException
     * @throws Exception
     */
    public Request subscribe(String subscribeToURI, Response response, Integer expires) throws ParseException, InvalidArgumentException, SipException, Exception {
        System.out.println("subscribe....Begin\n");
        String fromURI = localSipURI;
        //generate a fresh request with new callerId, etc
        /*3261: CSEQ:
         * “ When a UAC resubmits a request with its credentials after receiving a
         401 (Unauthorized) or 407 (Proxy Authentication Required) response,
         it MUST increment the CSeq header field value as it would normally
         when sending an updated request.
         “
         */
        seqSub++;
        // Get the destination address from the text field.
        Address addressTo = this.addressFactory.createAddress(subscribeToURI);
        // Create the request URI for the SIP message.
        javax.sip.address.URI requestURI = addressTo.getURI();

        // Create the SIP message headers.
        // The "Via" headers.
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portlocal, protocol, null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = this.sipProvider.getNewCallId();

        // The "CSeq" header.
        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seqSub, Request.SUBSCRIBE);

        // The "From" header.
        Address addressFrom = this.addressFactory.createAddress(fromURI);
        FromHeader fromHeader = this.headerFactory.createFromHeader(addressFrom, String.valueOf(this.tag));

        // The "To" header.
        ToHeader toHeader = this.headerFactory.createToHeader(addressTo, null);
        // create expires header
        ExpiresHeader expHd = this.headerFactory.createExpiresHeader(expires);

        // Create the REGISTER request.
        Request request = this.messageFactory.createRequest(
                requestURI,
                Request.SUBSCRIBE,
                callIdHeader,
                cSeqHeader,
                fromHeader,
                toHeader,
                viaHeaders,
                maxForwardsHeader);
        //create extra headers
        // The "UserAgent" headers
        ArrayList<String> uaHeaders = new ArrayList();
        uaHeaders.add(hostnameLocal);
        UserAgentHeader uaHeader = this.headerFactory.createUserAgentHeader(uaHeaders);
        //SUBSCRIBE HEADERS:
        EventHeader eventHd = this.headerFactory.createEventHeader("presence");

        // add expires header
        request.addHeader(expHd);
        request.addHeader(eventHd);
        request.addHeader(uaHeader);
        // Add the "Contact" header to the request.
        request.addHeader(contactHeader);

        //check if there is a response recieved with 407 code, then add to the request Proxy-Authorisation header
        if (response != null) {
            retryAuth = true;
            if (response.getStatusCode() == Response.PROXY_AUTHENTICATION_REQUIRED) {
                System.out.print(" subscribe 407");
                ProxyAuthorizationHeader authorHeader = (ProxyAuthorizationHeader) sipClientBO.makeAuthHeader(headerFactory, response, request, this.username, this.password);
                request.addHeader(authorHeader);
            } else {
                System.out.print("subscribe: other status code");
            }
        }//end of response!null

        //log the request to log file
        PrintWriterObj printSingleton = PrintWriterObj.getInstance();
        PrintWriter pw = printSingleton.getSipLogsPW();
        PrintWriterObj.writePrintWriter(pw, request.toString());
        System.out.print("subscribe::" + request.toString());
        //create client transaction and send the request to the server
        ClientTransaction clientTransaction = this.sipProvider.getNewClientTransaction(request);
        clientTransaction.sendRequest();
        return request;
    }

    public String subscribeMultiple(ArrayList<Buddy> buddies, Response response, Integer expires) throws ParseException, InvalidArgumentException, SipException, Exception {
        String res = "";
        for (Buddy buddy : buddies) {
            String sipURI = "sip:" + buddy.name + "@" + ipServer + ":" + portServer;
            subscribe(sipURI, response, expires);
        }
        return res;
    }
    /*subscribeResourceList: the server won't respond to any request
     * 
     */

    @Deprecated
    public Request subscribeResourceList(String subscribeToURI, Response response, Integer expires) throws ParseException, InvalidArgumentException, SipException, Exception {
        System.out.println("subscribeResourceList....Begin\n");
        String fromURI = localSipURI;
        //generate a fresh request
         /*3261: CSEQ:
         * “ When a UAC resubmits a request with its credentials after receiving a
         401 (Unauthorized) or 407 (Proxy Authentication Required) response,
         it MUST increment the CSeq header field value as it would normally
         when sending an updated request.
         “
         */

        seqSub++;
        // Get the destination address from the text field.
        //should add the RLS server if its available
        Address addressTo = this.addressFactory.createAddress(subscribeToURI);
        // Create the request URI for the SIP message.
        javax.sip.address.URI requestURI = addressTo.getURI();

        // Create the SIP message headers.
        // The "Via" headers.
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portlocal, protocol, null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = this.sipProvider.getNewCallId();

        // The "CSeq" header.
        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seqSub, Request.SUBSCRIBE);

        // The "From" header.
        Address addressFrom = this.addressFactory.createAddress(fromURI);
        FromHeader fromHeader = this.headerFactory.createFromHeader(addressFrom, String.valueOf(this.tag));

        // The "To" header.
        ToHeader toHeader = this.headerFactory.createToHeader(addressTo, null);

        // Create the REGISTER request.
        Request request = this.messageFactory.createRequest(
                requestURI,
                Request.SUBSCRIBE,
                callIdHeader,
                cSeqHeader,
                fromHeader,
                toHeader,
                viaHeaders,
                maxForwardsHeader);
        /*
         * public Request createRequest(
         * URI requestURI, String method, CallIdHeader callId, CSeqHeader cSeq,
         * FromHeader from, ToHeader to, List via, MaxForwardsHeader maxForwards, ContentTypeHeader contentType, Object content)
         */

        // Add the "Contact" header to the request.
        request.addHeader(contactHeader);
        // The "UserAgent" headers
        ArrayList<String> uaHeaders = new ArrayList();

        UserAgentHeader uaHeader = this.headerFactory.createUserAgentHeader(uaHeaders);
        request.addHeader(uaHeader);
        //SUBSCRIBE HEADERS:
        EventHeader eventHd = this.headerFactory.createEventHeader("presence");
        //require header
        // RequireHeader reqHeader = headerFactory.createRequireHeader("recipient-list-subscribe");
        RequireHeader reqHeaderAdhoclist = headerFactory.createRequireHeader("adhoclist");
        //proxy require
        // Proxy-Require: ms-benotify
        ProxyRequireHeader proxyRqHd = headerFactory.createProxyRequireHeader("ms-benotify");
        //supported hd
        SupportedHeader suppHd = headerFactory.createSupportedHeader("eventlist");
        SupportedHeader suppHd2 = headerFactory.createSupportedHeader("com.microsoft.autoextend");
        SupportedHeader suppHdbenotify = headerFactory.createSupportedHeader("ms-benotify");
        SupportedHeader suppHdpiggyback = headerFactory.createSupportedHeader("ms-piggyback-first-notify");

        //accept headers
        //AcceptHeader acc1 = headerFactory.createAcceptHeader("application", "cpim-pidf+xml");
        AcceptHeader acc2 = headerFactory.createAcceptHeader("application", "rlmi+xml");
        //added by SALIM
        //AcceptHeader acc6 = headerFactory.createAcceptHeader("application", "xpidf+xml");

        AcceptHeader acc3 = headerFactory.createAcceptHeader("multipart", "related");
        //AcceptHeader acc4 = headerFactory.createAcceptHeader("multipart", "signed");
        //AcceptHeader acc5 = headerFactory.createAcceptHeader("multipart", "encrypted");

        AcceptHeader acc7 = headerFactory.createAcceptHeader("text", "xml+msrtc.pidf");
        //content disposition
        //ContentDispositionHeader contdispoHd = headerFactory.createContentDispositionHeader("recipient-list");
        //adding the headers
        uaHeaders.add(hostnameLocal);
        request.addHeader(eventHd);
        //add require
        request.addHeader(reqHeaderAdhoclist);
        //add proxy require header
        request.addHeader(proxyRqHd);
        //add supported
        request.addHeader(suppHd);
        request.addFirst(suppHd2);
        request.addHeader(suppHdbenotify);
        request.addFirst(suppHdpiggyback);
        //add accept header
        //request.addHeader(acc1);
        request.addHeader(acc2);
        request.addHeader(acc3);
        //request.addHeader(acc4);
        // request.addHeader(acc5);
        //request.addHeader(acc6);

        //request.addHeader(contdispoHd);
        //expires header
        ExpiresHeader exHd = this.headerFactory.createExpiresHeader(expires);
        request.addHeader(exHd);

        //create add content
        ContentTypeHeader contentTypeHd = this.headerFactory.createContentTypeHeader("application", "adrl+xml");
        request.setContent(sipClientBO.getResourceXMLAdhocasString(), contentTypeHd);
        //check if there is a response recieved with 407 code, then add to the request Proxy-Authorisation header
        if (response != null) {
            retryAuth = true;
            if (response.getStatusCode() == Response.PROXY_AUTHENTICATION_REQUIRED) {
                System.out.print(" subscribe 407");
                ProxyAuthorizationHeader authorHeader = (ProxyAuthorizationHeader) sipClientBO.makeAuthHeader(headerFactory, response, request, this.username, this.password);
                request.addHeader(authorHeader);
            } else {
                System.out.print("subscribe: other status code");
            }
        }//end of response!null
        System.out.print(request.toString());
        //log the request to log file
        PrintWriterObj printSingleton = PrintWriterObj.getInstance();
        PrintWriter pw = printSingleton.getSipLogsPW();
        PrintWriterObj.writePrintWriter(pw, request.toString());
        //create client transaction and send the request to the server
        ClientTransaction clientTransaction = this.sipProvider.getNewClientTransaction(request);
        clientTransaction.sendRequest();
        return request;
    }

    /**
    http://tools.ietf.org/html/rfc3665#section-2.4
    * TODO: add the credential to the cancel request
     */
    public Request registerCancel(Response response) throws ParseException, InvalidArgumentException, SipException {
        System.out.println("registerCancel....Begin\n");
        int expires = 0;
        //generate a fresh request with new callerId, etc
        String fromURI = localSipURI;
        // Get the destination address from the text field.
        Address addressTo = this.addressFactory.createAddress(localSipURI);
        // Create the request URI for the SIP message.
        javax.sip.address.URI requestURI = addressTo.getURI();

        // Create the SIP message headers.
        // The "Via" headers.
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portlocal, protocol, null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = this.sipProvider.getNewCallId();

        // The "CSeq" header.
        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seqSub, Request.SUBSCRIBE);

        // The "From" header.
        Address addressFrom = this.addressFactory.createAddress(fromURI);
        FromHeader fromHeader = this.headerFactory.createFromHeader(addressFrom, String.valueOf(this.tag));

        // The "To" header.
        ToHeader toHeader = this.headerFactory.createToHeader(addressTo, null);
        // create expires header
        ExpiresHeader expHd = this.headerFactory.createExpiresHeader(expires);

        // Create the REGISTER request.
        Request request = this.messageFactory.createRequest(
                requestURI,
                Request.SUBSCRIBE,
                callIdHeader,
                cSeqHeader,
                fromHeader,
                toHeader,
                viaHeaders,
                maxForwardsHeader);
        // Add the "Contact" header to the request.
        request.addHeader(contactHeader);
        // The "UserAgent" headers
        ArrayList<String> uaHeaders = new ArrayList();
        uaHeaders.add(hostnameLocal);
        UserAgentHeader uaHeader = this.headerFactory.createUserAgentHeader(uaHeaders);
        request.addHeader(uaHeader);
        // add expires header
        request.addHeader(expHd);
       

        //check if there is a response recieved with 407 code, then add to the request Proxy-Authorisation header
        if (response != null) {
            retryAuth = true;
            if (response.getStatusCode() == Response.PROXY_AUTHENTICATION_REQUIRED) {
                System.out.print(" subscribe 407");
                ProxyAuthorizationHeader authorHeader = (ProxyAuthorizationHeader) sipClientBO.makeAuthHeader(headerFactory, response, request, this.username, this.password);
                request.addHeader(authorHeader);
            } else {
                System.out.print("subscribe: other status code");
            }
        }//end of response!null

        System.out.print(request.toString());
        //create client transaction and send the request to the server
        ClientTransaction clientTransaction = this.sipProvider.getNewClientTransaction(request);
        clientTransaction.sendRequest();
        return request;
    }
    /*
     * OPTIONS sip:100@46.249.204.123 SIP/2.0
     Via: SIP/2.0/UDP 127.0.0.1:5064;branch=z9hG4bK-715721026;rport=5064;received=202.136.208.124
     From: "sipvicious" <sip:100@1.1.1.1>;tag=3265663963633762313363340132303633383738383232
     Accept: application/sdp
     User-Agent: friendly-scanner
     To: "sipvicious" <sip:100@1.1.1.1>
     Contact: <sip:100@127.0.0.1:5064>
     CSeq: 1 OPTIONS
     Call-ID: 530810658518591419365271
     Max-Forwards: 70
     Content-Length: 0
     */

    public Request sendOptions(String destAdresstextfield, Response response) throws ParseException, InvalidArgumentException, SipException {
        System.out.println("buildOptions....Begin\n");
        int expires = 30;
        //generate a fresh request with new callerId, etc
        String fromURI = localSipURI;
        // Get the destination address from the text field.
        Address addressTo = this.addressFactory.createAddress(destAdresstextfield);
        // Create the request URI for the SIP message.
        javax.sip.address.URI requestURI = addressTo.getURI();

        // Create the SIP message headers.
        // The "Via" headers.
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portlocal, protocol, null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = this.sipProvider.getNewCallId();

        // The "CSeq" header.
        CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(seqSub, Request.OPTIONS);

        // The "From" header.
        Address addressFrom = this.addressFactory.createAddress(fromURI);
        FromHeader fromHeader = this.headerFactory.createFromHeader(addressFrom, String.valueOf(this.tag));

        // The "To" header.
        ToHeader toHeader = this.headerFactory.createToHeader(addressTo, null);
        // create expires header
        ExpiresHeader expHd = this.headerFactory.createExpiresHeader(expires);

        // Create the REGISTER request.
        Request request = this.messageFactory.createRequest(
                requestURI,
                Request.OPTIONS,
                callIdHeader,
                cSeqHeader,
                fromHeader,
                toHeader,
                viaHeaders,
                maxForwardsHeader);
        // Add the "Contact" header to the request.
        request.addHeader(contactHeader);
        // The "UserAgent" headers
        ArrayList<String> uaHeaders = new ArrayList();
        uaHeaders.add(hostnameLocal);
        UserAgentHeader uaHeader = this.headerFactory.createUserAgentHeader(uaHeaders);
        request.addHeader(uaHeader);
        AcceptHeader accepthd = this.headerFactory.createAcceptHeader("application", "sdp");
        request.addHeader(accepthd);
        request.addHeader(expHd);
        System.out.print(request.toString());
        //create client transaction and send the request to the server
        ClientTransaction clientTransaction = this.sipProvider.getNewClientTransaction(request);
        clientTransaction.sendRequest();
        return request;
    }

    @Override
    /**
     * Desc: Actually it process the request below: 1- Notify request: it send
     * an 200 OK as response to a Notify request
     */
    public void processRequest(RequestEvent requestEvent) {

        Response response = null;
        //used to render the buddy list every time it receive a request NOTIFY
        BuddyCellRenderer bcr = null;
        //instintiate a new saxparserPresence

        //get the printwriter singleton
        PrintWriterObj printSingleton = PrintWriterObj.getInstance();
        PrintWriter pw = printSingleton.getSipLogsPW();
        try {

            Request request = requestEvent.getRequest();
            String requestStr = requestEvent.getRequest().toString();

            //log the request to log file
            PrintWriterObj.writePrintWriter(pw, "Process Request...........\n" + requestStr);
            System.out.println("\nProcess Request...........\n" + requestStr);

            // Get or create the server transaction.
            ServerTransaction transaction = requestEvent.getServerTransaction();

            if (transaction == null) {
                System.out.println("transaction == null get new one for the request...\n");
                transaction = this.sipProvider.getNewServerTransaction(request);
            }
            // If the request is a NOTIFY.
            if (request.getMethod().equals(Request.NOTIFY)) {
                //Steps: 1- send an Ok message
                //2- read the status of the extension
                //3- update the UI 

                //1- send 200 OK
                response = this.messageFactory.createResponse(200, request);
                ((ToHeader) response.getHeader("TO")).setTag(String.valueOf(this.tag));
                response.addHeader(this.contactHeader);
                System.out.println("\n replying..... response=" + response.toString());
                transaction.sendResponse(response);
                //log the request to log file
                PrintWriterObj.writePrintWriter(pw, " / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
                System.out.println(" / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());

                //2- retrieve that status of the extension: build the POJO presence      
                //declare the task that will return the PresenceVO correspond of the Notify Request
                Future<PresenceVO> taskPresence;

                //get the presence Obj
                taskPresence = service.submit(new PresenceCallable(request, saxparser));
                PresenceVO presenceObj = taskPresence.get();
                System.out.println("presenceObj=" + presenceObj.toString() + "/sipID=" + presenceObj.getSipId());
                //add the current presenceObj to the hash
                //Hashtable<String, PresenceVO> presHash = SipClientBO.getPresenceHash();
                //presHash.put(presenceObj.getSipId(), presenceObj);

                /* 
                 *The below block gets the list from the model and update a buddy in this list passed by NOTIFY message:
                 */
                DefaultListModel listModel = (DefaultListModel) jList1.getModel();
                Object[] ObjArray = listModel.toArray();
                for (Object obj : ObjArray) {
                    if (obj instanceof Buddy) {
                        Buddy buddyInst = (Buddy) obj;
                        System.out.println("IN FOR LOOP");
                        if (buddyInst.name.equalsIgnoreCase(presenceObj.getSipId())) {
                            System.out.println("found=" + presenceObj.getSipId());
                            buddyInst.status = presenceObj.getStatus();
                            buddyInst.statusAddress = presenceObj.statusAddress;
                            buddyInst.message = buddyInst.status.toString();
                        }
                    }
                }

                //instantiate the celle renderer once
                if (bcr == null) {
                    bcr = new BuddyCellRenderer(new BuddyAdapter());
                    System.out.println("processRequest : initializeBuddyCellRenderer");
                }
                //update the UI to reflect the status of the SIPIDs
                jList1.setCellRenderer(bcr);
                jList1.setPrototypeCellValue(ObjArray[0]);
                
            } else if (request.getMethod().equals(Request.REGISTER)) {
                // If the request is a REGISTER.
                response = this.messageFactory.createResponse(200, request);
                ((ToHeader) response.getHeader("TO")).setTag(String.valueOf(this.tag));
                response.addHeader(this.contactHeader);
                transaction.sendResponse(response);
                //log the request to log file
                PrintWriterObj.writePrintWriter(pw, " / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
                System.out.println(" / SENT " + response.getStatusCode() + " " + response.getReasonPhrase());
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (Exception ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        String requestURITextField = "sip:" + extLocal + "@" + ipServer + ":" + portServer;
        try {
            // Get the response.
            Response response = responseEvent.getResponse();

            //log the request to log file
            PrintWriterObj printSingleton = PrintWriterObj.getInstance();
            PrintWriter pw = printSingleton.getSipLogsPW();
            PrintWriterObj.writePrintWriter(pw, "Process Response...........\n" + response.toString());

            // Display the response message in the text area.
            System.out.println("\nReceived response: " + response.toString());

            //get the status code
            int statusCode = response.getStatusCode();
            //get the seq header to extract the method
            CSeqHeader cseqHd = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
            String methodResponse = cseqHd.getMethod();

            if (statusCode == Response.OK) {
                responseProcessor.processSucess(response, this);
                
            }
            /*
             * Manage the client error response: 400 -> 410
             * case1: Handling register: resending the request with credentials
             * case2: handling Subscribe: resending the request with credentials
             **/
            // if (statusCode > Response.BAD_REQUEST && statusCode < Response.GONE && !retryAuth) {
            if (statusCode > Response.BAD_REQUEST && statusCode < Response.GONE) {
                try {

                    responseProcessor.processClientError(this, response, requestURITextField);
                } catch (Exception ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }//end error client responsesubsc
            else if (statusCode == Response.CALL_OR_TRANSACTION_DOES_NOT_EXIST) {
                System.out.println("transaction or call does exist");
                //if the error response method is Subscribe, then the user need to re-subscribe a new subscription: 
                //http://tools.ietf.org/html/rfc3265#section-3.1.4.1
                if (methodResponse.equalsIgnoreCase(Request.SUBSCRIBE)) {
                    try {
                        subscribe(requestURITextField, response, SipClientBO.Expires_default);
                    } catch (ParseException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidArgumentException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SipException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
