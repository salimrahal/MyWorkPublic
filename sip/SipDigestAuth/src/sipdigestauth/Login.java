package sipdigestauth;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.ListeningPoint;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.SipFactory;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Login implements SipListener {
	SipFactory sipFactory; // Used to access the SIP API.
	SipStack sipStack; // The SIP stack.
	SipProvider sipProvider; // Used to send SIP messages.
	MessageFactory messageFactory; // Used to create SIP message factory.
	HeaderFactory headerFactory; // Used to create SIP headers.
	AddressFactory addressFactory; // Used to create SIP URIs.
	ListeningPoint listeningPoint; // SIP listening IP address/port.
	Properties properties; // Other properties.
	protected Dialog dialog;
	protected ClientTransaction inviteTid;

	String ip = "93.185.238.210"; // The local IP address.
	int port = 5060; // The local port.
	String protocol = "UDP"; // The local protocol (UDP).
	String contactTransport = "udp";
        int tag = (new Random()).nextInt(); // The local tag.
	Address contactAddress; // The contact address.
	ContactHeader contactHeader; // The contact header.
	
	protected Request request;
	protected boolean retry;
	protected long cseq = 0;
        
        protected String privateUserId;
        
        //TalkSwitch
	protected String username = "299";
	protected String password = "Winter2013";
         protected String proxy = "173.231.103.38";
	private String server = "173.231.103.38";
        
        //Sip2Sip
//        protected String username = "salimrahal";
//	protected String password = "1975*1990+";
//	
//        protected String proxy = "sip2sip.info";
//	private String server = "sip2sip.info";
	


	private static final Logger logger = Logger.getLogger(Login.class
			.getSimpleName());

	public void init() {
		try {
			logger.addAppender(new ConsoleAppender(new SimpleLayout()));
			// Get the local IP address.
			//this.ip = InetAddress.getLocalHost().getHostAddress();
                        
			// Create the SIP factory and set the path name.
			this.sipFactory = SipFactory.getInstance();
			this.sipFactory.setPathName("gov.nist");
			// Create and set the SIP stack properties.
			this.properties = new Properties();
			this.properties.setProperty("javax.sip.STACK_NAME", "stack");
			this.properties.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "32");
			
			if(proxy != null) {
				this.properties.setProperty("javax.sip.OUTBOUND_PROXY", proxy + ':' + port + '/' + protocol);
			}

			this.properties.setProperty(
					"gov.nist.javax.sip.LOG_MESSAGE_CONTENT", "true");
			this.properties.setProperty("gov.nist.javax.sip.DEBUG_LOG",
					"mss-jsip-debuglog.txt");
			this.properties.setProperty("gov.nist.javax.sip.SERVER_LOG",
					"mss-jsip-messages.xml");
			// Create the SIP stack.
			this.sipStack = this.sipFactory.createSipStack(this.properties);
			// Create the SIP message factory.
			this.messageFactory = this.sipFactory.createMessageFactory();
			// Create the SIP header factory.
			this.headerFactory = this.sipFactory.createHeaderFactory();
			// Create the SIP address factory.
			this.addressFactory = this.sipFactory.createAddressFactory();
			// Create the SIP listening point and bind it to the local IP
			// address, port and protocol.
			this.listeningPoint = this.sipStack.createListeningPoint(this.ip,
					this.port, this.protocol);
			// Create the SIP provider.
			this.sipProvider = this.sipStack
					.createSipProvider(this.listeningPoint);
			// Add our application as a SIP listener.
			this.sipProvider.addSipListener(this);
			// Create the contact address used for all SIP messages.
			this.contactAddress = this.addressFactory.createAddress("sip:" + this.username + "@"
					+ this.ip + ";transport="+contactTransport);
			// Create the contact header used for all SIP messages.
			this.contactHeader = this.headerFactory
					.createContactHeader(contactAddress);
			// Display the local IP address and port in the text area.
			logger.debug("Local address: " + this.ip + ":" + this.port + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			// If an error occurs, display an error message box and exit.
			logger.error(e);
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		Login login = new Login();
		login.init();
		login.register(null);
                //login.option(null);
	}

	public void register(Response response) {
		try {
			cseq++;
			ArrayList viaHeaders = new ArrayList();
			ViaHeader viaHeader = this.headerFactory.createViaHeader(this.ip,
					this.port, "tcp", null);
			viaHeaders.add(viaHeader);
			// The "Max-Forwards" header.
			MaxForwardsHeader maxForwardsHeader = this.headerFactory
					.createMaxForwardsHeader(70);
			// The "Call-Id" header.
			CallIdHeader callIdHeader = this.sipProvider.getNewCallId();
			// The "CSeq" header.
			CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(cseq,
					"REGISTER");

			Address fromAddress = addressFactory.createAddress("sip:"
					+ username + '@' + server);

			FromHeader fromHeader = this.headerFactory.createFromHeader(
					fromAddress, String.valueOf(this.tag));
			// The "To" header.
			ToHeader toHeader = this.headerFactory.createToHeader(fromAddress,
					null);

			// this.contactHeader = this.headerFactory
			// .createContactHeader(contactAddress);

			request = this.messageFactory.createRequest("REGISTER sip:"
					+ server + " SIP/2.0\r\n\r\n");
			request.addHeader(callIdHeader);
			request.addHeader(cSeqHeader);
			request.addHeader(fromHeader);
			request.addHeader(toHeader);
			request.addHeader(maxForwardsHeader);
			request.addHeader(viaHeader);
			request.addHeader(contactHeader);
			if (response != null) {
				retry = true;
				AuthorizationHeader authHeader = Utils.makeAuthHeader(headerFactory, response,
						request, username, password);
				request.addHeader(authHeader);
			}
			inviteTid = sipProvider.getNewClientTransaction(request);
			// send the request out.
			inviteTid.sendRequest();
			this.dialog = inviteTid.getDialog();
			// Send the request statelessly through the SIP provider.
//			this.sipProvider.sendRequest(request);

			// Display the message in the text area.
			logger.debug("Request sent:\n" + request.toString() + "\n\n");
		} catch (Exception e) {
			// If an error occurred, display the error.
			e.printStackTrace();
			logger.debug("Request sent failed: " + e.getMessage() + "\n");
		}
	}

        public void option(Response response) {
		try {
			cseq++;
			ArrayList viaHeaders = new ArrayList();
			ViaHeader viaHeader = this.headerFactory.createViaHeader(this.ip,
					this.port, "tcp", null);
			viaHeaders.add(viaHeader);
			// The "Max-Forwards" header.
			MaxForwardsHeader maxForwardsHeader = this.headerFactory
					.createMaxForwardsHeader(70);
			// The "Call-Id" header.
			CallIdHeader callIdHeader = this.sipProvider.getNewCallId();
			// The "CSeq" header.
			CSeqHeader cSeqHeader = this.headerFactory.createCSeqHeader(cseq,
					"OPTION");

			Address fromAddress = addressFactory.createAddress("sip:"
					+ username + '@' + server);

			FromHeader fromHeader = this.headerFactory.createFromHeader(
					fromAddress, String.valueOf(this.tag));
			// The "To" header.
			ToHeader toHeader = this.headerFactory.createToHeader(fromAddress,
					null);

			// this.contactHeader = this.headerFactory
			// .createContactHeader(contactAddress);

			request = this.messageFactory.createRequest("OPTION sip:"
					+ server + " SIP/2.0\r\n\r\n");
			request.addHeader(callIdHeader);
			request.addHeader(cSeqHeader);
			request.addHeader(fromHeader);
			request.addHeader(toHeader);
			request.addHeader(maxForwardsHeader);
			request.addHeader(viaHeader);
			request.addHeader(contactHeader);
			if (response != null) {
				retry = true;
				AuthorizationHeader authHeader = Utils.makeAuthHeader(headerFactory, response,
						request, username, password);
				request.addHeader(authHeader);
			}
			inviteTid = sipProvider.getNewClientTransaction(request);
			// send the request out.
			inviteTid.sendRequest();
			this.dialog = inviteTid.getDialog();
			// Send the request statelessly through the SIP provider.
//			this.sipProvider.sendRequest(request);

			// Display the message in the text area.
			logger.debug("Request sent:\n" + request.toString() + "\n\n");
		} catch (Exception e) {
			// If an error occurred, display the error.
			e.printStackTrace();
			logger.debug("Request sent failed: " + e.getMessage() + "\n");
		}
	}
	@Override
	public void processResponse(ResponseEvent responseEvent) {
		int statusCode = responseEvent.getResponse().getStatusCode();
                System.out.println("message recieved="+responseEvent.getResponse().toString());
		if (statusCode > 400 && statusCode < 410 && !retry) {
			register(responseEvent.getResponse());
		}
	}

	@Override
	public void processRequest(RequestEvent requestEvent) {
		
	}

	@Override
	public void processTimeout(TimeoutEvent timeoutEvent) {
		
	}

	@Override
	public void processIOException(IOExceptionEvent exceptionEvent) {
		
	}

	@Override
	public void processTransactionTerminated(
			TransactionTerminatedEvent transactionTerminatedEvent) {
		
	}

	@Override
	public void processDialogTerminated(
			DialogTerminatedEvent dialogTerminatedEvent) {
		
	}
}
