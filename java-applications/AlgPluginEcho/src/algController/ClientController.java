/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algController;

import algBo.ALGBo;
import algBo.Networking;
import static algBo.Networking.getLocalIpAddress;
import algGui.AlgJPanel;
import algVo.Test;
import java.awt.Color;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salim
 */
public class ClientController {
//BO

    ALGBo algBo;

    Properties properties;          // Other properties.
    //Extract the config File
    //ConfVO confVO = ConfBO.retrieveConfigurations("./conf/properties.xml");
    // ConfVO confVO = ConfVO.getInstance();
    // Objects keeping local configuration.
    String iplocal = null;
    String hostnameLocal;
    Integer portSrc1;
    Integer portSrc2;
    Integer portSrc3;
    Integer portSrc4;

    String extlocal;
    //Server Info
    String ipServer;
    Integer portServer1;
    Integer portServer2;
    Integer portServer3;
    Integer portServer4;

    String transport1;
    String transport2;
    String transport3;
    String transport4; // 
    int tag = (new Random()).nextInt(); // The local tag.
    //Address contactAddress;         // The contact address.
    //ContactHeader contactHeader;    // The contact header.
    public boolean retryAuth;
    public long seqReg = 0;
    public long seqSub = 0;
    /**
     * ******UI Parameters: **********
     */
    String toSipURI;
    //local SIP URI taken from the config
    String localSipURI = "sip:" + 201 + "@" + ipServer + ":" + transport1;

    //String requestURITextField = "sip:" + 201 + "@" + ipServer + ":" + portServer;
    public ClientController() throws SocketException {
        algBo = new ALGBo();
        //parse xml file
        algBo.performConfiParsing();
        extlocal = algBo.getExtlocal();
        iplocal = algBo.getIplocal();
        //get server configs
        ipServer = algBo.getIpServer();
        
        portSrc1 = algBo.getPortsrc1();
        portSrc2 = algBo.getPortsrc2();
        portSrc3 = algBo.getPortsrc3();
        portSrc4 = algBo.getPortsrc4();

        portServer1 = algBo.getPortdest1();
        portServer2 = algBo.getPortdest2();
        portServer3 = algBo.getPortdest3();
        portServer4 = algBo.getPortdest4();

        transport1 = algBo.getTransport1();
        transport2 = algBo.getTransport2();
        transport3 = algBo.getTransport3();
        transport4 = algBo.getTransport4();
        hostnameLocal = algBo.getHostname();
    }

    public ALGBo getAlgBo() {
        return algBo;
    }

    public void setAlgBo(ALGBo algBo) {
        this.algBo = algBo;
    }
    public String getIplocal() throws SocketException {
        return this.iplocal;
    }

    public String resetIplocal() throws SocketException {
        setIplocal(algBo.getIplocal());
        return getIplocal();
    }

    public void setIplocal(String iplocal) {
        this.iplocal = iplocal;
    }

