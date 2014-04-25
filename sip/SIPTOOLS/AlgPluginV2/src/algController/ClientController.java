/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algController;

import algBo.ALGBo;
import algGui.AlgJFrame;
import algGui.AlgJPanel;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
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
import javax.sip.Timeout;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.TransportNotSupportedException;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.header.AcceptHeader;
import javax.sip.header.AllowHeader;
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

//BO
    ALGBo algBo;
// Objects used to communicate to the JAIN SIP API.

    SipFactory sipFactory;          // Used to access the SIP API.
    SipStack sipStack;              // The SIP stack.
    SipProvider sipProvider1;        // Used to send SIP messages.
    SipProvider sipProvider2; 
    SipProvider sipProvider3; 
    SipProvider sipProvider4; 
    
    MessageFactory messageFactory;  // Used to create SIP message factory.
    HeaderFactory headerFactory;    // Used to create SIP headers.
    AddressFactory addressFactory;  // Used to create SIP URIs.

    ListeningPoint listeningPoint1;  // SIP listening IP address/port.
    ListeningPoint listeningPoint2;
    ListeningPoint listeningPoint3;
    ListeningPoint listeningPoint4;

    Properties properties;          // Other properties.
    //Extract the config File
    //ConfVO confVO = ConfBO.retrieveConfigurations("./conf/properties.xml");
    // ConfVO confVO = ConfVO.getInstance();
    // Objects keeping local configuration.
    String iplocal;
    String hostnameLocal;
    Integer portSrc1;
    Integer portSrc2;
    Integer portdest1;

    String extlocal;
    //Server Info
    String ipServer;
    Integer portServer;
    String transportudp;       // The local protocol (UDP).
    String transporttcp;       // 
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
    String localSipURI = "sip:" + 201 + "@" + ipServer + ":" + transportudp;

    //String requestURITextField = "sip:" + 201 + "@" + ipServer + ":" + portServer;
    public ClientController() throws SocketException {
        algBo = new ALGBo();
        iplocal = ALGBo.getIplocal();
        extlocal = ALGBo.getExtlocal();
        portSrc1 = ALGBo.getPortsrc1();
        portSrc2 = ALGBo.getPortsrc2();
        ipServer = ALGBo.getIpServer();
        portServer = ALGBo.getPortdest1();
        transportudp = ALGBo.getTransportudp();
        transporttcp = ALGBo.getTransporttcp();
        hostnameLocal = ALGBo.getHostname();
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
            this.sipProvider1.removeListeningPoint(listeningPoint1);
            this.sipProvider1.removeSipListener(this);
            this.sipStack.deleteSipProvider(this.sipProvider1);
            
            this.sipProvider2.removeListeningPoint(listeningPoint2);
            this.sipProvider2.removeSipListener(this);
            this.sipStack.deleteSipProvider(this.sipProvider2);
            
            this.sipProvider3.removeListeningPoint(listeningPoint3);
            this.sipProvider3.removeSipListener(this);
            this.sipStack.deleteSipProvider(this.sipProvider3);
            
            this.sipProvider4.removeListeningPoint(listeningPoint4);
            this.sipProvider4.removeSipListener(this);
            this.sipStack.deleteSipProvider(this.sipProvider4);
            
            this.sipFactory.resetFactory();
            this.sipStack = null;
        } catch (Exception ex) {
            msg = ex.getLocalizedMessage();
        }
        System.out.println("reset() :reset msg:" + msg);
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
        this.listeningPoint1 = this.sipStack.createListeningPoint(this.iplocal, this.portSrc1, this.transportudp);
        this.listeningPoint2 = this.sipStack.createListeningPoint(this.iplocal, this.portSrc1, this.transporttcp);
        this.listeningPoint3 = this.sipStack.createListeningPoint(this.iplocal, this.portSrc2, this.transportudp);
        this.listeningPoint4 = this.sipStack.createListeningPoint(this.iplocal, this.portSrc2, this.transporttcp);

