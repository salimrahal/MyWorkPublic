/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import controller.ClientController;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sip.message.Request;
import javax.sip.message.Response;
import javax.swing.JLabel;
import vo.PrintWriterObj;
import vo.Status;

/**
 * @author salim this thread is responsible for setting the status of a label to
 * connected during an expires time
 */
public class StatusRunnable implements Runnable {

    PrintWriterObj printSingleton = PrintWriterObj.getInstance();
    Integer expires;
    JLabel jlabel;//The jlabel it won't be updated on Windows platform.

    //int cycle = 24; //how many time we invoke the Subscription every 1 hour.
    //int sleepTime;//in millisecond:  is the same value as the expires*1000ms [s*1000 = in ms]
//    public StatusRunnable( Integer expires) {
//      this.expires = expires;//used to retrieve the expirey time
//    }
    public StatusRunnable(Integer expires, JLabel jlabel) {
        this.expires = expires;//used to retrieve the expirey time
        this.jlabel = jlabel;
    }

    @Override
    public void run() {
        try {
            long sleepTime = this.expires * 1000;
            //log the request to log file
            PrintWriter pw = printSingleton.getSipLogsPW();
            String msg = "StatusRunnable= " + new Date() + "/thread name:" + Thread.currentThread().getName() + ",sleep" + sleepTime + " ms before reseting the status to disconnected";
            System.out.println(msg);
            PrintWriterObj.writePrintWriter(pw, msg);

            PrintWriterObj.writePrintWriter(pw, "\"StatusRunnable..");
            Status.computingStatus(jlabel, Status.STATUS.CONNECTED.toString());
            Thread.sleep(sleepTime);
            Status.computingStatus(jlabel, Status.STATUS.DISCONNETED.toString());

            //update the status the label to connected
            //Status.setStatus(Status.STATUS.CONNECTED.toString());
            //after the time expires reset the status to disconnected
            //Status.setStatus(Status.STATUS.DISCONNETED.toString());
        } catch (Exception ex) {
            Logger.getLogger(StatusRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
