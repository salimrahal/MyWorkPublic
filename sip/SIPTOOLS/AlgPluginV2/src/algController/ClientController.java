/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algController;

import algBo.ALGBo;
import algGui.AlgJFrame;
import algGui.AlgJPanel;
import java.io.PrintWriter;
import java.net.SocketException;
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
    // ConfVO confVO = ConfVO.getInstance();
    // Objects keeping local configuration.
    String iplocal;
    String hostnameLocal = "";
    Integer portSrc;
    String extlocal;
    //Server Info
    String ipServer;
    Integer portServer;
    String protocol;       // The local protocol (UDP).
    int tag = (new Random()).nextInt(); // The local tag.
    Address contactAddress;         // The contact address.
    ContactHeader contactHeader;    // The contact header.
    public boolean retryAuth;
    public long seqReg = 0;
    public long seqSub = 0;
    /**
     * ******UI Parameters: **********
     */
    String toSipURI;
    //local SIP URI taken from the config
    String localSipURI = "sip:" + 201 + "@" + ipServer + ":" + protocol;

    //String requestURITextField = "sip:" + 201 + "@" + ipServer + ":" + portServer;
    public ClientController() throws SocketException {
         iplocal = ALGBo.getIplocal();
         extlocal = ALGBo.getExtlocal();
         portSrc = ALGBo.comb3SrcPort5060;
         ipServer = ALGBo.getIpServer();
         portServer = ALGBo.comb3DestPort5060;
         protocol = ALGBo.comb3ProtoUDP; 
    }

    
    
    /**
     * It removes the listening point and sip provider in runtime. so the user
     * can change his IP address at runtime
     *
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
        // Create the SIP factory Singleton and set the path name.
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
        this.listeningPoint = this.sipStack.createListeningPoint(this.iplocal, this.portSrc, this.protocol);
        // Create the SIP provider.
        this.sipProvider = this.sipStack.createSipProvider(this.listeningPoint);
        // Add our application as a SIP listener.
        this.sipProvider.addSipListener(this);
        // Create the contact address used for all SIP messages.
        this.contactAddress = this.addressFactory.createAddress("sip:" + this.extlocal + "@" + this.iplocal + ":" + this.portSrc + ";transport=" + this.protocol);
        // Create the contact header used for all SIP messages.
        this.contactHeader = this.headerFactory.createContactHeader(contactAddress);
        //add instance-id to contact header
        //this.contactHeader.setParameter("+sip.instance", sipInstance);
    }

    public Request generateFreshBasicRequestRegister(String fromAdress, String destAdresstextfield, String method, String protocol, Integer expiresparam) throws ParseException, InvalidArgumentException {
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
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portSrc, protocol, null);
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
        System.out.println(request.toString());
        return request;

    }

    public String sendRegisterStateless() {
        String res = "";
        // A method called when you click on the "Reg (SL)" button.
        try {
            // Get the destination address from the text field.
            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
            //register here has the same URI to and FROM
            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portSrc;
            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portServer;
            Request request = generateFreshBasicRequestRegister(addressFromStr, addressToStr, "REGISTER", this.protocol, 27);

            // Send the request statelessly through the SIP provider.
            this.sipProvider.sendRequest(request);

            // Display the message in the text area.
            res = ("\nRequest sent:\n" + request.toString() + "\n\n");
        } catch (Exception e) {
            // If an error occurred, display the error.
            res = "\nRequest sent failed: " + e.getMessage() + "\n";
        }
        return res;
    }

    @Override
    /**
     * Desc: Actually it process the request below: 1- Notify request: it send
     * an 200 OK as response to a Notify request
     */
    public void processRequest(RequestEvent requestEvent) {

        Response response = null;

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

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        // Get the response.
        Response response = responseEvent.getResponse();
        String responseStr = response.toString();
//            //log the request to log file
//            PrintWriterObj printSingleton = PrintWriterObj.getInstance();
//            PrintWriter pw = printSingleton.getSipLogsPW();
//            PrintWriterObj.writePrintWriter(pw, "Process Response...........\n" + response.toString());
        // Display the response message in the text area.
        System.out.println("\nReceived response: " + response.toString());
        AlgJPanel.comb1RcvMsg.append("\nReceived response: "+responseStr);
        /*
         TODO 1- implement the comparision algo then post the ALG detector message
         2- post the message:             
         */
        AlgJPanel.resultmsg.setText("No ALG Detected");

    }
}
