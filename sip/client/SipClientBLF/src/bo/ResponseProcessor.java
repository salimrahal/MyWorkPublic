/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import controller.ClientController;
import gui.SipBLFUI;
import java.awt.Color;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.header.CSeqHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import vo.PrintWriterObj;
import vo.Status;

/**
 *
 * @author salim
 */
public class ResponseProcessor {

    //Handles the OK response: upon Regsiter or Subscribe
    public void processSucess(Response response, ClientController sipClientController) throws InterruptedException, Exception {
        //get the seq header to extract the method
        CSeqHeader cseqHd = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
        String methodResponse = cseqHd.getMethod();
        //Manage the success message

        /* handle 200 OK due to SUBSCRIBE request
         * 1- first subscription is sent
         * 2- upon receiving the success response, we need to get the Expires time
         * 3- case1: if expires !=0 then run a thread based on the expiry given by the server
         *    case2: if expires ==0 then don't do refresh subscription,
         *           since this response is recieved due to a cancel subscription request initiated by the user
         */
        if (methodResponse.equalsIgnoreCase(Request.SUBSCRIBE)) {
            System.out.println("Success, implement Subscribe Business code: need to refresh the Subscription");
            //get the expires from the server
            int expires = response.getExpires().getExpires();
            // in case expires is !0: we need to refresh the subscitpion 
            if (expires != 0) {
                //get the TO header from the response: To: <sip:140@173.231.103.38:5060>;tag=f6db5911c1c6911f 
                ToHeader toHeader = (ToHeader) response.getHeader(ToHeader.NAME);
                String toURI = toHeader.getAddress().getURI().toString();

                //create the Thread instance
                SubscribeRunnable subscribeRunnable = new SubscribeRunnable(sipClientController, toURI, expires);
                //subscribeRunnable.setSleepTime(expires*1000);//convert to millisecond
                //create the thread
                Thread subcribeThread = new Thread(subscribeRunnable);
                //start the thread
                subcribeThread.start();
                //The join is removed                   
//                    subcribeThread.join();

            } //in case we got 200 OK with SUBSCRIPTION expires:0, don't do anything since the subcription is cancled by the UAC
            else if (expires == 0) {
                System.out.println("Subscription canceled");
            }

        } else if (methodResponse.equalsIgnoreCase(Request.REGISTER)) {
            //log the request to log file
            PrintWriterObj printSingleton = PrintWriterObj.getInstance();
            PrintWriter pw = printSingleton.getSipLogsPW();
            PrintWriterObj.writePrintWriter(pw, "\"Success, implement REGISTER Business code: need to refresh the REGISTER\"\n" + response.toString());

            System.out.println("Success, implement REGISTER Business code: need to refresh the REGISTER");
            //get the expires from the server
            int expires = response.getExpires().getExpires();
            // in case expires is !0: we need to refresh the subscitpion 
            if (expires != 0) {
                //get the TO header from the response: To: <sip:140@173.231.103.38:5060>;tag=f6db5911c1c6911f 
                ToHeader toHeader = (ToHeader) response.getHeader(ToHeader.NAME);
                String toURI = toHeader.getAddress().getURI().toString();

                //build the runnable instance          
                RegisterRunnable registerRunnable = new RegisterRunnable(sipClientController, toURI, expires);
                //subscribeRunnable.setSleepTime(expires*1000);//convert to millisecond
                //create the thread
                Thread registerThread = new Thread(registerRunnable);
                //start the thread
                registerThread.start();

                //every time I received an OK on register I put the status to connected
               //TODO: fixe the status: it wont be rendered on Windwos, excpet updateting it from the Sip BLF
//                SipBLFUI.statusJlabel.setText(Status.STATUS.CONNECTED.toString());
//                SipBLFUI.statusJlabel.setForeground(Color.green);
                //I should run a thread that set the status to connected during a limited time equal to expiry
                //build the runnable instance 
                StatusRunnable statusRunnable = new StatusRunnable(expires, SipBLFUI.statusJlabel);
                //create the thread
                Thread statusThread = new Thread(statusRunnable);
                //start the thread
                statusThread.start();
             
                //the current thread will wait untill the subscribe thread finish and join the current one
//                    registerThread.join();
            } //in case we got 200 OK with SUBSCRIPTION expires:0, don't do anything since the subcription is cancled by the UAC
            else if (expires == 0) {
                System.out.println("registerThread canceled");
            }
        }

    }

    public void processClientError(ClientController clientController, Response response, String requestURITextField) throws Exception {
        //get the seq header to extract the method
        CSeqHeader cseqHd = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
        String methodResponse = cseqHd.getMethod();
        if (methodResponse.equalsIgnoreCase(Request.REGISTER)) {

            clientController.registerStateful(requestURITextField, response, SipClientBO.Expires_default);

        } else if (methodResponse.equalsIgnoreCase(Request.SUBSCRIBE)) {
            System.out.println("processClientError:: Method = " + methodResponse + ".........");
            //get the TO URI then resend it for Subscribe
            ToHeader toheader = (ToHeader) response.getHeader(ToHeader.NAME);
            String ToURIHeader = toheader.getAddress().getURI().toString();
            clientController.subscribe(ToURIHeader, response, SipClientBO.Expires_default);
        } else {
            System.out.println("processClientError:: Method (CSeqHeader.NAME) = " + CSeqHeader.NAME + " is not implemented");
        }

    }

}