// Create the SIP provider.
        this.sipProvider1 = this.sipStack.createSipProvider(this.listeningPoint1);
        this.sipProvider2 = this.sipStack.createSipProvider(this.listeningPoint2);
        this.sipProvider3 = this.sipStack.createSipProvider(this.listeningPoint3);
        this.sipProvider4 = this.sipStack.createSipProvider(this.listeningPoint4);

        // Add our application as a SIP listener.
        this.sipProvider1.addSipListener(this);
        this.sipProvider2.addSipListener(this);
        this.sipProvider3.addSipListener(this);
        this.sipProvider4.addSipListener(this);
        
        //TODO: the below contact address will change , depends on the port scr/ transport
        // Create the contact address used for all SIP messages.
        this.contactAddress = this.addressFactory.createAddress("sip:" + this.extlocal + "@" + this.iplocal + ":" + this.portSrc1 + ";transport=" + this.transportudp);
        // Create the contact header used for all SIP messages.
        this.contactHeader = this.headerFactory.createContactHeader(contactAddress);
        //add instance-id to contact header
        //this.contactHeader.setParameter("+sip.instance", sipInstance);
    }

    public Request generateFreshBasicRequest(String fromAdress, String destAdresstextfield, String method, String protocol, Integer expiresparam) throws ParseException, InvalidArgumentException, SocketException {
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
        ViaHeader viaHeader = this.headerFactory.createViaHeader(this.iplocal, this.portSrc1, protocol, null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = this.sipProvider1.getNewCallId();

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
        //add allow header      
        AllowHeader allowHead = this.headerFactory.createAllowHeader("ACK, BYE, CANCEL, INFO, INVITE, NOTIFY, OPTIONS, REFER, UPDATE");
        request.addHeader(allowHead);
        SupportedHeader suppHead = this.headerFactory.createSupportedHeader("replaces");
        request.addHeader(suppHead);

        //request = this.messageFactory.createRequest(ALGBo.getSimpleSIPMessage());
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
            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portSrc1;
            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portServer;
            Request request = generateFreshBasicRequest(addressFromStr, addressToStr, "REGISTER", this.transportudp, 27);

            // Send the request statelessly through the SIP provider.
            this.sipProvider1.sendRequest(request);

            // Display the message in the text area.
            res = ("Request sent:\n" + request.toString() + "\n\n");
        } catch (Exception e) {
            // If an error occurred, display the error.
            res = "Request sent failed: " + e.getMessage() + "\n";
        }
        return res;
    }

    public String sendRegisterStateful() {
        String res = "";
        // A method called when you click on the "Reg (SL)" button.
        try {
            // Get the destination address from the text field.
            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
            //register here has the same URI to and FROM
            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portSrc1;
            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portServer;
            Request request = generateFreshBasicRequest(addressFromStr, addressToStr, "REGISTER", this.transportudp, 27);
            // Create a new SIP client transaction.
            ClientTransaction transaction = this.sipProvider1.getNewClientTransaction(request);
            //Send the request statefully, through the client transaction.
            transaction.sendRequest();

            System.out.println("getRetransmitTimer=" + transaction.getRetransmitTimer());
            // Display the message in the text area.
            res = ("Request sent:\n" + request.toString() + "\n\n");
        } catch (Exception e) {
            // If an error occurred, display the error.
            res = "Request sent failed: " + e.getMessage() + "\n";
        }
        return res;
    }

    public String sendInvite() {
        String res = "";
        // A method called when you click on the "Reg (SL)" button.
        try {
            // Get the destination address from the text field.
            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
            //register here has the same URI to and FROM
            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portSrc1;
            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + this.portServer;
            Request request = generateFreshBasicRequest(addressFromStr, addressToStr, "INVITE", this.transportudp, 27);

            // Send the request statelessly through the SIP provider.
            this.sipProvider1.sendRequest(request);

            // Display the message in the text area.
            res = ("Request sent:\n" + request.toString() + "\n\n");
        } catch (Exception e) {
            // If an error occurred, display the error.
            res = "Request sent failed: " + e.getMessage() + "\n";
        }
        return res;
    }

    @Override
    /**
     * Desc: Actually it process the request below: 1- Notify request: it send
     * an 200 OK as response to a Notify request
     */
    public void processRequest(RequestEvent requestEvent) {

        Request request = requestEvent.getRequest();
        System.out.println("Received request: " + request.toString());

    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        //The time out was minimize in: SipStackImpl: gov.nist.javax.sip.DIALOG_TIMEOUT_FACTOR 
        AlgJPanel.resultmsg.setText("Firewall issue");
        AlgJPanel.comb1RcvMsgREG.setText("No Packet Received - SIP ALG / Firewall issue");
        AlgJPanel.comb1RcvMsgINV.setText("No Packet Received - SIP ALG / Firewall issue");

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
        // Get the response and the request of a transaction
        Response response = responseEvent.getResponse();
        Request request = responseEvent.getClientTransaction().getRequest();
        //perform the ALG detection
        algBo.algdetection(request, response);

        CSeqHeader cseqHd = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
        String methodResponse = cseqHd.getMethod();
        String responseStr = response.toString();
//            //log the request to log file
//            PrintWriterObj printSingleton = PrintWriterObj.getInstance();
//            PrintWriter pw = printSingleton.getSipLogsPW();
//            PrintWriterObj.writePrintWriter(pw, "Process Response...........\n" + response.toString());
        // Display the response message in the text area.
        System.out.println("Received response: " + responseStr);
        if (methodResponse.equals("REGISTER")) {
               //TODO: recognize the combination in order to choose the right output text
            //port, transpot, port src, port dest

            //post the result to the corresponding output: 
            AlgJPanel.comb1RcvMsgREG.setText("Received response:\n " + responseStr);
            AlgJPanel.comb1RcvMsgREG.setCaretPosition(0);
        } else if (methodResponse.equals("INVITE")) {
            AlgJPanel.comb1RcvMsgINV.setText("Received response:\n " + responseStr);
            AlgJPanel.comb1RcvMsgINV.setCaretPosition(0);
        }
        /*
         TODO 1- implement the comparision algo then post the ALG detector message
         2- post the message:             
         */
        AlgJPanel.resultmsg.setText("No ALG Detected");
    }
}