    public Request generateFreshBasicRequest(String fromAdress, String destAdresstextfield, String ipLocal, SipProvider sippro, String method, Test comb, Integer expiresparam) throws ParseException, InvalidArgumentException, SocketException {
        /*3261: CSEQ:
         * “ When a UAC resubmits a request with its credentials after receiving a
         401 (Unauthorized) or 407 (Proxy Authentication Required) response,
         it MUST increment the CSeq header field value as it would normally
         when sending an updated request.
         “
         */
        seqReg++;
        Integer expires = expiresparam;
        Address contactAddress;         // The contact address.
        ContactHeader contactHeader;    // The contact header.
        // Create the contact address used for all SIP messages.
        contactAddress = this.addressFactory.createAddress("sip:" + this.extlocal + "@" + ipLocal + ":" + comb.getPortscr() + ";transport=" + comb.getTransport());
        // Create the contact header used for all SIP messages.
        contactHeader = this.headerFactory.createContactHeader(contactAddress);

        // Get the destination address from the text field.
        Address addressTo = this.addressFactory.createAddress(destAdresstextfield);
        // Create the request URI for the SIP message.
        javax.sip.address.URI requestURI = addressTo.getURI();

        // Create the SIP message headers.
        // The "Via" headers.
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = this.headerFactory.createViaHeader(getIplocal(), comb.getPortscr(), comb.getTransport(), null);
        viaHeaders.add(viaHeader);

        //Adding the branch param
        int tagBranch = (new Random()).nextInt(); // The local tag.
        viaHeader.setBranch("z9hG4bK" + tagBranch);

        // The "Max-Forwards" header.
        MaxForwardsHeader maxForwardsHeader = this.headerFactory.createMaxForwardsHeader(70);
        // The "Call-Id" header.
        CallIdHeader callIdHeader = sippro.getNewCallId();

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

   

    //it takes the combination including the sequence number: 1, udp, 5060, 5060
    public String sendRegisterStateful(Test combination) {
        String res = "";
        SipProvider sipPro = null;
        Request request = null;
        String iplocaltmp;
        // A method called when you click on the "Reg (SL)" button.
        try {
            // Get the destination address from the text field.
            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
            //register here has the same URI to and FROM
            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortscr();
            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortDest();
            sipPro = getSipProvider(combination.getSeqNumber());
            if (sipPro != null) {
                iplocaltmp = resetIplocal();
                System.out.println("after reset:iplocal" + iplocaltmp);
                if (!iplocaltmp.equalsIgnoreCase(Networking.LOOPBACK)) {
                    request = generateFreshBasicRequest(addressFromStr, addressToStr, iplocaltmp, sipPro, "REGISTER", combination, 27);
                    // Create a new SIP client transaction.
                    ClientTransaction transaction = sipPro.getNewClientTransaction(request);
                    //Send the request statefully, through the client transaction.
                    transaction.sendRequest();
                 
                    // Display the message in the text area.
                    res = ("Request sent:\n" + request.toString() + "\n\n");
                } else {
                    res = ALGBo.getNetworkError(iplocaltmp);
                    setresultmessage(res);
                }//end of else not loopback
            }//end SIpPro !null
            else {
                res = ALGBo.PLUGIN_REINSTALL;
                setresultmessage(res);
//createSipStack();
                //createSipFrameWork();
            }

        } catch (Exception e) {
            // If an error occurred, display the error.
            res = "Request sent failed: " + e.getMessage() + "\n";
            setresultmessage(res);
        }
        return res;
    }

    public String sendInvite(Test combination) {
        String res = "";
        SipProvider sipPro = null;
        String iplocaltmp;
        // A method called when you click on the "Reg (SL)" button.
        try {
            // Get the destination address from the text field.
            ////"sip:" + extLocal + "@" + ipServer + ":" + portlocal;
            //register here has the same URI to and FROM
            String addressFromStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortscr();
            String addressToStr = "sip:" + extlocal + "@" + ipServer + ":" + combination.getPortDest();
            sipPro = getSipProvider(combination.getSeqNumber());
            if (sipPro != null) {
                iplocaltmp = resetIplocal();
                System.out.println("after reset:iplocal" + iplocaltmp);
                if (!iplocaltmp.equalsIgnoreCase(Networking.LOOPBACK)) {
                    Request request = generateFreshBasicRequest(addressFromStr, addressToStr, iplocaltmp, sipPro, "INVITE", combination, 27);
                //TODO:ALG create content Type, header
                    // Create a new SIP client transaction.
                    ClientTransaction transaction = sipPro.getNewClientTransaction(request);
                    //Send the request statefully, through the client transaction.
                    transaction.sendRequest();

                    // Display the message in the text area.
                    res = ("Request sent:\n" + request.toString() + "\n\n");
                } else {
                    res = ALGBo.getNetworkError(iplocaltmp);
                    setresultmessage(res);
                }//end of else not loopback
            }//end SIpPro !null
            else {
                res = ALGBo.PLUGIN_REINSTALL;
                setresultmessage(res);
//createSipStack();
                //createSipFrameWork();
            }
        } catch (Exception e) {
            // If an error occurred, display the error.
            res = "Request sent failed: " + e.getMessage() + "\n";
            setresultmessage(res);
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

    // it handle the response timeout
    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        //The time out was minimize in: SipStackImpl: gov.nist.javax.sip.DIALOG_TIMEOUT_FACTOR 
        setresultmessage("You have a firewall that might be blocking your Voice over IP Service. Please check your router or Internet Service Provider");
        ClientTransaction clientTrans = timeoutEvent.getClientTransaction();
        Request request = clientTrans.getRequest();
        setFWMsgToCorrespondentOutput(request);
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
        processresponseImpl(responseEvent);
    }

    public synchronized void processresponseImpl(ResponseEvent responseEvent) {
        // Get the response and the request of a transaction
        Response response = responseEvent.getResponse();
        ClientTransaction clientTrans = responseEvent.getClientTransaction();
        Request request = clientTrans.getRequest();

        //perform the ALG detection
        Integer resultcode = algBo.algdetection(request, response);
        String outmsg = "";
        if (resultcode.equals(1)) {
            outmsg = "No ALG Detected";
        } else if (resultcode.equals(-1)) {
            outmsg = "Critical Error : SIP ALG is corrupting SIP Messages, Please disable SIP ALG in the router";
        }
        else if (resultcode.equals(-2)) {
            outmsg = "Warning: SIP ALG detected, Is highly recommended to disable SIP ALG in the router";
        }
        setresultmessage(outmsg);
        //set the received msgs to the correspondent output
        CSeqHeader cseqHd = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
        String methodResponse = cseqHd.getMethod();
        String responseStr = response.toString();
//            //log the request to log file
//            PrintWriterObj printSingleton = PrintWriterObj.getInstance();
//            PrintWriter pw = printSingleton.getSipLogsPW();
//            PrintWriterObj.writePrintWriter(pw, "Process Response...........\n" + response.toString());

        System.out.println("Received response: " + responseStr);
        // Display the response message in the text area, where the radio button is selected
        setRcvMsgToCorrespondentOutput(methodResponse, response);
    }

    public void setFWMsgToCorrespondentOutput(Request request) {
        Integer combId = algBo.getCombinationIdFromRequest(request);
        switch (combId) {
            case 1:
                AlgJPanel.comb1RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
                AlgJPanel.comb1RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
                break;
            case 2:
                AlgJPanel.comb2RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
                AlgJPanel.comb2RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
                break;
            case 3:
                AlgJPanel.comb3RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
                AlgJPanel.comb3RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
                break;
            case 4:
                AlgJPanel.comb4RcvMsgREG.setText(ALGBo.FIREWALL_MSG);
                AlgJPanel.comb4RcvMsgINV.setText(ALGBo.FIREWALL_MSG);
                break;
        }
    }

    public void setRcvMsgToCorrespondentOutput(String methodResponse, Response response) {
        String responseStr = response.toString();
        Integer combId = algBo.getCombinationIdFromResponse(response);

        switch (combId) {
            case 1:
                //filling the output log after sending the messeges
                //REG
                if (methodResponse.equals("REGISTER")) {
                    AlgJPanel.comb1RcvMsgREG.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb1RcvMsgREG.setCaretPosition(0);
                } else if (methodResponse.equals("INVITE")) {
                    //INV
                    AlgJPanel.comb1RcvMsgINV.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb1RcvMsgINV.setCaretPosition(0);
                }
                break;
            case 2:
                if (methodResponse.equals("REGISTER")) {
                    AlgJPanel.comb2RcvMsgREG.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb2RcvMsgREG.setCaretPosition(0);
                } else if (methodResponse.equals("INVITE")) {
                    //INV
                    AlgJPanel.comb2RcvMsgINV.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb2RcvMsgINV.setCaretPosition(0);
                }
                break;
            case 3:
                if (methodResponse.equals("REGISTER")) {
                    AlgJPanel.comb3RcvMsgREG.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb3RcvMsgREG.setCaretPosition(0);
                } else if (methodResponse.equals("INVITE")) {
                    //INV
                    AlgJPanel.comb3RcvMsgINV.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb3RcvMsgINV.setCaretPosition(0);
                }
                break;
            case 4:
                if (methodResponse.equals("REGISTER")) {
                    AlgJPanel.comb4RcvMsgREG.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb4RcvMsgREG.setCaretPosition(0);
                } else if (methodResponse.equals("INVITE")) {
                    //INV
                    AlgJPanel.comb4RcvMsgINV.setText("Received response:\n " + responseStr);
                    //set the caret to the top always
                    AlgJPanel.comb4RcvMsgINV.setCaretPosition(0);
                }
                break;
        }
    }

    /*
    
     */
    protected void setresultmessage(String outmessage) {
        //AlgJPanel.resultmsgjlabel.setText(outmessage);        
        String labelText = String.format("<html><div style=\"width:%dpx;\"><p align=\"center\">%s</p></div><html>", 200, outmessage);
        AlgJPanel.resultmsgjlabel.setText(labelText);
        AlgJPanel.resultmsgjlabel.setBackground(Color.red);
    }

}
